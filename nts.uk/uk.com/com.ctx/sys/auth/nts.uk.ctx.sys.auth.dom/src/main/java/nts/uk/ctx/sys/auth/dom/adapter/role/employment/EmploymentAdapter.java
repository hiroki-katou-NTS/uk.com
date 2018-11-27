package nts.uk.ctx.sys.auth.dom.adapter.role.employment;

import java.util.List;

public interface EmploymentAdapter {
    List<EmploymentRolePubDto> getAllByCompanyId(String companyId);
}
