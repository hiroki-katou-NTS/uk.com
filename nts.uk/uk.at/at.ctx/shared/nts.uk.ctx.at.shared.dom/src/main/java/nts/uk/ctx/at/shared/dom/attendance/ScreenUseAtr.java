package nts.uk.ctx.at.shared.dom.attendance;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ScreenUseAtr {
	/* 人件費*/
	COST(0),
	/* 乖離時間*/
	DIVERGENCETIME(1);
	public final int value;
}
