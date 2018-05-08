package nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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
	/** no */
	private int no;
	/** 36超過時間 */
	private OverTime ot36;
	/** 36超過回数 */
	private Number excessNum;
	/** 表示するメッセージ */
	private MessageDisp messageDisp;
	
	public static AgreeCondOt createFromJavaType(String id, int no, BigDecimal ot36, int excessNum, String messageDisp){
		return new AgreeCondOt(id, no, new OverTime(ot36), new Number(excessNum),
				messageDisp == null ? null : new MessageDisp(messageDisp));
	}
	
	public String createId(){
		return UUID.randomUUID().toString();
	}
}
