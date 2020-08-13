package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime;

import java.util.Optional;

public interface OvertimeAppSetRepository {
    Optional<OvertimeAppSet> findByCompanyId(String companyId);
}
