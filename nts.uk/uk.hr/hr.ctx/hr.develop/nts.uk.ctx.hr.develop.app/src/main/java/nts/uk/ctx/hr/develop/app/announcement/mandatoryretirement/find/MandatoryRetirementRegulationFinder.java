package nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.find;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto.MandatoryRetirementRegulationDto;
import nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto.RelateMasterDto;
import nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto.RetirePlanCourceDto;
import nts.uk.ctx.hr.develop.app.announcement.mandatoryretirement.dto.StartDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetirementRegulation;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetirementRegulationRepository;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetirePlanCource;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetirePlanCourceRepository;
import nts.uk.ctx.hr.shared.dom.employee.GrpCmonMasterImport;
import nts.uk.ctx.hr.shared.dom.employee.GrpCommonMasterAdaptor;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class MandatoryRetirementRegulationFinder {

	@Inject
	private GrpCommonMasterAdaptor commonMasterAdap;

	@Inject
	private RetirePlanCourceRepository retirePlanCourceRep;
	
	@Inject
	private MandatoryRetirementRegulationRepository mandatoryRep;
	
	public StartDto startPage(String selectHistory) {
		// アルゴリズム [関連マスタの取得] を実行する
		RelateMasterDto relateDto = this.getRelateMaster();
		if(relateDto == null) {
			throw new BusinessException("MsgJ_JMM018_16");
		}else {
			if(selectHistory == null) {
				throw new BusinessException("MsgJ_JMM018_15");
			}else {
				MandatoryRetirementRegulationDto mandatory = this.getLaborRegulation(selectHistory);
				if(mandatory == null) {
					throw new BusinessException("MsgJ_JMM018_17");
				}else {
					return new StartDto(relateDto, mandatory);
				}
			}
		}
		
	}

	/**
	 * アルゴリズム [関連マスタの取得] を実行する(Thực hiện thuật toán [lấy RelatedMaster])
	 * @return RelateMasterDto
	 */
	public RelateMasterDto getRelateMaster() {
		String contractCd = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		// ドメインモデル [グループ会社共通マスタ] を取得する
		Optional<GrpCmonMasterImport> getDomainCompanyCommonMaster = commonMasterAdap.findCommonMasterByContract(contractCd, "M000031");
		if(getDomainCompanyCommonMaster.isPresent()) {
			// アルゴリズム [全ての定年退職コースの取得] を実行する(Thực hiện thuật toán [lấy tất cả RetirePlanCourse])
			List<RetirePlanCource> listRetirePlan = retirePlanCourceRep.getlistRetirePlanCource(companyId);
			if(listRetirePlan.isEmpty()) {
				return null;
			}else {
				List<RetirePlanCourceDto> listRetireDto = listRetirePlan.stream()
						.map(x -> new RetirePlanCourceDto(x.getRetirePlanCourseId(), x.getRetirePlanCourseCode(), x.getRetirePlanCourseName(), x.getRetirePlanCourseClass().value, x.getRetirementAge().v(), x.getDurationFlg().value))
						.collect(Collectors.toList());
				return new RelateMasterDto(null, getDomainCompanyCommonMaster.get().getCommonMasterName(), getDomainCompanyCommonMaster.get().getCommonMasterItems(), listRetireDto);
			}

		}else {
			return null;
		}
	}
	
	/**
	 * アルゴリズム [就業規則の取得] を実行する (THực hiện thuật toán  [Lấy Quy tắc làm việc/Labor regulations] )
	 */
	public MandatoryRetirementRegulationDto getLaborRegulation(String historyId) {
		// アルゴリズム [定年退職の就業規則の取得] を実行する(Thực hiện thuật toán [lấy mandatoryRetirementRegulations])
		Optional<MandatoryRetirementRegulation> findMandotory = mandatoryRep.findByKey(historyId);
		if(findMandotory.isPresent()) {
			MandatoryRetirementRegulation found = findMandotory.get();
			return new MandatoryRetirementRegulationDto(found); 
		}else {
			return null;
		}
	}
	
}
