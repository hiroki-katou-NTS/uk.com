package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.EmployeeOvertimeDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.AtEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.service.GoBackDirectAppSetService;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.service.GoBackDirectCommonService;
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
	

	/**
	 * convert to GoBackDirectSettingDto
	 * 
	 * @param SID
	 * @return
	 */
	public GoBackDirectSettingDto getGoBackDirectCommonSetting(List<String> employeeIDs, String paramDate) {
		GeneralDate appDate = GeneralDate.fromString(paramDate, "yyyy/MM/dd");
		GoBackDirectSettingDto result = new GoBackDirectSettingDto();
		String companyID = AppContexts.user().companyId();
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
