package nts.uk.ctx.at.record.app.find.remainingnumber.annleagrtremnum;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.shr.pereg.app.find.PeregQuery;

@Stateless
public class AnnLeaGrantRemnNumFinder{
	
	@Inject
	private AnnLeaGrantRemDataRepository annLeaDataRepo;

	public AnnLeaGrantRemnNumDto getSingleData(PeregQuery query) {
		Optional<AnnualLeaveGrantRemainingData> annLeaDataOpt = annLeaDataRepo.get(query.getEmployeeId());
		if ( annLeaDataOpt.isPresent()) {
			return AnnLeaGrantRemnNumDto.createFromDomain(annLeaDataOpt.get());
		}
		return null;
	}

}
