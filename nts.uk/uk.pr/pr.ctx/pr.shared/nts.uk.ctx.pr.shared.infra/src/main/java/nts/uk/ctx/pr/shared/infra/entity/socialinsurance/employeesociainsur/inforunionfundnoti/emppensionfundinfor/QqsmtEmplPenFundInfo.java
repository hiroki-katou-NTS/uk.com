package nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.inforunionfundnoti.emppensionfundinfor;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emppensionfundinfor.EmplPenFundInfor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
* 社員厚生年金基金情報
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QQSMT_EMPL_PEN_FUND_INFO")
public class QqsmtEmplPenFundInfo extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public QqsmtEmplPenFundInfoPk emplPenFundInfoPk;

    
    /**
    * 加算適用区分(給与)
    */
    @Basic(optional = false)
    @Column(name = "ADD_APP_CTG_SAL")
    public int addAppCtgSal;
    
    /**
    * 加算給与月額
    */
    @Basic(optional = false)
    @Column(name = "ADD_SAL")
    public String addSal;
    
    /**
    * 標準給与月額
    */
    @Basic(optional = false)
    @Column(name = "STAND_SAL")
    public String standSal;
    
    /**
    * 第２加算給与月額
    */
    @Basic(optional = false)
    @Column(name = "SEC_ADD_SALARY")
    public String secAddSalary;
    
    /**
    * 第２標準給与月額
    */
    @Basic(optional = false)
    @Column(name = "SEC_STAND_SAL")
    public String secStandSal;
    
    /**
    * 加算適用区分(賞与)
    */
    @Basic(optional = false)
    @Column(name = "ADD_APP_CTG_BS")
    public int addAppCtgBs;
    
    /**
    * 加算賞与月額
    */
    @Basic(optional = false)
    @Column(name = "ADD_BONUS")
    public String addBonus;
    
    /**
    * 標準賞与月額
    */
    @Basic(optional = false)
    @Column(name = "STAND_BONUS")
    public String standBonus;
    
    /**
    * 第２加算賞与月額
    */
    @Basic(optional = false)
    @Column(name = "SEC_STAND_BONUS")
    public String secStandBonus;
    
    /**
    * 第２標準賞与月額
    */
    @Basic(optional = false)
    @Column(name = "SEC_STAND_BONUS")
    public String secAddBonus;
    
    /**
    * 基金固有項目１
    */
    @Basic(optional = false)
    @Column(name = "FUND_SPECIFIC1")
    public String fundSpecific1;
    
    /**
    * 基金固有項目２
    */
    @Basic(optional = false)
    @Column(name = "FUND_SPECIFIC2")
    public String fundSpecific2;
    
    /**
    * 基金固有項目３
    */
    @Basic(optional = false)
    @Column(name = "FUND_SPECIFIC3")
    public String fundSpecific3;
    
    /**
    * 基金固有項目４
    */
    @Basic(optional = false)
    @Column(name = "FUND_SPECIFIC4")
    public String fundSpecific4;
    
    /**
    * 基金固有項目５
    */
    @Basic(optional = false)
    @Column(name = "FUND_SPECIFIC5")
    public String fundSpecific5;
    
    /**
    * 基金固有項目７
    */
    @Basic(optional = false)
    @Column(name = "FUND_SPECIFIC7")
    public String fundSpecific7;
    
    /**
    * 基金固有項目８
    */
    @Basic(optional = false)
    @Column(name = "FUND_SPECIFIC8")
    public String fundSpecific8;
    
    /**
    * 基金固有項目９
    */
    @Basic(optional = false)
    @Column(name = "FUND_SPECIFIC9")
    public String fundSpecific9;
    
    /**
    * 基金固有項目１０
    */
    @Basic(optional = false)
    @Column(name = "FUND_SPECIFIC10")
    public String fundSpecific10;
    
    /**
    * 加入形態区分
    */
    @Basic(optional = false)
    @Column(name = "SUB_TYPE")
    public int subType;
    
    /**
    * 適用形態区分
    */
    @Basic(optional = false)
    @Column(name = "APP_FORM_CLS")
    public int appFormCls;
    
    /**
    * 退職後の郵便番号
    */
    @Basic(optional = false)
    @Column(name = "POST_CD")
    public String postCd;
    
    /**
    * 退職後の住所カナ
    */
    @Basic(optional = false)
    @Column(name = "RETIREMENT_ADD_BEFORE")
    public String retirementAddBefore;
    
    /**
    * 退職後の住所
    */
    @Basic(optional = false)
    @Column(name = "RETIREMENT_ADD")
    public String retirementAdd;
    
    /**
    * 喪失理由区分
    */
    @Basic(optional = false)
    @Column(name = "REASON_FOR_LOSS")
    public int reasonForLoss;
    
    /**
    * 適用終了理由区分
    */
    @Basic(optional = false)
    @Column(name = "REASON")
    public int reason;
    
    /**
    * 基金固有項目６
    */
    @Basic(optional = false)
    @Column(name = "FUND_SPECIFIC6")
    public String fundSpecific6;




    @Override
    protected Object getKey()
    {
        return emplPenFundInfoPk;
    }

    public EmplPenFundInfor toDomain() {
        return new EmplPenFundInfor(this.emplPenFundInfoPk.employeeId, this.addAppCtgSal, this.addSal, this.standSal, this.secAddSalary, this.secStandSal, this.addAppCtgBs, this.addBonus, this.standBonus, this.secStandBonus, this.secStandBonus, this.fundSpecific1, this.fundSpecific2, this.fundSpecific3, this.fundSpecific4, this.fundSpecific5, this.fundSpecific7, this.fundSpecific8, this.fundSpecific9, this.fundSpecific10, this.subType, this.appFormCls, this.postCd, this.retirementAddBefore, this.retirementAdd, this.reasonForLoss, this.reason, this.fundSpecific6);
    }
