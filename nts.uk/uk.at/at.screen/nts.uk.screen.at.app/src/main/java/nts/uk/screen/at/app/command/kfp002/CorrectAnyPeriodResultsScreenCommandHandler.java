package nts.uk.screen.at.app.command.kfp002;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.anyperiod.AnyPeriodCorrectionLogRegisterService;
import nts.uk.ctx.at.shared.dom.scherec.anyperiod.attendancetime.converter.AnyPeriodRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.AnyPeriodActualResultCorrectionDetail;
import nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.AnyPeriodResultRegisterAndStateEditService;
import nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.AnyPeriodResultRegistrationDetail;
import nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.CalculatedItemDetail;
import nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.editstate.AnyPeriodCorrectionEditingState;
import nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.editstate.AnyPeriodEditingStateRepository;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriodRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AtItemNameAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.TypeOfItemImport;
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
    private AttendanceTimeOfAnyPeriodRepository attendanceRepo;

    @Inject
    private AttendanceItemConvertFactory converterFactory;

    @Inject
    private AtItemNameAdapter atItemNameAdapter;

    @Inject
    private AnyPeriodEditingStateRepository anyPeriodEditingStateRepo;

    @Override
    protected List<CalculatedItemDetail> handle(CommandHandlerContext<CorrectAnyPeriodResultsScreenCommand> commandHandlerContext) {
        String frameCode = commandHandlerContext.getCommand().getFrameCode();
        Map<String, List<ItemValueCommand>> itemsByEmployee = commandHandlerContext.getCommand().getItems();

        List<AtomTask> tasks = new ArrayList<>();
        List<AnyPeriodActualResultCorrectionDetail> correctionDetails = new ArrayList<>();
        List<CalculatedItemDetail> calculatedDetails = new ArrayList<>();
        AnyPeriodRecordToAttendanceItemConverter converter = converterFactory.createOptionalItemConverter();

        itemsByEmployee.forEach((employeeId, items) -> {
            AnyPeriodResultRegistrationDetail detail = AnyPeriodResultRegisterAndStateEditService.register(
                    new AnyPeriodResultRegisterAndStateEditService.Require() {
                        @Override
                        public void persist(AnyPeriodCorrectionEditingState state) {
                            anyPeriodEditingStateRepo.persist(state);
                        }
                        @Override
                        public Optional<AttendanceTimeOfAnyPeriod> find(String employeeId, String frameCode) {
                            return attendanceRepo.find(employeeId, frameCode);
                        }
                        @Override
                        public AnyPeriodRecordToAttendanceItemConverter createOptionalItemConverter() {
                            return converter;
                        }
                        @Override
                        public void update(AttendanceTimeOfAnyPeriod attendanceTime) {
                            attendanceRepo.persistAndUpdate(attendanceTime);
                        }
                    },
                    frameCode,
                    AppContexts.user().employeeId(),
                    employeeId,
                    items.stream().map(ItemValueCommand::toDomain).collect(Collectors.toList())
            );
            tasks.addAll(detail.getProcesses());
            correctionDetails.add(detail.getCorrectionDetail());

            calculatedDetails.add(detail.getCorrectionDetail().getCalculatedItems(converter));
        });

        AnyPeriodCorrectionLogRegisterService.register(
                new AnyPeriodCorrectionLogRegisterService.Require() {
                    @Override
                    public List<AttItemName> getNameOfAttendanceItem(List<Integer> attendanceItemIds, TypeOfItemImport type) {
                        return atItemNameAdapter.getNameOfAttendanceItem(attendanceItemIds, type);
                    }
                    @Override
                    public Optional<AttendanceTimeOfAnyPeriod> find(String frameCode, String employeeId) {
                        return attendanceRepo.find(employeeId, frameCode);
                    }
                    @Override
                    public AnyPeriodRecordToAttendanceItemConverter createOptionalItemConverter() {
                        return converter;
                    }
                },
                AppContexts.user().companyId(),
                correctionDetails
        );

        transaction.execute(AtomTask.bundle(tasks));

        return calculatedDetails;
    }
}
