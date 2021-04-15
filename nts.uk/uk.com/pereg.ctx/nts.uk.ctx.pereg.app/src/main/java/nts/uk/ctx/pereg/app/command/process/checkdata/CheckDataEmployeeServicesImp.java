package nts.uk.ctx.pereg.app.command.process.checkdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridLayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.processor.PeregProcessor;
import nts.uk.ctx.pereg.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.setting.validatecheck.PerInfoValidChkCtgRepository;
import nts.uk.ctx.pereg.dom.person.setting.validatecheck.PerInfoValidateCheckCategory;
import nts.uk.query.model.employee.RegulationInfoEmployee;
import nts.uk.query.model.employee.RegulationInfoEmployeeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.system.config.InstalledProduct;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;
import nts.uk.shr.pereg.app.find.PeregEmpInfoQuery;

@Stateless
public class CheckDataEmployeeServicesImp implements CheckDataEmployeeServices {

	@Inject
	private RegulationInfoEmployeeRepository regulationInfoEmployeeRepo;
	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepo;
	@Inject
	private PerInfoValidChkCtgRepository perInfoCheckCtgRepo;
	@Inject
	private PerInfoItemDefRepositoty perInfotemDfRepo;
	@Inject
	private PeregProcessor peregProcessor;
	@Inject
	I18NResourcesForUK ukResouce;
	@Inject 
	private EmployeeDataMngInfoRepository empDataMngInfoRepo;
	@Inject 
	private CheckPersonInfoProcess checkPersonInfoProcess;
	@Inject 
	private CheckMasterProcess checkMasterProcess;
	
	/** The Constant TIME_DAY_START. */
	public static final String TIME_DAY_START = " 00:00:00";

	/** The Constant DATE_TIME_FORMAT. */
	public static final String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
	/** The Constant DEFAULT_VALUE. */
	private static final int DEFAULT_VALUE = 0;
	final String cancelRequest = TextResource.localize("CPS013_51");

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public <C> void manager(CheckDataFromUI excuteCommand, AsyncCommandHandlerContext<C> async) {

		// 実行状態 初期設定
		val dataSetter = async.getDataSetter();
		// システム日時を取得する (lấy system date)
		dataSetter.setData("startTime", GeneralDateTime.now().toString());
		
		// アルゴリズム「個人情報条件で社員を検索して並び替える」を実行する
		// (thực thi thuật toán 「Search employee theo điều kiện thông tin cá nhân, và thay đổi thứ tự」
		List<EmployeeResultDto> listEmp = this.findEmployeesInfo(excuteCommand);
		if(CollectionUtil.isEmpty(listEmp)) {
			throw new BusinessException("Msg_1564");
		}
		
		Map<String, String> mapSIdWthBussinessName = listEmp.stream().collect(Collectors.toMap(e -> e.sid, e -> e.bussinessName));
		
		List<String> sids = listEmp.stream().map(mapper -> mapper.sid).collect(Collectors.toList());
		
		List<EmployeeDataMngInfo> listEmpData = this.empDataMngInfoRepo.findByListEmployeeId(sids);
		
		List<EmployeeDataMngInfo> listEmpDataOrder = listEmpData.stream()
				.sorted(Comparator.comparing(EmployeeDataMngInfo::getEmployeeCode))
				.collect(Collectors.toList());
		
		// アルゴリズム「個人情報カテゴリ取得」を実行する (Thực hiện thuật toán 「Lấy PersonInfoCategory」)
		Map<PersonInfoCategory, List<PersonInfoItemDefinition>> mapCategoryWithListItemDf = this.getAllCategory(AppContexts.user().companyId());
		
		List<ErrorInfoCPS013> listError = new ArrayList<>();
		
		int countError = DEFAULT_VALUE;
		dataSetter.setData("numberEmpChecked", 0);
		dataSetter.setData("countEmp", listEmpData.size());
		dataSetter.setData("statusCheck", ExecutionStatusCps013.PROCESSING.name);
		
		//アルゴリズム「整合性チェック処理」を実行する (Thực hiện thuật toán 「Xử lý check tính hợp lệ」)
		for (int i = 0; i < listEmpDataOrder.size(); i++) {
			
			// check request cancel
			if (async.hasBeenRequestedToCancel()) {
				dataSetter.updateData("statusCheck", cancelRequest);
				dataSetter.setData("endTimeForRequestedToCancel", GeneralDateTime.now().toString());
				async.finishedAsCancelled();
				return ;
			}
			
			EmployeeDataMngInfo employee = listEmpDataOrder.get(i);
			
			String bussinessName = mapSIdWthBussinessName.get(employee.getEmployeeId());
			
			PeregEmpInfoQuery empCheck = new PeregEmpInfoQuery(employee.getPersonId(), employee.getEmployeeId(), null);
			
			//チェック対象データ取得 (Lấy data đối tượng check)
			Map<String, List<GridLayoutPersonInfoClsDto>> dataOfEmployee =  this.peregProcessor.getFullCategoryDetailByListEmp(Arrays.asList(empCheck), mapCategoryWithListItemDf);
			
			checkDataOfEmp(empCheck, dataOfEmployee, excuteCommand, mapCategoryWithListItemDf, employee, bussinessName, dataSetter, listError);
			
			countError += 1;
			
			dataSetter.updateData("numberEmpChecked", countError);
		}
		
		if (listError.isEmpty()) {
			dataSetter.updateData("statusCheck", ExecutionStatusCps013.DONE.name);
		} else {
			dataSetter.updateData("statusCheck", ExecutionStatusCps013.DONE_WITH_ERROR.name);
		}
	}

