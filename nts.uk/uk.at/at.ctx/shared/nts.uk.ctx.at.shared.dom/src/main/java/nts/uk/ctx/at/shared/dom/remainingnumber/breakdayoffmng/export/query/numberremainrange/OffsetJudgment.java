package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import lombok.AllArgsConstructor;

/**
 * @author ThanhNX
 *
 *         相殺判定の返すパラメータ
 */
@AllArgsConstructor
public enum OffsetJudgment {

	/* 終了状態 ← エラー終了 */
	ERROR(0),

	/* 終了状態 ← 正常終了 */
	SUCCESS(1);

	public final int value;
}
