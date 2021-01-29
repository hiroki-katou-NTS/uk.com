package nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;

@Data
public class OutingTimeZoneDto {
    private int frameNo;
    private Integer outingAtr;
    private Integer startTime;
    private Integer endTime;

//    public OutingTimeSheet toOutingTimeSheet() {
//        return new OutingTimeSheet();
//    }
}
