package nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.mandatoryRetirementRegulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.EnableRetirePlanCourse;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetireTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetirementRegulation;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetirementRegulationRepository;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.PlanCourseApplyTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetireDateTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetirePlanCource;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.ComprehensiveEvaluationDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.EmployeeAgeDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.EmployeeBasicInfoImport;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.EmployeeInformationImport;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.EmploymentDateDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.EmploymentInfoImport;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.EvaluationInfoDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.OutputObjectDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetirePlanParam;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetireTermDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetirementCourseInformationDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetirementCourseInformationIdDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetirementDateDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetirementInforDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetirementPlannedPersonDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.SearchCondition;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.retirePlanCource.RetirePlanCourceService;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.enums.ReachedAgeTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.enums.RetireDateRule;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.enums.RetirePlanCourseClass;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.primitiveValue.RetirementAge;
import nts.uk.ctx.hr.develop.dom.empregulationhistory.algorithm.EmploymentRegulationHistoryInterface;
import nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.service.IGetYearStartEndDateByDate;
import nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.service.YearStartEnd;
import nts.uk.ctx.hr.shared.dom.dateTerm.DateCaculationTerm;
import nts.uk.ctx.hr.shared.dom.dateTerm.service.DateCaculationTermService;
import nts.uk.ctx.hr.shared.dom.dateTerm.service.dto.EmployeeDateDto;
import nts.uk.ctx.hr.shared.dom.personalinfo.humanresourceevaluation.HumanResourceEvaluation;
import nts.uk.ctx.hr.shared.dom.personalinfo.humanresourceevaluation.HumanResourceEvaluationService;
import nts.uk.ctx.hr.shared.dom.personalinfo.humanresourceevaluation.PersonnelAssessmentResults;
import nts.uk.ctx.hr.shared.dom.personalinfo.medicalhistory.MedicalhistoryItemResults;
import nts.uk.ctx.hr.shared.dom.personalinfo.medicalhistory.MedicalhistoryManagement;
import nts.uk.ctx.hr.shared.dom.personalinfo.medicalhistory.MedicalhistoryServices;
import nts.uk.ctx.hr.shared.dom.personalinfo.stresscheck.StressCheckManagement;
import nts.uk.ctx.hr.shared.dom.personalinfo.stresscheck.StressCheckResults;
import nts.uk.ctx.hr.shared.dom.personalinfo.stresscheck.StressCheckService;
import nts.uk.ctx.hr.shared.dom.referEvaluationItem.EvaluationItem;
import nts.uk.ctx.hr.shared.dom.referEvaluationItem.ReferEvaluationItem;
import nts.arc.time.calendar.period.DatePeriod;

@Stateless
public class MandatoryRetirementRegulationServiceImpl implements MandatoryRetirementRegulationService {

	@Inject
	private MandatoryRetirementRegulationRepository repo;
	
	@Inject
	private EmploymentRegulationHistoryInterface employmentRegulationHistoryInterface;
	
	@Inject
	private RetirePlanCourceService retirePlanCourceService;
	
	@Inject
	private SyEmploymentService syEmploymentService;
	
	@Inject
	private HumanResourceEvaluationService humanResourceEvaluationService;
	
	@Inject
	private MedicalhistoryServices medicalhistoryServices;
	
	@Inject
	private StressCheckService stressCheckService;
	
	@Inject
	private IGetYearStartEndDateByDate iGetYearStartEndDateByDate;
	
	@Inject
	private DateCaculationTermService dateCaculationTermService;
	
	@Override
	public Optional<MandatoryRetirementRegulation> getMandatoryRetirementRegulation(String companyId, String historyId) {
		return repo.getMandatoryRetirementRegulation(historyId, companyId);
	}

	@Override
	public void addMandatoryRetirementRegulation(String companyId, String historyId, int reachedAgeTerm,
			DateCaculationTerm publicTerm, RetireDateTerm retireDateTerm, boolean planCourseApplyFlg,
			List<MandatoryRetireTerm> mandatoryRetireTerm, List<ReferEvaluationItem> referEvaluationTerm,
			PlanCourseApplyTerm planCourseApplyTerm) {
		MandatoryRetirementRegulation domain = MandatoryRetirementRegulation.createFromJavaType(
				companyId, 
				historyId, 
				reachedAgeTerm, 
				publicTerm, 
				retireDateTerm, 
				planCourseApplyFlg, 
				mandatoryRetireTerm, 
				referEvaluationTerm, 
				planCourseApplyTerm);
		repo.add(domain);
	}

