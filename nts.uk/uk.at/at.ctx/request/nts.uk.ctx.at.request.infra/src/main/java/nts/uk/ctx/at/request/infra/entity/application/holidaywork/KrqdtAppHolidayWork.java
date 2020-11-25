package nts.uk.ctx.at.request.infra.entity.application.holidaywork;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.HolidayMidNightTime;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeShiftNight;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.application.overtime.ReasonDivergence;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceReason;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtAppOvertimeDetail;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtAppOvertimeDetail_Old;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.deviationtime.DiverdenceReasonCode;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 休日出勤申請
 * @author huylq
 *Refactor5
 */
@Entity
@Table(name = "KRQDT_APP_HOLIDAY_WORK")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtAppHolidayWork extends UkJpaEntity implements Serializable{

	private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KrqdtAppHolidayWorkPK krqdtAppHolidayWorkPK;
    
    @Version
	@Column(name="EXCLUS_VER")
	public Long version;
    
    @Column(name = "WORK_TYPE_CD")
	public String workTypeCode;
	
	@Column(name = "WORK_TIME_CD")
	public String workTimeCode;
	
	@Column(name = "WORK_TIME_START1")
	public Integer workTimeStart1;
	
	@Column(name = "WORK_TIME_END1")
	public Integer workTimeEnd1;
	
	@Column(name = "GO_WORK_ATR")
	public Integer goWorkAtr;
	
	@Column(name = "BACK_HOME_ATR")
	public Integer backHomeAtr;
	
	@Column(name = "WORK_TIME_START2")
	public Integer workTimeStart2;
	
	@Column(name = "WORK_TIME_END2")
	public Integer workTimeEnd2;
	
	@Column(name = "DIVERGENCE_TIME_NO")
	public Integer divergenceTimeNo;
	
	@Column(name = "DIVERGENCE_CD")
	public String divergenceCode;
	
	@Column(name = "DIVERGENCE_REASON")
	public String divergenceReason;
	
	@Column(name = "OVERTIME_NIGHT")
	public Integer overtimeNight;
	
	@Column(name = "TOTAL_NIGHT")
	public Integer totalNight;
	
	@Column(name = "LEGAL_HD_NIGHT")
	public Integer legalHdNight;
	
	@Column(name = "NON_LEGAL_HD_NIGHT")
	public Integer nonLegalHdNight;
	
	@Column(name = "NON_LEGAL_PUBLIC_HD_NIGHT")
	public Integer nonLegalPublicHdNight;
	
	@Column(name = "BREAK_TIME_START1")
	public Integer breakTimeStart1;
	
	@Column(name = "BREAK_TIME_END1")
	public Integer breakTimeEnd1;
	
	@Column(name = "BREAK_TIME_START2")
	public Integer breakTimeStart2;
	
	@Column(name = "BREAK_TIME_END2")
	public Integer breakTimeEnd2;
	
	@Column(name = "BREAK_TIME_START3")
	public Integer breakTimeStart3;
	
	@Column(name = "BREAK_TIME_END3")
	public Integer breakTimeEnd3;
	
	@Column(name = "BREAK_TIME_START4")
	public Integer breakTimeStart4;
	
	@Column(name = "BREAK_TIME_END4")
	public Integer breakTimeEnd4;
	
	@Column(name = "BREAK_TIME_START5")
	public Integer breakTimeStart5;
	
	@Column(name = "BREAK_TIME_END5")
	public Integer breakTimeEnd5;
	
	@Column(name = "BREAK_TIME_START6")
	public Integer breakTimeStart6;
	
	@Column(name = "BREAK_TIME_END6")
	public Integer breakTimeEnd6;
	
	@Column(name = "BREAK_TIME_START7")
	public Integer breakTimeStart7;
	
	@Column(name = "BREAK_TIME_END7")
	public Integer breakTimeEnd7;
	
	@Column(name = "BREAK_TIME_START8")
	public Integer breakTimeStart8;
	
	@Column(name = "BREAK_TIME_END8")
	public Integer breakTimeEnd8;
	
	@Column(name = "BREAK_TIME_START9")
	public Integer breakTimeStart9;
	
	@Column(name = "BREAK_TIME_END9")
	public Integer breakTimeEnd9;
	
	@Column(name = "BREAK_TIME_START10")
	public Integer breakTimeStart10;
	
	@Column(name = "BREAK_TIME_END10")
	public Integer breakTimeEnd10;
	
	@OneToOne(targetEntity = KrqdtAppOvertimeDetail.class, mappedBy = "appHolidayWork", cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "KRQDT_APP_OVERTIME_DETAIL")
	public KrqdtAppOvertimeDetail appOvertimeDetail;
    
    @Override
	protected Object getKey() {
		return krqdtAppHolidayWorkPK;
	}
    
    public AppHolidayWork toDomain() {
    	if (getKey() == null) return null;
    	AppHolidayWork appHolidayWork = new AppHolidayWork();
    	
    	if (StringUtils.isNotBlank(workTimeCode) || StringUtils.isNotBlank(workTimeCode)) {
			WorkInformation workInformation = new WorkInformation("", "");
			appHolidayWork.setWorkInformation(workInformation);
			if (StringUtils.isNotBlank(workTypeCode)) {
				workInformation.setWorkTypeCode(workTypeCode);
			}
			if (StringUtils.isNotBlank(workTimeCode)) {
				workInformation.setWorkTimeCode(workTimeCode);
			}
		}
    	
    	ApplicationTime applicationTime = new ApplicationTime();
    	appHolidayWork.setOpReversionReason(Optional.empty());
    	ReasonDivergence reasonDivergence = new ReasonDivergence();
    	if (divergenceTimeNo != null) {
    		reasonDivergence.setDiviationTime(divergenceTimeNo);
		}
		if (divergenceCode != null) {
			DiverdenceReasonCode reasonCode = new DiverdenceReasonCode(divergenceCode);
			reasonDivergence.setReasonCode(reasonCode);
		}
		if (divergenceReason != null) {
			DivergenceReason diReason = new DivergenceReason(divergenceReason);
			reasonDivergence.setReason(diReason);
		}
		List<ReasonDivergence> reasonDissociation;
		if (!reasonDivergence.isNullProp()) {
			reasonDissociation = new ArrayList<ReasonDivergence>();
			reasonDissociation.add(reasonDivergence);
			applicationTime.setReasonDissociation(Optional.of(reasonDissociation));
		}	
		OverTimeShiftNight overTimeShiftNight = new OverTimeShiftNight();
		if (overtimeNight == null 
				&& totalNight == null
				&& legalHdNight == null 
				&& nonLegalHdNight == null 
				&& nonLegalPublicHdNight == null) {
			applicationTime.setOverTimeShiftNight(Optional.empty());
		} else {
			if (overtimeNight != null) {
				overTimeShiftNight.setOverTimeMidNight(new AttendanceTime(overtimeNight));
			}
			if (totalNight != null) {
				overTimeShiftNight.setMidNightOutSide(new AttendanceTime(totalNight));
			}
			List<HolidayMidNightTime> midNightHolidayTimes = new ArrayList<HolidayMidNightTime>();
			if (legalHdNight != null) {
				HolidayMidNightTime holidayMidNightTime = new HolidayMidNightTime();
				holidayMidNightTime.setAttendanceTime(new AttendanceTime(legalHdNight));
				holidayMidNightTime.setLegalClf(StaturoryAtrOfHolidayWork.WithinPrescribedHolidayWork);
				midNightHolidayTimes.add(holidayMidNightTime);
			}
			if (nonLegalHdNight != null) {
				HolidayMidNightTime holidayMidNightTime = new HolidayMidNightTime();
				holidayMidNightTime.setAttendanceTime(new AttendanceTime(nonLegalHdNight));
				holidayMidNightTime.setLegalClf(StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork);
				midNightHolidayTimes.add(holidayMidNightTime);
			}
			if (nonLegalPublicHdNight != null) {
				HolidayMidNightTime holidayMidNightTime = new HolidayMidNightTime();
				holidayMidNightTime.setAttendanceTime(new AttendanceTime(nonLegalPublicHdNight));
				holidayMidNightTime.setLegalClf(StaturoryAtrOfHolidayWork.PublicHolidayWork);
				midNightHolidayTimes.add(holidayMidNightTime);
			}
			overTimeShiftNight.setMidNightHolidayTimes(midNightHolidayTimes);
			applicationTime.setOverTimeShiftNight(Optional.of(overTimeShiftNight));
		}
		appHolidayWork.setApplicationTime(applicationTime);
    	
    	appHolidayWork.setBackHomeAtr(EnumAdaptor.valueOf(backHomeAtr, NotUseAtr.class));
    	appHolidayWork.setGoWorkAtr(EnumAdaptor.valueOf(goWorkAtr, NotUseAtr.class));

		List<TimeZoneWithWorkNo> breakTimeList = new ArrayList<TimeZoneWithWorkNo>();
		if (breakTimeStart1 != null && breakTimeEnd1 != null) {
			TimeZoneWithWorkNo timeZoneWithWorkNo = new TimeZoneWithWorkNo(1, breakTimeStart1, breakTimeEnd1);
			breakTimeList.add(timeZoneWithWorkNo);
			appHolidayWork.setBreakTimeList(Optional.of(breakTimeList));
		}
		if (breakTimeStart2 != null && breakTimeEnd2 != null) {
			TimeZoneWithWorkNo timeZoneWithWorkNo = new TimeZoneWithWorkNo(1, breakTimeStart2, breakTimeEnd2);
			breakTimeList.add(timeZoneWithWorkNo);
			if (!appHolidayWork.getBreakTimeList().isPresent()) {
				appHolidayWork.setBreakTimeList(Optional.of(breakTimeList));				
			}
		}
		if (breakTimeStart3 != null && breakTimeEnd3 != null) {
			TimeZoneWithWorkNo timeZoneWithWorkNo = new TimeZoneWithWorkNo(1, breakTimeStart3, breakTimeEnd3);
			breakTimeList.add(timeZoneWithWorkNo);
			if (!appHolidayWork.getBreakTimeList().isPresent()) {
				appHolidayWork.setBreakTimeList(Optional.of(breakTimeList));				
			}
		}
		if (breakTimeStart4 != null && breakTimeEnd4 != null) {
			TimeZoneWithWorkNo timeZoneWithWorkNo = new TimeZoneWithWorkNo(1, breakTimeStart4, breakTimeEnd4);
			breakTimeList.add(timeZoneWithWorkNo);
			if (!appHolidayWork.getBreakTimeList().isPresent()) {
				appHolidayWork.setBreakTimeList(Optional.of(breakTimeList));				
			}
		}
		if (breakTimeStart5 != null && breakTimeEnd5 != null) {
			TimeZoneWithWorkNo timeZoneWithWorkNo = new TimeZoneWithWorkNo(1, breakTimeStart5, breakTimeEnd5);
			breakTimeList.add(timeZoneWithWorkNo);
			if (!appHolidayWork.getBreakTimeList().isPresent()) {
				appHolidayWork.setBreakTimeList(Optional.of(breakTimeList));				
			}
		}
		if (breakTimeStart6 != null && breakTimeEnd6 != null) {
			TimeZoneWithWorkNo timeZoneWithWorkNo = new TimeZoneWithWorkNo(1, breakTimeStart6, breakTimeEnd6);
			breakTimeList.add(timeZoneWithWorkNo);
			if (!appHolidayWork.getBreakTimeList().isPresent()) {
				appHolidayWork.setBreakTimeList(Optional.of(breakTimeList));				
			}
		}
		if (breakTimeStart7 != null && breakTimeEnd7 != null) {
			TimeZoneWithWorkNo timeZoneWithWorkNo = new TimeZoneWithWorkNo(1, breakTimeStart7, breakTimeEnd7);
			breakTimeList.add(timeZoneWithWorkNo);
			if (!appHolidayWork.getBreakTimeList().isPresent()) {
				appHolidayWork.setBreakTimeList(Optional.of(breakTimeList));				
			}
		}
		if (breakTimeStart8 != null && breakTimeEnd8 != null) {
			TimeZoneWithWorkNo timeZoneWithWorkNo = new TimeZoneWithWorkNo(1, breakTimeStart8, breakTimeEnd8);
			breakTimeList.add(timeZoneWithWorkNo);
			if (!appHolidayWork.getBreakTimeList().isPresent()) {
				appHolidayWork.setBreakTimeList(Optional.of(breakTimeList));				
			}
		}
		if (breakTimeStart9 != null && breakTimeEnd9 != null) {
			TimeZoneWithWorkNo timeZoneWithWorkNo = new TimeZoneWithWorkNo(1, breakTimeStart9, breakTimeEnd9);
			breakTimeList.add(timeZoneWithWorkNo);
			if (!appHolidayWork.getBreakTimeList().isPresent()) {
				appHolidayWork.setBreakTimeList(Optional.of(breakTimeList));				
			}
		}
		if (breakTimeStart10 != null && breakTimeEnd10 != null) {
			TimeZoneWithWorkNo timeZoneWithWorkNo = new TimeZoneWithWorkNo(1, breakTimeStart10, breakTimeEnd10);
			breakTimeList.add(timeZoneWithWorkNo);
			if (!appHolidayWork.getBreakTimeList().isPresent()) {
				appHolidayWork.setBreakTimeList(Optional.of(breakTimeList));				
			}
		}
		
		List<TimeZoneWithWorkNo> workingTimeList = new ArrayList<TimeZoneWithWorkNo>();
		if (workTimeStart1 != null && workTimeEnd1 != null) {
			TimeZoneWithWorkNo timeZoneWithWorkNo = new TimeZoneWithWorkNo(1, workTimeStart1, workTimeEnd1);
			workingTimeList.add(timeZoneWithWorkNo);
			appHolidayWork.setWorkingTimeList(Optional.of(workingTimeList));
		}
		if (workTimeStart2 != null && workTimeEnd2 != null) {
			TimeZoneWithWorkNo timeZoneWithWorkNo = new TimeZoneWithWorkNo(2, workTimeStart2, workTimeEnd2);
			workingTimeList.add(timeZoneWithWorkNo);		
		}
		
		if (appOvertimeDetail != null) {
			appHolidayWork.setAppOvertimeDetail(Optional.of(appOvertimeDetail.toDomain()));
		}
		//huytodo Input
		
    	return appHolidayWork;
    }
}
