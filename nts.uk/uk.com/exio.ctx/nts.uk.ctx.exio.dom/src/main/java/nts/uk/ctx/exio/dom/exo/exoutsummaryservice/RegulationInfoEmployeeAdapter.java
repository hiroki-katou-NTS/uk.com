package nts.uk.ctx.exio.dom.exo.exoutsummaryservice;

import java.util.List;

import javax.ejb.Stateless;

@Stateless
public interface RegulationInfoEmployeeAdapter {

	List<RegulationInfoEmployeeImport> findEmployees(RegulationInfoEmployeeQueryImport importQuery);
}
