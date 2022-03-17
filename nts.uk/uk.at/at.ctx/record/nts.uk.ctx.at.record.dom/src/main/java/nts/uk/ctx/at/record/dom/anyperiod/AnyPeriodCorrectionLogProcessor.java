package nts.uk.ctx.at.record.dom.anyperiod;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriod;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriodRepository;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.security.audittrail.correction.content.*;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionLogProcessorContext;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;
import nts.uk.shr.com.security.audittrail.correction.processor.DataCorrectionLogProcessor;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 任意期間別実績の修正記録
 */
@Stateless
public class AnyPeriodCorrectionLogProcessor extends DataCorrectionLogProcessor {
    @Inject
    private AnyAggrPeriodRepository anyAggrPeriodRepo;

    @Inject
    private MonthlyAttendanceItemRepository monthlyItemRepo;

    @Override
    public CorrectionProcessorId getId() {
        return CorrectionProcessorId.ANY_PERIOD_AGGREGATION;
    }

    @Override
    protected void buildLogContents(CorrectionLogProcessorContext correctionLogProcessorContext) {
        AnyPeriodCorrectionLogParameter parameter = correctionLogProcessorContext.getParameter();
        List<AnyPeriodLogContent> contents = parameter.getContents();
        List<AnyPeriodCorrection> targetInfoList = this.createParameter(contents);
        targetInfoList.forEach(info -> {
            correctionLogProcessorContext.addCorrection(info);
        });
    }

    /**
     * [prv-1] データ修正記録のパラメータを作成する
     * @param contents 修正詳細リスト
     * @return 対象情報リスト
     */
    private List<AnyPeriodCorrection> createParameter(List<AnyPeriodLogContent> contents) {
        List<AnyPeriodCorrection> result = new ArrayList<>();
        List<String> employeeIds = contents.stream().map(AnyPeriodLogContent::getEmployeeId).distinct().collect(Collectors.toList());
        Map<String, UserInfo> userMap = this.userInfoAdaptor.findByEmployeeId(employeeIds)
                .stream().collect(Collectors.toMap(UserInfo::getEmployeeId, Function.identity()));
        contents.forEach(content -> {
            UserInfo userInfo = userMap.getOrDefault(content.getEmployeeId(), new UserInfo("", "", ""));
            Optional<AnyAggrPeriod> anyAggrPeriod = anyAggrPeriodRepo.findOneByCompanyIdAndFrameCode(AppContexts.user().companyId(), content.getAnyPeriodFrameCode());
            List<AnyPeriodCorrection> targetInfos = this.createTargetInfoList(userInfo, content, anyAggrPeriod);
            result.addAll(targetInfos);
        });
        return result;
    }

    /**
     * [prv-2] 対象情報リストを作成する
     * @param userInfo ユーザ情報
     * @param content 修正詳細
     * @return 対象情報リスト
     */
    private List<AnyPeriodCorrection> createTargetInfoList(UserInfo userInfo, AnyPeriodLogContent content, Optional<AnyAggrPeriod> anyAggrPeriod) {
        List<AnyPeriodCorrection> result = new ArrayList<>();
        Set<Integer> itemIds = content.getCorrectedList().stream().map(i -> Integer.parseInt(i.getId())).collect(Collectors.toSet());
        itemIds.addAll(content.getCalculatedList().stream().map(i -> Integer.parseInt(i.getId())).collect(Collectors.toSet()));
        Map<Integer, MonthlyAttendanceItem> attItemMap = monthlyItemRepo.findByAttendanceItemId(AppContexts.user().companyId(), new ArrayList<>(itemIds))
                .stream().collect(Collectors.toMap(MonthlyAttendanceItem::getAttendanceItemId, Function.identity()));
        content.getCorrectedList().forEach(i -> {
            MonthlyAttendanceItem attItem = attItemMap.get(Integer.parseInt(i.getId()));
            AnyPeriodCorrection data = createTargetInformation(
                    userInfo,
                    content.getAnyPeriodFrameCode(),
                    CorrectionAttr.EDIT,
                    i,
                    anyAggrPeriod,
                    attItem != null ? attItem.getDisplayNumber() : 0
            );
            result.add(data);
        });
        content.getCalculatedList().forEach(i -> {
            MonthlyAttendanceItem attItem = attItemMap.get(Integer.parseInt(i.getId()));
            AnyPeriodCorrection data = createTargetInformation(
                    userInfo,
                    content.getAnyPeriodFrameCode(),
                    CorrectionAttr.CALCULATE,
                    i,
                    anyAggrPeriod,
                    attItem != null ? attItem.getDisplayNumber() : 0
            );
            result.add(data);
        });
        return result;
    }

    /**
     * [prv-3] 対象情報を作成する
     * @param targetUser ユーザ情報
     * @param anyPeriodFrameCode 任意集計枠コード
     * @param correctionAttr 修正区分
     * @param itemInfo 項目情報
     * @return 対象情報
     */
    private AnyPeriodCorrection createTargetInformation(UserInfo targetUser, String anyPeriodFrameCode, CorrectionAttr correctionAttr, ItemInfo itemInfo, Optional<AnyAggrPeriod> anyAggrPeriod, int dispOrder) {
        GeneralDate date = anyAggrPeriod.isPresent() ? anyAggrPeriod.get().getPeriod().start() : GeneralDate.today();

        return new AnyPeriodCorrection(
                targetUser,
                date,
                anyPeriodFrameCode,
                correctionAttr,
                itemInfo,
                dispOrder
        );
    }
}
