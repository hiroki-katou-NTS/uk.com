package nts.uk.ctx.pereg.pubimp.person.info.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.daterangeitem.DateRangeItem;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.pereg.pub.person.info.item.CodeNameRefTypeExport;
import nts.uk.ctx.pereg.pub.person.info.item.DateRangeItemExport;
import nts.uk.ctx.pereg.pub.person.info.item.EnumRefConditionExport;
import nts.uk.ctx.pereg.pub.person.info.item.MasterRefConditionExport;
import nts.uk.ctx.pereg.pub.person.info.item.PerInfoItemDefExport;
import nts.uk.ctx.pereg.pub.person.info.item.PersonInfoItemPub;
import nts.uk.ctx.pereg.pub.person.info.item.ReferenceTypesExport;
import nts.uk.ctx.pereg.pub.person.info.item.SelectionItemExport;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;
import nts.uk.shr.pereg.app.ComboBoxObject;

@Stateless
public class PersonInfoItemPubImpl implements PersonInfoItemPub{
	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;
	
	@Inject
	private PerInfoCategoryRepositoty perInfoCategoryRepo;
	
	@Inject
	private I18NResourcesForUK ukResouce;
	
	
	
	@Override
	public List<PerInfoItemDefExport> getPerInfoItemDefByListIdForLayout(List<String> listItemDefId) {
		List<PersonInfoItemDefinition> itemDefinition = this.pernfoItemDefRep.getPerInfoItemDefByListIdv2(listItemDefId,
				AppContexts.user().contractCode());
		return itemDefinition.stream().map(i -> {
			int dispOrder = this.pernfoItemDefRep.getItemDispOrderBy(i.getPerInfoCategoryId(), i.getPerInfoItemDefId());
			return mappingFromDomaintoDto(i, dispOrder);
		}).collect(Collectors.toList());
	}
	
	public PerInfoItemDefExport mappingFromDomaintoDto(PersonInfoItemDefinition itemDef, int dispOrder) {
		List<EnumConstant> selectionItemRefTypes = EnumAdaptor.convertToValueNameList(ReferenceTypes.class, ukResouce);
		PerInfoItemDefExport dto = ItemConvertDomainToDto.createPerInfoItemDefExport(itemDef);
		dto.setDispOrder(dispOrder);
		dto.setSelectionItemRefTypes(selectionItemRefTypes);
		return dto;
	}

	@Override
	public List<String> getAllItemIds(String cid, List<String> ctgCds, List<String> itemCds) {
		if(CollectionUtil.isEmpty(ctgCds) || CollectionUtil.isEmpty(itemCds))
			return new ArrayList<>();
		return this.pernfoItemDefRep.getAllItemIdsByCtgCodeAndItemCd(cid, ctgCds, itemCds);
	}

	@Override
	public String getCategoryName(String cid, String categoryCode) {
		Optional<PersonInfoCategory> ctgOpt = this.perInfoCategoryRepo.getPerInfoCategoryByCtgCD(categoryCode, cid);
		return ctgOpt.isPresent() == true? ctgOpt.get().getCategoryName().v(): null;
	}

	@Override
	public DateRangeItemExport getDateRangeItemByCtgId(String categoryId) {
		DateRangeItem dateRangeItem = perInfoCategoryRepo.getDateRangeItemByCtgId(categoryId);
		return new DateRangeItemExport(dateRangeItem.getPersonInfoCtgId(), dateRangeItem.getStartDateItemId(),
				dateRangeItem.getEndDateItemId(), dateRangeItem.getDateRangeItemId());
	}

	@Override
	public List<ComboBoxObject> getCombo(SelectionItemExport selectionItemDto,
			Map<String, Map<Boolean, List<ComboBoxObject>>> combobox, String employeeId,
			GeneralDate comboBoxStandardDate, boolean isRequired, int perEmplType, boolean isDataType6,
			String categoryCode) {
		if (comboBoxStandardDate == null) {
			comboBoxStandardDate = GeneralDate.today();
		}

		ReferenceTypesExport RefType = selectionItemDto.getReferenceType();
		String referenceCode = "";
		switch (RefType) {
		case ENUM:
			EnumRefConditionExport enumTypeDto = (EnumRefConditionExport) selectionItemDto;
			referenceCode = enumTypeDto.getEnumName();
			break;
		case CODE_NAME:
			CodeNameRefTypeExport codeNameTypeDto = (CodeNameRefTypeExport) selectionItemDto;
			referenceCode = codeNameTypeDto.getTypeCode();
			break;
		case DESIGNATED_MASTER:
			MasterRefConditionExport masterRefTypeDto = (MasterRefConditionExport) selectionItemDto;
			referenceCode = masterRefTypeDto.getMasterType();
			break;
		}
		
		
		return null;
	}
	
	public List<ComboBoxObject> getComboBox(ReferenceTypesExport referenceType, String referenceCode,
			GeneralDate standardDate, String employeeId, String workplaceId, boolean isRequired,
			int perEmplType, boolean isDataType6, String categoryCode, GeneralDate realBaseDate,  boolean isCps009) {

		List<ComboBoxObject> resultList = new ArrayList<ComboBoxObject>();
		List<ComboBoxObject> comboboxItems = new ArrayList<ComboBoxObject>();
		switch (referenceType) {
		case ENUM:
			//resultList = getEnumComboBox(referenceCode);
			break;
		case CODE_NAME:
			//resultList = getCodeNameComboBox(referenceCode, standardDate, perEmplType);
			break;
		case DESIGNATED_MASTER:
			//resultList = getMasterComboBox(referenceCode, employeeId, standardDate, workplaceId, categoryCode, realBaseDate, isCps009);
			break;

		}
		if (!CollectionUtil.isEmpty(resultList)) {
			if (!isRequired && isDataType6) {

				comboboxItems = new ArrayList<ComboBoxObject>(Arrays.asList(new ComboBoxObject("", "")));
			}
			comboboxItems.addAll(resultList);
		}
		return comboboxItems;
	}

	@Override
	public String getItemDfId(String ctgId, String itemCd) {
		String itemDfID = pernfoItemDefRep.getItemDfId(ctgId, itemCd);
		return itemDfID;
	}
}
