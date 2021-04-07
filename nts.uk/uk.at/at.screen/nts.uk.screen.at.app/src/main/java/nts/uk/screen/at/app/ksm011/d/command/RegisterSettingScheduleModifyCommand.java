package nts.uk.screen.at.app.ksm011.d.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.*;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterSettingScheduleModifyCommand {
    // 対象年月の利用区分
    private int initDispMonth;

    // 締め日
    private int endDay;

    // 略名表示の利用区分    (FuncCtrlDisplayFormat.WorkInfo)
    public int modeFull;

    // 勤務表示の利用区分    (FuncCtrlDisplayFormat.AbbreviatedName)
    public int modeAbbr;

    // シフト表示の利用区分   (FuncCtrlDisplayFormat.Shift)
    private int modeShift;

    // 28日周期の利用区分
    private int display28days;

    // 末日表示の利用区分
    private int display1month;

    // 日付別表示の利用区分
    private int openDispByDate;

    // 完了機能の利用区分
    private int useCompletion;

    // 完了実行方法区分
    private Integer completionMethod;

    // 確定の利用区分
    private Integer confirmUsage;

    // アラームチェックの利用区分
    private Integer alarmCheckUsage;

    // アラーム条件リスト
    private List<String> alarmConditions;

    // 利用する項目一覧
    private List<Integer> conditionDisplayControls;

    public ScheFunctionCtrlByWorkplace toScheFuncCtrlByWkpDomain() {
        List<FuncCtrlDisplayPeriod> lstDisplayPeriod = new ArrayList<>();
        if (this.display28days == 1)
            lstDisplayPeriod.add(FuncCtrlDisplayPeriod.TwentyEightDayCycle);

        if (this.display1month == 1)
            lstDisplayPeriod.add(FuncCtrlDisplayPeriod.LastDayUtil);

        List<FuncCtrlDisplayFormat> lstDisplayFormat = new ArrayList<>();
        if (this.modeAbbr == 1)
            lstDisplayFormat.add(FuncCtrlDisplayFormat.AbbreviatedName);

        if (this.modeFull == 1)
            lstDisplayFormat.add(FuncCtrlDisplayFormat.WorkInfo);

        if (this.modeShift == 1)
            lstDisplayFormat.add(FuncCtrlDisplayFormat.Shift);

        List<FuncCtrlStartControl> lstStartControl = new ArrayList<>();
        if (this.openDispByDate == 1)
            lstStartControl.add(FuncCtrlStartControl.ByDate);

        List<FuncCtrlCompletionMethod> lstCompletionMethod = new ArrayList<>();
        if (this.confirmUsage == 1)
            lstCompletionMethod.add(FuncCtrlCompletionMethod.Confirm);

        if (this.alarmCheckUsage == 1)
            lstCompletionMethod.add(FuncCtrlCompletionMethod.AlarmCheck);

        Optional<CompletionMethodControl> completionMethodCtrl =
                Optional.of(CompletionMethodControl.create(
                        this.completionMethod == 0
                                ? FuncCtrlCompletionExecutionMethod.SelectAtRuntime
                                : FuncCtrlCompletionExecutionMethod.SettingBefore,
                        lstCompletionMethod,
                        alarmConditions
                ));

        return ScheFunctionCtrlByWorkplace.create(
                lstDisplayPeriod,
                lstDisplayFormat,
                lstStartControl,
                NotUseAtr.valueOf(this.useCompletion),
                completionMethodCtrl);
    }
}
