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
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
import nts.uk.ctx.pereg.dom.person.info.singleitem.SingleItem;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgData;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.categor.PerInfoCtgDataRepository;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ItemValue;
import nts.uk.shr.pereg.app.find.PeregQuery;
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
			if(itemDef.getItemTypeState().getItemType() == ItemType.SET_ITEM) {
				
				List<PersonInfoItemDefinition> lstDomain = getListChildrenDef(itemDef.getItemTypeState());	
				lstDomain.forEach(i -> {
					if(i.getItemTypeState().getItemType() == ItemType.SET_ITEM) {
						parentItems.add(i);
					}else {
						lstItemDef.add(getItemValueFromDomain(i));
					}
				});
			}
		}
		return lstItemDef;
	}
	private List<PersonInfoItemDefinition> getListChildrenDef(ItemTypeState item){
		String contractCode = AppContexts.user().contractCode();
		// get itemId list of children
		SetItem setItem = (SetItem) item;
		List<String> items = setItem.getItems();
		
		// get children by itemId list
		List<PersonInfoItemDefinition> lstDomain = perInfoItemDefRepositoty.getPerInfoItemDefByListId(items,
				contractCode);
		return lstDomain;
	}
	
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

	private void setItemDefValueOfFixedCtg(List<ItemValue> itemDefList, PeregDto peregDto) {
		Map<String, Object> itemCodeValueMap = MappingFactory.getFullDtoValue(peregDto);
		setItemValueFromMap(itemDefList, itemCodeValueMap);
	}
	
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
