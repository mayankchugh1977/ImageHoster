package ImageHoster.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    //Write the annotation to specify that the corresponding attribute is a primary key
    @GeneratedValue(strategy = GenerationType.AUTO)
    //Write the annotation to specify that the attribute will be mapped to the column in the database.
    //Also explicitly mention the column name as 'id'
    @Column(name = "id")
    private Integer id;

    //Write the annotation to specify that the attribute will be mapped to the column in the database.
    //Also explicitly mention the column name as 'username'
    @Column(name = "username")
    private String username;

    //Write the annotation to specify that the attribute will be mapped to the column in the database.
    //Also explicitly mention the column name as 'password'
    @Column(name = "password")
    private String password;

    //Write the annotation to specify the below mentioned features
    //The 'users' table is mapped to 'user_profile' table with One:One mapping
    //Also if a record in 'user_profile' table is deleted or updated, then all the records in 'users' table associated to that particular record in 'user_profile' table will be deleted or updated  first and then the record in the 'user_profile' table will be deleted or updated
    //FetchType is EAGER

    //Write the annotation to indicate that the name of the column in 'users' table referring the primary key in 'user_profile' table will be 'profile_id'
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id")
//    @Transient
    private UserProfile profile;

    //The 'users' table is referenced by the 'images' table
    //The table (primary key) is referenced by the 'user' field in the 'images' table
    //cascade = CascadeType.REMOVE specifies that if a record in 'users' table is deleted, then all the records in 'images' table associated to that particular record in 'users' table will be deleted first and then the record in the 'users' table will be deleted
    //FetchType is LAZY
    //Write the annotation to implement the above features
    //@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();


    //Genarate getters and setters for all the attributes


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}

