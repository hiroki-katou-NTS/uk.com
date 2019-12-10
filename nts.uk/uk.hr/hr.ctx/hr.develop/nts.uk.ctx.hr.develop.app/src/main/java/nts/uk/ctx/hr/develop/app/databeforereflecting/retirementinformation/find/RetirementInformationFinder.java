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
import nts.uk.ctx.hr.develop.dom.empregulationhistory.algorithm.EmploymentRegulationHistoryInterface;
import nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.service.IGetYearStartEndDateByDate;
import nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.service.YearStartEnd;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.service.IGetDatePeriod;
import nts.uk.ctx.hr.shared.dom.employment.EmploymentInfoImport;
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
	public RetiDateDto searchRetiredEmployees(SearchRetiredEmployeesQuery query) {

		// アルゴリズム[検索事前チェック]を実行する(Thực hiện thuật toan [check pre-search ])
		checkPreSearch(query);

		// アルゴリズム[検索前警告チェック]を実行する(Thực hiện thuật toán [check cảnh báo trước khi
		// search])

		if (!query.isConfirmCheckRetirementPeriod()) {
			preSearchWarning(query.getEndDate());
		}
		// アルゴリズム[定年退職者一覧の取得]を実行する(Thực hiện thuật toán [lấy danh sách người
		// nghỉ hưu])
		getListRetiredEmployees(query);
		return null;
	}

	private List<Object> getListRetiredEmployees(SearchRetiredEmployeesQuery query) {
		
		String cId = AppContexts.user().companyId();
		GeneralDate baseDate = GeneralDate.today();
		
		// アルゴリズム[定年退職予定者の取得]を実行する(Thực hiện thuật toán [lấy người dự định nghỉ
		// hưu])
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
		
		List<RetirementCourseDto> retirePlanCourses = createRetirePlanCourses(query,mada,empInfos,retires);

		// 検索条件リストを生成する ( Tạo list điều kiện tìm kiếm/Search condition list)
		return null;

		// empInfos.forEach(arg0);
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

			if (retirePlanCourses.isEmpty()) {
				return Collections.emptyList();
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

			if (retirePlanCourses.isEmpty()) {
				return Collections.emptyList();
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
