/**
 * 
 */
package nts.uk.ctx.hr.develop.dom.retiredismissalregulation.algorithm;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.hr.develop.dom.empregulationhistory.algorithm.EmploymentRegulationHistoryInterface;
import nts.uk.ctx.hr.develop.dom.retiredismissalregulation.DismissRestrictionTerm;
import nts.uk.ctx.hr.develop.dom.retiredismissalregulation.DissisalNoticeTerm;
import nts.uk.ctx.hr.develop.dom.retiredismissalregulation.RetireDismissalRegulation;
import nts.uk.ctx.hr.shared.dom.dateTerm.DateCaculationTerm;
import nts.uk.ctx.hr.shared.dom.dateTerm.service.DateCaculationTermService;
import nts.uk.ctx.hr.shared.dom.dateTerm.service.dto.EmployeeDateDto;
import nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.WageTypeDto;
import nts.uk.ctx.hr.shared.dom.personalinfo.laborcontracthistory.algorithm.LaborContractHistoryService;

/**
 * @author laitv
 * 
 *
 */
@Stateless
public class RetireDismissalRegulationServices {

	@Inject
	private RetireDismissalRegulationRepository retireDismissalRegulationRepo;

	@Inject
	private EmploymentRegulationHistoryInterface employmentRegulationHis;

	@Inject
	private DateCaculationTermService dateCaculationTermService;

	@Inject
	private LaborContractHistoryService laborContractHistoryService;
	
	//退職・解雇の就業規則の取得
	//Path:UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.contexts.人材育成.人事発令.退職・解雇.アルゴリズム.退職・解雇の就業規則.発注対象外.退職・解雇の就業規則の取得
	// [Input]
	//・会社ID// companyID
	//・履歴ID// HistoryID
	public RetireDismissalRegulation getEmploymentRulesForRetirementAndDismissal(String cid, String hisId){
		
		Optional<RetireDismissalRegulation> domain =  this.retireDismissalRegulationRepo.getDomain(cid, hisId);
		if (domain.isPresent()) {
			return domain.get();
		}
		
		return null;
		
	}
	
	// 退職・解雇の就業規則の追加
	// Path:UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.contexts.人材育成.人事発令.退職・解雇.アルゴリズム.退職・解雇の就業規則.発注対象外.退職・解雇の就業規則の追加
	// [Input]
	//・会社ID// companyID
	//・履歴ID// HistoryID
	//・公開条件// PublicTerm
	//・解雇予告日アラーム// DismissalNoticeDateAlarm
	//・解雇制限アラーム// DismissalRestrictionAlarm
	//・List<解雇予告条件>// List<DismissalNoticeTerm>
	//・List<解雇制限条件>// List<DismissalRestrictionTerm>
	public void addEmploymentRulesForRetirementAndDismissal(String cid, String hisId, DateCaculationTerm publicTerm,
			Boolean dismissalNoticeAlerm, Boolean dismissalRestrictionAlerm,
			List<DissisalNoticeTerm> dismissalNoticeTermList,
			List<DismissRestrictionTerm> dismissalRestrictionTermList) {
		
		RetireDismissalRegulation domain = RetireDismissalRegulation.builder()
				.companyId(cid)
				.historyId(hisId)
				.publicTerm(publicTerm)
				.dismissalNoticeAlerm(dismissalNoticeAlerm)
				.dismissalRestrictionAlerm(dismissalRestrictionAlerm)
				.dismissalNoticeTermList(dismissalNoticeTermList)
				.dismissalRestrictionTermList(dismissalRestrictionTermList)
				.build();
		retireDismissalRegulationRepo.addRetireDismissalRegulation(domain);		
	}

	// 退職・解雇の就業規則の更新
	// UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.contexts.人材育成.人事発令.退職・解雇.アルゴリズム.退職・解雇の就業規則.発注対象外.退職・解雇の就業規則の更新
	// [Input]
	// ・会社ID// companyID
	// ・履歴ID/ HistoryID
	// ・公開条件// PublicTerm
	// ・解雇予告日アラーム// DismissalNoticeDateAlarm
	// ・解雇制限アラーム// DismissalRestrictionAlarm
	// ・List<解雇予告条件>// List<DismissalNoticeTerm>
	// ・List<解雇制限条件>// List<DismissalRestrictionTerm>
	public void updateEmploymentRulesForRetirementAndDismissal(String cid, String hisId, DateCaculationTerm publicTerm,
			Boolean dismissalNoticeAlerm, Boolean dismissalRestrictionAlerm,
			List<DissisalNoticeTerm> dismissalNoticeTermList,
			List<DismissRestrictionTerm> dismissalRestrictionTermList) {
		
		RetireDismissalRegulation domain = RetireDismissalRegulation.builder()
				.companyId(cid)
				.historyId(hisId)
				.publicTerm(publicTerm)
				.dismissalNoticeAlerm(dismissalNoticeAlerm)
				.dismissalRestrictionAlerm(dismissalRestrictionAlerm)
				.dismissalNoticeTermList(dismissalNoticeTermList)
				.dismissalRestrictionTermList(dismissalRestrictionTermList)
				.build();
		retireDismissalRegulationRepo.updateRetireDismissalRegulation(domain);	
		
	}
	
	

