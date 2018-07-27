package nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonthlyRecordWorkType;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.MonthlyRecordWorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class MonthlyRecordWorkTypeFinder {

	@Inject
	private MonthlyRecordWorkTypeRepository monthlyRecordWorkTypeRepo;
	
	public List<MonthlyRecordWorkTypeDto> getListMonthlyRecordWorkType(){
		String companyID = AppContexts.user().companyId();
		List<MonthlyRecordWorkTypeDto> data = this.monthlyRecordWorkTypeRepo.getAllMonthlyRecordWorkType(companyID)
				.stream().map(c->MonthlyRecordWorkTypeDto.fromDomain(c)).collect(Collectors.toList());
		if(data.isEmpty())
			return Collections.emptyList();
		return data;
	}
	
	public MonthlyRecordWorkTypeDto getMonthlyRecordWorkTypeByCode(String businessTypeCode ) {
		String companyID = AppContexts.user().companyId();
		Optional<MonthlyRecordWorkType> data  = monthlyRecordWorkTypeRepo.getMonthlyRecordWorkTypeByCode(companyID, businessTypeCode);
		if(data.isPresent())
			return MonthlyRecordWorkTypeDto.fromDomain(data.get());
		return null;
				
	}
	
	public List<MonthlyRecordWorkTypeDto> getMonthlyRecordWorkTypeByListCode(String companyID, List<String> businessTypeCodes){
		return monthlyRecordWorkTypeRepo.getMonthlyRecordWorkTypeByListCode(companyID, businessTypeCodes)
				.stream()
				.map(item -> MonthlyRecordWorkTypeDto.fromDomain(item))
				.collect(Collectors.toList());
	}
	
}
