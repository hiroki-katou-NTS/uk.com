package nts.uk.ctx.at.record.infra.entity.workinformation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author nampt 日別実績の勤務情報
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAI_PER_WORK_INFO")
public class KrcdtDaiPerWorkInfo extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KrcdtDaiPerWorkInfoPK krcdtDaiPerWorkInfoPK;

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

	@OneToMany(mappedBy = "daiPerWorkInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(nullable = true)
	public List<KrcdtWorkScheduleTime> scheduleTimes;
	
	@Version
	@Column(name = "EXCLUS_VER")
	public long version;

	public KrcdtDaiPerWorkInfo(KrcdtDaiPerWorkInfoPK krcdtDaiPerWorkInfoPK) {
		super();
		this.krcdtDaiPerWorkInfoPK = krcdtDaiPerWorkInfoPK;
		this.scheduleTimes = new ArrayList<>();
	}
	
	@Override
	protected Object getKey() {
		return this.krcdtDaiPerWorkInfoPK;
	}

	public WorkInfoOfDailyPerformance toDomain() {
		return toDomain(this, scheduleTimes);
	}
	
	public static WorkInfoOfDailyPerformance toDomain(KrcdtDaiPerWorkInfo entity, List<KrcdtWorkScheduleTime> scheduleTimes) {
		WorkInfoOfDailyPerformance domain = new WorkInfoOfDailyPerformance(entity.krcdtDaiPerWorkInfoPK.employeeId,
												new WorkInformation(entity.recordWorkWorktimeCode, entity.recordWorkWorktypeCode),
												new WorkInformation(entity.scheduleWorkWorktimeCode, entity.scheduleWorkWorktypeCode),
												EnumAdaptor.valueOf(entity.calculationState, CalculationState.class),
												EnumAdaptor.valueOf(entity.goStraightAttribute, NotUseAttribute.class),
												EnumAdaptor.valueOf(entity.backStraightAttribute, NotUseAttribute.class), 
												entity.krcdtDaiPerWorkInfoPK.ymd,
												EnumAdaptor.valueOf(entity.dayOfWeek, DayOfWeek.class),
												KrcdtWorkScheduleTime.toDomain(scheduleTimes));
		
		domain.setVersion(entity.version);
		return domain;
	}

	public static KrcdtDaiPerWorkInfo toEntity(WorkInfoOfDailyPerformance workInfoOfDailyPerformance) {
		return new KrcdtDaiPerWorkInfo(
				new KrcdtDaiPerWorkInfoPK(workInfoOfDailyPerformance.getEmployeeId(),
						workInfoOfDailyPerformance.getYmd()),
				workInfoOfDailyPerformance.getWorkInformation().getRecordInfo().getWorkTypeCode() !=null ? workInfoOfDailyPerformance.getWorkInformation().getRecordInfo().getWorkTypeCode().v() : null,
				workInfoOfDailyPerformance.getWorkInformation().getRecordInfo().getWorkTimeCode() != null ? workInfoOfDailyPerformance.getWorkInformation().getRecordInfo().getWorkTimeCode().v() : null,
				workInfoOfDailyPerformance.getWorkInformation().getScheduleInfo().getWorkTypeCode() != null ? workInfoOfDailyPerformance.getWorkInformation().getScheduleInfo().getWorkTypeCode().v() : null,
				workInfoOfDailyPerformance.getWorkInformation().getScheduleInfo().getWorkTimeCode() != null ? workInfoOfDailyPerformance.getWorkInformation().getScheduleInfo().getWorkTimeCode().v() : null,
				workInfoOfDailyPerformance.getWorkInformation().getCalculationState() != null ? workInfoOfDailyPerformance.getWorkInformation().getCalculationState().value : null,
				workInfoOfDailyPerformance.getWorkInformation().getGoStraightAtr() != null ? workInfoOfDailyPerformance.getWorkInformation().getGoStraightAtr().value : null,
				workInfoOfDailyPerformance.getWorkInformation().getBackStraightAtr() != null ? workInfoOfDailyPerformance.getWorkInformation().getBackStraightAtr().value : null,
				workInfoOfDailyPerformance.getWorkInformation().getDayOfWeek() != null ? workInfoOfDailyPerformance.getWorkInformation().getDayOfWeek().value : null,
				workInfoOfDailyPerformance.getWorkInformation().getScheduleTimeSheets() != null ? 
				workInfoOfDailyPerformance.getWorkInformation().getScheduleTimeSheets().stream().map(f -> KrcdtWorkScheduleTime
						.toEntity(workInfoOfDailyPerformance.getEmployeeId(), workInfoOfDailyPerformance.getYmd(), f))
						.collect(Collectors.toList()) : null,
						workInfoOfDailyPerformance.getVersion());
	}

}
