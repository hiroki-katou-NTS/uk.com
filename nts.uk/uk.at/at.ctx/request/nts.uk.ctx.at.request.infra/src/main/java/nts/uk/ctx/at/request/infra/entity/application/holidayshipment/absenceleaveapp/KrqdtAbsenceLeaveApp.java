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
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

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
public class KrqdtAbsenceLeaveApp extends ContractUkJpaEntity implements Serializable {

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
	 * 就業時間帯
	 */
	@Basic(optional = true)
	@Column(name = "WORK_TIME_CD")
	private String workTimeCD;

	/**
	 * 開始時刻
	 */
	@Basic(optional = true)
	@Column(name = "START_WORK_TIME1")
	private Integer startWorkTime1;

	/**
	 * 終了時刻
	 */
	@Basic(optional = true)
	@Column(name = "END_WORK_TIME1")
	private Integer endWorkTime1;

	/**
	 * 開始時刻
	 */
	@Basic(optional = true)
	@Column(name = "START_WORK_TIME2")
	private Integer startWorkTime2;

	/**
	 * 終了時刻
	 */
	@Basic(optional = true)
	@Column(name = "END_WORK_TIME2")
	private Integer endWorkTime2;

	@Override
	protected Object getKey() {
		return appID;
	}

}
