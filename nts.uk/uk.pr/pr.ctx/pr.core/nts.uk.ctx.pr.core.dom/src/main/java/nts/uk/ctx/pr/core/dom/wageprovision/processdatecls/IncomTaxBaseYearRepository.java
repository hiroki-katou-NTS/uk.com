package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import java.util.Optional;
import java.util.List;

/**
* 所得税基準年月日
*/
public interface IncomTaxBaseYearRepository
{

    List<IncomTaxBaseYear> getAllIncomTaxBaseYear();

    Optional<IncomTaxBaseYear> getIncomTaxBaseYearById(String cid, int processCateNo);

    void add(IncomTaxBaseYear domain);

    void update(IncomTaxBaseYear domain);

    void remove(String cid, int processCateNo);

}
