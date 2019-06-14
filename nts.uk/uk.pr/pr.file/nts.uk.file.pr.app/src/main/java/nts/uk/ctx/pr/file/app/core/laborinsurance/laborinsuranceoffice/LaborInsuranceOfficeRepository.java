package nts.uk.ctx.pr.file.app.core.laborinsurance.laborinsuranceoffice;

import java.util.List;

public interface LaborInsuranceOfficeRepository {

    List<Object[]> getLaborInsuranceOfficeByCompany(String cid);

}
