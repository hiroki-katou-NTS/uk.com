package nts.uk.ctx.exio.app.find.exi.execlog;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.exio.dom.exi.execlog.ExacExeResultLogRepository;

@Stateless
/**
 * 外部受入実行結果ログ
 */
public class ExacExeResultLogFinder {

	@Inject
	private ExacExeResultLogRepository finder;

	public List<ExacExeResultLogDto> getAllExacExeResultLog() {
		return finder.getAllExacExeResultLog().stream().map(item -> ExacExeResultLogDto.fromDomain(item))
				.collect(Collectors.toList());
	}

	/**
	 * @param externalProcessId
	 * @return
	 */
	public List<ExacExeResultLogDto> getExacExeResultLogByProcessId(String externalProcessId) {
		return finder.getExacExeResultLogByProcessId(externalProcessId).stream()
				.map(item -> ExacExeResultLogDto.fromDomain(item)).collect(Collectors.toList());
	}

}
