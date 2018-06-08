package nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 36協定エラーアラームチェック名称
 * @author yennth
 *
 */
@Getter
@AllArgsConstructor
public class AgreeNameError extends AggregateRoot {
	/** 期間 */
	private Period period;
	/** エラーアラーム */
	private ErrorAlarm errorAlarm;
	/** 名称 */
	private Name name;
	
	public static AgreeNameError createFromJavaType(int period, int errorAlarm, String name){
		return new AgreeNameError(EnumAdaptor.valueOf(period, Period.class),
				EnumAdaptor.valueOf(errorAlarm, ErrorAlarm.class),
				new Name(name));
	}
}
