package nts.uk.ctx.exio.infra.entity.exo.outcnddetail;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.exo.outcnddetail.SearchCodeList;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 検索コードリスト
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_SEARCH_CODE_LIST")
public class OiomtSearchCodeList extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtSearchCodeListPk searchCodeListPk;

	/**
	 * 検索コード
	 */
	@Basic(optional = false)
	@Column(name = "SEARCH_CODE")
	public String searchCode;

	/**
	 * 検索項目名
	 */
	@Basic(optional = false)
	@Column(name = "SEARCH_ITEM_NAME")
	public String searchItemName;

	@Override
	protected Object getKey() {
		return searchCodeListPk;
	}

	@ManyToOne
	@PrimaryKeyJoinColumns({
		@PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
		@PrimaryKeyJoinColumn(name = "CONDITION_SETTING_CD", referencedColumnName = "CONDITION_SETTING_CD"),
		@PrimaryKeyJoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_ID"),
		@PrimaryKeyJoinColumn(name = "CATEGORY_ITEM_NO", referencedColumnName = "CATEGORY_ITEM_NO"),
		@PrimaryKeyJoinColumn(name = "SERI_NUM", referencedColumnName = "SERI_NUM")
	})
	public OiomtOutCndDetailItem oiomtOutCndDetailItem;

	public OiomtSearchCodeList(String id, String cid, String cndSetCd, int categoryId, int categoryItemNo,
			int seriNum, String searchCode, String searchItemName) {
		this.searchCodeListPk = new OiomtSearchCodeListPk(id, cid, cndSetCd, categoryId, categoryItemNo, seriNum);
		this.searchCode = searchCode;
		this.searchItemName = searchItemName;
	}

	public SearchCodeList toDomain() {
		return new SearchCodeList(this.searchCodeListPk.id, this.searchCodeListPk.cid,
				this.searchCodeListPk.conditionSetCd, this.searchCodeListPk.categoryId,
				this.searchCodeListPk.categoryItemNo, this.searchCodeListPk.seriNum, this.searchCode,
				this.searchItemName);
	}
	
	public static OiomtSearchCodeList toEntity(SearchCodeList domain) {
		return new OiomtSearchCodeList(domain.getId(), domain.getCid(), domain.getConditionSetCode().v(),
				domain.getCategoryId().v(), domain.getCategoryItemNo().v(), domain.getSeriNum(),
				domain.getSearchCode().v(), domain.getSearchItemName());
	}
}
