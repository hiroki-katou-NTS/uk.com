package nts.uk.ctx.at.request.dom.setting.workplace.appuseset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.setting.workplace.AppUseSetRemark;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.職場別設定.申請利用設定.申請利用設定
 * @author Doan Duy Hung
 *
 */
@Getter
@AllArgsConstructor
public class ApplicationUseSetting {
	
	/**
	 * 備考
	 */
	private AppUseSetRemark memo;
	
	/**
	 * 利用区分
	 */
	private UseAtr userAtr;
	
	/**
	 * 申請種類
	 */
	private ApplicationType appType;
}
