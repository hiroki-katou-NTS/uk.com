package nts.uk.ctx.at.record.dom.stamp.card.stampcard;

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
public class StampCardCreateResult {
	// 打刻カード番号
	private final String cardNumber;

	// 永続化処理
	private final AtomTask atomTask;

	// [C-0] 打刻カード作成結果(打刻カード番号, 永続化処理)
	public StampCardCreateResult(String cardNumber, AtomTask atomTask) {
		super();
		this.cardNumber = cardNumber;
		this.atomTask = atomTask;
	}
}
