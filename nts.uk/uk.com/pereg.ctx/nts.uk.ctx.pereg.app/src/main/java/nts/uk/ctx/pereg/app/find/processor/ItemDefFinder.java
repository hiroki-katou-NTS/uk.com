package nts.uk.ctx.pereg.app.find.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.app.find.common.MappingFactory;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmInfoCtgDataRepository;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmpInfoCtgData;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemDataRepository;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.pereg.dom.person.info.category.IsFixed;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.ItemType;
import nts.uk.ctx.pereg.dom.person.info.item.ItemTypeState;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.pereg.dom.person.info.setitem.SetItem;
import nts.uk.ctx.pereg.dom.person.info.setitem.SetTableItem;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
import nts.uk.ctx.pereg.dom.person.info.singleitem.SingleItem;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgData;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgDataRepository;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.find.PeregEmpInfoQuery;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregQueryByListEmp;
import nts.uk.shr.pereg.app.find.dto.GridPeregDto;
import nts.uk.shr.pereg.app.find.dto.OptionalItemDataDto;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

@Stateless
public class ItemDefFinder {

	
	@Inject
	private PerInfoItemDefRepositoty perItemRepo;
	
	@Inject
	private LayoutingProcessor layoutingProcessor;
	
	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepo;

	@Inject
	private EmpInfoItemDataRepository empInfoItemDataRepo;
	
	@Inject
	private PerInfoItemDataRepository perInfoItemDataRepo;
	
	@Inject
	private EmInfoCtgDataRepository empInCtgDataRepo;
	
	@Inject
	private PerInfoCtgDataRepository perInCtgDataRepo;
	
	@Inject
	private PerInfoItemDefRepositoty perInfoItemDefRepositoty;
	// hàm này dùng cho màn cps001
	public List<ItemValue> getFullListItemDef(PeregQuery query){
		// app context
		String contractCd = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();

		// get category 
		PersonInfoCategory perInfoCtg = perInfoCtgRepo.getPerInfoCategoryByCtgCD(query.getCategoryCode(), companyId).get();
		query.setCategoryId(perInfoCtg.getPersonInfoCategoryId());
		
		PeregDto peregDto = null;
		
		if ((perInfoCtg.getIsFixed() == IsFixed.FIXED && query.getInfoId() != null)
				||(query.getInfoId() == null && perInfoCtg.getCategoryType() == CategoryType.SINGLEINFO)) {
			peregDto = layoutingProcessor.findSingle(query);
		}	
		
		List<PersonInfoItemDefinition> lstPerInfoDef = perItemRepo.getAllPerInfoItemDefByCategoryId(perInfoCtg.getPersonInfoCategoryId(), contractCd);
		List<ItemValue> lstItemDef = getItemDefFromDomain(lstPerInfoDef);
		setItemDefValue(lstItemDef, query, perInfoCtg, peregDto);
		return lstItemDef;
	}
	/**
	 * dùng cho màn cps03
	 * @param query
	 * @return
	 */
	public Map<String, List<ItemValue>> getFullListItemDefCPS003(PeregQueryByListEmp query){
		// app context
		String contractCd = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		Map<String, PeregMatrixByEmp> empLst = new HashMap<>();
		Map<String, List<ItemValue>> result = new HashMap<>();

		// get category 
		PersonInfoCategory perInfoCtg = perInfoCtgRepo.getPerInfoCategoryByCtgCD(query.getCategoryCode(), companyId).get();
		
		List<GridPeregDto> gridPeregDto = new ArrayList<>();
		
		if (perInfoCtg.getIsFixed() == IsFixed.FIXED) {
			gridPeregDto.addAll(layoutingProcessor.findAllData(query));
		}else {
			
		}	
		
		List<PersonInfoItemDefinition> lstPerInfoDef = perItemRepo.getAllPerInfoItemDefByCategoryId(perInfoCtg.getPersonInfoCategoryId(), contractCd);

		if(!gridPeregDto.isEmpty()) {
			gridPeregDto.stream().forEach(m ->{
				PeregEmpInfoQuery empQuery = new PeregEmpInfoQuery(m.getEmployeeId(), m.getPersonId(),
						m.getPeregDto() == null ? null
								: (m.getPeregDto().getDomainDto() == null ? null
										: m.getPeregDto().getDomainDto().getRecordId()));
				List<ItemValue> lstItemDef = getItemDefFromDomain(lstPerInfoDef);
				empLst.put(m.getEmployeeId(), new PeregMatrixByEmp(lstItemDef, empQuery, m.getPeregDto()));
			});
		}
		
		setItemDefValueCPS003(empLst, perInfoCtg,  query);
		
		empLst.entrySet().stream().forEach(c ->{
			result.put(c.getKey(), c.getValue().getItems());
		});
		
		return result;
	}
	
