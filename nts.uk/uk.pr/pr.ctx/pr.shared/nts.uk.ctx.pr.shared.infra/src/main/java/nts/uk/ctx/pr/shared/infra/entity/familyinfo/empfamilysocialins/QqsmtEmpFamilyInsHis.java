package nts.uk.ctx.pr.shared.infra.entity.familyinfo.empfamilysocialins;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins.EmpFamilyInsHis;
import nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins.EmpFamilySocialIns;
import nts.uk.ctx.pr.shared.dom.familyinfo.empfamilysocialins.EmpFamilySocialInsCtg;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
* 社員家族社会保険履歴
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSMT_EMP_FAMILY_INS_HIS")
public class QqsmtEmpFamilyInsHis extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqsmtEmpFamilyInsHisPk empFamilyInsHisPk;
    
    /**
    * 開始日
    */
    @Basic(optional = false)
    @Column(name = "START_DATE")
    public GeneralDate startDate;
    
    /**
    * 終了日
    */
    @Basic(optional = false)
    @Column(name = "END_DATE")
    public GeneralDate endDate;
    
    /**
    * 介護保険適用区分
    */
    @Basic(optional = false)
    @Column(name = "INS_CARE_DIVISION_ATR")
    public int insCareDivision;
    
    /**
    * 社会保険適用区分
    */
    @Basic(optional = false)
    @Column(name = "SOCIAL_INSURANCE_CLS_ATR")
    public int socialInsuranceCls;
    
    /**
    * 家族基礎年金番号
    */
    @Basic(optional = true)
    @Column(name = "FM_BS_PEN_NUM")
    public String fmBsPenNum;
    
    /**
    * 被扶養者区分
    */
    @Basic(optional = false)
    @Column(name = "DEPENDENT_ATR")
    public int dependent;
    
    @Override
    protected Object getKey()
    {
        return empFamilyInsHisPk;
    }

    public static EmpFamilyInsHis toDomainEmpFamilyInsHis(List<QqsmtEmpFamilyInsHis> empFamilyInsHis) {
        if (empFamilyInsHis.isEmpty()) {
            return null;
        }
        else {
            List<DateHistoryItem> dateHistoryItem = empFamilyInsHis.stream().map(i -> {return
                new DateHistoryItem(i.empFamilyInsHisPk.historyId, new DatePeriod(i.startDate, i.endDate));}).collect(Collectors.toList());
            return new EmpFamilyInsHis(empFamilyInsHis.get(0).empFamilyInsHisPk.empId, empFamilyInsHis.get(0).empFamilyInsHisPk.familyId, dateHistoryItem);
        }
    }

    public EmpFamilySocialInsCtg toDomainEmpFamilySocialInsCtg() {
        return new EmpFamilySocialInsCtg(this.empFamilyInsHisPk.historyId, this.dependent);
    }

    public EmpFamilySocialIns toDomains() {
        return new EmpFamilySocialIns(this.insCareDivision, this.empFamilyInsHisPk.historyId,this.socialInsuranceCls, this.fmBsPenNum);
    }

}
