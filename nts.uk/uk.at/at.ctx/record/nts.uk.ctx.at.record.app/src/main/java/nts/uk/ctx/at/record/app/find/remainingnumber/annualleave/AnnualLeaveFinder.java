package nts.uk.ctx.at.record.app.find.remainingnumber.annualleave;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.AnnLeaMaxDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class AnnualLeaveFinder implements PeregFinder<AnnualLeaveDto>{
	
	@Inject
	private AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepo;
	
	@Inject
	private AnnLeaMaxDataRepository maxDataRepo;

	@Override
	public String targetCategoryCode() {
		return "CS00024";
	}

	@Override
	public Class<AnnualLeaveDto> dtoClass() {
		return AnnualLeaveDto.class;
	}

	@Override
	public DataClassification dataType() {
		return DataClassification.EMPLOYEE;
	}

	@Override
	public AnnualLeaveDto getSingleData(PeregQuery query) {
		Optional<AnnualLeaveEmpBasicInfo> basicInfoOpt = annLeaEmpBasicInfoRepo.get(query.getEmployeeId());
		Optional<AnnualLeaveMaxData> maxDataOpt = maxDataRepo.get(query.getEmployeeId());
		if (basicInfoOpt.isPresent() && maxDataOpt.isPresent()) {
			AnnualLeaveDto dto = AnnualLeaveDto.createFromDomains(basicInfoOpt.get(), maxDataOpt.get());
			return dto;
		}
		return null;
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
