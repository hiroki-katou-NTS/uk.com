package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.stampsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.setting.DisplayAtr;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.打刻申請設定.外出種類の表示制御
 * @author Doan Duy Hung
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GoOutTypeDispControl {
	
	/**
	 * 表示する
	 */
	private DisplayAtr display;
	
	/**
	 * 外出種類
	 */
	private GoOutType goOutType;
	
}
