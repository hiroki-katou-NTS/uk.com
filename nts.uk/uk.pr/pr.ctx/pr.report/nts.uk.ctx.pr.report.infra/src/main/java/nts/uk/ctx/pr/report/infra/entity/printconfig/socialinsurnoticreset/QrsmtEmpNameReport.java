package nts.uk.ctx.pr.report.infra.entity.printconfig.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpNameReport;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.NameNotificationSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
* 社員ローマ字氏名届情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QRSMT_EMP_NAME_REPORT")
public class QrsmtEmpNameReport extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QrsmtEmpNameReportPk empNameReportPk;
    
    /**
    * その他
    */
    @Basic(optional = false)
    @Column(name = "PERSONAL_SET_OTHER")
    public int personalSetOther;
    
    /**
    * ローマ字氏名が記載されていない
    */
    @Basic(optional = false)
    @Column(name = "PERSONAL_SET_LISTED")
    public int personalSetListed;
    
    /**
    * 住民票
    */
    @Basic(optional = false)
    @Column(name = "PERSONAL_RESIDENT_CARD")
    public int personalResidentCard;
    
    /**
    * 海外に住所を有している
    */
    @Basic(optional = false)
    @Column(name = "PERSONAL_ADDRESS_OVERSEAS")
    public int personalAddressOverseas;
    
    /**
    * 短期在留者
    */
    @Basic(optional = false)
    @Column(name = "PERSONAL_SHORT_RESIDENT")
    public int personalShortResident;
    
    /**
    * その他理由内容
    */
    @Basic(optional = true)
    @Column(name = "PERSONAL_OTHER_REASON")
    public String personalOtherReason;
    
    /**
    * その他
    */
    @Basic(optional = false)
    @Column(name = "SPOUSE_SET_OTHER")
    public int spouseSetOther;
    
    /**
    * ローマ字氏名が記載されていない
    */
    @Basic(optional = false)
    @Column(name = "SPOUSE_SET_LISTED")
    public int spouseSetListed;
    
    /**
    * 住民票
    */
    @Basic(optional = false)
    @Column(name = "SPOUSE_RESIDENT_CARD")
    public int spouseResidentCard;
    
    /**
    * 海外に住所を有している
    */
    @Basic(optional = false)
    @Column(name = "SPOUSE_ADDRESS_OVERSEAS")
    public int spouseAddressOverseas;
    
    /**
    * 短期在留者
    */
    @Basic(optional = false)
    @Column(name = "SPOUSE_SHORT_RESIDENT")
    public int spouseShortResident;
    
    /**
    * その他理由内容
    */
    @Basic(optional = true)
    @Column(name = "SPOUSE_OTHER_REASON")
    public String spouseOtherReason;
    
    @Override
    protected Object getKey()
    {
        return empNameReportPk;
    }

    public EmpNameReport toDomain(){

        return new EmpNameReport(empNameReportPk.empId,
                new NameNotificationSet(
                        personalSetOther,
                        personalSetListed,
                        personalResidentCard,
                        personalAddressOverseas,
                        personalShortResident,
                        personalOtherReason),
                new NameNotificationSet(
                        spouseSetOther,
                        spouseSetListed,
                        spouseResidentCard,
                        spouseAddressOverseas,
                        spouseShortResident,
                        spouseOtherReason )
                );
    }

    public static QrsmtEmpNameReport toEntity(EmpNameReport domain){
        return new QrsmtEmpNameReport(
                new QrsmtEmpNameReportPk(
                        domain.getEmpId()),
                        domain.getPersonalSet().getOther(),
                        domain.getPersonalSet().getListed(),
                        domain.getPersonalSet().getResidentCard().value,
                        domain.getPersonalSet().getAddressOverseas(),
                        domain.getPersonalSet().getShortResident(),
                        domain.getPersonalSet().getOtherReason().map(i -> i.v()).orElse(null),
                        domain.getSpouse().getOther(),
                        domain.getSpouse().getListed(),
                        domain.getSpouse().getResidentCard().value,
                        domain.getSpouse().getAddressOverseas(),
                        domain.getSpouse().getShortResident(),
                        domain.getSpouse().getOtherReason().map(i -> i.v()).orElse(null)
        );
    }
}
