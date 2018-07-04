package nts.uk.ctx.exio.app.find.exo.executionlog;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.execlog.ExterOutExecLog;

@AllArgsConstructor
@Value
public class ExterOutExecLogDto {
	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * 外部出力処理ID
	 */
	private String outputProcessId;
	
	/**
	 * ファイルID
	 */
	private Optional<String> fileId;
	
	public static ExterOutExecLogDto fromDomain(ExterOutExecLog domain) {
		return new ExterOutExecLogDto(domain.getCompanyId(), domain.getOutputProcessId(), domain.getFileId());
	}
	
}
