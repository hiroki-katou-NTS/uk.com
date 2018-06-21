package nts.uk.ctx.at.record.pubimp.workrecord.erroralarm.condition.monthlycheckcondition;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecordName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.FixedExtraMon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.FixedExtraMonRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.SysFixedMonPerEral;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.FixedExtraMonPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition.FixedExtraMonPubEx;

@Stateless
public class FixedExtraMonPubImpl implements FixedExtraMonPub{

	@Inject
	private FixedExtraMonRepository repo;
	
	@Override
	public List<FixedExtraMonPubEx> getByEralCheckID(String monAlarmCheckID) {
		List<FixedExtraMonPubEx> data = repo.getByEralCheckID(monAlarmCheckID).stream()
				.map(c->convertToExport(c)).collect(Collectors.toList());
		if(data.isEmpty())
			return Collections.emptyList();
		return data;
	}

	@Override
	public void addFixedExtraMon(FixedExtraMonPubEx fixedExtraMon) {
		repo.addFixedExtraMon(convertToDomain(fixedExtraMon));
		
	}

	@Override
	public void updateFixedExtraMon(FixedExtraMonPubEx fixedExtraMon) {
		repo.updateFixedExtraMon(convertToDomain(fixedExtraMon));
		
	}

	@Override
	public void deleteFixedExtraMon(String monAlarmCheckID) {
		repo.deleteFixedExtraMon(monAlarmCheckID);
		
	}
	
	
	public FixedExtraMonPubEx convertToExport(FixedExtraMon domain) {
		return new FixedExtraMonPubEx(
				domain.getMonAlarmCheckID(),
				domain.getFixedExtraItemMonNo().value,
				domain.isUseAtr(),
				!domain.getMessage().isPresent()?null:domain.getMessage().get().v()
				);
		
	}
	
	public FixedExtraMon convertToDomain(FixedExtraMonPubEx dto) {
		return new FixedExtraMon(
				dto.getMonAlarmCheckID(),
				EnumAdaptor.valueOf(dto.getFixedExtraItemMonNo(), SysFixedMonPerEral.class),
				dto.isUseAtr(),
				new FixedConditionWorkRecordName(dto.getMessage())
				);
		
	}

}
