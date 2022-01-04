package nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkOutputDataRequire;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;

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
     * @param require
     * @param employeeId
     * @param date
     * @param supportWorkFrameNo
     * @param affiliationInfor
     * @param workInfor
     * @param attendanceItemIds
     * @param ouenWorkTimeSheetOfDaily
     * @param ouenWorkTimeOfDaily
     */
    public static SupportWorkDetails create(SupportWorkOutputDataRequire require,
                                            String employeeId,
                                            GeneralDate date,
                                            int supportWorkFrameNo,
                                            String affiliationInfor,
                                            String workInfor,
                                            List<Integer> attendanceItemIds,
                                            OuenWorkTimeSheetOfDailyAttendance ouenWorkTimeSheetOfDaily,
                                            OuenWorkTimeOfDailyAttendance ouenWorkTimeOfDaily) {
        List<ItemValue> itemList = getItemList(require, attendanceItemIds, ouenWorkTimeSheetOfDaily, ouenWorkTimeOfDaily);
        return new SupportWorkDetails(
                employeeId,
                date,
                supportWorkFrameNo,
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
    private static List<ItemValue> getItemList(SupportWorkOutputDataRequire require,
                                               List<Integer> attendanceItemIds,
                                               OuenWorkTimeSheetOfDailyAttendance ouenWorkTimeSheetOfDaily,
                                               OuenWorkTimeOfDailyAttendance ouenWorkTimeOfDaily) {
        return require.createDailyConverter()
                .withOuenSheet(ouenWorkTimeSheetOfDaily == null ? Collections.emptyList() : Arrays.asList(ouenWorkTimeSheetOfDaily))
                .withOuenWorkTime(ouenWorkTimeOfDaily == null ? Collections.emptyList() : Arrays.asList(ouenWorkTimeOfDaily))
                .convert(attendanceItemIds);
    }

}
