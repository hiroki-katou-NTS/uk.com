package nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AgreeTimeYearImport {
	/** 限度時間 */
	private Integer limitTime;
	/** 実績時間 */
	private Integer recordTime;
	/** 状態 */
	private Integer status;
}
