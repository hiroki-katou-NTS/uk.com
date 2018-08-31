package nts.uk.ctx.at.shared.app.find.yearholidaygrant;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTbl;

/**
 * 
 * B - Screen
 * @author TanLV
 *
 */
@Data
@AllArgsConstructor
public class GrantHolidayTblDto {
	/* 会社ID */
	private String companyId;

	/* 条件NO */
	private int conditionNo;
	
	/* 年休付与テーブル設定コード */
	private String yearHolidayCode;
	
	/* 付与回数 */
	private int grantNum;

	/* 年休付与日数 */
	private Double grantDays;

	/* 時間年休上限日数 */
	private Integer limitTimeHd;

	/* 半日年休上限回数 */
	private Integer limitDayYear;
	
	public static GrantHolidayTblDto fromDomain(GrantHdTbl domain){
		return new GrantHolidayTblDto(domain.getCompanyId(), 
									domain.getConditionNo(), 
									domain.getYearHolidayCode().v(), 
									domain.getGrantNum().v(), 
									domain.getGrantDays().v(), 
									domain.getLimitTimeHd().get().v(), 
									domain.getLimitDayYear().get().v());
	}
}
