package nts.uk.ctx.at.schedule.app.command.shift.schedulehorizontal;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.HoriCalDaysSet;

/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
public class HoriCalDaysSetCommand {
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
	
	/**
	 * convert hori cal day set to domain
	 * @param companyId
	 * @param categoryCode
	 * @return
	 */
	public HoriCalDaysSet toDomainCalSet(String companyId, String categoryCode, int totalItemNo){
		return HoriCalDaysSet.createFromJavaType(companyId, categoryCode, totalItemNo, halfDay, yearHd, specialHoliday, heavyHd);
	}
}
