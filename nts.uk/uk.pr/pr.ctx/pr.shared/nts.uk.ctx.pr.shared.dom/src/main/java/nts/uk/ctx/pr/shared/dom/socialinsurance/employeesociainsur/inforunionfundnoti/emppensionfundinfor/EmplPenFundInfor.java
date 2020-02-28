package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.inforunionfundnoti.emppensionfundinfor;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 社員厚生年金基金情報
*/
@Getter
public class EmplPenFundInfor extends AggregateRoot {
    
    /**
    * 社員ID
    */
    private String employeeId;
    
    /**
    * 電子申請内容
    */
    private EmpPensionFundInfor elecAppdetails;
    
    public EmplPenFundInfor(String employeeId, int addAppCtgSal, String addSal, String standSal, String secAddSalary, String secStandSal, int addAppCtgBs, String addBonus, String standBonus, String secStandBonus, String secAddBonus, String fundSpecific1, String fundSpecific2, String fundSpecific3, String fundSpecific4, String fundSpecific5, String fundSpecific7, String fundSpecific8, String fundSpecific9, String fundSpecific10, int subType, int appFormCls, String postCd, String retirementAddBefore, String retirementAdd, int reasonForLoss, int reason, String fundSpecific6) {
        this.employeeId = employeeId;
        this.elecAppdetails = new EmpPensionFundInfor(addAppCtgSal,addSal,standSal,secAddSalary,secStandSal,addAppCtgBs,addBonus,standBonus,secStandBonus,secAddBonus,fundSpecific1,fundSpecific2,fundSpecific3,fundSpecific4,fundSpecific5,fundSpecific7,fundSpecific8,fundSpecific9,fundSpecific10,subType,appFormCls,postCd,retirementAddBefore,retirementAdd,reasonForLoss,reason,fundSpecific6);
    }
    
}
