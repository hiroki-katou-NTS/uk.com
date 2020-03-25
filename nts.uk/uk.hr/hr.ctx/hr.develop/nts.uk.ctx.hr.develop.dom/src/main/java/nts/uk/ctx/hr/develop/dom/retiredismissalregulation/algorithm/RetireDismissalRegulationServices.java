/**
 * 
 */
package nts.uk.ctx.hr.develop.dom.retiredismissalregulation.algorithm;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.empregulationhistory.algorithm.EmploymentRegulationHistoryInterface;
import nts.uk.ctx.hr.develop.dom.retiredismissalregulation.RetireDismissalRegulation;
import nts.uk.ctx.hr.shared.dom.dateTerm.DateCaculationTerm;
import nts.uk.ctx.hr.shared.dom.dateTerm.service.DateCaculationTermService;
import nts.uk.ctx.hr.shared.dom.dateTerm.service.dto.EmployeeDateDto;

/**
 * @author laitv
 *
 */
public class RetireDismissalRegulationServices {
	
	@Inject
	private RetireDismissalRegulationRepository retireDismissalRegulationRepo;
	
	@Inject
	private EmploymentRegulationHistoryInterface employmentRegulationHis;
	
	@Inject
	private DateCaculationTermService dateCaculationTermService;
	
	// 基準日で退職・解雇の就業規則の取得
	public RetireDismissalRegulationDto getDismissalWorkRules(String cId, GeneralDate baseDate){
		
		RetireDismissalRegulationDto result = new RetireDismissalRegulationDto();
		
		// アルゴリズム [基準日から就業規則の履歴IDの取得] を実行する(Thực hiện thuật toán[Lấy EmploymentRegulationHistory ID từ baseDate])
		Optional<String> historyId =  employmentRegulationHis.getHistoryIdByDate( cId, baseDate);
		
		if (!historyId.isPresent()) {
			result.setProcessingResult(false);
			return result;
		}
		
		Optional<RetireDismissalRegulation>  retireDismissalRegulation = retireDismissalRegulationRepo.getDomain( cId,  historyId.get());
		
		if (!retireDismissalRegulation.isPresent()) {
			result.setProcessingResult(false);
			return result;
		}
		
		result.setProcessingResult(true);
		result.setCompanyId(cId); 
		result.setHistoryId(historyId.get());
		result.setPublicTerm(retireDismissalRegulation.get().getPublicTerm());
		result.setDismissalNoticeAlerm(retireDismissalRegulation.get().getDismissalNoticeAlerm());
		result.setDismissalRestrictionAlerm(retireDismissalRegulation.get().getDismissalRestrictionAlerm());
		result.setDismissalNoticeTermList(retireDismissalRegulation.get().getDismissalNoticeTermList());
		result.setDismissalRestrictionTermList(retireDismissalRegulation.get().getDismissalRestrictionTermList());
		
		return result;
	}
	
	
	// 退職関連情報の取得
	public RetirementRelatedInfoDto getRetirementRelatedInfo(String cId, GeneralDate baseDate, String sid, GeneralDate retirementDate,
			Integer retimentType) {		
		
		RetirementRelatedInfoDto result = new RetirementRelatedInfoDto();
		
		// アルゴリズム [基準日で退職・解雇の就業規則の取得] を実行する(Thực hiện thuật toán [lấy quy tắc công việc của Resignment/Dimissal bằng BaseDate])
		RetireDismissalRegulationDto retireDismissalRegulationDto = this.getDismissalWorkRules(cId, baseDate);
		
		if (!retireDismissalRegulationDto.getProcessingResult()) {
			throw new BusinessException("MsgJ_JMM018_23");
		}
		
		
		// アルゴリズム [算出日の取得] を実行する (Thực hiện thuật toán "Get CalculationDate")
		List<EmployeeDateDto> empDate = Arrays.asList(new EmployeeDateDto(sid, retirementDate));
		DateCaculationTerm dateCaculationTerm = retireDismissalRegulationDto.getPublicTerm();
		
		List<EmployeeDateDto> empDateResult =  this.dateCaculationTermService.getDateBySidList( empDate,  dateCaculationTerm);
		
		if (retimentType == 3) {
			if (retireDismissalRegulationDto.getDismissalNoticeAlerm()) {
				
			} else {
				
				
			}
			
			
			
			
		} else {
			
			
		}
		
		return result;
		
		
		
	}

}
