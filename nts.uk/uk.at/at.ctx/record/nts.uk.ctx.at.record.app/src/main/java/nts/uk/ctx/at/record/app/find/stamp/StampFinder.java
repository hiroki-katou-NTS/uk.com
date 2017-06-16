package nts.uk.ctx.at.record.app.find.stamp;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stamp.StampRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class StampFinder {
	@Inject
	private StampRepository stampRepo;
	
	/**
	 * 
	 * Get list Stamp Info by StartDate, endDate, list Card Number
	 * 
	 * */
	public List<StampDto> findByEmployeeCode(List<String> lstCardNumber, String startDate, String endDate) {
		String companyId = AppContexts.user().companyId();
		List<StampDto> lstStamp = stampRepo.findByEmployeeCode(companyId, lstCardNumber, startDate, endDate)
				.stream()
				.map(item -> StampDto.fromDomain(item))
				.collect(Collectors.toList());
		return lstStamp;
	}
}
