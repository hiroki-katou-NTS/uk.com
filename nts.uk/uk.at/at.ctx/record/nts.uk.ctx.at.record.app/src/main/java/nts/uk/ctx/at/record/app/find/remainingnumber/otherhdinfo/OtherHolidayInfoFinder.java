package nts.uk.ctx.at.record.app.find.remainingnumber.otherhdinfo;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.remainingnumber.excessleave.ExcessLeaveInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.excessleave.ExcessLeaveInfoRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.publicholiday.PublicHolidayRemain;
import nts.uk.ctx.at.record.dom.remainingnumber.publicholiday.PublicHolidayRemainRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class OtherHolidayInfoFinder implements PeregFinder<OtherHolidayInfoDto>{

	@Inject
	private PublicHolidayRemainRepository publicHolidayRemainRepository;
	
	@Inject 
	private ExcessLeaveInfoRepository excessLeaveInfoRepository;
	
	@Override
	public String targetCategoryCode() {
		return "CS00035";
	}

	@Override
	public Class<OtherHolidayInfoDto> dtoClass() {
		return OtherHolidayInfoDto.class;
	}

	@Override
	public DataClassification dataType() {
		return DataClassification.EMPLOYEE;
	}

	@Override
	public PeregDomainDto getSingleData(PeregQuery query) 
	{
		Optional<PublicHolidayRemain> holidayRemain = publicHolidayRemainRepository.get(query.getEmployeeId());
		Optional<ExcessLeaveInfo> excessLeave = excessLeaveInfoRepository.get(query.getEmployeeId());
		
		OtherHolidayInfoDto dto = OtherHolidayInfoDto.createFromDomain(holidayRemain, excessLeave);
		
		
		return dto;
	}

	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ComboBoxObject> getListFirstItems(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

}
