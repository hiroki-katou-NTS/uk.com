package nts.uk.ctx.pr.report.infra.entity.printconfig.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.AddChangeSetting;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpAddChangeInfo;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpNameChangeNotiInfor;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
* 社員住所変更届情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QRSMT_EMP_ADD_CGE_SETTING")
public class QrsmtEmpAddCgeSetting extends UkJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QrsmtEmpAddCgeSettingPk empAddCgeSettingPk;
    
    /**
    * 短期在留者
    */
    @Basic(optional = false)
    @Column(name = "SHORT_RESIDENT_ATR")
    public int shortResidentAtr;
    
    /**
    * 海外居住者
    */
    @Basic(optional = false)
    @Column(name = "LIVING_ABROAD_ATR")
    public int livingAbroadAtr;
    
    /**
    * 住民票住所以外居所
    */
    @Basic(optional = false)
    @Column(name = "RESIDENCE_OTHER_RESIDENT_ATR")
    public int residenceOtherResidentAtr;
    
    /**
    * その他
    */
    @Basic(optional = false)
    @Column(name = "OTHER_ATR")
    public int otherAtr;
    
    /**
    * その他理由
    */
    @Basic(optional = true)
    @Column(name = "OTHER_REASON")
    public String otherReason;
    
    /**
    * 短期在留者
    */
    @Basic(optional = false)
    @Column(name = "SPOUSE_SHORT_RESIDENT_ATR")
    public int spouseShortResidentAtr;
    
    /**
    * 海外居住者
    */
    @Basic(optional = false)
    @Column(name = "SPOUSE_LIVING_ABROAD_ATR")
    public int spouseLivingAbroadAtr;
    
    /**
    * 住民票住所以外居所
    */
    @Basic(optional = false)
    @Column(name = "SPOUSE_RESIDENCE_OTHER_RESIDENT_ATR")
    public int spouseResidenceOtherResidentAtr;
    
    /**
    * その他
    */
    @Basic(optional = false)
    @Column(name = "SPOUSE_OTHER_ATR")
    public int spouseOtherAtr;
    
    /**
    * その他理由
    */
    @Basic(optional = true)
    @Column(name = "SPOUSE_OTHER_REASON")
    public String spouseOtherReason;
    
    @Override
    protected Object getKey()
    {
        return empAddCgeSettingPk;
    }

    public static QrsmtEmpAddCgeSetting toEntity(EmpAddChangeInfo empAddChangeInfo){
        return new QrsmtEmpAddCgeSetting(
                new QrsmtEmpAddCgeSettingPk(AppContexts.user().companyId(),empAddChangeInfo.getSid()),
                empAddChangeInfo.getPersonalSet().getShortResident(),
                empAddChangeInfo.getPersonalSet().getLivingAbroadAtr(),
                empAddChangeInfo.getPersonalSet().getResidenceOtherResidentAtr(),
                empAddChangeInfo.getPersonalSet().getOtherAtr(),
                empAddChangeInfo.getPersonalSet().getOtherReason().map(i -> i.v()).orElse(null),
                empAddChangeInfo.getSpouse().getShortResident(),
                empAddChangeInfo.getSpouse().getLivingAbroadAtr(),
                empAddChangeInfo.getSpouse().getResidenceOtherResidentAtr(),
                empAddChangeInfo.getSpouse().getOtherAtr(),
                empAddChangeInfo.getSpouse().getOtherReason().map(i -> i.v()).orElse(null));
    }

    public EmpAddChangeInfo toDomain(){
        return new EmpAddChangeInfo(this.empAddCgeSettingPk.sid,
                new AddChangeSetting(this.shortResidentAtr, this.livingAbroadAtr, this.residenceOtherResidentAtr, this.otherAtr,this.otherReason),
                new AddChangeSetting(this.spouseShortResidentAtr, this.spouseLivingAbroadAtr,this.spouseResidenceOtherResidentAtr, this.spouseOtherAtr, this.spouseOtherReason));
    }

}
