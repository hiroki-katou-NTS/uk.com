package nts.uk.ctx.at.function.app.find.alarmworkplace;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlace;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class WkpAlarmPatternSettingDto {
    /**
     * アラームリストパターンコード
     */
    private String alarmPatternCD;
    /**
     * アラームリストパターン名称
     */
    private String alarmPatternName;

    /**
     * アラームリスト権限設定
     */
    private WkpAlarmPermissionSettingDto alarmPerSet;

    /**
     * チェック条件
     */
    private List<WkpCheckConditionDto> checkConList;

    public static WkpAlarmPatternSettingDto setData(AlarmPatternSettingWorkPlace domain) {
        List<WkpCheckConditionDto> conditionDtos = domain.getCheckConList().stream().map(x -> {
            List<String> lstCode = x.getCheckConditionLis().stream().map(PrimitiveValueBase::v).collect(Collectors.toList());
            return WkpCheckConditionDto.setdata(x,lstCode);
        }).collect(Collectors.toList());

        return new WkpAlarmPatternSettingDto(
            domain.getAlarmPatternCD().v(),
            domain.getAlarmPatternName().v(),
            new WkpAlarmPermissionSettingDto(domain.getAlarmPerSet().isAuthSetting() ? 1: 0,domain.getAlarmPerSet().getRoleIds()),
            conditionDtos);
    }
}
