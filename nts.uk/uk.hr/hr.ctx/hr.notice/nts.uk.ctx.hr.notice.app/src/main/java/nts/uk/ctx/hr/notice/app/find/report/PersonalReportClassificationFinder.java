package nts.uk.ctx.hr.notice.app.find.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
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
	
	/**
	 * アルゴリズム「届出一覧表示処理」を実行する (Thực hiện thuật toán「Xử lý hiển thị list đơn xin」 )
	 * @param abolition
	 * @return
	 */
	public List<PersonalReportClassificationDto> getAllReportCls(boolean abolition){
		//アルゴリズム「ログイン者がグループ会社管理者かどうか判定する]
		if(AppContexts.user().roles().forGroupCompaniesAdmin() == null) {
			throw new BusinessException("Msg_1103");
		}
		return this.reportClsRepo.getAllByCid(AppContexts.user().companyId(), abolition).stream().map(c -> {
			return PersonalReportClassificationDto.fromDomain(c);
		}).collect(Collectors.toList());
	}
	
	// get list report jhn011, bên màn JHN001 không cần check quyền.
	public List<PersonalReportClassificationDto> getAllReportClsForJHN001(boolean abolition){
		return this.reportClsRepo.getAllByCid(AppContexts.user().companyId(), abolition).stream().map(c -> {
			return PersonalReportClassificationDto.fromDomain(c);
		}).collect(Collectors.toList());
	}
	
	/**
	 * アルゴリズム「届出の内容表示処理」を実行する (Thực hiện thuật toán 「Xử lý hiển thị nội dung đơn xin」)
	 * @param reportClsId
	 * @return
	 */
	public PersonalReportClassificationDto getDetailReportCls(int reportClsId) {
		String cid = AppContexts.user().companyId();
		
		//ドメインモデル「個別届出種類」、「個別届出の登録項目」をすべて取得する 。ドメイン「[個人情報項目定義]」を取得する。(Get tất cả domain models「type đơn xin cá nhân」、「 Item dang ky don xin ca nhan」)
		Optional<PersonalReportClassification> reportClsOpt = this.reportClsRepo
				.getDetailReportClsByReportClsID(cid, reportClsId);
		
		//ドメインモデル「個人情報項目定義」、「個別届出の登録項目」をすべて取得する (Get all domain model 「個人情報項目定義」、「個別届出の登録項目」)
		List<RegisterPersonalReportItem> listItemCls = this.itemReportClsRepo.getAllItemBy(cid, reportClsId);
		
		List<LayoutReportClsDto> items =  mapItemCls(listItemCls);
		
		List<LayoutReportClsDto> itemInter = new ArrayList<>();
		
		int itemSize = items.size();
		
		for(int i = 0; i < itemSize; i++) {
			
			if(items.get(i).getLayoutItemType() == LayoutReportItemType.SeparatorLine && (i + 1) < itemSize ) {
				
				if(items.get(i + 1).getLayoutItemType() == LayoutReportItemType.SeparatorLine) {
					
					i = i + 1;
					
				}
				
				itemInter.add(items.get(i));
				
			}else {
				
				itemInter.add(items.get(i));
				
			}
		}
		
		return reportClsOpt.isPresent() == true ? PersonalReportClassificationDto.fromDomain(reportClsOpt.get(), itemInter)
				: new PersonalReportClassificationDto();
	}
	
	/**
	 * convert item để lấy thông tin kiểu dữ liệu của item đó
	 * @param listItemCls
	 * @return
	 */
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
						//ドメインモデル「個人情報項目定義」を取得する - Lấy định nghĩa item, điều kiện 
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
						String catDto = this.humanItemPub.getCategoryName(cid, classDto.getCategoryCode());
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
	
	/**
	 * アルゴリズム「システム利用区分から利用可能な個人情報カテゴリを全て取得する」を実行する
	 * (Thực hiện thuật toán 「Get tất cả các catergory thông tin cá nhân có thể sử dụng từ phân loại sử dụng system」)
	 * @return
	 */
	public PerInfoCtgDataEnumImport getCtg() {
		return this.humanCtgPub.getAllPerInfoCtgHumanByCompany();
	}

}
