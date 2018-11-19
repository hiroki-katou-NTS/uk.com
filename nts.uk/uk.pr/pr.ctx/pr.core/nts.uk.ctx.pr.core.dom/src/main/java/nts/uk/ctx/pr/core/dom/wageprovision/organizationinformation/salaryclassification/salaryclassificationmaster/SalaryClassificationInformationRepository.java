package nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster;

import java.util.Optional;
import java.util.List;

/**
* 給与分類情報
*/
public interface SalaryClassificationInformationRepository
{

    List<SalaryClassificationInformation> getAllSalaryClassificationInformation(String cid);

    Optional<SalaryClassificationInformation> getSalaryClassificationInformationById(String cid, String salaryClsCd);

    void add(SalaryClassificationInformation domain);

    void update(SalaryClassificationInformation domain);

    void remove(String cid, String salaryClsCd);

}
