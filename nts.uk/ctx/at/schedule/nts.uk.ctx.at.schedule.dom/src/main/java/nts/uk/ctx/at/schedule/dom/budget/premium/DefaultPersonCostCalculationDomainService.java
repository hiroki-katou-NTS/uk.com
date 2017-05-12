package nts.uk.ctx.at.schedule.dom.budget.premium;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.primitive.Memo;

@Stateless
public class DefaultPersonCostCalculationDomainService implements PersonCostCalculationDomainService {
	
	@Inject
	private PersonCostCalculationRepository personCostCalculationRepository;
	
	@Override
	public PersonCostCalculation createFromJavaType(String companyID, Memo memo, UnitPrice unitPrice,
			GeneralDate startDate, List<PremiumSetting> premiumSettings) {
		String historyID = UUID.randomUUID().toString();
		return new PersonCostCalculation(companyID, historyID, memo, unitPrice, startDate, GeneralDate.fromString("9999/12/31", "yyyy/MM/dd"), 
				premiumSettings.stream()
				.map(x -> new PremiumSetting(
						companyID, 
						historyID, 
						x.getAttendanceID(), 
						x.getPremiumRate(), 
						x.getPremiumName(), 
						x.getInternalID(), 
						x.getUseAtr(), 
						x.getTimeItemIDs()))
				.collect(Collectors.toList()));
	}

	@Override
	public void insertPersonCostCalculation(PersonCostCalculation personCostCalculation) {
		Optional<PersonCostCalculation> currentPersonCostResult = this.personCostCalculationRepository.findItemByDate(personCostCalculation.getCompanyID(), personCostCalculation.getStartDate());
		if(currentPersonCostResult.isPresent()) throw new RuntimeException();
		Optional<PersonCostCalculation> beforePersonCostResult = this.personCostCalculationRepository.findItemBefore(personCostCalculation.getCompanyID(), personCostCalculation.getStartDate());
		Optional<PersonCostCalculation> afterPersonCostResult = this.personCostCalculationRepository.findItemAfter(personCostCalculation.getCompanyID(), personCostCalculation.getStartDate().addDays(-1));
		if(afterPersonCostResult.isPresent()) throw new RuntimeException();
		if(beforePersonCostResult.isPresent()){
			if(beforePersonCostResult.get().getStartDate().after(personCostCalculation.getStartDate())) throw new RuntimeException();
			this.personCostCalculationRepository.update(
					new PersonCostCalculation(
						beforePersonCostResult.get().getCompanyID(), 
						beforePersonCostResult.get().getHistoryID(),
						beforePersonCostResult.get().getMemo(), 
						beforePersonCostResult.get().getUnitprice(), 
						beforePersonCostResult.get().getStartDate(), 
						personCostCalculation.getStartDate().addDays(-1), 
						beforePersonCostResult.get().getPremiumSettings()));
		}
		this.personCostCalculationRepository.add(
				createFromJavaType(
						personCostCalculation.getCompanyID(), 
						personCostCalculation.getMemo(), 
						personCostCalculation.getUnitprice(), 
						personCostCalculation.getStartDate(), 
						personCostCalculation.getPremiumSettings()));
	}

	@Override
	public void updatePersonCostCalculation(PersonCostCalculation personCostCalculation) {
		Optional<PersonCostCalculation> currentPersonCostResult = this.personCostCalculationRepository.findItemByHistoryID(personCostCalculation.getCompanyID(), personCostCalculation.getHistoryID());
		if(!currentPersonCostResult.isPresent()) throw new RuntimeException();
		Optional<PersonCostCalculation> beforePersonCostResult = this.personCostCalculationRepository.findItemBefore(personCostCalculation.getCompanyID(), currentPersonCostResult.get().getStartDate());
		Optional<PersonCostCalculation> afterPersonCostResult = this.personCostCalculationRepository.findItemAfter(personCostCalculation.getCompanyID(), currentPersonCostResult.get().getStartDate());
		if(afterPersonCostResult.isPresent()){
			if(personCostCalculation.getEndDate().after(afterPersonCostResult.get().getStartDate())) throw new RuntimeException();
		}
		if(beforePersonCostResult.isPresent()){
			if(beforePersonCostResult.get().getStartDate().after(personCostCalculation.getStartDate())) throw new RuntimeException();
			this.personCostCalculationRepository.update(
					new PersonCostCalculation(
						beforePersonCostResult.get().getCompanyID(), 
						beforePersonCostResult.get().getHistoryID(),
						beforePersonCostResult.get().getMemo(), 
						beforePersonCostResult.get().getUnitprice(), 
						beforePersonCostResult.get().getStartDate(), 
						personCostCalculation.getStartDate().addDays(-1), 
						beforePersonCostResult.get().getPremiumSettings()));
		}
		this.personCostCalculationRepository.update(personCostCalculation);
	}

	@Override
	public void deletePersonCostCalculation(PersonCostCalculation personCostCalculation) {
		Optional<PersonCostCalculation> currentPersonCostResult = this.personCostCalculationRepository.findItemByHistoryID(personCostCalculation.getCompanyID(), personCostCalculation.getHistoryID());
		if(!currentPersonCostResult.isPresent()) throw new RuntimeException();
		Optional<PersonCostCalculation> beforePersonCostResult = this.personCostCalculationRepository.findItemBefore(personCostCalculation.getCompanyID(), currentPersonCostResult.get().getStartDate());
		Optional<PersonCostCalculation> afterPersonCostResult = this.personCostCalculationRepository.findItemAfter(personCostCalculation.getCompanyID(), currentPersonCostResult.get().getStartDate());
		if(afterPersonCostResult.isPresent()) throw new RuntimeException();
		if(!beforePersonCostResult.isPresent()){
			throw new RuntimeException();
		}
		this.personCostCalculationRepository.update(
			new PersonCostCalculation(
				beforePersonCostResult.get().getCompanyID(), 
				beforePersonCostResult.get().getHistoryID(),
				beforePersonCostResult.get().getMemo(), 
				beforePersonCostResult.get().getUnitprice(), 
				beforePersonCostResult.get().getStartDate(), 
				GeneralDate.fromString("9999/12/31", "yyyy/MM/dd"), 
				beforePersonCostResult.get().getPremiumSettings()));
		this.personCostCalculationRepository.delete(personCostCalculation);
		
		
	}
}
