package nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkpjob;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.ComAutoCalSettingExport;

@Data
public class WkpJobAutoCalSettingExport extends ComAutoCalSettingExport{
    private String workPlaceCode;
    private String workPlaceName;
    private String positionCode;
    private String positionName;

    public WkpJobAutoCalSettingExport(String workPlaceCode, String workPlaceName, String positionCode, String positionName, int earlyOtTimeLimit, int earlyMidOtTimeLimit, int normalOtTimeLimit, int normalMidOtTimeLimit, int legarOtTimeLimit, int legarMidOtTimeLimit, int flexOtTimeLimit, int restTimeLimit, int lateNightTimeLimit, int legarOtTimeAtr, int earlyOtTimeAtr, int earlyMidOtTimeAtr, int normalOtTimeAtr, int normalMidOtTimeAtr, int legarMidOtTimeAtr, int flexOtTimeAtr, int restTimeAtr, int lateNightTimeAtr, int raiSingCalcAtr, int specificRaisingCalcAtr, int leaveEarly, int leaveLate, int divergence) {
        super(earlyOtTimeLimit, earlyMidOtTimeLimit, normalOtTimeLimit, normalMidOtTimeLimit, legarOtTimeLimit, legarMidOtTimeLimit, flexOtTimeLimit, restTimeLimit, lateNightTimeLimit, legarOtTimeAtr, earlyOtTimeAtr, earlyMidOtTimeAtr, normalOtTimeAtr, normalMidOtTimeAtr, legarMidOtTimeAtr, flexOtTimeAtr, restTimeAtr, lateNightTimeAtr, raiSingCalcAtr, specificRaisingCalcAtr, leaveEarly, leaveLate, divergence);
        this.workPlaceCode = workPlaceCode;
        this.workPlaceName = workPlaceName;
        this.positionCode = positionCode;
        this.positionName = positionName;
    }
}
