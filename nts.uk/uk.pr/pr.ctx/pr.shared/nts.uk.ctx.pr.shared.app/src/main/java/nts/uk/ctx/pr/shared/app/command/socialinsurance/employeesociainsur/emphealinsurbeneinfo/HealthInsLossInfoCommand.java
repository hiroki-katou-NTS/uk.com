package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.Value;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.HealthInsLossInfo;

@Value
public class HealthInsLossInfoCommand  {

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
    private Integer caInsurance;

    /**
     * 保険証回収返不能枚数
     */
    private Integer numRecoved;

    /**
     * 資格喪失原因
     */
    private Integer cause;

    private int screenMode;

    public HealthInsLossInfo fromCommandToDomain(){
        return new HealthInsLossInfo(empId, other, otherReason, caInsurance, numRecoved, cause);
    }

}
