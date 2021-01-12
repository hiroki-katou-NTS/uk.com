package nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;

/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckBeforeOutput {
	/**
	 * 時間外時間の詳細
	 */
	private AppOvertimeDetail appOvertimeDetail;
	/**
	 * 確認メッセージリスト
	 */
	private List<ConfirmMsgOutput> confirmMsgOutputs = Collections.emptyList();
}
