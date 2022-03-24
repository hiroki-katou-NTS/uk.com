package nts.uk.ctx.exio.app.find.exo.exoutsummarysetting;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;
import nts.uk.ctx.exio.dom.exo.exoutsummaryservice.ExOutSummarySetting;
import nts.uk.ctx.exio.dom.exo.exoutsummaryservice.ExOutSummarySettingService;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;

@Stateless
public class ExOutSummarySettingFinder {
	@Inject
	private ExOutSummarySettingService service;
	
	@Inject
	private StdOutputCondSetRepository stdOutputCondSetRepository;
	
	@Inject
	private StandardOutputItemRepository standardOutputItemRepository;
	
	public ExOutSummarySettingDto getExOutSummarySetting(String conditionSetCd) {
		ExOutSummarySetting exOutSummarySetting = service.getExOutSummarySetting(conditionSetCd);
		return new ExOutSummarySettingDto(exOutSummarySetting);
	}
	
	public SmileGetSettingDto getExOutSetting(String cid, String conditionSetCd){
		Optional<StdOutputCondSet> condSet = stdOutputCondSetRepository.getStdOutputCondSetById(cid, conditionSetCd);
		
		if(!condSet.isPresent()) 
			return new SmileGetSettingDto(false, null);
		
		List<StandardOutputItem> item = standardOutputItemRepository.getStdOutItemByCidAndSetCd(cid, conditionSetCd);
		return new SmileGetSettingDto(true, new OutConditionSetDto(condSet.get().getConditionSetName().v(), condSet.get().getConditionOutputName().value, 
																condSet.get().getItemOutputName().value, condSet.get().getDelimiter().value, condSet.get().getStringFormat().value,
																item.stream().map(x -> x.getOutputItemCode().v()).collect(Collectors.toList())
															));
	}
}
