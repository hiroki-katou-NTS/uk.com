package nts.uk.ctx.exio.infra.entity.exi.extcategory;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 外部受入カテゴリ
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_EX_ACP_CATEGORY")
public class OiomtExAcpCategory extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**	契約コード */
	@Basic(optional = false)
	@Column(name = "CONTRACT_CD")
	public String contractCd;
	/**
	 * 外部受入カテゴリID
	 */
	@Basic(optional = false)
	@Id
	@Column(name = "CATEGORY_ID")
	public int categoryId;

	/**
	 * カテゴリ名
	 */
	@Basic(optional = false)
	@Column(name = "CATEGORY_NAME")
	public String categoryName;
	
	/**就業システム区分	 */
	@Basic(optional = false)
	@Column(name = "EMPL_SYS_FLG")
	public int atSysFlg;
	
	/**人事システム区分	 */
	@Basic(optional = false)
	@Column(name = "PERS_SYS_FLG")
	public int persSysFlg;
	
	/**給与システム区分	 */
	@Basic(optional = false)
	@Column(name = "SALARY_SYS_FLG")
	public int salarySysFlg;
	
	/**オフィスヘルパー区分	 */
	@Basic(optional = false)
	@Column(name = "OFFICE_SYS_FLG")
	public int officeSysFlg;
	
	/**新規可能区分	 */
	@Basic(optional = false)
	@Column(name = "INSERT_FLG")
	public int insertFlg;
	
	/**削除可能区分	 */
	@Basic(optional = false)
	@Column(name = "DELETE_FLG")
	public int deleteFlg;
	
	@OneToMany(targetEntity = OiomtExAcpCategoryItem.class, cascade = CascadeType.ALL, mappedBy = "acpCategory", orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "OIOMT_EX_ACP_CATEGORY_ITEM")
	public List<OiomtExAcpCategoryItem> acpCategoryItem;
	
	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return this.categoryId;
	}
}
