package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.logging.log4j.util.Strings;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.EmployeeOvertimeDto;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoNoDateOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.service.GoBackDirectAppSetService;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeService;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkTypeAndSiftType;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSetting;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.service.GoBackDirectCommonService;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class GoBackDirectlyFinder {
	@Inject
	private GoBackDirectlyRepository goBackDirectRepo;
	@Inject
	private GoBackDirectCommonService goBackCommon;
	@Inject
	private GoBackDirectAppSetService goBackAppSet;
	@Inject
	private AtEmployeeAdapter atEmployeeAdapter;
	@Inject
	private GoBackDirectlyCommonSettingRepository goBackDirectCommonSetRepo;
	@Inject
	private CommonAlgorithm commonAlgorithm;
	@Inject
	private WorkTypeRepository workTypeRepository;
	@Inject
	private OvertimeService overtimeService;
	/**
	 * Get GoBackDirectlyDto
	 * 
	 * @param appID
	 * @return
	 */
	public GoBackDirectlyDto getGoBackDirectlyByAppID(String appID) {
		String companyID = AppContexts.user().companyId();
		return goBackDirectRepo.findByApplicationID(companyID, appID)
				.map(c -> GoBackDirectlyDto.convertToDto(c))
				.orElse(null);
	}
//	直行直帰申請起動時初期データを取得する 
	public InforGoBackDirectOutput getInforGoBackDirect(String companyId,String employeeId, AppDispInfoStartupDto appDispInfoStartupOutput) {
		
		
//		起動時勤務種類リストを取得する
//		get work type list
		String employmentId = appDispInfoStartupOutput.appDispInfoWithDateOutput.employmentSet.get(0).getEmploymentCode();
		List<String> list = getWorkTypeList(companyId, employmentId);
		List<WorkType> workrTypes = this.workTypeRepository.findWorkOneDay(companyId, 0, 1);
		
//		09_勤務種類就業時間帯の初期選択をセットする
//		WorkTypeAndSiftType workTypeAndSiftType = overtimeService.getWorkTypeAndSiftTypeByPersonCon(companyId, employeeId, 
//				Strings.isBlank(GeneralDate.today().toString()) ? null : GeneralDate.fromString(GeneralDate.today().toString(), "yyyy/MM/dd"), workTypeOvertimes, siftTypes);
		
		
		return null;
	}
	 
	public List<String> getWorkTypeList(String companyId, String employmentId) {
		if (employmentId != null) {
			
		} else {
			
		}
		return null;
	}
	public GoBackDirectSettingNewDto getGoBack(String companyId, String appDate, String employeeId, AppDispInfoStartupDto appDispInfoStartupOutput) {
		employeeId = null;
		
		
		GoBackDirectSettingNewDto getGoBack = new GoBackDirectSettingNewDto();
//		直行直帰申請起動時初期データを取得する
		getInforGoBackDirect(companyId, employeeId, appDispInfoStartupOutput);
		
//		エラー情報をチェックする(Check ErrorInfo)
		
		// is not error
		
//		ドメインモデル「直行直帰申請共通設定」より取得する 
		GoBackDirectlyCommonSetting goBackCommonSet = goBackDirectCommonSetRepo.findByCompanyID(companyId).get();
		getGoBack.setBackDirectCommonSetting(goBackCommonSet);
		return null;
	}
////	起動時の申請表示情報を取得する
//	public AppDispInfoStartupDto getAppDisp(String companyId, ApplicationType appType, List<String> applicantLst, List<GeneralDate> dateLst) {
////		申請表示情報(基準日関係なし)を取得する
//		AppDispInfoNoDateOutput appDispInfoStartupDto = commonAlgorithm.getAppDispInfo(companyId, applicantLst, appType);
//		
//// 		申請表示情報(基準日関係あり)を取得する
//		// not handle mode
//		AppDispInfoWithDateOutput appDispInfoWithDateOutput = commonAlgorithm.getAppDispInfoWithDate(companyId, appType, dateLst, appDispInfoStartupDto, true);
//		
//		
//		return null;
//	}
//	
	 

	private AppDispInfoNoDateOutput getAppDispInfoStartupDto() {
		
		return null;
	}
	/**
	 * convert to GoBackDirectSettingDto
	 * 
	 * @param SID
	 * @return
	 */
	
	public GoBackDirectSettingDto getGoBackDirectCommonSetting(List<String> employeeIDs, String paramDate) {
		String companyID = AppContexts.user().companyId();
		GeneralDate appDate = GeneralDate.fromString(paramDate, "yyyy/MM/dd");
		List<GeneralDate> listDate = new ArrayList();
		listDate.add(appDate);
//		起動時の申請表示情報を取得する
		AppDispInfoStartupDto appDispInfor = AppDispInfoStartupDto.fromDomain(commonAlgorithm.getAppDispInfoStart(companyID, ApplicationType.GO_RETURN_DIRECTLY_APPLICATION, null, listDate, true));
		
//		GoBackDirectSettingNewDto backDirectSettingNewDto = null;
//		アルゴリズム「直行直帰画面初期（新規）」を実行する
		//backDirectSettingNewDto = getGoBack(null, null, null, appDispInfor);
	//	backDirectSettingNewDto.setAppDispInfoStartupOutput(appDispInfor);
		
		
		
		GoBackDirectSettingDto result = new GoBackDirectSettingDto();
		
		String sID = AppContexts.user().employeeId();
		List<EmployeeOvertimeDto> employeeOTs = new ArrayList<>();
		if(!CollectionUtil.isEmpty(employeeIDs)){
			sID = employeeIDs.get(0);
			List<EmployeeInfoImport> employees = this.atEmployeeAdapter.getByListSID(employeeIDs);
			if(!CollectionUtil.isEmpty(employees)){
				for(EmployeeInfoImport emp : employees){
					EmployeeOvertimeDto employeeOT = new EmployeeOvertimeDto(emp.getSid(), emp.getBussinessName());
					employeeOTs.add(employeeOT);
				}
				
			}
		}
		result =  GoBackDirectSettingDto.convertToDto(goBackCommon.getSettingData(companyID, sID, appDate));
		result.setEmployees(employeeOTs);
		return result;
	}

	/**
	 * get Detail Data to
	 * 
	 * @param appID
	 * @return
	 */
	public GoBackDirectDetailDto getGoBackDirectDetailByAppId(String appID) {
		return GoBackDirectDetailDto.convertToDto(
				goBackAppSet.getGoBackDirectAppSet(appID)
			);
	}

	
}
