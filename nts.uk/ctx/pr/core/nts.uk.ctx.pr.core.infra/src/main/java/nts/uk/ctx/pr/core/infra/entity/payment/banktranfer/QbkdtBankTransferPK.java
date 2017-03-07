package nts.uk.ctx.pr.core.infra.entity.payment.banktranfer;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

@Embeddable
public class QbkdtBankTransferPK {
	@Basic(optional = false)
    @Column(name = "CCD")
    public String ccd;
    @Basic(optional = false)
    @Column(name = "PID")
    public String pid;
    @Basic(optional = false)
    @Column(name = "FROM_BANK_CD")
    public String fromBankCd;
    @Basic(optional = false)
    @Column(name = "FROM_BRANCH_CD")
    public String fromBranchCd;
    @Basic(optional = false)
    @Column(name = "FROM_ACCOUNT_ATR")
    public int fromAccountAtr;
    @Basic(optional = false)
    @Column(name = "FROM_ACCOUNT_NO")
    public String fromAccountNo;
    @Basic(optional = false)
    @Column(name = "TO_BANK_CD")
    public String toBankCd;
    @Basic(optional = false)
    @Column(name = "TO_BRANCH_CD")
    public String toBranchCd;
    @Basic(optional = false)
    @Column(name = "TO_ACCOUNT_ATR")
    public int toAccountAtr;
    @Basic(optional = false)
    @Column(name = "TO_ACCOUNT_NO")
    public String toAccountNo;
    @Basic(optional = false)
    @Column(name = "PAY_BONUS_ATR")
    public int payBonusAtr;
    @Basic(optional = false)
    @Column(name = "PAY_DATE")
    @Convert(converter = GeneralDateToDBConverter.class)
    public GeneralDate payDate;
    @Basic(optional = false)
    @Column(name = "SPARE_PAY_ATR")
    public int sparePayAtr;

    public QbkdtBankTransferPK() {
    }

    public QbkdtBankTransferPK(String ccd, String pid, String fromBankCd, String fromBranchCd, int fromAccountAtr, String fromAccountNo, String toBankCd, String toBranchCd, int toAccountAtr, String toAccountNo, int payBonusAtr, GeneralDate payDate, int sparePayAtr) {
        this.ccd = ccd;
        this.pid = pid;
        this.fromBankCd = fromBankCd;
        this.fromBranchCd = fromBranchCd;
        this.fromAccountAtr = fromAccountAtr;
        this.fromAccountNo = fromAccountNo;
        this.toBankCd = toBankCd;
        this.toBranchCd = toBranchCd;
        this.toAccountAtr = toAccountAtr;
        this.toAccountNo = toAccountNo;
        this.payBonusAtr = payBonusAtr;
        this.payDate = payDate;
        this.sparePayAtr = sparePayAtr;
    }

}
