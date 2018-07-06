package nts.uk.ctx.at.record.pubimp.workrecord.erroralarm.condition.monthlycheckcondition;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.FixedExtraItemMon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.FixedExtraItemMonRepository;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.FixedExtraItemMonPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.FixedExtraItemMonPubEx;
@Stateless
public class FixedExtraItemMonPubImpl implements FixedExtraItemMonPub {

	@Inject
	private FixedExtraItemMonRepository repo;
	
	@Override
	public List<FixedExtraItemMonPubEx> getAllFixedExtraItemMon() {
		List<FixedExtraItemMonPubEx> data = repo.getAllFixedExtraItemMon().stream()
				.map(c->convertToExport(c)).collect(Collectors.toList());
		if(data.isEmpty())
			return Collections.emptyList();
		return data;
	}
	

	private FixedExtraItemMonPubEx convertToExport(FixedExtraItemMon domain) {
		return new FixedExtraItemMonPubEx(
				domain.getFixedExtraItemMonNo().value,
				domain.getFixedExtraItemMonName().v(),
				domain.getMessage().v()
				);
		
	}
}
