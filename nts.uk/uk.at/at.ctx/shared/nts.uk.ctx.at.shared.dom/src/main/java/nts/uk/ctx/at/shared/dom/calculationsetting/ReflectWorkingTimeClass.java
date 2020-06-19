package nts.uk.ctx.at.shared.dom.calculationsetting;

import lombok.AllArgsConstructor;

/**
 * 
 * 就業時間帯打刻反映区分
 */
@AllArgsConstructor
public enum ReflectWorkingTimeClass {

	/* 打刻から就業時間帯を反映しない */
	DO_NOT_REFLECT(0),
	/* 打刻から就業時間帯を反映する */
	REFLECT(1);
	
	public final int value;
}
