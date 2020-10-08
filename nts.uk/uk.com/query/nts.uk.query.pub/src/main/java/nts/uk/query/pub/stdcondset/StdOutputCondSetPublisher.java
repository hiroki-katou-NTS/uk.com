package nts.uk.query.pub.stdcondset;

import java.util.List;

/**
 * The interface Standard output condition setting publisher.<br>
 * 出力条件設定（定型）
 * 
 * @author nws-minhnb
 */
public interface StdOutputCondSetPublisher {

	/**
	 * Gets all standard output condition setting by company id.
	 * 
	 * @param cid the Company id 「会社ID」
	 * @return the <code>StdOutputCondSetExport</code> list
	 */
	public List<StdOutputCondSetExport> getAllStdOutputCondSetsByCid(String cid);

}
