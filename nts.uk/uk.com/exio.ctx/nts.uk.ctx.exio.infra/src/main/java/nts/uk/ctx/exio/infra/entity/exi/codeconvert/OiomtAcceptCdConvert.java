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
@Table(name = "OIOMT_ACCEPT_CD_CONVERT")
public class OiomtAcceptCdConvert extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtAcceptCdConvertPk acceptCdConvertPk;

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

	@OneToMany(targetEntity = OiomtCdConvertDetails.class, cascade = CascadeType.ALL, mappedBy = "oiomtAcceptCdConvert", orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "OIOMT_CD_CONVERT_DETAILS")
	public List<OiomtCdConvertDetails> oiomtCdConvertDetails;

	@Override
	protected Object getKey() {
		return acceptCdConvertPk;
	}
}
