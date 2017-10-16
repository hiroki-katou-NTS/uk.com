package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 縦計時間帯設定
 * @author phongtq
 *
 */
@AllArgsConstructor
@Getter 
public class VerticalTime extends AggregateRoot {

	/**会社ID**/
	private String companyId;
	
	/**固定縦計設定NO**/
	private FixedItemAtr fixedItemAtr;
	
	private int verticalTimeNo;
	
	/**表示区分**/
	private DisplayAtr displayAtr;
	
	/**時刻**/
	private StartClock startClock;

	public static VerticalTime createFromJavaType(String companyId, int fixedItemAtr, int verticalTimeNo, int displayAtr, int startClock){
		return new VerticalTime(companyId, EnumAdaptor.valueOf(fixedItemAtr, FixedItemAtr.class),verticalTimeNo,
				EnumAdaptor.valueOf(displayAtr, DisplayAtr.class),new StartClock (startClock));
	}
}
