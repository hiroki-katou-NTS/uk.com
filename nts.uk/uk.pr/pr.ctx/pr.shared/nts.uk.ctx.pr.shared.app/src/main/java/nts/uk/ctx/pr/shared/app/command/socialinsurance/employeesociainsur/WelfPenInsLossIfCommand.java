package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur;

import lombok.Value;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfPenInsLossIf;


@Value
public class WelfPenInsLossIfCommand {
    /**
     * 社員ID
     */
    private String empId;

    /**
     * その他
     */
    private int other;

    /**
     * その他理由
     */
    private String otherReason;

    /**
     * 保険証回収添付枚数
     */
    private Integer caInsuarace;

    /**
     * 保険証回収返不能枚数
     */
    private Integer numRecoved;

    /**
     * 資格喪失原因
     */
    private Integer cause;


    private int screenMode;

    public WelfPenInsLossIf fromCommandToDomain(){
        return new WelfPenInsLossIf(empId, other, otherReason, caInsuarace, numRecoved, cause);
    }
}
