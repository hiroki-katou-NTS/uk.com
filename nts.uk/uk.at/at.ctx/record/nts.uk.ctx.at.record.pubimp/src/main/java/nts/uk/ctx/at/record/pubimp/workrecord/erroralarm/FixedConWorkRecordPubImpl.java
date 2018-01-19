package nts.uk.ctx.at.record.pubimp.workrecord.erroralarm;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecordRepository;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.FixedConWorkRecordPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.FixedConWorkRecordPubExport;

@Stateless
public class FixedConWorkRecordPubImpl implements FixedConWorkRecordPub {

	@Inject
	private FixedConditionWorkRecordRepository repo;
	
	@Override
	public List<FixedConWorkRecordPubExport> getAllFixedConWorkRecordByListID(List<String> listErrorAlarmID) {
		List<FixedConWorkRecordPubExport> data = repo.getAllFixedConWorkRecordByListID(listErrorAlarmID)
				.stream().map(c->convertToExport(c)).collect(Collectors.toList());
		if(data.isEmpty())
			return Collections.emptyList();
		return data;
	}

	@Override
	public FixedConWorkRecordPubExport getFixedConWRByCode(String errorAlarmID) {
		Optional<FixedConWorkRecordPubExport> data = repo.getFixedConWRByCode(errorAlarmID).map(c->convertToExport(c));
		if(data.isPresent())
			return data.get();
		return null;
	}
	
	public FixedConWorkRecordPubExport convertToExport(FixedConditionWorkRecord domain) {
		return new FixedConWorkRecordPubExport(
				domain.getErrorAlarmID(),
				domain.getFixConWorkRecordNo().value,
				domain.getMessage().v(),
				domain.isUseAtr()
				);
		
	}

}
