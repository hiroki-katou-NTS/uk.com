package nts.uk.ctx.at.schedule.app.command.schedule.setting.functioncontrol;

import lombok.Value;
import nts.uk.ctx.at.schedule.dom.displaysetting.functioncontrol.ScheFunctionControl;
import nts.uk.ctx.at.schedule.dom.schedule.support.SupportFunctionControl;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeForm;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * スケジュール修正の機能制御を登
 * UKDesign.UniversalK.就業.KSM_スケジュールマスタ.KSM011_スケジュール前準備.C：スケジュール基本の設定.メニュー別OCD.登録時
 *
 * @author viet.tx
 */
@Value
public class RegisterScheFuncCtrlCommand {
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
    private List<String> displayableWorkTypeCodeList;

    /**
     * 実績表示の利用区分
     */
    private int displayActual;

    /** 応援予定を利用するか **/
    private int use;

    /** 時間帯応援を利用するか **/
    private boolean useSupportInTimezone;

    public ScheFunctionControl toDomain(List<WorkType> lstWorkType) {
        List<WorkTimeForm> lstWork = new ArrayList<>();
        if (this.changeableFix == 1)
            lstWork.add(WorkTimeForm.FIXED);

        if (this.changeableFlex == 1)
            lstWork.add(WorkTimeForm.FLEX);

        if (this.changeableFluid == 1)
            lstWork.add(WorkTimeForm.FLOW);

        List<WorkTypeCode> workTypeCodes = new ArrayList<>();
        lstWorkType.forEach(x -> this.getDisplayableWorkTypeCodeList().forEach(code -> {
            if(x.getWorkTypeCode().equals(code)){
                workTypeCodes.add(new WorkTypeCode(x.getWorkTypeCode().v()));
            }
        }));

        return new ScheFunctionControl(
                lstWork,
                (this.displayActual == 1),
                NotUseAtr.valueOf(this.displayWorkTypeControl),
                workTypeCodes);
    }

    public SupportFunctionControl toSupportFunc(){
        return new SupportFunctionControl(use==1,useSupportInTimezone);
    }
}
