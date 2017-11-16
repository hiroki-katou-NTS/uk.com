package nts.uk.ctx.at.record.app.command.businesstype;

import lombok.Getter;
import lombok.Setter;
/**
 * @author yennth
 * Delete business type name command
 */
@Getter
@Setter
public class DeleteBusinessTypeNameCommand {
	/**会社ID**/
	private String companyId;
	/**コード**/
	private String businessTypeCode;
}
