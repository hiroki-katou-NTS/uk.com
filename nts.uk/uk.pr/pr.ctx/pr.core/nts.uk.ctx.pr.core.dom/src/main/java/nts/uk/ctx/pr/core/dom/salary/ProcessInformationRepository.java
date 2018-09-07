package nts.uk.ctx.pr.core.dom.salary;

import java.util.Optional;
import java.util.List;

/**
* 処理区分基本情報
*/
public interface ProcessInformationRepository
{

    List<ProcessInformation> getAllProcessInformation();

    Optional<ProcessInformation> getProcessInformationById(String cid, int processCateNo);

    void add(ProcessInformation domain);

    void update(ProcessInformation domain);

    void remove(String cid, int processCateNo);

}
