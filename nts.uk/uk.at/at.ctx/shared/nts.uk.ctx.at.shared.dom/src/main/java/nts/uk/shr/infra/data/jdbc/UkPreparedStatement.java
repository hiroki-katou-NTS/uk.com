package nts.uk.shr.infra.data.jdbc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;

import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.time.GeneralDateTime;

@RequiredArgsConstructor
public class UkPreparedStatement {
	
	private final PreparedStatement statement;
	
	public void setString(int parameterIndex, Object parameterValue) throws SQLException {
		
		if (parameterValue == null) {
			statement.setNull(parameterIndex, Types.NULL);
			return;
		}
		
		val type = parameterValue.getClass();
		
		if (type.equals(int.class) || type.equals(Integer.class)) {
			statement.setInt(parameterIndex, (int) parameterValue);
		} else if (type.equals(String.class)) {
			statement.setString(parameterIndex, (String) parameterValue);
		} else if (type.equals(GeneralDateTime.class)) {
			statement.setTimestamp(parameterIndex, Timestamp.valueOf(((GeneralDateTime) parameterValue).localDateTime()));
		}else if (type.equals(Date.class)) {
			statement.setDate(parameterIndex, (Date) parameterValue);
		} else {
			statement.setString(parameterIndex, (String) parameterValue);
		}
	}
	
	public void executeUpdate() throws SQLException {
		statement.executeUpdate();
	}
}
