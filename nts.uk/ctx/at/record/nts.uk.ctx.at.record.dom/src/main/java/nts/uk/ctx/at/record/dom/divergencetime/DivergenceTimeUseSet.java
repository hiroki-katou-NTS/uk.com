package nts.uk.ctx.at.record.dom.divergencetime;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DivergenceTimeUseSet {
	/* 使用しない*/
	UseAtr_NotUse(0),
	/* 使用する*/
	UseAtr_Use(1);
	
	public final int value;
}
