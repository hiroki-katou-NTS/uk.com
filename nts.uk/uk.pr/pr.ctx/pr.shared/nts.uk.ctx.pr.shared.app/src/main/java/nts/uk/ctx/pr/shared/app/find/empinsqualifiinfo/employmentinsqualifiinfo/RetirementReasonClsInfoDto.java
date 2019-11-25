package nts.uk.ctx.pr.shared.app.find.empinsqualifiinfo.employmentinsqualifiinfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@AllArgsConstructor
@Value
@Builder
public class RetirementReasonClsInfoDto {
    /**
     * 会社ID
     */
    private String cId;

    /**
     * 退職解雇理由区分コード
     */
    private String retirementReasonClsCode;

    /**
     * 退職解雇理由名称
     */
    private String retirementReasonClsName;

}
