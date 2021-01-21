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
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregQueryByListEmp;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainBySidDto;
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

		List<String> sids = query.getEmpInfos().stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());

		query.getEmpInfos().forEach(c -> {
			result.add(new GridPeregDomainDto(c.getEmployeeId(), c.getPersonId(), null));
		});

		List<DateHistoryItem> dateHistItems = shortTimeHistoryRepo.finsLstBySidsAndCidAndDate(cid, sids,
				query.getStandardDate());

		List<ShortWorkTimeHistoryItem> hisItemLst = this.shortTimeHistoryItemRepo
				.findByHistIds(dateHistItems.stream().map(c -> c.identifier()).collect(Collectors.toList()));

		result.stream().forEach(c -> {
			Optional<ShortWorkTimeHistoryItem> histItemOpt = hisItemLst.stream()
					.filter(emp -> emp.getEmployeeId().equals(c.getEmployeeId())).findFirst();
			if (histItemOpt.isPresent()) {
				Optional<DateHistoryItem> dateHistItemOpt = dateHistItems.stream()
						.filter(date -> date.identifier().equals(histItemOpt.get().getHistoryId())).findFirst();
				c.setPeregDomainDto(dateHistItemOpt.isPresent() == true
						? ShortWorkTimeDto.createShortWorkTimeDto(dateHistItemOpt.get(), histItemOpt.get())
						: null);
			}
		});

		return result;
	}

	@Override
	public List<GridPeregDomainBySidDto> getListData(PeregQueryByListEmp query) {
		
		String cid = AppContexts.user().companyId();

		List<GridPeregDomainBySidDto> result = new ArrayList<>();

		List<String> sids = query.getEmpInfos().stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());

		query.getEmpInfos().forEach(c -> {
			result.add(new GridPeregDomainBySidDto(c.getEmployeeId(), c.getPersonId(), new ArrayList<>()));
		});
		
		List<DateHistoryItem> dateHistItems = shortTimeHistoryRepo.getByEmployeeListNoWithPeriod(cid, sids);
		
		List<Object[]> hisItemLstAndListEnum = this.shortTimeHistoryItemRepo
				.findByHistIdsCPS013(dateHistItems.stream().map(c -> c.identifier()).collect(Collectors.toList()));
		
		result.stream().forEach(c -> {
			List<Object[]> listHistItem = hisItemLstAndListEnum.stream()
					.filter(emp -> ((ShortWorkTimeHistoryItem) emp[0]).getEmployeeId().equals(c.getEmployeeId())).collect(Collectors.toList());
			
			if (!listHistItem.isEmpty()) {
				List<PeregDomainDto> listPeregDomainDto = new ArrayList<>();
				listHistItem.forEach(h -> {
					ShortWorkTimeHistoryItem shortWorkTimeHisItem = (ShortWorkTimeHistoryItem) h[0];
					@SuppressWarnings("unchecked")
					Map<String, Integer> mapListEnum =  (Map<String, Integer>) h[1];
					
					Optional<DateHistoryItem> dateHistoryItem = dateHistItems.stream().filter(i -> i.identifier().equals(shortWorkTimeHisItem
							.getHistoryId())).findFirst();
					if (dateHistoryItem.isPresent()) {
						listPeregDomainDto.add(ShortWorkTimeDto.createShortWorkTimeDto(dateHistoryItem.get() , shortWorkTimeHisItem,
								mapListEnum.get("IS00104")));
					}
				});
				if (!listPeregDomainDto.isEmpty()) {
					c.setPeregDomainDto(listPeregDomainDto);
				}
			}
		});
		return result.stream().distinct().collect(Collectors.toList());
	}
}
