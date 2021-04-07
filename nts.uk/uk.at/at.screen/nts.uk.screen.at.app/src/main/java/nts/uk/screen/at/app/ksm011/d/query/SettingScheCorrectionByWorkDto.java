package nts.uk.screen.at.app.ksm011.d.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByWorkplace;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.*;
import org.apache.commons.lang3.BooleanUtils;

import java.util.List;
import java.util.Optional;

@Getter
@AllArgsConstructor
public class SettingScheCorrectionByWorkDto {
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
    private List<AlarmCheckConditionDto> alarmConditions;

    // 利用する項目一覧
    private List<PersonInfoDisplayCtrlDto> conditionDisplayControls;

    public static SettingScheCorrectionByWorkDto getDto(Optional<DisplaySettingByWorkplace> displaySettingByWkp, Optional<ScheFunctionCtrlByWorkplace> scheFuncCtrlByWkp,
                                                        List<PersonInfoDisplayCtrlDto> conditionDisplayControls, List<AlarmCheckConditionDto> checkConditionList) {
        return new SettingScheCorrectionByWorkDto(
                displaySettingByWkp.get().getInitDispMonth().value
                , displaySettingByWkp.get().getEndDay().getClosingDate().getDay()
                , BooleanUtils.toInteger(scheFuncCtrlByWkp.get().isUseDisplayFormat(FuncCtrlDisplayFormat.WorkInfo))
                , BooleanUtils.toInteger(scheFuncCtrlByWkp.get().isUseDisplayFormat(FuncCtrlDisplayFormat.AbbreviatedName))
                , BooleanUtils.toInteger(scheFuncCtrlByWkp.get().isUseDisplayFormat(FuncCtrlDisplayFormat.Shift))
                , BooleanUtils.toInteger(scheFuncCtrlByWkp.get().isUseDisplayPeriod(FuncCtrlDisplayPeriod.TwentyEightDayCycle))
                , BooleanUtils.toInteger(scheFuncCtrlByWkp.get().isUseDisplayPeriod(FuncCtrlDisplayPeriod.LastDayUtil))
                , BooleanUtils.toInteger(scheFuncCtrlByWkp.get().isStartControl(FuncCtrlStartControl.ByDate))
                , scheFuncCtrlByWkp.get().getUseCompletionAtr().value
                , scheFuncCtrlByWkp.get().getCompletionMethodControl().get().getCompletionExecutionMethod().value
                , BooleanUtils.toInteger(scheFuncCtrlByWkp.get().getCompletionMethodControl().get().isCompletionMethodControl(FuncCtrlCompletionMethod.Confirm))
                , BooleanUtils.toInteger(scheFuncCtrlByWkp.get().getCompletionMethodControl().get().isCompletionMethodControl(FuncCtrlCompletionMethod.AlarmCheck))
                , checkConditionList
                , conditionDisplayControls
        );
    }
}
