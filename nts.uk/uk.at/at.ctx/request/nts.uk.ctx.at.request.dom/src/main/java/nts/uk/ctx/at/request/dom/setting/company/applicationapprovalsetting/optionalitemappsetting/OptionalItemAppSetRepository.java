package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.optionalitemappsetting;

import java.util.List;
import java.util.Optional;

public interface OptionalItemAppSetRepository {
    Optional<OptionalItemApplicationSetting> findByCompanyAndCode(String companyId, String code);
    List<OptionalItemApplicationSetting> findByCompany(String companyId);
    void save(OptionalItemApplicationSetting domain);
    void delete(String companyId, String code);
}
