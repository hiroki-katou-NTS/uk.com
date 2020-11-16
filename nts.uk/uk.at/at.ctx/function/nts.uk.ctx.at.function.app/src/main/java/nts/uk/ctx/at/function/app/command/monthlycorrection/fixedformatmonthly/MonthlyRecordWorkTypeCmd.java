package nts.uk.ctx.at.function.app.command.monthlycorrection.fixedformatmonthly;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonthlyRecordWorkType;
import nts.uk.ctx.at.shared.dom.workrule.businesstype.BusinessTypeCode;

@Getter
@Setter
@NoArgsConstructor
public class MonthlyRecordWorkTypeCmd {
	/**会社ID*/
	private String companyID;
	/**コード*/
	private String businessTypeCode;
	/**表示項目*/
	private MonthlyActualResultsCmd displayItem;
	public MonthlyRecordWorkTypeCmd(String companyID, String businessTypeCode, MonthlyActualResultsCmd displayItem) {
		super();
		this.companyID = companyID;
		this.businessTypeCode = businessTypeCode;
		this.displayItem = displayItem;
	}
	
	public static MonthlyRecordWorkType fromCommand(MonthlyRecordWorkTypeCmd command) {
		return new MonthlyRecordWorkType(
				command.getCompanyID(),
				new BusinessTypeCode( command.getBusinessTypeCode()),
				MonthlyActualResultsCmd.fromCommand( command.getDisplayItem())
				);
	}
}
