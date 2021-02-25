package nts.uk.ctx.at.function.dom.adapter.standardmenu;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StandardMenuNameImport {
	/**
	 * プログラムID
	 */
	private String programId;

	/**
	 * 遷移先の画面ID
	 */
	private String screenId;

	/**
	 * クエリ文字列
	 */
	private String queryString;

	/**
	 * 表示名称
	 */
	private String displayName;
}
