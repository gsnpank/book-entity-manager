package telran.java2022.book.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import telran.java2022.book.model.Publisher;

@Repository
public class PublisherRepositoryImpl implements PublisherRepository {
	
	@PersistenceContext
	EntityManager em;

	@Override
	public List<String> findPublishersByAuthor(String authorName) {
		String queryString = "select distinct p.publisherName from Book b join b.authors a join b.publisher p where a.name=?1";
		TypedQuery<String> query = em.createQuery(queryString, String.class);
		query.setParameter(1, authorName);
		return query.getResultList();
	}

	@Override
	public Stream<Publisher> findDistinctByBooksAuthorsName(String authorName) {
		String queryString = "select distinct p from Book b join b.authors a join b.publisher p where a.name=?1";
		TypedQuery<Publisher> query = em.createQuery(queryString, Publisher.class);
		query.setParameter(1, authorName);
		return query.getResultStream();
	}

	@Override
	public Optional<Publisher> findById(String publisherName) {
		Publisher publisher = em.find(Publisher.class, publisherName);
		return Optional.ofNullable(publisher);
	}

	@Override
	public Publisher save(Publisher publisher) {
		em.persist(publisher);
//		em.merge(publisher);
		return publisher;
	}




}
