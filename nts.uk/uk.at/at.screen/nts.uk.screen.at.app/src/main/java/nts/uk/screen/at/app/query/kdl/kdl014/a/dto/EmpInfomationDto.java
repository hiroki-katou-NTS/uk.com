package nts.uk.screen.at.app.query.kdl.kdl014.a.dto;

import lombok.Data;
import nts.arc.time.GeneralDateTime;
import nts.gul.location.GeoCoordinate;

@Data
public class EmpInfomationDto {
	
	private String employeeId;
	
	private String code;
	
	private String name;
	
	private String stampDateTime;
	
	private String time;
	
	private Integer stampMeans;
	
	private String stampAtr;
	
	private String workLocationName;
	
	private GeoCoordinate locationInfo;

	public EmpInfomationDto(String employeeId, String code, String name, GeneralDateTime stampDateTime, Integer stampMeans,
			String stampAtr, String workLocationName, GeoCoordinate locationInfo) {
		super();
		this.employeeId = employeeId;
		this.code = code;
		this.name = name;
		this.stampDateTime = stampDateTime.toString("yyyy/MM/dd");
		this.time = stampDateTime.toString("HH:mm");
		this.stampMeans = stampMeans;
		this.stampAtr = stampAtr;
		this.workLocationName = workLocationName;
		this.locationInfo = locationInfo;
	}

	
}
