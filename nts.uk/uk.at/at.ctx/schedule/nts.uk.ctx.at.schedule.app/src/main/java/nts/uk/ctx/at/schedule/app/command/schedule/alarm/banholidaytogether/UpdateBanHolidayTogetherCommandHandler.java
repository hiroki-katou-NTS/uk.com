package nts.uk.ctx.at.schedule.app.command.schedule.alarm.banholidaytogether;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether.*;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationCode;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.BusinessDaysCalendarType;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.ReferenceCalendar;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.ReferenceCalendarClass;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.ReferenceCalendarWorkplace;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * 同日休日禁止を更新する
 */
@Stateless
public class UpdateBanHolidayTogetherCommandHandler extends CommandHandler<UpdateBanHolidayTogetherDto> {
    @Inject
    private BanHolidayTogetherRepository banHolidayTogetherRepo;

    @Override
    protected void handle(CommandHandlerContext<UpdateBanHolidayTogetherDto> context) {
        UpdateBanHolidayTogetherDto command = context.getCommand();

        String companyId = AppContexts.user().companyId();

        TargetOrgIdenInfor targeOrg = new TargetOrgIdenInfor(
                EnumAdaptor.valueOf(command.getUnit(), TargetOrganizationUnit.class),
                Optional.ofNullable(command.getWorkplaceId()),
                Optional.ofNullable(command.getWorkplaceGroupId())
        );

        BanHolidayTogetherCode banHolidayCode = new BanHolidayTogetherCode(command.getBanHolidayTogetherCode());

        Optional<BanHolidayTogether> banHdTogether = banHolidayTogetherRepo.get(companyId, targeOrg, banHolidayCode);

        if (banHdTogether.isPresent()) {
            BanHolidayTogetherName banHolidayName = new BanHolidayTogetherName(command.getBanHolidayTogetherName());

            Optional<ReferenceCalendar> workDayReference = Optional.ofNullable(null);
            if (command.getCheckDayReference()) {
                BusinessDaysCalendarType selectedWorkDayReference = EnumAdaptor.valueOf(command.getSelectedWorkDayReference(), BusinessDaysCalendarType.class);

                if (selectedWorkDayReference == BusinessDaysCalendarType.CLASSSICATION) {
                    ReferenceCalendarClass referenceCalendarClass = new ReferenceCalendarClass(new ClassificationCode(command.getClassificationOrWorkplaceCode()));
                    workDayReference = Optional.of(referenceCalendarClass);
                }
                if (selectedWorkDayReference == BusinessDaysCalendarType.WORKPLACE) {
                    ReferenceCalendarWorkplace referenceCalendarWorkplace = new ReferenceCalendarWorkplace(command.getWorkplaceId());
                    workDayReference = Optional.of(referenceCalendarWorkplace);
                }
            }

            MinNumberEmployeeTogether minNumberOfEmployeeToWork = EnumAdaptor.valueOf(command.getMinNumberOfEmployeeToWork(), MinNumberEmployeeTogether.class);

            BanHolidayTogether banHdTogetherUpdate = BanHolidayTogether.create(
                    targeOrg,
                    banHolidayCode,
                    banHolidayName,
                    workDayReference,
                    minNumberOfEmployeeToWork,
                    command.getEmpsCanNotSameHolidays()
            );

            banHolidayTogetherRepo.update(companyId, banHdTogetherUpdate);
        }
    }
}
