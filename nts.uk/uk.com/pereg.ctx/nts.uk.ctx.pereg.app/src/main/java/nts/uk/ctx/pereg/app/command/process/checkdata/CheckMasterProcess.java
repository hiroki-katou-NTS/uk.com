package nts.uk.ctx.pereg.app.command.process.checkdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.task.data.TaskDataSetter;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridLayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.pereg.dom.person.setting.validatecheck.PerInfoValidChkCtgRepository;
import nts.uk.ctx.pereg.dom.person.setting.validatecheck.PerInfoValidateCheckCategory;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.system.config.InstalledProduct;
import nts.uk.shr.pereg.app.find.PeregEmpInfoQuery;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Stateless
public class CheckMasterProcess {
	
	@Inject
	private PerInfoValidChkCtgRepository perInfoCheckCtgRepo;
	
	final String chekMasterType = TextResource.localize("CPS013_11");
	
	@SuppressWarnings("unused")
	public void checkMaster(PeregEmpInfoQuery empCheck, Map<String, List<GridLayoutPersonInfoClsDto>> dataOfEmployee,
			CheckDataFromUI excuteCommand,
			Map<PersonInfoCategory, List<PersonInfoItemDefinition>> mapCategoryWithListItemDf,
			EmployeeDataMngInfo employee, String bussinessName, TaskDataSetter dataSetter,
			List<ErrorInfoCPS013> listError) {

		// 「実行時情報」の「システム利用区分」をチェックする (check thuộc tính 「システム利用区分」 trong RuntimeInformation)
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
		
		List<PersonInfoCategory> listCategory = new ArrayList<>(mapCategoryWithListItemDf.keySet()); 
		
		List<String> listCategoryCode = listCategory.stream().map(m -> m.getCategoryCode().v()).collect(Collectors.toList());
		
		List<PerInfoValidateCheckCategory> lstCtgSetting = this.perInfoCheckCtgRepo.getListPerInfoValidByListCtgId(listCategoryCode,
				AppContexts.user().contractCode());
		for (int i = 0; i < listCategory.size(); i++) {
			
			PersonInfoCategory category = listCategory.get(i);
			
			PerInfoValidateCheckCategory settingCtg = lstCtgSetting.stream()
					.filter(ctg -> ctg.getCategoryCd().equals(category.getCategoryCode().v())).findFirst().get();
			
			// スケジュール管理チェックに選択された場合 (TH được chọn để kiểm tra quản lý lịch trình)
			if ((forAttendance == NotUseAtr.USE.value) && (excuteCommand.isScheduleMngCheck())
					&& (settingCtg.getScheduleMngReq().value == NotUseAtr.USE.value)) {
				List<GridLayoutPersonInfoClsDto> datas = dataOfEmployee.get(category.getCategoryCode().v());
				if (datas.isEmpty()) {
					writeError(employee, category, bussinessName, dataSetter, "スケジュール管理", listError );
				}
			}
			
			// 日別実績管理チェックに選択された場合 ( TH được chọn để kiểm tra kết quả quản lý kết quả hàng ngày)
			if ((forAttendance == NotUseAtr.USE.value) && (excuteCommand.isDailyPerforMngCheck())
					&& (settingCtg.getDailyActualMngReq().value == NotUseAtr.USE.value)) {
				List<GridLayoutPersonInfoClsDto> datas = dataOfEmployee.get(category.getCategoryCode().v());
				if (datas.isEmpty()) {
					writeError(employee, category, bussinessName, dataSetter, "日別実績管理", listError );
				}
			}
			
			// 月別実績管理チェックに選択された場合 ( TH được chọn để kiểm tra kết quả quản lý hàng tháng)
			if ((forAttendance == NotUseAtr.USE.value) && (excuteCommand.isMonthPerforMngCheck())
					&& (settingCtg.getMonthActualMngReq().value == NotUseAtr.USE.value)) {
				List<GridLayoutPersonInfoClsDto> datas = dataOfEmployee.get(category.getCategoryCode().v());
				if (datas.isEmpty()) {
					writeError(employee, category, bussinessName, dataSetter, "月別実績管理", listError );
				}
			}
			
			// 給与チェックに選択された場合 (TH được chọn để kiểm tra  lương)
			if ((forPayroll == NotUseAtr.USE.value) && (excuteCommand.isPayRollMngCheck())
					&& (settingCtg.getPayMngReq().value == NotUseAtr.USE.value)) {
				List<GridLayoutPersonInfoClsDto> datas = dataOfEmployee.get(category.getCategoryCode().v());
				if (datas.isEmpty()) {
					writeError(employee, category, bussinessName, dataSetter, "給与管理", listError);
				}
			}
			
			// 賞与チェックに選択された場合 (TH được chọn để kiểm tra tiền thưởng)
			if ((forPayroll == NotUseAtr.USE.value) && (excuteCommand.isBonusMngCheck())
					&& (settingCtg.getBonusMngReq().value == NotUseAtr.USE.value)) {
				List<GridLayoutPersonInfoClsDto> datas = dataOfEmployee.get(category.getCategoryCode().v());
				if (datas.isEmpty()) {
					writeError(employee, category, bussinessName, dataSetter, "賞与管理", listError);
				}
			}
			
			// 年調チェックに選択された場合 (TH được chọn để kiểm tra điều chỉnh năm)
			if ((forPayroll == NotUseAtr.USE.value) && (excuteCommand.isYearlyMngCheck())
					&& (settingCtg.getYearMngReq().value == NotUseAtr.USE.value)) {
				List<GridLayoutPersonInfoClsDto> datas = dataOfEmployee.get(category.getCategoryCode().v());
				if (datas.isEmpty()) {
					writeError(employee, category, bussinessName, dataSetter, "年調管理", listError);
				}
			}
			
			// 月額算定チェックに選択された場合 (TH được chọn để kiểm tra tính toán hàng tháng)
			if ((forPayroll == NotUseAtr.USE.value) && (excuteCommand.isMonthCalCheck())
					&& (settingCtg.getMonthCalcMngReq().value == NotUseAtr.USE.value)) {
				List<GridLayoutPersonInfoClsDto> datas = dataOfEmployee.get(category.getCategoryCode().v());
				if (datas.isEmpty()) {
					writeError(employee, category, bussinessName, dataSetter, "月額算定管理", listError);
				}
			}
		}
	}

	private void writeError(EmployeeDataMngInfo employee, PersonInfoCategory category, String bussinessName,
			TaskDataSetter dataSetter, String errorText, List<ErrorInfoCPS013> listError) {
		ErrorInfoCPS013 error = new ErrorInfoCPS013(employee.getEmployeeId(),category.getPersonInfoCategoryId(), employee.getEmployeeCode().v(), bussinessName,
				chekMasterType, category.getCategoryName().v(),
				TextResource.localize("Msg_1483", errorText));
		listError.add(error);
		setErrorDataGetter(error, dataSetter);
		
	}
	
	private void setErrorDataGetter(ErrorInfoCPS013 error, TaskDataSetter dataSetter) {
		String ramdom = IdentifierUtil.randomUniqueId();
		ObjectMapper mapper = new ObjectMapper();
		try {
			dataSetter.setData("employeeId" + ramdom, mapper.writeValueAsString(error));
		} catch (JsonProcessingException e) {
			System.out.println("cps013 mapper object to json fail");
			e.printStackTrace();
			
		}
	}

}
