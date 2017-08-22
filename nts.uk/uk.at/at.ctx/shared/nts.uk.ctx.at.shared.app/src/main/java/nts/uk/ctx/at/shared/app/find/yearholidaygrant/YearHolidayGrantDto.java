package nts.uk.ctx.at.shared.app.find.yearholidaygrant;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantCondition;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.shr.com.primitive.Memo;

@Value
public class YearHolidayGrantDto {
	/* 会社ID */
	private String companyId;

	/* コード */
	private String yearHolidayCode;

	/* 名称 */
	private String yearHolidayName;

	/* 計算方法 */
	private int calculationMethod;

	/* 計算基準 */
	private int standardCalculation;

	/* 一斉付与を利用する */
	private int useSimultaneousGrant;

	/* 一斉付与日 */
	private int simultaneousGrandMonthDays;

	/* 備考 */
	private Memo yearHolidayNote;
	
	private List<GrantCondition> grantConditions;
	
	public static YearHolidayGrantDto fromDomain(GrantHdTblSet domain){
		return new YearHolidayGrantDto(
			domain.getCompanyId(),
			domain.getYearHolidayCode().v(),
			domain.getYearHolidayName().v(),
			domain.getCalculationMethod().value,
			domain.getStandardCalculation().value,
			domain.getUseSimultaneousGrant().value,
			domain.getSimultaneousGrandMonthDays(),
			domain.getYearHolidayNote(),
			domain.getGrantConditions()
		);
	}
}
