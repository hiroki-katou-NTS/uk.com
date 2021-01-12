package nts.uk.screen.com.app.find.ccg005;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.command.jobtitle.dto.JobTitleInfoDto;
import nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfoRepository;

/*
 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG005_ミニ在席照会.B：権限の設定.メニュー別OCD.権限の設定を取得
 * 
 */
@Stateless
public class PermissionSettingsScreenQuery {
	
	@Inject
	private JobTitleRepository jobTitleRepo;
	
	@Inject 
	private JobTitleInfoRepository jobTitleInfoRepo;
	
	//	基準日から職位を取得
	private JobTitleInfoDto getJobTitleInfo(String sId, GeneralDate date) {
		//	ドメインモデル「職位」を取得
		
		return null;
	}
}
