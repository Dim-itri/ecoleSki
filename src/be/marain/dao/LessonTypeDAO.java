package be.marain.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.marain.classes.Accreditation;
import be.marain.classes.LessonType;

public class LessonTypeDAO extends DAO<LessonType> {
	public LessonTypeDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(LessonType obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(LessonType obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(LessonType obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LessonType find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LessonType> findAll() {
		List<LessonType> lessonTypes = new ArrayList<LessonType>();
		
		try {
			ResultSet resultSet = this.connect
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)
					.executeQuery("SELECT lt.ltid, lt.lessonlevel, lt.price, "
							+ "a.accreditationid, a.name "
							+ " FROM lessontype lt "
							+ "INNER JOIN accreditation a ON a.accreditationid = lt.accreditationid");
			
			while(resultSet.next()) {
				int ltid = resultSet.getInt("ltid");
				String lessonlevel = resultSet.getString("lessonlevel");
				double price = resultSet.getDouble("price");
				int accredId = resultSet.getInt("accreditationid");
				String accredName = resultSet.getString("name");
				
				LessonType newLessonType = new LessonType(ltid, lessonlevel, price, new Accreditation(accredId, accredName));
				
				lessonTypes.add(newLessonType);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lessonTypes;
	}
}
