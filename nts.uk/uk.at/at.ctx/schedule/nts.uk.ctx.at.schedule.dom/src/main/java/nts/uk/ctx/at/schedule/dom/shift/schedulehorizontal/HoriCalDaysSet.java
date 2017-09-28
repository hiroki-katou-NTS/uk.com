package nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal;

import nts.arc.enums.EnumAdaptor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.primitives.CategoryCode;
/**
 * 
 * @author yennth                                                       
 *
 */
@AllArgsConstructor
@Getter
public class HoriCalDaysSet extends DomainObject{
	/**会社ID**/
	private String companyId;
	/** カテゴリコード */
	private CategoryCode categoryCode;
	/** 半日カウント区分 */
	private HalfDay halfDay;
	/** 年休カウント区分*/
	private YearHd yearHd;
	/** 特休カウント区分 */
	private SpecialHoliday specialHoliday;
	/** 積休カウント区分 */
	private HeavyHd heavyHd;
	
	public static HoriCalDaysSet createFromJavaType(String companyId, 
													String categoryCode, Integer halfDay, 
													Integer yearHd, Integer specialHoliday, Integer heavyHd){
		return new HoriCalDaysSet(companyId, 
									new CategoryCode(categoryCode), 
									EnumAdaptor.valueOf(halfDay, HalfDay.class), 
									EnumAdaptor.valueOf(yearHd, YearHd.class), 
									EnumAdaptor.valueOf(specialHoliday, SpecialHoliday.class), 
									EnumAdaptor.valueOf(heavyHd, HeavyHd.class));
	}
}
