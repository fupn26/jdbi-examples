package user;

import ex8.LegoSet;
import ex8.LegoSetDao;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;
import java.time.Year;

public class Main {
    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()) {
            UserDAO dao = handle.attach(UserDAO.class);
            dao.createTable();
            User user1 = User.builder()
                    .name("James Bond")
                    .username("007")
                    .password("bond")
                    .gender(User.Gender.MALE)
                    .email("bond@james.hu")
                    .dob(LocalDate.parse("1920-11-11"))
                    .enabled(true)
                    .build();
            User user2 = User.builder()
                    .name("Thor")
                    .username("thor")
                    .password("lightning")
                    .gender(User.Gender.MALE)
                    .email("thor@asgard.hu")
                    .dob(LocalDate.parse("1980-09-28"))
                    .enabled(true)
                    .build();
            User user3 = User.builder()
                    .name("Sherlock Holmes")
                    .username("find")
                    .password("you")
                    .gender(User.Gender.MALE)
                    .email("scherlock@london.hu")
                    .dob(LocalDate.parse("1887-06-10"))
                    .enabled(false)
                    .build();
            User user4 = User.builder()
                    .name("Amelia Earhart")
                    .username("fly")
                    .password("away")
                    .gender(User.Gender.MALE)
                    .email("female@pilot.hu")
                    .dob(LocalDate.parse("1920-01-26"))
                    .enabled(false)
                    .build();
            user1.setId(dao.insert(user1));
            user2.setId(dao.insert(user2));
            user3.setId(dao.insert(user3));
            user4.setId(dao.insert(user4));

            dao.list().stream().forEach(System.out::println);

            System.out.println("User with id 4:");
            System.out.println(dao.findByID(4).get().toString());

            System.out.println("User with username 007:");
            System.out.println(dao.findByUserName("007").get().toString());

            System.out.println("Delete user4: ");
            dao.delete(user4);
            dao.list().stream().forEach(System.out::println);
        }
    }
}
