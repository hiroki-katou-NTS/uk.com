package nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 36協定時間超過回数のチェック条件, 36協定時間超過回数抽出条件
 * @author yennth
 *
 */
@Getter
@AllArgsConstructor
public class AgreeCondOt {
	/** ID */
	private String id;
	/** no */
	private int no;
	/** 36超過時間 */
	private BigDecimal ot36;
	/** 36超過回数 */
	private int excessNum;
	/** 表示するメッセージ */
	private MessageDisp messageDisp;
	
	public static AgreeCondOt createFromJavaType(int no, BigDecimal ot36, int excessNum, String messageDisp){
		return new AgreeCondOt(UUID.randomUUID().toString(), no, ot36, excessNum,
				messageDisp == null ? null : new MessageDisp(messageDisp));
	}
}
