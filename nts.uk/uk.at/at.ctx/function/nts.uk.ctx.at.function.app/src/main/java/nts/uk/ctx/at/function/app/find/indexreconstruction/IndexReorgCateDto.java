package nts.uk.ctx.at.function.app.find.indexreconstruction;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.indexreconstruction.IndexReorgCat;

/**
 * The class Index reorganization category dto.<br>
 * Dto インデックス再構成カテゴリ
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
public class IndexReorgCateDto implements IndexReorgCat.MementoSetter {

	/**
	 * カテゴリNO
	 */
	private int categoryNo;

	/**
	 * カテゴリ名
	 */
	private String categoryName;

	/**
	 * No args constructor.
	 */
	private IndexReorgCateDto() {
	}

	/**
	 * Creates from domain.
	 *
	 * @param domain the domain インデックス再構成カテゴリ
	 * @return the dto インデックス再構成カテゴリ
	 */
	public static IndexReorgCateDto createFromDomain(IndexReorgCat domain) {
		if (domain == null) {
			return null;
		}
		IndexReorgCateDto dto = new IndexReorgCateDto();
		domain.setMemento(dto);
		return dto;
	}

}
