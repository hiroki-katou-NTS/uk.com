package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.FixedCheckSDailyItems;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.FixedExtractionSDailyCon;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmMessage;

import java.util.Optional;

/**
 * AggregateRoot: スケジュール月次の固有抽出条件
 *
 */
@Getter
@AllArgsConstructor
public class FixedExtractionSMonCon extends AggregateRoot {
    /**
     * 職場のエラーアラームチェックID
     */
    private String errorAlarmWorkplaceId;

    /**
     * No
     */
    private FixedCheckSMonItems fixedCheckSMonItems;

    /**
     *メッセージ
     */
    private Optional<ErrorAlarmMessage> messageDisp;

    /**使用区分*/
    private boolean useAtr;

    public static FixedExtractionSMonCon create(String errorAlarmWorkplaceId,
            int fixedCheckDayItems,
            String messageDisp, Boolean useAtr) {

	return new FixedExtractionSMonCon(errorAlarmWorkplaceId,
			EnumAdaptor.valueOf(fixedCheckDayItems, FixedCheckSMonItems.class),
			Optional.ofNullable(messageDisp == null? null :new ErrorAlarmMessage(messageDisp)),useAtr);
	}
}
