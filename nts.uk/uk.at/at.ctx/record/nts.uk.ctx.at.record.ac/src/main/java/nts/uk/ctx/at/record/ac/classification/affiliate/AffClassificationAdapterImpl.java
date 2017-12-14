package nts.uk.ctx.at.record.ac.classification.affiliate;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationAdapter;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationSidImport;
import nts.uk.ctx.bs.employee.pub.classification.SClsHistExport;
import nts.uk.ctx.bs.employee.pub.classification.SyClassificationPub;

@Stateless
public class AffClassificationAdapterImpl implements AffClassificationAdapter {

	@Inject
	private SyClassificationPub syClassificationPub;

	@Override
	public Optional<AffClassificationSidImport> findByEmployeeId(String companyId, String employeeId,
			GeneralDate baseDate) {
		Optional<SClsHistExport> hisExport = this.syClassificationPub.findSClsHistBySid(companyId, employeeId,
				baseDate);
		if(!hisExport.isPresent()){
			return Optional.empty();
		}
		AffClassificationSidImport affClassificationSidImport = new AffClassificationSidImport(
				hisExport.get().getEmployeeId(), hisExport.get().getClassificationCode(), hisExport.get().getPeriod());
		return Optional.of(affClassificationSidImport);
	}

}
