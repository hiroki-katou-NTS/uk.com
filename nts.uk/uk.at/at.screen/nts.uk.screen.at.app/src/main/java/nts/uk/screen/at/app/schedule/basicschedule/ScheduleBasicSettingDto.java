package nts.uk.screen.at.app.schedule.basicschedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionControl;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeForm;
import org.apache.commons.lang3.BooleanUtils;

import java.util.List;

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
    public int changeableFluid;

    /**
     * フレックス勤務の利用区分
     */
    public int changeableFlex;

    /**
     * 勤務種類制御の利用区分
     */
    private int displayWorkTypeControl;

    /**
     * 勤務種類名称リスト
     */
    private List<WorkTypeNameDto> displayableWorkTypeCodeList;

    /**
     * 実績表示の利用区分
     */
    private int displayActual;

    public static ScheduleBasicSettingDto fromDomain(ScheFunctionControl domain, List<WorkTypeNameDto> workTypeNameList) {
        if (domain == null) return null;

        return new ScheduleBasicSettingDto(
                BooleanUtils.toInteger(domain.isChangeableForm(WorkTimeForm.FIXED))
                , BooleanUtils.toInteger(domain.isChangeableForm(WorkTimeForm.FLOW))
                , BooleanUtils.toInteger(domain.isChangeableForm(WorkTimeForm.FLEX))
                , domain.getDisplayWorkTypeControl().value
                , workTypeNameList
                , BooleanUtils.toInteger(domain.isDisplayActual())
        );
    }
}
