package de.schindy.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import de.schindy.dao.UserDAO;
import de.schindy.dao.mapper.UserMapper;
import de.schindy.model.User;

public class JdbcUserDAO implements UserDAO {

	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void insert(User user) {
		String sql = "INSERT INTO user "
				+ "(ID, LOGIN_NAME, LOGIN_PASSWORD, FIRST_NAME, LAST_NAME, EMAIL) VALUES (?, ?, ?, ?, ?, ?)";

		jdbcTemplate.update(
				sql,
				new Object[] { user.getId(), user.getLoginname(),
						user.getPassword(), user.getFirstname(),
						user.getLastname(), user.getEmail() });
	}

	public User findByCustomerId(int id) {

		String sql = "SELECT * FROM user WHERE ID = ?";

		//ohne schablone
		
		// Connection conn = null;
		//
		// try {
		// conn = jdbc.getConnection();
		// PreparedStatement ps = conn.prepareStatement(sql);
		// ps.setInt(1, id);
		// User user = null;
		// ResultSet rs = ps.executeQuery();
		// if (rs.next()) {
		// user = new User();
		// user.setId(rs.getInt("ID"));
		// user.setLoginname(rs.getString("LOGIN_NAME"));
		// user.setLoginpassword(rs.getString("LOGIN_PASSWORD"));
		// user.setFirstname(rs.getString("FIRST_NAME"));
		// user.setLastname(rs.getString("LAST_NAME"));
		// user.setEmail(rs.getString("EMAIL"));
		// }
		// rs.close();
		// ps.close();
		// return user;
		// } catch (SQLException e) {
		// throw new RuntimeException(e);
		// } finally {
		// if (conn != null) {
		// try {
		// conn.close();
		// } catch (SQLException e) {}
		// }
		// }
		// }
		
		
		// make sure both the property and column has the same name, e.g property ���custId��� will match to column name ���CUSTID��� or with underscores ���CUST_ID���.
		
		// User user = (User) jdbcTemplate.queryForObject(sql, new Object[] { id
		// }, new BeanPropertyRowMapper<User>(User.class));
		User user = (User) jdbcTemplate.queryForObject(sql,
				new Object[] { id }, new UserMapper());
		return user;
	}

	public List<User> getAll() {
		String sql = "SELECT * FROM user";
		List<User> users = new ArrayList<User>();
		List<Map<String, Object>> rows= jdbcTemplate.queryForList(sql);
		for (Map<String, Object> row : rows) {
			User user = new User();
			user.setId((Long) row.get("id"));
			user.setLoginname((String) row.get("login_name"));
			users.add(user);
		}
		return users; 
	}

	public User findUserByLoginName(String loginname) {
		String sql = "SELECT * FROM user WHERE LOGIN_NAME = ?";
		User user = (User) jdbcTemplate.queryForObject(sql,
				new Object[] { loginname }, new UserMapper());
		return user;
	}

}