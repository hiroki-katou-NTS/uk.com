package nts.uk.file.at.app.export.statement.stamp;

import lombok.Data;

@Data
public class StampList {
	
	private String date;
	private String time;
	private String classification;
	private String mean;
	private String method;
	private String insLocation;
	private String locationInfor;
	private String supportCard;
	private String workingHour;
	private String overtimeHour;
	private String nightTime;

}
