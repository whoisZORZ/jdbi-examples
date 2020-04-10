package user;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args){

        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());

        try (Handle handle = jdbi.open()) {
            UserDao dao = handle.attach(UserDao.class);
            dao.createTable();
            User user = User.builder()
                    .username("007")
                    .password("JB007")
                    .name("James Bond")
                    .email("jamesbond@mi6.com")
                    .gender(User.Gender.MALE)
                    .dob(LocalDate.parse("1920-11-11"))
                    .enabled(true)
                    .build();

            System.out.println(dao.insert(user));
            dao.findById(1).ifPresent(System.out::println);
            dao.findByUsername("James Bond").ifPresent(System.out::println);
            dao.list().forEach(System.out::println);
            dao.delete(user);

        }

    }

}
