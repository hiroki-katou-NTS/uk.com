package nts.uk.ctx.hr.develop.app.databeforereflecting.retirementinformation.find;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetireTerm;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.MandatoryRetirementRegulation;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.RetirePlanCource;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.mandatoryRetirementRegulation.MandatoryRetirementRegulationService;
import nts.uk.ctx.hr.develop.dom.announcement.mandatoryretirement.algorithm.retirePlanCource.RetirePlanCourceService;
import nts.uk.ctx.hr.develop.dom.empregulationhistory.algorithm.EmploymentRegulationHistoryInterface;
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

	public RetiDateDto startPage() {

		// アルゴリズム[定年退職設定の取得]を実行する(thực hiện thuật toán [lấy setting nghỉ hưu])
		List<RetirementCourseDto> RetirementCourseDtos = getRetirementAgeSetting();
		
		// アルゴリズム[定年退職期間設定の取得]を実行する(thực hiện thuật toán [lấy setting khoảng
		// thời gian nghỉ hưu])
		DateDisplaySettingPeriodDto dateDisplaySettingPeriodDto = getRetirementPeriod();
		
		// アルゴリズム[参考情報表示設定の取得]を実行する(thực hiện thuật toán [lấy setting hiển thị
		// thông tin tham khảo])
		List<ReferEvaluationItemDto>  referEvaluationItems = GetEmpRegulationHistoryIDByBaseDate();
	

		return new RetiDateDto(dateDisplaySettingPeriodDto, RetirementCourseDtos, referEvaluationItems);
	}

	private List<ReferEvaluationItemDto> GetEmpRegulationHistoryIDByBaseDate() {
		String cId = AppContexts.user().companyId();
		return this.medaRepo.getReferEvaluationItemByDate(cId, GeneralDate.today()).stream().map(x-> new ReferEvaluationItemDto(x)).collect(Collectors.toList());
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
			throw new BusinessException(" MsgJ_JMM018_9");
		}

		// 雇用情報リストを確認する (Xác nhận Employment information list)
		empInfos.forEach(x -> {
			if (!StringUtil.isNullOrEmpty(x.getEmpCommonMasterItemId(), false)) {

				Integer value = Integer.valueOf(x.getEmpCommonMasterItemId());

				if (value >= 1) {
					throw new BusinessException(" MsgJ_JMM018_10");
				}
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
				.filter(emp -> emp.getEmpCommonMasterItemId() == retireTerm.getEmpCommonMasterItemId()).findFirst();

		String employmentCode = empInfo.isPresent() ? empInfo.get().getEmploymentCode() : null;
		String employmentName = empInfo.isPresent() ? empInfo.get().getEmploymentName() : null;

		retireTerm.getEnableRetirePlanCourse().forEach(x -> {

			RetirementCourseDto dto = 
					RetirementCourseDto
					.builder()
					.employmentCode(employmentCode)
					.employmentName(employmentName)
					.retireDateTerm(new RetireDateTermDto(mada.getRetireDateTerm()))
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
