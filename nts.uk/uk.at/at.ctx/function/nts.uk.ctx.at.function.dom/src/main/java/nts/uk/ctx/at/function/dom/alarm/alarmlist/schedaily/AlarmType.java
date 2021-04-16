package nts.uk.ctx.at.function.dom.alarm.alarmlist.schedaily;

import lombok.AllArgsConstructor;

/**
 * 0： 任意抽出条件
 * 1: 固有抽出
 */
@AllArgsConstructor
public enum AlarmType {
	// 任意抽出条件
	ARBITRARY_EXTRACTION_CONDITIONS(0, "任意抽出条件"),
	
	// 固有抽出
	UNIQUE_EXTRACTION(1, "固有抽出");

    public final int value;
    public final String nameId;
}
