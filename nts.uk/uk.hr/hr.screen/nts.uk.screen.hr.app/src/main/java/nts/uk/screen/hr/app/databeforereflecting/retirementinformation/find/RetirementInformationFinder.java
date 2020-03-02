package nts.uk.screen.hr.app.databeforereflecting.retirementinformation.find;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetireTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetirementRegulation;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetireDateTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetirePlanCource;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.dto.RetirementPlannedPersonDto;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.mandatoryRetirementRegulation.MandatoryRetirementRegulationService;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.retirePlanCource.RetirePlanCourceService;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.primitiveValue.RetirementAge;
import nts.uk.ctx.hr.develop.dom.empregulationhistory.algorithm.EmploymentRegulationHistoryInterface;
import nts.uk.ctx.hr.develop.dom.interview.InterviewCategory;
import nts.uk.ctx.hr.develop.dom.interview.service.IInterviewRecordSummary;
import nts.uk.ctx.hr.develop.dom.interview.service.InterviewSummary;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.service.IGetDatePeriod;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInformationImport;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.retirementinformation.RetirementInformation_New;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.retirementinformation.RetirementInformation_NewService;
import nts.uk.ctx.hr.shared.dom.employee.EmployeeInformationAdaptor;
import nts.uk.ctx.hr.shared.dom.employment.EmploymentInfoImport;
import nts.uk.ctx.hr.shared.dom.employment.SyEmploymentAdaptor;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author sonlb
 *
 */
@Stateless
public class RetirementInformationFinder {

	@Inject
	private EmploymentRegulationHistoryInterface empHis;

	@Inject
	private MandatoryRetirementRegulationService madaRepo;

	@Inject
	private RetirePlanCourceService retiRepo;

	@Inject
	private SyEmploymentAdaptor sysEmp;

	@Inject
	private IGetDatePeriod getDate;

	@Inject
	private RetirementInformation_NewService retiInfoService;

	@Inject
	private IInterviewRecordSummary interviewSum;

	@Inject
	private EmployeeInformationAdaptor empInfoAdaptor;

	/**
	 * 1.起動する(Khởi động)
	 */
	public RetiDateDto startPage() {

		// アルゴリズム[定年退職設定の取得]を実行する(thực hiện thuật toán [lấy setting nghỉ hưu])
		List<RetirementCourseDto> RetirementCourseDtos = getRetirementAgeSetting();

		// アルゴリズム[定年退職期間設定の取得]を実行する(thực hiện thuật toán [lấy setting khoảng
		// thời gian nghỉ hưu])
		DateDisplaySettingPeriodDto dateDisplaySettingPeriodDto = getRetirementPeriod();

		// アルゴリズム[参考情報表示設定の取得]を実行する(thực hiện thuật toán [lấy setting hiển thị
		// thông tin tham khảo])
		List<ReferEvaluationItemDto> referEvaluationItems = GetEmpRegulationHistoryIDByBaseDate();

		return new RetiDateDto(dateDisplaySettingPeriodDto, RetirementCourseDtos, referEvaluationItems);
	}

	/**
	 * 2.定年退職対象者を検索する(Search đối tượng nghỉ hưu)
	 */
	public SearchRetiredResultDto searchRetiredEmployees(SearchRetiredEmployeesQuery query) {

		// アルゴリズム[検索事前チェック]を実行する(Thực hiện thuật toan [check pre-search ])
		checkPreSearch(query);

		// アルゴリズム[検索前警告チェック]を実行する(Thực hiện thuật toán [check cảnh báo trước khi
		// search])

		if (!query.isConfirmCheckRetirementPeriod()) {
			preSearchWarning(query.getEndDate());
		}
		// アルゴリズム[定年退職者一覧の取得]を実行する(Thực hiện thuật toán [lấy danh sách người
		// nghỉ hưu])
		SearchRetiredResultDto result = getListRetiredEmployees(query);
		// アルゴリズム[社員情報リストを取得]を実行する

		List<String> sIDs = result.getRetiredEmployees().stream().map(x -> x.getSId()).collect(Collectors.toList());

		List<EmployeeInformationImport> employeeImports = this.empInfoAdaptor.getEmployeeInfos(
				Optional.ofNullable(null), sIDs, GeneralDate.today(), Optional.ofNullable(true),
				Optional.ofNullable(true), Optional.ofNullable(true));

		result.setEmployeeImports(employeeImports);

		return result;
	}

