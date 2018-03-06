package nts.uk.ctx.at.request.infra.entity.application.holidayshipment.recruitmentapp;

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
 * 振出申請
 * 
 * @author sonnlb
 */
@Entity
@Table(name = "KRQDT_RECRUITMENT_APP")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtRecruitmentApp extends UkJpaEntity implements Serializable {

	/**
	 * 
	 */
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
	@Id
	@Basic(optional = false)
	@Column(name = "WORK_TYPE_CD")
	private String workTypeCD;

	/**
	 * 勤務場所コード
	 */
	@Id
	@Basic(optional = false)
	@Column(name = "WORK_LOCATION_CD")
	private String workLocationCD;

	/**
	 * 就業時間帯
	 */
	@Id
	@Basic(optional = false)
	@Column(name = "WORK_TIME_CD")
	private String workTimeCD;

	/**
	 * 日区分
	 */
	@Id
	@Basic(optional = false)
	@Column(name = "START_WORK_TIME_ATR1")
	private int startWorkTimeAtr1;

	/**
	 * 時刻
	 */
	@Id
	@Basic(optional = false)
	@Column(name = "START_WORK_TIME1")
	private GeneralDate startWorkTime1;

	/**
	 * 日区分
	 */
	@Id
	@Basic(optional = false)
	@Column(name = "END_WORK_TIME_ATR1")
	private int endWorkTimeAtr1;

	/**
	 * 時刻
	 */
	@Id
	@Basic(optional = false)
	@Column(name = "END_WORK_TIME1")
	private GeneralDate endWorkTime1;

	/**
	 * 直行
	 */
	@Id
	@Basic(optional = false)
	@Column(name = "START_USE_ATR1")
	private int startUseAtr1;

	/**
	 * 直帰
	 */
	@Id
	@Basic(optional = false)
	@Column(name = "END_USE_ATR1")
	private int endUseAtr1;

	/**
	 * 日区分
	 */
	@Id
	@Basic(optional = true)
	@Column(name = "START_WORK_TIME_ATR2")
	private int startWorkTimeAtr2;

	/**
	 * 時刻
	 */
	@Id
	@Basic(optional = true)
	@Column(name = "START_WORK_TIME2")
	private GeneralDate startWorkTime2;

	/**
	 * 日区分
	 */
	@Id
	@Basic(optional = true)
	@Column(name = "END_WORK_TIME_ATR2")
	private int endWorkTimeAtr2;

	/**
	 * 時刻
	 */
	@Id
	@Basic(optional = true)
	@Column(name = "END_WORK_TIME2")
	private GeneralDate endWorkTime2;

	/**
	 * 直行
	 */
	@Id
	@Basic(optional = true)
	@Column(name = "START_USE_ATR2")
	private int startUseAtr2;

	/**
	 * 直帰
	 */
	@Id
	@Basic(optional = true)
	@Column(name = "END_USE_ATR2")
	private int endUseAtr2;

	/**
	 * 申請内容
	 */
	@Id
	@Basic(optional = true)
	@Column(name = "APP_CONTENT")
	private String appContent;

	/**
	 * 使用時間数
	 */
	@Id
	@Basic(optional = false)
	@Column(name = "HOURS_USED")
	private GeneralDate hoursUsed;

	/**
	 * 休出管理データ
	 */
	@Id
	@Basic(optional = true)
	@Column(name = "LEAVE_MNG_DATA_ID")
	private String leaveMngDataID;

	/**
	 * 休出発生日
	 */
	@Id
	@Basic(optional = false)
	@Column(name = "BREAK_OUT_DATE")
	private GeneralDate breakOutDate;

	/**
	 * 休出状態
	 */
	@Id
	@Basic(optional = false)
	@Column(name = "REST_STATE")
	private int restState;

	@Override
	protected Object getKey() {
		return appID;
	}

}
