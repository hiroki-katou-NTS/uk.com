package nts.uk.screen.at.app.query.knr.knr001.a;

import java.util.List;

import lombok.Data;

@Data
public class GetWorkplaceNameChangingBaseDateInput {

	private List<String> listWorkPlaceId;
	
	private String baseDate;
}
