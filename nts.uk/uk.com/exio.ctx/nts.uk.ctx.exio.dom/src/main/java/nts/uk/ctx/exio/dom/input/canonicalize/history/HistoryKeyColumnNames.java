package nts.uk.ctx.exio.dom.input.canonicalize.history;

import lombok.Value;

/**
 * 個人情報履歴のテーブルのキーとなるカラム名
 */
@Value
public class HistoryKeyColumnNames {

	private final String startDate;
	private final String endDate;
}
