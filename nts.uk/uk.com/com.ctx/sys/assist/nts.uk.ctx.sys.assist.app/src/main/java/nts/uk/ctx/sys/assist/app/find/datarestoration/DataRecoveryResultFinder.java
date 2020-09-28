package nts.uk.ctx.sys.assist.app.find.datarestoration;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryResultRepository;

/**
 * アルゴリズム「保存セット一覧表示」
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DataRecoveryResultFinder {
	@Inject
	private DataRecoveryResultRepository dataRecoveryResultRepository;
	
	public List<SaveSetDto> getDataRecoveryResultByStartDatetime(GeneralDateTime from, GeneralDateTime to) {
		/**
		 * ドメインモデル「データ復旧の結果」から保存セットコードで集約して保存名称を取得する
		 */
		return dataRecoveryResultRepository.getDataRecoveryResultByStartDatetime(from, to)
											.parallelStream()
											.map(SaveSetDto::fromDomain)
											.collect(Collectors.toList());
	}
}
