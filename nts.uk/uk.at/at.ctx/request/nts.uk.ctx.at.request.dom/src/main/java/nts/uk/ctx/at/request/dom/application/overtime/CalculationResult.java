package nts.uk.ctx.at.request.dom.application.overtime;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Refactor5
 * UKDesign.UniversalK.就業.KAF_申請.KAF005_残業申請.A：残業申請（新規登録）.アルゴリズム.19_計算処理
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
// 計算結果
public class CalculationResult {
	// 事前申請・実績の超過状態
	private Optional<OverStateOutput> overStateOutput = Optional.empty();
	// 申請時間
	private List<ApplicationTime> applicationTimes = Collections.emptyList();
}
