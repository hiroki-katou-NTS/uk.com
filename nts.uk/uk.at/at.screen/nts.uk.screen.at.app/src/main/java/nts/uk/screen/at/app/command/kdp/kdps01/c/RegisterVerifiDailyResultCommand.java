package nts.uk.screen.at.app.command.kdp.kdps01.c;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author sonnlb
 *
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.algorithm.ParamIdentityConfirmDay;

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

	public ParamIdentityConfirmDay toParam() {

		return new ParamIdentityConfirmDay(employeeId,
				confirmDetails.stream().map(x -> x.toParam()).collect(Collectors.toList()));
	}
}
