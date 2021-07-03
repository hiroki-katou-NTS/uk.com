package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmMessage;

import java.util.Optional;

/**
 * AggregateRoot: スケジュール日次の固有抽出条件
 *
 */
@Getter
@AllArgsConstructor
public class FixedExtractionSDailyCon extends AggregateRoot {
    /**
     * 職場のエラーアラームチェックID
     */
    private String errorAlarmWorkplaceId;

    /**
     * No
     */
    private FixedCheckSDailyItems fixedCheckDayItems;

    /**
     *メッセージ
     */
    private Optional<ErrorAlarmMessage> messageDisp;

    /**使用区分*/
    private boolean useAtr;

    /**
     * 作成する
     *
     * @param errorAlarmWorkplaceId 職場のエラーアラームチェックID
     * @param fixedCheckDayItems    No
     * @param messageDisp           表示するメッセージ
     */
    public static FixedExtractionSDailyCon create(String errorAlarmWorkplaceId,
                                                  int fixedCheckDayItems,
                                                  String messageDisp, Boolean useAtr) {

        return new FixedExtractionSDailyCon(errorAlarmWorkplaceId,
                EnumAdaptor.valueOf(fixedCheckDayItems, FixedCheckSDailyItems.class),
                Optional.ofNullable(messageDisp == null? null :new ErrorAlarmMessage(messageDisp)),useAtr);
    }
}
