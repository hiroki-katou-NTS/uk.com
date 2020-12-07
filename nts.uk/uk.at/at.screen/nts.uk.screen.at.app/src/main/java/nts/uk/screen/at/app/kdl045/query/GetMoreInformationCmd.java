package nts.uk.screen.at.app.kdl045.query;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author tutk
 *
 */
@Data
@NoArgsConstructor
public class GetMoreInformationCmd {
	private String employeeId;
	private String workType;
	private String workTimeCode;
}
