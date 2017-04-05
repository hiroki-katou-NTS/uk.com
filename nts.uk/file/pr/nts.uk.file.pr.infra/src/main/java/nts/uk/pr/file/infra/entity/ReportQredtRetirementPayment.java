/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.pr.file.infra.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 *
 * @author chinhbv
 */
@AllArgsConstructor
@Entity
@Table(name = "QREDT_RETIREMENT_PAYMENT")
public class ReportQredtRetirementPayment extends UkJpaEntity {

    @EmbeddedId
    public ReportQredtRetirementPaymentPK qredtRetirementPaymentPK;
    
    @Column(name = "TRIAL_PERIOD_SET")
    public int trialPeriodSet;
    @Column(name = "EXCLUSION_YEARS")
    public int exclusionYears;
    @Column(name = "ADDITIONAL_BOARD_YEARS")
    public int additionalBoardYears;
    @Column(name = "BOARD_YEARS")
    public int boardYears;
    @Column(name = "TOTAL_PAYMENT_MNY")
    public BigDecimal totalPaymentMny;
    @Column(name = "DEDUCTION1_MNY")
    public BigDecimal deduction1Mny;
    @Column(name = "DEDUCTION2_MNY")
    public BigDecimal deduction2Mny;
    @Column(name = "DEDUCTION3_MNY")
    public BigDecimal deduction3Mny;
    @Column(name = "OTHER_RETIREMENT_PAY_OP")
    public int otherRetirementPayOp;
    @Column(name = "TAX_CAL_METHOD_SET")
    public int taxCalMethodSet;
    @Column(name = "INCOME_TAX_MNY")
    public BigDecimal incomeTaxMny;
    @Column(name = "CITY_TAX_MNY")
    public BigDecimal cityTaxMny;
    @Column(name = "PREFECTURE_TAX_MNY")
    public BigDecimal prefectureTaxMny;
    @Column(name = "TOTAL_DEDUCTION_MNY")
    public BigDecimal totalDeductionMny;
    @Column(name = "ACTUAL_RECIEVE_MNY")
    public BigDecimal actualRecieveMny;
    @Column(name = "BANK_TRANSFER_OP1")
    public int bankTransferOp1;
    @Column(name = "OP1_MNY")
    public BigDecimal op1Mny;
    @Column(name = "BANK_TRANSFER_OP2")
    public int bankTransferOp2;
    @Column(name = "OP2_MNY")
    public BigDecimal op2Mny;
    @Column(name = "BANK_TRANSFER_OP3")
    public int bankTransferOp3;
    @Column(name = "OP3_MNY")
    public BigDecimal op3Mny;
    @Column(name = "BANK_TRANSFER_OP4")
    public int bankTransferOp4;
    @Column(name = "OP4_MNY")
    public BigDecimal op4Mny;
    @Column(name = "BANK_TRANSFER_OP5")
    public int bankTransferOp5;
    @Column(name = "OP5_MNY")
    public BigDecimal op5Mny;
    @Column(name = "WITHHOLDING_MENO")
    public String withholdingMeno;
    @Column(name = "STATEMENT_MEMO")
    public String statementMemo;

    public ReportQredtRetirementPayment() {
    }

    public ReportQredtRetirementPayment(ReportQredtRetirementPaymentPK qredtRetirementPaymentPK) {
        this.qredtRetirementPaymentPK = qredtRetirementPaymentPK;
    }


    public ReportQredtRetirementPayment(String ccd, String pid, Date payDate) {
        this.qredtRetirementPaymentPK = new ReportQredtRetirementPaymentPK(ccd, pid, payDate);
    }

    public ReportQredtRetirementPaymentPK getQredtRetirementPaymentPK() {
        return qredtRetirementPaymentPK;
    }

    public void setQredtRetirementPaymentPK(ReportQredtRetirementPaymentPK qredtRetirementPaymentPK) {
        this.qredtRetirementPaymentPK = qredtRetirementPaymentPK;
    }

	@Override
	protected ReportQredtRetirementPaymentPK getKey() {
		return this.qredtRetirementPaymentPK;
	}
    
}
