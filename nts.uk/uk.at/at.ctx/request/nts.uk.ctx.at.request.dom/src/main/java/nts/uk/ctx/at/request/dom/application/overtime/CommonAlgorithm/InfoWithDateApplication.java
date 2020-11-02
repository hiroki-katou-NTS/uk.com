package nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Refactor5
 * UKDesign.UniversalK.就業.KAF_申請.KAF005_残業申請.AB画面の共通アルゴリズム.申請日に関する情報を取得する
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
// 申請日に関係する情報 pending
public class InfoWithDateApplication {
	// 勤務時間
	private Optional<Integer> workHours = Optional.empty();
}
