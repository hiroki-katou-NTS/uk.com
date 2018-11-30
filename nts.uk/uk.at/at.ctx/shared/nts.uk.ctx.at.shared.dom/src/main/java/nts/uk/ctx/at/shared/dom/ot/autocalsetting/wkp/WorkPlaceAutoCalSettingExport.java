package nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkp;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.ComAutoCalSettingExport;

@Data
public class WorkPlaceAutoCalSettingExport extends ComAutoCalSettingExport{
    private String workPlaceCode;
    private String workPlaceName;
    private String registered;

    public WorkPlaceAutoCalSettingExport(String workPlaceCode, String workPlaceName, String registered, int earlyOtTimeLimit, int earlyMidOtTimeLimit, int normalOtTimeLimit, int normalMidOtTimeLimit, int legarOtTimeLimit, int legarMidOtTimeLimit, int flexOtTimeLimit, int restTimeLimit, int lateNightTimeLimit, int lateOtTimeAtr, int earlyOtTimeAtr, int earlyMidOtTimeAtr, int normalOtTimeAtr, int normalMidOtTimeAtr, int legarMidOtTimeAtr, int flexOtTimeAtr, int restTimeAtr, int lateNightTimeAtr, int raiSingCalcAtr, int specificRaisingCalcAtr, int leaveEarly, int leaveLate, int divergence) {
        super(earlyOtTimeLimit, earlyMidOtTimeLimit, normalOtTimeLimit, normalMidOtTimeLimit, legarOtTimeLimit, legarMidOtTimeLimit, flexOtTimeLimit, restTimeLimit, lateNightTimeLimit, lateOtTimeAtr, earlyOtTimeAtr, earlyMidOtTimeAtr, normalOtTimeAtr, normalMidOtTimeAtr, legarMidOtTimeAtr, flexOtTimeAtr, restTimeAtr, lateNightTimeAtr, raiSingCalcAtr, specificRaisingCalcAtr, leaveEarly, leaveLate, divergence);
        this.workPlaceCode = workPlaceCode;
        this.workPlaceName = workPlaceName;
        this.registered = registered;
    }
}
