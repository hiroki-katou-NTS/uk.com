package nts.uk.screen.at.app.schedule.basicschedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionControl;
import nts.uk.ctx.at.schedule.dom.schedule.support.SupportFunctionControl;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeForm;
import org.apache.commons.lang3.BooleanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author viet.tx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleBasicSettingDto {
    /**
     * 通常勤務の利用区分
     */
    public int changeableFix;

    /**
     * 流動勤務の利用区分
     */
    public int changeableFluid = 1;

    /**
     * フレックス勤務の利用区分
     */
    public int changeableFlex = 1;

    /**
     * 勤務種類制御の利用区分
     */
    private int displayWorkTypeControl;

    /**
     * 勤務種類名称リスト
     */
    private List<String> workTypeCodeList;

    /**
     * 実績表示の利用区分
     */
    private int displayActual;

    private List<WorkTypeNameDto> displayableWorkTypeList;

    /** 応援予定を利用するか **/
    private int use;
    /** 時間帯応援を利用するか **/
    private boolean useSupportInTimezone;

    public static ScheduleBasicSettingDto fromDomain(ScheFunctionControl domain, List<WorkTypeNameDto> workTypeNameList, SupportFunctionControl supportFunctionControl) {
        if (domain == null) {
            ScheduleBasicSettingDto dto = new ScheduleBasicSettingDto();
            dto.setDisplayableWorkTypeList(workTypeNameList);
            return dto;
        }
        if (supportFunctionControl == null){
            supportFunctionControl = new SupportFunctionControl(false,false);
        }
        return new ScheduleBasicSettingDto(
                BooleanUtils.toInteger(domain.isChangeableForm(WorkTimeForm.FIXED)),
                BooleanUtils.toInteger(domain.isChangeableForm(WorkTimeForm.FLOW)),
                BooleanUtils.toInteger(domain.isChangeableForm(WorkTimeForm.FLEX)),
                domain.getDisplayWorkTypeControl().value,
                domain.getDisplayableWorkTypeCodeList().stream().map(i -> i.v()).collect(Collectors.toList()),
                BooleanUtils.toInteger(domain.isDisplayActual()),
                workTypeNameList,supportFunctionControl.isUse() ? 1 :0,supportFunctionControl.isUseSupportInTimezone()

        );
    }
}
