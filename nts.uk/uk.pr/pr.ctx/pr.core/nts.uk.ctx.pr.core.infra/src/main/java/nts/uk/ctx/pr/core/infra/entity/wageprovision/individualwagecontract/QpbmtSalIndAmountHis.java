package nts.uk.ctx.pr.core.infra.entity.wageprovision.individualwagecontract;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.PersonalAmount;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmount;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHis;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalaryIndividualAmountHistory;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 給与個人別金額履歴
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_SAL_IND_AMOUNT_HIS")
public class QpbmtSalIndAmountHis extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QpbmtSalIndAmountHisPk salIndAmountHisPk;
    
    /**
    * 開始年月
    */
    @Basic(optional = false)
    @Column(name = "START_YM")
    public int periodStartYm;
    
    /**
    * 終了年月
    */
    @Basic(optional = false)
    @Column(name = "END_YM")
    public int periodEndYm;
    
    /**
    * 金額
    */
    @Basic(optional = false)
    @Column(name = "AMOUNT_OF_MONEY")
    public long amountOfMoney;
    
    @Override
    protected Object getKey()
    {
        return salIndAmountHisPk;
    }

    public static SalaryIndividualAmountHistory toDomain(QpbmtSalIndAmountHis entity) {
        return new SalaryIndividualAmountHistory(entity.salIndAmountHisPk.empId, entity.salIndAmountHisPk.historyId, entity.salIndAmountHisPk.salBonusCate, entity.salIndAmountHisPk.cateIndicator, entity.salIndAmountHisPk.perValCode, entity.periodStartYm, entity.periodEndYm, entity.amountOfMoney);
    }

    public static QpbmtSalIndAmountHis toEntity(SalIndAmountHis domain1, SalIndAmount domain2) {
        return new QpbmtSalIndAmountHis(new QpbmtSalIndAmountHisPk(domain1.cid, domain1.getEmpId(), domain1.getPeriod().get(0).getHistoryID(), domain1.getSalBonusCate().value, domain1.getCateIndicator().value, domain1.getPerValCode()), domain1.getPeriod().get(0).getPeriodYearMonth().start().v(), domain1.getPeriod().get(0).getPeriodYearMonth().end().v(), domain2.getAmountOfMoney().v());
    }

    public static PersonalAmount toDomainObject(QpbmtSalIndAmountHis entity) {
        return new PersonalAmount(entity.salIndAmountHisPk.empId, entity.salIndAmountHisPk.historyId, "", "", entity.periodStartYm, entity.periodEndYm, entity.amountOfMoney);
    }
}
