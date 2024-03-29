package com.renandiego.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.renandiego.workshopmongo.domain.Post;
import com.renandiego.workshopmongo.domain.User;
import com.renandiego.workshopmongo.dto.AuthorDTO;
import com.renandiego.workshopmongo.dto.CommentDTO;
import com.renandiego.workshopmongo.repository.PostRepository;
import com.renandiego.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner{
	

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");

		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		
		Post post1 = new Post(null, sdf.parse("21/03/2018"), "Partiu viagem", "Vou viajar pra São Paulo", new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("20/04/2018"), "Voltei de viagem", "Voltei de São Paulo", new AuthorDTO(maria));
		
		CommentDTO c1 = new CommentDTO("Boa viagem mano", sdf.parse("21/03/2018"), new AuthorDTO(alex)); 
		CommentDTO c2 = new CommentDTO("Blz", sdf.parse("20/04/2018"), new AuthorDTO(alex));
		
		post1.getComments().addAll(Arrays.asList(c1, c2));
		
		postRepository.saveAll(Arrays.asList(post1, post2));
		
		maria.getPosts().addAll(Arrays.asList(post1, post2));
		userRepository.save(maria);
	}

}
