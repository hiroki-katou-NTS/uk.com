package nts.uk.ctx.exio.app.find.exo.executionlog;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.execlog.ExterOutExecLogRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 外部出力実行結果ログ
 */
@Stateless
public class ExterOutExecLogFinder {

	@Inject
	private ExterOutExecLogRepository exterOutExecLogRepository;

	public ExterOutExecLogDto getExterOutExecLogById(String storeProcessingId) {
		String companyId = AppContexts.user().companyId();
		return exterOutExecLogRepository.getExterOutExecLogById(companyId, storeProcessingId)
				.map(item -> ExterOutExecLogDto.fromDomain(item)).orElse(null);
	}
}
