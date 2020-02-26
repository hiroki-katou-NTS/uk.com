package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpHealthInsurBenefits;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmplHealInsurQualifiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealInsurNumberInfor;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
* 社員健康保険資格情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSDT_KENHO_INFO")
public class QqsmtEmpHealInsurQi extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqsmtEmpHealInsurQiPk empHealInsurQiPk;
    
    /**
    * 健康保険資格取得日
    */
    @Basic(optional = false)
    @Column(name = "START_DATE")
    public GeneralDate startDate;
    
    /**
    * 健康保険資格喪失日
    */
    @Basic(optional = false)
    @Column(name = "END_DATE")
    public GeneralDate endDate;

    /**
     * 介護保険番号
     */
    @Basic(optional = true)
    @Column(name = "KAIHO_NUM")
    public String careIsNumber;

    /**
     * 健康保険番号
     */
    @Basic(optional = true)
    @Column(name = "KENHO_NUM")
    public String healInsurNumber;
    
    @Override
    protected Object getKey()
    {
        return empHealInsurQiPk;
    }

    public static EmplHealInsurQualifiInfor toDomain(List<QqsmtEmpHealInsurQi> qqsmtEmpHealInsurQi) {
        return new EmplHealInsurQualifiInfor(
                qqsmtEmpHealInsurQi.get(0).empHealInsurQiPk.employeeId,
                qqsmtEmpHealInsurQi.stream().map(i -> new EmpHealthInsurBenefits(i.empHealInsurQiPk.hisId , new DateHistoryItem(i.empHealInsurQiPk.hisId, new DatePeriod(i.startDate, i.endDate))))
                        .collect(Collectors.toList()));
    }

    public EmplHealInsurQualifiInfor toDomain() {
        List<EmpHealthInsurBenefits> date = new ArrayList<>();
        date.add(new EmpHealthInsurBenefits(this.empHealInsurQiPk.hisId, new DateHistoryItem(this.empHealInsurQiPk.hisId, new DatePeriod(this.startDate, this.endDate))));
        return new EmplHealInsurQualifiInfor(
                this.empHealInsurQiPk.employeeId, date);
    }

    public  HealInsurNumberInfor toHealInsurNumberInfor(){
        return new HealInsurNumberInfor(this.empHealInsurQiPk.hisId,this.careIsNumber,this.healInsurNumber);
    }

    public static QqsmtEmpHealInsurQi toEntity(EmplHealInsurQualifiInfor domain) {
        return null;
    }

}
