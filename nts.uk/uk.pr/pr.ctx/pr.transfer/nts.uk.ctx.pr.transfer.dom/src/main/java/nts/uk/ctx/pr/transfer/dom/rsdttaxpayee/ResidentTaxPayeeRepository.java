package nts.uk.ctx.pr.transfer.dom.rsdttaxpayee;

import java.util.List;
import java.util.Optional;

/**
 * 住民税納付先
 */
public interface ResidentTaxPayeeRepository {

    List<ResidentTaxPayee> getAllResidentTaxPayee();

    Optional<ResidentTaxPayee> getResidentTaxPayeeById(String cid, String code);

    void add(ResidentTaxPayee domain);

    void update(ResidentTaxPayee domain);

    void remove(String cid, String code);

}
