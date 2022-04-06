package nts.uk.ctx.at.record.infra.entity.daily.ouen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeSheetOfDaily;
import nts.uk.ctx.at.record.infra.entity.daily.timezone.KrcdtDayTsSupSupplInfo;
import nts.uk.ctx.at.record.infra.entity.daily.timezone.KrcdtDayTsSupSupplInfoPk;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.EngravingMethod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.ChoiceCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SuppInfoCommentItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SuppInfoNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SuppInfoNumItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SuppInfoSelectionItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SuppInfoTimeItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SuppNumValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SupportFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.TimeSheetOfAttendanceEachOuenSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.WorkContent;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.WorkSuppComment;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.WorkSuppInfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.record.WorkplaceOfWorkEachOuen;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkGroup;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.shr.infra.data.entity.ContractCompanyUkJpaEntity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "KRCDT_DAY_TS_SUP")
public class KrcdtDayOuenTimeSheet extends ContractCompanyUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 主キー */
	@EmbeddedId
	public KrcdtDayOuenTimePK pk;

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
	
	@OneToOne(mappedBy = "krcdtDayOuenTimeSheet", cascade = CascadeType.ALL, orphanRemoval = true)
	public KrcdtDayTsSupSupplInfo krcdtDayTsSupSupplInfo;
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static List<KrcdtDayOuenTimeSheet> convert(OuenWorkTimeSheetOfDaily domain) {
		
		List<KrcdtDayOuenTimeSheet> rs = new ArrayList<KrcdtDayOuenTimeSheet>();
		
		for (OuenWorkTimeSheetOfDailyAttendance oTimeSheetAtt : domain.getOuenTimeSheet()) {
			KrcdtDayOuenTimeSheet entity = new KrcdtDayOuenTimeSheet();
			
			entity.pk = new KrcdtDayOuenTimePK(domain.getEmpId(), domain.getYmd(), oTimeSheetAtt.getWorkNo().v());
			
			entity.workplaceId = oTimeSheetAtt.getWorkContent().getWorkplace() == null ? "" : oTimeSheetAtt.getWorkContent().getWorkplace().getWorkplaceId().v();		
			entity.workLocationCode = oTimeSheetAtt.getWorkContent().getWorkplace() == null ? null : !oTimeSheetAtt.getWorkContent().getWorkplace().getWorkLocationCD().isPresent() ? null 
					: oTimeSheetAtt.getWorkContent().getWorkplace().getWorkLocationCD().get().v();
			
			oTimeSheetAtt.getWorkContent().getWork().ifPresent(work -> {
				entity.workCd1 = work.getWorkCD1().v() == "" ? null : work.getWorkCD1().v();
				entity.workCd2 = work.getWorkCD2().map(w -> w.v()).orElse(null);
				entity.workCd3 = work.getWorkCD3().map(w -> w.v()).orElse(null); 
				entity.workCd4 = work.getWorkCD4().map(w -> w.v()).orElse(null);
				entity.workCd5 = work.getWorkCD5().map(w -> w.v()).orElse(null);
			});
			
			entity.workNo = oTimeSheetAtt.getTimeSheet().getWorkNo().v();
			
			oTimeSheetAtt.getTimeSheet().getStart().ifPresent(start -> {
				entity.startTimeChangeWay = null;
				entity.startStampMethod = null;
				if(start.getReasonTimeChange() != null) {
					if(start.getReasonTimeChange().getTimeChangeMeans() != null) {
						entity.startTimeChangeWay = start.getReasonTimeChange().getTimeChangeMeans().value;
					}
					entity.startStampMethod = start.getReasonTimeChange().getEngravingMethod().map(c -> c.value).orElse(null);
				} 
				
				entity.startTime = start.getTimeWithDay().map(c -> c.v()).orElse(null); 
			});
			
			oTimeSheetAtt.getTimeSheet().getEnd().ifPresent(end -> {
				entity.endTimeChangeWay = null;
				entity.endStampMethod = null;
				if(end.getReasonTimeChange() != null) {
					if(end.getReasonTimeChange().getTimeChangeMeans() != null) {
						entity.endTimeChangeWay = end.getReasonTimeChange().getTimeChangeMeans().value;
					}
					entity.endStampMethod = end.getReasonTimeChange().getEngravingMethod().map(c -> c.value).orElse(null);
				}
				
				entity.endTime = end.getTimeWithDay().map(c -> c.v()).orElse(null); 
			});
			
			oTimeSheetAtt.getWorkContent().getWorkSuppInfo().ifPresent(ws -> {
				entity.krcdtDayTsSupSupplInfo = mappingSupInfo(ws, domain.getEmpId(), domain.getYmd(),oTimeSheetAtt.getWorkNo().v());
			});
			
			rs.add(entity);
		}
		
		return rs;
	}

	private static KrcdtDayTsSupSupplInfo mappingSupInfo(WorkSuppInfo ws, String sID, GeneralDate YMD, Integer workNo) {
		KrcdtDayTsSupSupplInfo supInfo = new KrcdtDayTsSupSupplInfo();

		supInfo.pk = new KrcdtDayTsSupSupplInfoPk(sID, YMD, workNo);
		ws.getSuppInfoTimeItems().forEach(ti -> {
			if (ti.getSuppInfoNo().v() == 1) { supInfo.supplInfoTime1 =  ti.getAttTime() == null ? null : ti.getAttTime().v();}
			if (ti.getSuppInfoNo().v() == 2) { supInfo.supplInfoTime2 =  ti.getAttTime() == null ? null : ti.getAttTime().v();}
			if (ti.getSuppInfoNo().v() == 3) { supInfo.supplInfoTime3 =  ti.getAttTime() == null ? null : ti.getAttTime().v();}
			if (ti.getSuppInfoNo().v() == 4) { supInfo.supplInfoTime4 =  ti.getAttTime() == null ? null : ti.getAttTime().v();}
			if (ti.getSuppInfoNo().v() == 5) { supInfo.supplInfoTime5 =  ti.getAttTime() == null ? null : ti.getAttTime().v();}
		});
		
		ws.getSuppInfoNumItems().forEach(ni -> {
			if (ni.getSuppInfoNo().v() == 1) { supInfo.supplInfoNumber1 =  ni.getSuppNumValue() == null ? null : ni.getSuppNumValue().v();}
			if (ni.getSuppInfoNo().v() == 2) { supInfo.supplInfoNumber2 =  ni.getSuppNumValue() == null ? null : ni.getSuppNumValue().v();}
			if (ni.getSuppInfoNo().v() == 3) { supInfo.supplInfoNumber3 =  ni.getSuppNumValue() == null ? null : ni.getSuppNumValue().v();}
			if (ni.getSuppInfoNo().v() == 4) { supInfo.supplInfoNumber4 =  ni.getSuppNumValue() == null ? null : ni.getSuppNumValue().v();}
			if (ni.getSuppInfoNo().v() == 5) { supInfo.supplInfoNumber5 =  ni.getSuppNumValue() == null ? null : ni.getSuppNumValue().v();}
		});
		
		ws.getSuppInfoCommentItems().forEach(ci -> {
			if (ci.getSuppInfoNo().v() == 1) { supInfo.supplInfoComment1 =  ci.getWorkSuppComment() == null ? null : ci.getWorkSuppComment().v();}
			if (ci.getSuppInfoNo().v() == 2) { supInfo.supplInfoComment2 =  ci.getWorkSuppComment() == null ? null : ci.getWorkSuppComment().v();}
			if (ci.getSuppInfoNo().v() == 3) { supInfo.supplInfoComment3 =  ci.getWorkSuppComment() == null ? null : ci.getWorkSuppComment().v();}
			if (ci.getSuppInfoNo().v() == 4) { supInfo.supplInfoComment4 =  ci.getWorkSuppComment() == null ? null : ci.getWorkSuppComment().v();}
			if (ci.getSuppInfoNo().v() == 5) { supInfo.supplInfoComment5 =  ci.getWorkSuppComment() == null ? null : ci.getWorkSuppComment().v();}
		});
		
		ws.getSuppInfoSelectionItems().forEach(si -> {
			if (si.getSuppInfoSelectionNo().v() == 1) { supInfo.supplInfoCode1 =  si.getChoiceCode() == null ? null : si.getChoiceCode().v();}
			if (si.getSuppInfoSelectionNo().v() == 2) { supInfo.supplInfoCode2 =  si.getChoiceCode() == null ? null : si.getChoiceCode().v();}
			if (si.getSuppInfoSelectionNo().v() == 3) { supInfo.supplInfoCode3 =  si.getChoiceCode() == null ? null : si.getChoiceCode().v();}
			if (si.getSuppInfoSelectionNo().v() == 4) { supInfo.supplInfoCode4 =  si.getChoiceCode() == null ? null : si.getChoiceCode().v();}
			if (si.getSuppInfoSelectionNo().v() == 5) { supInfo.supplInfoCode5 =  si.getChoiceCode() == null ? null : si.getChoiceCode().v();}
		});
		return supInfo;
	}
	
	public OuenWorkTimeSheetOfDailyAttendance domain(Optional<KrcdtDayTsSupSupplInfo> krcdtDayTsSupSupplInfo) {
		
		return OuenWorkTimeSheetOfDailyAttendance.create(
				SupportFrameNo.of(this.pk.ouenNo), 
				WorkContent.create(
						WorkplaceOfWorkEachOuen.create(new WorkplaceId(this.workplaceId), new WorkLocationCD(this.workLocationCode)),
						(this.workCd1 == null && this.workCd2 == null && this.workCd3 == null && this.workCd4 == null && this.workCd5 == null) ? Optional.empty() :
						Optional.of(WorkGroup.create(this.workCd1, this.workCd2, this.workCd3, this.workCd4, this.workCd5)),
						krcdtDayTsSupSupplInfo.map(si -> si.domain())), 
				TimeSheetOfAttendanceEachOuenSheet.create(
						new WorkNo(this.workNo),
						createTimeInfo(this.startTimeChangeWay, this.startStampMethod, this.startTime),
						createTimeInfo(this.endTimeChangeWay, this.endStampMethod, this.endTime)), 
				Optional.empty());
	}
	
	private Optional<WorkTimeInformation> createTimeInfo(Integer timeChangeWay, Integer stampMethod, Integer time) {
		
		if (timeChangeWay == null && stampMethod == null && time == null) return Optional.empty();
		
		return Optional.of(new WorkTimeInformation(
				new ReasonTimeChange(
						timeChangeWay == null ? TimeChangeMeans.AUTOMATIC_SET : EnumAdaptor.valueOf(timeChangeWay, TimeChangeMeans.class), 
						stampMethod == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(stampMethod, EngravingMethod.class))), 
				time == null ? null : new TimeWithDayAttr(time)));
	}
}