	private void checkDataOfEmp(PeregEmpInfoQuery empCheck, Map<String, List<GridLayoutPersonInfoClsDto>> dataOfEmployee, 
			CheckDataFromUI excuteCommand,Map<PersonInfoCategory, List<PersonInfoItemDefinition>> mapCategoryWithListItemDf, EmployeeDataMngInfo employee, String bussinessName, TaskDataSetter dataSetter, List<ErrorInfoCPS013> listError) {
		
		if (excuteCommand.isMasterCheck() && excuteCommand.isPerInfoCheck()) {
			checkMasterAndPersonInfo(empCheck, dataOfEmployee, excuteCommand, mapCategoryWithListItemDf, employee, bussinessName, dataSetter, listError);
		} else if (excuteCommand.isPerInfoCheck()) {
			this.checkPersonInfoProcess.checkPersonInfo(empCheck, dataOfEmployee, excuteCommand, mapCategoryWithListItemDf, employee, bussinessName, dataSetter, listError);
		} else if (excuteCommand.isMasterCheck()) {
			this.checkMasterProcess.checkMaster(empCheck, dataOfEmployee, excuteCommand, mapCategoryWithListItemDf, employee, bussinessName, dataSetter, listError);
		}
	}

	private void checkMasterAndPersonInfo(PeregEmpInfoQuery empCheck, Map<String, List<GridLayoutPersonInfoClsDto>> dataOfEmployee,
			CheckDataFromUI excuteCommand, Map<PersonInfoCategory, List<PersonInfoItemDefinition>> mapCategoryWithListItemDf, EmployeeDataMngInfo employee, String bussinessName,  TaskDataSetter dataSetter, List<ErrorInfoCPS013> listError) {
		
		// 個人基本情報チェック (Check thông tin cá nhân cơ bản)
		this.checkPersonInfoProcess.checkPersonInfo(empCheck, dataOfEmployee, excuteCommand, mapCategoryWithListItemDf, employee, bussinessName, dataSetter, listError);

		// 対象カテゴリの絞り込み(Filter Category đối tượng check)
		/**
		 * Loai bo category doi tuong duoi day ra khoi list category doi tuongTruong hop [Personal information integrity check category].
		 * [Employment system required]:True or 
		 * [Human Resources System Required]:True or 
		 * [Salary System Required]:True
		 * 
		 * ※Vi viec kiem tra System required category da duoc check trong「Personal Basic Information Check」, nen khong can hien thi error message 2 lan nua
		 */
		
		List<PersonInfoCategory> listCategory = new ArrayList<>(mapCategoryWithListItemDf.keySet()); 
		
		List<String> listCategoryCode = listCategory.stream().map(m -> m.getCategoryCode().v()).collect(Collectors.toList());
		
		List<PerInfoValidateCheckCategory> lstCtgSetting = this.perInfoCheckCtgRepo.getListPerInfoValidByListCtgId(listCategoryCode, AppContexts.user().contractCode());
		
		List<PerInfoValidateCheckCategory> listCategorySystemFilter = lstCtgSetting.stream().filter(ctg -> {
			if ((ctg.getHumanSysReq().value == NotUseAtr.USE.value) || (ctg.getPaySysReq().value == NotUseAtr.USE.value)
			|| (ctg.getJobSysReq().value == NotUseAtr.USE.value)) {
				return true;
			}
			return false;
		}).collect(Collectors.toList());
		
		List<PerInfoValidateCheckCategory> listCategoryNotSystemFilter = lstCtgSetting.stream()
                .filter(i -> !listCategorySystemFilter.contains(i))
                .collect (Collectors.toList());
		
		List<String> listCtgNotSystemFilterCode = listCategoryNotSystemFilter.stream().map(m -> m.getCategoryCd().v()).collect(Collectors.toList());
		
		Map<PersonInfoCategory, List<PersonInfoItemDefinition>> mapCategoryWithListItemDfNew = mapCategoryWithListItemDf.entrySet().stream()
				.filter(x -> listCtgNotSystemFilterCode.contains(x.getKey().getCategoryCode().v()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		
		// マスタチェック (check master)
		this.checkMasterProcess.checkMaster(empCheck, dataOfEmployee, excuteCommand, mapCategoryWithListItemDfNew, employee, bussinessName, dataSetter, listError);
		
	}

	// アルゴリズム「個人情報条件で社員を検索して並び替える」を実行する
	// (thực thi thuật toán 「Search employee theo điều kiện thông tin cá nhân, và thay đổi thứ tự」)
	private List<EmployeeResultDto> findEmployeesInfo(CheckDataFromUI query) {
		EmpQueryDto queryDto = new EmpQueryDto();
		GeneralDateTime baseDate = GeneralDateTime.fromString(query.getDateTime() + TIME_DAY_START, DATE_TIME_FORMAT);
		return this.regulationInfoEmployeeRepo.find(AppContexts.user().companyId(), queryDto.toQueryModel(baseDate))
				.stream().map(model -> this.toEmployeeDto(model)).collect(Collectors.toList());
	}

	private EmployeeResultDto toEmployeeDto(RegulationInfoEmployee model) {
		return new EmployeeResultDto(model.getEmployeeID(), model.getEmployeeCode(), model.getName().orElse(""));
	}

	// アルゴリズム「個人情報カテゴリ取得」を実行する (Thực hiện thuật toán 「Lấy PersonInfoCategory」)
	public Map<PersonInfoCategory, List<PersonInfoItemDefinition>> getAllCategory(String cid) {
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

		String contracCd = AppContexts.user().contractCode();

		// trong câu query đã lọc ra những category không có item nào rồi.
		List<PersonInfoCategory> lstCtg = perInfoCtgRepo.getAllCategoryForCPS013(cid, forAttendance, forPayroll,
				forPersonnel);

		// ドメインモデル「個人情報整合性チェックカテゴリ」を全て取得する
		// (Get toàn bộ domain model 「PerInfoValidChkCtg」)
		// チェック対象項目件数をチェックする
		// (Check số data item đối tượng check)
		if (lstCtg.isEmpty()) {
			throw new BusinessException("Msg_930");
		}

		List<String> listCategoryCode = lstCtg.stream().map(i -> i.getCategoryCode().toString())
				.collect(Collectors.toList());

		List<PerInfoValidateCheckCategory> lstCtgCheck = this.perInfoCheckCtgRepo
				.getListPerInfoValidByListCtgId(listCategoryCode, contracCd);

		Map<PersonInfoCategory, List<PersonInfoItemDefinition>> mapCategoryWithListItemDf = new HashMap<>();

		lstCtgCheck.forEach(ctg -> {
			PersonInfoCategory category = lstCtg.stream()
					.filter(i -> i.getCategoryCode().toString().equals(ctg.getCategoryCd().toString())).findFirst()
					.get();
			List<PersonInfoItemDefinition> listItemDf = this.perInfotemDfRepo.getAllPerInfoItemDefByCategoryIdCPS013(
					category.getPersonInfoCategoryId(), AppContexts.user().contractCode());
			if (!listItemDf.isEmpty()) {
				mapCategoryWithListItemDf.put(category, listItemDf);
			}
		});

		// チェック対象となるカテゴリを絞り込み (filter category là đối tượng check)
		filterCategory(lstCtgCheck, mapCategoryWithListItemDf);

		return mapCategoryWithListItemDf;
	}

	public void filterCategory(List<PerInfoValidateCheckCategory> listCtgSetting, Map<PersonInfoCategory, List<PersonInfoItemDefinition>> mapCategoryWithListItemDf) {
		mapCategoryWithListItemDf.entrySet().forEach(ctg -> {
			PerInfoValidateCheckCategory ctgSetting = listCtgSetting.stream()
					.filter(i -> i.getCategoryCd().toString().equals(ctg.getKey().getCategoryCode().toString())).findFirst()
					.get();
			if (!checkCategory(ctgSetting)) {
				mapCategoryWithListItemDf.remove(ctg);
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
}