	// 基準日で退職・解雇の就業規則の取得
	// Path:UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.contexts.人材育成.人事発令.退職・解雇.アルゴリズム.退職・解雇の就業規則.基準日で退職・解雇の就業規則の取得
	public RetireDismissalRegulationDto getDismissalWorkRules(String cId, GeneralDate baseDate) {

		RetireDismissalRegulationDto result = new RetireDismissalRegulationDto();

		// アルゴリズム [基準日から就業規則の履歴IDの取得] を実行する(Thực hiện thuật toán[Lấy
		// EmploymentRegulationHistory ID từ baseDate])
		Optional<String> historyId = employmentRegulationHis.getHistoryIdByDate(cId, baseDate);

		if (!historyId.isPresent()) {
			result.setProcessingResult(false);
			return result;
		}

		Optional<RetireDismissalRegulation> retireDismissalRegulation = retireDismissalRegulationRepo.getDomain(cId, historyId.get());

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
	// Path:UKDesign.ドメインモデル.NittsuSystem.UniversalK.人事.contexts.人材育成.人事発令.退職・解雇.アルゴリズム.退職・解雇の就業規則.退職関連情報の取得
	public RetirementRelatedInfoDto getRetirementRelatedInfo(String cId, GeneralDate baseDate, String sid,
			GeneralDate retirementDate, Integer retimentType) {

		RetirementRelatedInfoDto result = new RetirementRelatedInfoDto();

		// アルゴリズム [基準日で退職・解雇の就業規則の取得] を実行する(Thực hiện thuật toán [lấy quy tắc
		// công việc của Resignment/Dimissal bằng BaseDate])
		RetireDismissalRegulationDto retireDismissalRegulationDto = this.getDismissalWorkRules(cId, baseDate);

		if (!retireDismissalRegulationDto.getProcessingResult()) {
			result.setProcessingResult(false);
			result.setErrorMessageId("MsgJ_JMM018_23");
			return result;
		}

		// アルゴリズム [算出日の取得] を実行する (Thực hiện thuật toán "Get CalculationDate")
		List<EmployeeDateDto> empDate = Arrays.asList(new EmployeeDateDto(sid, retirementDate));
		DateCaculationTerm dateCaculationTerm = retireDismissalRegulationDto.getPublicTerm();
		List<EmployeeDateDto> empDateResult = this.dateCaculationTermService.getDateBySidList(empDate,
				dateCaculationTerm);

		// 公開日を生成する(Tạo releasedate) 公開日
		GeneralDate releaseDate = null;
		if (!empDateResult.isEmpty()) {
			Optional<EmployeeDateDto> empDateResultOpt = empDateResult.stream()
					.filter(p -> p.getEmployeeId().equals(sid)).findFirst();
			if (empDateResultOpt.isPresent() && empDateResultOpt.get().getTargetDate() != null) {
				releaseDate = empDateResultOpt.get().getTargetDate();
			}
		}

		// 退職区分 Not 3
		if (retimentType != 3) {
			result.setProcessingResult(true);
			result.setReleaseDate(releaseDate);
			return result;

		} else if (retimentType == 3) {
			if (retireDismissalRegulationDto.dismissalNoticeAlerm == false) {
				result.setProcessingResult(true);
				result.setReleaseDate(releaseDate);
				result.setDismissalNoticeAlerm(false);
				return result;

			} else {

				if (retireDismissalRegulationDto.dismissalNoticeTermList.isEmpty()) {
					result.setProcessingResult(true);
					result.setReleaseDate(releaseDate);
					result.setDismissalNoticeAlerm(true);
					result.setDismissalNoticeDateCheckProcess(false);
					result.setErrorMessageId("MsgJ_JMM018_25");
					return result;

				} else {

					// List<解雇予告条件> を絞り込む(Filter list<DismissalNoticeCondition>)
					List<DissisalNoticeTerm> dismissalNoticeTermListAfterFilter = retireDismissalRegulationDto.dismissalNoticeTermList
							.stream().filter(item -> item.getNoticeTermFlg().booleanValue() == true)
							.collect(Collectors.toList());
					// List<解雇予告条件> EMPTY(List<DismissalNoticeTerm>Empty)
					if (dismissalNoticeTermListAfterFilter.isEmpty()) {
						result.setProcessingResult(true);
						result.setReleaseDate(releaseDate);
						result.setDismissalNoticeAlerm(true);
						result.setDismissalNoticeDateCheckProcess(true);
						result.setDismissalAllowance(false);
						return result;

					} else {

						// アルゴリズム [基準日、社員IDリストより、給与区分を取得する] を実行する
						// (Thực hiện thuật toán [lấy WageType từ BaseDate,
						// EmployeeIDList])
						List<WageTypeDto> listWageType = laborContractHistoryService.getWageTypeInfo(Arrays.asList(sid),
								baseDate);

						// List<給与区分> EMPTY
						if (listWageType.isEmpty()) {
							result.setProcessingResult(true);
							result.setReleaseDate(releaseDate);
							result.setDismissalNoticeAlerm(true);
							result.setDismissalNoticeDateCheckProcess(false);
							result.setErrorMessageId("MsgJ_JMM018_24");
							return result;

						} else {

							// List<解雇予告条件>から該当条件を取得する(lấy điều kiện tương ứng từ list<DismissalNoticeTerm>)
							// input dismissalNoticeTermListAfterFilter, listWageType.
							for (int i = 0; i < listWageType.size(); i++) {
								WageTypeDto wageTypeDto = listWageType.get(i);
								if (wageTypeDto.getSid() == sid) {
									List<DissisalNoticeTerm> listDissisalNoticeTerm = dismissalNoticeTermListAfterFilter
											.stream().filter(dis -> dis.getWageType().value == wageTypeDto.getWageType())
											.collect(Collectors.toList());

									if (!listDissisalNoticeTerm.isEmpty()) {
										DissisalNoticeTerm disNoticeTerm = listDissisalNoticeTerm.get(0);
										result.setProcessingResult(true);
										result.setReleaseDate(releaseDate);
										result.setDismissalNoticeAlerm(true);
										result.setDismissalNoticeDateCheckProcess(true);
										result.setDismissalAllowance(disNoticeTerm.getNoticeTermFlg());
										if (disNoticeTerm.getNoticeDateTerm().isPresent()) {
											DismissalNoticeDateCondition disNotDateCon = new DismissalNoticeDateCondition(
													disNoticeTerm.getNoticeDateTerm().get().getCalculationTerm(),
													disNoticeTerm.getNoticeDateTerm().get().getDateSettingNum(),
													disNoticeTerm.getNoticeDateTerm().get().getDateSettingDate()
															.isPresent()
																	? disNoticeTerm.getNoticeDateTerm().get().getDateSettingDate()
																	: Optional.empty());
											result.setDismissalNoticeDateCondition(disNotDateCon);
										} else {
											result.setDismissalNoticeDateCondition(null);
										}
									}
								}
							}
							
							if (!result.getDismissalAllowance()) {
								return result;
							}else{
								
								DismissalNoticeDateCondition disNotice = result.getDismissalNoticeDateCondition();
								// アルゴリズム [算出日の取得] を実行する (Thực hiện thuật toán "Get CalculationDate")
								List<EmployeeDateDto> empInput = Arrays.asList(new EmployeeDateDto(sid, retirementDate));
								DateCaculationTerm dateCaculationTermInput = new DateCaculationTerm(disNotice.getCalculationTerm(), disNotice.getDateSettingNum(), disNotice.getDateSettingDate().isPresent() ?  disNotice.getDateSettingDate() : Optional.empty());
								List<EmployeeDateDto> empDateResult2 = this.dateCaculationTermService.getDateBySidList(empInput,
										dateCaculationTermInput);
								
								
								// 公開日を生成する(Tạo releasedate) 公開日
								GeneralDate dismissalNoticeDate = null;
								if (!empDateResult2.isEmpty()) {
									Optional<EmployeeDateDto> empDateResultOpt2 = empDateResult.stream()
											.filter(p -> p.getEmployeeId().equals(sid)).findFirst();
									if (empDateResultOpt2.isPresent() && empDateResultOpt2.get().getTargetDate() != null) {
										dismissalNoticeDate = empDateResultOpt2.get().getTargetDate();
									}
								}
								
								result.setDismissalNoticeDate(dismissalNoticeDate);
								
								return result;

							}
						}
					}
				}
			}
		}
		
		return result;
	}

}
