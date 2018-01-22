package nts.uk.ctx.at.request.dom.setting.company.request.stamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;
/**
 * 外出種類の表示制御
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class GoOutTypeDisplayControl {
	/**
	 * 私用
	 */
	private DisplayAtr stampGoOutAtrPrivateDisp;
	
	/**
	 * 公用
	 */
	private DisplayAtr stampGoOutAtrPublicDisp;
	
	/**
	 * 有償
	 */
	private DisplayAtr stampGoOutAtrCompensationDisp;
	
	/**
	 * 組合
	 */
	private DisplayAtr stampGoOutAtrUnionDisp;
}