	@Override
	public void updateMandatoryRetirementRegulation(String companyId, String historyId, int reachedAgeTerm,
			DateCaculationTerm publicTerm, RetireDateTerm retireDateTerm, boolean planCourseApplyFlg,
			List<MandatoryRetireTerm> mandatoryRetireTerm, List<ReferEvaluationItem> referEvaluationTerm,
			PlanCourseApplyTerm planCourseApplyTerm) {
		MandatoryRetirementRegulation domain = MandatoryRetirementRegulation.createFromJavaType(
				companyId, 
				historyId, 
				reachedAgeTerm, 
				publicTerm, 
				retireDateTerm, 
				planCourseApplyFlg, 
				mandatoryRetireTerm, 
				referEvaluationTerm, 
				planCourseApplyTerm);
		repo.update(domain);
		
	}

	@Override
	public List<ReferEvaluationItem> getReferEvaluationItemByDate(String companyId, GeneralDate baseDate) {
		Optional<String> historyId = employmentRegulationHistoryInterface.getHistoryIdByDate(companyId, baseDate);
		if(!historyId.isPresent()) {
			throw new BusinessException("MsgJ_JMM018_2");
		}
		Optional<MandatoryRetirementRegulation> result = repo.getMandatoryRetirementRegulation(historyId.get(), companyId);
		if(!result.isPresent()) {
			throw new BusinessException("MsgJ_JMM018_2");
		}
		return result.get().getReferEvaluationTerm();
	}

	@Override
	public List<RetirementCourseInformationDto> getAppliedRetireCourseByDate(String companyId, GeneralDate baseDate) {
		Optional<String> historyId = employmentRegulationHistoryInterface.getHistoryIdByDate(companyId, baseDate);
		if(!historyId.isPresent()) {
			throw new BusinessException("MsgJ_JMM018_2");
		}
		Optional<MandatoryRetirementRegulation> mandatoryRetirementRegulation = repo.getMandatoryRetirementRegulation(historyId.get(), companyId);
		if(!mandatoryRetirementRegulation.isPresent()) {
			throw new BusinessException("MsgJ_JMM018_2");
		}
		mandatoryRetirementRegulation.get().getMandatoryRetireTerm().removeIf(c->!c.isUsageFlg());
		
		//退職日条件
		RetireDateTerm retireDateTerm = mandatoryRetirementRegulation.get().getRetireDateTerm();
		//List<定年退職コース情報>{共通マスタ項目ID、定年退職コースID}
		List<RetirementCourseInformationIdDto> retirementCourseInformation =  new ArrayList<>();
		for (MandatoryRetireTerm item : mandatoryRetirementRegulation.get().getMandatoryRetireTerm()) {
			retirementCourseInformation.addAll(item.getEnableRetirePlanCourse().stream().map(c-> new RetirementCourseInformationIdDto(item.getEmpCommonMasterItemId(), c.getRetirePlanCourseId())).collect(Collectors.toList()));
		}
		
		//List<定年退職コース>{定年退職コースID、定年退職コースCD、定年退職コース名、定年退職コース区分、定年年齢、継続区分}
		List<RetirePlanCource> retirePlanCourceList = retirePlanCourceService.getAllRetirePlanCource(companyId);
		if(retirePlanCourceList.isEmpty()) {
			throw new BusinessException("MsgJ_JMM018_6");
		}
		
		//・List<雇用情報>{雇用コード、雇用名称、グループ会社共通マスタ項目ID}
		List<EmploymentInfoImport> employmentInfoImportList = syEmploymentService.getEmploymentInfo(companyId, Optional.of(true), Optional.of(false), Optional.of(false), Optional.of(false), Optional.of(true));
		if(employmentInfoImportList.isEmpty()) {
			throw new BusinessException("MsgJ_JMM018_9");
		}
		
		for (EmploymentInfoImport employmentInfo : employmentInfoImportList) {
			if(StringUtil.isNullOrEmpty(employmentInfo.empCommonMasterItemId, true)){
				throw new BusinessException("MsgJ_JMM018_10");
			}
		}
		
		List<RetirementCourseInformationDto> result = new ArrayList<>();
		for (RetirementCourseInformationIdDto item : retirementCourseInformation) {
			Optional<RetirePlanCource> retirePlanCource = retirePlanCourceList.stream().filter(c->c.getRetirePlanCourseId()==item.getRetirePlanCourseId()).findFirst();
			if(retirePlanCource.isPresent()) {
				List<EmploymentInfoImport> employmentInfoImportSubList = employmentInfoImportList.stream().filter(c->c.empCommonMasterItemId.equals(item.getEmpCommonMasterItemId())).collect(Collectors.toList());
				for (EmploymentInfoImport employmentInfoImport : employmentInfoImportSubList) {
					result.add(new RetirementCourseInformationDto(
							employmentInfoImport.getEmploymentCode(), 
							employmentInfoImport.getEmploymentName(), 
							retirePlanCource.get().getRetirePlanCourseClass(), 
							retirePlanCource.get().getRetirementAge(), 
							retireDateTerm, 
							retirePlanCource.get().getRetirePlanCourseId(), 
							retirePlanCource.get().getRetirePlanCourseCode(), 
							retirePlanCource.get().getRetirePlanCourseName(), 
							retirePlanCource.get().getDurationFlg()));
				}
			}
		}
		return result;
	}

