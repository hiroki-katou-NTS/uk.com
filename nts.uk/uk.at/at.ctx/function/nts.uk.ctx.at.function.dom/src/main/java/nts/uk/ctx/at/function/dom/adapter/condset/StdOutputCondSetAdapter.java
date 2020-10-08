package nts.uk.ctx.at.function.dom.adapter.condset;

import java.util.List;

/**
 * The interface Standard output condition setting adapter.<br>
 * 出力条件設定（定型）
 *
 * @author nws-minhnb
 */
public interface StdOutputCondSetAdapter {

	/**
	 * Finds all standard output condition setting by company id.
	 * 
	 * @param cid the Company id 「会社ID」
	 * @return the <code>StdOutputCondSetExport</code> list
	 */
	public List<StdOutputCondSetImport> findAllStdOutputCondSetsByCid(String cid);

}
