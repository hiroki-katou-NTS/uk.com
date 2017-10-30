package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.UseAtr;
/**
 * 固定縦計設定
 * @author phongtq
 *
 */
@AllArgsConstructor
@Getter
public class FixedVertical extends AggregateRoot {

	/** 会社ID **/
	private String companyId;
	
	/**固定項目区分**/
	private FixedItemAtr fixedItemAtr;
	
	/**利用区分**/
	private UseAtr useAtr;
	
	
	/**
	 * Create from Java Type of Fixed Vertical
	 * @param companyId
	 * @param fixedItemAtr
	 * @param useAtr
	 * @param fixedItemAtr
	 * @param verticalDetailedSettings
	 * @return
	 */
	public static FixedVertical createFromJavaType(String companyId,int fixedItemAtr, int useAtr){
		return new FixedVertical(companyId, 
				EnumAdaptor.valueOf(fixedItemAtr, FixedItemAtr.class),
				EnumAdaptor.valueOf(useAtr, UseAtr.class));
	}
}
