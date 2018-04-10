package nts.uk.ctx.at.record.app.find.remainingnumber.nursingcareleave.info;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.data.NursCareLevRemainDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.data.NursingCareLeaveRemainingData;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.info.NursCareLevRemainInfoRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.info.NursingCareLeaveRemainingInfo;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class CareLeaveInfoFinder implements PeregFinder<CareLeaveInfoDto>{

	@Inject
	private NursCareLevRemainInfoRepository infoRepo;
	
	@Inject
	private NursCareLevRemainDataRepository dataRepo;

	@Override
	public String targetCategoryCode() {
		return "CS00036";
	}

	@Override
	public Class<CareLeaveInfoDto> dtoClass() {
		return CareLeaveInfoDto.class;
	}

	@Override
	public DataClassification dataType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PeregDomainDto getSingleData(PeregQuery query) {
		
		Optional<NursingCareLeaveRemainingInfo> careInfoDomainOpt = infoRepo.getCareByEmpId(query.getEmployeeId());
		Optional<NursingCareLeaveRemainingInfo> childCareInfoDomainOpt = infoRepo.getChildCareByEmpId(query.getEmployeeId());
		Optional<NursingCareLeaveRemainingData> careDataDomainOpt = dataRepo.getCareByEmpId(query.getEmployeeId());
		Optional<NursingCareLeaveRemainingData> childCareDataDomainOpt = dataRepo.getChildCareByEmpId(query.getEmployeeId());
		if (careInfoDomainOpt.isPresent() && childCareInfoDomainOpt.isPresent() &&
				careDataDomainOpt.isPresent() && childCareDataDomainOpt.isPresent()) {
			NursingCareLeaveRemainingInfo careInfoDomain = careInfoDomainOpt.get();
			NursingCareLeaveRemainingInfo childCareInfoDomain = childCareInfoDomainOpt.get();
			NursingCareLeaveRemainingData careDataDomain = careDataDomainOpt.get();
			NursingCareLeaveRemainingData childCareDataDomain = childCareDataDomainOpt.get();
			return CareLeaveInfoDto.createDomain(query.getEmployeeId(), childCareInfoDomain.isUseClassification(), childCareInfoDomain.getUpperlimitSetting().value,
					childCareInfoDomain.getMaxDayForThisFiscalYear().isPresent() ? childCareInfoDomain.getMaxDayForThisFiscalYear().get() : null, 
					childCareInfoDomain.getMaxDayForNextFiscalYear().isPresent() ? childCareInfoDomain.getMaxDayForNextFiscalYear().get() : null,  
					childCareDataDomain.getNumOfUsedDay(), careInfoDomain.isUseClassification(), careInfoDomain.getUpperlimitSetting().value, 
					careInfoDomain.getMaxDayForThisFiscalYear().isPresent() ? careInfoDomain.getMaxDayForThisFiscalYear().get() : null, 
					careInfoDomain.getMaxDayForNextFiscalYear().isPresent() ? careInfoDomain.getMaxDayForNextFiscalYear().get() : null, 
							careDataDomain.getNumOfUsedDay());
		} else {
			return null;
		}
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
