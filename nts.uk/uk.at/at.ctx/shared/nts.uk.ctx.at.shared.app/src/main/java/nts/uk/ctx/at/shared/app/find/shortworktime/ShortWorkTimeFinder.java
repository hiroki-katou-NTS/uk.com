/**
 * 
 */
package nts.uk.ctx.at.shared.app.find.shortworktime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistoryRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistory;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistoryItem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregEmpInfoQuery;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregQueryByListEmp;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainDto;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

/**
 * @author danpv
 *
 */
@Stateless
public class ShortWorkTimeFinder implements PeregFinder<ShortWorkTimeDto> {

	@Inject
	private SWorkTimeHistoryRepository shortTimeHistoryRepo;

	@Inject
	private SWorkTimeHistItemRepository shortTimeHistoryItemRepo;

	@Override
	public String targetCategoryCode() {
		return "CS00019";
	}

	@Override
	public Class<ShortWorkTimeDto> dtoClass() {
		return ShortWorkTimeDto.class;
	}

	@Override
	public DataClassification dataType() {
		return DataClassification.EMPLOYEE;
	}

	@Override
	public ShortWorkTimeDto getSingleData(PeregQuery query) {
		String employeeId = query.getEmployeeId();
		Optional<ShortWorkTimeHistory> history;
		if (query.getInfoId() != null) {
			history = shortTimeHistoryRepo.findByKey(employeeId, query.getInfoId());
		} else {
			history = shortTimeHistoryRepo.findByBaseDate(employeeId, query.getStandardDate());
		}

		if (history.isPresent()) {
			DateHistoryItem dateHistoryItem = history.get().getHistoryItems().get(0);
			Optional<ShortWorkTimeHistoryItem> historyItem = shortTimeHistoryItemRepo.findByKey(employeeId,
					dateHistoryItem.identifier());
			if (historyItem.isPresent()) {
				return ShortWorkTimeDto.createShortWorkTimeDto(dateHistoryItem, historyItem.get());
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.shr.pereg.app.find.PeregFinder#getListData(nts.uk.shr.pereg.app.
	 * find.PeregQuery)
	 */
	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ComboBoxObject> getListFirstItems(PeregQuery query) {
		String employeeId = query.getEmployeeId();
		Optional<ShortWorkTimeHistory> history = shortTimeHistoryRepo.getBySidDesc(AppContexts.user().companyId(),
				query.getEmployeeId());
		if (history.isPresent()) {
			return history.get().getHistoryItems().stream()
					.filter(item -> shortTimeHistoryItemRepo.findByKey(employeeId, item.identifier()).isPresent())
					.map(x -> ComboBoxObject.toComboBoxObject(x.identifier(), x.start().toString(), 
							x.end().equals(GeneralDate.max()) 
							//&& query.getCtgType() == 3 
							? "" : x.end().toString()))							
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	@Override
	public List<GridPeregDomainDto> getAllData(PeregQueryByListEmp query) {
		String cid = AppContexts.user().companyId();
		List<GridPeregDomainDto> result = new ArrayList<>();
		// key - sid , value - pid getEmployeeId getPersonId 
		Map<String, String> mapSids = query.getEmpInfos().stream().collect(Collectors.toMap(PeregEmpInfoQuery:: getEmployeeId, PeregEmpInfoQuery:: getPersonId));
		List<DateHistoryItem> dateHistItems = shortTimeHistoryRepo.finsLstBySidsAndCidAndDate(cid, new ArrayList<>(mapSids.keySet()), query.getStandardDate());
		List<ShortWorkTimeHistoryItem> hisItemLst = this.shortTimeHistoryItemRepo.findByHistIds(dateHistItems.stream().map(c -> c.identifier()).collect(Collectors.toList()));
		
		hisItemLst.stream().forEach(item ->{
			DateHistoryItem dateHistItem = dateHistItems.stream().filter(c -> c.identifier().equals(item.getHistoryId())).findFirst().get();
			result.add( new GridPeregDomainDto(item.getEmployeeId(), mapSids.get(item.getEmployeeId()),ShortWorkTimeDto.createShortWorkTimeDto(dateHistItem, item)));
		});
		
		if (query.getEmpInfos().size() > result.size()) {
			for (int i = result.size(); i < query.getEmpInfos().size(); i++) {
				PeregEmpInfoQuery emp = query.getEmpInfos().get(i);
				result.add(new GridPeregDomainDto(emp.getEmployeeId(), emp.getPersonId(), null));
			}
		}
		
		return result;
	}
}
