package nts.uk.ctx.hr.notice.app.find.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.hr.notice.dom.report.PersonalReportClassification;
import nts.uk.ctx.hr.notice.dom.report.PersonalReportClassificationRepository;
import nts.uk.ctx.hr.notice.dom.report.RegisterPersonalReportItem;
import nts.uk.ctx.hr.notice.dom.report.RegisterPersonalReportItemRepository;
import nts.uk.ctx.hr.notice.dom.report.valueImported.HumanItemPub;
import nts.uk.ctx.hr.notice.dom.report.valueImported.PerInfoItemDefImport;
import nts.uk.ctx.hr.notice.dom.report.valueImported.ctg.HumanCategoryPub;
import nts.uk.ctx.hr.notice.dom.report.valueImported.ctg.PerInfoCtgDataEnumImport;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PersonalReportClassificationFinder {
	@Inject
	private PersonalReportClassificationRepository reportClsRepo;
	
	@Inject
	private RegisterPersonalReportItemRepository  itemReportClsRepo;
	
	@Inject
	private HumanItemPub humanItemPub;
	
	
	@Inject
	private HumanCategoryPub humanCtgPub;
	
	public List<PersonalReportClassificationDto> getAllReportCls(boolean abolition){
		return this.reportClsRepo.getAllByCid(AppContexts.user().companyId(), abolition).stream().map(c -> {
			return PersonalReportClassificationDto.fromDomain(c);
		}).collect(Collectors.toList());
	}
	
	public PersonalReportClassificationDto getDetailReportCls(int reportClsId) {
		String cid = AppContexts.user().companyId();
		
		Optional<PersonalReportClassification> reportClsOpt = this.reportClsRepo
				.getDetailReportClsByReportClsID(reportClsId);
		
		List<RegisterPersonalReportItem> listItemCls = this.itemReportClsRepo.getAllItemBy(cid, reportClsId);
		List<LayoutReportClsDto> items =  mapItemCls(listItemCls);
		
		return reportClsOpt.isPresent() == true ? PersonalReportClassificationDto.fromDomain(reportClsOpt.get(), items)
				: new PersonalReportClassificationDto();
	}
	
	
	private List<LayoutReportClsDto> mapItemCls(List<RegisterPersonalReportItem> listItemCls){
		List<LayoutReportClsDto> result = new ArrayList<>();
		String cid = AppContexts.user().companyId();
		if(CollectionUtil.isEmpty(listItemCls)) return new ArrayList<LayoutReportClsDto>();
		int order = 0;
		for(RegisterPersonalReportItem item: listItemCls) {
			LayoutReportClsDto classDto = new LayoutReportClsDto(item.getPReportClsId(),
					item.getLayoutOrder(),item.getCategoryId(),item.getItemType(), item.getCategoryCd(), 1, item.getCategoryName());
			switch(item.getItemType()) {
			case 0://single item
			case 1://list item
				if(order == item.getLayoutOrder())  break;
				List<RegisterPersonalReportItem> itemsByOrder1 = listItemCls.stream()
						.filter(c -> c.getLayoutOrder() == item.getLayoutOrder() && c.getPReportClsId() == item.getPReportClsId()).collect(Collectors.toList());
				order = item.getLayoutOrder();
				List<String> itemIds = itemsByOrder1.stream().map(c -> c.getItemId()).distinct().collect(Collectors.toList());
					if(!CollectionUtil.isEmpty(itemIds)) {
						List<PerInfoItemDefImport> listItemDefDto = new ArrayList<PerInfoItemDefImport>();

						List<PerInfoItemDefImport> listItemDef = humanItemPub.getAll(itemIds);
						listItemDef.stream().forEach(c ->{
							c.setCategoryCode(item.getCategoryCd());
							c.setCategoryName(item.getCategoryName());
						});
						
						for(String id: itemIds) {
							List<PerInfoItemDefImport> dto = listItemDef.stream().filter(p -> p.getId().equals(id))
									.collect(Collectors.toList());

							if (!dto.isEmpty()) {
								listItemDefDto.add(dto.get(0));
							}
						}
						
						List<String> roots = listItemDefDto.stream()
								.filter(f -> f.getItemParentCode().equals("") || f.getItemParentCode() == null)
								.map(m -> m.getItemCode()).collect(Collectors.toList());
						if (roots.size() > 1) {
							classDto.setListItemDf(listItemDefDto);
						} else {
							if (listItemDefDto.size() > 1) {
								if (classDto.getLayoutItemType() == LayoutReportItemType.ITEM) {
									if (listItemDefDto.get(0).getItemTypeState().getItemType() == 1
											|| listItemDefDto.get(0).getItemTypeState().getItemType() == 3) {
										classDto.setListItemDf(listItemDefDto);
									} else {
										classDto.setListItemDf(new ArrayList<PerInfoItemDefImport>());
									}
								} else if (classDto.getLayoutItemType() == LayoutReportItemType.LIST) {
									classDto.setListItemDf(listItemDefDto);
								}
							} else if (listItemDefDto.size() == 1) {
								classDto.setListItemDf(listItemDefDto);
							} else {
								classDto.setListItemDf(new ArrayList<PerInfoItemDefImport>());
							}

							if (classDto.getLayoutItemType() == LayoutReportItemType.ITEM && !listItemDefDto.isEmpty()
									&& listItemDefDto.get(0) != null) {
								classDto.setClassName(listItemDefDto.get(0).getItemName());
							}
						}
					}
									
					if (classDto.getLayoutItemType() == LayoutReportItemType.LIST) {
						String catDto = this.humanItemPub.getCategoryName(cid, "");
						if (catDto != null) {
							classDto.setClassName(catDto);
						}
					}
					result.add(classDto);
				break;
			case 2:// SeparatorLine
				result.add(classDto);
				 break;
			}
			
		}
		
		
		return result.stream()
				.filter(m -> (m.getLayoutItemType() != LayoutReportItemType.SeparatorLine && m.getListItemDf() != null
						&& !m.getListItemDf().isEmpty()) || m.getLayoutItemType() == LayoutReportItemType.SeparatorLine)
				.collect(Collectors.toList());
	}
	
	public PerInfoCtgDataEnumImport getCtg() {
		return this.humanCtgPub.getAllPerInfoCtgHumanByCompany();
	}
	
	
	

}
