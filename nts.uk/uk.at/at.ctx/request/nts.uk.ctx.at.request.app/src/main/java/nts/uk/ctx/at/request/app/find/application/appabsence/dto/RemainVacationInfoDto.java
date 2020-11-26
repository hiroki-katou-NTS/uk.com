package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
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
    private Integer yearRemain;

    // 年休残時間
    private Integer yearHourRemain;

    // 代休残数
    private Integer subHdRemain;

    // 振休残数
    private Integer subVacaRemain;

    // 代休残時間
    private Integer subVacaHourRemain;

    // 代休残時間
    private Integer subHdHourRemain;

    // 60H超休残時間
    private Integer over60HHourRemain;

    // 子看護残数
    private Integer childNursingRemain;

    // 子看護残時間
    private Integer childNursingHourRemain;

    // 介護残数
    private Integer nursingRemain;

    // 介護残時間
    private Integer nirsingHourRemain;

    public static RemainVacationInfoDto fromDomain(RemainVacationInfo remainVacationInfo) {
        return new RemainVacationInfoDto(
                AnualLeaveManagementDto.fromDomain(remainVacationInfo.getAnnualLeaveManagement()),
                AccumulatedRestManagementDto.fromDomain(remainVacationInfo.getAccumulatedRestManagement()),
                SubstituteLeaveManagementDto.fromDomain(remainVacationInfo.getSubstituteLeaveManagement()),
                HolidayManagementDto.fromDomain(remainVacationInfo.getHolidayManagement()),
                Overtime60HManagementDto.fromDomain(remainVacationInfo.getOvertime60hManagement()),
                NursingCareLeaveManagementDto.fromDomain(remainVacationInfo.getNursingCareLeaveManagement()),
                remainVacationInfo.getYearRemain().isPresent() ? remainVacationInfo.getYearRemain().get() : null,
                remainVacationInfo.getYearHourRemain().isPresent() ? remainVacationInfo.getYearHourRemain().get()
                        : null,
                remainVacationInfo.getSubHdRemain().isPresent() ? remainVacationInfo.getSubHdRemain().get() : null,
                remainVacationInfo.getSubVacaRemain().isPresent() ? remainVacationInfo.getSubVacaRemain().get() : null,
                remainVacationInfo.getSubVacaHourRemain().isPresent() ? remainVacationInfo.getSubVacaHourRemain().get()
                        : null,
                remainVacationInfo.getSubHdHourRemain().isPresent() ? remainVacationInfo.getSubHdHourRemain().get()
                        : null,
                remainVacationInfo.getOver60HHourRemain().isPresent() ? remainVacationInfo.getOver60HHourRemain().get()
                        : null,
                remainVacationInfo.getChildNursingRemain().isPresent()
                        ? remainVacationInfo.getChildNursingRemain().get()
                        : null,
                remainVacationInfo.getChildNursingHourRemain().isPresent()
                        ? remainVacationInfo.getChildNursingHourRemain().get()
                        : null,
                remainVacationInfo.getNursingRemain().isPresent() ? remainVacationInfo.getNursingRemain().get() : null,
                remainVacationInfo.getNirsingHourRemain().isPresent() ? remainVacationInfo.getNirsingHourRemain().get()
                        : null);
    }

    public RemainVacationInfo toDomain() {
        return new RemainVacationInfo(annualLeaveManagement.toDomain(), accumulatedRestManagement.toDomain(),
                substituteLeaveManagement.toDomain(), holidayManagement.toDomain(), overtime60hManagement.toDomain(),
                nursingCareLeaveManagement.toDomain(), Optional.ofNullable(yearRemain),
                Optional.ofNullable(yearHourRemain), Optional.ofNullable(subHdRemain),
                Optional.ofNullable(subVacaRemain), Optional.ofNullable(subVacaHourRemain),
                Optional.ofNullable(subHdHourRemain), Optional.ofNullable(over60HHourRemain),
                Optional.ofNullable(childNursingRemain), Optional.ofNullable(childNursingHourRemain),
                Optional.ofNullable(nursingRemain), Optional.ofNullable(nirsingHourRemain));
    }
}
