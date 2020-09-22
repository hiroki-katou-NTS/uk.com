package nts.uk.ctx.at.function.app.nrl;

import java.util.Arrays;
import java.util.Optional;

/**
 * Command.
 *  コマンドの種類
 * @author manhnd
 */
public enum Command {

	ACCEPT("ACK", "11", "01"),
	NOACCEPT("NAK", "12", "02"),
	POLLING("Polling", "13", "03"),
	SESSION("Session", "17", "01"),
	TEST("Test", "14", "04"),
	
	ALL_IO_TIME("IOTime", "15", "01"),
	ALL_PETITIONS("AllPetitions", "19", "01"),
	ALL_RESERVATION("AllReservation", "18", "01"),
	
	PERSONAL_INFO("PersonalInfo", "1B", "07"),
	WORKTYPE_INFO("WorkTypeInfo", "1C", "08"),
	WORKTIME_INFO("WorkTimeInfo", "1D", "09"),
	OVERTIME_INFO("OverTimeInfo", "1E", "0A"),
	RESERVATION_INFO("ReservationInfo", "1F", "0B"),
	APPLICATION_INFO("ApplicationInfo", "20", "0C"),
	TIMESET_INFO("TimeSetInfo", "16", "05"),
	
	TR_REMOTE("TimeRecordRemote", "21", "0D");

	/**
	 * Name
	 */
	public String Name;
	
	
	/**
	 *	Request 
	 */
	public String Request;
	
	
	/**
	 * Response
	 */
	public String Response;
	
	private Command(String name, String request, String response) {
		this.Name = name;
		this.Request = request;
		this.Response = response;
	}
	
	/**
	 * Find request name.
	 * @param request request
	 * @return command
	 */
	public static Optional<Command> findName(String request) {
		return Arrays.asList(values()).stream().filter(c -> request.toUpperCase().equals(c.Request)).findFirst();
	}
}
