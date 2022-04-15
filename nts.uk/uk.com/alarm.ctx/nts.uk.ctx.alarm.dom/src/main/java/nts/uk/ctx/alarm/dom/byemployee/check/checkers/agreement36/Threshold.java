/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.ExcessState;

/**
 *
 * @author raiki_asada
 */
public interface Threshold<V> {
    ExcessState check(V targetValue);
}
