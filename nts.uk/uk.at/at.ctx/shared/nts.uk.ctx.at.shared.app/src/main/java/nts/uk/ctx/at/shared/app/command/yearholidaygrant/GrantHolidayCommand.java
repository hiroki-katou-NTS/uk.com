package nts.uk.ctx.at.shared.app.command.yearholidaygrant;

import lombok.Value;

/**
 * 
 * @author TanLV
 *
 */
@Value
public class GrantHolidayCommand {	
	/* 年休付与テーブル設定コード */
	private String yearHolidayCode;
	
	/* 付与回数 */
	private int grantNum;
	
	/* 一斉付与する */
	private int allowStatus;
	
	/* 付与基準日 */
	private int standGrantDay;

	/* 年数 */
	private Integer year;
	
	/* 月数 */
	private Integer month;
	
	/* 条件NO */
	private int conditionNo;
	
	/* 年休付与日数 */
	private Double grantDays;
	
	/* 時間年休上限日数 */
	private Integer limitTimeHd;

	/* 半日年休上限回数 */
	private Integer limitDayYear;
}
