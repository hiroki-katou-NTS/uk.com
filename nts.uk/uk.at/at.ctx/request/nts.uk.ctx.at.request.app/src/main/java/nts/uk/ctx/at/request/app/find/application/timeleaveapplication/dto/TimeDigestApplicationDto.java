package nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimeDigestApplicationDto {

    /**
     * 60H超休
     */
    private Integer super60AppTime;

    /**
     * 介護時間
     */
    private Integer careAppTime;

    /**
     * 子の看護時間
     */
    private Integer childCareAppTime;

    /**
     * 時間代休時間
     */
    private Integer substituteAppTime;

    /**
     * 時間特別休暇
     */
    private Integer specialAppTime;

    /**
     * 時間年休時間
     */
    private Integer annualAppTime;

    /**
     * 特別休暇枠NO
     */
    private Integer specialLeaveFrameNo;

}
