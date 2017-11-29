package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaTimeUnit;

/**
 * TanLV
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FormulaTimeUnitDto {
	/** 会社ID */
	private String companyId;

	/** コード */
	private String verticalCalCd;

	/** 汎用縦計項目ID */
	private String verticalCalItemId;

	/** 単位 */
	private int roundingTime;

	/** 端数処理 */
	private int roundingAtr;

	/** 単価 */
	private int unitPrice;

	/** 単価 */
	private int actualDisplayAtr;

	private List<TimeUnitFuncDto> lstTimeUnitFuncs;

	/**
	 * fromDomain
	 * 
	 * @param unit
	 * @return
	 */
	public static FormulaTimeUnitDto fromDomain(FormulaTimeUnit unit) {
		if (unit == null) {
			return null;
		}
		List<TimeUnitFuncDto> items = unit.getLstTimeUnitFuncs().stream().map(x -> TimeUnitFuncDto.fromDomain(x))
				.collect(Collectors.toList());
		return new FormulaTimeUnitDto(unit.getCompanyId(), unit.getVerticalCalCd(), unit.getVerticalCalItemId(),
				unit.getRoundingTime().value, unit.getRoundingAtr().value, unit.getUnitPrice().value,
				unit.getActualDisplayAtr().value, items);
	}
}
