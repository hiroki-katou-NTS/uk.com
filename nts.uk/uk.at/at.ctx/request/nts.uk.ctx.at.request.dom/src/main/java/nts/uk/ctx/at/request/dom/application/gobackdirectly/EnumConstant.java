package nts.uk.ctx.at.request.dom.application.gobackdirectly;

import lombok.AllArgsConstructor;

@AllArgsConstructor
//するしない区分
public enum EnumConstant {
	/* しない */
	NOT_USE(0),
	/* する */
	USE(1);
	
	public final int value;
}
