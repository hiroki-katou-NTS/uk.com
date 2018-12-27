package nts.uk.ctx.exio.app.find.exo.executionlog;

import lombok.Value;
import nts.uk.ctx.exio.app.find.exo.execlog.ExternalOutLogDto;

@Value
public class ErrorContentDto {
	private String nameSetting;
	private ExterOutExecLogDto resultLog;
	private ExternalOutLogDto[] errorLog;
}
