package nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonthlyRecordWorkType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.primitivevalue.BusinessTypeCode;

@Getter
@Setter
@NoArgsConstructor
public class MonthlyRecordWorkTypeDto {
	/**会社ID*/
	private String companyID;
	/**コード*/
	private String businessTypeCode;
	/**表示項目*/
	private MonthlyActualResultsDto displayItem;
	public MonthlyRecordWorkTypeDto(String companyID, String businessTypeCode, MonthlyActualResultsDto displayItem) {
		super();
		this.companyID = companyID;
		this.businessTypeCode = businessTypeCode;
		this.displayItem = displayItem;
	}
	
	public static MonthlyRecordWorkTypeDto fromDomain(MonthlyRecordWorkType domain) {
		return new MonthlyRecordWorkTypeDto(
				domain.getCompanyID(),
				domain.getBusinessTypeCode().v(),
				MonthlyActualResultsDto.fromDomain(domain.getDisplayItem())
				);
	}
}
