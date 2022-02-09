package nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkOutputDataRequire;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 応援勤務明細
 */
@AllArgsConstructor
@Getter
public class SupportWorkDetails {
    /**
     * 社員ID
     */
    private String employeeId;

    /**
     * 年月日
     */
    private GeneralDate date;

    /**
     * 応援勤務枠No
     */
    private int supportWorkFrameNo;

    /**
     * 所属先情報
     */
    private String affiliationInfo;

    /**
     * 勤務先情報
     */
    private String workInfo;

    /**
     * 項目一覧
     */
    private List<ItemValue> itemList;

    /**
     * 応援勤務か
     */
    @Setter
    private boolean supportWork;

    /**
     * [C-1] 新規作成
     * @param employeeId
     * @param date
     * @param affiliationInfor
     * @param workInfor
     * @param attendanceItemIds
     * @param ouenWorkTimeSheetOfDaily
     * @param ouenWorkTimeOfDaily
     */
    public static SupportWorkDetails create(String employeeId,
                                            GeneralDate date,
                                            String affiliationInfor,
                                            String workInfor,
                                            List<Integer> attendanceItemIds,
                                            OuenWorkTimeSheetOfDailyAttendance ouenWorkTimeSheetOfDaily,
                                            OuenWorkTimeOfDailyAttendance ouenWorkTimeOfDaily) {
        List<ItemValue> itemList = getItemList(attendanceItemIds, ouenWorkTimeSheetOfDaily, ouenWorkTimeOfDaily);
        return new SupportWorkDetails(
                employeeId,
                date,
                ouenWorkTimeSheetOfDaily.getWorkNo().v(),
                affiliationInfor,
                workInfor,
                itemList,
                false
        );
    }

    /**
     * [prv-1] 項目値を作成する
     * 説明:勤怠項目IDに対応する応援作業時間帯,応援作業時間の値をマッピングする
     */
    private static List<ItemValue> getItemList(List<Integer> attendanceItemIds,
                                               OuenWorkTimeSheetOfDailyAttendance ouenWorkTimeSheetOfDaily,
                                               OuenWorkTimeOfDailyAttendance ouenWorkTimeOfDaily) {
        List<ItemValue> result = new ArrayList<>();

        attendanceItemIds.forEach(itemId -> {
            ItemValue itemValue = null;
            switch (itemId) {
                case 924:
                    itemValue = ItemValue.builder()
                            .itemId(itemId)
                            .value(ouenWorkTimeSheetOfDaily.getWorkContent().getWork().map(i -> i.getWorkCD1().v()).orElse(null))
                            .valueType(ValueType.TEXT)
                            .completed();
                    break;
                case 925:
                    itemValue = ItemValue.builder()
                            .itemId(itemId)
                            .value(ouenWorkTimeSheetOfDaily.getWorkContent().getWork().map(i -> i.getWorkCD2().map(PrimitiveValueBase::v).orElse(null)).orElse(null))
                            .valueType(ValueType.TEXT)
                            .completed();
                    break;
                case 926:
                    itemValue = ItemValue.builder()
                            .itemId(itemId)
                            .value(ouenWorkTimeSheetOfDaily.getWorkContent().getWork().map(i -> i.getWorkCD3().map(PrimitiveValueBase::v).orElse(null)).orElse(null))
                            .valueType(ValueType.TEXT)
                            .completed();
                    break;
                case 927:
                    itemValue = ItemValue.builder()
                            .itemId(itemId)
                            .value(ouenWorkTimeSheetOfDaily.getWorkContent().getWork().map(i -> i.getWorkCD4().map(PrimitiveValueBase::v).orElse(null)).orElse(null))
                            .valueType(ValueType.TEXT)
                            .completed();
                    break;
                case 928:
                    itemValue = ItemValue.builder()
                            .itemId(itemId)
                            .value(ouenWorkTimeSheetOfDaily.getWorkContent().getWork().map(i -> i.getWorkCD5().map(PrimitiveValueBase::v).orElse(null)).orElse(null))
                            .valueType(ValueType.TEXT)
                            .completed();
                    break;
                case 929:
                    itemValue = ItemValue.builder()
                            .itemId(itemId)
                            .value(ouenWorkTimeSheetOfDaily.getTimeSheet().getStartTimeWithDayAttr().map(PrimitiveValueBase::v).orElse(null))
                            .valueType(ValueType.TIME_WITH_DAY)
                            .completed();
                    break;
                case 930:
                    itemValue = ItemValue.builder()
                            .itemId(itemId)
                            .value(ouenWorkTimeSheetOfDaily.getTimeSheet().getEndTimeWithDayAttr().map(PrimitiveValueBase::v).orElse(null))
                            .valueType(ValueType.TIME_WITH_DAY)
                            .completed();
                    break;
                case 1305:
                    itemValue = ItemValue.builder()
                            .itemId(itemId)
                            .value(ouenWorkTimeOfDaily == null ? null : ouenWorkTimeOfDaily.getWorkTime().getTotalTime().v())
                            .valueType(ValueType.TIME)
                            .completed();
                    break;
                case 1306:
                    itemValue = ItemValue.builder()
                            .itemId(itemId)
                            .value(ouenWorkTimeOfDaily == null ? null : ouenWorkTimeOfDaily.getWorkTime().getWithinTime().v())
                            .valueType(ValueType.TIME)
                            .completed();
                    break;
                case 1309:
                    itemValue = ItemValue.builder()
                            .itemId(itemId)
                            .value(ouenWorkTimeOfDaily == null ? null : ouenWorkTimeOfDaily.getAmount().v())
                            .valueType(ValueType.AMOUNT_NUM)
                            .completed();
                    break;
                case 1336:
                    itemValue = ItemValue.builder()
                            .itemId(itemId)
                            .value(ouenWorkTimeOfDaily == null ? null : ouenWorkTimeOfDaily.getMoveTime().getTotalMoveTime().v())
                            .valueType(ValueType.TIME)
                            .completed();
                    break;
                case 2191:
                    itemValue = ItemValue.builder()
                            .itemId(itemId)
                            .value(ouenWorkTimeOfDaily == null ? null : ouenWorkTimeOfDaily.getWorkTime().getPremiumTime().getTotalWorkingTime().v())
                            .valueType(ValueType.TIME)
                            .completed();
                    break;
                default:
                    break;
            }
            if (itemValue != null) {
                result.add(itemValue);
            }
        });

        return result;
    }

}
