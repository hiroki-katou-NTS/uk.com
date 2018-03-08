package nts.uk.ctx.at.request.infra.entity.application.holidayshipment.absenceleaveapp;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 振休申請
 * 
 * @author sonnlb
 */
@Entity
@Table(name = "KRQDT_ABSENCE_LEAVE_APP")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtAbsenceLeaveApp extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 申請ID
	 */
	@Id
	@Basic(optional = false)
	@Column(name = "APP_ID")
	private String appID;

	/**
	 * 勤務種類
	 */
	@Basic(optional = false)
	@Column(name = "WORK_TYPE_CD")
	private String workTypeCD;

	/**
	 * 就業時間帯変更
	 */
	@Basic(optional = false)
	@Column(name = "CHANGE_WORK_HOURS_ATR")
	private int changeWorkHoursAtr;

	/**
	 * 勤務場所コード
	 */
	@Basic(optional = true)
	@Column(name = "WORK_LOCATION_CD")
	private String workLocationCD;

	/**
	 * 就業時間帯
	 */
	@Basic(optional = true)
	@Column(name = "WORK_TIME_CD")
	private String workTimeCD;

	/**
	 * 日区分
	 */
	@Basic(optional = true)
	@Column(name = "START_WORK_TIME_ATR1")
	private int startWorkTimeAtr1;

	/**
	 * 開始時刻
	 */
	@Basic(optional = true)
	@Column(name = "START_WORK_TIME1")
	private GeneralDate startWorkTime1;

	/**
	 * 日区分
	 */
	@Basic(optional = true)
	@Column(name = "END_WORK_TIME_ATR1")
	private int endWorkTimeAtr1;

	/**
	 * 終了時刻
	 */
	@Basic(optional = true)
	@Column(name = "END_WORK_TIME1")
	private GeneralDate endWorkTime1;

	/**
	 * 日区分
	 */
	@Basic(optional = true)
	@Column(name = "START_WORK_TIME_ATR2")
	private int startWorkTimeAtr2;

	/**
	 * 開始時刻
	 */
	@Basic(optional = true)
	@Column(name = "START_WORK_TIME2")
	private GeneralDate startWorkTime2;

	/**
	 * 日区分
	 */
	@Basic(optional = true)
	@Column(name = "END_WORK_TIME_ATR2")
	private int endWorkTimeAtr2;

	/**
	 * 終了時刻
	 */
	@Basic(optional = true)
	@Column(name = "END_WORK_TIME2")
	private GeneralDate endWorkTime2;

	/**
	 * 申請内容
	 */
	@Basic(optional = true)
	@Column(name = "APP_CONTENT")
	private String appContent;

	/**
	 * 使用時間数
	 */
	@Basic(optional = false)
	@Column(name = "HOURS_USED")
	private GeneralDate hoursUsed;

	/**
	 * 休出管理データ
	 */
	@Basic(optional = true)
	@Column(name = "LEAVE_MNG_DATA_ID")
	private String leaveMngDataID;

	/**
	 * 休出発生日
	 */
	@Basic(optional = false)
	@Column(name = "BREAK_OUT_DATE")
	private GeneralDate breakOutDate;

	/**
	 * 管理データ区分
	 */
	@Basic(optional = false)
	@Column(name = "REST_STATE")
	private int restState;

	/**
	 * 管理データ日数単位
	 */
	@Basic(optional = false)
	@Column(name = "DAYS_USED_NO")
	private int daysUsedNo;

	/**
	 * 振出管理データ
	 */
	@Basic(optional = true)
	@Column(name = "PAYOUT_MNG_DATA_ID")
	private String payoutMngDataID;

	/**
	 * 管理データ区分
	 */
	@Basic(optional = false)
	@Column(name = "PICK_UP_STATE")
	private int pickUpState;

	/**
	 * 振休発生日
	 */
	@Basic(optional = false)
	@Column(name = "OCCURRENCE_DATE")
	private GeneralDate occurrenceDate;

	@Override
	protected Object getKey() {
		return appID;
	}

}
