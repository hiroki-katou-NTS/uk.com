package nts.uk.ctx.pr.report.infra.entity.printdata.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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

}
