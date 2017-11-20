package nts.uk.ctx.at.record.infra.entity.monthly;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.KrcdtAggrTotalTmSpent;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.KrcdtMonthlyCalculation;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.actualworkingtime.KrcdtIrregWrkTimeMon;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.actualworkingtime.KrcdtRegIrregTimeMon;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.flex.KrcdtFlexTimeMon;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.KrcdtAggrTotalWrkTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.KrcdtPrescrWrkTimeMon;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.KrcdtWorkTimeOfMonth;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.holidayusetime.KrcdtAnnualLeaveUtMon;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.holidayusetime.KrcdtCompenLeaveUtMon;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.holidayusetime.KrcdtRetentYearUtMon;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.holidayusetime.KrcdtSpHolidayUtMon;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave.KrcdtAggrHolidayWrkTm;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave.KrcdtHolidayWorkTmMon;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtimework.KrcdtAggrOverTimeWork;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtimework.KrcdtOverTimeWorkMon;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：月別実績の勤怠時間
 * @author shuichi_ishida
 */
@Entity
@Table(name = "KRCDT_MON_ATTENDANCE_TIME")
@NoArgsConstructor
public class KrcdtMonAttendanceTime extends UkJpaEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAttendanceTimePK PK;

	/** 集計日数 */
	@Column(name = "AGGREGATE_DAYS")
	public double aggregateDays;

	/** 月別実績の月の計算 */
    @OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
    public KrcdtMonthlyCalculation krcdtMonthlyCalculation;

    /** 実働時間：月別実績の通常変形時間 */
    @OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
    public KrcdtRegIrregTimeMon krcdtRegIrregTimeMon;

    /** 実働時間：月別実績の変形労働時間 */
    @OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
    public KrcdtIrregWrkTimeMon krcdtIrregWrkTimeMon;

	/** 月別実績のフレックス時間 */
    @OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
    public KrcdtFlexTimeMon krcdtFlexTimeMon;

	/** 総労働時間：休暇使用時間：月別実績の年休使用時間 */
    @OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
    public KrcdtAnnualLeaveUtMon krcdtAnnualLeaveUtMon;

	/** 総労働時間：休暇使用時間：月別実績の積立年休使用時間 */
    @OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
    public KrcdtRetentYearUtMon krcdtRetentYearUtMon;

	/** 総労働時間：休暇使用時間：月別実績の特別休暇使用時間 */
    @OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
    public KrcdtSpHolidayUtMon krcdtSpHolidayUtMon;

	/** 総労働時間：休暇使用時間：月別実績の代休使用時間 */
    @OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
    public KrcdtCompenLeaveUtMon krcdtCompenLeaveUtMon;

	/** 総労働時間：集計総労働時間 */
    @OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
    public KrcdtAggrTotalWrkTime krcdtAggrTotalWrkTime;

	/** 総労働時間：月別実績の就業時間 */
    @OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
    public KrcdtWorkTimeOfMonth krcdtWorkTimeOfMonth;

	/** 総労働時間：月別実績の所定労働時間 */
    @OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
    public KrcdtPrescrWrkTimeMon krcdtPrescrWrkTimeMon;

	/** 総労働時間：残業時間：月別実績の残業時間 */
    @OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
    public KrcdtOverTimeWorkMon krcdtOverTimeWorkMon;

    /** 総労働時間：残業時間：集計残業時間 */
    @OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
    public List<KrcdtAggrOverTimeWork> krcdtAggrOverTimeWorks;

	/** 総労働時間：休出・代休：月別実績の休出時間 */
    @OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
    public KrcdtHolidayWorkTmMon krcdtHolidayWorkTmMon;

    /** 総労働時間：休出・代休：集計休出時間 */
    @OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
    public List<KrcdtAggrHolidayWrkTm> krcdtAggrHolidayWrkTms;

	/** 集計総拘束時間 */
    @OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
    public KrcdtAggrTotalTmSpent krcdtAggrTotalTmSpent;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.PK;
	}
}
