package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import java.util.Optional;
import java.util.List;

/**
* 処理区分基本情報
*/
public interface ProcessInformationRepository
{

    List<ProcessInformation> getAllProcessInformation();

    Optional<ProcessInformation> getProcessInformationById(String cid, int processCateNo);
    
    List<ProcessInformation> getProcessInformationByDeprecatedCategory(String cid, int deprecatedCategory);

    void add(ProcessInformation domain);

    void update(ProcessInformation domain);

    void remove(String cid, int processCateNo);

}
