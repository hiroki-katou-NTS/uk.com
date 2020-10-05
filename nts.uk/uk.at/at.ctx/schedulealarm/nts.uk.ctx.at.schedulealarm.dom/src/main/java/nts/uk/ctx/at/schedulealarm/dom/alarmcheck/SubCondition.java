package nts.uk.ctx.at.schedulealarm.dom.alarmcheck;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * サブ条件
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定のアラームチェック.アラームチェック条件.サブ条件
 * @author lan_lt
 *
 */
@Value
public class SubCondition implements DomainValue{
	/** サブコード */
	private final SubCode subCode;
	
	/** メッセージ */
	private final  AlarmCheckMsgContent message;
	
	/** 説明 */
	private final String  explanation;

}
