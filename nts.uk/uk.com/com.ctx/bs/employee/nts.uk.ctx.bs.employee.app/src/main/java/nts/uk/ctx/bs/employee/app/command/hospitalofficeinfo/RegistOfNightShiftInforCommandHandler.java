package nts.uk.ctx.bs.employee.app.command.hospitalofficeinfo;


import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.clock.ClockHourMinute;
import nts.arc.time.clock.ClockHourMinuteSpan;
import nts.uk.ctx.bs.employee.dom.workplace.group.hospitalofficeinfo.*;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalTime;
import java.util.Optional;

@Stateless
public class RegistOfNightShiftInforCommandHandler extends CommandHandler<RegistOfNightShiftInforCommand> {
    @Inject
    HospitalBusinessOfficeInfoHistoryRepository historyRepository;

    @Override
    protected void handle(CommandHandlerContext<RegistOfNightShiftInforCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        NotUseAtr nightShiftOperationAtr = EnumAdaptor.valueOf(command.getNightShiftOperationAtr(), NotUseAtr.class);
        Optional<ClockHourMinuteSpan> shiftTime = Optional.empty();
        Optional<NursingCareEstablishmentInfo> nursingCareEstInfo = Optional.empty();
        if (command.getClockHourMinuteStart() != null && command.getClockHourMinuteEnd() != null) {

            LocalTime localTimeStart = LocalTime.parse(command.getClockHourMinuteStart());
            LocalTime localTimeEnd = LocalTime.parse(command.getClockHourMinuteEnd());
            ClockHourMinute clockHourMinuteStart = ClockHourMinute.hm(localTimeStart.getHour(), localTimeStart.getMinute());
            ClockHourMinute clockHourMinuteEnd = ClockHourMinute.hm(localTimeEnd.getHour(), localTimeEnd.getMinute());
            ClockHourMinuteSpan clockHourMinuteSpan = ClockHourMinuteSpan.create(clockHourMinuteStart, clockHourMinuteEnd);
            shiftTime = Optional.of(clockHourMinuteSpan);

        }
        NightShiftOperationRule nightShiftOperationRule = new NightShiftOperationRule(
                nightShiftOperationAtr,
                shiftTime
        );
        HospitalBusinessOfficeInfo hospitalBusinessOfficeInfo = new HospitalBusinessOfficeInfo(
                command.getWorkplaceGroupId(),
                command.getHistoryId(),
                nightShiftOperationRule,
                nursingCareEstInfo
        );
        // Get old domain HospitalBusinessOfficeInfo.
        Optional<HospitalBusinessOfficeInfo> officeInfo = historyRepository.get(command.getHistoryId());
        // Get oll domain HospitalBusinessOfficeInfoHistory.
        Optional<HospitalBusinessOfficeInfoHistory> officeInfoHistory = historyRepository
                .getHospitalBusinessOfficeInfoHistory(command.getWorkplaceGroupId());
        if (officeInfoHistory.isPresent()) {
            if (officeInfo.isPresent()) {
                historyRepository.update(hospitalBusinessOfficeInfo, officeInfoHistory.get());
            } else {
                historyRepository.insert(hospitalBusinessOfficeInfo, officeInfoHistory.get());
            }
        }

    }
}
