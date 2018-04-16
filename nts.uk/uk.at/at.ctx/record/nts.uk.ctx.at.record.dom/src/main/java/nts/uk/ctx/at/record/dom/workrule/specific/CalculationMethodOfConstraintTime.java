package nts.uk.ctx.at.record.dom.workrule.specific;

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
}
