/**
 * Created by Emmanuel on 20/7/2016.
 */
// Libraries
import spark.ModelAndView;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.sql.DriverManager.getConnection;
import static spark.Spark.get;
import static spark.Spark.post;

public class Main {

    private static ArrayList<Student> students = null;

    public static void main(String[] args) throws Exception{

        Spark.staticFileLocation("/public");

        RunSparkEnvironment();
    }

    // H2 Database Functions
    private static void AddStudent(String matricula, String name, String lastName, String telephone)
    {
        Student stud = new Student(matricula, name, lastName, telephone);
        ExecuteQuery(stud, "insert");
    }

    private static void DeleteStudent(String matricula)
    {
        Student stud = new Student(matricula, "X", "X", "X");
        ExecuteQuery(stud, "delete");
    }

    private static void ModifyStudent(String matricula, String newName, String newLastName, String newTelephone)
    {
        // Them matricula iss the only non modifiable attribute
        // Serves as reference key
        // Once assigned it CANNOT be changed
        Student stud = new Student(matricula, newName, newLastName, newTelephone);
        ExecuteQuery(stud, "modify");
    }

    private static void ExecuteQuery(Student stud, String query)
    {
        try
        {
            System.out.println("Connecting to DataBase...");
            Class.forName("org.h2.Driver");
            // H2 automatically creates new databases is they don't exist
            // ;IFEXIST=TRUE is used to specify the application will only
            // use a pre-existing database


            Connection connectionn = getConnection("jdbc:h2:tcp://localhost/~/estudiante_db", "sa", "");
            System.out.println("\nSuccessfully Connected!");

            // Preparing to execute query
            Statement stat = connectionn.createStatement();
            ResultSet rs;
            String querycmd;

            switch (query)
            {
                case "insert":

                    querycmd = "INSERT INTO ESTUDIANTES (MATRICULA, NOMBRE, APELLIDOS, TELEFONO) VALUES ('" + stud.getMatricula() + "', '" + stud.getName() + "', '" + stud.getLastName() + "', '" + stud.getTelephone()+ "')";

                    stat.execute(querycmd);

                    break;

                case "delete":

                    querycmd = "DELETE FROM ESTUDIANTES WHERE MATRICULA='" + stud.getMatricula() + "'";

                    stat.execute(querycmd);

                    break;

                case "modify":

                    querycmd = "UPDATE ESTUDIANTES SET NOMBRE='" + stud.getName() + "' ,APELLIDOS='" + stud.getLastName() + "' ,TELEFONO='"  + stud.getTelephone() + "' WHERE MATRICULA='" + stud.getMatricula() + "'";

                    stat.execute(querycmd);

                    break;

                case "list": // search query

                    rs = stat.executeQuery("Select * From ESTUDIANTES");

                    students = new ArrayList<>();

                    while(rs.next())
                        students.add(new Student(rs.getString("matricula"), rs.getString("nombre"), rs.getString("apellidos"), rs.getString("telefono")));

                    break;
                default: // specific search query

                    rs = stat.executeQuery("Select * From ESTUDIANTES WHERE MATRICULA='" + query + "'");

                    students = new ArrayList<>();

                    while(rs.next())
                        students.add(new Student(rs.getString("matricula"), rs.getString("nombre"), rs.getString("apellidos"), rs.getString("telefono")));

                    break;
            }
        }
        catch (ClassNotFoundException exp)// Driver error
        {
            System.out.println("DRIVER ERROR: " + exp.getMessage());
        }
        catch (SQLDataException exp)
        {
            System.out.println("SQL DATA ERROR: " + exp.getMessage());
        }
        catch (SQLException exp)
        {
            System.out.println("SQL ERROR: " + exp.getMessage());
        }
        catch (Exception exp) // General errors
        {
            System.out.println("ERROR! --> " + exp.getMessage());
        }
    }

    // Sparkjava Environment Functions
    public static void RunSparkEnvironment()
    {
        // http://localhost:4567/
        get("/", (req, res) -> {
            res.status(200);
            Map<String, Object> attributes = new HashMap<>();

            ExecuteQuery(new Student(), "list");

            if(!students.isEmpty())
                attributes.put("Registry", students.toArray());
            else
                System.out.println("EMPTY VARIABLE");

            attributes.put("message", "Registered Students");

            return new ModelAndView(attributes, "index.ftl");
        }, new FreeMarkerEngine());

        post("/delete", (req, res) -> {
            DeleteStudent(req.queryParams("matricula"));

            res.redirect("/");

            return "You have successfully deleted a student";
        });

        // http://localhost:4567/modify/xxxxXXXX
        // Best to use Matricula as parameter to avoid data corruption "/"
        get("/modify", (req, res) -> {
            res.status(200);
            Map<String, Object> attributes = new HashMap<>();

            ExecuteQuery(new Student(), req.queryParams("matricula"));

            Student stud = students.remove(0);

            attributes.put("message", "Student Modification");
            attributes.put("matricula", req.queryParams("matricula"));
            attributes.put("oldName", stud.getName());
            attributes.put("oldLastName", stud.getLastName());
            attributes.put("oldTelephone", stud.getTelephone());

            return new ModelAndView(attributes, "modify.ftl");
        }, new FreeMarkerEngine());

        // http://localhost:4567/modify/xxxxXXXX
        post("/modify", (req, res) -> {
            if(req.queryParams("matricula") == "" || req.queryParams("new_name") == "" || req.queryParams("new_last_name") == "" || req.queryParams("new_telephone") == "")
                res.redirect("/");
            else
            {
                ModifyStudent(req.queryParams("matricula"),
                        req.queryParams("new_name"),
                        req.queryParams("new_last_name"),
                        req.queryParams("new_telephone"));

                res.redirect("/");
            }

            return "You have successfully modify a student";
        });

        // http://localhost:4567/new
        get("/new", (req, res) -> {
            res.status(200);
            Map<String, Object> attributes = new HashMap<>();

            attributes.put("message", "Student Registration");

            return new ModelAndView(attributes, "register.ftl");
        }, new FreeMarkerEngine());

        //http://localhost:4567/new
        post("/new", (req, res) -> {

            if(req.queryParams("matricula") == "" || req.queryParams("name") == "" || req.queryParams("last_name") == "" || req.queryParams("telephone") == "")
                res.redirect("/");
            else
            {
                AddStudent(req.queryParams("matricula"),
                        req.queryParams("name"),
                        req.queryParams("last_name"),
                        req.queryParams("telephone"));

                res.redirect("/");
            }

            return "You have just added a student";
        });

    }
}