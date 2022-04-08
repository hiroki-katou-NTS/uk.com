/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement;

import java.util.Set;
import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.ExcessState;

/**
 * 超過状態をチェックする条件
 * @author raiki_asada
 */
@AllArgsConstructor
public class ExcessStateChecker {
    private Set<ExcessState> taretState;
    
    public Boolean check(ExcessState state) {
        return this.taretState.contains(state);
    }
}
