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
				stampRequestSetting.getStampAtr_Work_Disp(), 
				stampRequestSetting.getStampAtr_GoOut_Disp(), 
				stampRequestSetting.getStampAtr_Care_Disp(), 
				stampRequestSetting.getStampAtr_Sup_Disp(), 
				stampRequestSetting.getStampGoOutAtr_Private_Disp(), 
				stampRequestSetting.getStampGoOutAtr_Public_Disp(), 
				stampRequestSetting.getStampGoOutAtr_Compensation_Disp(), 
				stampRequestSetting.getStampGoOutAtr_Union_Disp());
	}
	
}
