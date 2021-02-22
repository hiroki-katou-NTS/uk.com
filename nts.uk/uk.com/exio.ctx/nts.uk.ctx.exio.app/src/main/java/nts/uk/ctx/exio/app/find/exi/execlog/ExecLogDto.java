package nts.uk.ctx.exio.app.find.exi.execlog;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * The Class ExecLogDto.
 */
@Builder
@Data
public class ExecLogDto {

	/** The exac exe result log dtos. */
	private List<ExacExeResultLogDto> exacExeResultLogDtos;
	
	/** The error log dtos. */
	private List<ExacErrorLogDto> errorLogDtos;
}
