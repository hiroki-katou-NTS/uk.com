package nts.uk.ctx.at.function.app.find.alarmworkplace.checkcondition;

import lombok.val;
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
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.FixedExtractionAppapvCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.FixedExtractionAppapvConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.FixedExtractionAppapvItemsRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.BasicFixedExtractionCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.BasicFixedExtractionConditionRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.BasicFixedExtractionItemRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.FixedExtractionDayConRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.FixedExtractionDayItemsRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.AlarmFixedExtractionCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.AlarmFixedExtractionConditionRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.AlarmFixedExtractionItemRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
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

    public void init(InitScreenDto param) {

        List<FixedExtractionCdtOutput> outputs = Collections.emptyList();

        List<AlarmCheckCdtWorkplaceCategory> alarmCheck = alarmCheckCdtWkpCtgRepo
                .getByCategory(EnumAdaptor.valueOf(param.getCategoryID(), WorkplaceCategory.class));

        if (!alarmCheck.isEmpty()) {
            val firstCategory = alarmCheck.get(0);
            switch (firstCategory.getCategory()) {
                // マスタチェック(基本)
                case MASTER_CHECK_BASIC: {
                    List<String> ids = ((AlarmMasterBasicCheckCdt) firstCategory.getCondition()).getAlarmCheckWkpID();
                    // ドメインモデル「アラームリスト（職場）基本の固定抽出項目」を取得する。
                    val listBasicExtractItem = basicFixedExtractionItemRepo.getAll();
                    // ドメインモデル「アラームリスト（職場）基本の固定抽出条件」を取得する。
                    val listBasicExtractCdt = basicFixedExtractionConditionRepo.getByIDs(ids);
                    // 取得したList＜アラームリスト（職場）基本の固定抽出条件＞．Sizeをチェックする。
                    if (listBasicExtractCdt.isEmpty()) {
                        outputs = listBasicExtractItem.stream().map(i -> new FixedExtractionCdtOutput(
                                null,
                                i.getNo().value,
                                i.getCheckCls().value,
                                i.getName().v(),
                                i.getDisplayMessage().v()
                        )).collect(Collectors.toList());
                    } else {
                        val mapBasicExtractCon = listBasicExtractCdt.stream()
                                .collect(Collectors.toMap(BasicFixedExtractionCondition::getNo, Function.identity()));

                        outputs = listBasicExtractItem.stream().map(i -> {
                            val itemHasSameNo = mapBasicExtractCon.get(i.getNo());
                            if (itemHasSameNo != null) {
                                return new FixedExtractionCdtOutput(
                                        itemHasSameNo.getId(),
                                        itemHasSameNo.getNo().value,
                                        i.getCheckCls().value,
                                        i.getName().v(),
                                        itemHasSameNo.getDisplayMessage().v()
                                );
                            }
                            return new FixedExtractionCdtOutput(
                                    null,
                                    i.getNo().value,
                                    i.getCheckCls().value,
                                    i.getName().v(),
                                    i.getDisplayMessage().v()
                            );
                        }).collect(Collectors.toList());
                    }
                    break;
                }
                // マスタチェック(職場)
                case MASTER_CHECK_WORKPLACE: {
                    List<String> ids = ((AlarmMasterWkpCheckCdt) firstCategory.getCondition()).getAlarmCheckWkpID();
                    // ドメインモデル「アラームリスト（職場）固定抽出項目」を取得する。
                    val listMasterCheckWkpItem = alarmFixedExtractionItemRepo.getAll();
                    // ドメインモデル「アラームリスト（職場）固定抽出条件」を取得する。
                    val masterCheckWkpCdt = alarmFixedExtractionConditionRepo.getByIDs(ids);

                    if (listMasterCheckWkpItem.isEmpty()) {
                        outputs = listMasterCheckWkpItem.stream().map(i -> new FixedExtractionCdtOutput(
                                null,
                                i.getNo().value,
                                i.getAlarmCheckCls().value,
                                i.getWorkplaceCheckName().v(),
                                i.getDisplayMessage().v()
                        )).collect(Collectors.toList());
                    } else {
                        val mapBasicExtractCon = masterCheckWkpCdt.stream()
                                .collect(Collectors.toMap(AlarmFixedExtractionCondition::getNo, Function.identity()));

                        outputs = listMasterCheckWkpItem.stream().map(i -> {
                            val itemHasSameNo = mapBasicExtractCon.get(i.getNo());
                            if (itemHasSameNo != null) {
                                return new FixedExtractionCdtOutput(
                                        itemHasSameNo.getId(),
                                        itemHasSameNo.getNo().value,
                                        i.getAlarmCheckCls().value,
                                        i.getWorkplaceCheckName().v(),
                                        itemHasSameNo.getDisplayMessage().v()
                                );
                            }
                            return new FixedExtractionCdtOutput(
                                    null,
                                    i.getNo().value,
                                    i.getAlarmCheckCls().value,
                                    i.getWorkplaceCheckName().v(),
                                    i.getDisplayMessage().v()
                            );
                        }).collect(Collectors.toList());
                    }
                    break;
                }
                // マスタチェック(日次)
                case MASTER_CHECK_DAILY: {
                    List<String> ids = ((AlarmMasterDailyCheckCdt) firstCategory.getCondition()).getAlarmCheckWkpID();

                    break;
                }
                // スケジュール／日次
                case SCHEDULE_DAILY: {
                    List<String> fixedIds = ((AlarmScheduleCheckCdt) firstCategory.getCondition()).getListExtractionIDs();
                    List<String> opIds = ((AlarmScheduleCheckCdt) firstCategory.getCondition()).getListOptionalIDs();
                    break;
                }
                // 月次
                case MONTHLY: {
                    List<String> ids = ((AlarmMonthlyCheckCdt) firstCategory.getCondition()).getListExtractionIDs();
                    List<String> opIds = ((AlarmMonthlyCheckCdt) firstCategory.getCondition()).getListOptionalIDs();
                    break;
                }
                // 申請承認
                case APPLICATION_APPROVAL: {
                    List<String> ids = ((AlarmAppApprovalCheckCdt) firstCategory.getCondition()).getAlarmCheckWkpID();
                    // ドメインモデル「アラームリスト（職場）申請承認の固定抽出項目」を取得する。
                    val listItem = fixedExtractionAppapvItemsRepo.getAll();
                    // ドメインモデル「アラームリスト（職場）申請承認の固定抽出条件」を取得する。
                    val listCon = fixedExtractionAppapvConRepos.getByIds(ids);
                    // 取得したList＜アラームリスト（職場）日別の固定抽出条件＞．Sizeをチェックする。
                    if (listCon.isEmpty()) {
                        outputs = listItem.stream().map(i -> new FixedExtractionCdtOutput(
                                null,
                                i.getCheckItemAppapv().value,
                                i.getAlarmCheckCls().value,
                                i.getAppapvCheckName(),
                                i.getFirstMessageDisp().v()
                        )).collect(Collectors.toList());
                    } else {
                        val mapBasicExtractCon = listCon.stream()
                                .collect(Collectors.toMap(FixedExtractionAppapvCon::getCheckItemAppapv, Function.identity()));

                        outputs = listItem.stream().map(i -> {
                            val itemHasSameNo = mapBasicExtractCon.get(i.getCheckItemAppapv());
                            if (itemHasSameNo != null) {
                                return new FixedExtractionCdtOutput(
                                        itemHasSameNo.getErrorAlarmWorkplaceId(),
                                        itemHasSameNo.getCheckItemAppapv().value,
                                        i.getAlarmCheckCls().value,
                                        i.getAppapvCheckName(),
                                        itemHasSameNo.getMessageDisp().v()
                                );
                            }
                            return new FixedExtractionCdtOutput(
                                    null,
                                    i.getCheckItemAppapv().value,
                                    i.getAlarmCheckCls().value,
                                    i.getAppapvCheckName(),
                                    i.getFirstMessageDisp().v()
                            );
                        }).collect(Collectors.toList());
                    }
                    break;
                }
            }

        }

    }

    // カテゴリの値をチェックする。
    public void checkCategoryValue(String id, WorkplaceCategory category) {

    }

}
