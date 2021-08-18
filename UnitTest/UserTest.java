

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.triquang.entity.Users;

public class UserTest {

	public static void main(String[] args) {

		Users users = new Users();
		users.setEmail("aptech@gmail.com");
		users.setFullName("aptech");
		users.setPassword("1234567");

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookStoreWebsite");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		entityManager.getTransaction().begin();

		entityManager.persist(users);

		entityManager.getTransaction().commit();
		entityManager.close();
		entityManagerFactory.close();

		System.out.println("Done !");
	}

}
