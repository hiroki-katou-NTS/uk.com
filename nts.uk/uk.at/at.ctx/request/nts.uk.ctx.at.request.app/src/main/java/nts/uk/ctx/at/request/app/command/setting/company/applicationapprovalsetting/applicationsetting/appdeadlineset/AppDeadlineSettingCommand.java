package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.setting.UseDivision;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset.AppDeadlineSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset.Deadline;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset.DeadlineCriteria;
import org.apache.commons.lang3.BooleanUtils;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppDeadlineSettingCommand {
	/**
	 * 利用区分
	 */
	private boolean useAtr;
	
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
	
	public AppDeadlineSetting toDomain() {
		return new AppDeadlineSetting(
				EnumAdaptor.valueOf(BooleanUtils.toInteger(useAtr), UseDivision.class),
				closureId, 
				new Deadline(deadline), 
				EnumAdaptor.valueOf(deadlineCriteria, DeadlineCriteria.class));
	}
}
