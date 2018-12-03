package nts.uk.ctx.at.shared.dom.ot.autocalsetting.com;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComAutoCalSettingExport {
    private int earlyOtTimeLimit;
    private int earlyMidOtTimeLimit;
    private int normalOtTimeLimit;
    private int normalMidOtTimeLimit;
    private int legarOtTimeLimit;
    private int legarMidOtTimeLimit;
    private int flexOtTimeLimit;
    private int restTimeLimit;
    private int lateNightTimeLimit;
    private int earlyOtTimeAtr;
    private int earlyMidOtTimeAtr;
    private int normalOtTimeAtr;
    private int normalMidOtTimeAtr;
    private int legarOtTimeAtr;
    private int legarMidOtTimeAtr;
    private int flexOtTimeAtr;
    private int restTimeAtr;
    private int lateNightTimeAtr;
    private int raiSingCalcAtr;
    private int specificRaisingCalcAtr;
    private int leaveEarly;
    private int leaveLate;
    private int divergence;

    public ComAutoCalSettingExport(int earlyOtTimeLimit, int earlyMidOtTimeLimit, int normalOtTimeLimit, int normalMidOtTimeLimit, int legarOtTimeLimit, int legarMidOtTimeLimit, int flexOtTimeLimit, int restTimeLimit, int lateNightTimeLimit, int legarOtTimeAtr, int earlyOtTimeAtr, int earlyMidOtTimeAtr, int normalOtTimeAtr, int normalMidOtTimeAtr, int legarMidOtTimeAtr, int flexOtTimeAtr, int restTimeAtr, int lateNightTimeAtr, int raiSingCalcAtr, int specificRaisingCalcAtr, int leaveEarly, int leaveLate, int divergence) {
        this.earlyOtTimeLimit = earlyOtTimeLimit;
        this.earlyMidOtTimeLimit = earlyMidOtTimeLimit;
        this.normalOtTimeLimit = normalOtTimeLimit;
        this.normalMidOtTimeLimit = normalMidOtTimeLimit;
        this.legarOtTimeLimit = legarOtTimeLimit;
        this.legarMidOtTimeLimit = legarMidOtTimeLimit;
        this.flexOtTimeLimit = flexOtTimeLimit;
        this.restTimeLimit = restTimeLimit;
        this.lateNightTimeLimit = lateNightTimeLimit;
        this.earlyOtTimeAtr = earlyOtTimeAtr;
        this.earlyMidOtTimeAtr = earlyMidOtTimeAtr;
        this.normalOtTimeAtr = normalOtTimeAtr;
        this.normalMidOtTimeAtr = normalMidOtTimeAtr;
        this.legarOtTimeAtr = legarOtTimeAtr;
        this.legarMidOtTimeAtr = legarMidOtTimeAtr;
        this.flexOtTimeAtr = flexOtTimeAtr;
        this.restTimeAtr = restTimeAtr;
        this.lateNightTimeAtr = lateNightTimeAtr;
        this.raiSingCalcAtr = raiSingCalcAtr;
        this.specificRaisingCalcAtr = specificRaisingCalcAtr;
        this.leaveEarly = leaveEarly;
        this.leaveLate = leaveLate;
        this.divergence = divergence;
    }
}
