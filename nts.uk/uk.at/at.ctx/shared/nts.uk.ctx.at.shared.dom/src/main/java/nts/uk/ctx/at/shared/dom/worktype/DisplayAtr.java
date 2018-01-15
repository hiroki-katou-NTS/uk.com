package nts.uk.ctx.at.shared.dom.worktype;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DisplayAtr {
	/* 使用しない*/
	DisplayAtr_NotDisplay(0),
	/* 使用する*/
	DisplayAtr_Display(1);
	
	public final int value;
}
