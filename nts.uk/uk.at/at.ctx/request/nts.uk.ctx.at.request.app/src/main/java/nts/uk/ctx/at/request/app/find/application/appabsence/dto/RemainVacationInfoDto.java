package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.appabsence.service.RemainVacationInfo;

@Data
@AllArgsConstructor
public class RemainVacationInfoDto {

    // 年休管理
    private AnualLeaveManagementDto annualLeaveManagement;

    // 積休管理
    private AccumulatedRestManagementDto accumulatedRestManagement;

    // 代休管理
    private SubstituteLeaveManagementDto substituteLeaveManagement;

    // 振休管理
    private HolidayManagementDto holidayManagement;

    // 60H超休管理
    private Overtime60HManagementDto overtime60hManagement;

    // 介護看護休暇管理
    private NursingCareLeaveManagementDto nursingCareLeaveManagement;

    // 年休残数
    private double yearRemain;

    // 年休残時間
    private Integer yearHourRemain;

    // 代休残数
    private double subHdRemain;

    // 振休残数
    private double subVacaRemain;

    // 代休残時間
    private Integer subVacaHourRemain;

    // 積休残数
    private double remainingHours;

    // 60H超休残時間
    private Integer over60HHourRemain;

    // 子看護残数
    private double childNursingRemain;

    // 子看護残時間
    private Integer childNursingHourRemain;

    // 介護残数
    private double nursingRemain;

    // 介護残時間
    private Integer nirsingHourRemain;
    
    // 付与年月日
    private String grantDate;
    
    // 付与日数
    private double grantDays;

    public static RemainVacationInfoDto fromDomain(RemainVacationInfo remainVacationInfo) {
        return new RemainVacationInfoDto(
                AnualLeaveManagementDto.fromDomain(remainVacationInfo.getAnnualLeaveManagement()),
                AccumulatedRestManagementDto.fromDomain(remainVacationInfo.getAccumulatedRestManagement()),
                SubstituteLeaveManagementDto.fromDomain(remainVacationInfo.getSubstituteLeaveManagement()),
                HolidayManagementDto.fromDomain(remainVacationInfo.getHolidayManagement()),
                Overtime60HManagementDto.fromDomain(remainVacationInfo.getOvertime60hManagement()),
                NursingCareLeaveManagementDto.fromDomain(remainVacationInfo.getNursingCareLeaveManagement()),
                remainVacationInfo.getYearRemain().isPresent() ? remainVacationInfo.getYearRemain().get() : 0d,
                remainVacationInfo.getYearHourRemain().isPresent() ? remainVacationInfo.getYearHourRemain().get()
                        : 0,
                remainVacationInfo.getSubHdRemain().isPresent() ? remainVacationInfo.getSubHdRemain().get() : 0d,
                remainVacationInfo.getSubVacaRemain().isPresent() ? remainVacationInfo.getSubVacaRemain().get() : 0d,
                remainVacationInfo.getSubVacaHourRemain().isPresent() ? remainVacationInfo.getSubVacaHourRemain().get()
                        : 0,
                remainVacationInfo.getRemainingHours().isPresent() ? remainVacationInfo.getRemainingHours().get()
                        : 0d,
                remainVacationInfo.getOver60HHourRemain().isPresent() ? remainVacationInfo.getOver60HHourRemain().get()
                        : 0,
                remainVacationInfo.getChildNursingRemain().isPresent()
                        ? remainVacationInfo.getChildNursingRemain().get()
                        : 0d,
                remainVacationInfo.getChildNursingHourRemain().isPresent()
                        ? remainVacationInfo.getChildNursingHourRemain().get()
                        : 0,
                remainVacationInfo.getNursingRemain().isPresent() ? remainVacationInfo.getNursingRemain().get() : 0d,
                remainVacationInfo.getNirsingHourRemain().isPresent() ? remainVacationInfo.getNirsingHourRemain().get()
                        : 0, 
                remainVacationInfo.getGrantDate().isPresent() ? remainVacationInfo.getGrantDate().get().toString("yyyy/MM/dd") : null, 
                remainVacationInfo.getGrantDays().isPresent() ? remainVacationInfo.getGrantDays().get() : 0);
    }

    public RemainVacationInfo toDomain() {
        return new RemainVacationInfo(annualLeaveManagement.toDomain(), accumulatedRestManagement.toDomain(),
                substituteLeaveManagement.toDomain(), holidayManagement.toDomain(), overtime60hManagement.toDomain(),
                nursingCareLeaveManagement.toDomain(), Optional.ofNullable(yearRemain),
                Optional.ofNullable(yearHourRemain), Optional.ofNullable(subHdRemain),
                Optional.ofNullable(subVacaRemain), Optional.ofNullable(subVacaHourRemain),
                Optional.ofNullable(remainingHours), Optional.ofNullable(over60HHourRemain),
                Optional.ofNullable(childNursingRemain), Optional.ofNullable(childNursingHourRemain),
                Optional.ofNullable(nursingRemain), Optional.ofNullable(nirsingHourRemain), 
                grantDate != null ? Optional.ofNullable(GeneralDate.fromString(grantDate, "yyyy/MM/dd")) : Optional.empty(), 
                Optional.ofNullable(grantDays));
    }
}
