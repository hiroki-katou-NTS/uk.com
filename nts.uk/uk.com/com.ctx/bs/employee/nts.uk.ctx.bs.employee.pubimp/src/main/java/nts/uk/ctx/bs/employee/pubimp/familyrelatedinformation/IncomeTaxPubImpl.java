package nts.uk.ctx.bs.employee.pubimp.familyrelatedinformation;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.familyrelatedinformation.incomtax.IncomeTaxExport;
import nts.uk.ctx.bs.employee.pub.familyrelatedinformation.incomtax.IncomeTaxPub;
import nts.uk.ctx.bs.employee.dom.familyrelatedinformation.incometax.IncomeTaxRepository;

@Stateless
public class IncomeTaxPubImpl implements IncomeTaxPub{

	@Inject
	private IncomeTaxRepository incomeTaxRepository;
	
	@Override
	public Optional<IncomeTaxExport> getIncomeTaxById(String incomeTaxId) {
		return incomeTaxRepository.getIncomeTaxById(incomeTaxId).map(x -> 
			new IncomeTaxExport(x.getIncomeTaxID(), x.getFamilyMemberId(), x.getSid(),
					x.getPeriod().start(), x.getPeriod().end(), x.isSupporter(), 
					x.getDisabilityType().value, x.getDeductionTargetType().value));
	}

}
