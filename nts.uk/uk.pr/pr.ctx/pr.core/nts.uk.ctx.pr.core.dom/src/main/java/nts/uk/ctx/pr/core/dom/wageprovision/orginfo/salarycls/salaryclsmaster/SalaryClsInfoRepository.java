package nts.uk.ctx.pr.core.dom.wageprovision.orginfo.salarycls.salaryclsmaster;

import java.util.List;
import java.util.Optional;

public interface SalaryClsInfoRepository {
    List<SalaryClsInfo> findAll();

    Optional<SalaryClsInfo> get(String salaryClsCode);

    void insert(SalaryClsInfo salaryClsInfo);

    void delete(String salaryClsCode);

    void update(SalaryClsInfo salaryClsInfo);

}
