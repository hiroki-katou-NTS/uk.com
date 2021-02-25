package nts.uk.ctx.at.schedule.dom.shift.specificdayset.service;

import java.util.List;

import nts.arc.time.GeneralDate;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface IWorkplaceSpecificDateSettingService {
	
	public SpecificDateItemOutput workplaceSpecificDateSettingService(String companyID, String workPlaceID, GeneralDate date);
	
	public SpecificDateItemOutput findSpecDateSetByWkpLst(String companyID, List<String> workPlaceIDLst, GeneralDate date);
	
}
