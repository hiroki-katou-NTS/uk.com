package nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionCode;

/**
 * 36協定エラーアラームのチェック条件, 36協定抽出条件
 * 
 * @author yennth
 *
 */
@Getter
@AllArgsConstructor
public class AgreeConditionError extends AggregateRoot {
	/** ID */
	@Setter
	private String id;
	private String companyId;
	private AlarmCategory category;
	/** カテゴリ */
	private AlarmCheckConditionCode code;
	/** 使用区分 */
	private UseClassification useAtr;
	/** 期間 */
	private Period period;
	/** エラーアラーム */
	private ErrorAlarm errorAlarm;
	/** 表示するメッセージ */
	private MessageDisp messageDisp;

	public static AgreeConditionError createFromJavaType(String id, String companyId, int category, String code,
			int useAtr, int period, int errorAlarm, String messageDisp) {
		return new AgreeConditionError(id, companyId, EnumAdaptor.valueOf(category, AlarmCategory.class),
				new AlarmCheckConditionCode(code), EnumAdaptor.valueOf(useAtr, UseClassification.class),
				EnumAdaptor.valueOf(period, Period.class), EnumAdaptor.valueOf(errorAlarm, ErrorAlarm.class),
				messageDisp == null ? null : new MessageDisp(messageDisp));
	}

	public String createId() {
		return UUID.randomUUID().toString();
	}

}