	private SearchRetiredResultDto getListRetiredEmployees(SearchRetiredEmployeesQuery query) {

		String cId = AppContexts.user().companyId();

		// アルゴリズム[定年退職予定者の取得]を実行する(Thực hiện thuật toán [lấy người dự định nghỉ
		// hưu])
		List<RetirementPlannedPersonDto> retiplandeds = this.madaRepo
				.getMandatoryRetirementListByPeriodDepartmentEmployment(cId, query.getStartDate(), query.getEndDate(),
						Optional.ofNullable(
								query.getRetirementAge() != null ? new RetirementAge(query.getRetirementAge()) : null),
						query.getSelectDepartment(), query.getSelectEmployment());

		List<PlannedRetirementDto> retiredEmployees = retiplandeds.stream().map(x -> toPlannedRetirementDto(x))
				.collect(Collectors.toList());

		

		// アルゴリズム[定年退職者情報の取得]を実行する(thực hiện thuật toán [lấy RetirementInfo])
		// mặc định set includingReflected = true để lấy hết ra giá trị
		List<RetirementInformation_New> retirementEmployees = this.retiInfoService.getRetirementInformation(cId,
				retiredEmployees.stream().map(x -> x.getSId()).collect(Collectors.toList()), Optional.ofNullable(true));

		// アルゴリズム[定年退職者一覧の表示]を実行する(thực hiện thuật toán [hiển thị danh sách
		// người nghỉ hưu])

		return createListRetiredEmployees(retirementEmployees, retiredEmployees, query.isIncludingReflected());
	}

	private PlannedRetirementDto toPlannedRetirementDto(RetirementPlannedPersonDto plan) {
		return PlannedRetirementDto.builder().pId(plan.getPersonalId()).sId(plan.getEmployeeId())
				.scd(plan.getEmployeeCode()).businessName(plan.getBusinessName())
				.businessnameKana(plan.getBusinessNameKana()).birthday(plan.getBirthday())
				.dateJoinComp(plan.getDateJoinComp()).departmentId(plan.getDepartmentId())
				.departmentCode(plan.getDepartmentCode()).departmentName(plan.getDepartmentName())
				.jobTitleId(plan.getPositionId()).jobTitleCd(plan.getPositionCode())
				.jobTitleName(plan.getPositionName()).employmentCode(plan.getEmploymentCode())
				.employmentName(plan.getEmploymentName()).retirementAge(plan.getAge())
				.retirementDate(plan.getRetirementDate()).releaseDate(plan.getReleaseDate())
				.hrEvaluation1(plan.getHrEvaluation1().isPresent() ? plan.getHrEvaluation1().get() : null)
				.hrEvaluation2(plan.getHrEvaluation2().isPresent() ? plan.getHrEvaluation2().get() : null)
				.hrEvaluation3(plan.getHrEvaluation3().isPresent() ? plan.getHrEvaluation3().get() : null)
				.healthStatus1(plan.getHealthAssessment1().isPresent() ? plan.getHealthAssessment1().get() : null)
				.healthStatus2(plan.getHealthAssessment2().isPresent() ? plan.getHealthAssessment2().get() : null)
				.healthStatus3(plan.getHealthAssessment3().isPresent() ? plan.getHealthAssessment3().get() : null)
				.stressStatus1(plan.getStressAssessment1().isPresent() ? plan.getStressAssessment1().get() : null)
				.stressStatus2(plan.getStressAssessment2().isPresent() ? plan.getStressAssessment2().get() : null)
				.stressStatus3(plan.getStressAssessment3().isPresent() ? plan.getStressAssessment3().get() : null)
				.build();
	}

	private SearchRetiredResultDto createListRetiredEmployees(List<RetirementInformation_New> retirementEmployees,
			List<PlannedRetirementDto> retiredEmployees, boolean isIncludingReflected) {

		List<PlannedRetirementDto> result = new ArrayList<PlannedRetirementDto>();

		retiredEmployees = retiredEmployees.stream().sorted(Comparator
				.comparing(PlannedRetirementDto::getRetirementDate).thenComparing(PlannedRetirementDto::getScd))
				.collect(Collectors.toList());

		result.addAll(retiredEmployees);
		// 定年退職予定者リストと定年退職者情報リストをマージする(Merger list người dự định nghỉ hưu và
		// list thông tin người nghỉ hưu)
		retiredEmployees.forEach(item -> {
			retirementEmployees.stream().filter(retirement -> retirement.getSId().equals(item.getSId())).findFirst()
					.ifPresent(retirement -> {
						setRetiredInfo(item, retirement);
					});

		});
		// アルゴリズム[面談記録チェック]を実行する(Thực hiện thuật toán [check báo cáo kết quả
		// phỏng vấn])

		// check includingReflected nếu không =true thì lọc re những giá trị có
		// status =3
		if (!isIncludingReflected) {
			result = result.stream().filter(x -> x.getStatus() == null || x.getStatus() != 3)
					.collect(Collectors.toList());
		}
		
		if (result.size() > 2000) {
			throw new BusinessException("MsgJ_JCM008_8");
		}

		if (result.size() == 0) {
			throw new BusinessException("MsgJ_JCM008_2");
		}

		List<String> employeeIds = result.stream().map(x -> x.getSId()).collect(Collectors.toList());

		String cId = AppContexts.user().companyId();
		InterviewSummary interView = this.interviewSum.getInterviewInfo(cId, InterviewCategory.RETIREMENT_AGE.value,
				employeeIds, false, true, true, true);

		return SearchRetiredResultDto.builder().retiredEmployees(result).interView(interView).build();

	}

