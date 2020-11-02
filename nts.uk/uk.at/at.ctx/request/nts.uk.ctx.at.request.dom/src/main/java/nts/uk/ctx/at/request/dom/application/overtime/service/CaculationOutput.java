package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.CalculationResult;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;

/**
 * Refactor5
 * output of 19_計算処理
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data

public class CaculationOutput {
	// 計算結果
	private CalculationResult calculationResult;
	// 休出枠<List>
	private List<WorkdayoffFrame> workdayoffFrames = Collections.emptyList();
}
