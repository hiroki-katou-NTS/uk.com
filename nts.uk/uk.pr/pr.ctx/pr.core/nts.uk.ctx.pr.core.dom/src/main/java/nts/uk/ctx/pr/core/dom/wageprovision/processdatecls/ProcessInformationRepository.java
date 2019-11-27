package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import java.util.List;
import java.util.Optional;

/**
 * 処理区分基本情報
 */
public interface ProcessInformationRepository {

	List<ProcessInformation> getAllProcessInformation();

	List<ProcessInformation> getProcessInformationByCid(String cid);

	Optional<ProcessInformation> getProcessInformationById(String cid, int processCateNo);

	List<ProcessInformation> getProcessInformationByDeprecatedCategory(String cid, int deprecatedCategory);

	void add(ProcessInformation domain);

	void update(ProcessInformation domain);

	void remove(String cid, int processCateNo);

}
