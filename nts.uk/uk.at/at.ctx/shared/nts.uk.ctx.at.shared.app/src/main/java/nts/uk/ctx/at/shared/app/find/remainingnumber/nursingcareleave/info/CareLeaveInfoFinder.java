package nts.uk.ctx.at.shared.app.find.remainingnumber.nursingcareleave.info;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.NursCareLevRemainDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.NursingCareLeaveRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.NursCareLevRemainInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.NursingCareLeaveRemainingInfo;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class CareLeaveInfoFinder implements PeregFinder<CareLeaveInfoDto> {

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
		Optional<NursingCareLeaveRemainingInfo> childCareInfoDomainOpt = infoRepo
				.getChildCareByEmpId(query.getEmployeeId());
		Optional<NursingCareLeaveRemainingData> careDataDomainOpt = dataRepo.getCareByEmpId(query.getEmployeeId());
		Optional<NursingCareLeaveRemainingData> childCareDataDomainOpt = dataRepo
				.getChildCareByEmpId(query.getEmployeeId());

		if (!careInfoDomainOpt.isPresent() && !childCareInfoDomainOpt.isPresent() && !careDataDomainOpt.isPresent()
				&& !childCareDataDomainOpt.isPresent()) {
			return null;
		}

		CareLeaveInfoDto result = new CareLeaveInfoDto();
		result.setSId(query.getEmployeeId());
		result.setRecordId(query.getEmployeeId());
		
		if (childCareInfoDomainOpt.isPresent()) {
			NursingCareLeaveRemainingInfo childCareInfoDomain = childCareInfoDomainOpt.get();
			result.setChildCareUseArt(childCareInfoDomain.isUseClassification() ? 1 : 0);
			result.setChildCareUpLimSet(childCareInfoDomain.getUpperlimitSetting().value);
			result.setChildCareThisFiscal(childCareInfoDomain.getMaxDayForThisFiscalYear().isPresent() ? childCareInfoDomain.getMaxDayForThisFiscalYear().get().v() : null);
			result.setChildCareNextFiscal(childCareInfoDomain.getMaxDayForNextFiscalYear().isPresent()? childCareInfoDomain.getMaxDayForNextFiscalYear().get().v() : null);
		}
		
		if (childCareDataDomainOpt.isPresent()) {
			NursingCareLeaveRemainingData childCareDataDomain = childCareDataDomainOpt.get();
			result.setChildCareUsedDays(childCareDataDomain.getNumOfUsedDay().v());
		}else {
			result.setChildCareUsedDays(null);
		}

		if (careInfoDomainOpt.isPresent()) {
			NursingCareLeaveRemainingInfo careInfoDomain = careInfoDomainOpt.get();
			result.setCareUseArt(careInfoDomain.isUseClassification() ? 1 : 0);
			result.setCareUpLimSet(careInfoDomain.getUpperlimitSetting().value);
			result.setCareThisFiscal(careInfoDomain.getMaxDayForThisFiscalYear().isPresent()? careInfoDomain.getMaxDayForThisFiscalYear().get().v(): null);
			result.setCareNextFiscal(careInfoDomain.getMaxDayForNextFiscalYear().isPresent()? careInfoDomain.getMaxDayForNextFiscalYear().get().v(): null);
		}

		if (careDataDomainOpt.isPresent()) {
			NursingCareLeaveRemainingData careDataDomain = careDataDomainOpt.get();
			result.setCareUsedDays(careDataDomain.getNumOfUsedDay().v());
		}else {
			result.setCareUsedDays(null);
		}

		return result;

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
