/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.pr.file.infra.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author chinhbv
 */
@Embeddable
public class ReportQredtRetirementPaymentPK {

    @Column(name = "CCD")
    public String ccd;
    @Column(name = "PID")
    public String pid;
    @Column(name = "PAY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date payDate;

    public ReportQredtRetirementPaymentPK() {
    }

    public ReportQredtRetirementPaymentPK(String ccd, String pid, Date payDate) {
        this.ccd = ccd;
        this.pid = pid;
        this.payDate = payDate;
    }
    
}
