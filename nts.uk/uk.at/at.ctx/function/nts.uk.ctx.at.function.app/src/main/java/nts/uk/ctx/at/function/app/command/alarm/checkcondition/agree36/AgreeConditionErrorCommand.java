package nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionCode;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeConditionError;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.ErrorAlarm;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.MessageDisp;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.Period;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.UseClassification;
import nts.uk.shr.com.context.AppContexts;
/**
 * insert/update AgreeConditionError Command
 * @author yennth
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class AgreeConditionErrorCommand {
	/** ID */
	private String id;
	private String code;
	private String companyId;
	private int category;
	/** 使用区分 */
	private int useAtr;
	/** 期間 */
	private int period;
	/** エラーアラーム */
	private int errorAlarm;
	/** 表示するメッセージ */
	private String messageDisp;
	
	public static AgreeConditionError toDomain(AgreeConditionErrorCommand command){
		return new AgreeConditionError(command.getId(), 
				AppContexts.user().companyId(), 
				EnumAdaptor.valueOf(command.getCategory(), AlarmCategory.class), 
				new AlarmCheckConditionCode(command.getCode()), 
				EnumAdaptor.valueOf(command.getUseAtr(), UseClassification.class),
				EnumAdaptor.valueOf(command.getPeriod(), Period.class), 
				EnumAdaptor.valueOf(command.getErrorAlarm(), ErrorAlarm.class), 
				command.getMessageDisp() == null ? null : new MessageDisp(command.getMessageDisp()));
	}
}
