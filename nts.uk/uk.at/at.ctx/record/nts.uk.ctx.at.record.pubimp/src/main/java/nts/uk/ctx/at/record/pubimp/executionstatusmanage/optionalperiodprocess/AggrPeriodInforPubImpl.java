package nts.uk.ctx.at.record.pubimp.executionstatusmanage.optionalperiodprocess;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInfor;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInforRepository;
import nts.uk.ctx.at.record.pub.executionstatusmanage.optionalperiodprocess.AggrPeriodInforPub;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AggrPeriodInforPubImpl implements AggrPeriodInforPub {
	
	@Inject
	private AggrPeriodInforRepository repo;

	@Override
	public List<AggrPeriodInfor> findAggrPeriodInfor(String execId) {
		return this.repo.findAll(execId);
	} 

}
