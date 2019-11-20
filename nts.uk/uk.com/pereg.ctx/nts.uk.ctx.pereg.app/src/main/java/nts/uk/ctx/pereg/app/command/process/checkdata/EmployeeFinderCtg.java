package nts.uk.ctx.pereg.app.command.process.checkdata;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.pereg.app.find.processor.LayoutingProcessor;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmInfoCtgDataRepository;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmpInfoCtgData;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemData;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.daterangeitem.DateRangeItem;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.info.setitem.SetItem;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgData;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgDataRepository;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PersonInfoItemData;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.find.PeregQuery;

/**
 * 
 * class nay để lấy list histId cua category History va infoId cua category Multi.
 * laitv
 *
 */
@Stateless
public class EmployeeFinderCtg {

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepositoty;

	@Inject
	private PerInfoItemDefRepositoty perInfoItemDefRepositoty;

	@Inject
	private LayoutingProcessor layoutingProcessor;

	@Inject
	private EmInfoCtgDataRepository emInfoCtgDataRepository;

	@Inject
	private EmpInfoItemDataRepository empInfoItemDataRepository;

	@Inject
	private PerInfoCtgDataRepository perInfoCtgDataRepository;

	@Inject
	private PerInfoItemDataRepository perInfoItemDataRepository;
	
	@Inject
	private EmployeeDataMngInfoRepository employeeRepository;

	public List<String> getListInfoCtgByCtgIdAndSid(PeregQuery query) {
		String categoryId = query.getCategoryId();
		String contractCode = AppContexts.user().contractCode();
		// get category
		PersonInfoCategory perInfoCtg = perInfoCtgRepositoty.getPerInfoCategory(categoryId, contractCode).get();

		if (perInfoCtg.isSingleCategory()) {
			// SINGLE CATEGORY
			return new ArrayList<>();
		}
		// get combo-box object
		List<PersonInfoItemDefinition> lstItemDef = perInfoItemDefRepositoty
				.getAllPerInfoItemDefByCategoryId(categoryId, contractCode);

		if (perInfoCtg.isMultiCategory()) {
			// MULTI CATEGORY
			return getListOfMultiCategory(query, perInfoCtg, lstItemDef);
		} else {
			// HISTORY CATEGORY
			return getListOfHistoryCategory(query, perInfoCtg, lstItemDef);
		}
	}

	private List<String> getListOfMultiCategory(PeregQuery query, PersonInfoCategory perInfoCtg,
			List<PersonInfoItemDefinition> lstItemDef) {
		PersonInfoItemDefinition firstItem = lstItemDef.get(0);

		List<String> infoList;
		if (perInfoCtg.isFixed()) {
			infoList = layoutingProcessor.getListFirstItems(query).stream().map(i -> i.getOptionValue())
					.collect(Collectors.toList());
		} else {
			if (perInfoCtg.isEmployeeType()) {
				infoList = getInfoItemEmployeeType(query, firstItem.getPerInfoItemDefId());
			} else {
				infoList = getInfoItemPersonType(query, firstItem.getPerInfoItemDefId());
			}
		}
		return infoList;
	}

	private List<String> getInfoItemEmployeeType(PeregQuery query, String itemId) {
		List<String> recordIds = emInfoCtgDataRepository
				.getByEmpIdAndCtgId(query.getEmployeeId(), query.getCategoryId()).stream()
				.map(data -> data.getRecordId()).collect(Collectors.toList());
		List<EmpInfoItemData> itemData = empInfoItemDataRepository.getItemsData(itemId, recordIds);
		return itemData.stream().map(data -> data.getRecordId()).collect(Collectors.toList());
	}

	private List<String> getInfoItemPersonType(PeregQuery query, String itemId) {
		List<String> recordIds = perInfoCtgDataRepository.getByPerIdAndCtgId(query.getPersonId(), query.getCategoryId())
				.stream().map(data -> data.getRecordId()).collect(Collectors.toList());
		List<PersonInfoItemData> itemData = perInfoItemDataRepository.getItemData(itemId, recordIds);
		return itemData.stream().map(data -> data.getRecordId()).collect(Collectors.toList());
	}
	
