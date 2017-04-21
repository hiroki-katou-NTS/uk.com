/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.pr.core.infra.entity.retirement.payment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import nts.arc.time.GeneralDate;

@Embeddable
public class QredtRetirementPaymentPK {

    @Column(name = "CCD")
    public String ccd;
    @Column(name = "PID")
    public String pid;
    @Column(name = "PAY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public GeneralDate payDate;

    public QredtRetirementPaymentPK() {
    }

    public QredtRetirementPaymentPK(String ccd, String pid, GeneralDate payDate) {
        this.ccd = ccd;
        this.pid = pid;
        this.payDate = payDate;
    }
    
}
