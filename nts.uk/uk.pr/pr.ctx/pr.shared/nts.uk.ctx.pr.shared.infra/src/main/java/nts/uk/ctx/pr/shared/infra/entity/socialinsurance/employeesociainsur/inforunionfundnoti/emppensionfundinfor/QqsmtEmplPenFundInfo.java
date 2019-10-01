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
@Table(name = "QQSDT_KIKIN_EGOV_INFO")
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
    @Column(name = "S_ADDITION_ATR")
    public int addAppCtgSal;
    
    /**
    * 加算給与月額
    */
    @Basic(optional = false)
    @Column(name = "S_ADD_MONTHLY_AMOUNT_1")
    public String addSal;
    
    /**
    * 標準給与月額
    */
    @Basic(optional = false)
    @Column(name = "S_SRD_MONTHLY_AMOUNT_1")
    public String standSal;
    
    /**
    * 第２加算給与月額
    */
    @Basic(optional = false)
    @Column(name = "S_ADD_MONTHLY_AMOUNT_2")
    public String secAddSalary;
    
    /**
    * 第２標準給与月額
    */
    @Basic(optional = false)
    @Column(name = "S_SRD_MONTHLY_AMOUNT_2")
    public String secStandSal;
    
    /**
    * 加算適用区分(賞与)
    */
    @Basic(optional = false)
    @Column(name = "B_ADDITION_ATE")
    public int addAppCtgBs;
    
    /**
    * 加算賞与月額
    */
    @Basic(optional = false)
    @Column(name = "B_ADD_MONTHLY_AMOUNT_1")
    public String addBonus;
    
    /**
    * 標準賞与月額
    */
    @Basic(optional = false)
    @Column(name = "B_SRD_MONTHLY_AMOUNT_1")
    public String standBonus;
    
    /**
    * 第２加算賞与月額
    */
    @Basic(optional = false)
    @Column(name = "B_ADD_MONTHLY_AMOUNT_2")
    public String secStandBonus;
    
    /**
    * 第２標準賞与月額
    */
    @Basic(optional = false)
    @Column(name = "B_SRD_MONTHLY_AMOUNT_2")
    public String secAddBonus;
    
    /**
    * 基金固有項目１
    */
    @Basic(optional = false)
    @Column(name = "SPECIFIC_ITEM_1")
    public String fundSpecific1;
    
    /**
    * 基金固有項目２
    */
    @Basic(optional = false)
    @Column(name = "SPECIFIC_ITEM_2")
    public String fundSpecific2;
    
    /**
    * 基金固有項目３
    */
    @Basic(optional = false)
    @Column(name = "SPECIFIC_ITEM_3")
    public String fundSpecific3;
    
    /**
    * 基金固有項目４
    */
    @Basic(optional = false)
    @Column(name = "SPECIFIC_ITEM_4")
    public String fundSpecific4;
    
    /**
    * 基金固有項目５
    */
    @Basic(optional = false)
    @Column(name = "SPECIFIC_ITEM_5")
    public String fundSpecific5;
    
    /**
    * 基金固有項目７
    */
    @Basic(optional = false)
    @Column(name = "SPECIFIC_ITEM_7")
    public String fundSpecific7;
    
    /**
    * 基金固有項目８
    */
    @Basic(optional = false)
    @Column(name = "SPECIFIC_ITEM_8")
    public String fundSpecific8;
    
    /**
    * 基金固有項目９
    */
    @Basic(optional = false)
    @Column(name = "SPECIFIC_ITEM_9")
    public String fundSpecific9;
    
    /**
    * 基金固有項目１０
    */
    @Basic(optional = false)
    @Column(name = "SPECIFIC_ITEM_10")
    public String fundSpecific10;
    
    /**
    * 加入形態区分
    */
    @Basic(optional = false)
    @Column(name = "ENTRY_TYPE_ATR")
    public int subType;
    
    /**
    * 適用形態区分
    */
    @Basic(optional = false)
    @Column(name = "APPLY_TYPE_ATR")
    public int appFormCls;
    
    /**
    * 退職後の郵便番号
    */
    @Basic(optional = false)
    @Column(name = "POSTALCD_AFTER_RETIRE")
    public String postCd;
    
    /**
    * 退職後の住所カナ
    */
    @Basic(optional = false)
    @Column(name = "ADDRESS_KN_AFTER_RETIRE")
    public String retirementAddBefore;
    
    /**
    * 退職後の住所
    */
    @Basic(optional = false)
    @Column(name = "ADDRESS_AFTER_RETIRE")
    public String retirementAdd;
    
    /**
    * 喪失理由区分
    */
    @Basic(optional = false)
    @Column(name = "LOSS_REASON_ATR")
    public int reasonForLoss;
    
    /**
    * 適用終了理由区分
    */
    @Basic(optional = false)
    @Column(name = "END_REASON_ATR")
    public int reason;
    
    /**
    * 基金固有項目６
    */
    @Basic(optional = false)
    @Column(name = "SPECIFIC_ITEM_6")
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
