package nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.find;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto.MandatoryRetirementRegulationDto;
import nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto.RelateMasterDto;
import nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto.RetirePlanCourceDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetirementRegulation;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetirePlanCource;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.mandatoryRetirementRegulation.MandatoryRetirementRegulationService;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.retirePlanCource.RetirePlanCourceService;
import nts.uk.ctx.hr.shared.dom.employee.GrpCmonMasterImport;
import nts.uk.ctx.hr.shared.dom.employee.GrpCommonMasterAdaptor;

@Stateless
public class MandatoryRetirementRegulationFinder {

	@Inject
	private GrpCommonMasterAdaptor commonMasterAdap;

	@Inject
	private RetirePlanCourceService retirePlanCourceService;
	
	@Inject
	private MandatoryRetirementRegulationService mandatoryService;
	
	//アルゴリズム [関連マスタの取得] を実行する(Thực hiện thuật toán [lấy RelatedMaster])
	public Optional<RelateMasterDto> getRelateMaster(String contractCd, String companyId, String commonMasterId) {
		Optional<GrpCmonMasterImport> commonMasterItem = commonMasterAdap.findCommonMasterByContract(contractCd, commonMasterId);
		if(!commonMasterItem.isPresent() || commonMasterItem.get().getCommonMasterItems().isEmpty()) {
			return Optional.empty();
		}
		List<RetirePlanCource> listRetirePlan = retirePlanCourceService.getAllRetirePlanCource(companyId);
		if(listRetirePlan.isEmpty()) {
			return Optional.empty();
		}
		
		return Optional.of(new RelateMasterDto(commonMasterItem.get(),
				listRetirePlan.stream()
				.map(x -> new RetirePlanCourceDto(x))
				.collect(Collectors.toList())));
	}
	
	//アルゴリズム [就業規則の取得] を実行する (THực hiện thuật toán  [Lấy Quy tắc làm việc/Labor regulations] )
	public Optional<MandatoryRetirementRegulationDto> getMandatoryRetirementRegulation(String companyId, String historyId) {
		Optional<MandatoryRetirementRegulation> result = mandatoryService.getMandatoryRetirementRegulation(companyId, historyId);
		if(result.isPresent()) {
			return Optional.of(new MandatoryRetirementRegulationDto(result.get()));
		}
		return Optional.empty();
	}
	
}
