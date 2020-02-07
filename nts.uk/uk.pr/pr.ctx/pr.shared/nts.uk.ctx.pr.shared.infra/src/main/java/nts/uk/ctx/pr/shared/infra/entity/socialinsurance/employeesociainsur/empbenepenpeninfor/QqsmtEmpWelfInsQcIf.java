package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmpWelfarePenInsQualiInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.EmployWelPenInsurAche;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfPenNumInformation;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfarePenTypeInfor;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
* 社員厚生年金保険資格情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSDT_KOUHO_INFO")
public class QqsmtEmpWelfInsQcIf extends UkJpaEntity implements Serializable {
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

    /**
     * 健保同一区分
     */
    @Basic(optional = false)
    @Column(name = "SAME_KENHO_ATR")
    public int healInsurSameCtg;

    /**
     * 厚生年金番号
     */
    @Basic(optional = true)
    @Column(name = "KOUHO_NU")
    public String welPenNumber;

    /**
     * 坑内員区分
     */
    @Basic(optional = false)
    @Column(name = "COAL_MINER_ATR")
    public int undergoundDivision;
    
    @Override
    protected Object getKey()
    {
        return empWelfInsQcIfPk;
    }


    public static EmpWelfarePenInsQualiInfor toDomain(List<QqsmtEmpWelfInsQcIf> qqsmtEmpWelfInsQcIf) {
        if(qqsmtEmpWelfInsQcIf.size() <= 0){
            return null;
        }
        return new EmpWelfarePenInsQualiInfor(
                qqsmtEmpWelfInsQcIf.get(0).empWelfInsQcIfPk.employeeId,
                qqsmtEmpWelfInsQcIf.stream().map(i -> new EmployWelPenInsurAche(i.empWelfInsQcIfPk.historyId, new DateHistoryItem(i.empWelfInsQcIfPk.historyId, new DatePeriod(i.startDate, i.endDate))))
                        .collect(Collectors.toList()));
    }

    public EmpWelfarePenInsQualiInfor toDomain() {
        List<EmployWelPenInsurAche> employWelPenInsurAche = new ArrayList<>();
        employWelPenInsurAche.add(new EmployWelPenInsurAche(this.empWelfInsQcIfPk.historyId, new DateHistoryItem(this.empWelfInsQcIfPk.historyId, new DatePeriod(this.startDate, this.endDate))));
        return new EmpWelfarePenInsQualiInfor(
                this.empWelfInsQcIfPk.employeeId, employWelPenInsurAche);
    }

    public WelfPenNumInformation toWelfPenNumInformation(){
        return new WelfPenNumInformation(this.empWelfInsQcIfPk.historyId, this.healInsurSameCtg,this.welPenNumber);
    }


    public WelfarePenTypeInfor toWelfarePenTypeI(){
        return new WelfarePenTypeInfor(this.empWelfInsQcIfPk.historyId, this.undergoundDivision);
    }

}
