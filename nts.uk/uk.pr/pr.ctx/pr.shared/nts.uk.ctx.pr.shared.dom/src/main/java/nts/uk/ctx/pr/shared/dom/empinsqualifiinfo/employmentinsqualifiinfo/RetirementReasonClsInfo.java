package nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@Setter
/**
 * 退職解雇理由区分情報
 */
public class RetirementReasonClsInfo extends AggregateRoot{

    /**
     * 会社ID
     */
    private String cId;

    /**
     * 退職解雇理由区分コード
     */
    private RetirementReasonClsCode retirementReasonClsCode;

    /**
     * 退職解雇理由名称
     */
    private RetirementReasonClsName retirementReasonClsName;

    public RetirementReasonClsInfo() {};

    public RetirementReasonClsInfo(String cId, String reasonTermination, String retirementReasonClsName){
        this.cId = cId;
        this.retirementReasonClsCode = new RetirementReasonClsCode(reasonTermination);
        this.retirementReasonClsName = new RetirementReasonClsName(retirementReasonClsName);
    }

}
