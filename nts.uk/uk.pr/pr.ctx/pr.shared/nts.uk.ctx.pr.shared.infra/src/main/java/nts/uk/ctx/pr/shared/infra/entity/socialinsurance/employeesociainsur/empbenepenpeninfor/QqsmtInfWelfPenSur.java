package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.InforOnWelfPenInsurAcc;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;


/**
* 厚生年金保険取得時情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSMT_INF_WELF_PEN_SUR")
public class QqsmtInfWelfPenSur extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqsmtInfWelfPenSurPk infWelfPenSurPk;
    
    /**
    * 健保同一区分
    */
    @Basic(optional = false)
    @Column(name = "HEAL_INSUR_DIS")
    public int healInsurDis;
    
    /**
    * 理由その他内容
    */
    @Basic(optional = false)
    @Column(name = "REA_AND_OTHER_CONT")
    public String reaAndOtherCont;
    
    /**
    * 70歳以上被用者
    */
    @Basic(optional = true)
    @Column(name = "70YEARS_OLDER")
    public Integer yearsOlder;
    
    /**
    * 備考その他
    */
    @Basic(optional = true)
    @Column(name = "REMARKS_OTHER")
    public Integer remarksOther;
    
    /**
    * 備考その他
    */
    @Basic(optional = true)
    @Column(name = "RE_OTHER_CONTENT")
    public String reOtherContent;
    
    /**
    * 報酬月額（現物）
    */
    @Basic(optional = true)
    @Column(name = "REMU_MONT_KIND")
    public Integer remuMontKind;
    
    /**
    * 報酬月額（金額）
    */
    @Basic(optional = true)
    @Column(name = "REMU_MONT_AMOUNT")
    public Integer remuMontAmount;
    
    /**
    * 報酬月額合計
    */
    @Basic(optional = true)
    @Column(name = "TOTAL_MONT_AMOUNT")
    public Integer totalMontAmount;
    
    /**
    * 海外在住
    */
    @Basic(optional = true)
    @Column(name = "LIVING_ABROAD")
    public Integer livingAbroad;
    
    /**
    * 理由その他
    */
    @Basic(optional = true)
    @Column(name = "REASON_OTHER")
    public Integer reasonOther;
    
    /**
    * 坑内員区分
    */
    @Basic(optional = true)
    @Column(name = "SHORT_TIME_WORKER")
    public Integer shortTimeWorker;
    
    /**
    * 短期在留
    */
    @Basic(optional = true)
    @Column(name = "SHORT_TERM_RESIDENCE")
    public Integer shortTermResidence;
    
    /**
    * 被扶養者届出区分
    */
    @Basic(optional = true)
    @Column(name = "DEPEN_NOTI_CLASS")
    public Integer depenNotiClass;
    
    /**
    * 資格取得備考
    */
    @Basic(optional = true)
    @Column(name = "QUA_ACQUI_REMARKS")
    public String quaAcquiRemarks;
    
    /**
    * 資格取得区分
    */
    @Basic(optional = true)
    @Column(name = "QUALIFI_CLASS")
    public Integer qualifiClass;
    
    /**
    * 退職後の継続再雇用者
    */
    @Basic(optional = true)
    @Column(name = "CONTI_REEM_AF_RETIREMENT")
    public Integer contiReemAfRetirement;
    
    @Override
    protected Object getKey()
    {
        return infWelfPenSurPk;
    }

    public InforOnWelfPenInsurAcc toDomain() {
        return null;
    }
    public static QqsmtInfWelfPenSur toEntity(InforOnWelfPenInsurAcc domain) {
        return null;
    }

}
