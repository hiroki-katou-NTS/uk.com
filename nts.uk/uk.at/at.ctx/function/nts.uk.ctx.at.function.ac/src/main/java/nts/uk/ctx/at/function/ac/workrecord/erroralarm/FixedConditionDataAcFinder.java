package nts.uk.ctx.at.function.ac.workrecord.erroralarm;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.FixedConditionDataAdapter;
import nts.uk.ctx.at.function.dom.adapter.FixedConditionDataAdapterDto;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.FixedConditionDataPub;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.FixedConditionDataPubExport;
@Stateless
public class FixedConditionDataAcFinder implements FixedConditionDataAdapter  {

	@Inject
	private FixedConditionDataPub repo;
	
	@Override
	public List<FixedConditionDataAdapterDto> getAllFixedConditionDataPub() {
		List<FixedConditionDataAdapterDto> data = this.repo.getAllFixedConditionDataPub()
				.stream().map(c->convertToImport(c)).collect(Collectors.toList());
		return data;
	}
	
	private FixedConditionDataAdapterDto convertToImport(FixedConditionDataPubExport export) {
		return new FixedConditionDataAdapterDto(
				export.getFixConWorkRecordNo(),
				export.getFixConWorkRecordName(),
				export.getMessage(),
				export.getEralarmAtr()
				);
	}

}
