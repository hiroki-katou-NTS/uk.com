package nts.uk.ctx.at.shared.app.find.remainingnumber.annualleave;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoDomService;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnLeaMaxDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RervLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class AnnualLeaveFinder implements PeregFinder<AnnualLeaveDto> {

	@Inject
	private AnnLeaEmpBasicInfoRepository annLeaBasicInfoRepo;

	@Inject
	private AnnLeaMaxDataRepository maxDataRepo;
	
	@Inject
	private AnnLeaEmpBasicInfoDomService annLeaDomainService;
	
	@Inject
	private AnnLeaGrantRemDataRepository annLeaDataRepo;
	
	@Inject
	private RervLeaGrantRemDataRepository rervLeaDataRepo;

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
		String companyId = AppContexts.user().companyId();
		String employeeId = query.getEmployeeId();
		AnnualLeaveDto dto = new AnnualLeaveDto(employeeId);
		
		
		
		// 年休残数
		List<AnnualLeaveGrantRemainingData> annualLeaveDataList = annLeaDataRepo.findNotExp(employeeId);
		dto.setAnnualLeaveNumber(annLeaDomainService.calculateAnnLeaNumWithFormat(companyId, annualLeaveDataList));
		dto.setLastGrantDate(annLeaDomainService.calculateLastGrantDate(employeeId));

		// 年休社員基本情報
		Optional<AnnualLeaveEmpBasicInfo> basicInfoOpt = annLeaBasicInfoRepo.get(employeeId);
		
		if (basicInfoOpt.isPresent()) {
//			AnnualLeaveEmpBasicInfo basicInfo = basicInfoOpt.get();
//			AnnLeaEmpBasicInfo annLeaInfo = new AnnLeaEmpBasicInfo(query.getEmployeeId(),
//					basicInfo.getGrantRule().getNextGrantDate(),
//					basicInfo.getGrantRule().getGrantTableCode().v(), null, null, null, null);
//					
//			YearHolidayInfoResult result = annLeaDomainService.getYearHolidayInfo(annLeaInfo);
//			
//			basicInfo.getGrantRule().setNextGrantDate(result.getNextGrantDate());
//			basicInfo.getGrantRule().setNextGrantDay(result.getNextGrantDay());
//			basicInfo.getGrantRule().setNextMaxTime(result.getNextMaxTime());
			
			dto.pullDataFromBasicInfo(basicInfoOpt.get());
		}

		// 年休上限データ
		Optional<AnnualLeaveMaxData> maxDataOpt = maxDataRepo.get(employeeId);
		if (maxDataOpt.isPresent()) {
			dto.pullDataFromMaxData(maxDataOpt.get());
		}

		// 積立年休残数
		List<ReserveLeaveGrantRemainingData> rervLeaveDataList = rervLeaDataRepo.findNotExp(employeeId, companyId);
		dto.setResvLeaRemainNumber(annLeaDomainService.calculateRervLeaveNumber(rervLeaveDataList));

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
