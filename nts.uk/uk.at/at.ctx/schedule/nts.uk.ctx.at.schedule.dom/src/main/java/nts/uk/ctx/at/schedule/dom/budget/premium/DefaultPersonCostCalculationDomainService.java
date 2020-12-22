package nts.uk.ctx.at.schedule.dom.budget.premium;

import java.util.*;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.primitive.Memo;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class DefaultPersonCostCalculationDomainService implements PersonCostCalculationDomainService {
	
	@Inject
	private PersonCostCalculationRepository personCostCalculationRepository;
	
	@Override
	public PersonCostCalculation createPersonCostCalculationFromJavaType(String companyID, GeneralDate startDate, 
			UnitPrice unitPrice, Memo memo, List<PremiumSetting> premiumSettings) {
		String historyID = UUID.randomUUID().toString();
		return new PersonCostCalculation(companyID, historyID, startDate,
				GeneralDate.fromString("9999/12/31", "yyyy/MM/dd"), unitPrice, memo,
				premiumSettings.stream()
				.map(x -> new PremiumSetting(
						companyID, 
						historyID,  
						x.getDisplayNumber(),
						x.getRate(), 
						x.getName(),
						x.getUseAtr(), 
						x.getAttendanceItems()))
				.collect(Collectors.toList()));
	}

	@Override
	public void insertPersonCostCalculation(PersonCostCalculation personCostCalculation) {
		Optional<PersonCostCalculation> currentPersonCostResult = this.personCostCalculationRepository.findItemByDate(personCostCalculation.getCompanyID(), personCostCalculation.getStartDate());
		if(currentPersonCostResult.isPresent()) throw new BusinessException("Msg_15");
		Optional<PersonCostCalculation> beforePersonCostResult = this.personCostCalculationRepository.findItemBefore(personCostCalculation.getCompanyID(), personCostCalculation.getStartDate());
		Optional<PersonCostCalculation> afterPersonCostResult = this.personCostCalculationRepository.findItemAfter(personCostCalculation.getCompanyID(), personCostCalculation.getStartDate().addDays(-1));
		if(afterPersonCostResult.isPresent()) throw new BusinessException("Msg_65");
		if(beforePersonCostResult.isPresent()){
			if(beforePersonCostResult.get().getStartDate().after(personCostCalculation.getStartDate())) throw new BusinessException("Msg_65");
			this.personCostCalculationRepository.update(
					new PersonCostCalculation(
						beforePersonCostResult.get().getCompanyID(), 
						beforePersonCostResult.get().getHistoryID(),
						beforePersonCostResult.get().getStartDate(), 
						personCostCalculation.getStartDate().addDays(-1), 
						beforePersonCostResult.get().getUnitPrice(),
						beforePersonCostResult.get().getMemo(),
						null));
		}
		this.personCostCalculationRepository.add(
				createPersonCostCalculationFromJavaType(
						personCostCalculation.getCompanyID(), 
						personCostCalculation.getStartDate(),
						personCostCalculation.getUnitPrice(), 
						personCostCalculation.getMemo(),
						personCostCalculation.getPremiumSettings()));
	}

	@Override
	public void updatePersonCostCalculation(PersonCostCalculation personCostCalculation) {
		Optional<PersonCostCalculation> currentPersonCostResult = this.personCostCalculationRepository.findItemByHistoryID(personCostCalculation.getCompanyID(), personCostCalculation.getHistoryID());
		if(!currentPersonCostResult.isPresent()) throw new RuntimeException();
		Optional<PersonCostCalculation> beforePersonCostResult = this.personCostCalculationRepository.findItemBefore(personCostCalculation.getCompanyID(), currentPersonCostResult.get().getStartDate());
		Optional<PersonCostCalculation> afterPersonCostResult = this.personCostCalculationRepository.findItemAfter(personCostCalculation.getCompanyID(), currentPersonCostResult.get().getStartDate());
		if(afterPersonCostResult.isPresent()){
			if(personCostCalculation.getEndDate().after(afterPersonCostResult.get().getStartDate())) throw new RuntimeException("end date after start date");
		}
		if(beforePersonCostResult.isPresent()){
			if(beforePersonCostResult.get().getStartDate().after(personCostCalculation.getStartDate())) throw new BusinessException("Msg_66");
			this.personCostCalculationRepository.update(
					new PersonCostCalculation(
						beforePersonCostResult.get().getCompanyID(), 
						beforePersonCostResult.get().getHistoryID(),
						beforePersonCostResult.get().getStartDate(), 
						personCostCalculation.getStartDate().addDays(-1), 
						beforePersonCostResult.get().getUnitPrice(), 
						beforePersonCostResult.get().getMemo(),
						null));
		}
		this.personCostCalculationRepository.update(personCostCalculation);
		
	}

	@Override
	public void deletePersonCostCalculation(PersonCostCalculation personCostCalculation) {
		Optional<PersonCostCalculation> currentPersonCostResult = this.personCostCalculationRepository.findItemByHistoryID(personCostCalculation.getCompanyID(), personCostCalculation.getHistoryID());
		if(!currentPersonCostResult.isPresent()) throw new RuntimeException("itme not exist");
		Optional<PersonCostCalculation> beforePersonCostResult = this.personCostCalculationRepository.findItemBefore(personCostCalculation.getCompanyID(), currentPersonCostResult.get().getStartDate());
		Optional<PersonCostCalculation> afterPersonCostResult = this.personCostCalculationRepository.findItemAfter(personCostCalculation.getCompanyID(), currentPersonCostResult.get().getStartDate());
		if(afterPersonCostResult.isPresent()) throw new RuntimeException("not last item");
		if(!beforePersonCostResult.isPresent()){
			throw new BusinessException("Msg_128");
		}
		this.personCostCalculationRepository.update(
			new PersonCostCalculation(
				beforePersonCostResult.get().getCompanyID(), 
				beforePersonCostResult.get().getHistoryID(),
				beforePersonCostResult.get().getStartDate(), 
				GeneralDate.fromString("9999/12/31", "yyyy/MM/dd"), 
				beforePersonCostResult.get().getUnitPrice(), 
				beforePersonCostResult.get().getMemo(), 
				null));
		this.personCostCalculationRepository.delete(personCostCalculation);
	}

	@Override
	public void updateHistPersonCalculation(PersonCostCalculation domain, String histotyId, DatePeriod period) {
		val companyId = AppContexts.user().companyId();
		// List all
		val listPersonCostCalculation = this.personCostCalculationRepository.getHistPersonCostCalculation(companyId);
		// Create list empty.
		HistPersonCostCalculation listHist = new HistPersonCostCalculation(companyId, new ArrayList<>());
		if (listPersonCostCalculation.isPresent()) {
			listHist = listPersonCostCalculation.get();
		}
		// Get item update
		Optional<DateHistoryItem> optionalHisItem = listHist.items().stream()
				.filter(x -> x.identifier().equals(histotyId)).findFirst();
		if (!optionalHisItem.isPresent()) {

			throw new BusinessException("invalid !");
		}
		//Update item
		listHist.changeSpan(optionalHisItem.get(), period);
		// get item before
		val itemBefore = listHist.immediatelyBefore(optionalHisItem.get());
		val listUpdate = new ArrayList<DateHistoryItem>();
		listUpdate.add(optionalHisItem.get());
		itemBefore.ifPresent(listUpdate::add);

		personCostCalculationRepository.updateHistPersonCl(new HistPersonCostCalculation(companyId, listUpdate));
	}

	@Override
	public void registerLaborCalculationSetting(PersonCostCalculation domain, GeneralDate date) {
		// Get all list history
		val companyId = AppContexts.user().companyId();
		val listPersonCostCalculation = this.personCostCalculationRepository.getHistPersonCostCalculation(companyId);
		// List history
		HistPersonCostCalculation listHist = new HistPersonCostCalculation(companyId, new ArrayList<>());
		if (listPersonCostCalculation.isPresent()) {
			listHist = listPersonCostCalculation.get();
		}
		DatePeriod datePeriod = new DatePeriod(date, GeneralDate.max());
		// Item need to add
		DateHistoryItem itemToBeAdded = DateHistoryItem.createNewHistory(datePeriod);
		// Add into old list
		listHist.add(itemToBeAdded);
		//  Item to be update.
		val itemToBeUpdated = listHist.immediatelyBefore(itemToBeAdded);

		// Update pre hist
		itemToBeUpdated.ifPresent(dateHistoryItem ->
				personCostCalculationRepository.updateHistPersonCl(HistPersonCostCalculation.toDomain(companyId, Collections.singletonList(dateHistoryItem))));
		// Add new item
		personCostCalculationRepository.createHistPersonCl(HistPersonCostCalculation.toDomain(companyId, Collections.singletonList(itemToBeAdded)));

	}
}
