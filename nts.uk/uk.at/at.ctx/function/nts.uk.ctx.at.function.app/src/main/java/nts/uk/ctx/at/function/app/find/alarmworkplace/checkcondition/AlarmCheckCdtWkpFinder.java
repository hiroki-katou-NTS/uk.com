//package nts.uk.ctx.at.function.app.find.alarm.workplace.checkcondition;
//
//import lombok.val;
//import nts.uk.ctx.at.function.dom.alarm.workplace.checkcondition.AlarmCheckCdtWkpCtgRepository;
//import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.AlarmCheckCdtWorkplaceCategory;
//import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.WorkplaceCategory;
//import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.basic.BasicFixedExtractionConditionRepository;
//import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.basic.BasicFixedExtractionItem;
//import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.basic.BasicFixedExtractionItemRepository;
//import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.workplace.AlarmFixedExtractionConditionRepository;
//import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.workplace.AlarmFixedExtractionItem;
//import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlist.workplace.AlarmFixedExtractionItemRepository;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//import java.util.Collections;
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//@Stateless
//public class AlarmCheckCdtWkpFinder {
//
//    @Inject
//    private AlarmCheckCdtWkpCtgRepository alarmCheckCdtWkpCtgRepo;
//
//    @Inject
//    private BasicFixedExtractionItemRepository basicFixedExtractionItemRepo;
//
//    @Inject
//    private BasicFixedExtractionConditionRepository basicFixedExtractionConditionRepo;
//
//    @Inject
//    private AlarmFixedExtractionItemRepository alarmFixedExtractionItemRepo;
//
//    @Inject
//    private AlarmFixedExtractionConditionRepository alarmFixedExtractionConditionRepo;
//
//
//    public void init(InitScreenDto param) {
//
//        List<FixedExtractionCdtOutput> outputs = Collections.emptyList();
//
//        Optional<AlarmCheckCdtWorkplaceCategory> alarmCheck = alarmCheckCdtWkpCtgRepo.getByCategoryID(param.getCategoryID());
//
//        if (alarmCheck.isPresent()) {
//            val category = alarmCheck.get().getCategory();
//            switch (category) {
//                // マスタチェック(基本)
//                case MASTER_CHECK_BASIC: {
//                    String firstID = alarmCheck.get().getCondition().getListIDs().get(0);
//                    // ドメインモデル「アラームリスト（職場）基本の固定抽出項目」を取得する。
//                    val listBasicExtractItem = basicFixedExtractionItemRepo.getAll();
//                    // ドメインモデル「アラームリスト（職場）基本の固定抽出条件」を取得する。
//                    val listBasicExtractCdt = basicFixedExtractionConditionRepo.getByID(firstID);
//                    // 取得したList＜アラームリスト（職場）基本の固定抽出条件＞．Sizeをチェックする。
//                    if (listBasicExtractCdt.isEmpty()) {
//                        outputs = listBasicExtractItem.stream().map(i -> new FixedExtractionCdtOutput(
//                                firstID,
//                                i.getNo().value,
//                                i.getCheckCls().value,
//                                i.getName().v(),
//                                i.getDisplayMessage().v()
//                        )).collect(Collectors.toList());
//                    } else {
//                        val mapBasicExtractItem = listBasicExtractItem.stream().collect(Collectors.toMap(BasicFixedExtractionItem::getNo, Function.identity()));
//                        outputs = listBasicExtractCdt.stream().map(i -> {
//                            val itemHasSameNo = mapBasicExtractItem.get(i.getNo());
//                            if (itemHasSameNo != null) {
//                                return new FixedExtractionCdtOutput(
//                                        firstID,
//                                        itemHasSameNo.getNo().value,
//                                        itemHasSameNo.getCheckCls().value,
//                                        itemHasSameNo.getName().v(),
//                                        itemHasSameNo.getDisplayMessage().v()
//                                );
//                            }
//                            return null;
//                        }).filter(Objects::nonNull).collect(Collectors.toList());
//                    }
//                    break;
//                }
//                // マスタチェック(職場)
//                case MASTER_CHECK_WORKPLACE: {
//                    String firstID = alarmCheck.get().getCondition().getListIDs().get(0);
//                    // ドメインモデル「アラームリスト（職場）固定抽出項目」を取得する。
//                    val listMasterCheckWkpItem = alarmFixedExtractionItemRepo.getAll();
//                    // ドメインモデル「アラームリスト（職場）固定抽出条件」を取得する。
//                    val masterCheckWkpCdt = alarmFixedExtractionConditionRepo.getByID(firstID);
//                    if (listMasterCheckWkpItem.isEmpty()) {
//                        outputs = listMasterCheckWkpItem.stream().map(i -> new FixedExtractionCdtOutput(
//                                firstID,
//                                i.getNo().value,
//                                i.getAlarmCheckCls().value,
//                                i.getWorkplaceCheckName().v(),
//                                i.getDisplayMessage().v()
//                        )).collect(Collectors.toList());
//                    } else {
//                        val mapBasicExtractItem = listMasterCheckWkpItem.stream()
//                                .collect(Collectors.toMap(AlarmFixedExtractionItem::getNo, Function.identity()));
//                        outputs = masterCheckWkpCdt.stream().map(i -> {
//                            val itemHasSameNo = mapBasicExtractItem.get(i.getNo());
//                            if (itemHasSameNo != null) {
//                                return new FixedExtractionCdtOutput(
//                                        firstID,
//                                        itemHasSameNo.getNo().value,
//                                        itemHasSameNo.getAlarmCheckCls().value,
//                                        itemHasSameNo.getWorkplaceCheckName().v(),
//                                        itemHasSameNo.getDisplayMessage().v()
//                                );
//                            }
//                            return null;
//                        }).filter(Objects::nonNull).collect(Collectors.toList());
//                    }
//                    break;
//                }
//                // マスタチェック(日次)
//                case MASTER_CHECK_DAILY:
//                    break;
//                // スケジュール／日次
//                case SCHEDULE_DAILY:
//                    break;
//                // 月次
//                case MONTHLY:
//                    break;
//                // 申請承認
//                case APPLICATION_APPROVAL:
//                    break;
//            }
//
//        }
//
//    }
//
//    // カテゴリの値をチェックする。
//    public void checkCategoryValue(String id, WorkplaceCategory category) {
//
//    }
//
//}
