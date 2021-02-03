package nts.uk.ctx.at.schedule.dom.displaysetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 会社別スケジュール修正日付別の表示設定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.表示設定.会社別スケジュール修正日付別の表示設定
 * @author hiroko_miura
 *
 */
@Getter
@AllArgsConstructor
public class DisplaySettingByDateForCompany implements DomainAggregate{
	
	/** 表示設定 */
	private DisplaySettingByDate dispSetting;
	
}