package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calculationsettings.totalrestrainttime;

// 総拘束時間の計算方法
public enum CalculationMethodOfConstraintTime {
	// PCログイン・ログオフから求める（出勤退勤よりも外側の場合のみ）
	REQUEST_FROM_PC_OUTSIDE_ATTENDANCE(0),
	// 入門退門から求める
	REQUEST_FROM_ENTRANCE_EXIT(1),
	// 入門退門から求める（出勤退勤よりも外側の場合のみ）
	REQUEST_FROM_ENTRANCE_EXIT_OUTSIDE_ATTENDANCE(2);
	
	private CalculationMethodOfConstraintTime (int val) {
		value = val;
	}
	
	public final int value;
	
	
	/**
	 * 入門退門から求めるか判定する
	 * @return 使用する
	 */
	public boolean isREQUEST_FROM_ENTRANCE_EXIT() {
		return REQUEST_FROM_ENTRANCE_EXIT.equals(this);
	}
	
	/**
	 * 入門退門から求める（出勤退勤よりも外側の場合のみ）か判定する
	 * @return 
	 */
	public boolean isREQUEST_FROM_ENTRANCE_EXIT_OUTSIDE_ATTENDANCE() {
		return REQUEST_FROM_ENTRANCE_EXIT_OUTSIDE_ATTENDANCE.equals(this);
	}
	
	/**
	 * PCログイン・ログオフから求める（出勤退勤よりも外側の場合のみ）か判定する
	 * @return 
	 */
	public boolean isREQUEST_FROM_PC_OUTSIDE_ATTENDANCE() {
		return REQUEST_FROM_PC_OUTSIDE_ATTENDANCE.equals(this);
	}
	
	
}
