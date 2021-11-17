package nts.uk.ctx.at.schedule.dom.shift.specificdayset.service;

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
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.CompanySpecificDateRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.SpecificDateItemRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.WorkplaceSpecificDateRepository;
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
			List<Integer> numberList = companySpecificDateItemOpt.get()
							.getOneDaySpecificItem()
							.getSpecificDayItems().stream()
							.map(item -> item.v())
							.collect(Collectors.toList());
					
			List<SpecificDateItem> currentList = specificDateItemRepository.getSpecifiDateByListCode(companyID, numberList);
			specificDateItemList.addAll(currentList.stream().map(x -> x.getSpecificDateItemNo()).collect(Collectors.toList()));
		}
			
		// アルゴリズム「職場IDから上位職場を取得する」を実行する ( Acquire upper workplace from workplace ID )
		List<String> workplaceIDList = scWorkplaceAdapter.findParentWpkIdsByWkpId(companyID, workPlaceID, date);
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
		return new SpecificDateItemOutput(date, specificDateItemList.stream().distinct().map(x -> x.v().intValue()).collect(Collectors.toList()));
	}

	@Override
	public SpecificDateItemOutput findSpecDateSetByWkpLst(String companyID, List<String> workPlaceIDLst,
			GeneralDate date) {
		List<SpecificDateItemNo> specificDateItemList = new ArrayList<SpecificDateItemNo>();
		Optional<CompanySpecificDateItem> companySpecificDateItemOpt = companySpecificDateRepository.get(companyID, date);
		if( !companySpecificDateItemOpt.isPresent() ){
			List<Integer> numberList = companySpecificDateItemOpt.get()
					.getOneDaySpecificItem()
					.getSpecificDayItems().stream()
					.map( item  -> item.v() )
					.distinct()
					.collect(Collectors.toList());
			List<SpecificDateItem> currentList = specificDateItemRepository.getSpecifiDateByListCode(companyID, numberList);
			specificDateItemList.addAll(currentList.stream().map(x -> x.getSpecificDateItemNo()).collect(Collectors.toList()));
		}
		
		workPlaceIDLst.stream().forEach(workplace -> {
		 Optional<WorkplaceSpecificDateItem> workplaceSpecificDateItemOpt = workplaceSpecificDateRepository.get( workplace, date );
			if( workplaceSpecificDateItemOpt.isPresent() ) {
				workplaceSpecificDateItemOpt.get().getOneDaySpecificItem().getSpecificDayItems()
				.stream().distinct()
				.forEach(item -> {
					if(!specificDateItemList.contains(item)){
						specificDateItemList.add(item);
					}
				});
				
			}
		});
		return new SpecificDateItemOutput(date, specificDateItemList.stream().distinct().map(x -> x.v().intValue()).collect(Collectors.toList()));
	}
	
}
