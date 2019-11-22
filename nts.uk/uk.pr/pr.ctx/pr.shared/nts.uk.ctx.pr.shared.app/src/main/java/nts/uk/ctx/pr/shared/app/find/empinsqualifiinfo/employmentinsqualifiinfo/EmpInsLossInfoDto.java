package nts.uk.ctx.pr.shared.app.find.empinsqualifiinfo.employmentinsqualifiinfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@AllArgsConstructor
@Value
@Builder
public class EmpInsLossInfoDto {
    /**
     * 会社ID
     */
    private String cId;

    /**
     * 社員ID
     */
    private String sId;

    /**
     * 喪失原因
     */
    private Integer causeOfLossAtr;

    /**
     * 離職票交付希望区分
     */
    private Integer requestForIssuance;

    /**
     * 補充予定区分
     */
    private Integer scheduleForReplenishment;

    /**
     * 喪失原因
     */
    private String causeOfLossEmpInsurance;

    /**
     * 週間の所定労働時間
     */
    private Integer scheduleWorkingHourPerWeek;

}
