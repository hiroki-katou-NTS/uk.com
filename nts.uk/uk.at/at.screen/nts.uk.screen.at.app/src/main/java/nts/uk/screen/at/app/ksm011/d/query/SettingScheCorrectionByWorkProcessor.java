package nts.uk.screen.at.app.ksm011.d.query;

import lombok.val;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.functioncontrol.DisplayCtrlPersonalConditionFinder;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.functioncontrol.DisplaySettingByWkpFinder;
import nts.uk.ctx.at.schedule.app.find.schedule.setting.functioncontrol.ScheFunctionCtrlByWorkplaceFinder;
import nts.uk.ctx.at.schedule.dom.displaysetting.DisplaySettingByWorkplace;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionCtrlByWorkplace;
import nts.uk.ctx.at.schedule.dom.workschedule.displaysetting.DisplayControlPersonalCondition;
import nts.uk.ctx.at.schedulealarm.app.query.alarmcheck.AlarmCheckConditionsQuery;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UKDesign.UniversalK.就業.KSM_スケジュールマスタ.KSM011_スケジュール前準備.D：スケジュール修正（職場別）の設定.メニュー別OCD.起動時
 * <<ScreenQuery>> スケジュール修正（職場別）の設定を取得する
 *
 * @author viet.tx
 */
@Stateless
public class SettingScheCorrectionByWorkProcessor {
    @Inject
    private DisplaySettingByWkpFinder displaySettingByWkpFinder;

    @Inject
    private ScheFunctionCtrlByWorkplaceFinder scheFuncCtrlByWorkplaceFinder;

    @Inject
    private DisplayCtrlPersonalConditionFinder displayCtrlPersonalCondFinder;

    @Inject
    private AlarmCheckConditionsQuery alarmCheckConditionsQuery;

    public SettingScheCorrectionByWorkDto getSettingSche() {
        // 1.
        Optional<DisplaySettingByWorkplace> displaySettingByWkp = displaySettingByWkpFinder.getDisplaySettingByWkp();

        // 2.
        Optional<ScheFunctionCtrlByWorkplace> scheFuncCtrlByWkp = scheFuncCtrlByWorkplaceFinder.getScheFuncCtrlByWorkplace();

        // 3.
        Optional<DisplayControlPersonalCondition> displayCtrlPersonalCondition = displayCtrlPersonalCondFinder.getDisplayCtrlPersonalCond();
        List<PersonInfoDisplayCtrlDto> conditionDisplayControls = new ArrayList<>();
        if (displayCtrlPersonalCondition.isPresent()) {
            conditionDisplayControls = displayCtrlPersonalCondition.get().getListConditionDisplayControl().stream()
                    .map(x -> new PersonInfoDisplayCtrlDto(x.getConditionATR().value, x.getConditionATR().name, x.getDisplayCategory().value))
                    .collect(Collectors.toList());
        }

        // 4.1. コードと名称と説明を取得する(コード) : 勤務予定のアラームチェック条件
        List<String> alarmCheckCodeList = new ArrayList<>();
        if (scheFuncCtrlByWkp.isPresent() && scheFuncCtrlByWkp.get().getCompletionMethodControl().isPresent()) {
            alarmCheckCodeList = scheFuncCtrlByWkp.get().getCompletionMethodControl().get().getAlarmCheckCodeList();
        }

        // 4.アラームチェック名称リストを取得する: Loop コード in スケジュール修正職場別の機能制御.完了方法制御.アラームチェックコードリスト
        List<AlarmCheckConditionDto> checkConditionList = new ArrayList<>();
        alarmCheckCodeList.forEach(code -> {
            val condition = alarmCheckConditionsQuery.getCodeNameDescription(code);
            checkConditionList.add(new AlarmCheckConditionDto(code, condition.getConditionName()));
        });

        return SettingScheCorrectionByWorkDto.getDto(displaySettingByWkp, scheFuncCtrlByWkp,
                conditionDisplayControls, checkConditionList);
    }
}
