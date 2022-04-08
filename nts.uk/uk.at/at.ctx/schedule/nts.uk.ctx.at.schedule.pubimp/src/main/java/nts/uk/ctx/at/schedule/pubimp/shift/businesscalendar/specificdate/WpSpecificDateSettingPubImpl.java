package nts.uk.ctx.at.schedule.pubimp.shift.businesscalendar.specificdate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemNo;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.SpecificDateItemRepository;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.service.IWorkplaceSpecificDateSettingService;
import nts.uk.ctx.at.schedule.dom.shift.specificdaysetting.service.SpecificDateItemOutput;
import nts.uk.ctx.at.schedule.pub.shift.businesscalendar.specificdate.SpecificDateItemExport;
import nts.uk.ctx.at.schedule.pub.shift.businesscalendar.specificdate.WpSpecificDateSettingExport;
import nts.uk.ctx.at.schedule.pub.shift.businesscalendar.specificdate.WpSpecificDateSettingPub;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class WpSpecificDateSettingPubImpl implements WpSpecificDateSettingPub {
	
	@Inject
	private IWorkplaceSpecificDateSettingService workplaceSpecificDateSettingService;
	
	@Inject
	private SpecificDateItemRepository specificDateItemRepository;

	@Override
	public WpSpecificDateSettingExport workplaceSpecificDateSettingService(String companyID, String workPlaceID,
			GeneralDate date) {
		SpecificDateItemOutput dateItemOutput = workplaceSpecificDateSettingService.workplaceSpecificDateSettingService(companyID, workPlaceID, date);
		return new WpSpecificDateSettingExport(dateItemOutput.getDate(), dateItemOutput.getNumberList());
	}

	@Override
	public List<SpecificDateItemExport> getSpecifiDateByListCode(String companyId, List<Integer> lstSpecificDateItem) {
		List<SpecificDateItemExport> result = new ArrayList<>();
		List<SpecificDateItem> specificDateItems = this.specificDateItemRepository.getSpecifiDateByListCode(companyId
				, lstSpecificDateItem.stream()
					.map(itemNo -> new SpecificDateItemNo( itemNo))
					.collect(Collectors.toList()));
		for(SpecificDateItem spec : specificDateItems ){
			SpecificDateItemExport  specificDateItemExport = new SpecificDateItemExport(spec.getCompanyId(),
					spec.getUseAtr().value,
					spec.getSpecificDateItemNo().v(),
					spec.getSpecificName().toString());
			result.add(specificDateItemExport);
		}
		return result;
	}

	@Override
	public WpSpecificDateSettingExport findSpecDateSetByWkpLst(String companyID, List<String> workPlaceIDLst,
			GeneralDate date) {
		SpecificDateItemOutput dateItemOutput = workplaceSpecificDateSettingService.findSpecDateSetByWkpLst(companyID, workPlaceIDLst, date);
		return new WpSpecificDateSettingExport(dateItemOutput.getDate(), dateItemOutput.getNumberList());
	}

}
