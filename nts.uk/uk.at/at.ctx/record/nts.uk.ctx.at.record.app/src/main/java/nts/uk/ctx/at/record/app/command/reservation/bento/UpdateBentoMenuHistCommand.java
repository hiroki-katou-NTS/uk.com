package nts.uk.ctx.at.record.app.command.reservation.bento;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class UpdateBentoMenuHistCommand {
    public GeneralDate startDatePerio;
    public GeneralDate endDatePerio;
    public String historyId;

}

