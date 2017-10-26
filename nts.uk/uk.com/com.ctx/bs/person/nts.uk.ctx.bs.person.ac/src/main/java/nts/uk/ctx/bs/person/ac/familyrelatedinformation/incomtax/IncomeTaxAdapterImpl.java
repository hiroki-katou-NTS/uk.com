package nts.uk.ctx.bs.person.ac.familyrelatedinformation.incomtax;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.familyrelatedinformation.incomtax.IncomeTaxPub;
import nts.uk.ctx.bs.person.dom.person.adapter.familyrelatedinformation.incometax.IncomeTaxAdapter;
import nts.uk.ctx.bs.person.dom.person.adapter.familyrelatedinformation.incometax.IncomeTaxImport;

@Stateless
public class IncomeTaxAdapterImpl implements IncomeTaxAdapter{

	@Inject 
	private IncomeTaxPub incomeTaxPub;
	@Override
	public Optional<IncomeTaxImport> getInComeTaxById(String incomeTaxId) {
		return incomeTaxPub.getIncomeTaxById(incomeTaxId).map( x -> 
		new IncomeTaxImport(x.getIncomeTaxID(), x.getFamilyMemberId(), x.getSid(),
				x.getStartDate(), x.getEndDate(), x.isSupporter(), 
				x.getDisabilityType(), x.getDeductionTargetType()));
	}

}
