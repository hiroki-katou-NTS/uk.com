package nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.infoterminal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_TR_REQUEST")
public class KrcmtTrRequest extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtTrRequestPK pk;

	/**
	 * 会社ID
	 */
	@Column(name = "CID")
	public String cid;

	/**
	 * 会社コード
	 */
	@Column(name = "COMPANY_CD")
	public String companyCode;

//	/**
//	 * 社員ID
//	 */
//	@Column(name = "SID")
//	public String sid;

	/**
	 * 残業・休日出勤送信
	 */
	@Column(name = "SEND_OVERTIME_NAME")
	public int sendOverTime;

	/**
	 * 申請理由送信
	 */
	@Column(name = "SEND_REASON_APP")
	public int sendReasonApp;

	/**
	 * 時刻セット
	 */
	@Column(name = "SEND_SERVERTIME")
	public int sendServerTime;
	
	/**
	 * 社員ID送信
	 */
	@Column(name = "SEND_SID")
	public int sendSid;

	/**
	 * 弁当メニュー枠番送信
	 */
	@Column(name = "SEND_RESERVATION")
	public int sendReservation;

	/**
	 * 	勤務種類コード送信
	 */
	@Column(name = "SEND_WORKTYPE")
	public int sendWorkType;
	
	/**
	 * 就業時間帯コード送信
	 */
	@Column(name = "SEND_WORKTIME")
	public int sendWorkTime;

	/**
	 * 全ての打刻データ
	 */
	@Column(name = "RECV_ALL_STAMP")
	public int recvStamp;

	/**
	 * 全ての予約データ
	 */
	@Column(name = "RECV_ALL_RESERVATION")
	public int recvReservation;

	/**
	 * 全ての申請データ
	 */
	@Column(name = "RECV_ALL_APPLICATION")
	public int recbApplication;
	
	/**
	 * リモート設定
	 */
	@Column(name = "REMOTE_SETTING")
	public int remoteSetting;

	/**
	 * 再起動を行う
	 */
	@Column(name = "REBOOT")
	public int reboot;
	
	/**
	 * 切替日時送信		
	 */
	@Column(name = "SEND_SWITCH_DATE")
	public int sendSwitchDate;
	
	/**
	 * 切替日時				
	 */
	@Column(name = "SWITCH_DATE")
	public GeneralDateTime switchDate;
	

	@Override
	protected Object getKey() {
		return pk;
	}

}
