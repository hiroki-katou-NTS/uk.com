package nts.uk.ctx.at.request.dom.application.proxy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachCompanyRepository;
import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachWorkplaceRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ProxyApplicationServiceImpl implements ProxyApplicationService {

	@Inject
	private EmployeeRequestAdapter empAdaptor;

	@Inject
	private RequestOfEachWorkplaceRepository requestOfEachWorkplaceRepository;

	@Inject
	private RequestOfEachCompanyRepository requestOfEachCompanyRepository;

	@Override
	public List<String> errorCheckDepartment(List<String> employeeIds, int applicationType) {
		if (Objects.isNull(employeeIds) || employeeIds.isEmpty()) {
			return Collections.emptyList();
		}
		String companyId               = AppContexts.user().companyId();
		GeneralDate baseDate           = GeneralDate.today();
		List<String> errorEmployeeName = new ArrayList<>();

		//社員IDリスト分ループ処理する
		for (String employeeId : employeeIds) {
			//申請本人の所属職場を含める上位職場を取得する
			List<String> workplaceIds = this.empAdaptor.findWpkIdsBySid(companyId, employeeId, baseDate);
			List<ApprovalFunctionSetting> existedSettingList = new ArrayList<>();
			for(String workplaceId: workplaceIds){
				//ドメインモデル「職場別申請承認設定」を取得する
				Optional<ApprovalFunctionSetting> settingOfEarchWorkplaceOp = this.requestOfEachWorkplaceRepository
						.getFunctionSetting(companyId, workplaceId, applicationType);
				if(settingOfEarchWorkplaceOp.isPresent()){
					existedSettingList.add(settingOfEarchWorkplaceOp.get());
					break;
				}
			}

			// ドメインモデル「職場別申請承認設定」を取得できたかチェックする
			if (existedSettingList.isEmpty()) {
				// ドメインモデル「会社別申請承認設定」を取得する
				Optional<ApprovalFunctionSetting> rqOptional = this.requestOfEachCompanyRepository
						.getFunctionSetting(companyId, applicationType);
				if (rqOptional.isPresent()) {
					existedSettingList.add(rqOptional.get());
				}
			}

			//取得できない場合もしくは申請種類がパラメータと一致する申請利用設定の利用区分が利用しないとき
			if(existedSettingList.isEmpty()){
				String employeeName = this.empAdaptor.getEmployeeName(employeeId);
				errorEmployeeName.add(employeeName);
			}
		}
		return errorEmployeeName;
	}

	@Override
	public void selectApplicationByType(List<String> employeeId, int applicationType) {
		List<String> errorEmployee = this.errorCheckDepartment(employeeId, applicationType);
		if(!errorEmployee.isEmpty()){
			String employeeName = String.join("\n", errorEmployee);
			//エラーメッセージ(#Msg_421#)を表示する
			throw new BusinessException("Msg_421", employeeName);
		}
	}
}
