package nts.uk.ctx.at.function.dom.adapter.standardmenu;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * （プログラムID、遷移先の画面ID、クエリ文字列（Optional））
 * 
 * @author sonnlb
 *
 */
@AllArgsConstructor
@Data
public class StandardMenuNameQueryImport {
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
	private Optional<String> queryString;
}
