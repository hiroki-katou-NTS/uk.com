package nts.uk.ctx.at.schedule.app.find.shift.schedulehorizontal;

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
public class HoriCalDaySetDto {
	/**会社ID**/
	private String companyId;
	/** カテゴリコード */
	private String categoryCode;
	/** 集計項目NO */
	private int totalItemNo; 
	/** 半日カウント区分 */
	private int halfDay;
	/** 年休カウント区分*/
	private int yearHd;
	/** 特休カウント区分 */
	private int specialHoliday;
	/** 積休カウント区分 */
	private int heavyHd;
}
