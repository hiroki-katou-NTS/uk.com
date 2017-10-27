package nts.uk.ctx.at.shared.app.find.specialholiday.yearserviceper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearServicePerSetDto {
	/**コード**/
	private String specialHolidayCode;
	private String yearServiceCode;
	private int yearServiceNo;
	/** 年数 **/
	private Integer year;
	/** 月数 **/
	private Integer month;
	/** 特別休暇付与日数 **/
	private Integer date;
}
