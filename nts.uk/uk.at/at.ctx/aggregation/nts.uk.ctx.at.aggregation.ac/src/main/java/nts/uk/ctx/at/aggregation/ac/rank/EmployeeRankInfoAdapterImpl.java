package nts.uk.ctx.at.aggregation.ac.rank;

import nts.uk.ctx.at.aggregation.dom.adapter.rank.EmployeeRankInfoAdapter;
import nts.uk.ctx.at.aggregation.dom.adapter.rank.EmployeeRankInfoImported;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class EmployeeRankInfoAdapterImpl implements EmployeeRankInfoAdapter {
    @Override
    public List<EmployeeRankInfoImported> get(List<String> employeeIds) {
        return new ArrayList<>();
    }
}
