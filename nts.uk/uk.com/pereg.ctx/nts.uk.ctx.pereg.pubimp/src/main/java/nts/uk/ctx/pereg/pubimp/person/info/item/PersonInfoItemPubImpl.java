package nts.uk.ctx.pereg.pubimp.person.info.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.pereg.pub.person.info.item.PerInfoItemDefExport;
import nts.uk.ctx.pereg.pub.person.info.item.PersonInfoItemPub;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;

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
}
