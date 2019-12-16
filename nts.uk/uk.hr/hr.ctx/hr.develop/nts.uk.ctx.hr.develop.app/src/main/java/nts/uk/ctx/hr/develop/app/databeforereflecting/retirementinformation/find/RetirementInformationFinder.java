package nts.uk.ctx.hr.develop.app.databeforereflecting.retirementinformation.find;

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
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetirePlanCource;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.mandatoryRetirementRegulation.MandatoryRetirementRegulationService;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.retirePlanCource.RetirePlanCourceService;
import nts.uk.ctx.hr.develop.dom.databeforereflecting.retirementinformation.RetirementInformation;
import nts.uk.ctx.hr.develop.dom.databeforereflecting.retirementinformation.service.RetirementInformationService;
import nts.uk.ctx.hr.develop.dom.empregulationhistory.algorithm.EmploymentRegulationHistoryInterface;
import nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.service.IGetYearStartEndDateByDate;
import nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.service.YearStartEnd;
import nts.uk.ctx.hr.develop.dom.interview.InterviewCategory;
import nts.uk.ctx.hr.develop.dom.interview.service.IInterviewRecordSummary;
import nts.uk.ctx.hr.develop.dom.interview.service.InterviewSummary;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.service.IGetDatePeriod;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInfoQueryImport;
import nts.uk.ctx.hr.shared.dom.adapter.EmployeeInformationImport;
import nts.uk.ctx.hr.shared.dom.employee.EmployeeInformationAdaptor;
import nts.uk.ctx.hr.shared.dom.employment.EmployeeBasicInfoImport;
import nts.uk.ctx.hr.shared.dom.employment.EmploymentInfoImport;
import nts.uk.ctx.hr.shared.dom.employment.ObjectParam;
import nts.uk.ctx.hr.shared.dom.employment.SyEmploymentAdaptor;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RetirementInformationFinder {

	@Inject
	private EmploymentRegulationHistoryInterface empHis;

	@Inject
	private MandatoryRetirementRegulationService medaRepo;

	@Inject
	private RetirePlanCourceService retiRepo;

	@Inject
	private SyEmploymentAdaptor sysEmp;

	@Inject
	private IGetDatePeriod getDate;

	@Inject
	private EmploymentRegulationHistoryInterface iEmpHis;

	@Inject
	private IGetYearStartEndDateByDate iGetYearStartEndDateByDate;

	@Inject
	private RetirePlanCourceService retiService;

	@Inject
	private EmployeeInformationAdaptor empInforAdaptor;

	@Inject
	private RetirementInformationService retiInfoService;

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
	public SearchRetiredResult searchRetiredEmployees(SearchRetiredEmployeesQuery query) {

		// アルゴリズム[検索事前チェック]を実行する(Thực hiện thuật toan [check pre-search ])
		checkPreSearch(query);

		// アルゴリズム[検索前警告チェック]を実行する(Thực hiện thuật toán [check cảnh báo trước khi
		// search])

		if (!query.isConfirmCheckRetirementPeriod()) {
			preSearchWarning(query.getEndDate());
		}
		// アルゴリズム[定年退職者一覧の取得]を実行する(Thực hiện thuật toán [lấy danh sách người
		// nghỉ hưu])
		SearchRetiredResult result = getListRetiredEmployees(query);
		// アルゴリズム[社員情報リストを取得]を実行する xử lý này tách thành 1 API riêng
		
		List<String> sIDs = result.getRetiredEmployees().stream().map(x-> x.getSId()).collect(Collectors.toList());
		this.empInfoAdaptor.getEmployeeInfos(Optional.ofNullable(null), sIDs, GeneralDate.today());
		
		return result;
	}

	private SearchRetiredResult getListRetiredEmployees(SearchRetiredEmployeesQuery query) {

		String cId = AppContexts.user().companyId();
		GeneralDate baseDate = GeneralDate.today();

		// アルゴリズム[定年退職予定者の取得]を実行する(Thực hiện thuật toán [lấy người dự định nghỉ
		// hưu])
		List<PlannedRetirement> retiredEmployees = getRetiredEmployees(query, cId, baseDate);

		if (retiredEmployees.size() > 2000) {
			throw new BusinessException(" MsgJ_JCM008_8");
		}

		// アルゴリズム[定年退職者情報の取得]を実行する(thực hiện thuật toán [lấy RetirementInfo])
		List<RetirementInformation> retirementEmployees = this.retiInfoService.getRetirementInformation(cId,
				Optional.ofNullable(retiredEmployees.stream().map(x -> x.getSId()).collect(Collectors.toList())),
				Optional.ofNullable(null));

		// アルゴリズム[定年退職者一覧の表示]を実行する(thực hiện thuật toán [hiển thị danh sách
		// người nghỉ hưu])

		return createListRetiredEmployees(retirementEmployees, retiredEmployees);
	}

	private SearchRetiredResult createListRetiredEmployees(List<RetirementInformation> retirementEmployees,
			List<PlannedRetirement> retiredEmployees) {
		List<PlannedRetirement> result = new ArrayList<PlannedRetirement>();

		result.addAll(retiredEmployees);
		// 定年退職予定者リストと定年退職者情報リストをマージする(Merger list người dự định nghỉ hưu và
		// list thông tin người nghỉ hưu)
		retirementEmployees.forEach(item -> {
			Optional<PlannedRetirement> retiredOps = retiredEmployees.stream()
					.filter(retired -> retired.getSId().equals(item.getSId())).findFirst();

			if (!retiredOps.isPresent()) {
				result.add(toRetired(item));
			}
		});
		// アルゴリズム[面談記録チェック]を実行する(Thực hiện thuật toán [check báo cáo kết quả
		// phỏng vấn])

		List<String> employeeIds = result.stream().map(x -> x.getSId()).collect(Collectors.toList());
		String cId = AppContexts.user().companyId();
		InterviewSummary interView = this.interviewSum.getInterviewInfo(cId, InterviewCategory.RETIREMENT_AGE.value,
				employeeIds, false, true, true, true);
		return SearchRetiredResult.builder().retiredEmployees(result).interView(interView).build();

	}

	private PlannedRetirement toRetired(RetirementInformation item) {

		return PlannedRetirement.builder().build();
	}

	private List<PlannedRetirement> getRetiredEmployees(SearchRetiredEmployeesQuery query, String cId,
			GeneralDate baseDate) {

		String hisId = getRetiredEmployees(cId, baseDate);

		// ドメインモデル [定年退職の就業規則] を取得する(Lấy domain [MandatoryRetirementRegulation])
		MandatoryRetirementRegulation mada = getRetirementRules(cId, hisId, baseDate);

		// アルゴリズム [全ての定年退職コースの取得] を実行する (THực hiện thuật toán " Lấy tất cả
		// retirePlanCourse")
		List<RetirePlanCource> retires = getAllRetires(cId);

		// アルゴリズム [会社ID、取得したい情報パラメータから雇用情報を取得する] を実行する(THực hiện thuật toán [lấy
		// thông tin employment từ companyID parameter thông tin muốn lấy, ]

		List<EmploymentInfoImport> empInfos = getEmpInfo(cId);

		// 雇用情報リストを確認する(Xác nhận employment Infomation list)
		checkEmpInfos(empInfos);

		// 定年条件リストを生成する ( Tạo retireTerm List/List điều kiện về hưu)

		List<RetirementCourseDto> retirePlanCourses = createRetirePlanCourses(query, mada, empInfos, retires);

		if (retirePlanCourses.isEmpty()) {
			return Collections.emptyList();
		}

		// 検索条件リストを生成する ( Tạo list điều kiện tìm kiếm/Search condition list)
		List<ObjectParam> listObjParam = createSearchConditionList(query.getStartDate(), query.getEndDate(),
				retirePlanCourses);

		// アルゴリズム [基準日、検索条件リストから該当する在籍社員を取得する] を実行する ( Thực hiện thuật toán "Lấy

		List<EmployeeBasicInfoImport> empBasicInfos = this.sysEmp.getEmploymentBasicInfo(listObjParam, baseDate, cId);

		if (empBasicInfos.isEmpty()) {
			return Collections.emptyList();
		}
		// アルゴリズム [社員の情報を取得する] を実行する (Thực hiện thuật toán "Lấy thông tin nhân
		// viên")

		List<EmployeeInformationImport> empInfoImports = this.empInforAdaptor.find(EmployeeInfoQueryImport.builder()
				.employeeIds(empBasicInfos.stream().map(x -> x.getSid()).collect(Collectors.toList()))
				.referenceDate(query.getEndDate()).toGetWorkplace(false).toGetDepartment(true).toGetPosition(true)
				.toGetEmployment(true).toGetClassification(false).toGetEmploymentCls(false).build());

		// 定年退職者情報リストを生成する (Tạo retirePlanTermList)
		List<RetirementInformationDto> retireInfos = createRetirePlanTermList(empBasicInfos, empInfoImports);

		if (!query.getSelectDepartment().isEmpty()) {
			// 定年退職者情報リストを絞り込む (Filter retirePlanTermList)
			retireInfos = retireInfos.stream()
					.filter(x -> query.getSelectDepartment().indexOf(x.getDepartmentId()) != -1)
					.collect(Collectors.toList());

			if (retireInfos.isEmpty()) {
				return Collections.emptyList();
			}
		}

		// 定年退職者リストを生成する (Tạo retirePlanList)
		List<RetirePlanDto> retirePlans = createRetirePlanList(retireInfos, retirePlanCourses);
		// アルゴリズム [退職日の取得] を実行する (Thực hiện thuật toán [Lấy Retirement date])

		// 社員年齢リストを生成する (Tạo Employee age list)

		// アルゴリズム [算出日の取得] を実行する (Thực hiện thuật toán "Get CalculationDate")

		// アルゴリズム [評価情報の取得] を実行する (THực hiện thuật toán " Lấy thông tin đánh
		// giá")

		// 定年退職予定者リストを生成する (Tạo List of retired employees)

		return Collections.emptyList();
	}

	private List<RetirePlanDto> createRetirePlanList(List<RetirementInformationDto> retireInfos,
			List<RetirementCourseDto> retirePlanCourses) {

		List<RetirePlanDto> retirePlans = new ArrayList<RetirePlanDto>();

		retireInfos.forEach(x -> {
			retirePlanCourses.stream().filter(rp -> rp.getEmploymentCode().equals(x.getEmployeeCode())).findFirst()
					.ifPresent(rp -> {
						retirePlans.add(RetirePlanDto.builder().employeeId(x.getEmployeeId())
								.employmentCode(x.getEmployeeCode()).birthday(x.getBirthday())
								.retirementAge(rp.getRetirementAge()).build());
					});
		});
		return retirePlans;
	}

	private List<RetirementInformationDto> createRetirePlanTermList(List<EmployeeBasicInfoImport> empBasicInfos,
			List<EmployeeInformationImport> empInfoImports) {

		List<RetirementInformationDto> result = new ArrayList<RetirementInformationDto>();

		empBasicInfos.forEach(x -> {
			empInfoImports.stream().filter(ei -> ei.getEmployeeId().equals(x.getSid())).findFirst().ifPresent(ei -> {
				result.add(RetirementInformationDto.builder().employeeId(x.getSid()).employeeCode(x.getEmploymentCode())
						.businessName(ei.getBusinessName()).businessNameKana(ei.getBusinessNameKana())
						.birthday(x.getBirthday()).dateJoinComp(x.getDateJoinComp())
						.departmentId(ei.getDepartment() == null ? null : ei.getDepartment().getDepartmentId())
						.departmentCode(ei.getDepartment() == null ? null : ei.getDepartment().getDepartmentCode())
						.departmentName(ei.getDepartment() == null ? null : ei.getDepartment().getDepartmentName())
						.positionId(ei.getPosition() == null ? null : ei.getPosition().getPositionId())
						.positionCode(ei.getPosition() == null ? null : ei.getPosition().getPositionCode())
						.positionName(ei.getPosition() == null ? null : ei.getPosition().getPositionName())
						.employmentCode(ei.getEmployment() == null ? null : ei.getEmployment().getEmploymentCode())
						.employmentName(ei.getEmployment() == null ? null : ei.getEmployment().getEmploymentName())
						.build());
			});
			;
		});

		return result;
	}

	private List<ObjectParam> createSearchConditionList(GeneralDate generalDate, GeneralDate generalDate2,
			List<RetirementCourseDto> retirePlanCourses) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<RetirementCourseDto> createRetirePlanCourses(SearchRetiredEmployeesQuery query,
			MandatoryRetirementRegulation mada, List<EmploymentInfoImport> empInfos, List<RetirePlanCource> retires) {
		List<RetirementCourseDto> retireCourseTerm = new ArrayList<RetirementCourseDto>();

		mada.getMandatoryRetireTerm().forEach(x -> {

			retireCourseTerm.addAll(getRetirePlanCoursesData(mada, x, empInfos, retires));
		});

		List<RetirementCourseDto> retirePlanCourses = new ArrayList<RetirementCourseDto>();

		if (query.getRetirementAge().isEmpty()) {
			// 定年条件リストを絞り込む(Fillter retireTerrmList)
			retirePlanCourses = retireCourseTerm.stream().filter(x -> x.getRetirePlanCourseClass().equals(0))
					.collect(Collectors.toList());

			if (!query.getSelectEmployment().isEmpty()) {
				// 定年条件リストを絞り込む (Fillter retireTerrmList)
				retirePlanCourses = retireCourseTerm.stream()
						.filter(x -> query.getSelectEmployment().indexOf(x.getEmploymentCode()) != -1)
						.collect(Collectors.toList());
			}

		} else {
			// 定年条件リストを絞り込む(Fillter retireTerrmList)
			retirePlanCourses = retireCourseTerm.stream()
					.filter(x -> x.getRetirementAge().equals(query.getRetirementAge())).collect(Collectors.toList());

			if (retirePlanCourses.isEmpty()) {
				return Collections.emptyList();
			}

			if (!query.getSelectEmployment().isEmpty()) {
				// 定年条件リストを絞り込む (Filter RetireTermList)
				retirePlanCourses = retireCourseTerm.stream()
						.filter(x -> query.getSelectEmployment().indexOf(x.getEmploymentCode()) != -1)
						.collect(Collectors.toList());
			}

		}
		return retirePlanCourses;
	}

	private void checkEmpInfos(List<EmploymentInfoImport> empInfos) {
		empInfos.forEach(x -> {
			if (StringUtil.isNullOrEmpty(x.getEmpCommonMasterItemId(), false)) {
				throw new BusinessException(" MsgJ_JMM018_10");
			}
		});
	}

	private List<EmploymentInfoImport> getEmpInfo(String cId) {
		List<EmploymentInfoImport> empInfos = this.sysEmp.getEmploymentInfo(cId, Optional.of(false), Optional.of(false),
				Optional.of(false), Optional.of(false), Optional.of(true));

		if (empInfos.isEmpty()) {
			throw new BusinessException("MsgJ_JMM018_9");
		}
		return empInfos;
	}

	private List<RetirePlanCource> getAllRetires(String cId) {
		List<RetirePlanCource> retires = this.retiService.getAllRetirePlanCource(cId);

		if (retires.isEmpty()) {
			throw new BusinessException("MsgJ_JMM018_6");
		}

		return retires;
	}

	private MandatoryRetirementRegulation getRetirementRules(String cId, String hisId, GeneralDate baseDate) {
		MandatoryRetirementRegulation mada = this.medaRepo.getMandatoryRetirementRegulation(cId, hisId);
		if (mada == null) {
			throw new BusinessException("MsgJ_JMM018_2");
		}

		switch (mada.getRetireDateTerm().getRetireDateTerm().value) {
		case 0:
		case 1:
		case 2:

			break;
		case 3:
			checkYearMonthDate(cId, baseDate);
			break;
		case 4:
			checkSalaryClosingDate(cId);
			break;
		case 5:
			getClosingDate(cId);
			break;
		}

		return mada;
	}

	private String getRetiredEmployees(String cId, GeneralDate baseDate) {
		Optional<String> hisIdOpt = this.iEmpHis.getHistoryIdByDate(cId, baseDate);

		if (!hisIdOpt.isPresent()) {
			throw new BusinessException("MsgJ_JMM018_2");
		}

		return hisIdOpt.get();
	}

	private void getClosingDate(String cId) {
		// TODO Auto-generated method stub

	}

	private void checkSalaryClosingDate(String cId) {
		// アルゴリズム [給与雇用と締めのリストを取得する] を実行する (Thực hiện thuật toán [Get a list of
		// salary employment and closing])
		// TODO Auto-generated method stub
	}

	private void checkYearMonthDate(String cId, GeneralDate baseDate) {
		// アルゴリズム [基準日から年度開始年月日、年度終了年月日の取得] を実行する((thực hiện thuật toán[lấy
		// YearStartMonthDate, YearEndMonthDate từ BaseDate])
		YearStartEnd yearStartEnd = this.iGetYearStartEndDateByDate.getByDate(cId, baseDate);
		if (yearStartEnd == null) {
			throw new BusinessException("MsgJ_JMM018_3");
		}
	}

	private void preSearchWarning(GeneralDate endDate) {

		if (endDate.before(GeneralDate.today().addYears(2))) {
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
		if (query.isAllSelectDepartment() && query.getSelectDepartment().isEmpty()) {
			bundleExeption.addMessage(new BusinessException("MsgJ_JCM008_3"));
		}
		// 雇用チェック(check employment)
		if (query.isAllSelectEmployment() && query.getSelectEmployment().isEmpty()) {
			bundleExeption.addMessage(new BusinessException("MsgJ_JCM008_4"));
		}
		if (!bundleExeption.getMessageId().isEmpty()) {
			throw bundleExeption;
		}
	}

	private List<ReferEvaluationItemDto> GetEmpRegulationHistoryIDByBaseDate() {
		String cId = AppContexts.user().companyId();
		return this.medaRepo.getReferEvaluationItemByDate(cId, GeneralDate.today()).stream()
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

		return retirementCourses.stream().sorted(Comparator.comparing(RetirementCourseDto::getRetirePlanCourseCode))
				.collect(Collectors.toList());
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
		MandatoryRetirementRegulation mada = this.medaRepo.getMandatoryRetirementRegulation(cId, hisIdOpt.get());
		if (mada == null) {
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
			if (!StringUtil.isNullOrEmpty(x.getEmpCommonMasterItemId(), false)) {
				throw new BusinessException(" MsgJ_JMM018_10");
			}
		});

		// 定年退職コース情報リストを生成する (Tao retirePlanCourseTermList)

		List<RetirementCourseDto> retirePlanCourses = new ArrayList<RetirementCourseDto>();

		mada.getMandatoryRetireTerm().forEach(x -> {

			retirePlanCourses.addAll(getRetirePlanCoursesData(mada, x, empInfos, retires));
		});

		return retirePlanCourses;
	}

	private List<RetirementCourseDto> getRetirePlanCoursesData(MandatoryRetirementRegulation mada,
			MandatoryRetireTerm retireTerm, List<EmploymentInfoImport> empInfos, List<RetirePlanCource> retires) {

		List<RetirementCourseDto> dtos = new ArrayList<RetirementCourseDto>();

		Optional<EmploymentInfoImport> empInfo = empInfos.stream()
				.filter(emp -> emp.getEmpCommonMasterItemId().equals(retireTerm.getEmpCommonMasterItemId()))
				.findFirst();

		String employmentCode = empInfo.isPresent() ? empInfo.get().getEmploymentCode() : null;
		String employmentName = empInfo.isPresent() ? empInfo.get().getEmploymentName() : null;

		retireTerm.getEnableRetirePlanCourse().forEach(x -> {

			RetirementCourseDto dto = RetirementCourseDto.builder().employmentCode(employmentCode)
					.employmentName(employmentName).retireDateTerm(new RetireDateTermDto(mada.getRetireDateTerm()))
					.build();

			Optional<RetirePlanCource> retire = retires.stream()
					.filter(re -> re.getRetirePlanCourseId() == Long.parseLong(x.getRetirePlanCourseId())).findFirst();

			if (retire.isPresent()) {
				dto.setRetirePlanCourseClass(retire.get().getRetirePlanCourseClass().value);
				dto.setRetirementAge(retire.get().getRetirementAge().v());
				dto.setRetirePlanCourseId(retire.get().getRetirePlanCourseId());
				dto.setRetirePlanCourseCode(retire.get().getRetirePlanCourseCode());
				dto.setRetirePlanCourseName(retire.get().getRetirePlanCourseName());
				dto.setDurationFlg(retire.get().isDurationFlg());
			}

			dtos.add(dto);

		});

		return dtos;
	}
}
