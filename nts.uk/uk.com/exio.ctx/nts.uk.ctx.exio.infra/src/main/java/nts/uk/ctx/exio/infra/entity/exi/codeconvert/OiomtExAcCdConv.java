package nts.uk.ctx.exio.infra.entity.exi.codeconvert;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 受入コード変換
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_EX_AC_CD_CONV")
public class OiomtExAcCdConv extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtExAcCdConvPk acceptCdConvertPk;

	/**
	 * コード変換名称
	 */
	@Basic(optional = false)
	@Column(name = "CONVERT_NAME")
	public String convertName;

	/**
	 * 設定のないコードの受入
	 */
	@Basic(optional = false)
	@Column(name = "ACCEPT_WITHOUT_SETTING")
	public int acceptWithoutSetting;

	@OneToMany(targetEntity = OiomtExAcCdConvDtl.class, cascade = CascadeType.ALL, mappedBy = "oiomtExAcCdConv", orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "OIOMT_EX_AC_CD_CONV_DTL")
	public List<OiomtExAcCdConvDtl> oiomtExAcCdConvDtl;

	@Override
	protected Object getKey() {
		return acceptCdConvertPk;
	}
}
