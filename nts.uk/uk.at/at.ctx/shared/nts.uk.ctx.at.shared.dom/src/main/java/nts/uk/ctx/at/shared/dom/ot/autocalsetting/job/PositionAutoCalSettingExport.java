package nts.uk.ctx.at.shared.dom.ot.autocalsetting.job;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.ComAutoCalSettingExport;

@Data
public class PositionAutoCalSettingExport extends ComAutoCalSettingExport{
    private String positionCode;
    private String positionName;
    private String registered;

    public PositionAutoCalSettingExport(String positionCode, String positionName, String registered, int earlyOtTimeLimit, int earlyMidOtTimeLimit, int normalOtTimeLimit, int normalMidOtTimeLimit, int legarOtTimeLimit, int legarMidOtTimeLimit, int flexOtTimeLimit, int restTimeLimit, int lateNightTimeLimit, int lateOtTimeAtr, int earlyOtTimeAtr, int earlyMidOtTimeAtr, int normalOtTimeAtr, int normalMidOtTimeAtr, int legarMidOtTimeAtr, int flexOtTimeAtr, int restTimeAtr, int lateNightTimeAtr, int raiSingCalcAtr, int specificRaisingCalcAtr, int leaveEarly, int leaveLate, int divergence) {
        super(earlyOtTimeLimit, earlyMidOtTimeLimit, normalOtTimeLimit, normalMidOtTimeLimit, legarOtTimeLimit, legarMidOtTimeLimit, flexOtTimeLimit, restTimeLimit, lateNightTimeLimit, lateOtTimeAtr, earlyOtTimeAtr, earlyMidOtTimeAtr, normalOtTimeAtr, normalMidOtTimeAtr, legarMidOtTimeAtr, flexOtTimeAtr, restTimeAtr, lateNightTimeAtr, raiSingCalcAtr, specificRaisingCalcAtr, leaveEarly, leaveLate, divergence);
        this.positionCode = positionCode;
        this.positionName = positionName;
        this.registered = registered;
    }
}
