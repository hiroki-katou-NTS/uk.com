package nts.uk.ctx.at.request.infra.entity.application.appabsence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.appabsence.ReflectFreeTimeApp;
import nts.uk.ctx.at.request.dom.application.appabsence.SupplementInfoVacation;
import nts.uk.ctx.at.request.dom.application.appabsence.VacationRequestInfo;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.ApplyforSpecialLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.RelationshipCDPrimitive;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.RelationshipReasonPrimitive;
import nts.uk.ctx.at.request.dom.application.appabsence.apptimedigest.TimeDigestApplication;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author anhnm
 *  休暇申請
 *
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KRQDT_APP_HD")
public class KrqdtAppHd extends ContractUkJpaEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    public KrqdtAppHdPK krqdtAppHdPK;
    
    @Column(name = "HOLIDAY_APP_TYPE")
    public int holidayAppType;
    
    @Column(name = "WORK_TYPE_CD")
    public String workTypeCd;
    
    @Column(name = "WORK_TIME_CD")
    public String workTimeCd;
    
    @Column(name = "WORK_TIME_CHANGE_ATR")
    public int workTimeChangeAtr;
    
    @Column(name = "WORK_TIME_START1")
    public Integer workTimeStart1;
    
    @Column(name = "WORK_TIME_END1")
    public Integer workTimeEnd1;
    
    @Column(name = "WORK_TIME_START2")
    public Integer workTimeStart2;
    
    @Column(name = "WORK_TIME_END2")
    public Integer workTimeEnd2;
    
    @Column(name = "HOUR_OF_SIXTY_OVERTIME")
    public Integer hourOfSixtyOvertime;
    
    @Column(name = "HOUR_OF_CARE")
    public Integer hourOfCare;
    
    @Column(name = "HOUR_OF_CHILD_CARE")
    public Integer hourOfChildCare;
    
    @Column(name = "HOUR_OF_HDCOM")
    public Integer hourOfHdCom;
    
    @Column(name = "FRAME_NO_OF_HDSP")
    public Integer frameNoOfHdsp;
    
    @Column(name = "HOUR_OF_HDSP")
    public Integer hourOfHdsp;
    
    @Column(name = "HOUR_OF_HDPAID")
    public Integer hourOfHdPaid;
    
    @Column(name = "MOURNER_FLG")
    public Integer mournerFlg;
    
    @Column(name = "RELATIONSHIP_CD")
    public String relationshipCD;
    
    @Column(name = "RELATIONSHIP_REASON")
    public String relationshipReason;
    
    @Column(name = "HDCOM_START_DATE")
    public GeneralDate hdComStartDate;
    
    @Column(name = "HDCOM_END_DATE")
    public GeneralDate hdComEndDate;

    @Override
    protected Object getKey() {
        return this.krqdtAppHdPK;
    }

    public static final JpaEntityMapper<KrqdtAppHd> MAPPER = new JpaEntityMapper<KrqdtAppHd>(KrqdtAppHd.class);
    
    /**
     * Convert from entity to domain
     * @return ApplyForLeave domain
     */
    public ApplyForLeave toDomain() {
        
        TimeZoneWithWorkNo timeZone1 = null;
        TimeZoneWithWorkNo timeZone2 = null;
        List<TimeZoneWithWorkNo> workingHours = new ArrayList<TimeZoneWithWorkNo>();
        if (this.workTimeStart1 != null && this.workTimeEnd1 != null) {
            timeZone1 = new TimeZoneWithWorkNo(1, this.workTimeStart1, this.workTimeEnd1);
        }
        if (this.workTimeStart2 != null && this.workTimeEnd2 != null) {
            timeZone2 = new TimeZoneWithWorkNo(2, this.workTimeStart2, this.workTimeEnd2);
        }
        if (timeZone1 != null) {
            workingHours.add(timeZone1);
        }
        if (timeZone2 != null) {
            workingHours.add(timeZone2);
        }
        
        return new ApplyForLeave(
                new ReflectFreeTimeApp(
                        workingHours.isEmpty() ? Optional.empty() : Optional.of(workingHours),
                        Optional.of(new TimeDigestApplication(
                                this.hourOfSixtyOvertime == null ? null : new AttendanceTime(this.hourOfSixtyOvertime), 
                                this.hourOfCare == null ? null : new AttendanceTime(this.hourOfCare), 
                                this.hourOfChildCare == null ? null : new AttendanceTime(this.hourOfChildCare), 
                                this.hourOfHdCom == null ? null : new AttendanceTime(this.hourOfHdCom), 
                                this.hourOfHdsp == null ? null : new AttendanceTime(this.hourOfHdsp), 
                                this.hourOfHdPaid == null ? null : new AttendanceTime(this.hourOfHdPaid), 
                                this.frameNoOfHdsp == null ? Optional.empty() : Optional.of(this.frameNoOfHdsp))),
                        new WorkInformation(
                                this.workTypeCd, 
                                this.workTimeCd),
                        EnumAdaptor.valueOf(this.workTimeChangeAtr, NotUseAtr.class)),
                new VacationRequestInfo(
                        EnumAdaptor.valueOf(this.holidayAppType, HolidayAppType.class),
                        new SupplementInfoVacation(
                        		(!Optional.ofNullable(this.hdComStartDate).isPresent() || !Optional.ofNullable(this.hdComEndDate).isPresent())
                                ? Optional.empty() 
                            	: Optional.of(new DatePeriod(
                                    this.hdComStartDate, 
                                    this.hdComEndDate)),
                            	(this.mournerFlg == null && this.relationshipCD == null && this.relationshipReason == null) ? Optional.empty() :
                                Optional.of(new ApplyforSpecialLeave(
                                        this.mournerFlg != null ? (this.mournerFlg == 1 ? true : false) : false,
                                        this.relationshipCD != null ? Optional.of(new RelationshipCDPrimitive(this.relationshipCD)) : Optional.empty(),
                                        this.relationshipReason != null ? Optional.of(new RelationshipReasonPrimitive(this.relationshipReason)) : Optional.empty())))));
    }
    
    public static KrqdtAppHd fromDomain(ApplyForLeave domain, String CID, String appID) {
        KrqdtAppHd entity = new KrqdtAppHd();
        
        // Primary key
        entity.setKrqdtAppHdPK(new KrqdtAppHdPK(CID, appID));
        
        // ReflectFreeTimeApp
        ReflectFreeTimeApp reflectFreeTimeApp = domain.getReflectFreeTimeApp();
        
        if (reflectFreeTimeApp.getWorkingHours().isPresent()) {
            for (TimeZoneWithWorkNo workHour : reflectFreeTimeApp.getWorkingHours().get()) {
                if (workHour.getWorkNo().v() == 1) {
                    entity.setWorkTimeStart1(workHour.getTimeZone().getStartTime().v());
                    entity.setWorkTimeEnd1(workHour.getTimeZone().getEndTime().v());
                }
                if (workHour.getWorkNo().v() == 2) {
                    entity.setWorkTimeStart2(workHour.getTimeZone().getStartTime().v());
                    entity.setWorkTimeEnd2(workHour.getTimeZone().getEndTime().v());
                }
            }
        }
        
        if (reflectFreeTimeApp.getTimeDegestion().isPresent()) {
            entity.setHourOfSixtyOvertime(reflectFreeTimeApp.getTimeDegestion().get().getOvertime60H() != null ? 
                    reflectFreeTimeApp.getTimeDegestion().get().getOvertime60H().v() : null);
            entity.setHourOfCare(reflectFreeTimeApp.getTimeDegestion().get().getNursingTime() != null ? 
                    reflectFreeTimeApp.getTimeDegestion().get().getNursingTime().v() : null);
            entity.setHourOfChildCare(reflectFreeTimeApp.getTimeDegestion().get().getChildTime() != null ? 
                    reflectFreeTimeApp.getTimeDegestion().get().getChildTime().v() : null);
            entity.setHourOfHdCom(reflectFreeTimeApp.getTimeDegestion().get().getTimeOff() != null ? 
                    reflectFreeTimeApp.getTimeDegestion().get().getTimeOff().v() : null);
            entity.setHourOfHdsp(reflectFreeTimeApp.getTimeDegestion().get().getTimeSpecialVacation() != null ? 
                    reflectFreeTimeApp.getTimeDegestion().get().getTimeSpecialVacation().v() : null);
            entity.setHourOfHdPaid(reflectFreeTimeApp.getTimeDegestion().get().getTimeAnnualLeave() != null ?
                    reflectFreeTimeApp.getTimeDegestion().get().getTimeAnnualLeave().v() : null);
            
            if (reflectFreeTimeApp.getTimeDegestion().get().getSpecialVacationFrameNO().isPresent()) {
                entity.setFrameNoOfHdsp(reflectFreeTimeApp.getTimeDegestion().get().getSpecialVacationFrameNO().isPresent() ? 
                        reflectFreeTimeApp.getTimeDegestion().get().getSpecialVacationFrameNO().get() : null);
            }
        }
        
        entity.setWorkTypeCd(reflectFreeTimeApp.getWorkInfo().getWorkTypeCode().v());
        entity.setWorkTimeCd(reflectFreeTimeApp.getWorkInfo().getWorkTimeCodeNotNull().isPresent() ? 
                reflectFreeTimeApp.getWorkInfo().getWorkTimeCodeNotNull().get().v() : null);
        entity.setWorkTimeChangeAtr(reflectFreeTimeApp.getWorkChangeUse().value);
        
        // VacationRequestInfo
        VacationRequestInfo vacationRequestInfo = domain.getVacationInfo();
        
        entity.setHolidayAppType(vacationRequestInfo.getHolidayApplicationType().value);
        
        if (vacationRequestInfo.getInfo().getDatePeriod().isPresent()) {
            entity.setHdComStartDate(vacationRequestInfo.getInfo().getDatePeriod().get().start());
            entity.setHdComEndDate(vacationRequestInfo.getInfo().getDatePeriod().get().end());
        }
        
        if (vacationRequestInfo.getInfo().getApplyForSpeLeaveOptional().isPresent()) {
            entity.setMournerFlg(vacationRequestInfo.getInfo().getApplyForSpeLeaveOptional().get().isMournerFlag() ? 1 : 0);
            if (vacationRequestInfo.getInfo().getApplyForSpeLeaveOptional().get().getRelationshipCD().isPresent()) {
                entity.setRelationshipCD(vacationRequestInfo.getInfo().getApplyForSpeLeaveOptional().get().getRelationshipCD().get().v());
            }
            if (vacationRequestInfo.getInfo().getApplyForSpeLeaveOptional().get().getRelationshipReason().isPresent()) {
                entity.setRelationshipReason(vacationRequestInfo.getInfo().getApplyForSpeLeaveOptional().get().getRelationshipReason().get().v());
            }
        }
        
        return entity;
    }
}