	@Override
	public List<RetirementPlannedPersonDto> getMandatoryRetirementListByPeriodDepartmentEmployment(String companyId, GeneralDate startDate,
			GeneralDate endDate, Optional<RetirementAge> retirementAge, List<String> departmentId,
			List<String> employmentCode) {
		Optional<String> historyId = employmentRegulationHistoryInterface.getHistoryIdByDate(companyId, GeneralDate.today());
		if(!historyId.isPresent()) {
			throw new BusinessException("MsgJ_JMM018_2");
		}
		Optional<MandatoryRetirementRegulation> mandatoryRetirementRegulation = repo.getMandatoryRetirementRegulation(historyId.get(), companyId);
		if(!mandatoryRetirementRegulation.isPresent()) {
			throw new BusinessException("MsgJ_JMM018_2");
		}
		//※）List<MandatoryRetireTerm>．xac dinh tuoi nghi huu = 1
		mandatoryRetirementRegulation.get().getMandatoryRetireTerm().removeIf(c->!c.isUsageFlg());
		
		//・年齢到達条件
		ReachedAgeTerm reachedAgeTerm = mandatoryRetirementRegulation.get().getReachedAgeTerm();
		
		//・退職日条件{退職日基準、退職日指定日}
		RetireDateTerm retireDateTerm = mandatoryRetirementRegulation.get().getRetireDateTerm();
		
		//・公開条件{日付基準、指定数、指定日}
		DateCaculationTerm publicTerm = mandatoryRetirementRegulation.get().getPublicTerm();
		
		//・List<評価参考情報>{評価項目、評価対象、評価表示数、判断基準}
		List<ReferEvaluationItem> referEvaluationTerm = mandatoryRetirementRegulation.get().getReferEvaluationTerm();
		
		//・List<定年退職コース情報>{共通マスタ項目ID、定年退職コースID}
		List<RetirementCourseInformationIdDto> retirementCourseInformation = new ArrayList<>();
		
		for (MandatoryRetireTerm item : mandatoryRetirementRegulation.get().getMandatoryRetireTerm()) {
			for (EnableRetirePlanCourse id : item.getEnableRetirePlanCourse()) {
				retirementCourseInformation.add(new RetirementCourseInformationIdDto(item.getEmpCommonMasterItemId(), id.getRetirePlanCourseId()));
			}
		}
		
		Optional<GeneralDate> yearEndMonthDate = Optional.empty(); 
		List<EmploymentDateDto> closingDate = new ArrayList<>();
		List<EmploymentDateDto> attendanceDate = new ArrayList<>();
		
		if(retireDateTerm.getRetireDateTerm() == RetireDateRule.THE_LAST_DAY_OF_THE_YEAR_INCLUDING_THE_DAY_OF_REACHING_RETIREMENT_AGE) {
			Optional<YearStartEnd> yearStartEnd = iGetYearStartEndDateByDate.getYearStartEndDateByDate(companyId, GeneralDate.today());
			if(!yearStartEnd.isPresent()) {
				throw new BusinessException("MsgJ_JMM018_3");
			}else {
				yearEndMonthDate = Optional.of(yearStartEnd.get().getYearEndYMD());
			}
		}else if(retireDateTerm.getRetireDateTerm() == RetireDateRule.FIRST_WAGE_CLOSING_DATE_AFTER_THE_DATE_OF_RETIREMENT_AGE) {
			
			//chỗ này đang gọi requestList 641
			// nhưng hiện tại requestList 641 đang viết trong context pr nên cần xác nhận lại 
			// đã xác nhận với bác sato lần này không phải làm
			
			if(closingDate.isEmpty()) {
				throw new BusinessException("MsgJ_JMM018_4");
			}
		}else if(retireDateTerm.getRetireDateTerm() == RetireDateRule.FIRST_DUE_DATE_AFTER_REACHING_RETIREMENT_AGE) {
			attendanceDate.addAll(syEmploymentService.getClosureDate(companyId));
			if(attendanceDate.isEmpty()) {
				throw new BusinessException("MsgJ_JMM018_5");
			}
		}
		//List<定年退職コース>{定年退職コースID、定年退職コース区分、定年退職コース名、定年年齢、継続区分}
		List<RetirePlanCource> retirePlanCourceList = retirePlanCourceService.getAllRetirePlanCource(companyId);
		if(retirePlanCourceList.isEmpty()) {
			throw new BusinessException("MsgJ_JMM018_6");
		}
		//List<雇用情報> = {雇用コード、グループ会社共通マスタ項目ID}
		List<EmploymentInfoImport> employmentInfoImportList = syEmploymentService.getEmploymentInfo(companyId, Optional.of(false), Optional.of(false), Optional.of(false), Optional.of(false), Optional.of(true)); 
		if(employmentInfoImportList.isEmpty()) {
			throw new BusinessException("MsgJ_JMM018_9");
		}
		
		for (EmploymentInfoImport item : employmentInfoImportList) {
			if(StringUtil.isNullOrEmpty(item.empCommonMasterItemId, true)){
				throw new BusinessException("MsgJ_JMM018_10");
			}
		}
		
		//List<定年条件> = {雇用コード、定年年齢、定年退職コース区分}
		List<RetireTermDto> retireTermList = new ArrayList<>();
		for (RetirementCourseInformationIdDto item : retirementCourseInformation) {
			Optional<RetirePlanCource> retirePlanCource = retirePlanCourceList.stream().filter(c->c.getRetirePlanCourseId()==item.getRetirePlanCourseId()).findFirst();
			if(retirePlanCource.isPresent()) {
				List<EmploymentInfoImport> employmentInfoImportSubList = employmentInfoImportList.stream().filter(c->c.empCommonMasterItemId.equals(item.getEmpCommonMasterItemId())).collect(Collectors.toList());
				for (EmploymentInfoImport employmentInfoImport : employmentInfoImportSubList) {
					retireTermList.add(new RetireTermDto(
							employmentInfoImport.getEmploymentCode(), 
							retirePlanCource.get().getRetirementAge(), 
							retirePlanCource.get().getRetirePlanCourseClass()));
				}
			}
		}
		
		if(retirementAge.isPresent()) {
			retireTermList.removeIf(c-> !(c.getRetirementAge().v() == retirementAge.get().v()));
			if(retireTermList.isEmpty()) {
				return new ArrayList<>();
			}else {
				if(!employmentCode.isEmpty()) {
					retireTermList.removeIf(c->!employmentCode.contains(c.getEmploymentCode()));
					if(retireTermList.isEmpty()) {
						return new ArrayList<>();
					}
				}
			}
		}else {
			retireTermList.removeIf(c-> !(c.getRetirePlanCourseClass() == RetirePlanCourseClass.STANDARD_COURSE));
			if(!employmentCode.isEmpty()) {
				retireTermList.removeIf(c->!employmentCode.contains(c.getEmploymentCode()));
				if(retireTermList.isEmpty()) {
					return new ArrayList<>();
				}
			}
		}
		
		List<SearchCondition> searchConditionList = new ArrayList<>();
		if(reachedAgeTerm == ReachedAgeTerm.THE_DAY_BEFORE_THE_BIRTHDAY) {
			searchConditionList.addAll(retireTermList.stream().map(c-> new SearchCondition(c.getEmploymentCode(), new DatePeriod(startDate.addYears(c.getRetirementAge().v()*-1).addDays(-1), endDate.addYears(c.getRetirementAge().v()*-1).addDays(-1)))).collect(Collectors.toList()));
		}else {
			searchConditionList.addAll(retireTermList.stream().map(c -> {
				GeneralDate s = GeneralDate.ymd(startDate.year(), startDate.month(), startDate.day());
				GeneralDate e = GeneralDate.ymd(endDate.year(), endDate.month(), endDate.day());
				if (startDate.month() == 2 && startDate.day() == 29
						&& GeneralDate.ymd(startDate.year() + c.getRetirementAge().v(), 2, 1).lastDateInMonth() != 29) {
					s = GeneralDate.ymd(startDate.year() - c.getRetirementAge().v(), startDate.month(), 28);
				} else {
					s = s.addYears(c.getRetirementAge().v() * -1);
				}
				if (endDate.month() == 2 && endDate.day() == 29
						&& GeneralDate.ymd(endDate.year() + c.getRetirementAge().v(), 2, 1).lastDateInMonth() != 29) {
					e = GeneralDate.ymd(endDate.year() - c.getRetirementAge().v(), endDate.month(), 28);
				} else {
					e = e.addYears(c.getRetirementAge().v() * -1);
				}
				return new SearchCondition(c.getEmploymentCode(), new DatePeriod(s, e));
			}).collect(Collectors.toList()));
		}
		//List<社員>{個人ID、社員ID、雇用コード、誕生日、入社日}
		List<EmployeeBasicInfoImport> employeeList = syEmploymentService.getEmploymentBasicInfo(searchConditionList, GeneralDate.today(), companyId);
		if(employeeList.isEmpty()) {
			return new ArrayList<>();
		}
		List<String> employeeIds = employeeList.stream().map(c->c.getSid()).collect(Collectors.toList());
		//List<社員情報>{社員ID、社員コード、ビジネスネーム、ビジネスネームカナ、部門ID、部門コード、部門名、職位ID、職位コード、職位名、雇用コード、雇用名}
		List<EmployeeInformationImport> employeeInfor = syEmploymentService.getEmployeeInfor(employeeIds, endDate, false, true, true, true, false, false);
		
		//List<定年退職者情報> = {個人ID、社員ID、社員コード、ビジネスネーム、ビジネスネームカナ、誕生日、入社日、部門ID、部門コード、部門名、職位ID、職位コード、職位名、雇用コード、雇用名}
		List<RetirementInforDto> retirementInforList = new ArrayList<>();
		for (EmployeeInformationImport item : employeeInfor) {
			Optional<EmployeeBasicInfoImport> em = employeeList.stream().filter(c->c.getSid().equals(item.getEmployeeId())).findFirst();
			if(em.isPresent()) {
				retirementInforList.add(new RetirementInforDto(item, em.get().getDateJoinComp(), em.get().getBirthday(), em.get().pid));
			}
		}
		
		if(!departmentId.isEmpty()) {
			retirementInforList.removeIf(c->{
				return !departmentId.contains(c.getDepartmentId());
			});
			if(retirementInforList.isEmpty()) {
				return new ArrayList<>();
			}
		}
		//・List<定年退職者> = {List<定年退職者情報>．社員ID、List<定年退職者情報>．雇用コード、List<定年退職者情報>．誕生日、List<定年条件>．定年年齢}
		List<RetirePlanParam> retirementList = new ArrayList<>();
		
		for (RetirementInforDto item : retirementInforList) {
			Optional<RetireTermDto> retireTerm = retireTermList.stream().filter(c->c.getEmploymentCode().equals(item.getEmploymentCode())).findFirst();
			if(retireTerm.isPresent()) {
				retirementList.add(new RetirePlanParam(item.getEmployeeId(), item.getEmploymentCode(), item.getBirthday(), retireTerm.get().getRetirementAge().v()));
			}
		}
		//・List<退職日> = {社員ID、雇用コード、退職日}
		List<RetirementDateDto> retireDateBySidList = this.getRetireDateBySidList(retirementList, reachedAgeTerm, retireDateTerm, yearEndMonthDate, closingDate, attendanceDate);
		
		//List<社員年齢> = {List<定年退職者>．社員ID、年齢}
		List<EmployeeAgeDto> employeeAgeList = new ArrayList<>();
		
		if(reachedAgeTerm == ReachedAgeTerm.THE_DAY_BEFORE_THE_BIRTHDAY) {
			for (RetirementDateDto item : retireDateBySidList) {
				Optional<RetirePlanParam> d = retirementList.stream().filter(c->c.getEmployeeId().equals(item.getEmployeeId())).findFirst();
				if(d.isPresent()) {
					employeeAgeList.add(new EmployeeAgeDto(item.getEmployeeId(), item.getRetirementDate().year()-d.get().getBirthday().addDays(-1).year()));
				}
			}
		}else {
			for (RetirementDateDto item : retireDateBySidList) {
				Optional<RetirePlanParam> d = retirementList.stream().filter(c->c.getEmployeeId().equals(item.getEmployeeId())).findFirst();
				if(d.isPresent()) {
					employeeAgeList.add(new EmployeeAgeDto(item.getEmployeeId(), item.getRetirementDate().year()-d.get().getBirthday().year()));
				}
			}
		}
		
		List<EmployeeDateDto> date = retireDateBySidList.stream().map(c->new EmployeeDateDto(c.getEmployeeId(), c.getRetirementDate())).collect(Collectors.toList());
		//List<公開日> = {社員ID、公開日}
		List<EmployeeDateDto> employeeDateList = dateCaculationTermService.getDateBySidList(date, publicTerm);
		
		List<String> retiredEmployeeId = retirementList.stream().map(c->c.getEmployeeId()).collect(Collectors.toList());
		EvaluationInfoDto evaluationInfoBySidList = this.getEvaluationInfoBySidList(retiredEmployeeId, referEvaluationTerm);
		
		List<RetirementPlannedPersonDto> result = new ArrayList<>();
		
		for (RetirementInforDto item : retirementInforList) {
			//社員年齢
			Optional<EmployeeAgeDto> employeeAge = employeeAgeList.stream().filter(c->c.getEmployeeId().equals(item.getEmployeeId())).findFirst();
			//退職日
			Optional<RetirementDateDto> retireDateBySid = retireDateBySidList.stream().filter(c->c.getEmployeeId().equals(item.getEmployeeId())).findFirst();
			//公開日
			Optional<EmployeeDateDto> employeeDate = employeeDateList.stream().filter(c->c.getEmployeeId().equals(item.getEmployeeId())).findFirst();
			//人事評価
			Optional<ComprehensiveEvaluationDto> hrEvaluationList = evaluationInfoBySidList.getHrEvaluationList().stream().filter(c->c.getEmployeeID().equals(item.getEmployeeId())).findFirst();
			//健康状態
			Optional<ComprehensiveEvaluationDto> healthStatusList = evaluationInfoBySidList.getHealthStatusList().stream().filter(c->c.getEmployeeID().equals(item.getEmployeeId())).findFirst();
			//ストレスチェック
			Optional<ComprehensiveEvaluationDto> stressStatusList = evaluationInfoBySidList.getStressStatusList().stream().filter(c->c.getEmployeeID().equals(item.getEmployeeId())).findFirst();
			
			if(employeeAge.isPresent() && retireDateBySid.isPresent() && employeeDate.isPresent()) {
				RetirementPlannedPersonDto em = new RetirementPlannedPersonDto(
						item.getPid(), 
						item.getEmployeeId(), 
						item.getEmployeeCode(), 
						item.getBusinessName(), 
						item.getBusinessNameKana(), 
						item.getBirthday(), 
						item.getDateJoinComp(), 
						item.getDepartmentId(), 
						item.getDepartmentCode(), 
						item.getDepartmentName(), 
						item.getPositionId(), 
						item.getPositionCode(), 
						item.getPositionName(), 
						item.getEmploymentCode(), 
						item.getEmploymentName(), 
						employeeAge.get().getRetirementAge(), 
						retireDateBySid.get().getRetirementDate(), 
						employeeDate.get().getTargetDate(), 
						hrEvaluationList, 
						healthStatusList, 
						stressStatusList);
				result.add(em);
			}
		}
		
		return result;
	}

