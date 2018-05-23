package nts.uk.ctx.at.function.ac.workrecord.erroralarm.condition.monthlycheckcondition;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.FixedExtraMonFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.FixedExtraMonFunImport;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.FixedExtraMonPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.FixedExtraMonPubEx;

@Stateless
public class FixedExtraMonAcFinder implements FixedExtraMonFunAdapter {
	
	@Inject
	private FixedExtraMonPub repo;
	
	@Override
	public List<FixedExtraMonFunImport> getByEralCheckID(String monAlarmCheckID) {
		List<FixedExtraMonFunImport> data = repo.getByEralCheckID(monAlarmCheckID).stream()
				.map(c->convertToExport(c)).collect(Collectors.toList());
		return data;
	}

	@Override
	public void addFixedExtraMon(FixedExtraMonFunImport fixedExtraMon) {
		FixedExtraMonPubEx export = convertToImport(fixedExtraMon);
		repo.addFixedExtraMon(export);
		
	}

	@Override
	public void updateFixedExtraMon(FixedExtraMonFunImport fixedExtraMon) {
		FixedExtraMonPubEx export = convertToImport(fixedExtraMon);
		repo.updateFixedExtraMon(export);
	}

	@Override
	public void deleteFixedExtraMon(String monAlarmCheckID) {
		repo.deleteFixedExtraMon(monAlarmCheckID);
		
	}
	
	private FixedExtraMonFunImport convertToExport(FixedExtraMonPubEx export) {
		return new FixedExtraMonFunImport(
				export.getMonAlarmCheckID(),
				export.getFixedExtraItemMonNo(),
				export.isUseAtr(),
				export.getMessage()
				);
	}
	
	private FixedExtraMonPubEx convertToImport(FixedExtraMonFunImport dto) {
		return new FixedExtraMonPubEx(
				dto.getMonAlarmCheckID(),
				dto.getFixedExtraItemMonNo(),
				dto.isUseAtr(),
				dto.getMessage()
				);
	}

}
