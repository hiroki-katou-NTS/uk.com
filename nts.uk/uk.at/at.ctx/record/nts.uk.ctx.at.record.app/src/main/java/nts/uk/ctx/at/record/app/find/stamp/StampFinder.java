package nts.uk.ctx.at.record.app.find.stamp;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.stamp.stampcard.StampCardDto;
import nts.uk.ctx.at.record.app.find.stamp.stampcard.StampCardFinder;
import nts.uk.ctx.at.record.dom.stamp.StampRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class StampFinder {
	@Inject
	private StampRepository stampRepo;
	@Inject
	private StampCardFinder cardFinder;
	/**
	 * 
	 * Get list Stamp Info by StartDate, endDate, list Card Number
	 * 
	 * */
	public List<StampDto> getListStampDetail(StampDetailParamDto stampDeParaDto){
		String companyId = AppContexts.user().companyId();
		//打刻カード
		List<String> cardInfors = cardFinder.findByLstSID(stampDeParaDto.sIDs);
		//get 打刻情報
		
		List<StampDto> lstStamp = stampRepo.findByEmployeeID(companyId, cardInfors, stampDeParaDto.startDate, stampDeParaDto.endDate)
				.stream()
				.map(item -> StampDto.fromDomain(item))
				.collect(Collectors.toList());
		return lstStamp;
	}
}
