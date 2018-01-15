package nts.uk.ctx.at.shared.dom.worktime_old;

/**
 * 勤務形態区分
 * @author Doan Duy Hung
 *
 */

public enum WorkTimeDailyAtr {
	
	// 通常勤務・変形労働用
	Enum_Regular_Work(0),
	
	// フレックス勤務用
	Enum_Flex_Work(1);
	
	public final int value;
	
	WorkTimeDailyAtr(int value){
		this.value = value;
	}

	/**
	 * フレックス勤務か判定
	 * @return　フレックス勤務である
	 */
	public boolean isFlex() {
		return Enum_Flex_Work.equals(this);
	}
}
