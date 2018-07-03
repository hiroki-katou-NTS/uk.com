package nts.uk.ctx.at.shared.app.find.remainingnumber.nursingcareleave;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.ChildCareLeaveRemaiDataRepo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.ChildCareLeaveRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.LeaveForCareData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.LeaveForCareDataRepo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.LeaveForCareInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.LeaveForCareInfoRepository;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class CareLeaveInfoFinder implements PeregFinder<CareLeaveInfoDto> {

	@Inject
	private LeaveForCareInfoRepository careInfoRepo;

	@Inject
	private ChildCareLeaveRemInfoRepository childCareInfoRepo;

	@Inject
	private ChildCareLeaveRemaiDataRepo childCareDataRepo;
	
	@Inject
	private LeaveForCareDataRepo careDataRepo;

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

		// child-care
		Optional<ChildCareLeaveRemainingInfo> childCareInfoDomainOpt = childCareInfoRepo
				.getChildCareByEmpId(query.getEmployeeId());
		Optional<ChildCareLeaveRemainingData> childCareDataDomainOpt = childCareDataRepo
				.getChildCareByEmpId(query.getEmployeeId());

		// care
		Optional<LeaveForCareInfo> careInfoDomainOpt = careInfoRepo.getCareByEmpId(query.getEmployeeId());
		Optional<LeaveForCareData> careDataDomainOpt = careDataRepo.getCareByEmpId(query.getEmployeeId());
		

		if (!careInfoDomainOpt.isPresent() && !childCareInfoDomainOpt.isPresent() && !careDataDomainOpt.isPresent()
				&& !childCareDataDomainOpt.isPresent()) {
			return null;
		}

		return CareLeaveInfoDto.createFromDomain(query.getEmployeeId(), childCareInfoDomainOpt, childCareDataDomainOpt,
				careInfoDomainOpt, careDataDomainOpt);

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
