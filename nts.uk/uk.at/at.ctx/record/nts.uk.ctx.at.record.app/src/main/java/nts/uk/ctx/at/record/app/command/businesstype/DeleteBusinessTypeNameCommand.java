package nts.uk.ctx.at.record.app.command.businesstype;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteBusinessTypeNameCommand {
	/*会社ID*/
	private String companyId;
	/*勤務種別コード*/
	private String businessTypeCode;
	/*勤務種別名*/
	private String businessTypeName;
}
