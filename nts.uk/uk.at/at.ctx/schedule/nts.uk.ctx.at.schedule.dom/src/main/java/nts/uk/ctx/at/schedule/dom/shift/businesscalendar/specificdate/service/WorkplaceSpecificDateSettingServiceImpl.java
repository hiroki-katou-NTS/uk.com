package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.item.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.primitives.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.repository.SpecificDateItemRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateRepository;

public class WorkplaceSpecificDateSettingServiceImpl implements IWorkplaceSpecificDateSettingService {
	
	@Inject
	private CompanySpecificDateRepository companySpecificDateRepository;
	
	@Inject
	private WorkplaceSpecificDateRepository workplaceSpecificDateRepository;
	
	@Inject
	private SpecificDateItemRepository specificDateItemRepository;

	@Override
	public SpecificDateItemOutput workplaceSpecificDateSettingService(String companyID, String workPlaceID, GeneralDate date) {
		List<SpecificDateItemNo> specificDateItemList = Collections.emptyList();
		List<CompanySpecificDateItem> companySpecificDateItemList = companySpecificDateRepository.getComSpecByDateWithName(companyID, date.toString("yyyy/MM/dd"));
		if(!CollectionUtil.isEmpty(companySpecificDateItemList)){
			List<String> numberList = companySpecificDateItemList.stream().map(x -> x.getSpecificDateItemNo().v().toString()).collect(Collectors.toList());
			numberList.stream().distinct();
			List<SpecificDateItem> currentList = specificDateItemRepository.getSpecifiDateByListCode(companyID, numberList);
			specificDateItemList.addAll(currentList.stream().map(x -> x.getSpecificDateItemNo()).collect(Collectors.toList()));
		}
			
		// アルゴリズム「職場IDから上位職場を取得する」を実行する ( Acquire upper workplace from workplace ID )
		// SyWorkplacePub.findParentWpkIdsByWkpId
		List<String> workplaceIDList = Collections.emptyList();
		workplaceIDList.stream().forEach(workplace -> {
			List<WorkplaceSpecificDateItem> workplaceSpecificDateItemList = workplaceSpecificDateRepository.getWpSpecByDateWithName(workplace, date.toString("yyyy/MM/dd"));
			workplaceSpecificDateItemList.stream().distinct();
			workplaceSpecificDateItemList.forEach(item -> {
				if(!specificDateItemList.contains(item.getSpecificDateItemNo())){
					specificDateItemList.add(item.getSpecificDateItemNo());
				}
			});
		});
		return new SpecificDateItemOutput(date, specificDateItemList.stream().distinct().map(x -> x.v().intValue()).collect(Collectors.toList()));
	}
	
}
