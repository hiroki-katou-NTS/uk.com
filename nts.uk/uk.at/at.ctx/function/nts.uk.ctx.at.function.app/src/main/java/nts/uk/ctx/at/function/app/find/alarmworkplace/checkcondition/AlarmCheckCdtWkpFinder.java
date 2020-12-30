package nts.uk.ctx.at.function.app.find.alarmworkplace.checkcondition;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.AlarmCheckCdtWkpCtgRepository;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.AlarmCheckCdtWorkplaceCategory;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.WorkplaceCategory;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.applicationapproval.AlarmAppApprovalCheckCdt;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.basic.AlarmMasterBasicCheckCdt;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.daily.AlarmMasterDailyCheckCdt;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.monthly.AlarmMonthlyCheckCdt;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.schedule.AlarmScheduleCheckCdt;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.workplace.AlarmMasterWkpCheckCdt;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.*;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.*;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.*;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.*;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.FixedCheckMonthlyItemName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.schedule.*;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.*;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CompareRange;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Stateless
public class AlarmCheckCdtWkpFinder {

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

    public ScreenContentDto getExtractItemsByCategory(int category) {

        WorkplaceCategory ctg = EnumAdaptor.valueOf(category, WorkplaceCategory.class);

        List<AlarmCheckCdtWorkplaceCategory> alarmCheck = alarmCheckCdtWkpCtgRepo
                .getByCategory(EnumAdaptor.valueOf(category, WorkplaceCategory.class))
                .stream()
                .sorted(Comparator.comparing(AlarmCheckCdtWorkplaceCategory::getCode))
                .collect(Collectors.toList());
        List<FixedExtractionItemDto> fixedItemsByCtg = new ArrayList<>();

        switch (ctg) {
            case MASTER_CHECK_BASIC: {
                // ドメインモデル「アラームリスト（職場）基本の固定抽出項目」を取得する。
                List<BasicFixedExtractionItem> fixedItems = basicFixedExtractionItemRepo.getAll();
                fixedItemsByCtg = fixedItems.stream().map(i -> new FixedExtractionItemDto(
                        null,
                        i.getNo().value,
                        i.getCheckCls().value,
                        i.getName().v(),
                        i.getDisplayMessage().v()
                )).collect(Collectors.toList());
                break;
            }
            // マスタチェック(職場)
            case MASTER_CHECK_WORKPLACE: {
                // ドメインモデル「アラームリスト（職場）固定抽出項目」を取得する。
                List<AlarmFixedExtractionItem> fixedItems = alarmFixedExtractionItemRepo.getAll();
                fixedItemsByCtg = fixedItems.stream().map(i -> new FixedExtractionItemDto(
                        null,
                        i.getNo().value,
                        i.getAlarmCheckCls().value,
                        i.getWorkplaceCheckName().v(),
                        i.getDisplayMessage().v()
                )).collect(Collectors.toList());
                break;
            }
            // マスタチェック(日次)
            case MASTER_CHECK_DAILY: {
                // ドメインモデル「アラームリスト（職場）日別の固定抽出項目」を取得する。
                List<FixedExtractionDayItems> fixedItems = fixedExtractionDayItemsRepo.getAll();
                fixedItemsByCtg = fixedItems.stream().map(i -> new FixedExtractionItemDto(
                        null,
                        i.getFixedCheckDayItems().value,
                        i.getAlarmCheckCls().value,
                        i.getDailyCheckName(),
                        i.getFirstMessageDisp().v()
                )).collect(Collectors.toList());
                break;
            }
            // スケジュール／日次
            case SCHEDULE_DAILY: {
                // ドメインモデル「アラームリスト（職場別）スケジュール／日次の固定抽出項目」を取得する。
                List<FixedExtractionScheduleItems> fixedItems = fixedExtractionScheduleItemsRepo.getAll();
                fixedItemsByCtg = fixedItems.stream().map(i -> new FixedExtractionItemDto(
                        null,
                        i.getFixedCheckDayItemName().value,
                        i.getAlarmCheckCls().value,
                        i.getScheduleCheckName(),
                        i.getFirstMessageDisp().v()
                )).collect(Collectors.toList());
                break;
            }
            // 月次
            case MONTHLY: {
                // ドメインモデル「アラームリスト（職場）月次の固定抽出項目」を取得する。
                List<FixedExtractionMonthlyItems> fixedItems = fixedExtractionMonthlyItemsRepo.getAll();
                fixedItemsByCtg = fixedItems.stream().map(i -> new FixedExtractionItemDto(
                        null,
                        i.getNo().value,
                        i.getAlarmCheckCls().value,
                        i.getMonthlyCheckName(),
                        i.getFirstMessageDisp().v()
                )).collect(Collectors.toList());
                break;
            }
            // 申請承認
            case APPLICATION_APPROVAL: {
                // ドメインモデル「アラームリスト（職場）申請承認の固定抽出項目」を取得する。
                List<FixedExtractionAppapvItems> fiexedItems = fixedExtractionAppapvItemsRepo.getAll();
                fixedItemsByCtg = fiexedItems.stream().map(i -> new FixedExtractionItemDto(
                        null,
                        i.getCheckItemAppapv().value,
                        i.getAlarmCheckCls().value,
                        i.getAppapvCheckName(),
                        i.getFirstMessageDisp().v()
                )).collect(Collectors.toList());
                break;
            }
        }

        List<AlarmWkpCheckCdtDto> alarmWkpCheckCdtDtos = alarmCheck.stream().map(i -> new AlarmWkpCheckCdtDto(
                i.getCategory().value,
                i.getCode().v(),
                i.getCID(),
                i.getName().v()
        )).collect(Collectors.toList());
        return new ScreenContentDto(alarmWkpCheckCdtDtos, fixedItemsByCtg);
    }

