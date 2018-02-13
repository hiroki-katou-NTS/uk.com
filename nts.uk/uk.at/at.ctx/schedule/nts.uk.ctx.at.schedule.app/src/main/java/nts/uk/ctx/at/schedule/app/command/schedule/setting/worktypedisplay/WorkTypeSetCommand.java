package nts.uk.ctx.at.schedule.app.command.schedule.setting.worktypedisplay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.schedule.setting.WorkTypeDisplaySetting;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author phongtq
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkTypeSetCommand {
	/** 条件NO */
	private String workTypeCode;
	
	/**
	 * To domain
	 * @param companyId
	 * @param workTypeCode
	 * @return
	 */
	public WorkTypeDisplaySetting toDomain(String workTypeCode){
		String companyId = AppContexts.user().companyId();
		return WorkTypeDisplaySetting.createFromJavaType(companyId, workTypeCode);
 	}
}
