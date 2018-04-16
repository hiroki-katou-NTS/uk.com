package nts.uk.ctx.exio.app.find.exi.execlog;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.exio.dom.exi.execlog.ExacErrorLogRepository;

@Stateless
/**
 * 外部受入エラーログ
 */
public class ExacErrorLogFinder {

	@Inject
	private ExacErrorLogRepository finder;

	/**
	 * @return
	 */
	public List<ExacErrorLogDto> getAllExacErrorLog() {
		return finder.getAllExacErrorLog().stream().map(item -> ExacErrorLogDto.fromDomain(item))
				.collect(Collectors.toList());
	}

	/**
	 * @param exacProcessId
	 * @return
	 */
	public List<ExacErrorLogDto> getExacErrorLogByProcessId(String exacProcessId) {
		return finder.getExacErrorLogByProcessId(exacProcessId).stream().map(item -> ExacErrorLogDto.fromDomain(item))
				.collect(Collectors.toList());
	}
}
