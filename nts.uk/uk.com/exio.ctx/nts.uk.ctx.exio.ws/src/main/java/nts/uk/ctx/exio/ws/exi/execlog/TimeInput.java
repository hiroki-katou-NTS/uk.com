package nts.uk.ctx.exio.ws.exi.execlog;

import lombok.Value;
import nts.arc.time.GeneralDateTime;

@Value
public class TimeInput {
	GeneralDateTime start;
	GeneralDateTime end;
}
