package nts.uk.screen.at.app.query.kdl.kdl014.a.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDateTime;
import nts.gul.location.GeoCoordinate;

@Data
@AllArgsConstructor
public class EmpInfomationDto {
	
	private String code;
	
	private String name;
	
	private GeneralDateTime stampDateTime;
	
	private Integer stampMeans;
	
	private String stampAtr;
	
	private String workLocationName;
	
	private GeoCoordinate locationInfo;

	
}