	private List<ItemValue> getItemDefFromDomain(List<PersonInfoItemDefinition> lstPerInfoDef){
		List<ItemValue> lstItemDef = new ArrayList<>();
		//
		ItemValue itemDef;
		for(PersonInfoItemDefinition item : lstPerInfoDef) {			
			if(item.getItemTypeState().getItemType() == ItemType.SINGLE_ITEM) {
				itemDef = getItemValueFromDomain(item);
				lstItemDef.add(itemDef);
			}else {
				lstItemDef.addAll(getListItemChildren(item));
			}
		}
		return lstItemDef;
	}
	
	private ItemValue getItemValueFromDomain(PersonInfoItemDefinition item) {
		SingleItem single = (SingleItem) item.getItemTypeState();
		DataTypeState dataTypeState = single.getDataTypeState();
		DataTypeValue dataType = single.getDataTypeState().getDataTypeValue();
		switch (dataType) {
		case SELECTION:
		case SELECTION_BUTTON:
		case SELECTION_RADIO:
			ReferenceTypes referenceType = dataTypeState.getReferenceTypes();
			String referenceCode = dataTypeState.getReferenceCode();
			return ItemValue.createItemValue(item.getPerInfoItemDefId(), item.getItemCode().v(),item.getItemName().v(),"", null, dataType.value,
					referenceType.value, referenceCode);
		default:
			return ItemValue.createItemValue(item.getPerInfoItemDefId(), item.getItemCode().v(),item.getItemName().v(),"", null, dataType.value,
					null, null);
		}
	}
	
	private List<ItemValue> getListItemChildren(PersonInfoItemDefinition parentItem){
		List<ItemValue> lstItemDef = new ArrayList<>();
		List<PersonInfoItemDefinition> parentItems = new ArrayList<>();
		parentItems.add(parentItem);
		int index;
		while(parentItems.size() > 0) {
			PersonInfoItemDefinition itemDef = parentItems.stream().findFirst().get();
			index = parentItems.indexOf(itemDef);
			parentItems.remove(index);
			// get children by itemId list
			if(itemDef.getItemTypeState().getItemType() == ItemType.SET_ITEM || itemDef.getItemTypeState().getItemType() == ItemType.TABLE_ITEM) {
				
				List<PersonInfoItemDefinition> lstDomain = getListChildrenDef(itemDef.getItemTypeState());	
				for(PersonInfoItemDefinition i: lstDomain) {
					if(i.getItemTypeState().getItemType().value == ItemType.SET_ITEM.value || i.getItemTypeState().getItemType().value == ItemType.TABLE_ITEM.value) {
						parentItems.add(i);
					}else {
						lstItemDef.add(getItemValueFromDomain(i));
					}
				}
			}
		}
		return lstItemDef;
	}
	private List<PersonInfoItemDefinition> getListChildrenDef(ItemTypeState item){
		String contractCode = AppContexts.user().contractCode();
		// get itemId list of children
		List<String> items = new ArrayList<>();
		if(item.getItemType()  == ItemType.SET_ITEM) {
			SetItem setItem = (SetItem) item;
			items.addAll(setItem.getItems());
		}else {
			SetTableItem setItem = (SetTableItem) item;
			items.addAll(setItem.getItems());
		}
		
		// get children by itemId list
		List<PersonInfoItemDefinition> lstDomain = perInfoItemDefRepositoty.getPerInfoItemDefByListId(items,
				contractCode);
		return lstDomain;
	}
	
