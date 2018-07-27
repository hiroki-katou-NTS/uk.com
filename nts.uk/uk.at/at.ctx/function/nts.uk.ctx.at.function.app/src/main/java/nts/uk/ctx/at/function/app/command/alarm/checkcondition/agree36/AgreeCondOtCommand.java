package nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionCode;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeCondOt;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.MessageDisp;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.Number;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.OverTime;
import nts.uk.shr.com.context.AppContexts;

/**
 * insert/update AgreeCondOtCommand
 * @author yennth
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class AgreeCondOtCommand {
	/** ID */
	private String id;
	private String companyId;
	private int category;
	/** no */
	private int no;
	private String code;
	/** 36超過時間 */
	private int ot36;
	/** 36超過回数 */
	private int excessNum;
	/** 表示するメッセージ */
	private String messageDisp;
	
	public static AgreeCondOt toDomain(AgreeCondOtCommand command){
		return new AgreeCondOt(command.getId(), 
				AppContexts.user().companyId(), 
				EnumAdaptor.valueOf(command.getCategory(), AlarmCategory.class), 
				new AlarmCheckConditionCode(command.getCode()), 
				command.getNo(), 
				new OverTime(command.getOt36()), 
				new Number(command.getExcessNum()), 
				command.getMessageDisp() == null ? null : new MessageDisp(command.getMessageDisp()));
	}
}
