package nts.uk.ctx.at.shared.pubimp.monthlyattendanceitem.aggregate;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.monthlyattditem.aggregate.MonthlyAttItemCanAggregateRepository;
import nts.uk.ctx.at.shared.pub.monthlyattendanceitem.aggregate.MonthlyAttItemCanAggregatePub;

/**
 * The Class MonthlyAttItemCanAggregatePubImp.
 * @author LienPTK
 *
 */
@Stateless
public class MonthlyAttItemCanAggregatePubImp implements MonthlyAttItemCanAggregatePub {
	
	/** The monthly att item can aggregate repo. */
	@Inject
	private MonthlyAttItemCanAggregateRepository monthlyAttItemCanAggregateRepo;

	@Override
	public List<Integer> getMonthlyAtdCanBeAggregate(String cid) {
		return this.monthlyAttItemCanAggregateRepo.getMonthlyAtdItemCanAggregate(cid).stream()
				.map(t -> t.v().intValue())
				.collect(Collectors.toList());
	}

}
