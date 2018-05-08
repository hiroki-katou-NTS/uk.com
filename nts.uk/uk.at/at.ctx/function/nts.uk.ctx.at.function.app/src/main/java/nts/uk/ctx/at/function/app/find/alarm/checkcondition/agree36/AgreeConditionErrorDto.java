package nts.uk.ctx.at.function.app.find.alarm.checkcondition.agree36;

import lombok.Value;

/**
 * 
 * @author yennth
 *
 */
@Value
public class AgreeConditionErrorDto {
	/** ID */
	String id;
	/** 使用区分 */
	int useAtr;
	/** 期間 */
	int period;
	/** エラーアラーム */
	int errorAlarm;
	/** 表示するメッセージ */
	String messageDisp;
	
	String name;
}
