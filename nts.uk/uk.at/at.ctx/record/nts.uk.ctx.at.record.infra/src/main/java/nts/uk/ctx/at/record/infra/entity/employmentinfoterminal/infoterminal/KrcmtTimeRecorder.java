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
	 * IPア ド レ ス１
	 */
	@Column(name = "IP_ADDRESS_1")
	public String ipAddress1;
	
	/**
	 * IPア ド レ ス２
	 */
	@Column(name = "IP_ADDRESS_2")
	public String ipAddress2;

	/**
	 * IPア ド レ ス３
	 */
	@Column(name = "IP_ADDRESS_3")
	public String ipAddress3;
	
	/**
	 * IPア ド レ ス４
	 */
	@Column(name = "IP_ADDRESS_4")
	public String ipAddress4;

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
	public Integer replaceGoOut;

	/**
	 * 出退勤を入退門に変換
	 */
	@Column(name = "REPLACE_LEAVINGGATE")
	public Integer replaceLeave;

	/**
	 * 監視間隔時間
	 */
	@Column(name = "INVERTER_TIME")
	public int inverterTime;

	/**
	 * 就業情報端末のメモ
	 */
	@Column(name = "MEMO")
	public String memo;
	
	/**
	 * 設置職場ID
	 */
	@Column(name = "WORKPLACE_ID")
	public String workPlaceId;
	
	/**
	 * 打刻分類1
	 */
	@Column(name = "MS_CHANGE_ATR_ATD")
	public Integer msChangeAtrAtd;
	
	/**
	 * 打刻分類2
	 */
	@Column(name = "MS_CHANGE_ATR_LVW")
	public Integer msChangeAtrLvw;
	
	/**
	 * 打刻分類3
	 */
	@Column(name = "MS_CHANGE_ATR_GO")
	public Integer msChangeAtrGo;
	
	/**
	 * 打刻分類4
	 */
	@Column(name = "MS_CHANGE_ATR_BACK")
	public Integer msChangeAtrBack;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	public String getIpAddress() {
		return this.ipAddress1 + '.' + this.ipAddress2 + '.' + this.ipAddress3 + '.' + this.ipAddress4;
	}

}
