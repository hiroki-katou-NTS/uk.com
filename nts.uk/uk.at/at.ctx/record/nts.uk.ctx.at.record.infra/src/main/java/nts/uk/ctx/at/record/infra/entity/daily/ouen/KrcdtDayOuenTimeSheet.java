package nts.uk.ctx.at.record.infra.entity.daily.ouen;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.EngravingMethod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.TimeSheetOfAttendanceEachOuenSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.WorkContent;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.record.WorkplaceOfWorkEachOuen;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkGroup;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@NoArgsConstructor
@Table(name = "KRCDT_DAY_TS_SUP")
public class KrcdtDayOuenTimeSheet extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 主キー */
	@EmbeddedId
	public KrcdtDayOuenTimePK pk;

	/** 勤務先会社ID */
	@Column(name = "SUP_CID")
	public String cid;

	/** 職場ID */
	@Column(name = "WORKPLACE_ID")
	public String workplaceId;		
	
	/** 勤務場所コード */
	@Column(name = "WORK_LOCATION_CD")
	public String workLocationCode;

	/** 勤務枠No */
	@Column(name = "WORK_NO")
	public int workNo;

	/** 開始時刻 */
	@Column(name = "START_TIME")
	public Integer startTime;

	/** 開始の時刻変更手段 */
	@Column(name = "START_TIME_CHANGE_WAY")
	public Integer startTimeChangeWay;

	/** 開始の打刻方法 */
	@Column(name = "START_STAMP_METHOD")
	public Integer startStampMethod;

	/** 終了時刻 */
	@Column(name = "END_TIME")
	public Integer endTime;

	/** 終了の時刻変更手段 */
	@Column(name = "END_TIME_CHANGE_WAY")
	public Integer endTimeChangeWay;

	/** 終了の打刻方法 */
	@Column(name = "END_STAMP_METHOD")
	public Integer endStampMethod;

	/** 作業CD1 */
	@Column(name = "WORK_CD1")
	public String workCd1;

	/** 作業CD2 */
	@Column(name = "WORK_CD2")
	public String workCd2;

	/** 作業CD3 */
	@Column(name = "WORK_CD3")
	public String workCd3;

	/** 作業CD4 */
	@Column(name = "WORK_CD4")
	public String workCd4;

	/** 作業CD5 */
	@Column(name = "WORK_CD5")
	public String workCd5;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	public OuenWorkTimeSheetOfDaily domain() {
		return OuenWorkTimeSheetOfDaily.create(
				pk.sid, pk.ymd, 
				OuenWorkTimeSheetOfDailyAttendance.create(
						pk.ouenNo, 
						WorkContent.create(
								cid, 
								WorkplaceOfWorkEachOuen.create(workplaceId, new WorkLocationCD(workLocationCode)), 
								Optional.ofNullable(workCd1 == null ? null : 
									WorkGroup.create(workCd1, workCd2, workCd3, workCd4, workCd5))), 
						TimeSheetOfAttendanceEachOuenSheet.create(
								new WorkNo(workNo), 
								Optional.ofNullable(startTime == null ? null : 
									new WorkTimeInformation(
											new ReasonTimeChange(
													EnumAdaptor.valueOf(startTimeChangeWay, TimeChangeMeans.class), 
													startStampMethod == null ? null : EnumAdaptor.valueOf(startStampMethod, EngravingMethod.class)), 
											startTime == null ? null : new TimeWithDayAttr(startTime))), 
								Optional.ofNullable(endTime == null ? null : 
									new WorkTimeInformation(
											new ReasonTimeChange(
													EnumAdaptor.valueOf(endTimeChangeWay, TimeChangeMeans.class), 
													endStampMethod == null ? null : EnumAdaptor.valueOf(endStampMethod, EngravingMethod.class)), 
											endTime == null ? null : new TimeWithDayAttr(endTime))))));
	}
	
	public static KrcdtDayOuenTimeSheet convert(OuenWorkTimeSheetOfDaily domain) {
		KrcdtDayOuenTimeSheet entity = new KrcdtDayOuenTimeSheet();
		
		entity.pk = new KrcdtDayOuenTimePK(domain.getEmpId(), 
				domain.getYmd(), domain.getOuenTimeSheet().getWorkNo());
		
		entity.cid = domain.getOuenTimeSheet().getWorkContent().getCompanyId();
		entity.workplaceId = domain.getOuenTimeSheet().getWorkContent().getWorkplace().getWorkplaceId();		
		entity.workLocationCode = domain.getOuenTimeSheet().getWorkContent().getWorkplace().getWorkLocationCD().v();
		
		domain.getOuenTimeSheet().getWorkContent().getWork().ifPresent(work -> {
			entity.workCd1 = work.getWorkCD1().v();
			entity.workCd2 = work.getWorkCD2().map(w -> w.v()).orElse(null);
			entity.workCd3 = work.getWorkCD3().map(w -> w.v()).orElse(null); 
			entity.workCd4 = work.getWorkCD4().map(w -> w.v()).orElse(null);
			entity.workCd5 = work.getWorkCD5().map(w -> w.v()).orElse(null);
		});
		
		entity.workNo = domain.getOuenTimeSheet().getTimeSheet().getWorkNo().v();
		
		domain.getOuenTimeSheet().getTimeSheet().getStart().ifPresent(start -> {
			entity.startTimeChangeWay = start.getReasonTimeChange().getTimeChangeMeans().value;
			entity.startStampMethod = start.getReasonTimeChange().getEngravingMethod().map(c -> c.value).orElse(null);
			entity.startTime = start.getTimeWithDay().map(c -> c.v()).orElse(null); 
		});
		
		domain.getOuenTimeSheet().getTimeSheet().getEnd().ifPresent(end -> {
			entity.endTimeChangeWay = end.getReasonTimeChange().getTimeChangeMeans().value;
			entity.endStampMethod = end.getReasonTimeChange().getEngravingMethod().map(c -> c.value).orElse(null);
			entity.endTime = end.getTimeWithDay().map(c -> c.v()).orElse(null); 
		});
		
		return entity;
	}
}
