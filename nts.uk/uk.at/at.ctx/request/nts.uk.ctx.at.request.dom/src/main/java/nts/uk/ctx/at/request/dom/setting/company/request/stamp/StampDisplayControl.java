package nts.uk.ctx.at.request.dom.setting.company.request.stamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;
/**
 * 打刻区分の表示制御
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class StampDisplayControl {
	/**
	 * 出勤／退勤
	 */
	private DisplayAtr stampAtrWorkDisp;
	
	/**
	 * 外出/戻り
	 */
	private DisplayAtr stampAtrGoOutDisp;
	
	/**
	 * 介護外出/介護戻り
	 */
	private DisplayAtr stampAtrCareDisp;
	
	/**
	 * 応援入/応援出
	 */
	private DisplayAtr stampAtrSupDisp;
	
	/**
	 * 育児外出/育児戻り
	 */
	private DisplayAtr stampAtrChildCareDisp;
}
