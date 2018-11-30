package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.service;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.*;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.*;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.Abolition;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

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
        // TODO #125441
        // ドメインモデル「明細書項目」を取得する
        // ドメインモデル「明細書項目名称」を取得する
        return statementItemRepo.getItemCustomByCtgAndExcludeCodes(cid, categoryAtr, Abolition.NOT_ABOLISH.value,
                itemNameCdFixedList, itemNameCdSelected, itemNameCdExcludeList);
    }

    //新規作成実行処理
    public void addStatementLayout(int isClone, String histIdNew, String histIdClone, int layoutPatternClone,
                                   String statementCode, String statementName, int startDate, int layoutPattern) {
        validate(isClone, statementCode, layoutPatternClone, layoutPattern);

        String cid = AppContexts.user().companyId();
        StatementLayout statementLayout = new StatementLayout(cid, statementCode, statementName);
        YearMonthHistoryItem statementLayoutHist = new YearMonthHistoryItem(histIdNew, new YearMonthPeriod(new YearMonth(startDate), new YearMonth(LAST_END_YEAR_MONTH)));

        statementLayoutRepo.add(statementLayout);
        statementLayoutHistRepo.add(cid, statementCode, statementLayoutHist, layoutPattern);

        if (isClone == 0) {
            addNewStatementLayoutSet(histIdNew, layoutPattern);
        } else {
            cloneStatementLayoutSet(histIdNew, histIdClone);
        }
    }

    //新規に作成の場合
    private void addNewStatementLayoutSet(String histIdNew, int layoutPattern) {
        List<SettingByCtg> listSettingByCtg = new ArrayList<>();

        //支給項目
        List<LineByLineSetting> paymentLineList = new ArrayList<>();
        paymentLineList.add(getNewLineTypePayment(histIdNew, 1, F001));
        paymentLineList.add(getNewLineTypePayment(histIdNew, 2, F002));
        paymentLineList.add(getNewLineTypePayment(histIdNew, 3, F003));

        SettingByCtg paymentCtgSetting = new SettingByCtg(CategoryAtr.PAYMENT_ITEM.value, paymentLineList);
        listSettingByCtg.add(paymentCtgSetting);

        //控除項目
        List<LineByLineSetting> deducationLineList = new ArrayList<>();
        List<SettingByItem> itemsInLine1 = new ArrayList<>();
        List<SettingByItem> itemsInLine2 = new ArrayList<>();
        itemsInLine1.add(getNewItemTypeDeduction(histIdNew, 1, F101, DeductionTotalObjAtr.INSIDE.value));
        itemsInLine1.add(getNewItemTypeDeduction(histIdNew, 2, F102, DeductionTotalObjAtr.INSIDE.value));
        itemsInLine1.add(getNewItemTypeDeduction(histIdNew,  3, F103, DeductionTotalObjAtr.INSIDE.value));
        itemsInLine1.add(getNewItemTypeDeduction(histIdNew,  4, F104, DeductionTotalObjAtr.INSIDE.value));
        itemsInLine1.add(getNewItemTypeDeduction(histIdNew,  5, F105, DeductionTotalObjAtr.OUTSIDE.value));
        itemsInLine1.add(getNewItemTypeDeduction(histIdNew,  6, F106, DeductionTotalObjAtr.OUTSIDE.value));
        itemsInLine1.add(getNewItemTypeDeduction(histIdNew,  7, F107, DeductionTotalObjAtr.INSIDE.value));
        itemsInLine1.add(getNewItemTypeDeduction(histIdNew,  8, F108, DeductionTotalObjAtr.INSIDE.value));
        itemsInLine2.add(getNewItemTypeDeduction(histIdNew,  9, F114, DeductionTotalObjAtr.OUTSIDE.value));

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
            deducationLineList.add(new LineByLineSetting(StatementPrintAtr.PRINT.value, 3, new ArrayList<>()));

            timeLineList.add(new LineByLineSetting(StatementPrintAtr.PRINT.value, 1, new ArrayList<>()));
            timeLineList.add(new LineByLineSetting(StatementPrintAtr.PRINT.value, 2, new ArrayList<>()));
        }

        //記事項目
        List<LineByLineSetting> reportLineList = new ArrayList<>();
        SettingByItem item = new SettingByItem(9, F309);
        List<SettingByItem> listSetByItem = new ArrayList<>();
        listSetByItem.add(item);

        LineByLineSetting reportLine1 = new LineByLineSetting(StatementPrintAtr.PRINT.value, 1, listSetByItem);
        reportLineList.add(reportLine1);

        SettingByCtg reportCtgSetting = new SettingByCtg(CategoryAtr.REPORT_ITEM.value, reportLineList);
        listSettingByCtg.add(reportCtgSetting);

        StatementLayoutSet statementLayoutSet = new StatementLayoutSet(histIdNew, layoutPattern, listSettingByCtg);
        statementLayoutSetRepo.add(statementLayoutSet);
    }

    //既存のレイアウトをコピーする場合
    private void cloneStatementLayoutSet(String histIdNew, String histIdClone) {
        Optional<StatementLayoutSet> cloneStatementLayoutSetOptional = statementLayoutSetRepo.getStatementLayoutSetById(histIdClone);

        if(cloneStatementLayoutSetOptional.isPresent()) {
            StatementLayoutSet cloneStatementLayoutSet = cloneStatementLayoutSetOptional.get();
            cloneStatementLayoutSet.setHistId(histIdNew);

            statementLayoutSetRepo.add(cloneStatementLayoutSet);
        } else {
            throw new BusinessException("sai me roi");
        }
    }

    private LineByLineSetting getNewLineTypePayment(String histId, int lineNumber, String statementCode) {
        PaymentItemDetailSet detail = new PaymentItemDetailSet(histId, statementCode, PaymentTotalObjAtr.OUTSIDE.value, PaymentProportionalAtr.NOT_PROPORTIONAL.value,
                null, PaymentCaclMethodAtr.MANUAL_INPUT.value, null, null, null, null, null);
        SettingByItemCustom item = new SettingByItemCustom(LAST_POSITION, statementCode, null, null, detail);
        List<SettingByItem> listSetByItem = new ArrayList<>();
        listSetByItem.add(item);

        return new LineByLineSetting(StatementPrintAtr.PRINT.value, lineNumber, listSetByItem);
    }

    private SettingByItem getNewItemTypeDeduction(String histId, int position, String statementCode, int totalObj) {
        DeductionItemDetailSet detail = new DeductionItemDetailSet(histId, statementCode, totalObj, PaymentProportionalAtr.NOT_PROPORTIONAL.value,
                null, PaymentCaclMethodAtr.MANUAL_INPUT.value, null, null, null, null, null);
        SettingByItem item = new SettingByItemCustom(position, statementCode, null, detail, null);

        return item;
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

        if((layoutPatternClone == StatementLayoutPattern.LASER_CRIMP_LANDSCAPE_ONE_PERSON.value) ||
                (layoutPatternClone == StatementLayoutPattern.DOT_PRINT_CONTINUOUS_PAPER_ONE_PERSON.value)) {
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
