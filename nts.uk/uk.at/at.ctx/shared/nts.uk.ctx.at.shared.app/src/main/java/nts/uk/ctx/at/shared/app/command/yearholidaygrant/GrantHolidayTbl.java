package nts.uk.ctx.at.shared.app.command.yearholidaygrant;

import java.math.BigDecimal;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTbl;

/**
 * 
 * @author yennth
 *
 */
@Value
public class GrantHolidayTbl {
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
	
	/**
	 * Convert to domain object
	 * @return
	 */
	public GrantHdTbl toDomain(String companyId) {
		return  GrantHdTbl.createFromJavaType(companyId, conditionNo, yearHolidayCode, grantNum, grantDays, limitTimeHd, limitDayYear);
	}
}
