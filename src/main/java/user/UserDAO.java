package user;

import ex7.LegoSet;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.jdbi.v3.core.result.RowView;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;


@RegisterBeanMapper(User.class)
public interface UserDAO {
    @SqlUpdate("""
        CREATE TABLE users (
            id IDENTITY,
            username VARCHAR NOT NULL UNIQUE,
            password VARCHAR NOT NULL,
            name VARCHAR NOT NULL,
            email VARCHAR NOT NULL,
            gender VARCHAR NOT NULL,
            dob DATE NOT NULL,
            enabled BIT NOT NULL
        )
        """
    )
    void createTable();

    @SqlUpdate("""
        INSERT INTO users VALUES (
                :id,
                :username,
                :password,
                :name,
                :email,
                :gender,
                :dob, 
                :enabled
        )
        """
    )
    @GetGeneratedKeys("id")
    Long insert(@BindBean User user);

    @SqlQuery("SELECT * FROM users WHERE id = :id")
    Optional<User> findByID(@Bind("id") long id);

    @SqlQuery("SELECT * FROM users WHERE username = :username")
    Optional<User> findByUserName(@Bind("username") String username);

    @SqlUpdate("DELETE FROM users WHERE username = :u.username")
    void delete(@BindBean("u") User user);

    @SqlQuery("SELECT * FROM users")
    List<User> list();




}
