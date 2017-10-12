package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
/**
 * 縦計時間帯設定
 * @author phongtq
 *
 */
@AllArgsConstructor
@Getter 
public class VerticalTime {

	/**会社ID**/
	private String companyId;
	
	/**固定縦計設定NO**/
	private int fixedVerticalNo;
	
	/**表示区分**/
	private DisplayAtr displayAtr;
	
	/**時刻**/
	private StartClock startClock;

	public static VerticalTime createFromJavaType(String companyId, int fixedVerticalNo, int displayAtr, int startClock){
		return new VerticalTime(companyId, fixedVerticalNo,
				EnumAdaptor.valueOf(displayAtr, DisplayAtr.class),new StartClock (startClock));
	}
}
