package nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.request.dom.application.DisabledSegment_New;
import nts.uk.ctx.at.request.dom.application.UseAtr;
/**
 * 申請表示設定
 * @author Doan Duy Hung
 *
 */
@Getter
public class AppDisplaySetting extends DomainObject {
	
	/**
	 * 事前事後区分表示
	 */
	private DisplayAtr prePostAtr;
	
	/**
	 * 就業時間帯の検索
	 */
	private UseAtr searchWorkingHours;
	
	/**
	 * 登録時の手動メール送信の初期値
	 */
	private DisabledSegment_New manualSendMailAtr;
	
}
