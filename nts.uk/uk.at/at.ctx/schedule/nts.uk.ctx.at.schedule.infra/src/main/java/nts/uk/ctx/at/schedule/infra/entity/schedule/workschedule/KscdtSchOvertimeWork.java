package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.clearovertime.OverTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.OverTimeFrameTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.OverTimeFrameTimeSheet;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 勤務予定の残業時間
 * UKDesign.データベース.ER図.就業.勤務予定.勤務予定.勤務予定
 * @author HieuLt
 *
 */
@Entity
@NoArgsConstructor
@Table(name="KSCDT_SCH_OVERTIME_WORK")
@Getter
public class KscdtSchOvertimeWork extends ContractUkJpaEntity {
	
	@EmbeddedId
	public KscdtSchOvertimeWorkPK pk;
	/** 会社ID **/
	@Column(name = "CID")
	public String cid;
	/** 残業時間 **/
	@Column(name = "OVERTIME_WORK_TIME")
	public int overtimeWorkTime;
	/** 振替時間 **/
	@Column(name = "OVERTIME_WORK_TIME_TRANS")
	public int overtimeWorkTimeTrans;
	/** 事前申請時間 **/
	@Column(name = "OVERTIME_WORK_TIME_PREAPP")
	public int overtimeWorkTimePreApp;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "SID", referencedColumnName = "SID"),
			@PrimaryKeyJoinColumn(name = "YMD", referencedColumnName = "YMD") })
	public KscdtSchTime kscdtSchTime;
	
	public static KscdtSchOvertimeWork toEntity (OverTimeFrameTime overTimeFrameTime, String cid , String sid , GeneralDate ymd ){
		KscdtSchOvertimeWorkPK pk = new KscdtSchOvertimeWorkPK(sid, ymd, overTimeFrameTime.getOverWorkFrameNo().v());		
		return new  KscdtSchOvertimeWork(pk,
				cid,
				overTimeFrameTime.getOverTimeWork().getTime().v(),
				overTimeFrameTime.getTransferTime().getTime().v(),
				overTimeFrameTime.getBeforeApplicationTime().v()
				);
	}
	
	public KscdtSchOvertimeWork(KscdtSchOvertimeWorkPK pk, String cid, int overtimeWorkTime, int overtimeWorkTimeTrans,
			int overtimeWorkTimePreApp) {
		super();
		this.pk = pk;
		this.cid = cid;
		this.overtimeWorkTime = overtimeWorkTime;
		this.overtimeWorkTimeTrans = overtimeWorkTimeTrans;
		this.overtimeWorkTimePreApp = overtimeWorkTimePreApp;
	}

	//勤務予定．勤怠時間．勤務時間．総労働時間．所定外時間．残業時間
	public OverTimeOfDaily toDomain(OverTimeOfDaily overTimeOfDailys, List<KscdtSchOvertimeWork> overtimeWorks){
		OverTimeOfDaily overTimeOfDaily = null;
		List<OverTimeFrameTimeSheet> overTimeFrameTimeSheets = new ArrayList<>();
		List<OverTimeFrameTime>  overTimeFrameTimes = new ArrayList<>();
		if(!overtimeWorks.isEmpty()) {
		overtimeWorks.stream().forEach(x ->{
			OverTimeFrameTimeSheet timesheet = new OverTimeFrameTimeSheet(null, new OverTimeFrameNo(x.getPk().frameNo));
			OverTimeFrameTime time = new OverTimeFrameTime(
					new OverTimeFrameNo(x.getPk().frameNo),
					 TimeDivergenceWithCalculation.sameTime(new AttendanceTime(x.getOvertimeWorkTime()) ), 
					 TimeDivergenceWithCalculation.sameTime(new AttendanceTime(x.getOvertimeWorkTimeTrans()) ),
					new AttendanceTime(x.getOvertimeWorkTimePreApp()),
					null);
			overTimeFrameTimeSheets.add(timesheet);
			overTimeFrameTimes.add(time);	
		});
		}
		overTimeOfDaily = new OverTimeOfDaily(overTimeFrameTimeSheets, overTimeFrameTimes, 
				Finally.of(overTimeOfDailys.getExcessOverTimeWorkMidNightTime().get()), overTimeOfDailys.getIrregularWithinPrescribedOverTimeWork()
				, overTimeOfDailys.getFlexTime(), 
				overTimeOfDailys.getOverTimeWorkSpentAtWork());
		return overTimeOfDaily;
	}

}
