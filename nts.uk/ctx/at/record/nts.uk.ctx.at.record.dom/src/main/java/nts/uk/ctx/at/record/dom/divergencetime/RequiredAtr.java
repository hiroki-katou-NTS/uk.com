package nts.uk.ctx.at.record.dom.divergencetime;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RequiredAtr {
	/* 使用しない*/
	DivergenceReasonInputRequiredAtr_Optional(0),
	/* 使用する*/
	DivergenceReasonInputRequiredAtr_Required(1);
	
	public final int value;
}
