package nts.uk.ctx.at.request.dom.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.申請承認.申請.残業休出申請.残業申請.複数回残業内容
 * 複数回残業内容
 */
@Getter
@AllArgsConstructor
public class OvertimeWorkMultipleTimes {
    // 残業時間帯
    private List<OvertimeHour> overtimeHours;

    // 残業理由
    private List<OvertimeReason> overtimeReasons;
}
