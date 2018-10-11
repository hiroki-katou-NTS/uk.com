package nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice;

import java.util.Optional;
import java.util.List;

/**
* 労働保険事業所
*/
public interface LaborInsuranceOfficeRepository
{

    List<LaborInsuranceOffice> getLaborInsuranceOfficeByCompany();

    Optional<LaborInsuranceOffice> getLaborInsuranceOfficeById(String laborOfficeCode);

    void add(LaborInsuranceOffice domain);

    void update(LaborInsuranceOffice domain);

    void remove(String laborOfficeCode);

}
