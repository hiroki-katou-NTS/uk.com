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
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.KrcdtMonAggrTotalSpt;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.actualworkingtime.KrcdtMonRegIrregTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.flex.KrcdtMonFlexTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.KrcdtMonAggrTotalWrk;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.hdwkandcompleave.KrcdtMonAggrHdwkTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.hdwkandcompleave.KrcdtMonHdwkTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtime.KrcdtMonAggrOverTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtime.KrcdtMonOverTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.vacationusetime.KrcdtMonVactUseTime;
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
	
	/** 開始年月日 */
	@Column(name = "START_YMD")
	public GeneralDate startYmd;

	/** 終了年月日 */
	@Column(name = "END_YMD")
	public GeneralDate endYmd;

	/** 集計日数 */
	@Column(name = "AGGREGATE_DAYS")
	public Double aggregateDays;
	
	/** 法定労働時間 */
	@Column(name = "STAT_WORKING_TIME")
	public int statutoryWorkingTime;

    /** 実働時間：月別実績の通常変形時間 */
    @OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
    public KrcdtMonRegIrregTime krcdtMonRegIrregTime;

	/** 月別実績のフレックス時間 */
    @OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
    public KrcdtMonFlexTime krcdtMonFlexTime;

	/** 総労働時間：月別実績の休暇使用時間 */
    @OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
    public KrcdtMonVactUseTime krcdtMonVactUseTime;

	/** 総労働時間：集計総労働時間 */
    @OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
    public KrcdtMonAggrTotalWrk krcdtMonAggrTotalWrk;

	/** 総労働時間：残業時間：月別実績の残業時間 */
    @OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
    public KrcdtMonOverTime krcdtMonOverTime;

    /** 総労働時間：残業時間：集計残業時間 */
    @OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
    public List<KrcdtMonAggrOverTime> krcdtMonAggrOverTimes;

	/** 総労働時間：休出・代休：月別実績の休出時間 */
    @OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
    public KrcdtMonHdwkTime krcdtMonHdwkTime;

    /** 総労働時間：休出・代休：集計休出時間 */
    @OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
    public List<KrcdtMonAggrHdwkTime> krcdtMonAggrHdwkTimes;

	/** 集計総拘束時間 */
    @OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
    public KrcdtMonAggrTotalSpt krcdtMonAggrTotalSpt;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.PK;
	}
}
