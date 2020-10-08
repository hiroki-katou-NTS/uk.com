package nts.uk.ctx.at.function.ac.condset;

import nts.uk.ctx.at.function.dom.adapter.condset.StdOutputCondSetAdapter;
import nts.uk.ctx.at.function.dom.adapter.condset.StdOutputCondSetImport;
import nts.uk.query.pub.stdcondset.StdOutputCondSetPublisher;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class Standard output condition setting adapter impl.<br>
 * 出力条件設定（定型）
 *
 * @author nws-minhnb
 */
public class StdOutputCondSetAdapterImpl implements StdOutputCondSetAdapter {

	/**
	 * The Standard output condition setting publisher.
	 */
	@Inject
	private StdOutputCondSetPublisher stdOutputCondSetPub;

	/**
	 * Finds all standard output condition setting by company id.
	 * 
	 * @param cid the Company id 「会社ID」
	 * @return the <code>StdOutputCondSetExport</code> list
	 */
	@Override
	public List<StdOutputCondSetImport> findAllStdOutputCondSetsByCid(String cid) {
		return this.stdOutputCondSetPub.getAllStdOutputCondSetsByCid(cid)
									   .stream()
									   .map(export -> new StdOutputCondSetImport(
															export.getCid(),
															export.getConditionSetCd(),
															export.getCategoryId(),
															export.getDelimiter(),
															export.getItemOutputName(),
															export.getAutoExecution(),
															export.getConditionSetName(),
															export.getConditionOutputName(),
															export.getStringFormat()))
									   .collect(Collectors.toList());
	}

}
