package nts.uk.ctx.pr.report.infra.entity.printconfig.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpNameReport;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.NameNotificationSet;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
* 社員ローマ字氏名届情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QRSMT_EMP_ROMANNM_REPORT")
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
    @Column(name = "OTHER_ATR")
    public int personalSetOther;
    
    /**
    * ローマ字氏名が記載されていない
    */
    @Basic(optional = false)
    @Column(name = "SET_LISTED_ATR")
    public int personalSetListed;
    
    /**
    * 住民票
    */
    @Basic(optional = false)
    @Column(name = "RESIDENT_CARD_ATR")
    public int personalResidentCard;
    
    /**
    * 海外に住所を有している
    */
    @Basic(optional = false)
    @Column(name = "ADDRESS_OVERSEAS_ATR")
    public int personalAddressOverseas;
    
    /**
    * 短期在留者
    */
    @Basic(optional = false)
    @Column(name = "SHORT_RESIDENT_ATR")
    public int personalShortResident;
    
    /**
    * その他理由内容
    */
    @Basic(optional = true)
    @Column(name = "OTHER_REASON")
    public String personalOtherReason;
    
    /**
    * その他
    */
    @Basic(optional = false)
    @Column(name = "SPOUSE_OTHER_ATR")
    public int spouseSetOther;
    
    /**
    * ローマ字氏名が記載されていない
    */
    @Basic(optional = false)
    @Column(name = "SPOUSE_SET_LISTED_ATR")
    public int spouseSetListed;
    
    /**
    * 住民票
    */
    @Basic(optional = false)
    @Column(name = "SPOUSE_RESIDENT_CARD_ATR")
    public int spouseResidentCard;
    
    /**
    * 海外に住所を有している
    */
    @Basic(optional = false)
    @Column(name = "SPOUSE_ADDRESS_OVERSEAS_ATR")
    public int spouseAddressOverseas;
    
    /**
    * 短期在留者
    */
    @Basic(optional = false)
    @Column(name = "SPOUSE_SHORT_RESIDENT_ATR")
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

        return new EmpNameReport(empNameReportPk.empId, empNameReportPk.cid,
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
                        domain.getEmpId(),
                        domain.getCid()),
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
