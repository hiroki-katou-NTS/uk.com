package nts.uk.ctx.at.shared.dom.worktime_old;

/**
 * 就業時間帯の設定方法
 * @author Doan Duy Hung
 *
 */

public enum WorkTimeMethodSet {
	
	// 固定勤務
	Enum_Fixed_Work(0),
	
	// 時差勤務
	Enum_Jogging_Time(1),
	
	// 残業枠勤務
	Enum_Overtime_Work(2),
	
	// 流動勤務
	Enum_Fluid_Work(3);
	
	public final int value;
	
	WorkTimeMethodSet(int value){
		this.value = value;
	}
	
	/**
	 * 固定勤務か判定
	 * @return　固定勤務である
	 */
	public boolean isFixedWork() {
		return Enum_Fixed_Work.equals(this);
	}
	
	/**
	 * 流動勤務か判定する
	 * @return 流動勤務である
	 */
	public boolean isFluidWork() {
		return Enum_Fluid_Work.equals(this);
	}
}