	/**
	 * dùng cho màn cps003
	 * 
	 * @param itemDefList
	 * @param query
	 * @param perInfoCtg
	 * @param peregDto
	 */
	private void setItemDefValueCPS003(Map<String, PeregMatrixByEmp> emp, PersonInfoCategory perInfoCtg,
			PeregQueryByListEmp query) {
		if (perInfoCtg.getIsFixed() == IsFixed.FIXED) {
			setItemDefValueOfFixedCtg(emp);
		} else {
			// mục tiêu trả về một list recordIf
			Map<String, String> recordIdsBySid = query.getEmpInfos().stream().filter(c -> c.getInfoId()!= null)
					.collect(Collectors.toMap(PeregEmpInfoQuery::getEmployeeId, PeregEmpInfoQuery::getInfoId));
			if (perInfoCtg.getCategoryType() == CategoryType.SINGLEINFO) {
				recordIdsBySid.clear();
				recordIdsBySid.putAll(getRecordIdBySid(query, perInfoCtg));
			}
			setItemDefValueOfOptCtg(recordIdsBySid, perInfoCtg.getPersonEmployeeType(), emp);
		}
	}

	
	private Map<String, String> getRecordIdBySid(PeregQueryByListEmp query, PersonInfoCategory perInfoCtg) {
		// recordId, sid
		Map<String, String> result = new HashMap<>();

		if (perInfoCtg.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE) {
			List<String> sids = query.getEmpInfos().stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
			Map<String, List<EmpInfoCtgData>> empInfoCtgDatas = empInCtgDataRepo
					.getBySidsAndCtgId(sids, perInfoCtg.getPersonInfoCategoryId()).stream()
					.collect(Collectors.groupingBy(c -> c.getEmployeeId()));
			empInfoCtgDatas.forEach((k, v) -> {
				result.put(v.get(0).getRecordId(), k);
			});
		} else {
			List<String> pids = query.getEmpInfos().stream().map(c -> c.getPersonId()).collect(Collectors.toList());
			Map<String, List<PerInfoCtgData>> perInfoCtgDatas = perInCtgDataRepo
					.getAllByPidsAndCtgId(pids, perInfoCtg.getPersonInfoCategoryId()).stream()
					.collect(Collectors.groupingBy(c -> c.getPersonId()));
			perInfoCtgDatas.forEach((k, v) -> {
				PeregEmpInfoQuery emp = query.getEmpInfos().stream().filter(c -> c.getEmployeeId().equals(k))
						.findFirst().get();
				result.put(v.get(0).getRecordId(), emp.getEmployeeId());
			});
		}

		return result;

	}
	
	/**
	 * dung cho cps001 & cps002
	 * @param itemDefList
	 * @param query
	 * @param perInfoCtg
	 * @param peregDto
	 */
	private void setItemDefValue(List<ItemValue> itemDefList, PeregQuery query,
			PersonInfoCategory perInfoCtg, PeregDto peregDto) {
		if (perInfoCtg.getIsFixed() == IsFixed.FIXED) {			 
			setItemDefValueOfFixedCtg(itemDefList, peregDto);
		} else {
			String recordId = query.getInfoId();
			if(perInfoCtg.getCategoryType() == CategoryType.SINGLEINFO)
			{
				if(perInfoCtg.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE) {
					List<EmpInfoCtgData> empInfoCtgDatas = empInCtgDataRepo.getByEmpIdAndCtgId(query.getEmployeeId(),
							perInfoCtg.getPersonInfoCategoryId());
					recordId = empInfoCtgDatas.get(0).getRecordId();
				}else {
					List<PerInfoCtgData> perInfoCtgDatas = perInCtgDataRepo.getByPerIdAndCtgId(query.getPersonId(),
							perInfoCtg.getPersonInfoCategoryId());
					recordId = perInfoCtgDatas.get(0).getRecordId();
				}
			}
			setItemDefValueOfOptCtg(recordId, perInfoCtg.getPersonEmployeeType(), itemDefList);
			
		}
	}
	
	/**
	 * dùng cho màn cps003
	 * @author lanlt
	 * @param itemDefList
	 * @param peregDto
	 */
	private void setItemDefValueOfFixedCtg(Map<String, PeregMatrixByEmp> itemDefList) {
		itemDefList.entrySet().stream().forEach(c ->{
			Map<String, Object> itemCodeValueMap = MappingFactory.getFullDtoValue(c.getValue().getDto());
			setItemValueFromMap(c.getValue().getItems(), itemCodeValueMap);
		});
	}
	
	/**
	 * dùng cho màn cps001 && cps002
	 * @author lanlt
	 * @param itemDefList
	 * @param peregDto
	 */
	private void setItemDefValueOfFixedCtg(List<ItemValue> itemDefList, PeregDto peregDto) {
		Map<String, Object> itemCodeValueMap = MappingFactory.getFullDtoValue(peregDto);
		setItemValueFromMap(itemDefList, itemCodeValueMap);
	}
	