	@Override
	public List<RetirementDateDto> getRetireDateBySidList(List<RetirePlanParam> retirePlan, ReachedAgeTerm reachedAgeTerm,
			RetireDateTerm retireDateTerm, Optional<GeneralDate> endDate, List<EmploymentDateDto> closingDate,
			List<EmploymentDateDto> attendanceDate) {
		List<RetirementDateDto> result = new ArrayList<>();
		if(reachedAgeTerm == ReachedAgeTerm.THE_DAY_BEFORE_THE_BIRTHDAY) {
			result.addAll(retirePlan.stream().map(c-> new RetirementDateDto(c.getEmployeeId(), c.getEmploymentCode(), c.getBirthday().addYears(c.getRetirementAge()).addDays(-1))).collect(Collectors.toList()));
		}else if(reachedAgeTerm == ReachedAgeTerm.ON_THE_DAY_OF_BIRTHDAY) {
			result.addAll(retirePlan.stream().map(c-> new RetirementDateDto(c.getEmployeeId(), c.getEmploymentCode(), c.getBirthday().addYears(c.getRetirementAge()))).collect(Collectors.toList()));
		}
		
		if(retireDateTerm.getRetireDateTerm() == RetireDateRule.THE_DAY_OF_REACHING_RETIREMENT_AGE) {
			return result;
		}else if(retireDateTerm.getRetireDateTerm() == RetireDateRule.RETIREMENT_DATE_DESIGNATED_DATE) {
			if(!retireDateTerm.getRetireDateSettingDate().isPresent()) {
				throw new BusinessException("MsgJ_JMM018_22");
			}
			return result.stream().map(c->{
					c.setDay(retireDateTerm.getRetireDateSettingDate().get().value);
					return c;
				}).collect(Collectors.toList());
		}else if(retireDateTerm.getRetireDateTerm() == RetireDateRule.THE_LAST_DAY_OF_THE_MONTH_INCLUDING_THE_DAY_OF_REACHING_RETIREMENT_AGE) {
			return result.stream().map(c->{
				c.setLastDateInMonth();
				return c;
			}).collect(Collectors.toList());
		}else if(retireDateTerm.getRetireDateTerm() == RetireDateRule.THE_LAST_DAY_OF_THE_YEAR_INCLUDING_THE_DAY_OF_REACHING_RETIREMENT_AGE && endDate.isPresent()) {
			return result.stream().map(c->{
				c.setRetirementDateByYear(endDate.get());
				return c;
			}).collect(Collectors.toList());
		}else if(retireDateTerm.getRetireDateTerm() == RetireDateRule.FIRST_WAGE_CLOSING_DATE_AFTER_THE_DATE_OF_RETIREMENT_AGE) {
			for (RetirementDateDto item : result) {
				Optional<EmploymentDateDto> employmentDate = closingDate.stream().parallel().filter(c->c.getEmploymentCode().equals(item.getEmploymentCode())).findAny();
				if(!employmentDate.isPresent()) {
					throw new BusinessException("MsgJ_JMM018_7");
				}
				item.setDay(employmentDate.get().getRetirementDate());
			}
			return result;
		}else if(retireDateTerm.getRetireDateTerm() == RetireDateRule.FIRST_DUE_DATE_AFTER_REACHING_RETIREMENT_AGE) {
			for (RetirementDateDto item : result) {
				Optional<EmploymentDateDto> employmentDate = attendanceDate.stream().parallel().filter(c->c.getEmploymentCode().equals(item.getEmploymentCode())).findAny();
				if(!employmentDate.isPresent()) {
					throw new BusinessException("MsgJ_JMM018_8");
				}
				item.setDay(employmentDate.get().getRetirementDate());
			}
			return result;
		}
		return result;
	}

