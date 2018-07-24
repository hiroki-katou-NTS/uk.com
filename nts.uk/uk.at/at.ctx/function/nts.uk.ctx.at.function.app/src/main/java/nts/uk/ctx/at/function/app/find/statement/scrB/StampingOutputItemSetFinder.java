package nts.uk.ctx.at.function.app.find.statement.scrB;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.statement.StampingOutputItemSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class StampingOutputItemSetFinder {
	
	@Inject
	private StampingOutputItemSetRepository stampingOutputItemSetRepository; 
	
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<OutputItemSetDto> findAll(){
		String companyID = AppContexts.user().companyId();
		return stampingOutputItemSetRepository.getByCid(companyID).stream().map(domain -> {
			OutputItemSetDto outputItemSetDto = new OutputItemSetDto();
			outputItemSetDto.setStampOutputSetCode(domain.getStampOutputSetCode().v());
			outputItemSetDto.setStampOutputSetName(domain.getStampOutputSetName().v());
			outputItemSetDto.setOutputEmbossMethod(domain.isOutputEmbossMethod());
			outputItemSetDto.setOutputNightTime(domain.isOutputNightTime());
			outputItemSetDto.setOutputOT(domain.isOutputOT());
			outputItemSetDto.setOutputPosInfor(domain.isOutputPosInfor());
			outputItemSetDto.setOutputSetLocation(domain.isOutputSetLocation());
			outputItemSetDto.setOutputSupportCard(domain.isOutputSupportCard());
			outputItemSetDto.setOutputWorkHours(domain.isOutputWorkHours());
			return outputItemSetDto;
		}).collect(Collectors.toList());
		
	}
	
	

}
