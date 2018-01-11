package nts.uk.ctx.at.request.app.find.setting.requestofearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.app.find.application.requestofearch.RequestAppDetailSettingDto;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestOfEachCompany;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestOfEachCompanyRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author yennth
 *
 */
@Stateless
public class RequestOfEachCompanyFinder {
	@Inject
	private RequestOfEachCompanyRepository requestRep;
	/**
	 * convert from List Request AppDetail Setting to list dto
	 * @param domain
	 * @return
	 */
	public static List<RequestAppDetailSettingDto> convertToDto(List<RequestAppDetailSetting> domain) {
		List<RequestAppDetailSettingDto> list = new ArrayList<>();
		if(domain.size() > 0){
			for(RequestAppDetailSetting request : domain){
				if(request==null) return null;
				RequestAppDetailSettingDto temp = new RequestAppDetailSettingDto(request.getCompanyId(), request.getAppType().value, request.getMemo().v(),
						request.getUserAtr().value, request.getPrerequisiteForpauseFlg().value, 
						request.getOtAppSettingFlg().value,
						request.getHolidayTimeAppCalFlg().value, request.getLateOrLeaveAppCancelFlg().value,
						request.getLateOrLeaveAppSettingFlg().value, request.getBreakInputFieldDisFlg().value,
						request.getBreakTimeDisFlg().value, request.getAtworkTimeBeginDisFlg().value,
						request.getGoOutTimeBeginDisFlg().value, request.getTimeCalUseAtr().value,
						request.getTimeInputUseAtr().value, request.getRequiredInstructionFlg().value);
				list.add(temp);
			}
			return list;
		}
		return null;
	}
	/**
	 * find Request Of Each Company  
	 * @return
	 */
	public RequestOfEachCompanyDto findRequest(){
		String companyId = AppContexts.user().companyId(); 
		Optional<RequestOfEachCompany> requestCom = this.requestRep.getRequestByCompany(companyId);
		if(requestCom.isPresent()){
			return new RequestOfEachCompanyDto(requestCom.get().getSelectOfApproversFlg().value,
					convertToDto(requestCom.get().getRequestAppDetailSettings()));
		}
		return null;
	}
}
