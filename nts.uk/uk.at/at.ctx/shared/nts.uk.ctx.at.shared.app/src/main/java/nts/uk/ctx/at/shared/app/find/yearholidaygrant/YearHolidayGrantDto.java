package nts.uk.ctx.at.shared.app.find.yearholidaygrant;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;

/**
 * 
 * @author TanLV
 *
 */
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
	private String yearHolidayNote;
	
	private List<GrantConditionDto> grantConditions;
	
	public static YearHolidayGrantDto fromDomain(GrantHdTblSet domain, Require require){
		List<GrantConditionDto> conditions = domain.getGrantConditions().stream()
				.map(x-> GrantConditionDto.fromDomain(x,require))
				.collect(Collectors.toList());
		
		return new YearHolidayGrantDto(
			domain.getCompanyId(),
			domain.getYearHolidayCode().v(),
			domain.getYearHolidayName().v(),
			domain.getCalculationMethod().value,
			domain.getStandardCalculation().value,
			domain.getUseSimultaneousGrant().value,
			domain.getSimultaneousGrandMonthDays(),
			domain.getYearHolidayNote().v(),
			conditions
		);
	}
	
	public static interface Require extends GrantConditionDto.Require{
		
	}
}
