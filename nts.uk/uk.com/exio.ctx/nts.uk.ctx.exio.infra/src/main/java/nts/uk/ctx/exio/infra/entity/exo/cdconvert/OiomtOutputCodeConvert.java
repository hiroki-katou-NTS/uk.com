package nts.uk.ctx.exio.infra.entity.exo.cdconvert;

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
 * 外部出力出力コード変換
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OIOMT_EX_OUT_CD_CONV")
public class OiomtOutputCodeConvert extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public OiomtOutputCodeConvertPk outputCodeConvertPk;


	/**
	 * コード変換名称
	 */
	@Basic(optional = false)
	@Column(name = "CONVERT_NAME")
	public String convertName;


	/**
	 * 設定のないコードの出力
	 */
	@Basic(optional = false)
	@Column(name = "ACCEPT_WITHOUT_SETTING")
	public int acceptWithoutSetting;

	@Override
	protected Object getKey() {
		return outputCodeConvertPk;
	}
	
	@OneToMany(targetEntity = OiomtCdConvertDetail.class, cascade = CascadeType.ALL, mappedBy = "oiomtOutputCodeConvert", orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "OIOMT_EX_OUT_CD_CONV_DTL")												
	public List<OiomtCdConvertDetail> listOiomtCdConvertDetail;
}
