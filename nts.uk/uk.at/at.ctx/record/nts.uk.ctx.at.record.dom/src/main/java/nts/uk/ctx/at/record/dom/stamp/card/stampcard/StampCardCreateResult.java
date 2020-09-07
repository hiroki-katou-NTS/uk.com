package nts.uk.ctx.at.record.dom.stamp.card.stampcard;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.task.tran.AtomTask;

/**
 * 
 * @author sonnlb
 * 
 *         打刻カード作成結果
 *
 *         UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻カード.打刻カード番号を自動作成する.打刻カード作成結果
 */
@Value
@AllArgsConstructor
public class StampCardCreateResult {
	// 打刻カード番号
	private final String cardNumber;

	// 永続化処理
	private final Optional<AtomTask> atomTask;

	// [C-0] 打刻カード作成結果(打刻カード番号, 永続化処理)
}
