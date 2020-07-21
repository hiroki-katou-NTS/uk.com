package nts.uk.ctx.at.schedule.ac.classification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.classification.SClsHistImported;
import nts.uk.ctx.at.schedule.dom.adapter.classification.SyClassificationAdapter;
import nts.uk.ctx.at.schedule.dom.employeeinfo.employeesort.EmpClassifiImport;
import nts.uk.ctx.bs.employee.pub.classification.EmpClassifiExport;
import nts.uk.ctx.bs.employee.pub.classification.SyClassificationPub;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class SyClassificationApdaterImpl implements SyClassificationAdapter {

	@Inject
	private SyClassificationPub pub;

	@Override
	public Optional<SClsHistImported> findSClsHistBySid(String companyId, String employeeId, GeneralDate baseDate) {
		return this.pub.findSClsHistBySid(companyId, employeeId, baseDate).map(x -> new SClsHistImported(x.getPeriod(),
				x.getEmployeeId(), x.getClassificationCode(), x.getClassificationName()));
	}

	@Override
	public Map<String, String> getClassificationMapCodeName(String companyId, List<String> clsCds) {
		return this.pub.getClassificationMapCodeName(companyId, clsCds);
	}

	@Override
	public List<EmpClassifiImport> getByListSIDAndBasedate(GeneralDate baseDate, List<String> listempID) {
		List<EmpClassifiExport> listExport =  pub.getByListSIDAndBasedate(baseDate, listempID);
		if (listExport.isEmpty()) {
			return new ArrayList<>();
		}
		return listExport.stream().map(mapper -> {
			return new EmpClassifiImport(mapper.getEmpID(), mapper.getClassificationCode().toString());
		}).collect(Collectors.toList());
	}
}
