package nts.uk.ctx.exio.infra.entity.exo.outputitem;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * カテゴリ項目
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_CTG_ITEM")
@Getter
public class OiomtCtgItem extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtCtgItemPk ctgItemPk;

	/**
	 * カテゴリID
	 */
	@Basic(optional = false)
	@Column(name = "CTG_ID")
	public int ctgId;

	/**
	 * 演算符号
	 */
	@Basic(optional = true)
	@Column(name = "OPERATION_SYMBOL")
	public Integer operationSymbol;

	/**
	 * カテゴリ項目
	 */
	@ManyToOne
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
			@PrimaryKeyJoinColumn(name = "OUT_ITEM_CD", referencedColumnName = "OUT_ITEM_CD"),
			@PrimaryKeyJoinColumn(name = "COND_SET_CD", referencedColumnName = "COND_SET_CD")})
	private OiomtStdOutItem oiomtStdOutItem;

	
	@Override
	protected Object getKey() {
		return ctgItemPk;
	}

}
