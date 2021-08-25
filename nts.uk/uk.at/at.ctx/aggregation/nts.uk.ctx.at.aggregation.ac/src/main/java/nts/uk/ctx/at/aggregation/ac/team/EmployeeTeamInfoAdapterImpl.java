package nts.uk.ctx.at.aggregation.ac.team;

import nts.uk.ctx.at.aggregation.dom.adapter.team.EmployeeTeamInfoAdapter;
import nts.uk.ctx.at.aggregation.dom.adapter.team.EmployeeTeamInfoImported;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class EmployeeTeamInfoAdapterImpl implements EmployeeTeamInfoAdapter {
    @Override
    public List<EmployeeTeamInfoImported> get(List<String> employeeIds) {
        return new ArrayList<>();
    }
}
