package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class ToDo extends Model {
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	public String description;
	public boolean done = false;
	public Date dateOfDone;
	@ManyToOne
	public User assignedUser;

	private static Finder<String, ToDo> find = new Finder<String, ToDo>(String.class, ToDo.class);
    
	public static List<ToDo> findTodosByUserEMail(String email) {
		return find.where()
				.eq("done", false)
				.eq("assignedUser.email", email)
				.findList();
	}


    public static int count() {
        return find.findRowCount();
    }

}
