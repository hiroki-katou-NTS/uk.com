package nts.uk.ctx.at.record.app.find.divergence.time.setting;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTime;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DivergenceTimeSettingFinder {
	
	@Inject
	private DivergenceTimeRepository divTimeRepo;
	
	public List<DivergenceTimeDto> getAllDivTime(){
		String companyId = AppContexts.user().companyId();
		List<DivergenceTime> listDivTime = this.divTimeRepo.getAllDivTime(companyId);
		if (listDivTime.isEmpty()){
			return Collections.emptyList();
			}
		
		return listDivTime.stream().map(e -> {
			DivergenceTimeDto dto = new DivergenceTimeDto();
			e.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
	

}
