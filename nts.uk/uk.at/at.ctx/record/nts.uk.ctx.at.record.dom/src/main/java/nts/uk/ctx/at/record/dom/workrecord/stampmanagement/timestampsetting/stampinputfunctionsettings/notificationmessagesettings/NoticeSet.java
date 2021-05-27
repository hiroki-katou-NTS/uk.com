/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampinputfunctionsettings.notificationmessagesettings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.record.dom.daily.dailyperformance.classification.DoWork;

/**
 * AR: お知らせメッセージ設定
 * path : UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.打刻管理.打刻設定.打刻入力の機能設定.お知らせメッセージ設定.お知らせメッセージ設定
 * @author ThanhPV
 */
@AllArgsConstructor
@Getter
public class NoticeSet implements DomainAggregate{
	
	// 会社メッセージ色
	private ColorSetting companyColor;
	
	// 会社宛タイトル
	private MessageTitle companyTitle;
	
	// 個人メッセージ色
	private ColorSetting personalColor;
	
	// 職場メッセージ色
	private ColorSetting workplaceColor;
	
	// 職場宛タイトル
	private MessageTitle workplaceTitle;
	
	// 表示区分
	private DoWork displayAtr;
	
}
