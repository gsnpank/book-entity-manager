package telran.java2022.book.dao;

import java.util.Optional;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import telran.java2022.book.model.Book;

@Repository
public class BooksRepositoryImpl implements BookRepository {
	
	@PersistenceContext
	EntityManager em;

	@Override
	public Stream<Book> findByAuthorsName(String authorName) {
		String queryString = "select distinct b from Book b join b.authors a where a.name=?1";
		TypedQuery<Book> query = em.createQuery(queryString, Book.class);
		query.setParameter(1, authorName);
		return query.getResultStream();
	}

	@Override
	public Stream<Book> findByPublisherPublisherName(String publisherName) {
		String queryString = "select distinct b from Book b join b.publisher p where p.publisherName=?1";
		TypedQuery<Book> query = em.createQuery(queryString, Book.class);
		query.setParameter(1, publisherName);
		return query.getResultStream();

	}

	@Override
	public boolean existsById(String isbn) {
		return em.find(Book.class, isbn) != null;
	}

	@Override
	public Optional<Book> findById(String isbn) {
		return Optional.ofNullable(em.find(Book.class, isbn));
	}

	@Override
//	@Transactional
	public Book save(Book book) {
		em.persist(book);
//		em.merge(book);
		return book;
	}

	@Override
	@Transactional
	public void deleteById(String isbn) {
		Book book = em.find(Book.class, isbn);
		em.remove(book);

	}

}
