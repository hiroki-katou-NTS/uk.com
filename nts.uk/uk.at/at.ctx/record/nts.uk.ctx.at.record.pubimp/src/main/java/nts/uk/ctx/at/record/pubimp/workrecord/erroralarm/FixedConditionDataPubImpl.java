package nts.uk.ctx.at.record.pubimp.workrecord.erroralarm;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionData;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionDataRepository;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.FixedConditionDataPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.FixedConditionDataPubExport;

@Stateless
public class FixedConditionDataPubImpl  implements FixedConditionDataPub{
	
	@Inject
	private FixedConditionDataRepository repo;

	@Override
	public List<FixedConditionDataPubExport> getAllFixedConditionDataPub() {
		List<FixedConditionDataPubExport> data = this.repo.getAllFixedConditionData()
				.stream().map(c->convertToExport(c)).collect(Collectors.toList());
		if(data.isEmpty())
			return Collections.emptyList();
		return data;
	}
	
	private FixedConditionDataPubExport convertToExport(FixedConditionData domain) {
		return new FixedConditionDataPubExport(
				domain.getFixConWorkRecordNo().value,
				domain.getFixConWorkRecordName().v(),
				domain.getMessage().v(),
				domain.getEralarmAtr()!=null?domain.getEralarmAtr().value:2
				);
		
	}

}
