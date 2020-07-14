package nts.uk.ctx.at.request.dom.application.common.service.setting.output;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.common.service.other.AppDetailContent;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.PrePostInitAtr;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@NoArgsConstructor
@Setter
@Getter
public class AppDispInfoRelatedDateOutput {
	
	/**
	 * 事前事後区分
	 */
	private PrePostInitAtr prePostAtr;
	
	/**
	 * 表示する実績内容
	 */
	private List<AchievementOutput> achievementOutputLst;
	
	/**
	 * 表示する事前申請内容
	 */
	private List<AppDetailContent> appDetailContentLst;
	
}
