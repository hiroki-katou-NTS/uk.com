package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.service;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.*;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.Abolition;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.arc.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class StatementLayoutService {

    @Inject
    private FixedItemClassificationListRepository fixedItemClassificationListRepo;

    @Inject
    private StatementItemRepository statementItemRepo;

    @Inject
    private StatementLayoutRepository statementLayoutRepo;

    @Inject
    private StatementLayoutHistRepository statementLayoutHistRepo;

    @Inject
    private StatementLayoutSetRepository statementLayoutSetRepo;

    @Inject
    private
    StatementItemNameRepository statementItemNameRepo;

    private static final int LAST_END_YEAR_MONTH = 999912;
    private static final int LAST_POSITION = 9;
    private static final String F001 = "F001";
    private static final String F002 = "F002";
    private static final String F003 = "F003";
    private static final String F101 = "F101";
    private static final String F102 = "F102";
    private static final String F103 = "F103";
    private static final String F104 = "F104";
    private static final String F105 = "F105";
    private static final String F106 = "F106";
    private static final String F107 = "F107";
    private static final String F108 = "F108";
    private static final String F114 = "F114";
    private static final String F309 = "F309";


    /**
     * 支給項目一覧を更新する
     */
    public List<StatementItemCustom> getStatementItem(int categoryAtr, String itemNameCdSelected, List<String> itemNameCdExcludeList) {
        String cid = AppContexts.user().companyId();
        // ドメインモデル「固定項目区分一覧」を取得する
        List<String> itemNameCdFixedList = fixedItemClassificationListRepo
                .getFixedItemClassificationListBySpecOutPutCls(SpecificationOutputCls.DO_NOT_OUTPUT.value)
                .stream().map(x -> x.getItemNameCd().v()).collect(Collectors.toList());
        // ドメインモデル「明細書項目」を取得する
        // ドメインモデル「明細書項目名称」を取得する
        return statementItemRepo.getItemCustomByCtgAndExcludeCodes(cid, categoryAtr, Abolition.NOT_ABOLISH.value,
                itemNameCdFixedList, itemNameCdSelected, itemNameCdExcludeList);
    }

    //新規作成実行処理
    public void addStatementLayout(int isClone, String histIdNew, String histIdClone, int layoutPatternClone,
                                   String statementCode, String statementName, int startDate, int layoutPattern, String statementCodeClone) {
        validate(isClone, statementCode, layoutPatternClone, layoutPattern);

        String cid = AppContexts.user().companyId();
        StatementLayout statementLayout = new StatementLayout(cid, statementCode, statementName);
        YearMonthHistoryItem statementLayoutHist = new YearMonthHistoryItem(histIdNew, new YearMonthPeriod(new YearMonth(startDate), new YearMonth(LAST_END_YEAR_MONTH)));

        statementLayoutRepo.add(statementLayout);
        statementLayoutHistRepo.add(cid, statementCode, statementLayoutHist, layoutPattern);

        Optional<StatementLayoutSet> statementLayoutSet;
        if (isClone == 0) {
            statementLayoutSet = getNewStatementLayoutSet(histIdNew, layoutPattern, statementCode);
        } else {
            statementLayoutSet = cloneStatementLayoutSet(cid, statementCodeClone, histIdNew, histIdClone, layoutPattern, statementCode);
        }

        if(statementLayoutSet.isPresent()) {
            statementLayoutSetRepo.add(statementCode, statementLayoutSet.get());
        } else {
            throw new BusinessException("Some err");
        }
    }

    public Optional<StatementLayoutSet> initStatementLayoutData(String statementCode, String histIdNew, int isClone, int layoutPattern) {
        String cid = AppContexts.user().companyId();
        Optional<YearMonthHistoryItem> lastHistOptional = statementLayoutHistRepo.getLatestHistByCidAndCode(cid, statementCode);
        Optional<StatementLayoutSet> statementLayoutSet;

        if (isClone == 1) {
            statementLayoutSet = getNewStatementLayoutSet(histIdNew, layoutPattern, statementCode);
        } else if(lastHistOptional.isPresent()){
            statementLayoutSet = cloneStatementLayoutSet(cid, statementCode, histIdNew, lastHistOptional.get().identifier(), layoutPattern, statementCode);
        } else {
            return Optional.empty();
        }

        return statementLayoutSet;
    }

    public String getShortName(int categoryAtr, String itemNameCd) {
        String cid = AppContexts.user().companyId();
        return statementItemNameRepo.getStatementItemNameById(cid, categoryAtr, itemNameCd).map(i -> i.getShortName().v()).orElse(null);
    }

    //新規に作成の場合
    private Optional<StatementLayoutSet> getNewStatementLayoutSet(String histIdNew, int layoutPattern, String statementCode) {
        List<SettingByCtg> listSettingByCtg = new ArrayList<>();

        //支給項目
        List<LineByLineSetting> paymentLineList = new ArrayList<>();
        paymentLineList.add(getNewLineTypePayment(histIdNew, 1, F001, statementCode));
        paymentLineList.add(getNewLineTypePayment(histIdNew, 2, F002, statementCode));
        paymentLineList.add(getNewLineTypePayment(histIdNew, 3, F003, statementCode));

        SettingByCtg paymentCtgSetting = new SettingByCtg(CategoryAtr.PAYMENT_ITEM.value, paymentLineList);
        listSettingByCtg.add(paymentCtgSetting);

        //控除項目
        List<LineByLineSetting> deducationLineList = new ArrayList<>();
        List<SettingByItem> itemsInLine1 = new ArrayList<>();
        List<SettingByItem> itemsInLine2 = new ArrayList<>();
        List<SettingByItem> itemsInLine3 = new ArrayList<>();
        itemsInLine1.add(getNewItemTypeDeduction(histIdNew, 1, F101, statementCode, DeductionTotalObjAtr.INSIDE.value));
        itemsInLine1.add(getNewItemTypeDeduction(histIdNew, 2, F102, statementCode, DeductionTotalObjAtr.INSIDE.value));
        itemsInLine1.add(getNewItemTypeDeduction(histIdNew,  3, F103, statementCode, DeductionTotalObjAtr.INSIDE.value));
        itemsInLine1.add(getNewItemTypeDeduction(histIdNew,  4, F104, statementCode, DeductionTotalObjAtr.INSIDE.value));
        itemsInLine1.add(getNewItemTypeDeduction(histIdNew,  5, F105, statementCode, DeductionTotalObjAtr.OUTSIDE.value));
        itemsInLine1.add(getNewItemTypeDeduction(histIdNew,  6, F106, statementCode, DeductionTotalObjAtr.OUTSIDE.value));
        itemsInLine1.add(getNewItemTypeDeduction(histIdNew,  7, F107, statementCode, DeductionTotalObjAtr.INSIDE.value));
        itemsInLine1.add(getNewItemTypeDeduction(histIdNew,  8, F108, statementCode, DeductionTotalObjAtr.INSIDE.value));

        SettingByItem itemF114 = getNewItemTypeDeduction(histIdNew, 9, F114, statementCode, DeductionTotalObjAtr.OUTSIDE.value);
        if (StatementLayoutPattern.DOT_PRINT_CONTINUOUS_PAPER_ONE_PERSON.value == layoutPattern) {
            itemsInLine3.add(itemF114);
            deducationLineList.add(new LineByLineSetting(StatementPrintAtr.PRINT.value, 3, itemsInLine3));
        } else {
            itemsInLine2.add(itemF114);
        }

        LineByLineSetting line1 = new LineByLineSetting(StatementPrintAtr.PRINT.value, 1, itemsInLine1);
        LineByLineSetting line2 = new LineByLineSetting(StatementPrintAtr.PRINT.value, 2, itemsInLine2);
        deducationLineList.add(line1);
        deducationLineList.add(line2);

        SettingByCtg deducationCtgSetting = new SettingByCtg(CategoryAtr.DEDUCTION_ITEM.value, deducationLineList);
        listSettingByCtg.add(deducationCtgSetting);

        //勤怠項目
        List<LineByLineSetting> timeLineList = new ArrayList<>();
        SettingByCtg timeCtgSetting = new SettingByCtg(CategoryAtr.ATTEND_ITEM.value, timeLineList);
        listSettingByCtg.add(timeCtgSetting);

        if(StatementLayoutPattern.DOT_PRINT_CONTINUOUS_PAPER_ONE_PERSON.value == layoutPattern) {
            timeLineList.add(new LineByLineSetting(StatementPrintAtr.PRINT.value, 1, new ArrayList<>()));
            timeLineList.add(new LineByLineSetting(StatementPrintAtr.PRINT.value, 2, new ArrayList<>()));
        }

        //記事項目
        List<LineByLineSetting> reportLineList = new ArrayList<>();
        SettingByItem item = new SettingByItemCustom(9, F309, getShortName(CategoryAtr.REPORT_ITEM.value, F309), null, null, null);
        List<SettingByItem> listSetByItem = new ArrayList<>();
        listSetByItem.add(item);

        LineByLineSetting reportLine1 = new LineByLineSetting(StatementPrintAtr.PRINT.value, 1, listSetByItem);
        reportLineList.add(reportLine1);

        SettingByCtg reportCtgSetting = new SettingByCtg(CategoryAtr.REPORT_ITEM.value, reportLineList);
        listSettingByCtg.add(reportCtgSetting);

        return Optional.of(new StatementLayoutSet(histIdNew, layoutPattern, listSettingByCtg));
    }

    //既存のレイアウトをコピーする場合
    private Optional<StatementLayoutSet> cloneStatementLayoutSet(String cid, String statementCodeClone, String histIdNew, String histIdClone, int layoutPattern, String statementCode) {
        Optional<StatementLayoutSet> cloneStatementLayoutSetOptional = statementLayoutSetRepo.getStatementLayoutSetById(cid, statementCodeClone, histIdClone);

        if(cloneStatementLayoutSetOptional.isPresent()) {
            StatementLayoutSet cloneStatementLayoutSet = cloneStatementLayoutSetOptional.get();
            cloneStatementLayoutSet.setHistId(histIdNew);
            cloneStatementLayoutSet.setLayoutPattern(EnumAdaptor.valueOf(layoutPattern, StatementLayoutPattern.class));

            for (SettingByCtg settingByCtg : cloneStatementLayoutSet.getListSettingByCtg()) {
                for (LineByLineSetting lineByLineSetting : settingByCtg.getListLineByLineSet()) {
                    for (SettingByItem settingByItem : lineByLineSetting.getListSetByItem()) {
                        if(settingByItem instanceof SettingByItemCustom) {
                            SettingByItemCustom settingByItemCustom = (SettingByItemCustom) settingByItem;
                            if(settingByItemCustom.getPaymentItemDetailSet().isPresent()) {
                                settingByItemCustom.getPaymentItemDetailSet().get().setHistId(histIdNew);
                                settingByItemCustom.getPaymentItemDetailSet().get().setStatementCode(new StatementCode(statementCode));
                            }

                            if(settingByItemCustom.getDeductionItemDetailSet().isPresent()) {
                                settingByItemCustom.getDeductionItemDetailSet().get().setHistId(histIdNew);
                                settingByItemCustom.getDeductionItemDetailSet().get().setStatementCode(new StatementCode(statementCode));
                            }

                            if(settingByItemCustom.getItemRangeSetting().isPresent()) {
                                settingByItemCustom.getItemRangeSetting().get().setHistId(histIdNew);
                                settingByItemCustom.getItemRangeSetting().get().setStatementCode(new StatementCode(statementCode));
                            }
                        }
                    }
                }
            }

            return Optional.of(cloneStatementLayoutSet);
        } else {
            return Optional.empty();
        }
    }

    private LineByLineSetting getNewLineTypePayment(String histId, int lineNumber, String itemNameCd, String statementCode) {
        String cid = AppContexts.user().companyId();

        PaymentItemDetailSet detail = new PaymentItemDetailSet(histId, cid, statementCode, itemNameCd, PaymentTotalObjAtr.OUTSIDE.value, PaymentProportionalAtr.NOT_PROPORTIONAL.value,
                null, PaymentCaclMethodAtr.MANUAL_INPUT.value, null, null, null, null, null);
        SettingByItemCustom item = new SettingByItemCustom(LAST_POSITION, itemNameCd, getShortName(CategoryAtr.PAYMENT_ITEM.value, itemNameCd), null, detail, null);
        List<SettingByItem> listSetByItem = new ArrayList<>();
        listSetByItem.add(item);

        return new LineByLineSetting(StatementPrintAtr.PRINT.value, lineNumber, listSetByItem);
    }

    private SettingByItem getNewItemTypeDeduction(String histId, int position, String itemNameCd, String statementCode, int totalObj) {
        String cid = AppContexts.user().companyId();

        DeductionItemDetailSet detail = new DeductionItemDetailSet(histId, cid, statementCode, itemNameCd, totalObj, PaymentProportionalAtr.NOT_PROPORTIONAL.value,
                null, PaymentCaclMethodAtr.MANUAL_INPUT.value, null, null, null, null, null);
        return new SettingByItemCustom(position, itemNameCd, getShortName(CategoryAtr.DEDUCTION_ITEM.value, itemNameCd), detail, null, null);
    }

    //新規作成時チェック処理
    private void validate(int isClone, String statementCode, int layoutPatternClone, int layoutPattern) {
        String cid = AppContexts.user().companyId();
        Optional<StatementLayout> statementLayoutOptional = statementLayoutRepo.getStatementLayoutById(cid, statementCode);

        if(statementLayoutOptional.isPresent()) {
            throw new BusinessException("Msg_3");
        }

        if((isClone == 0) || (layoutPatternClone == layoutPattern)) {
            return;
        }

        if((layoutPattern == StatementLayoutPattern.LASER_CRIMP_LANDSCAPE_ONE_PERSON.value) ||
                (layoutPattern == StatementLayoutPattern.DOT_PRINT_CONTINUOUS_PAPER_ONE_PERSON.value)) {
            throw new BusinessException("MsgQ_33");
        }

        int maxLineOfLayoutClone = getMaxLineOfLayoutPattern(layoutPatternClone);
        int maxLineOfLayoutNew = getMaxLineOfLayoutPattern(layoutPattern);

        if(maxLineOfLayoutClone > maxLineOfLayoutNew) throw new BusinessException("MsgQ_33");
    }

    private int getMaxLineOfLayoutPattern(int layoutPattern) {
        StatementLayoutPattern layoutPatternEnum = EnumAdaptor.valueOf(layoutPattern, StatementLayoutPattern.class);
        switch (layoutPatternEnum) {
            case LASER_PRINT_A4_PORTRAIT_ONE_PERSON:
                return 30;
            case LASER_PRINT_A4_PORTRAIT_TWO_PERSON:
                return 17;
            case LASER_PRINT_A4_PORTRAIT_THREE_PERSON:
                return 10;
            case LASER_PRINT_A4_LANDSCAPE_TWO_PERSON:
                return 10;
            case LASER_CRIMP_PORTRAIT_ONE_PERSON:
                return 17;
            case LASER_CRIMP_LANDSCAPE_ONE_PERSON:
                return 11111111;
            case DOT_PRINT_CONTINUOUS_PAPER_ONE_PERSON:
                return 11111111;
            default:
                return 0;
        }
    }
}
