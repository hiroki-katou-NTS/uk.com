package nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly;

import lombok.Data;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonPfmCorrectionFormat;

@Data
public class MonPfmCorrectionFormatDto {
	/** 会社ID */
	private String companyID;
	/** コード */
	private String monthlyPfmFormatCode;
	/** 名称 */
	private String monPfmCorrectionFormatName;
	/** 表示項目 */
	private MonthlyActualResultsDto displayItem;

	public MonPfmCorrectionFormatDto(String companyID, String monthlyPfmFormatCode, String monPfmCorrectionFormatName,
			MonthlyActualResultsDto displayItem) {
		super();
		this.companyID = companyID;
		this.monthlyPfmFormatCode = monthlyPfmFormatCode;
		this.monPfmCorrectionFormatName = monPfmCorrectionFormatName;
		this.displayItem = displayItem;
	}

	public static MonPfmCorrectionFormatDto fromDomain(MonPfmCorrectionFormat domain) {
		return new MonPfmCorrectionFormatDto(domain.getCompanyID(), domain.getMonthlyPfmFormatCode().v(),
				domain.getMonPfmCorrectionFormatName().v(),
				MonthlyActualResultsDto.fromDomain(domain.getDisplayItem()));
	}
}