	/**
	 * dung cho man cps001 && cps002
	 * @param recordId
	 * @param type
	 * @param itemDefList
	 */
	private void setItemDefValueOfOptCtg(String recordId, PersonEmployeeType type, List<ItemValue> itemDefList) {
		if (type == PersonEmployeeType.EMPLOYEE) {
			List<OptionalItemDataDto> empOptionItemData = empInfoItemDataRepo
					.getAllInfoItemByRecordId(recordId).stream().map(x -> x.genToPeregDto())
					.collect(Collectors.toList());
			Map<String, Object> mapEmpOptionItemData = new HashMap<>();
			for(OptionalItemDataDto i : empOptionItemData) {
				mapEmpOptionItemData.put(i.getItemCode(), i.getValue());
			}
			setItemValueFromMap(itemDefList, mapEmpOptionItemData);
		} else {
			List<OptionalItemDataDto> perOptionItemData = perInfoItemDataRepo
					.getAllInfoItemByRecordId(recordId).stream().map(x -> x.genToPeregDto())
					.collect(Collectors.toList());
			Map<String, Object> mapPerOptionItemData = new HashMap<>();
			for(OptionalItemDataDto i : perOptionItemData) {
				mapPerOptionItemData.put(i.getItemCode(), i.getValue());
			}
			setItemValueFromMap(itemDefList, mapPerOptionItemData);
		}
	}
	
	/**
	 * dung cho man cps003
	 * @param recordId
	 * @param type
	 * @param itemDefList
	 */
	private void setItemDefValueOfOptCtg(Map<String, String> recordIdsBySid, PersonEmployeeType type, Map<String, PeregMatrixByEmp> itemDefList) {
		if (type == PersonEmployeeType.EMPLOYEE) {
			// lấy tất cả item theo một list recordIds
			List<OptionalItemDataDto> empOptionItemData = empInfoItemDataRepo
					.getAllInfoItemByRecordId(recordIdsBySid.keySet().stream().collect(Collectors.toList())).stream().map(x -> x.genToPeregDto())
					.collect(Collectors.toList());
			// groupBy theo recordId : ứng với mỗi recordId sẽ có một list các item dạng optional
			Map<String, List<OptionalItemDataDto>> empOptionItemDataMap = empOptionItemData.parallelStream().collect(Collectors.groupingBy(c -> c.getRecordId()));
			
			//mục đích  mapping value theo itemCode, trả về một
			empOptionItemDataMap.entrySet().parallelStream().forEach(c ->{
				String sid = recordIdsBySid.get(c.getKey());
				PeregMatrixByEmp emp = itemDefList.get(sid);
				Map<String, Object> mapEmpOptionItemData = new HashMap<>();
				for(OptionalItemDataDto i : c.getValue()) {
					mapEmpOptionItemData.put(i.getItemCode(), i.getValue());
				}
				setItemValueFromMap(emp.getItems(), mapEmpOptionItemData);
			});

			
		} else {
			// lấy tất cả item theo một list recordIds
			List<OptionalItemDataDto> perOptionItemData = perInfoItemDataRepo
					.getAllInfoItemByRecordId(recordIdsBySid.keySet().stream().collect(Collectors.toList())).stream().map(x -> x.genToPeregDto())
					.collect(Collectors.toList());
			// groupBy theo recordId : ứng với mỗi recordId sẽ có một list các item dạng optional
			Map<String, List<OptionalItemDataDto>> perOptionItemDataMap = perOptionItemData.parallelStream().collect(Collectors.groupingBy(c -> c.getRecordId()));
			
			//mục đích  mapping value theo itemCode, trả về một
			perOptionItemDataMap.entrySet().parallelStream().forEach(c ->{
				String sid = recordIdsBySid.get(c.getKey());
				PeregMatrixByEmp emp = itemDefList.get(sid);
				Map<String, Object> mapPerOptionItemData = new HashMap<>();
				for(OptionalItemDataDto i : c.getValue()) {
					mapPerOptionItemData.put(i.getItemCode(), i.getValue());
				}
				setItemValueFromMap(emp.getItems(), mapPerOptionItemData);
			});
		}
	}
	
	/**
	 * dùng cho màn cps001 && cps002
	 * @param itemDefList
	 * @param itemCodeValueMap
	 */
	private void setItemValueFromMap(List<ItemValue> itemDefList, Map<String, Object> itemCodeValueMap){
		for(ItemValue itemDef : itemDefList) {
			if(itemCodeValueMap.containsKey(itemDef.itemCode())) {
				Object value = itemCodeValueMap.get(itemDef.itemCode());
				itemDef.setValue(value);
				itemDef.setValueBefore(value);
			}
		}
	}
}
