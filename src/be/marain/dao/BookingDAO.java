package be.marain.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import be.marain.classes.Accreditation;
import be.marain.classes.Booking;
import be.marain.classes.Instructor;
import be.marain.classes.Lesson;
import be.marain.classes.LessonType;
import be.marain.classes.Period;
import be.marain.classes.Skier;

public class BookingDAO extends DAO<Booking> {
	public BookingDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Booking obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Booking obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Booking obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Booking find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Booking> findAll() {
		List<Booking> bookings = new ArrayList<Booking>();
		
		try {
			//Getting all the infos beside accreditations for the instructor
			//to avoid multiple lines for 1 booking
			String query = "SELECT b.bookingid, b.bookingDate, b.duration, b.isindividual, "
					+ "s.skierid, s.name AS \"skier_name\", s.surname AS \"skier_surname\", s.dateofbirth AS \"skier_dob\", s.phonenumber AS \"skier_phone\", "
					+ "p.periodid, p.startdate, p.enddate, "
					+ "l.lessonid, l.minbookings, l.maxbookings, l.lessondate"
					+ "i.instructorid, i.name AS \"instructor_name\", i.surname AS \"instructor_surname\", i.phonenumber AS \"instructor_phone\", i.dateofbirth AS \"instructor_dob\", "
					+ "lt.ltid, lt.lessonlevel, lt.price "
					+ "a.accreditationid, a.name AS \"accred_name\""
					+ "FROM booking b "
					+ "INNER JOIN period p ON p.periodid = b.periodid "
					+ "INNER JOIN skier s ON s.skierid = b.skierid "
					+ "INNER JOIN lesson l ON l.lessonid = b.lessonid "
					+ "INNER JOIN instructor i ON i.instructorid = l.instructorid "
					+ "INNER JOIN lessontype lt ON lt.ltid = l.ltid "
					+ "INNER JOIN Accreditation a ON a.accreditationid = lt.accreditationid";
			
			String accredQuery = "SELECT * FROM accreditation a "
					+ "INNER JOIN instructoraccred ia ON ia.accreditationid = a.accreditationid "
					+ "WHERE instructor id = ?";
			
			ResultSet res = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY).executeQuery(query);
			
			
			while(res.next()) {
				// Récupération des données de booking
                int bookingId = res.getInt("bookingid");
                Date bookingDate = res.getDate("bookingDate");
                int duration = res.getInt("duration");
                boolean isIndividual;
                
                if(res.getString("isindividual") == "Y") {
                	isIndividual = true;
                }else {
                	isIndividual = false;
                }
           
                // Récupération des données de skier
                int skierId = res.getInt("skierid");
                String skierName = res.getString("skier_name");
                String skierSurname = res.getString("skier_surname");
                LocalDate skierDob = res.getDate("skier_dob").toLocalDate();
                int skierPhone = Integer.parseInt(res.getString("skier_phone"));

                // Récupération des données de period
                int periodId = res.getInt("periodid");
                LocalDate startDate = res.getDate("startdate").toLocalDate();
                LocalDate endDate = res.getDate("enddate").toLocalDate();

                // Récupération des données de lesson
                int lessonId = res.getInt("lessonid");
                int minBookings = res.getInt("minbookings");
                int maxBookings = res.getInt("maxbookings");
                LocalDate lessonDate = res.getDate("lessondate").toLocalDate();

                // Récupération des données de instructor
                int instructorId = res.getInt("instructorid");
                String instructorName = res.getString("instructor_name");
                String instructorSurname = res.getString("instructor_surname");
                int instructorPhone = Integer.parseInt(res.getString("instructor_phone"));
                LocalDate instructorDob = res.getDate("instructor_dob").toLocalDate();
                
                //Getting accreditation for lesson type
                int accredid = res.getInt("accreditationid");
                String accredName = res.getString("accred_name");
                
                PreparedStatement statement = connect.prepareStatement(query);
                statement.setInt(1, instructorId);
                ResultSet accredRes = statement.executeQuery();
                
                List<Accreditation> accreditations = new ArrayList<Accreditation>();
                
                while(accredRes.next()) {
                	int accreditationid = accredRes.getInt("accreditationid");
                	String accredNameInst = accredRes.getString("name");
                	
                	Accreditation currAccreditation = new Accreditation(accreditationid, accredNameInst);
                	
                	accreditations.add(currAccreditation);
                }
                
                // Récupération des données de lessonType
                int lessonTypeId = res.getInt("ltid");
                String lessonLevel = res.getString("lessonlevel");
                double price = res.getDouble("price");
                
                Instructor currInst = new Instructor(instructorName, instructorSurname, instructorDob, instructorPhone, accreditations.get(0));
                
                for(int i = 1;i<accreditations.size();i++) {
                	currInst.addAccreditation(accreditations.get(i));
                }
                
                LessonType currLessonType = new LessonType(lessonTypeId, lessonLevel, price, new Accreditation(accredid, accredName));
                
                Lesson currLesson = new Lesson(lessonId, minBookings, maxBookings, lessonDate, currInst, currLessonType);
                
                Skier currSkier = new Skier(skierName, skierSurname, skierDob, skierPhone);
                
                Period currPeriod = new Period(periodId, startDate, endDate, true);
                
                Booking currBooking = new Booking(bookingId ,duration, isIndividual, currInst, currSkier, currLesson, currPeriod);
                
                bookings.add(currBooking);
			}
			//Getting accreditations
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
}