	private void setRetiredInfo(PlannedRetirementDto item, RetirementInformation_New reti) {
		item.setHistoryId(reti.getHistoryId());
		item.setStatus(reti.getStatus().value);
		item.setDesiredWorkingCourseCd(reti.getDesiredWorkingCourseCd());
		item.setExtendEmploymentFlg(reti.getExtendEmploymentFlg().value);
		item.setCompanyId(reti.getCompanyId());
		item.setWorkName(reti.getWorkName());
		item.setDesiredWorkingCourseId(reti.getDesiredWorkingCourseId());
		item.setCompanyCode(reti.getCompanyCode());
		item.setPersonName(reti.getPersonName());
		item.setDesiredWorkingCourseName(reti.getDesiredWorkingCourseName());
		item.setContractCode(reti.getContractCode());
		item.setPendingFlag(reti.getPendingFlag().value);
		item.setWorkId(reti.getWorkId());
		item.setDst_HistId(reti.getDst_HistId());
		item.setInputDate(reti.getInputDate());
		item.setRetirementCategory(reti.getRetirementCategory().value);
		item.setNotificationCategory(reti.getNotificationCategory().value);
		item.setRetirementReasonCtgID1(reti.getRetirementReasonCtgID1());
		item.setRetirementReasonCtgCd1(reti.getRetirementReasonCtgCd1());
		item.setRetirementReasonCtgName1(reti.getRetirementReasonCtgName1());
	}

	private void preSearchWarning(GeneralDate endDate) {

		if (!endDate.before(GeneralDate.today().addYears(2))) {
			throw new BusinessException("MsgJ_JCM008_5");
		}
	}

	private void checkPreSearch(SearchRetiredEmployeesQuery query) {
		BundledBusinessException bundleExeption = BundledBusinessException.newInstance();
		// 定年退職年齢の指定チェック(chek setting cua RetirementAge)
		if (query.isRetirementAgeSetting() && query.getRetirementAge() == null) {
			bundleExeption.addMessage(new BusinessException("MsgJ_JCM008_7"));
		}
		// 部門チェック(check department)
		if (!query.isAllSelectDepartment() && query.getSelectDepartment().isEmpty()) {
			bundleExeption.addMessage(new BusinessException("MsgJ_JCM008_3"));
		}
		// 雇用チェック(check employment)
		if (!query.isAllSelectEmployment() && query.getSelectEmployment().isEmpty()) {
			bundleExeption.addMessage(new BusinessException("MsgJ_JCM008_4"));
		}
		if (!bundleExeption.getMessageId().isEmpty()) {
			throw bundleExeption;
		}
	}

	private List<ReferEvaluationItemDto> GetEmpRegulationHistoryIDByBaseDate() {
		String cId = AppContexts.user().companyId();
		return this.madaRepo.getReferEvaluationItemByDate(cId, GeneralDate.today()).stream()
				.map(x -> new ReferEvaluationItemDto(x)).collect(Collectors.toList());
	}

	private DateDisplaySettingPeriodDto getRetirementPeriod() {
		String cId = AppContexts.user().companyId();
		// アルゴリズム[期間開始日、期間終了日の取得]を実行する(thực hiện thuật toán [lấy
		// PeriodStartDate, PeriodEndDate])
		return new DateDisplaySettingPeriodDto(this.getDate.getDatePeriod(cId, "JCM008"));
	}

	private List<RetirementCourseDto> getRetirementAgeSetting() {
		String cId = AppContexts.user().companyId();
		GeneralDate baseDate = GeneralDate.today();
		// アルゴリズム[基準日で選択されている定年退職コースの取得]を実行する(Thực hiện thuật toán [lấy
		// RetirementCourse đang chọn ở BaseDate])
		List<RetirementCourseDto> retirementCourses = getRetirementCourse(cId, baseDate);

		if (retirementCourses.isEmpty()) {
			throw new BusinessException("MsgJ_JCM008_1");
		}

		// アルゴリズム[定年退職期間設定の取得]を実行する(thực hiện thuật toán [lấy setting khoảng
		// thời gian nghỉ hưu])

		return retirementCourses;
	}

