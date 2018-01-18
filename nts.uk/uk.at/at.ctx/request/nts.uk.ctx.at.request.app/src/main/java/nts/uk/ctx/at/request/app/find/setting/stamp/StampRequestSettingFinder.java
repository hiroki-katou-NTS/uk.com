package nts.uk.ctx.at.request.app.find.setting.stamp;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.app.find.setting.stamp.dto.StampRequestSettingDto;
import nts.uk.ctx.at.request.dom.setting.stamp.StampRequestSetting;
import nts.uk.ctx.at.request.dom.setting.stamp.StampRequestSettingRepository;


@Stateless
public class StampRequestSettingFinder {

	@Inject
	private StampRequestSettingRepository stampRequestSettingRepository;
	
	public StampRequestSettingDto findByCompanyID(String companyID){
		StampRequestSetting stampRequestSetting = this.stampRequestSettingRepository.findByCompanyID(companyID);
		return new StampRequestSettingDto(
				companyID, 
				stampRequestSetting.getTopComment(), 
				stampRequestSetting.getTopCommentFontColor(), 
				stampRequestSetting.getTopCommentFontWeight(), 
				stampRequestSetting.getBottomComment(), 
				stampRequestSetting.getBottomCommentFontColor(), 
				stampRequestSetting.getBottomCommentFontWeight(), 
				stampRequestSetting.getResultDisp(), 
				stampRequestSetting.getSupFrameDispNO(), 
				stampRequestSetting.getStampPlaceDisp(), 
				stampRequestSetting.getStampAtrWorkDisp(), 
				stampRequestSetting.getStampAtrGoOutDisp(), 
				stampRequestSetting.getStampAtrCareDisp(), 
				stampRequestSetting.getStampAtrSupDisp(), 
				stampRequestSetting.getStampGoOutAtrPrivateDisp(), 
				stampRequestSetting.getStampGoOutAtrPublicDisp(), 
				stampRequestSetting.getStampGoOutAtrCompensationDisp(), 
				stampRequestSetting.getStampGoOutAtrUnionDisp());
	}
	
}
