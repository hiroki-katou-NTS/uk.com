package nts.uk.screen.at.app.query.knr.knr002.e;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LocationSettingDto {

	private Integer majorNo;
	
	private String majorClassification;
	
	private Integer smallNo;
	
	private String smallClassification;
	
	private String settingValue;
	
	private String inputRange;
}
