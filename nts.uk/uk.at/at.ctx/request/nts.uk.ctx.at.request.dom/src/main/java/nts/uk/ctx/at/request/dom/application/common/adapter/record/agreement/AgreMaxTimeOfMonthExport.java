package nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 月別実績の36協定上限時間
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AgreMaxTimeOfMonthExport {
	/** 36協定時間 */
	private Integer agreementTime;
	/** 上限時間 */
	private Integer maxTime;
	/** 状態 */
	private Integer status;
}
