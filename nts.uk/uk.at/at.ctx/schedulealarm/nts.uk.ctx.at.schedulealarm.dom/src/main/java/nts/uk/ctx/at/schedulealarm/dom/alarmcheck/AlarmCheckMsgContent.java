package nts.uk.ctx.at.schedulealarm.dom.alarmcheck;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
/**
 * アラームチェックのメッセージ内容
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定のアラームチェック.アラームチェック条件.アラームチェックのメッセージ内容
 * @author lan_lt
 *
 */
@Value
public class AlarmCheckMsgContent implements DomainValue{
	/** 既定のメッセージ  */
	private final AlarmCheckMessage defaultMsg;
	
	/** 任意のメッセージ  */
	private final AlarmCheckMessage message;

}
