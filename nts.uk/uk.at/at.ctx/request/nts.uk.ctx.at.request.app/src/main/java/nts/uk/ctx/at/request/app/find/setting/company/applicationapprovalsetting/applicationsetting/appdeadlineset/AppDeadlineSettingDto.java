package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.UseDivision;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset.AppDeadlineSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset.Deadline;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset.DeadlineCriteria;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppDeadlineSettingDto {
	/**
	 * 利用区分
	 */
	private int useAtr;
	
	/**
	 * 締めＩＤ
	 */
	private int closureId;
	
	/**
	 * 締切日数
	 */
	private int deadline;
	
	/**
	 * 締切基準
	 */
	private int deadlineCriteria;
	
	public static AppDeadlineSettingDto fromDomain(AppDeadlineSetting appDeadlineSetting) {
		return new AppDeadlineSettingDto(
				appDeadlineSetting.getUseAtr().value, 
				appDeadlineSetting.getClosureId(), 
				appDeadlineSetting.getDeadline().v(), 
				appDeadlineSetting.getDeadlineCriteria().value);
	}
	
	public AppDeadlineSetting toDomain() {
		return new AppDeadlineSetting(
				EnumAdaptor.valueOf(useAtr, UseDivision.class), 
				closureId, 
				new Deadline(deadline), 
				EnumAdaptor.valueOf(deadlineCriteria, DeadlineCriteria.class));
	}
}
