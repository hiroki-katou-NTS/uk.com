package nts.uk.ctx.at.function.app.find.indexreconstruction;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.indexreconstruction.IndexReorgCat;

/**
 * The class Index reorganization category dto.<br>
 * インデックス再構成カテゴリ
 * 
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
public class IndexReorgCateDto {

	/** カテゴリNO */
	private String indexReconstrucCateNo;

	/** カテゴリ名 */
	private String categoryName;

	/**
	 * From domain.
	 * 
	 * @param domain the Index reorganization category domain
	 * @return the <code>IndexReorgCateDto</code>
	 */
	public static IndexReorgCateDto fromDomain(IndexReorgCat domain) {
		return new IndexReorgCateDto(domain.getCategoryNo().v(),
									 domain.getCategoryName().v());
	}

}
