package nts.uk.ctx.exio.app.find.exo.executionlog;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.exio.app.find.exo.execlog.ExternalOutLogDto;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;
import nts.uk.ctx.exio.dom.exo.execlog.ExterOutExecLogRepository;
import nts.uk.ctx.exio.dom.exo.execlog.ExternalOutLogRepository;
import nts.uk.ctx.exio.dom.exo.execlog.ProcessingClassification;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class ExOutLogFinder.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ExOutLogFinder {
	
	@Inject
	private ExterOutExecLogRepository exterOutExecLogRepository;
	
	@Inject
	private ExternalOutLogRepository externalOutLogRepository;
	
	@Inject
	private StdOutputCondSetRepository stdOutputCondSetRepository;

	/**
	 * Gets the exter out exec log by id.
	 *	ドメインモデル「外部出力実行結果ログ」および「出力条件設定」を取得する
	 * @param storeProcessingId the store processing id
	 * @return the exter out exec log by id
	 */
	public ExterOutExecLogDto getExterOutExecLogById(String storeProcessingId) {
		String companyId = AppContexts.user().companyId();
		return exterOutExecLogRepository.getExterOutExecLogById(companyId, storeProcessingId)
				.map(item -> ExterOutExecLogDto.fromDomain(item)).orElse(null);
	}

	/**
	 * Gets the external out log by id.
	 *	ドメインモデル「外部出力結果ログ」を取得する
	 * @param storeProcessingId the store processing id
	 * @return the external out log by id
	 */
	public List<ExternalOutLogDto> getExternalOutLogById(String storeProcessingId) {
		String companyId = AppContexts.user().companyId();
		int processContent = ProcessingClassification.ERROR.value;
		return externalOutLogRepository.getExternalOutLogById(companyId, storeProcessingId, processContent).stream()
				.map(item -> {
					return ExternalOutLogDto.fromDomain(item);
				})
				.sorted((o1, o2) -> o1.getLogRegisterDateTime().compareTo(o2.getLogRegisterDateTime()))
				.collect(Collectors.toList());
	}
	
	/**
	 * Gets the std output cond set by id.
	 *	Get 出力条件設定
	 * @param cid the cid
	 * @param conditionSetCd the condition set cd
	 * @return the std output cond set by id
	 */
	public StdOutputCondSet getStdOutputCondSetById(String cid, String conditionSetCd) {
		Optional<StdOutputCondSet> stdOutputCondSet = this.stdOutputCondSetRepository.getStdOutputCondSetById(cid, conditionSetCd);
		if (stdOutputCondSet.isPresent()) {
			return stdOutputCondSet.get();
		}
		return null;
	}
	
	/**
	 * Gets the ex out log dto.
	 *	アルゴリズム「外部出力エラーログ設定」を実行する
	 * @param storeProcessingId the store processing id
	 * @return the ex out log dto
	 */
	public ExOutLogDto getExOutLogDto(String storeProcessingId) {
	//	1. ドメインモデル「外部出力実行結果ロLグ」および「出力条件設定」を取得する
	ExterOutExecLogDto exterOutExecLogDto = this.getExterOutExecLogById(storeProcessingId);
	// TODO - not found 出力条件設定
	// 	出力条件設定.条件設定コード　＝　条件設定コード
	//	※定型区分がユーザの場合
	//	　・出力条件設定.ユーザID　＝ 　ユーザID
	StdOutputCondSet stdOutputCondSet = this.getStdOutputCondSetById(AppContexts.user().companyId(), AppContexts.user().userId());
	//	2. ドメインモデル「外部出力結果ログ」を取得する
	List<ExternalOutLogDto> listExternalOutLogDtos = this.getExternalOutLogById(storeProcessingId);
	//	3. ドメインモデル「外部出力結果ログ」を取得する
	//TODO
	//	4. 画面に反映する
	//TODO
	return ExOutLogDto.builder()
			.listExternalOutLogDtos(listExternalOutLogDtos)
			.exterOutExecLogDto(exterOutExecLogDto)
			.build();
	}
}
