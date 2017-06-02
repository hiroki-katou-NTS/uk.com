package nts.uk.ctx.at.shared.dom.worktime;

/**
 * 就業時間帯の設定方法
 * @author Doan Duy Hung
 *
 */

public enum WorkMethodSetting {
	
	// 通常勤務
	NormalWork(0),
	
	// フレックス勤務
	FlexWork(1),
	
	// 残業枠勤務
	OvertimeWork(2),
	
	// 流動勤務
	FluidWork(3);
	
	public final int value;
	
	WorkMethodSetting(int value){
		this.value = value;
	}

}
