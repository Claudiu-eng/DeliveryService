package mypack.BusinessLayer;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String email;
    private String password;
    private String rol;
    private int nrComenzi;

    public User() {
    }

    public int getNrComenzi() {
        return nrComenzi;
    }

    public void setNrComenzi(int nrComenzi) {
        this.nrComenzi = nrComenzi;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password=" + password +
                ", rol='" + rol + '\'' +
                '}';
    }

    /**
     * constructor for user
     * @param email
     * @param password
     * @param rol
     */
    public User(int id,String email, String password, String rol) {
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.id=id;
        nrComenzi=0;
    }

    /**
     * getter for id
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * setter for id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * getter for email
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * setter for email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * getter for password
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * setter for password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * getter for rol
     * @return
     */
    public String getRol() {
        return rol;
    }

    /**
     * setter for rol
     * @param rol
     */
    public void setRol(String rol) {
        this.rol = rol;
    }
}
