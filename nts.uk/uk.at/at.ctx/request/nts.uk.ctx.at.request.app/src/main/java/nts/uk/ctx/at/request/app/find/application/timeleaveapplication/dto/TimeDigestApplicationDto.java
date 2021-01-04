package nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.appabsence.apptimedigest.TimeDigestApplication;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

import java.util.Optional;

@Data
@AllArgsConstructor
public class TimeDigestApplicationDto {

    private int appTimeType;

    private TimeDigestDto timeDigestDto;

}
