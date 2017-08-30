package nts.uk.ctx.at.shared.app.command.specialholiday.yearserviceset;

import lombok.Getter;
import lombok.Setter;
/**
 * update year month date
 * @author yennth
 *
 */
@Getter
@Setter
public class UpdateYearServiceSetCommand {
	/**コード**/
	private int specialHolidayCode;
	/** 年間サービスタイプ **/
	private int yearServiceType;
	/** 月数 **/
	private int year;
	/** 年数 **/
	private int month;
	/** 特別休暇付与日数 **/
	private int date;
}
