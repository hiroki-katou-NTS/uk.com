/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement;

import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.ExcessState;


/**
 * アラームするメッセージ
 */
@AllArgsConstructor
public class MessageForAlarm {
    private Map<ExcessState, String> messages;

    public String getMessage(ExcessState excessState) {
        return Optional.ofNullable(this.messages.get(excessState))
                .orElseThrow(() -> new RuntimeException("メッセージないぞ"));
    }
}
