package nts.uk.ctx.at.function.ac.workrecord.erroralarm.condition.monthlycheckcondition;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.FixedExtraItemMonFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.FixedExtraItemMonFunImport;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.FixedExtraItemMonPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.FixedExtraItemMonPubEx;

@Stateless
public class FixedExtraItemMonAcFinder implements FixedExtraItemMonFunAdapter {

	@Inject
	private FixedExtraItemMonPub repo ;
	
	@Override
	public List<FixedExtraItemMonFunImport> getAllFixedExtraItemMon() {
		List<FixedExtraItemMonFunImport> data = repo.getAllFixedExtraItemMon().stream()
				.map(c->convertToImport(c)).collect(Collectors.toList());
		return data;
	}	
	
	private FixedExtraItemMonFunImport convertToImport(FixedExtraItemMonPubEx export) {
		return new FixedExtraItemMonFunImport(
				export.getFixedExtraItemMonNo(),
				export.getFixedExtraItemMonName(),
				export.getMessage()
				);
	}

}
