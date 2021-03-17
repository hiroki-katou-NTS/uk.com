package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.hdworkapplicationsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRQST_WITHDRAWAL_APP_SET")
public class KrqstWithDrawalAppSet extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 会社ID **/
	@Id
	@Column(name = "CID")
	public String companyId;
	
	/** 事前反映設定 **/
	@Column(name = "PRE_REFLECT_SET")
	public int prePerflex;
	
	/** 休憩時間 **/
	@Column(name = "BREAK_TIME")
	public int breakTime;
	
	/** 勤務時間（出勤、退勤時刻） **/
	@Column(name = "WORK_TIME")
	public int workTime;
	
	/** 休出時間未入力チェック **/
	@Column(name = "CHECK_NO_HD_TIME")
	public int checkHdTime;
	
	/** 休出申請勤務種類 **/
	@Column(name = "TYPES_OF_PAID_LEAVE")
	public int typePaidLeave;
	
	/** 勤務変更設定 **/
	@Column(name = "WORK_CHANGE_SET")
	public int workChange;
	
	/** 時間初期表示 **/
	@Column(name = "TIME_INITIAL_DISP")
	public int timeInit;
	
	/** 法内法外矛盾チェック **/
	@Column(name = "CHECK_OUTSIDE_LEGAL")
	public int checkOut;
	
	/** 代休先取り許可 **/
	@Column(name = "PREFIX_LEAVE_PERMISSION")
	public int prefixLeave;
	
	/** 休出時間指定単位  **/
	@Column(name = "UNIT_TIME_HD")
	public int unitTime;
	
	/** 休暇申請同時申請設定 **/
	@Column(name = "APP_SIMULTANEOUS_APP_SET")
	public int appSimul;
	
	/** 直帰区分 **/
	@Column(name = "BOUNCE_SEGMENT_ATR")
	public int bounSeg;
	
	/** 直行区分 **/
	@Column(name = "DIRECT_DIVISION_ATR")
	public int directDivi;
	
	/** 休出時間 **/
	@Column(name = "REST_TIME")
	public int restTime;
	
	/** 実績超過打刻優先設定 **/
	@Column(name = "OVERRIDE_SET")
	public int overrideSet;
	
	/** 打刻漏れ計算区分 **/
	@Column(name = "CALCULATION_STAMP_MISS")
	public int calStampMiss;

	@Override
	protected Object getKey() {
		return null;
	}
}
