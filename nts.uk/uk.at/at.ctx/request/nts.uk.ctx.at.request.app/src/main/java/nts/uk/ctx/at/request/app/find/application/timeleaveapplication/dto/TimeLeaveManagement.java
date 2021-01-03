package nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.output.*;
import nts.uk.ctx.at.shared.app.find.worktype.specialholidayframe.SpecialHolidayFrameDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrame;
import nts.uk.shr.com.context.AppContexts;

import java.util.stream.Collectors;

/**
 * 時間休暇管理
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TimeLeaveManagement {
    // 時間年休管理
    private TimeAnnualLeaveMng timeAnnualLeaveMng;

    // 時間代休管理
    private TimeSubstituteLeaveMng timeSubstituteLeaveMng;

    // 60H超休管理
    private Super60HLeaveMng super60HLeaveMng;

    // 子看護介護管理
    private NursingLeaveMng nursingLeaveMng;

    // 時間特別休暇管理
    private TimeSpecialLeaveMng timeSpecialLeaveMng;

    public static TimeVacationManagementOutput setDtaOutput(TimeLeaveManagement dto) {
        return new TimeVacationManagementOutput(
            new SupHolidayManagement(
                EnumAdaptor.valueOf(dto.super60HLeaveMng.getSuper60HLeaveUnit(), TimeDigestiveUnit.class),
                dto.super60HLeaveMng.getSuper60HLeaveMngAtr()
            ),
            new ChildNursingManagement(
                EnumAdaptor.valueOf(dto.nursingLeaveMng.getTimeCareLeaveUnit(), TimeDigestiveUnit.class),
                dto.nursingLeaveMng.getTimeCareLeaveMngAtr(),
                EnumAdaptor.valueOf(dto.nursingLeaveMng.getTimeChildCareLeaveUnit(), TimeDigestiveUnit.class),
                dto.nursingLeaveMng.getTimeChildCareLeaveMngAtr()
            ),
            new TimeAllowanceManagement(
                EnumAdaptor.valueOf(dto.timeSubstituteLeaveMng.getTimeSubstituteLeaveUnit(), TimeDigestiveUnit.class),
                dto.timeSubstituteLeaveMng.getTimeSubstituteLeaveMngAtr()
            ),
            new TimeAnnualLeaveManagement(
                EnumAdaptor.valueOf(dto.timeAnnualLeaveMng.getTimeAnnualLeaveUnit(), TimeDigestiveUnit.class),
                dto.timeAnnualLeaveMng.getTimeAnnualLeaveMngAtr()
            ),
            new TimeSpecialLeaveManagement(
                    EnumAdaptor.valueOf(dto.getTimeSpecialLeaveMng().getTimeSpecialLeaveUnit(), TimeDigestiveUnit.class),
                    dto.getTimeSpecialLeaveMng().getTimeSpecialLeaveMngAtr(),
                    dto.getTimeSpecialLeaveMng().getListSpecialFrame().stream().map(i -> SpecialHolidayFrame.createFromJavaType(
                            AppContexts.user().companyId(),
                            i.getSpecialHdFrameNo(),
                            i.getSpecialHdFrameName(),
                            i.getDeprecateSpecialHd(),
                            i.getTimeMngAtr()
                    )).collect(Collectors.toList())
            )
        );
    }

    public static TimeLeaveManagement fromOutput(TimeVacationManagementOutput output) {
        return new TimeLeaveManagement(
                new TimeAnnualLeaveMng(
                        output.getTimeAnnualLeaveManagement().getTimeAnnualLeaveUnit().value,
                        output.getTimeAnnualLeaveManagement().isTimeAnnualManagement()
                ),
                new TimeSubstituteLeaveMng(
                        output.getTimeAllowanceManagement().getTimeBaseRestingUnit().value,
                        output.getTimeAllowanceManagement().isTimeBaseManagementClass()
                ),
                new Super60HLeaveMng(
                        output.getSupHolidayManagement().getSuper60HDigestion().value,
                        output.getSupHolidayManagement().isOverrest60HManagement()
                ),
                new NursingLeaveMng(
                        output.getChildNursingManagement().getTimeDigestiveUnit().value,
                        output.getChildNursingManagement().isTimeManagementClass(),
                        output.getChildNursingManagement().getTimeChildDigestiveUnit().value,
                        output.getChildNursingManagement().isTimeChildManagementClass()
                ),
                new TimeSpecialLeaveMng(
                        output.getTimeSpecialLeaveMng().getTimeSpecialLeaveUnit().value,
                        output.getTimeSpecialLeaveMng().isTimeSpecialLeaveManagement(),
                        output.getTimeSpecialLeaveMng().getListSpecialFrame().stream().map(SpecialHolidayFrameDto::fromDomain).collect(Collectors.toList())
                )
        );
    }
}
