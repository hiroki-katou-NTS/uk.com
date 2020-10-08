package nts.uk.query.pubimp.stdcondset;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;
import nts.uk.query.pub.stdcondset.StdOutputCondSetExport;
import nts.uk.query.pub.stdcondset.StdOutputCondSetPublisher;

/**
 * The class Standard output condition setting publisher impl.<br>
 * 出力条件設定（定型）
 * 
 * @author nws-minhnb
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class StdOutputCondSetPubImpl implements StdOutputCondSetPublisher {

	/**
	 * The Standard output condition setting repository.
	 */
	@Inject
	private StdOutputCondSetRepository mainRepo;

	/**
	 * Gets all Standard output condition setting
	 * 
	 * @param cid the Company id 「会社ID」
	 * @return the <code>StdOutputCondSetExport</code> list
	 */
	@Override
	public List<StdOutputCondSetExport> getAllStdOutputCondSetsByCid(String cid) {
		return this.mainRepo.getStdOutCondSetByCid(cid).stream()
				.map(domain -> new StdOutputCondSetExport(
						domain.getCid(),
						domain.getConditionSetCode().v(),
						domain.getCategoryId().v(),
						domain.getDelimiter().value,
						domain.getItemOutputName().value,
						domain.getAutoExecution().value,
						domain.getConditionSetName().v(),
						domain.getConditionOutputName().value,
						domain.getStringFormat().value))
				.collect(Collectors.toList());
	}

}
