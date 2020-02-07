package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empfunmeminfor;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor.EmPensionFundPartiPeriodInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor.FundMembership;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;


/**
* 厚生年金基金加入期間情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSDT_KIKIN_INFO")
public class QqsmtTemPenPartInfo extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqsmtTemPenPartInfoPk temPenPartInfoPk;
    
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
    * 基金加入員番号
    */
    @Basic(optional = false)
    @Column(name = "KIKIN_NUM")
    public String memberNumber;
    
    @Override
    protected Object getKey()
    {
        return temPenPartInfoPk;
    }

    public EmPensionFundPartiPeriodInfor toDomain() {
        DatePeriod period = new DatePeriod(this.startDate,endDate);
        DateHistoryItem historyItem = new DateHistoryItem(this.temPenPartInfoPk.historyId,period);
        return new EmPensionFundPartiPeriodInfor(this.temPenPartInfoPk.employeeId,historyItem);

    }

    public FundMembership toFundMembership(){
        return new FundMembership(this.temPenPartInfoPk.historyId, this.memberNumber);
    }


    public static QqsmtTemPenPartInfo toEntity(EmPensionFundPartiPeriodInfor domain) {
        return null;
    }

}
