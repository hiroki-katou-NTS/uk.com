package nts.uk.ctx.at.schedulealarm.dom.alarmcheck;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * アラームチェックのメッセージ
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定のアラームチェック.アラームチェック条件.アラームチェックのメッセージ
 * @author lan_lt
 *
 */
@StringMaxLength(100)
public class AlarmCheckMessage extends StringPrimitiveValue<AlarmCheckMessage>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public AlarmCheckMessage(String rawValue) {
		super(rawValue);
	}

}
