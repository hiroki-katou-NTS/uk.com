package nts.uk.screen.at.app.command.kdp.kdps01.c;

import java.util.List;

/**
 * 
 * @author sonnlb
 *
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterVerifiDailyResultCommand {
	/**
	 * 社員ID
	 */

	private String employeeId;
	/**
	 * 本人確認内容
	 */
	
	private List<ConfirmDetailCommand> confirmDetails;
}
