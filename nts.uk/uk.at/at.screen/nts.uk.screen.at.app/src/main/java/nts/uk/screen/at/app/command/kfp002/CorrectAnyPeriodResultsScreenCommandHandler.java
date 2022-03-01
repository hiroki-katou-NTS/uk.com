package nts.uk.screen.at.app.command.kfp002;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.anyperiod.AnyPeriodCorrectionLogRegisterService;
import nts.uk.ctx.at.shared.dom.scherec.anyperiod.attendancetime.converter.AnyPeriodRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.AnyPeriodActualResultCorrectionDetail;
import nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.AnyPeriodResultRegisterAndStateEditService;
import nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.AnyPeriodResultRegistrationDetail;
import nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.CalculatedItemDetail;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriodRepository;
import nts.uk.screen.at.app.kdw013.a.ItemValueCommand;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 任意期間別実績を修正する
 */
@Stateless
public class CorrectAnyPeriodResultsScreenCommandHandler extends CommandHandlerWithResult<CorrectAnyPeriodResultsScreenCommand, List<CalculatedItemDetail>> {
    @Inject
    private AnyPeriodResultRegisterAndStateEditService registerDomainService;

    @Inject
    private AttendanceTimeOfAnyPeriodRepository attendanceRepo;

    @Inject
    private AnyPeriodCorrectionLogRegisterService correctionLogService;

    @Inject
    private AttendanceItemConvertFactory converterFactory;

    @Override
    protected List<CalculatedItemDetail> handle(CommandHandlerContext<CorrectAnyPeriodResultsScreenCommand> commandHandlerContext) {
        String frameCode = commandHandlerContext.getCommand().getFrameCode();
        Map<String, List<ItemValueCommand>> itemsByEmployee = commandHandlerContext.getCommand().getItems();

        List<AtomTask> tasks = new ArrayList<>();
        List<AnyPeriodActualResultCorrectionDetail> correctionDetails = new ArrayList<>();
        List<CalculatedItemDetail> calculatedDetails = new ArrayList<>();
        AnyPeriodRecordToAttendanceItemConverter converter = converterFactory.createOptionalItemConverter();

        itemsByEmployee.forEach((employeeId, items) -> {
            AnyPeriodResultRegistrationDetail detail = registerDomainService.register(
                    attendanceTime -> attendanceRepo.persistAndUpdate(attendanceTime),
                    frameCode,
                    AppContexts.user().employeeId(),
                    employeeId,
                    items.stream().map(ItemValueCommand::toDomain).collect(Collectors.toList())
            );
            tasks.addAll(detail.getProcesses());
            correctionDetails.add(detail.getCorrectionDetail());

            calculatedDetails.add(detail.getCorrectionDetail().getCalculatedItems(converter));
        });

        transaction.execute(AtomTask.bundle(tasks));

        correctionLogService.register(
                (frameCode1, employeeId) -> attendanceRepo.find(employeeId, frameCode1),
                AppContexts.user().companyId(),
                correctionDetails
        );

        return calculatedDetails;
    }
}
