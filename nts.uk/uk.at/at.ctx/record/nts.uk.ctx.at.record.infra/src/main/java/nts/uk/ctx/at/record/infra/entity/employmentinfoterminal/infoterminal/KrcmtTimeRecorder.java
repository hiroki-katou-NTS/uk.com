package nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.infoterminal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table(name = "KRCMT_TIMERECORDER")
public class KrcmtTimeRecorder extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtTimeRecorderPK pk;

	/**
	 * 名称
	 */
	@Column(name = "NAME")
	public String name;

	/**
	 * 機種
	 */
	@Column(name = "RECORD_TYPE")
	public int type;

	/**
	 * IPア ド レ ス
	 */
	@Column(name = "IP_ADDRESS")
	public String ipAddress;

	/**
	 * MACア ド レ ス
	 */
	@Column(name = "MAC_ADDRESS")
	public String macAddress;

	/**
	 * ル情報 端末シリアルNO
	 */
	@Column(name = "SERIAL_NO")
	public String serialNo;

	/**
	 * 設置場所コ ー ド
	 */
	@Column(name = "WORKLOCATION_CD")
	public String workLocationCode;

	/**
	 * 外出理由
	 */
	@Column(name = "REASON_GOOUT")
	public Integer reasonGoOut;

	/**
	 * 置換する
	 */
	@Column(name = "REPLACE_GOOUT")
	public int replaceGoOut;

	/**
	 * 換退勤を入退門に変換
	 */
	@Column(name = "REPLACE_LEAVINGGATE")
	public int replaceLeave;

	/**
	 * 換を応援に変換
	 */
	@Column(name = "REPLACE_SUPPORTWORK")
	public int replaceSupport;

	/**
	 * 監視間隔時間
	 */
	@Column(name = "INVERTER_TIME")
	public int inverterTime;

	@Override
	protected Object getKey() {
		return pk;
	}

}
