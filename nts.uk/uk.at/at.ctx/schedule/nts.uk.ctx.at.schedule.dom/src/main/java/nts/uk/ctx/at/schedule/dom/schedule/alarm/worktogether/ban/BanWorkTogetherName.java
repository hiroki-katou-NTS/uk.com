package nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 同時出勤禁止名称
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.同時出勤.同時出勤禁止.同時出勤禁止名称
 * @author lan_lt
 *
 */
@StringMaxLength(20)
public class BanWorkTogetherName extends StringPrimitiveValue<BanWorkTogetherName>{
	/**	serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public BanWorkTogetherName(String rawValue) {
		super(rawValue);
	}


}
