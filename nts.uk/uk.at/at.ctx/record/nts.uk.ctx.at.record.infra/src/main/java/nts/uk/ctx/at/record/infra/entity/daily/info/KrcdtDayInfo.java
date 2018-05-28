package nts.uk.ctx.at.record.infra.entity.daily.info;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

public class KrcdtDayInfo extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	/* 主キー */
	@EmbeddedId
	public KrcdtDayInfoPK krcdtDayInfoPK;
	
	// 勤務実績の勤務情報. 勤務種類コード
	@Column(name = "RECORD_WORK_WORKTYPE_CODE")
	public String recordWorkWorktypeCode;

	// 勤務実績の勤務情報. 就業時間帯コード
	@Column(name = "RECORD_WORK_WORKTIME_CODE")
	public String recordWorkWorktimeCode;

	// 勤務予定の勤務情報. 勤務種類コード
	@Column(name = "SCHEDULE_WORK_WORKTYPE_CODE")
	public String scheduleWorkWorktypeCode;

	// 勤務予定の勤務情報. 勤務種類コード
	@Column(name = "SCHEDULE_WORK_WORKTIME_CODE")
	public String scheduleWorkWorktimeCode;

	@Column(name = "CALCULATION_STATE")
	public Integer calculationState;

	@Column(name = "GO_STRAIGHT_ATR")
	public Integer goStraightAttribute;

	@Column(name = "BACK_STRAIGHT_ATR")
	public Integer backStraightAttribute;
	
	@Column(name = "DAY_OF_WEEK")
	public Integer dayOfWeek;
	
	
	@Override
	protected Object getKey() {
		return this.krcdtDayInfoPK;
	}
	
	
}