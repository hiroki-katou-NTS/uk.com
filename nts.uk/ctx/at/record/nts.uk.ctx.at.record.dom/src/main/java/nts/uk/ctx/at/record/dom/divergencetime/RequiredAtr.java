package nts.uk.ctx.at.record.dom.divergencetime;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RequiredAtr {
	/* 使用しない*/
	RequiredAtr_Optional(0),
	/* 使用する*/
	RequiredAtr_Required(1);
	
	public final int value;
}
