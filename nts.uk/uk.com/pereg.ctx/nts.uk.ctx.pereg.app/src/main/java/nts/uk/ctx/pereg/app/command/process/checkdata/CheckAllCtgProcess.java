package nts.uk.ctx.pereg.app.command.process.checkdata;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.setting.validatecheck.PerInfoValidChkCtgRepository;
import nts.uk.ctx.pereg.dom.person.setting.validatecheck.PerInfoValidateCheckCategory;
import nts.uk.query.model.employee.RegulationInfoEmployee;
import nts.uk.query.model.employee.RegulationInfoEmployeeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.system.config.InstalledProduct;

@Stateless
public class CheckAllCtgProcess {

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepositoty;

	@Inject
	private PerInfoValidChkCtgRepository perInfoCheckCtgRepo;
	
	@Inject
	private RegulationInfoEmployeeRepository regulationInfoEmployeeRepo;

	/** The Constant TIME_DAY_START. */
	public static final String TIME_DAY_START = " 00:00:00";

	/** The Constant DATE_TIME_FORMAT. */
	public static final String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";

	/**
	 * アルゴリズム「個人情報カテゴリ取得」を実行する (Thực hiện thuật toán 「Lấy PersonInfoCategory」)
	 */
	public CategoryResult getAllCategory(CheckDataFromUI query) {
		// アルゴリズム「システム利用区分から利用可能な個人情報カテゴリを全て取得する」を実行する
		// (Thực hiện thuật toán 「Từ SystemUseAtr, lấy toàn bộ
		// PersonInfoCategory có thể sử dụng」)
		int forAttendance = nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.NotUseAtr.NOT_USE.value;
		int forPayroll = nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.NotUseAtr.NOT_USE.value;
		int forPersonnel = nts.uk.ctx.at.schedule.dom.plannedyearholiday.frame.NotUseAtr.NOT_USE.value;
		List<InstalledProduct> installProduct = AppContexts.system().getInstalledProducts();
		for (InstalledProduct productType : installProduct) {
			switch (productType.getProductType()) {
			case ATTENDANCE:
				forAttendance = NotUseAtr.USE.value;
				break;
			case PAYROLL:
				forPayroll = NotUseAtr.USE.value;
				break;
			case PERSONNEL:
				forPersonnel = NotUseAtr.USE.value;
				break;
			default:
				break;
			}
		}

		String cid = AppContexts.user().companyId();
		String contractCode = AppContexts.user().contractCode();

		// Chi bao gom nhưng category co item.
		List<PersonInfoCategory> lstCtg = perInfoCtgRepositoty.getAllCategoryForCPS013(cid, forAttendance, forPayroll,
				forPersonnel);

		List<String> listCategoryCode = lstCtg.stream().map(i -> i.getCategoryCode().toString())
				.collect(Collectors.toList());

		List<PerInfoValidateCheckCategory> lstCtgCheck = this.perInfoCheckCtgRepo.getListPerInfoValidByListCtgId(listCategoryCode,
				contractCode);

		// チェック対象となるカテゴリを絞り込み (filter category là đối tượng check)
		filterCategory(lstCtgCheck, lstCtg);

		// ドメインモデル「個人情報整合性チェックカテゴリ」を全て取得する
		// (Get toàn bộ domain model 「PerInfoValidChkCtg」)
		// チェック対象項目件数をチェックする
		// (Check số data item đối tượng check)
		if (lstCtgCheck.isEmpty()) {
			throw new BusinessException("Msg_930");
		}

		List<CategoryInfo>  ctgInfo = lstCtgCheck.stream().map(ctgSetting -> {
			PersonInfoCategory ctg  = lstCtg.stream().filter(i -> i.getCategoryCode().toString().equals(ctgSetting.getCategoryCd().toString())).findFirst().get();
			return new CategoryInfo(ctg.getPersonInfoCategoryId(), ctg.getCategoryCode().toString(),
					ctg.getCategoryName().toString());
		}).collect(Collectors.toList());
		
		/**
		 * アルゴリズム「個人情報条件で社員を検索して並び替える」を実行する
		 * Thực thi thuật toán 「Search employee theo điều kiện thông tin cá nhân, và thay đổi thứ tự」
		 */
		List<EmployeeResultDto> listEmp = findEmployeesInfo(query);
		
		if(CollectionUtil.isEmpty(listEmp)) {
			throw new BusinessException("Msg_1564");
		}
		
		GeneralDateTime endDateTime = GeneralDateTime.now();
		GeneralDateTime startDateTime = GeneralDateTime.now();
		CategoryResult result = new CategoryResult(ctgInfo, listEmp.size(), startDateTime , endDateTime);
		return result;
	}

	public void filterCategory(List<PerInfoValidateCheckCategory> listCtgSetting, List<PersonInfoCategory> listCtg) {
		listCtgSetting.forEach(ctgSetting -> {
			if (!checkCategory(ctgSetting)) {
				listCtg.remove(ctgSetting);
			}
		});
	}

	private boolean checkCategory(PerInfoValidateCheckCategory ctgSetting) {
		if ((ctgSetting.getHumanSysReq() == NotUseAtr.USE) || (ctgSetting.getJobSysReq() == NotUseAtr.USE)
				|| (ctgSetting.getPaySysReq() == NotUseAtr.USE) || (ctgSetting.getPayMngReq() == NotUseAtr.USE)
				|| (ctgSetting.getMonthCalcMngReq() == NotUseAtr.USE)
				|| (ctgSetting.getMonthActualMngReq() == NotUseAtr.USE)
				|| (ctgSetting.getBonusMngReq() == NotUseAtr.USE) || (ctgSetting.getScheduleMngReq() == NotUseAtr.USE)
				|| (ctgSetting.getDailyActualMngReq() == NotUseAtr.USE)
				|| (ctgSetting.getYearMngReq() == NotUseAtr.USE))
			return true;
		return false;
	}
	
	// アルゴリズム「個人情報条件で社員を検索して並び替える」を実行する
	private List<EmployeeResultDto> findEmployeesInfo(CheckDataFromUI query) {
		EmpQueryDto queryDto = new EmpQueryDto();
		GeneralDateTime baseDate = GeneralDateTime.fromString(query.getDateTime() + TIME_DAY_START, DATE_TIME_FORMAT);
		return this.regulationInfoEmployeeRepo.find(AppContexts.user().companyId(), queryDto.toQueryModel(baseDate))
				.stream().map(model -> this.toEmployeeDto(model)).collect(Collectors.toList());
	}

	private EmployeeResultDto toEmployeeDto(RegulationInfoEmployee model) {
		return new EmployeeResultDto(model.getEmployeeID(), model.getEmployeeCode(), model.getName().orElse(""));
	}

}