//    public static QqsmtEmplPenFundInfo toEntity(EmplPenFundInfor domain) {
//        return new QqsmtEmplPenFundInfo(
//                new QqsmtEmplPenFundInfoPk(domain.getEmployeeId()),
//                domain.getElecAppdetails().getAddAppCtgSal().get().value,
//                domain.getElecAppdetails().getAddSal().get().v(),
//                domain.getElecAppdetails().getStandSal().get().v(),
//                domain.getElecAppdetails().getSecAddSalary().get().v(),
//                domain.getElecAppdetails().getSecStandSal().get().v(),
//                domain.getElecAppdetails().getAddAppCtgBs().get().value,
//                domain.getElecAppdetails().getAddBonus().get().v(),
//                domain.getElecAppdetails().getStandBonus().get().v(),
//                domain.getElecAppdetails().getSecAddBonus().get().v(),
//                domain.getElecAppdetails().getFundSpecific1().get().v(),
//                domain.getElecAppdetails().getFundSpecific2().get().v(),
//                domain.getElecAppdetails().getFundSpecific3().get().v(),
//                domain.getElecAppdetails().getFundSpecific4().get().v(),
//                domain.getElecAppdetails().getFundSpecific5().get().v(),
//                domain.getElecAppdetails().getFundSpecific7().get().v(),
//                domain.getElecAppdetails().getFundSpecific8().get().v(),
//                domain.getElecAppdetails().getFundSpecific9().get().v(),
//                domain.getElecAppdetails().getFundSpecific10().get().v(),
//                domain.getElecAppdetails().getSubType().get().value,
//                domain.getElecAppdetails().getAppFormCls().get().value,
//                domain.getElecAppdetails().getPostCd().get(),
//                domain.getElecAppdetails().getRetirementAddBefore().get().v(),
//                domain.getElecAppdetails().getRetirementAdd().get().v(),
//                domain.getElecAppdetails().getReasonForLoss().get().value,
//                domain.getElecAppdetails().getReason().get().value,
//                domain.getElecAppdetails().getFundSpecific6().get().v()
//        );
//    }

}
