package nts.uk.ctx.at.shared.dom.remainingnumber.base;

import lombok.AllArgsConstructor;

/**
 * 消化区分
 * @author HopNT	
 *
 */
@AllArgsConstructor
public enum DigestionAtr {
	// 未消化
	UNUSED(0),
	// 消化済
	USED(1),
	// 消滅
	EXPIRED(2);
	
	public final int value;
}
