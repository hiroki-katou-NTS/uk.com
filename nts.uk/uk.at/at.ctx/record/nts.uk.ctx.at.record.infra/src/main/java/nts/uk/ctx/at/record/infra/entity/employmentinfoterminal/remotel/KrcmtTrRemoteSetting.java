package nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.remotel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_TR_REMOTE_SETTING")
public class KrcmtTrRemoteSetting extends UkJpaEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtTrRemoteSettingPK pk;

	/**
	 * 機種名
	 */
	@Column(name = "TR_NAME")
	public String empInfoTerName;

	/**
	 * ROMバージョン
	 */
	@Column(name = "ROM_VERSION")
	public String romVersion;

	/**
	 * 機種区分
	 */
	@Column(name = "RECORD_TYPE")
	public int modelEmpInfoTer;

	/**
	 * 大項目名称
	 */
	@Column(name = "MAJOR_NAME")
	public String majorClassification;

	/**
	 * 小項目名称
	 */
	@Column(name = "SMALL_NAME")
	public String smallClassification;

	/**
	 * 入力タイプ
	 */
	@Column(name = "INPUT_TYPE")
	public Integer type;

	/**
	 * 入力桁数
	 */
	@Column(name = "NUMBER_DIGIST")
	public Integer numberOfDigits;

	/**
	 * 設定値
	 */
	@Column(name = "SET_VALUE")
	public String settingValue;

	/**
	 * 入力範囲
	 */
	@Column(name = "INPUT_RANGE")
	public String inputRange;

	/**
	 * 再起動フラグ
	 */
	@Column(name = "REBOOT")
	public Integer rebootFlg;

	/**
	 * 現在の値
	 */
	@Column(name = "CURRENT_VALUE")
	public String currentValue;

	@Override
	protected Object getKey() {
		return pk;
	}

}