	@Override
	public EvaluationInfoDto getEvaluationInfoBySidList(List<String> retiredEmployeeId,
			List<ReferEvaluationItem> evaluationReferInfo) {
		OutputObjectDto outputObject = new OutputObjectDto();
		for (ReferEvaluationItem item : evaluationReferInfo) {
			if(item.getEvaluationItem() == EvaluationItem.PERSONNEL_ASSESSMENT) {
				outputObject.setHrEvaluationRefer(item.isUsageFlg());
				outputObject.setHrEvaluationDispNumber(item.getDisplayNum());
			}else if(item.getEvaluationItem() == EvaluationItem.HEALTH_CONDITION) {
				outputObject.setHealthStatusRefer(item.isUsageFlg());
				outputObject.setHealthStatusDispNumber(item.getDisplayNum());
			}else if(item.getEvaluationItem() == EvaluationItem.STRESS_CHECK) {
				outputObject.setStressStatusRefer(item.isUsageFlg());
				outputObject.setStressStatusDispNumber(item.getDisplayNum());
			}
		}
		List<ComprehensiveEvaluationDto> hrEvaluationList = new ArrayList<>();
		if(outputObject.isHrEvaluationRefer()) {
			HumanResourceEvaluation HREvaluation = humanResourceEvaluationService.loadHRevaluation(retiredEmployeeId, GeneralDate.today().addYears(-1 * (outputObject.getHrEvaluationDispNumber() +1)));
			Map<String, List<PersonnelAssessmentResults>> mapSid = HREvaluation.getPersonnelAssessmentsResult().stream().collect(Collectors.groupingBy(c -> c.getEmployeeID())); 
			for(String id: retiredEmployeeId) {
				List<PersonnelAssessmentResults> personList = mapSid.get(id);
				if(personList == null || personList.isEmpty()) {
					hrEvaluationList.add(new ComprehensiveEvaluationDto(id, "", "", ""));
				}else {
					List<PersonnelAssessmentResults> personListSort = personList.stream().sorted((x, y) -> y.getStartDate().compareTo(x.getStartDate())).collect(Collectors.toList());
					
					ComprehensiveEvaluationDto dto = new ComprehensiveEvaluationDto(id, personListSort.get(0).getEvaluation(), "", "");
					if(outputObject.getHrEvaluationDispNumber() >= 2 && personListSort.size() >= 2) {
						dto.setOverallResult2(personListSort.get(1).getEvaluation());
					} 
					if(outputObject.getHrEvaluationDispNumber() >= 3 && personListSort.size() >= 3) {
						dto.setOverallResult3(personListSort.get(2).getEvaluation());
					}
					hrEvaluationList.add(dto);
				}
			}
		}
		List<ComprehensiveEvaluationDto> healthStatusList = new ArrayList<>();
		if(outputObject.isHealthStatusRefer()) {
			MedicalhistoryManagement medicalhistoryManagement = medicalhistoryServices.loadMedicalhistoryItem(retiredEmployeeId, GeneralDate.today().addYears(-1 * (outputObject.getHealthStatusDispNumber() +1)));
			Map<String, List<MedicalhistoryItemResults>> mapSid = medicalhistoryManagement.getMedicalhistoryItemResults().stream().collect(Collectors.groupingBy(c -> c.getEmployeeID())); 
			for(String id: retiredEmployeeId) {
				List<MedicalhistoryItemResults> personList = mapSid.get(id);
				if(personList == null || personList.isEmpty()) {
					healthStatusList.add(new ComprehensiveEvaluationDto(id, "", "", ""));
				}else {
					List<MedicalhistoryItemResults> personListSort = personList.stream().sorted((x, y) -> y.getStartDate().compareTo(x.getStartDate())).collect(Collectors.toList());
					
					ComprehensiveEvaluationDto dto = new ComprehensiveEvaluationDto(id, personListSort.get(0).getEvaluation(), "", "");
					if(outputObject.getHealthStatusDispNumber() >= 2 && personListSort.size() >= 2) {
						dto.setOverallResult2(personListSort.get(1).getEvaluation());
					} 
					if(outputObject.getHealthStatusDispNumber() >= 3 && personListSort.size() >= 3) {
						dto.setOverallResult3(personListSort.get(2).getEvaluation());
					}
					healthStatusList.add(dto);
				}
			}
		}
		List<ComprehensiveEvaluationDto> stressStatusList = new ArrayList<>();
		if(outputObject.isStressStatusRefer()) {
			StressCheckManagement stressCheckManagement = stressCheckService.loadStressCheck(retiredEmployeeId, GeneralDate.today().addYears(-1 * (outputObject.getStressStatusDispNumber() +1)));
			Map<String, List<StressCheckResults>> mapSid = stressCheckManagement.getStressChecks().stream().collect(Collectors.groupingBy(c -> c.getEmployeeID())); 
			for(String id: retiredEmployeeId) {
				List<StressCheckResults> personList = mapSid.get(id);
				if(personList == null || personList.isEmpty()) {
					stressStatusList.add(new ComprehensiveEvaluationDto(id, "", "", ""));
				}else {
					List<StressCheckResults> personListSort = personList.stream().sorted((x, y) -> y.getStartDate().compareTo(x.getStartDate())).collect(Collectors.toList());
					
					ComprehensiveEvaluationDto dto = new ComprehensiveEvaluationDto(id, personListSort.get(0).getEvaluation(), "", "");
					if(outputObject.getStressStatusDispNumber() >= 2 && personListSort.size() >= 2) {
						dto.setOverallResult2(personListSort.get(1).getEvaluation());
					} 
					if(outputObject.getStressStatusDispNumber() >= 3 && personListSort.size() >= 3) {
						dto.setOverallResult3(personListSort.get(2).getEvaluation());
					}
					stressStatusList.add(dto);
				}
			}
			
		}
		return new EvaluationInfoDto(hrEvaluationList, healthStatusList, stressStatusList);
		
	}

}
