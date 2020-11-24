package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare;

/**
 * 申告時間枠エラー
 * @author shuichi_ishida
 */
public enum DeclareTimeFrameError {
	/** 早出残業 (the early overtime) */
	EARLY_OT,
	/** 早出残業深夜 (the early overtime midnight) */
	EARLY_OT_MN,
	/** 普通残業 (the overtime) */
	OVERTIME,
	/** 普通残業深夜 (the overtime midnight) */
	OVERTIME_MN,
	/** 休出 (the holidaywork) */
	HOLIDAYWORK,
	/** 休出深夜 (the holidaywork midnight) */
	HOLIDAYWORK_MN
}
