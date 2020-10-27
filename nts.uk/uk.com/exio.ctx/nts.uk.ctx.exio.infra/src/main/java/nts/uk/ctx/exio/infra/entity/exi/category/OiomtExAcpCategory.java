package nts.uk.ctx.exio.infra.entity.exi.category;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 外部受入カテゴリ
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_EX_ACP_CATEGORY")
public class OiomtExAcpCategory extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Version
	@Column(name = "EXCLUS_VER")
	public Long version;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtExAcpCategoryPk exAcpCategoryPk;

	/**
	 * カテゴリ名
	 */
	@Basic(optional = false)
	@Column(name = "CATEGORY_NAME")
	public String categoryName;

	@Override
	protected Object getKey() {
		return exAcpCategoryPk;
	}
}
