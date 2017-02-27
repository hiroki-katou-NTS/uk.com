package nts.uk.ctx.basic.app.find.system.bank.branch.era;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.system.era.EraRepository;

@Stateless
public class EraFinder {
	@Inject
	private EraRepository eraRepository;
	public List<EraDto> getEras(){
		List<EraDto> result = this.eraRepository.getEras().stream().map(x-> {return EraDto.fromDomain(x);})
				.collect(Collectors.toList());
	
		return result;
	}
//	public Optional<EraDto> getEraDetail(GeneralDate startDate){
//		return this.eraRepository.getEraDetail(startDate).map(era -> EraDto.fromDomain(era));
//	}
	public Optional<EraDto> getEraDetail(GeneralDate startDate){
		return this.eraRepository.getEraDetail(startDate).map(era -> EraDto.fromDomain(era));
	}
}
