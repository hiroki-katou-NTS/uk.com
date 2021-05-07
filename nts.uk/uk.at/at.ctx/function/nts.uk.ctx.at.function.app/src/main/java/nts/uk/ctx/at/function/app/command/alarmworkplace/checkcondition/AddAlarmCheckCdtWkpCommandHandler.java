package nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.AlarmCheckCdtWkpCtgRepository;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.AlarmCheckCdtWorkplaceCategory;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.ExtractionCondition;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.WorkplaceCategory;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.applicationapproval.AlarmAppApprovalCheckCdt;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.basic.AlarmMasterBasicCheckCdt;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.daily.AlarmMasterDailyCheckCdt;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.monthly.AlarmMonthlyCheckCdt;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.schedule.AlarmScheduleCheckCdt;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.workplace.AlarmMasterWkpCheckCdt;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.FixedExtractionAppapvCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.FixedExtractionAppapvConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.FixedExtractionAppapvItemsRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.BasicFixedExtractionCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.BasicFixedExtractionConditionRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.BasicFixedExtractionItemRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.FixedExtractionDayCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.FixedExtractionDayConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.FixedExtractionDayItemsRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.*;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.*;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.AlarmFixedExtractionCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.AlarmFixedExtractionConditionRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.AlarmFixedExtractionItemRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class AddAlarmCheckCdtWkpCommandHandler extends CommandHandler<AddAlarmCheckCdtWkpCommand> {

    @Inject
    private AlarmCheckCdtWkpCtgRepository alarmCheckCdtWkpCtgRepo;

    @Inject
    private BasicFixedExtractionItemRepository basicFixedExtractionItemRepo;

    @Inject
    private BasicFixedExtractionConditionRepository basicFixedExtractionConditionRepo;

    @Inject
    private AlarmFixedExtractionItemRepository alarmFixedExtractionItemRepo;

    @Inject
    private AlarmFixedExtractionConditionRepository alarmFixedExtractionConditionRepo;

    @Inject
    private FixedExtractionDayConRepository fixedExtractionDayConRepo;

    @Inject
    private FixedExtractionDayItemsRepository fixedExtractionDayItemsRepo;

    @Inject
    private FixedExtractionAppapvConRepository fixedExtractionAppapvConRepos;

    @Inject
    private FixedExtractionAppapvItemsRepository fixedExtractionAppapvItemsRepo;

    @Inject
    private FixedExtractionScheduleConRepository fixedExtractionScheduleConRepo;

    @Inject
    private FixedExtractionScheduleItemsRepository fixedExtractionScheduleItemsRepo;

    @Inject
    private ExtractionScheduleConRepository extractionScheduleConRepo;

    @Inject
    private FixedExtractionMonthlyItemsRepository fixedExtractionMonthlyItemsRepo;

    @Inject
    private FixedExtractionMonthlyConRepository fixedExtractionMonthlyConRepo;

    @Inject
    private ExtractionMonthlyConRepository extractionMonthlyConRepo;

    @Override
    protected void handle(CommandHandlerContext<AddAlarmCheckCdtWkpCommand> context) {
        AddAlarmCheckCdtWkpCommand command = context.getCommand();
        List<String> fixedIds = new ArrayList<>();
        List<String> optionalIds = new ArrayList<>();
        ExtractionCondition condition = null;

        Optional<AlarmCheckCdtWorkplaceCategory> checkExist = alarmCheckCdtWkpCtgRepo
                .getByID(command.getAlarmCheck().getCategory(), command.getAlarmCheck().getCode());
        if (checkExist.isPresent()) {
            throw new BusinessException("Msg_3");
        }

        WorkplaceCategory category = EnumAdaptor.valueOf(command.getAlarmCheck().getCategory(), WorkplaceCategory.class);
        switch (category) {
            case MASTER_CHECK_BASIC: {
                List<BasicFixedExtractionCondition> conItems = command
                        .getAlarmCheckCon()
                        .stream()
                        .map(i -> new BasicFixedExtractionCondition(
                                UUID.randomUUID().toString(),
                                i.getNo(),
                                i.isCheck(),
                                i.getMessage()
                        ))
                        .collect(Collectors.toList());
                condition = new AlarmMasterBasicCheckCdt(conItems.stream().map(BasicFixedExtractionCondition::getId).collect(Collectors.toList()));
                basicFixedExtractionConditionRepo.registerAll(conItems);
                break;
            }
            // マスタチェック(職場)
            case MASTER_CHECK_WORKPLACE: {
                List<AlarmFixedExtractionCondition> conItems = command
                        .getAlarmCheckCon()
                        .stream()
                        .map(i -> new AlarmFixedExtractionCondition(
                                UUID.randomUUID().toString(),
                                i.getNo(),
                                i.isCheck(),
                                i.getMessage()
                        ))
                        .collect(Collectors.toList());
                alarmFixedExtractionConditionRepo.register(conItems);
                condition = new AlarmMasterWkpCheckCdt(conItems.stream().map(AlarmFixedExtractionCondition::getId).collect(Collectors.toList()));
                break;
            }
            // マスタチェック(日次)
            case MASTER_CHECK_DAILY: {
                List<FixedExtractionDayCon> conItems = command
                        .getAlarmCheckCon()
                        .stream()
                        .map(i -> FixedExtractionDayCon.create(
                                UUID.randomUUID().toString(),
                                i.getNo(),
                                i.getMessage(),
                                i.isCheck()
                        ))
                        .collect(Collectors.toList());
                condition = new AlarmMasterDailyCheckCdt(conItems.stream().map(FixedExtractionDayCon::getErrorAlarmWorkplaceId).collect(Collectors.toList()));
                fixedExtractionDayConRepo.register(conItems);
                break;
            }
            // スケジュール／日次
            case SCHEDULE_DAILY: {
                List<FixedExtractionScheduleCon> conItems = command
                        .getAlarmCheckCon()
                        .stream()
                        .map(i -> FixedExtractionScheduleCon.create(
                                UUID.randomUUID().toString(),
                                i.getNo(),
                                i.isCheck(),
                                i.getMessage()
                        ))
                        .collect(Collectors.toList());
                List<ExtractionScheduleCon> opItems = command.toDomainSchedule();
                condition = new AlarmScheduleCheckCdt(
                        conItems.stream().map(FixedExtractionScheduleCon::getErrorAlarmWorkplaceId).collect(Collectors.toList()),
                        opItems.stream().map(ExtractionScheduleCon::getErrorAlarmWorkplaceId).collect(Collectors.toList()));
                extractionScheduleConRepo.register(opItems);
                fixedExtractionScheduleConRepo.register(conItems);
                break;
            }
            // 月次
            case MONTHLY: {
                List<FixedExtractionMonthlyCon> conItems = command
                        .getAlarmCheckCon()
                        .stream()
                        .map(i -> FixedExtractionMonthlyCon.create(
                                UUID.randomUUID().toString(),
                                i.getNo(),
                                i.isCheck(),
                                i.getMessage()
                        ))
                        .collect(Collectors.toList());
                List<ExtractionMonthlyCon> opItems = command.toDomainMon();
                condition = new AlarmMonthlyCheckCdt(
                        conItems.stream().map(FixedExtractionMonthlyCon::getErrorAlarmWorkplaceId).collect(Collectors.toList()),
                        opItems.stream().map(ExtractionMonthlyCon::getErrorAlarmWorkplaceId).collect(Collectors.toList())
                );
                extractionMonthlyConRepo.register(opItems);
                fixedExtractionMonthlyConRepo.register(conItems);
                break;
            }
            // 申請承認
            case APPLICATION_APPROVAL: {
                List<FixedExtractionAppapvCon> conItems = command
                        .getAlarmCheckCon()
                        .stream()
                        .map(i -> FixedExtractionAppapvCon.create(
                                UUID.randomUUID().toString(),
                                i.getNo(),
                                i.getMessage(),
                                i.isCheck()
                        ))
                        .collect(Collectors.toList());
                condition = new AlarmAppApprovalCheckCdt(conItems.stream().map(FixedExtractionAppapvCon::getErrorAlarmWorkplaceId).collect(Collectors.toList()));
                fixedExtractionAppapvConRepos.register(conItems);
                break;
            }
        }

        AlarmCheckCdtWorkplaceCategory domain = new AlarmCheckCdtWorkplaceCategory(
                command.getAlarmCheck().getCategory(),
                command.getAlarmCheck().getCode(),
                command.getAlarmCheck().getName(),
                condition
        );

        alarmCheckCdtWkpCtgRepo.register(domain);

    }

}
