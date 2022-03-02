package nts.uk.ctx.at.shared.dom.scherec.anyperiodattdcal;

import nts.uk.ctx.at.shared.dom.scherec.anyperiod.attendancetime.converter.AnyPeriodRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.service.AttendanceItemConvertFactory;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.AttendanceTimeOfAnyPeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * 修正後の任意期間別実績を作成する
 */
public class AnyPeriodActualResultCorrectionService {
    /**
     * 作成する
     * @param require
     * @param anyPeriodTotalFrameCode 任意集計枠コード
     * @param employeeId 対象社員
     * @param items 編集項目値リスト
     * @return 任意期間実績の修正詳細
     */
    public static AnyPeriodActualResultCorrectionDetail create(Require require, String anyPeriodTotalFrameCode, String employeeId, List<ItemValue> items) {
        AttendanceTimeOfAnyPeriod beforeCorrection = require.find(employeeId, anyPeriodTotalFrameCode).orElse(null);

        AnyPeriodRecordToAttendanceItemConverter converter = require.createOptionalItemConverter()
                .withBase(employeeId)
                .withAttendanceTime(beforeCorrection)
                .completed();
        converter.merge(items);
        Optional<AttendanceTimeOfAnyPeriod> afterCorrection = converter.toAttendanceTime();

        // $計算後の勤怠時間 = 計算処理を実行する
        // ※計算処理まだないため、一旦$修正後の勤怠時間をセットする
        AttendanceTimeOfAnyPeriod afterCalculation = afterCorrection.orElse(null);

        return new AnyPeriodActualResultCorrectionDetail(
                afterCorrection.orElse(null),
                afterCalculation
        );
    }

    public interface Require {
        Optional<AttendanceTimeOfAnyPeriod> find(String employeeId, String frameCode);

        AnyPeriodRecordToAttendanceItemConverter createOptionalItemConverter();
    }
}
