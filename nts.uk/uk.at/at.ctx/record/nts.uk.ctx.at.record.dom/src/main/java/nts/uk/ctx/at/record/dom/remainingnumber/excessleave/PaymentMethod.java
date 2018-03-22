package nts.uk.ctx.at.record.dom.remainingnumber.excessleave;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PaymentMethod {
	
	// 休暇発生
	VACATION_OCCURRED(0),
	
	// 金額精算
	AMOUNT_PAYMENT(1);
	
	public final int value;
}
