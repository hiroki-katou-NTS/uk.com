package nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.ScWorkplaceAdapter;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.CompanySpecificDateRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.WorkplaceSpecificDateRepository;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class WorkplaceSpecificDateSettingServiceImpl implements IWorkplaceSpecificDateSettingService {
	
	@Inject
	private CompanySpecificDateRepository companySpecificDateRepository;
	
	@Inject
	private WorkplaceSpecificDateRepository workplaceSpecificDateRepository;
	
	@Inject
	private SpecificDateItemRepository specificDateItemRepository;
	
	@Inject
	private ScWorkplaceAdapter scWorkplaceAdapter;

	@Override
	public SpecificDateItemOutput workplaceSpecificDateSettingService(String companyID, String workPlaceID, GeneralDate date) {
		List<SpecificDateItemNo> specificDateItemList = new ArrayList<SpecificDateItemNo>();
		Optional<CompanySpecificDateItem> companySpecificDateItemOpt = companySpecificDateRepository.get( companyID, date);
		if(companySpecificDateItemOpt.isPresent()){
			List<SpecificDateItemNo> specificDateItemNos = companySpecificDateItemOpt.get()
							.getOneDaySpecificItem()
							.getSpecificDayItems();
					
			List<SpecificDateItem> currentList = specificDateItemRepository.getSpecifiDateByListCode(companyID, specificDateItemNos);
			specificDateItemList.addAll(currentList.stream().map(x -> x.getSpecificDateItemNo()).collect(Collectors.toList()));
		}
			
		// アルゴリズム「職場IDから上位職場を取得する」を実行する ( Acquire upper workplace from workplace ID )
		List<String> workplaceIDList = scWorkplaceAdapter.getWorkplaceIdAndUpper(companyID, date, workPlaceID);
		workplaceIDList.stream().forEach(workplace -> {
			Optional<WorkplaceSpecificDateItem> workplaceSpecificDateItemOpt = workplaceSpecificDateRepository.get( workplace, date );
			if( workplaceSpecificDateItemOpt.isPresent() ) {
				workplaceSpecificDateItemOpt.get().getOneDaySpecificItem().getSpecificDayItems().forEach(item -> {
					if(!specificDateItemList.contains(item)){
						specificDateItemList.add(item);
					}
				});
				
			}

		});
		return new SpecificDateItemOutput(	date
										,	specificDateItemList.stream()
												.distinct()
												.map(x -> x.v().intValue())
												.collect(Collectors.toList())
											);
	}

	@Override
	public SpecificDateItemOutput findSpecDateSetByWkpLst(String companyID, List<String> workPlaceIDLst,
			GeneralDate date) {
		List<SpecificDateItemNo> specificDateItemList = new ArrayList<SpecificDateItemNo>();
		Optional<CompanySpecificDateItem> companySpecificDateItemOpt = companySpecificDateRepository.get(companyID, date);
		if( companySpecificDateItemOpt.isPresent() ){
			List<SpecificDateItemNo> specificDateItemNos = companySpecificDateItemOpt.get()
					.getOneDaySpecificItem()
					.getSpecificDayItems();
			List<SpecificDateItem> currentList = specificDateItemRepository.getSpecifiDateByListCode(companyID, specificDateItemNos);
			specificDateItemList.addAll(currentList.stream().map(x -> x.getSpecificDateItemNo()).collect(Collectors.toList()));
		}
		
		workPlaceIDLst.stream().forEach(workplace -> {
		 Optional<WorkplaceSpecificDateItem> workplaceSpecificDateItemOpt = workplaceSpecificDateRepository.get( workplace, date );
			if( workplaceSpecificDateItemOpt.isPresent() ) {
				workplaceSpecificDateItemOpt.get().getOneDaySpecificItem().getSpecificDayItems()
				.stream().forEach(item -> {
					if(!specificDateItemList.contains(item)){
						specificDateItemList.add(item);
					}
				});
				
			}
		});
		
		return new SpecificDateItemOutput(	date
										,	specificDateItemList.stream()
											.distinct()
											.map(x -> x.v().intValue())
											.collect(Collectors.toList())
											);
	}
	
}
