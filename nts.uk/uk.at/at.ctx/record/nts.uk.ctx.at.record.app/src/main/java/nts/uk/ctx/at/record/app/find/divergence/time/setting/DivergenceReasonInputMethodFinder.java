package nts.uk.ctx.at.record.app.find.divergence.time.setting;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonInputMethod;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonInputMethodRepository;
import nts.uk.shr.com.context.AppContexts;

public class DivergenceReasonInputMethodFinder {
	@Inject
	private DivergenceReasonInputMethodRepository divReasonRepo;
	
	List<DivergenceReasonInputMethodDto> getAllDivTime(){
		String companyId = AppContexts.user().companyId();
		List<DivergenceReasonInputMethod> listDivTime = this.divReasonRepo.getAllDivTime(companyId);
		if (listDivTime.isEmpty()){
			return Collections.emptyList();
			}
		
		return listDivTime.stream().map(e -> {
			DivergenceReasonInputMethodDto dto = new DivergenceReasonInputMethodDto();
			e.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
	

}