	private List<RetirementCourseDto> getRetirementCourse(String cId, GeneralDate baseDate) {
		// アルゴリズム [基準日から就業規則の履歴IDの取得] を実行する((Thực hiện thuật toán[Lấy
		// EmploymentRegulationHistory ID từ baseDate])
		Optional<String> hisIdOpt = this.empHis.getHistoryIdByDate(cId, baseDate);

		if (!hisIdOpt.isPresent()) {
			throw new BusinessException("MsgJ_JMM018_2");
		}

		// ドメインモデル [定年退職の就業規則] を取得する (Lấy domain
		// [MandatoryRetirementRegulation])
		Optional<MandatoryRetirementRegulation> madaOpt = this.madaRepo.getMandatoryRetirementRegulation(cId,
				hisIdOpt.get());
		if (!madaOpt.isPresent()) {
			throw new BusinessException("MsgJ_JMM018_2");
		}

		// アルゴリズム [全ての定年退職コースの取得] を実行する (Thực hiện thuật toán "Lấy tất cả
		// retirePlanCourse")

		List<RetirePlanCource> retires = this.retiRepo.getRetirePlanCourse(cId);

		if (retires.isEmpty()) {
			throw new BusinessException("MsgJ_JMM018_6");
		}

		// アルゴリズム [会社ID、取得したい情報パラメータから雇用情報を取得する] を実行する (Thực hiện thuật toán
		// "(Lấy domain [Lấy thông tin employment từ companyID và info param
		// muốn lấy)"
		List<EmploymentInfoImport> empInfos = this.sysEmp.getEmploymentInfo(cId, Optional.of(true), Optional.of(false),
				Optional.of(false), Optional.of(false), Optional.of(true));

		if (empInfos.isEmpty()) {
			throw new BusinessException("MsgJ_JMM018_9");
		}

		// 雇用情報リストを確認する (Xác nhận Employment information list)
		empInfos.forEach(x -> {
			if (StringUtil.isNullOrEmpty(x.getEmpCommonMasterItemId(), false)) {
				throw new BusinessException("MsgJ_JMM018_10");
			}
		});

		// 定年退職コース情報リストを生成する (Tao retirePlanCourseTermList)

		List<RetirementCourseDto> retirePlanCourses = new ArrayList<RetirementCourseDto>();
		
		empInfos.forEach(emp -> {

			retirePlanCourses.addAll(
					getRetirePlanCoursesData(madaOpt.get(), madaOpt.get().getMandatoryRetireTerm(), emp, retires));
		});

		return retirePlanCourses;
	}

	private List<RetirementCourseDto> getRetirePlanCoursesData(MandatoryRetirementRegulation mada,
			List<MandatoryRetireTerm> retireTerms, EmploymentInfoImport emp, List<RetirePlanCource> retires) {

		List<RetirementCourseDto> dtos = new ArrayList<RetirementCourseDto>();
		
		Optional<MandatoryRetireTerm> retireTermOpt = retireTerms.stream().filter(
				reti -> reti.getEmpCommonMasterItemId().equals(emp.getEmpCommonMasterItemId()) && reti.isUsageFlg())
				.findFirst();
		
		if(!retireTermOpt.isPresent()){
			return Collections.emptyList();
		}
		
		MandatoryRetireTerm  retireTerm = retireTermOpt.get();

		String employmentCode = emp.getEmploymentCode();
		String employmentName = emp.getEmploymentName();

		retireTerm.getEnableRetirePlanCourse().forEach(x -> {

			RetirementCourseDto dto = RetirementCourseDto.builder().employmentCode(employmentCode)
					.employmentName(employmentName).retireDateBase(jugDateBase(mada.getRetireDateTerm()))
					.retireDateTerm(new RetireDateTermDto(mada.getRetireDateTerm())).build();

			Optional<RetirePlanCource> retire = retires.stream()
					.filter(re -> re.getRetirePlanCourseId() == x.getRetirePlanCourseId()).findFirst();

			if (retire.isPresent() && employmentCode != null && employmentName != null) {
				dto.setRetirePlanCourseClass(retire.get().getRetirePlanCourseClass().value);
				dto.setRetirementAge(retire.get().getRetirementAge().v());
				dto.setRetirePlanCourseId(retire.get().getRetirePlanCourseId());
				dto.setRetirePlanCourseCode(retire.get().getRetirePlanCourseCode());
				dto.setRetirePlanCourseName(retire.get().getRetirePlanCourseName());
				dto.setDurationFlg(retire.get().getDurationFlg().value);
				dtos.add(dto);
			}

		});

		return dtos;
	}

	private String jugDateBase(RetireDateTerm retireDateTerm) {

		String result = null;

		if (retireDateTerm == null) {
			return result;
		}
		switch (retireDateTerm.getRetireDateTerm()) {

		case RETIREMENT_DATE_DESIGNATED_DATE:
			result = retireDateTerm.getRetireDateSettingDate().isPresent()
					? retireDateTerm.getRetireDateSettingDate().get().nameId : null;
			break;

		default:
			result = retireDateTerm.getRetireDateTerm().nameId;
			break;
		}

		return result;
	}
}
