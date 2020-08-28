package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardCreateResult;

/**
 * 打刻利用判断結果
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻設定.打刻入力の機能設定.打刻機能が利用できるか.打刻利用判断結果
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
public class MakeUseJudgmentResults {

	// 打刻利用可否
	@Getter
	private final CanEngravingUsed used;

	// 打刻カード作成結果
	@Getter
	private final Optional<StampCardCreateResult> cardResult;
	
	// [C-1] 利用できる
	public static MakeUseJudgmentResults get() {
		return new MakeUseJudgmentResults(CanEngravingUsed.AVAILABLE, Optional.empty());
	}
}
