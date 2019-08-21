package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmployWelPenInsurAche;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
* 社員厚生年金保険資格情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSMT_EMP_WELF_INS_QC_IF")
public class QqsmtEmpWelfInsQcIf extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqsmtEmpWelfInsQcIfPk empWelfInsQcIfPk;
    
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
    
    @Override
    protected Object getKey()
    {
        return empWelfInsQcIfPk;
    }

    public static Optional<EmpWelfarePenInsQualiInfor> toDomain(List<QqsmtEmpWelfInsQcIf> data) {
        List<EmployWelPenInsurAche> mournPeriod = new ArrayList<>();
        String employeeId = data.get(0).empWelfInsQcIfPk.employeeId;
        data.forEach(x -> {
            DatePeriod period = new DatePeriod(x.startDate,x.endDate);
            DateHistoryItem item = new DateHistoryItem(x.empWelfInsQcIfPk.historyId,period);
            mournPeriod.add(new EmployWelPenInsurAche(x.empWelfInsQcIfPk.historyId,item) );
        });
        return Optional.ofNullable(new EmpWelfarePenInsQualiInfor(
                employeeId,
                mournPeriod
        ));
    }

    public EmpWelfarePenInsQualiInfor toDomain(){
        return null;
    }
    public static QqsmtEmpWelfInsQcIf toEntity(EmpWelfarePenInsQualiInfor domain) {

        return new QqsmtEmpWelfInsQcIf(
                new QqsmtEmpWelfInsQcIfPk(domain.getEmployeeId(),domain.getMournPeriod().get(0).getHistoryId()),
                domain.getMournPeriod().get(0).getDatePeriod().start(),
                domain.getMournPeriod().get(0).getDatePeriod().end()
        );
    }

}
