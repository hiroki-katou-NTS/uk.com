package nts.uk.screen.at.app.kdl035;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TargetSelectionAtr;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Kdl035OutputData {
    private String employeeId;

    // 対象選択区分
    private int targetSelectionAtr;

    // 振休日リスト
    private List<GeneralDate> substituteHolidayList;

    // 振出情報一覧
    private List<SubstituteWorkData> substituteWorkInfoList;

    // 日数単位
    private double daysUnit;
}
