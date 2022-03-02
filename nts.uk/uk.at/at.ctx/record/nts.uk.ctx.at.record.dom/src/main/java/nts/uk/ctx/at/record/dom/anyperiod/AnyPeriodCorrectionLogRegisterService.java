package nts.uk.ctx.at.record.dom.anyperiod;

import nts.uk.ctx.at.shared.dom.scherec.anyperiod.attendancetime.converter.AnyPeriodRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal.AnyPeriodActualResultCorrectionDetail;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.AttendanceItemIdContainer;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.shr.com.security.audittrail.correction.DataCorrectionContext;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 任意期間別実績の修正記録を登録する
 */
public class AnyPeriodCorrectionLogRegisterService {
    /**
     * [1] 登録する
     * @param require
     * @param companyId 会社ID
     * @param correctionDetails 修正詳細リスト
     */
    public static void register(Require require, String companyId, List<AnyPeriodActualResultCorrectionDetail> correctionDetails) {
        List<AnyPeriodLogContent> contents = correctionDetails.stream()
                .map(d -> createLogContent(require, companyId, d))
                .collect(Collectors.toList());

        DataCorrectionContext.transactional(CorrectionProcessorId.ANY_PERIOD_AGGREGATION, () -> {
            AnyPeriodCorrectionLogParameter parameter = new AnyPeriodCorrectionLogParameter(contents);
            DataCorrectionContext.setParameter(parameter);
        });
    }

    /**
     * [prv-1] 任意実績のログ登録内容を作成する
     * @param require
     * @param companyId 会社ID
     * @param correctionDetail 修正詳細
     * @return 任意実績のログ登録内容
     */
    private static AnyPeriodLogContent createLogContent(Require require, String companyId, AnyPeriodActualResultCorrectionDetail correctionDetail) {
        String employeeId = correctionDetail.getAfterCorrection().getEmployeeId();
        String frameCode = correctionDetail.getAfterCorrection().getAnyAggrFrameCode().v();

        AttendanceTimeOfAnyPeriod beforeCorrection = require.find(frameCode, employeeId).orElse(null);

        AnyPeriodRecordToAttendanceItemConverter converter = require.createOptionalItemConverter();
        List<Integer> itemIds = AttendanceItemIdContainer.getIds(AttendanceItemUtil.AttendanceItemType.ANY_PERIOD_ITEM)
                .stream().map(ItemValue::getItemId).collect(Collectors.toList());
        List<ItemValue> beforeCorrectItems = converter.withBase(employeeId).withAttendanceTime(beforeCorrection).convert(itemIds);
        List<ItemValue> afterCorrectItems = converter.withBase(employeeId).withAttendanceTime(correctionDetail.getAfterCorrection()).convert(itemIds);
        List<ItemValue> afterCalculateItems = converter.withBase(employeeId).withAttendanceTime(correctionDetail.getAfterCalculation()).convert(itemIds);

        List<ItemInfo> manualItems = CorrectionLogInfoItemCreateService.create(
                require,
                beforeCorrectItems,
                afterCorrectItems
        );
        List<ItemInfo> calculatedItems = CorrectionLogInfoItemCreateService.create(
                require,
                afterCorrectItems,
                afterCalculateItems
        );
        return new AnyPeriodLogContent(
                companyId,
                correctionDetail.getAfterCorrection().getEmployeeId(),
                correctionDetail.getAfterCorrection().getAnyAggrFrameCode().v(),
                manualItems,
                calculatedItems
        );
    }

    public interface Require extends CorrectionLogInfoItemCreateService.Require {
        Optional<AttendanceTimeOfAnyPeriod> find(String frameCode, String employeeId);

        AnyPeriodRecordToAttendanceItemConverter createOptionalItemConverter();
    }
}
