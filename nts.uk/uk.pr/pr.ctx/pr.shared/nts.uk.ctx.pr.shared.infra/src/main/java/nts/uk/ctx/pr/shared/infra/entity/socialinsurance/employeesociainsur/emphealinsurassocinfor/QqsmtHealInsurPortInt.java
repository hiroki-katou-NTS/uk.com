package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurassocinfor;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealInsurPortPerIntell;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealthCarePortInfor;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
* 健保組合加入期間情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSDT_KNKUM_INFO")
public class QqsmtHealInsurPortInt extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqsmtHealInsurPortIntPk healInsurPortIntPk;
    
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
    * 健康保険組合番号
    */
    @Basic(optional = false)
    @Column(name = "KNKUM_NUM")
    public String healInsurUnionNmber;
    
    @Override
    protected Object getKey()
    {
        return healInsurPortIntPk;
    }

    public static HealInsurPortPerIntell toDomain(List<QqsmtHealInsurPortInt> entity) {
        if(entity.size() <= 0){
            return null;
        }
        String empID = entity.get(0).healInsurPortIntPk.employeeId;
        String historyID = entity.get(0).healInsurPortIntPk.hisId;
        List<DateHistoryItem> period = new ArrayList<>();
        entity.forEach(x -> {
            DatePeriod datePeriod = new DatePeriod(x.startDate,x.endDate);
            DateHistoryItem historyItem = new DateHistoryItem(historyID,datePeriod);
            period.add(historyItem);
        });

        return new HealInsurPortPerIntell(empID,period);

    }
    public static QqsmtHealInsurPortInt toEntity(HealInsurPortPerIntell domain) {
        return null;
    }

    public HealthCarePortInfor toDomainHealthCare(){
        return new HealthCarePortInfor(this.healInsurPortIntPk.hisId, this.healInsurUnionNmber);
    }

}
