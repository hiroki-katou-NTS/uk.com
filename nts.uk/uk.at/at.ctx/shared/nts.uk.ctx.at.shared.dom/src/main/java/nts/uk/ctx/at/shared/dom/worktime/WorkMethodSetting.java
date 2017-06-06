package nts.uk.ctx.at.shared.dom.worktime;

/**
 * 就業時間帯の設定方法
 * @author Doan Duy Hung
 *
 */

public enum WorkMethodSetting {
	
	// 通常勤務
	Enum_Normal_Work(0),
	
	// フレックス勤務
	Enum_Flex_Work(1),
	
	// 残業枠勤務
	Enum_Overtime_Work(2),
	
	// 流動勤務
	Enum_Fluid_Work(3);
	
	public final int value;
	
	WorkMethodSetting(int value){
		this.value = value;
	}

}
