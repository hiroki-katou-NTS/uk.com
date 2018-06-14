package nts.uk.ctx.at.record.dom.calculationattribute.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
/** 乖離時間Attr */
public enum DivergenceTimeAttr {
	
	// 0: 使用しない
	NOT_USE(0),
	// 1: 使用する
	USE(1);

	public final int value;
	
	public boolean isUse() {
		return USE.equals(this);
	}
}
