package db;

import java.io.*; 
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import business.Actor;

public class ActorTextFile implements DAO<Actor> {
	private List<Actor> actors = null;
	private Path actorsPath = null;
	private File actorsFile = null;
	private final String FIELD_SEP = "\t";
	
	public ActorTextFile() {
		actorsPath = Paths.get("actors.txt");
		actorsFile = actorsPath.toFile();
		actors = getALL();		
	}

	@Override
	public Actor get(int actorID) {
		return null;
	}
	
	// get all products from text file if our list is null
	@Override
	public List<Actor> getALL() {
		if (actors != null) {
			return actors;
		}
		actors = new ArrayList<>();
		if (Files.exists(actorsPath)) {	
			try (BufferedReader in = new BufferedReader(new FileReader(actorsFile))) {
				// read products from file into our list 
				String line = in.readLine();
				while (line != null) {
					String[] fields = line.split(FIELD_SEP);
					String actorIDStr = fields[0];
					int actorID = Integer.parseInt(actorIDStr);
					String firstName = fields[1];
					String lastName = fields[2];
					String gender = fields[3];
					String birthDateStr = fields[4];
					LocalDate birthDate = LocalDate.parse(birthDateStr);
					
					Actor p = new Actor(actorID,firstName,lastName,gender,birthDate);
					actors.add(p);
					line = in.readLine();
				}			
			} catch (IOException ioe) {
				System.out.println(ioe);
				return null;
			}
		
		} else {
			System.out.println(actorsPath.toAbsolutePath()+ " doesn't exist.");
			return null;
		}
		return actors;
	}


	@Override
	public boolean add(Actor p) {
		actors.add(p);
		return saveALL();
	}

	@Override
	public boolean update(Actor a) {
		Actor oldActor = this.get(a.getActorID());
		int i = actors.indexOf(oldActor);
		actors.remove(i);
		actors.add(i,a);
		return saveALL();
	}

	@Override
	public boolean delete(Actor a) {
		actors.remove(a);
		return saveALL();
	}
	
	private boolean saveALL() {
		// after every maintenance (add, update, delete) function, rewrite the text file
		// rewrite the text file
		try (PrintWriter out = new PrintWriter(
				  	           new BufferedWriter(
				               new FileWriter(actorsFile)))) {
		// write all products in the list to the file 
			for (Actor a: actors) {
				out.print(a.getActorID()+FIELD_SEP);
				out.print(a.getFirstName()+FIELD_SEP);
				out.print(a.getLastName()+FIELD_SEP);
				out.print(a.getGender()+FIELD_SEP);
				out.println(a.getBirthDate());           // last one make 
			}
			return true;
		}
		catch (IOException ioe) {
			System.out.println(ioe);
			return false;
		}
	}

	

}
