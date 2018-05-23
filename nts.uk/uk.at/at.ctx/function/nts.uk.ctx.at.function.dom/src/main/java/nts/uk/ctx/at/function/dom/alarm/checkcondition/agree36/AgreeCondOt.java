package nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionCode;
/**
 * 36協定時間超過回数のチェック条件, 36協定時間超過回数抽出条件
 * @author yennth
 *
 */
@Getter
@AllArgsConstructor
public class AgreeCondOt {
	/** ID */
	@Setter
	private String id;
	private String companyId;
	private AlarmCategory category;
	private AlarmCheckConditionCode code;
	/** no */
	private int no;
	/** 36超過時間 */
	private OverTime ot36;
	/** 36超過回数 */
	private Number excessNum;
	/** 表示するメッセージ */
	private MessageDisp messageDisp;
	
	public static AgreeCondOt createFromJavaType(String id, String companyId, int category, String code, int no, 
													int ot36, int excessNum, String messageDisp){
		return new AgreeCondOt(id, companyId,
				EnumAdaptor.valueOf(category, AlarmCategory.class),
				new AlarmCheckConditionCode(code), no, new OverTime(ot36), new Number(excessNum),
				messageDisp == null ? null : new MessageDisp(messageDisp));
	}
	
	public String createId(){
		return UUID.randomUUID().toString();
	}
}
