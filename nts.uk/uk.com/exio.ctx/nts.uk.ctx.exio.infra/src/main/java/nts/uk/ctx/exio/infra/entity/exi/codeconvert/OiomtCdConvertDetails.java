package nts.uk.ctx.exio.infra.entity.exi.codeconvert;

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
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * コード変換詳細
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_CD_CONVERT_DETAILS")
public class OiomtCdConvertDetails extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtCdConvertDetailsPk cdConvertDetailsPk;

	/**
	 * 出力項目
	 */
	@Basic(optional = false)
	@Column(name = "OUTPUT_ITEM")
	public String outputItem;

	/**
	 * 本システムのコード
	 */
	@Basic(optional = false)
	@Column(name = "SYSTEM_CD")
	public String systemCd;

	@ManyToOne
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
			@PrimaryKeyJoinColumn(name = "CONVERT_CD", referencedColumnName = "CONVERT_CD") })
	private OiomtAcceptCdConvert oiomtAcceptCdConvert;

	@Override
	protected Object getKey() {
		return cdConvertDetailsPk;
	}
}
