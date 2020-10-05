package nts.uk.ctx.at.shared.app.find.workcheduleworkrecord.appreflectprocess.appreflectcondition.timeleaveapplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeLeaveDestinationDto {
    private int firstBeforeWork;
    private int secondBeforeWork;
    private int firstAfterWork;
    private int secondAfterWork;
    private int privateGoingOut;
    private int unionGoingOut;
}
