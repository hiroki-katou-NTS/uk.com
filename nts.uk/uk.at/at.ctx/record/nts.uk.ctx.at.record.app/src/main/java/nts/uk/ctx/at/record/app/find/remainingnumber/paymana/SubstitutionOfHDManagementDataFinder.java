package nts.uk.ctx.at.record.app.find.remainingnumber.paymana;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SubstitutionOfHDManaDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;

@Stateless
public class SubstitutionOfHDManagementDataFinder {

	@Inject
	private SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepository;
	
	public List<SubstitutionOfHDManagementData> getBySidDatePeriod(String sid, GeneralDate startDate, GeneralDate endDate){
		return null;
	}
}
