package nts.uk.ctx.at.record.pubimp.divergencetime;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.record.pub.divergencetime.DivergenceTimePub;
import nts.uk.ctx.at.record.pub.divergencetime.DivergenceTimePubDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DivergenceTimePubImpl implements DivergenceTimePub{
	
	@Inject
	private DivergenceTimeRepository divergenceTimeRepository;

	@Override
	public List<DivergenceTimePubDto> getDivergenceTimeName(String companyId, List<Integer> divTimeIds) {
		List<DivergenceTimePubDto> divergenceTimes = this.divergenceTimeRepository.getUsedDivTimeListByNoV3(companyId, divTimeIds).stream().map(f -> {
			return new DivergenceTimePubDto(f.getCompanyId(), f.getDivergenceTimeNo(), f.getDivTimeName().v());
		}).collect(Collectors.toList());
		return divergenceTimes;
	}

	@Override
	public List<DivergenceTimePubDto> findByCompanyAndUseDistination(String comapanyId, int useDistination) {
		return this.divergenceTimeRepository.findByCompanyAndUseDistination(comapanyId, useDistination).stream()
				.map(f -> {
					return new DivergenceTimePubDto(f.getCompanyId(), f.getDivergenceTimeNo(), f.getDivTimeName().v());
				}).collect(Collectors.toList());
	}
}
