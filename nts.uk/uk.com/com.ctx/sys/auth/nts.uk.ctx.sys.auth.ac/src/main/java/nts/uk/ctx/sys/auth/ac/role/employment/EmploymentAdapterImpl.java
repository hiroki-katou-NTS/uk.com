package nts.uk.ctx.sys.auth.ac.role.employment;

import nts.uk.ctx.at.auth.pub.employmentrole.EmploymentRolePub;
import nts.uk.ctx.sys.auth.dom.adapter.role.employment.EmploymentAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.role.employment.EmploymentRolePubDto;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class EmploymentAdapterImpl implements EmploymentAdapter {
    @Inject
    private EmploymentRolePub mEmploymentRolePub;
    @Override
    public List<EmploymentRolePubDto> getAllByCompanyId(String companyId) {
        return mEmploymentRolePub.getAllByCompanyId(companyId).stream().map(x -> new EmploymentRolePubDto(x.getCompanyId(),
                x.getRoleId(),
                x.getScheduleEmployeeRef(),
                x.getBookEmployeeRef(),
                x.getEmployeeRefSpecAgent(),
                x.getPresentInqEmployeeRef(),
                x.getFutureDateRefPermit())).collect(Collectors.toList());
    }
}
