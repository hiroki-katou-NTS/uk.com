package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampinputfunctionsettings.notificationmessagesettings;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @name メッセージタイトル
 * @part UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.打刻管理.打刻設定.打刻入力の機能設定.お知らせメッセージ設定.メッセージタイトル
 * @author ThanhPV
 */

@StringMaxLength(10)
public class MessageTitle extends StringPrimitiveValue<MessageTitle>{
	
	private static final long serialVersionUID = 1L;

	/**
	 * @param rawValue
	 */
	public MessageTitle(String rawValue) {
		super(rawValue);
	}
}