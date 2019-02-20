package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DailyInterimRemainMngDataAndFlg {
	private DailyInterimRemainMngData data;
	/**
	 * 「Workを考慮した月次処理用の暫定残数管理データを作成する」から取得した場合 True
	 * 「暫定年休管理データを取得」から取得した場合 false
	 */
	private boolean referenceFlg;
}
