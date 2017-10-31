package nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.primitives.CategoryCode;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.primitives.TotalItemNo;
/**
 * 月間勤務日数集計設定
 * @author yennth                                                       
 *
 */
@Getter
@AllArgsConstructor
public class HoriCalDaysSet extends DomainObject{
	/**会社ID**/
	private String companyId;
	/** カテゴリコード */
	private CategoryCode categoryCode;
	/** 集計項目NO */
	private TotalItemNo totalItemNo;
	/** 半日カウント区分 */
	private HalfDay halfDay;
	/** 年休カウント区分*/
	private YearHd yearHd;
	/** 特休カウント区分 */
	private SpecialHoliday specialHoliday;
	/** 積休カウント区分 */
	private HeavyHd heavyHd;
	
	public static HoriCalDaysSet createFromJavaType(String companyId, 
													String categoryCode, int totalItemNo, int halfDay, 
													int yearHd, int specialHoliday, int heavyHd){
		return new HoriCalDaysSet(companyId, 
									new CategoryCode(categoryCode), 
									new TotalItemNo(totalItemNo),
									EnumAdaptor.valueOf(halfDay, HalfDay.class), 
									EnumAdaptor.valueOf(yearHd, YearHd.class), 
									EnumAdaptor.valueOf(specialHoliday, SpecialHoliday.class), 
									EnumAdaptor.valueOf(heavyHd, HeavyHd.class));
	}
}
