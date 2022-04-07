/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement;

/**
 * アラームチェック項目
 */
public enum TargetOfAlarmCheck {
    //３６協定時間（法定休日出勤は含まない）
    AGREEMENT_36_TIME,
    //３６協定時間（法定休日出勤を含む）
    LEGAL_MAX_TIME;
}