    public ExtractionCondtionsDto getCategoryItemInfo(InitScreenDto param) {

        List<FixedExtractionConditionDto> fixedExtractConditions = Collections.emptyList();
        Optional<AlarmCheckCdtWorkplaceCategory> alarmCheck = alarmCheckCdtWkpCtgRepo
                .getByID(param.getCategory(), param.getCode());
        List<ExtractionSchelConDto> opSchelCons = new ArrayList<>();
        List<ExtractionMonConDto> opMonCons = new ArrayList<>();

        if (alarmCheck.isPresent()) {
            switch (alarmCheck.get().getCategory()) {
                case MASTER_CHECK_BASIC: {
                    List<String> ids = ((AlarmMasterBasicCheckCdt) alarmCheck.get().getCondition()).getAlarmCheckWkpID();
                    // ドメインモデル「アラームリスト（職場）基本の固定抽出条件」を取得する。
                    List<BasicFixedExtractionCondition> conItems = basicFixedExtractionConditionRepo.getByIDs(ids);

                    fixedExtractConditions = conItems.stream().map(i -> new FixedExtractionConditionDto(
                            i.getId(),
                            i.getNo().value,
                            i.isUseAtr(),
                            i.getDisplayMessage().v()
                    )).collect(Collectors.toList());

                    break;
                }
                // マスタチェック(職場)
                case MASTER_CHECK_WORKPLACE: {
                    List<String> ids = ((AlarmMasterWkpCheckCdt) alarmCheck.get().getCondition()).getAlarmCheckWkpID();
                    // ドメインモデル「アラームリスト（職場）固定抽出条件」を取得する。
                    List<AlarmFixedExtractionCondition> conItems = alarmFixedExtractionConditionRepo.getByIDs(ids);

                    fixedExtractConditions = conItems.stream().map(i -> new FixedExtractionConditionDto(
                            i.getId(),
                            i.getNo().value,
                            i.isUseAtr(),
                            i.getDisplayMessage().v()
                    )).collect(Collectors.toList());

                    break;
                }
                // マスタチェック(日次)
                case MASTER_CHECK_DAILY: {
                    List<String> ids = ((AlarmMasterDailyCheckCdt) alarmCheck.get().getCondition()).getAlarmCheckWkpID();
                    // ドメインモデル「アラームリスト（職場）日別の固定抽出条件」を取得する。
                    List<FixedExtractionDayCon> conItems = fixedExtractionDayConRepo.getRange(ids);

                    fixedExtractConditions = conItems.stream().map(i -> new FixedExtractionConditionDto(
                            i.getErrorAlarmWorkplaceId(),
                            i.getFixedCheckDayItems().value,
                            i.isUseAtr(),
                            i.getMessageDisp().v()
                    )).collect(Collectors.toList());

                    break;
                }
                // スケジュール／日次
                case SCHEDULE_DAILY: {
                    List<String> fixedIds = ((AlarmScheduleCheckCdt) alarmCheck.get().getCondition()).getAlarmCheckWkpID();
                    List<String> opIds = ((AlarmScheduleCheckCdt) alarmCheck.get().getCondition()).getListOptionalIDs();
                    // ドメインモデル「アラームリスト（職場別）スケジュール／日次の固定抽出条件」を取得する。
                    List<FixedExtractionScheduleCon> conItems = fixedExtractionScheduleConRepo.getByIds(fixedIds);
                    fixedExtractConditions = conItems.stream().map(i -> new FixedExtractionConditionDto(
                            i.getErrorAlarmWorkplaceId(),
                            i.getFixedCheckDayItemName().value,
                            i.isUseAtr(),
                            i.getMessageDisp().v()
                    )).collect(Collectors.toList());

                    // ドメインモデル「アラームリスト（職場別）スケジュール／日次の抽出条件」を取得する。
                    List<ExtractionScheduleCon> opItems = extractionScheduleConRepo.getByIds(opIds);
                    opSchelCons = opItems.stream().map(ExtractionSchelConDto::fromDomain).collect(Collectors.toList());
                    break;
                }
                // 月次
                case MONTHLY: {
                    List<String> ids = ((AlarmMonthlyCheckCdt) alarmCheck.get().getCondition()).getAlarmCheckWkpID();
                    List<String> opIds = ((AlarmMonthlyCheckCdt) alarmCheck.get().getCondition()).getListOptionalIDs();
                    // ドメインモデル「アラームリスト（職場）月次の固定抽出条件」を取得する。
                    List<FixedExtractionMonthlyCon> conItems = fixedExtractionMonthlyConRepo.getByIds(ids);
                    // 取得したList＜アラームリスト（職場）月次の固定抽出条件＞．Sizeをチェックする。
                    fixedExtractConditions = conItems.stream().map(i -> new FixedExtractionConditionDto(
                            i.getErrorAlarmWorkplaceId(),
                            i.getNo().value,
                            i.isUseAtr(),
                            i.getMessageDisp().v()
                    )).collect(Collectors.toList());
                    // ドメインモデル「アラームリスト（職場）月次の抽出条件」を取得する。
                    List<ExtractionMonthlyCon> opItems = extractionMonthlyConRepo.getByIds(opIds);
                    opMonCons = opItems.stream().map(ExtractionMonConDto::fromDomain).collect(Collectors.toList());
                    break;
                }
                // 申請承認
                case APPLICATION_APPROVAL: {
                    List<String> ids = ((AlarmAppApprovalCheckCdt) alarmCheck.get().getCondition()).getAlarmCheckWkpID();
                    // ドメインモデル「アラームリスト（職場）申請承認の固定抽出条件」を取得する。
                    List<FixedExtractionAppapvCon> conItems = fixedExtractionAppapvConRepos.getByIds(ids);
                    fixedExtractConditions = conItems.stream().map(i -> new FixedExtractionConditionDto(
                            i.getErrorAlarmWorkplaceId(),
                            i.getCheckItemAppapv().value,
                            i.isUseAtr(),
                            i.getMessageDisp().v()
                    )).collect(Collectors.toList());
                    break;
                }
            }
        }

        return new ExtractionCondtionsDto(fixedExtractConditions, opSchelCons, opMonCons);

    }
}
