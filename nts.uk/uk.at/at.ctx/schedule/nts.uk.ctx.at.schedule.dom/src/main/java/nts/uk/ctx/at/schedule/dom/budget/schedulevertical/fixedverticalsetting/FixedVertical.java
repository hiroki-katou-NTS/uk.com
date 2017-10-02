package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

@AllArgsConstructor
@Getter
public class FixedVertical extends AggregateRoot {

	/* 会社ID */
	private String companyId;
	
	/**/
	private int fixedVerticalNo;
	
	/*利用区分*/
	private UseAtr useAtr;
	
	/*固定項目区分*/
	private FixedItemAtr fixedItemAtr;
	
	/*縦計詳細設定*/
	private VerticalDetailedSettings verticalDetailedSettings;
	
	/**
	 * Create from Java Type of Fixed Vertical
	 * @param companyId
	 * @param fixedVerticalNo
	 * @param useAtr
	 * @param fixedItemAtr
	 * @param verticalDetailedSettings
	 * @return
	 */
	public static FixedVertical createFromJavaType(String companyId,int fixedVerticalNo, int useAtr, int fixedItemAtr, int verticalDetailedSettings){
		return new FixedVertical(companyId, fixedVerticalNo,
				EnumAdaptor.valueOf(useAtr, UseAtr.class),
				EnumAdaptor.valueOf(fixedItemAtr, FixedItemAtr.class),
				EnumAdaptor.valueOf(verticalDetailedSettings, VerticalDetailedSettings.class));
	}
}
