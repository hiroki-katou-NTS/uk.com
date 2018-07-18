package nts.uk.ctx.exio.app.find.exo.executionlog;

import lombok.Value;
import nts.uk.ctx.exio.dom.exo.execlog.ExterOutExecLog;

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

	public static ExterOutExecLogDto fromDomain(ExterOutExecLog domain) {
		return new ExterOutExecLogDto(domain.getCompanyId(), domain.getOutputProcessId());
	}
}
