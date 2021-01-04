package nts.uk.ctx.at.request.app.command.application.timeleaveapplication;

import lombok.Data;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeDigestDto;

@Data
public class TimeDigestApplicationCommand {

    private int appTimeType;

    private TimeDigestDto timeDigestApplication;

}
