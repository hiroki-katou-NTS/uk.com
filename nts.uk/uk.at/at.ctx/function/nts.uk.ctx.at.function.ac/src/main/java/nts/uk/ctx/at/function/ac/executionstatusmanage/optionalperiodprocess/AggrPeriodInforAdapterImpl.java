package nts.uk.ctx.at.function.ac.executionstatusmanage.optionalperiodprocess;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInforAdapter;
import nts.uk.ctx.at.function.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInforImported;
import nts.uk.ctx.at.record.pub.executionstatusmanage.optionalperiodprocess.AggrPeriodInforPub;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AggrPeriodInforAdapterImpl implements AggrPeriodInforAdapter {

	@Inject
	private AggrPeriodInforPub pub;
	
	@Override
	public List<AggrPeriodInforImported> findAggrPeriodInfor(String execId) {
		return this.pub.findAggrPeriodInfor(execId).stream()
				.map(domain -> AggrPeriodInforImported.builder()
						.memberId(domain.getMemberId())
						.periodArrgLogId(domain.getPeriodArrgLogId())
						.resourceId(domain.getResourceId())
						.processDay(domain.getProcessDay())
						.errorMess(domain.getErrorMess().v())
						.build())
				.collect(Collectors.toList());
	}

}
