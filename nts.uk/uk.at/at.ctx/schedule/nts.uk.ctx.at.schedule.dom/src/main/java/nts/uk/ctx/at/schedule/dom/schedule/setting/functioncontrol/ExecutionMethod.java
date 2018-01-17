package nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol;

import lombok.AllArgsConstructor;

/**
 * 実行方法
 * 
 * @author TanLV
 *
 */
@AllArgsConstructor
public enum ExecutionMethod {
	/* 条件選択 */
	CONDITION_SELECTION(0),
	/* 事前設定 */
	PRESETTING(1);
	
	public final int value;
}
