package nts.uk.ctx.sys.assist.pub.command.mastercopy;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author HoangNM
 *
 */
@Getter
@Setter
public class MasterCopyTaskStatusPubDto {
	/** The Constant DB_PREFIX. */
	public static final String DB_PREFIX = "TASK_METHOD_";

	/** The Constant NUMBER_OF_SUCCESS. */
	public static final String NUMBER_OF_SUCCESS = "NUMBER_OF_SUCCESS";

	/** The Constant NUMBER_OF_ERROR. */
	public static final String NUMBER_OF_ERROR = "NUMBER_OF_ERROR";
	
	public static final int STATUS_UNKNOWN = 0;
	public static final int STATUS_PROCESSING = 1;
	public static final int STATUS_FINISHED = 2;
	public static final int STATUS_ABORTED = 3;
	
	public static final String STATUS_NAME_UNKNOWN = "UNKNOWN";
	public static final String STATUS_NAME_PROCESSING = "PROCESSING";
	public static final String STATUS_NAME_FINISHED = "FINISHED";
	public static final String STATUS_NAME_ABORTED = "ABORTED";


	/**  The constant CATEGORY_NAME. */
	private static final String STATUS = "status";
	
	/**  The constant CATEGORY_NAME. */
	private static final String PROGRESS = "progress";

	/**  The constant MESSAGE. */
	private static final String MESSAGE = "message";
	
	private Integer status = STATUS_UNKNOWN;
	private Integer progress = 0;
	private String message = "";
	
	/**
	 * Create a task status json object to transfer to client side.
	 * @return task status json object
	 */
	public JsonObject buildJsonObject() {
		JsonObjectBuilder tmp = Json.createObjectBuilder();
		tmp.add(MESSAGE, message);
		tmp.add(PROGRESS, progress);
		tmp.add(STATUS, getStatusName());
		
		return tmp.build();
	}
	
	private String getStatusName() {
		switch (status) {
			case STATUS_PROCESSING:
				return STATUS_NAME_PROCESSING;
			case STATUS_FINISHED:
				return STATUS_NAME_FINISHED;
			case STATUS_ABORTED:
				return STATUS_NAME_ABORTED;
		}
		return STATUS_NAME_UNKNOWN;
	}
	
	/**
	 * Create a json string from status dto json object
	 * @return string of status dto json object
	 */
	@Override
	public String toString() {
		return buildJsonObject().toString();
	}
	
	/**
	 * Convert name to status number
	 * 
	 * @param name
	 * @return
	 */
	private static int getStatusFromName(String name) {
		switch (name) {
			case STATUS_NAME_PROCESSING:
				return STATUS_PROCESSING;
			case STATUS_NAME_FINISHED:
				return STATUS_FINISHED;
			case STATUS_NAME_ABORTED:
				return STATUS_ABORTED;
		}
		return STATUS_UNKNOWN;
	}
	
	/**
	 * Deserialize a json string to dto
	 * (no safety check yet)
	 * 
	 * @param jsonString
	 * @return
	 */
	public static MasterCopyTaskStatusPubDto FromJson(String jsonString) {
		MasterCopyTaskStatusPubDto dto = new MasterCopyTaskStatusPubDto();
		JsonReader jr = Json.createReader(new StringReader(jsonString));
		JsonObject jo = jr.readObject();
		jr.close();
		
		dto.message = jo.getString(MESSAGE, "");
		dto.progress = jo.getInt(PROGRESS);
		dto.status = getStatusFromName(jo.getString(STATUS, STATUS_NAME_UNKNOWN));
		
		return dto;
	}
}
