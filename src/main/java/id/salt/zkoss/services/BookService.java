package id.salt.zkoss.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import id.salt.zkoss.models.Book;
import id.salt.zkoss.repositories.BookRepository;

@Service("bookService")
public class BookService {
	
	@Autowired
	private BookRepository repo;
	
	public List<Book> getList()
	{
		var b = new Book();
		b.setAuthor("Author1");
		var dataLagi = repo.findAllByTitleLikeOrAuthorLike("%Titl%", "%%");
		return dataLagi;
	}
	
}
