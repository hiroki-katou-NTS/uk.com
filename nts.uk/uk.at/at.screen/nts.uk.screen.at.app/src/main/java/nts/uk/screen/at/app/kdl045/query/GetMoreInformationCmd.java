package nts.uk.screen.at.app.kdl045.query;

import lombok.Data;

/**
 * 
 * @author tutk
 *
 */
@Data
public class GetMoreInformationCmd {
	private String employeeId;
	private String workType;
	private String workTimeCode;
}
