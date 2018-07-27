package nts.uk.ctx.at.schedule.ac.classification;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.classification.SClsHistImported;
import nts.uk.ctx.at.schedule.dom.adapter.classification.SyClassificationAdapter;
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
}
