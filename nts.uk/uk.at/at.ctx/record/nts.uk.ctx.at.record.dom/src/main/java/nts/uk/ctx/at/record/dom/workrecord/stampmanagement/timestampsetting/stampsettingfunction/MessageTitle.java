package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.stampsettingfunction;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.打刻管理.打刻設定.打刻入力の機能設定.お知らせメッセージ設定.メッセージタイトル
 * @author tutt
 *
 */
@StringMaxLength(10)
public class MessageTitle  extends StringPrimitiveValue<MessageTitle> {

	private static final long serialVersionUID = 1L;

	/**
	 * @param rawValue
	 */
	public MessageTitle(String rawValue) {
		super(rawValue);
	}
}
