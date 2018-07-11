package nts.uk.ctx.exio.app.find.exo.executionlog;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.exio.app.find.exo.execlog.ExternalOutLogDto;
@Getter
@Setter
public class ErrorContentDto {
	private String nameSetting;
	private ExterOutExecLogDto resultLog;
	private ExternalOutLogDto[] errorLog;
}
