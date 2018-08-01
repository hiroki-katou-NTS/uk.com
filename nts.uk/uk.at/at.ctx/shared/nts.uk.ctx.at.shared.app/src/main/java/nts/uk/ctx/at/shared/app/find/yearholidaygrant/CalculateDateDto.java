package nts.uk.ctx.at.shared.app.find.yearholidaygrant;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTbl;

/**
 * 
 * B - Screen
 * @author TanLV
 *
 */
@Data
@AllArgsConstructor
public class CalculateDateDto {
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
	
	/* 一斉付与する */
	private int allowStatus;
	
	/* 付与基準日 */
	private int standGrantDay;

	/* 年数 */
	private int year;
	
	/* 月数 */
	private int month;
	
	private GeneralDate grantDate;

	public static CalculateDateDto fromDomain(GrantHdTbl domain, int allowStatus, int standGrantDay, int year, int month, GeneralDate grantDate) {
		return new CalculateDateDto(domain.getCompanyId(), 
				domain.getConditionNo(), 
				domain.getYearHolidayCode().v(), 
				domain.getGrantNum().v(), 
				domain.getGrantDays().v(), 
				domain.getLimitTimeHd().get().v(), 
				domain.getLimitDayYear().get().v(),
				allowStatus,
				standGrantDay,
				year,
				month,
				grantDate);
	}
}
