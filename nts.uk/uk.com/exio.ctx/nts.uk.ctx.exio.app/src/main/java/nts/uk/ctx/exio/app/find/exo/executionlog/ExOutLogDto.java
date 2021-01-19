package nts.uk.ctx.exio.app.find.exo.executionlog;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.exio.app.find.exo.execlog.ExternalOutLogDto;

/**
 * The Class ExOutLogDto.
 */

@Data
@Builder
public class ExOutLogDto {

	/** The exter out exec log dto. */
	private ExterOutExecLogDto exterOutExecLogDto;
	
	/** The list external out log dtos. */
	private List<ExternalOutLogDto> listExternalOutLogDtos;
}