	private List<String> getListOfHistoryCategory(PeregQuery query, PersonInfoCategory perInfoCtg,
			List<PersonInfoItemDefinition> lstItemDef) {

		String categoryId = query.getCategoryId();
		boolean isSelf = AppContexts.user().employeeId().equals(query.getEmployeeId());

		PersonInfoItemDefinition period = getPeriodItem(query.getCategoryCode(), categoryId, lstItemDef);

		List<String> infoList = new ArrayList<>();
		if (perInfoCtg.isFixed()) {
			infoList = layoutingProcessor.getListFirstItems(query).stream().map(i -> i.getOptionValue()).collect(Collectors.toList());
		} else {
			infoList = getInfoListHistType(perInfoCtg, query, period);
		}

		return infoList;
	}
	
	private PersonInfoItemDefinition getPeriodItem(String categoryCode, String categoryId,
			List<PersonInfoItemDefinition> lstItemDef) {
		if (categoryCode.equals("CS00003")) {
			return lstItemDef.get(0);
		}
		
		DateRangeItem dateRangeItem = perInfoCtgRepositoty.getDateRangeItemByCategoryId(categoryId).get();
		return lstItemDef.stream().filter(x -> x.getPerInfoItemDefId().equals(dateRangeItem.getDateRangeItemId()))
				.findFirst().get();
	}
	
	private List<String> getInfoListHistType(PersonInfoCategory perInfoCtg, PeregQuery query,
			PersonInfoItemDefinition period) {

		List<String> timePerInfoItemDefIds = ((SetItem) period.getItemTypeState()).getItems();

		return perInfoCtg.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE
				? getHistInfoEmployeeType(timePerInfoItemDefIds, query)
				: getHistInfoPersonType(timePerInfoItemDefIds, query);

	}
	
	private List<String> getHistInfoEmployeeType(List<String> timePerInfoItemDefIds, PeregQuery query) {
		// get EmpInfoCtgData to get record id
		List<EmpInfoCtgData> lstEmpInfoCtgData = emInfoCtgDataRepository.getByEmpIdAndCtgId(query.getEmployeeId(),
				query.getCategoryId());
		if (lstEmpInfoCtgData.size() == 0)
			return new ArrayList<>();

		List<String> infoIds = new ArrayList<>();
		for (EmpInfoCtgData empInfoCtgData : lstEmpInfoCtgData) {
			// get option value value combo box
			String value = empInfoCtgData.getRecordId();
			// get option text
			List<String> optionText = new ArrayList<>();
			List<EmpInfoItemData> lstEmpInfoCtgItemData = empInfoItemDataRepository
					.getAllInfoItemByRecordId(empInfoCtgData.getRecordId());
			if (lstEmpInfoCtgItemData.size() != 0) {
				for (EmpInfoItemData itemData : lstEmpInfoCtgItemData) {
					if (timePerInfoItemDefIds.contains(itemData.getPerInfoDefId())) {
						Object dateValue = itemData.getDataState().getDateValue();
						optionText.add(dateValue == null ? "" : dateValue.toString());
					}
				}
				if (optionText.size() > 0)
					infoIds.add(value);
			}
		}
		return infoIds;
	}
	
	private List<String> getHistInfoPersonType(List<String> timePerInfoItemDefIds, PeregQuery query) {
		List<String> infoIds = new ArrayList<>();

		EmployeeDataMngInfo employee = employeeRepository.findByEmpId(query.getEmployeeId()).get();
		// get EmpInfoCtgData to get record id
		List<PerInfoCtgData> lstPerInfoCtgData = perInfoCtgDataRepository.getByPerIdAndCtgId(employee.getPersonId(),
				query.getCategoryId());
		if (lstPerInfoCtgData.size() == 0)
			return infoIds;

		// get lst item data and filter base on item def
		List<PersonInfoItemData> lstValidItemData = new ArrayList<>();
		for (PerInfoCtgData empInfoCtgData : lstPerInfoCtgData) {
			// get option value value combo box
			String value = empInfoCtgData.getRecordId();
			// get option text
			List<String> optionText = new ArrayList<>();
			List<PersonInfoItemData> lstPerInfoCtgItemData = perInfoItemDataRepository
					.getAllInfoItemByRecordId(empInfoCtgData.getRecordId());
			if (lstPerInfoCtgItemData.size() != 0) {
				for (PersonInfoItemData itemData : lstValidItemData) {
					if (timePerInfoItemDefIds.contains(itemData.getPerInfoItemDefId())) {
						Object dateValue = itemData.getDataState().getDateValue();
						optionText.add(dateValue == null ? "" : dateValue.toString());
					}
				}
				if (optionText.size() > 0)
					infoIds.add(value);
			}
		}
		return infoIds;
	}

}
