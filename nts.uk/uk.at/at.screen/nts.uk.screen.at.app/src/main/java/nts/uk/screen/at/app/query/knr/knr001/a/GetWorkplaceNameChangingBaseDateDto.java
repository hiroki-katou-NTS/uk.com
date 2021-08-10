package nts.uk.screen.at.app.query.knr.knr001.a;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GetWorkplaceNameChangingBaseDateDto {

	private String workplaceId;
	
	private String hierarchyCode;

	private String workplaceCode;
	
	private String workplaceName;
	
	private String displayName;
	
	private String genericName;
	
	private String externalCode;
}
