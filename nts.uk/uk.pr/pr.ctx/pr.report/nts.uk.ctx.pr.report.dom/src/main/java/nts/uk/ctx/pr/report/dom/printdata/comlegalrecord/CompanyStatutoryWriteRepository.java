package nts.uk.ctx.pr.report.dom.printdata.comlegalrecord;

import java.util.List;
import java.util.Optional;

/**
* 法定調書用会社
*/
public interface CompanyStatutoryWriteRepository
{

    List<CompanyStatutoryWrite> getAllCompanyStatutoryWrite();

    Optional<CompanyStatutoryWrite> getCompanyStatutoryWriteById(String cid, String code);

    void add(CompanyStatutoryWrite domain);

    void update(CompanyStatutoryWrite domain);

    void remove(String cid, String code);

    List<CompanyStatutoryWrite> getByCid(String cid);

}
