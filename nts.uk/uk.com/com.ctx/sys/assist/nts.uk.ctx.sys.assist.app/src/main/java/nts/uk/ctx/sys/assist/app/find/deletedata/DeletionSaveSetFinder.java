package nts.uk.ctx.sys.assist.app.find.deletedata;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.app.find.autosetting.deletion.DeleteSetDto;
import nts.uk.ctx.sys.assist.dom.deletedata.ResultDeletionRepository;

/**
 * アルゴリズム「データ削除実行履歴検索」
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DeletionSaveSetFinder {
	
	@Inject
	private ResultDeletionRepository resultDeletionRepository;
	
	public List<DeleteSetDto> findSaveSet(GeneralDateTime from, GeneralDateTime to) {
		return resultDeletionRepository.getByStartDatetimeDel(from, to)
				.stream()
				.map(DeleteSetDto::fromDomain)
				.distinct()
				.sorted(Comparator.comparing(DeleteSetDto::getPatternCode))
				.collect(Collectors.toList());
	}
}
