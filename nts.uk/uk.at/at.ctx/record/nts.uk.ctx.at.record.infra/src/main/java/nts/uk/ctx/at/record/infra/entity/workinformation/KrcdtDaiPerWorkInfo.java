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
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author nampt 日別実績の勤務情報
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAI_PER_WORK_INFO")
public class KrcdtDaiPerWorkInfo extends ContractUkJpaEntity implements Serializable {

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
												new WorkInformation(entity.recordWorkWorktypeCode, entity.recordWorkWorktimeCode),
												new WorkInformation(entity.scheduleWorkWorktypeCode, entity.scheduleWorkWorktimeCode),
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
		WorkInfoOfDailyAttendance wki = workInfoOfDailyPerformance.getWorkInformation();
		
		return new KrcdtDaiPerWorkInfo(
				new KrcdtDaiPerWorkInfoPK(workInfoOfDailyPerformance.getEmployeeId(),
						workInfoOfDailyPerformance.getYmd()),
				wki.getRecordInfo().getWorkTypeCode() != null ? wki.getRecordInfo().getWorkTypeCode().v() : null,
				wki.getRecordInfo().getWorkTimeCodeNotNull().map(m -> m.v()).orElse(null),
				wki.getScheduleInfo().getWorkTypeCode() != null ? wki.getScheduleInfo().getWorkTypeCode().v() : null,
				wki.getScheduleInfo().getWorkTimeCodeNotNull().map(m -> m.v()).orElse(null),
				wki.getCalculationState() != null ? wki.getCalculationState().value : null,
				wki.getGoStraightAtr() != null ? wki.getGoStraightAtr().value : null,
				wki.getBackStraightAtr() != null ? wki.getBackStraightAtr().value : null,
				wki.getDayOfWeek() != null ? wki.getDayOfWeek().value : null,
				wki.getScheduleTimeSheets() != null ? wki.getScheduleTimeSheets().stream()
						.map(f -> KrcdtWorkScheduleTime.toEntity(workInfoOfDailyPerformance.getEmployeeId(),
								workInfoOfDailyPerformance.getYmd(), f))
						.collect(Collectors.toList()) : null,
				workInfoOfDailyPerformance.getVersion());
	}

}
