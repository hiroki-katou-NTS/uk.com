package nts.uk.screen.at.app.query.kmp.kmp001.a;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;

@Stateless
public class GetSelectedEmployeeAndCardNumber {

	@Inject
	private IPersonInfoPub IPersonInfoPub;
	
	@Inject StampCardRepository stampCardRepo;
}
