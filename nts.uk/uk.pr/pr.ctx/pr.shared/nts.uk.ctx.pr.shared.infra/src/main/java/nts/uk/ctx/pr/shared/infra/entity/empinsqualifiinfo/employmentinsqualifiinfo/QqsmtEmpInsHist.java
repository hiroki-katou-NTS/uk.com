package nts.uk.ctx.pr.shared.infra.entity.empinsqualifiinfo.employmentinsqualifiinfo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsHist;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
* 社員雇用保険履歴
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSMT_EMP_INS_HIST")
public class QqsmtEmpInsHist extends UkJpaEntity implements Serializable{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqsmtEmpInsHistPk empInsHistPk;
    
    /**
    * 開始日
    */
    @Basic(optional = true)
    @Column(name = "START_DATE")
    public GeneralDate startDate;
    
    /**
    * 終了日
    */
    @Basic(optional = true)
    @Column(name = "END_DATE")
    public GeneralDate endDate;
    
    /**
    * 雇用保険番号
    */
    @Basic(optional = true)
    @Column(name = "EMP_INS_NUMBER")
    public String empInsNumber;
    
    /**
    * 高年齢被保険者
    */
    @Basic(optional = true)
    @Column(name = "ELDERLY_INSURED_ATR")
    public Integer elderlyInsuredAtr;
    
    /**
    * 計算区分
    */
    @Basic(optional = false)
    @Column(name = "CAL_CLS")
    public int calCls;
    
    /**
    * 短期雇用特例被保険者区分
    */
    @Basic(optional = true)
    @Column(name = "EMP_EXC_INS_CTG")
    public Integer empExcInsCtg;
    
    /**
    * 雇用保険計算区分
    */
    @Basic(optional = true)
    @Column(name = "EMP_INS_CTG_ATR")
    public Integer empInsCtgAtr;
    
    @Override
    protected Object getKey()
    {
        return empInsHistPk;
    }

    public EmpInsHist toEmploymentHistory() {
        DateHistoryItem dateItem;
        dateItem =  new DateHistoryItem(this.empInsHistPk.histId, new DatePeriod(this.startDate, this.endDate));
        List<DateHistoryItem> dateHistoryItems = new ArrayList<>();
        dateHistoryItems.add(dateItem);
        return new EmpInsHist(this.empInsHistPk.sid, dateHistoryItems);
    }

}
