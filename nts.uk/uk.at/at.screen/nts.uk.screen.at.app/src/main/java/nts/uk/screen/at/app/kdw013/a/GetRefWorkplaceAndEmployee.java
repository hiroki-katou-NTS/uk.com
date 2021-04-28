package nts.uk.screen.at.app.kdw013.a;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.jobmanagement.workchangeableperiodsetting.WorkChangeablePeriodSetting;
import nts.uk.ctx.at.record.dom.jobmanagement.workchangeableperiodsetting.WorkChangeablePeriodSettingRepository;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;
import nts.uk.ctx.bs.employee.dom.workplace.info.OutsideWorkplaceCode;
import nts.uk.ctx.bs.employee.dom.workplace.info.WkpCode;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceDisplayName;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceGenericName;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfo;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceName;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.WorkplaceExportServiceAdapter;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KDW013_工数入力.A:工数入力.メニュー別OCD.参照可能職場・社員を取得する
 * 
 * @author tutt <<ScreenQuery>>
 */
@Stateless
public class GetRefWorkplaceAndEmployee {

	@Inject
	private WorkChangeablePeriodSettingRepository workChangeablePeriodSettingRepo;

	@Inject
	private WorkplaceExportServiceAdapter serviceAdapter;

	@Inject
	private EmpEmployeeAdapter empEmployeeAdapter;

	public GetRefWorkplaceAndEmployeeDto get(GeneralDate refDate) {
		String companyId = AppContexts.user().companyId();
		GetRefWorkplaceAndEmployeeDto result = new GetRefWorkplaceAndEmployeeDto();

		// 1: get(ログイン会社ID)
		Optional<WorkChangeablePeriodSetting> optWorkChangeablePeriodSetting = workChangeablePeriodSettingRepo
				.get(companyId);

		// 2: 参照可能範囲を取得する(ログイン会社ID, ログインユーザID, ログイン社員ID, 基準日): Map<社員ID,職場ID>
		//TODO: Thanh chua code xong
		Map<String, String> employeeInfos;

		// 3: <call>()
		// [No.600]社員ID（List）から社員コードと表示名を取得（削除社員考慮）
		DatePeriod datePeriod = new DatePeriod(refDate, refDate);
		// List<EmployeeBasicInfoImport> lstEmployeeInfo = empEmployeeAdapter.getEmpInfoLstBySids(lstEmpIds, datePeriod, true, true);

		// 4: <call>()
		// [No.560]職場IDから職場の情報をすべて取得する
		// ※Map<社員ID,職場ID>からList＜社員ID（List）から社員コードと表示名を取得＞にない「社員ID」を除いてから「対象職場」を作る
		List<String> listWorkplaceId = new ArrayList<>();

		List<WorkplaceInfo> workplaceInfos = serviceAdapter
				.getWorkplaceInforByWkpIds(companyId, listWorkplaceId, refDate).stream()
				.map(mapper -> new WorkplaceInfo(companyId, 
						null,
						mapper.getWorkplaceId(),
						new WkpCode(mapper.getWorkplaceCode()), 
						new WorkplaceName(mapper.getWorkplaceName()),
						new WorkplaceGenericName(mapper.getWorkplaceGenericName()),
						new WorkplaceDisplayName(mapper.getWorkplaceDisplayName()),
						new OutsideWorkplaceCode(mapper.getWorkplaceExternalCode())))
				.collect(Collectors.toList());

		result.setWorkplaceInfos(workplaceInfos);

		return result;
	}

}
