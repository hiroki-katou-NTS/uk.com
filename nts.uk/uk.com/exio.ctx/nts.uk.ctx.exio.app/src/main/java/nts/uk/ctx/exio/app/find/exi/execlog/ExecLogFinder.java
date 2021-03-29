package nts.uk.ctx.exio.app.find.exi.execlog;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exi.execlog.ExacErrorLogRepository;
import nts.uk.ctx.exio.dom.exi.execlog.ExacExeResultLogRepository;

/**
 * The Class ExecLogFinder.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ExecLogFinder {

	/** The exac error log repository. */
	@Inject
	private ExacErrorLogRepository exacErrorLogRepository;
	
	/** The exac exe result log repository. */
	@Inject
	private ExacExeResultLogRepository exacExeResultLogRepository;
	
	/**
	 * Gets the exac exe result log by process id.
	 *	1. ドメインモデル「外部受入実行結果ログ」を取得する
	 * @param externalProcessId the external process id
	 * @return the exac exe result log by process id
	 */
	public List<ExacExeResultLogDto> getExacExeResultLogByProcessId(String externalProcessId) {
		return this.exacExeResultLogRepository.getExacExeResultLogByProcessId(externalProcessId).stream()
				.map(item -> ExacExeResultLogDto.fromDomain(item))
				.collect(Collectors.toList());
	}
	
	/**
	 * Gets the exac error log by process id.
	 *	2. ドメインモデル「外部受入エラーログ」を取得する
	 * @param exacProcessId the exac process id
	 * @return the exac error log by process id
	 */
	public List<ExacErrorLogDto> getExacErrorLogByProcessId(String exacProcessId) {
		return this.exacErrorLogRepository.getExacErrorLogByProcessId(exacProcessId).stream()
				.map(item -> ExacErrorLogDto.fromDomain(item))
				.sorted(Comparator
						.comparingInt(ExacErrorLogDto::getRecordNumber) // ドメイン取得時にレコード番号
						.thenComparingInt(ExacErrorLogDto::getLogSeqNumber)) // ログ連番順に取得する
				.collect(Collectors.toList());
	}
	
	/**
	 * Gets the error list.
	 *	アルゴリズム「エラー一覧表示」を実行する
	 * @param externalProcessId the external process id
	 * @return the error list
	 */
	public ExecLogDto getErrorList(String externalProcessId) {
		// 1. ドメインモデル「外部受入実行結果ログ」を取得する
		List<ExacExeResultLogDto> listExacExeResultLogDtos = this.getExacExeResultLogByProcessId(externalProcessId);
		// 2. ドメインモデル「外部受入エラーログ」を取得する
		List<ExacErrorLogDto> listExacErrorLogDtos = this.getExacErrorLogByProcessId(externalProcessId);
		// 3. エラー一覧ダイアログに編集して表示する
		return ExecLogDto.builder()
				.exacExeResultLogDtos(listExacExeResultLogDtos)
				.errorLogDtos(listExacErrorLogDtos)
				.build();
	}
	
}
