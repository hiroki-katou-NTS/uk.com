package nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.AlarmCheckCdtWkpCtgRepository;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.AlarmCheckCdtWorkplaceCategory;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.applicationapproval.AlarmAppApprovalCheckCdt;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.basic.AlarmMasterBasicCheckCdt;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.daily.AlarmMasterDailyCheckCdt;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.monthly.AlarmMonthlyCheckCdt;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.schedule.AlarmScheduleCheckCdt;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.workplace.AlarmMasterWkpCheckCdt;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.FixedExtractionAppapvConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.FixedExtractionAppapvItemsRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.BasicFixedExtractionConditionRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.BasicFixedExtractionItemRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.FixedExtractionDayConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.FixedExtractionDayItemsRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.ExtractionMonthlyConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.FixedExtractionMonthlyConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.FixedExtractionMonthlyItemsRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.ExtractionScheduleConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.FixedExtractionScheduleConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.FixedExtractionScheduleItemsRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.AlarmFixedExtractionConditionRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.AlarmFixedExtractionItemRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class DeleteAlarmCheckCdtWkpCommandHandler extends CommandHandler<DeleteAlarmCheckCdtWkpCommand> {

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
    protected void handle(CommandHandlerContext<DeleteAlarmCheckCdtWkpCommand> context) {

        DeleteAlarmCheckCdtWkpCommand command = context.getCommand();

        Optional<AlarmCheckCdtWorkplaceCategory> domain = alarmCheckCdtWkpCtgRepo.getByID(command.getCategory(), command.getCode());

        if (domain.isPresent()) {
            alarmCheckCdtWkpCtgRepo.delete(domain.get());
            switch (domain.get().getCategory()) {

                case MASTER_CHECK_BASIC: {
                    List<String> ids = ((AlarmMasterBasicCheckCdt) domain.get().getCondition()).getAlarmCheckWkpID();
                    basicFixedExtractionConditionRepo.delete(ids);
                    break;
                }
                // マスタチェック(職場)
                case MASTER_CHECK_WORKPLACE: {
                    List<String> ids = ((AlarmMasterWkpCheckCdt) domain.get().getCondition()).getAlarmCheckWkpID();
                    alarmFixedExtractionConditionRepo.delete(ids);
                    break;
                }
                // マスタチェック(日次)
                case MASTER_CHECK_DAILY: {
                    List<String> ids = ((AlarmMasterDailyCheckCdt) domain.get().getCondition()).getAlarmCheckWkpID();
                    fixedExtractionDayConRepo.delete(ids);
                    break;
                }
                // スケジュール／日次
                case SCHEDULE_DAILY: {
                    List<String> fixedIds = ((AlarmScheduleCheckCdt) domain.get().getCondition()).getAlarmCheckWkpID();
                    List<String> opIds = ((AlarmScheduleCheckCdt) domain.get().getCondition()).getListOptionalIDs();
                    fixedExtractionScheduleConRepo.delete(fixedIds);
                    extractionScheduleConRepo.delete(opIds);
                    break;
                }
                // 月次
                case MONTHLY: {
                    List<String> fixedIds = ((AlarmMonthlyCheckCdt) domain.get().getCondition()).getAlarmCheckWkpID();
                    List<String> opIds = ((AlarmMonthlyCheckCdt) domain.get().getCondition()).getListOptionalIDs();
                    fixedExtractionMonthlyConRepo.delete(fixedIds);
                    extractionMonthlyConRepo.delete(opIds);
                    break;
                }
                // 申請承認
                case APPLICATION_APPROVAL: {
                    List<String> ids = ((AlarmAppApprovalCheckCdt) domain.get().getCondition()).getAlarmCheckWkpID();
                    fixedExtractionAppapvConRepos.delete(ids);
                    break;
                }
                
            }
        }
        

        

    }

}
