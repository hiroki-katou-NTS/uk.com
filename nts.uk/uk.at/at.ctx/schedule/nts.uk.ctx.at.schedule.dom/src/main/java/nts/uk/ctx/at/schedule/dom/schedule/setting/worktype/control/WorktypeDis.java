package nts.uk.ctx.at.schedule.dom.schedule.setting.worktype.control;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.schedule.dom.schedule.setting.WorkTypeDisplaySetting;
import nts.uk.ctx.at.schedule.dom.schedule.setting.functioncontrol.UseAtr;
/**
 * 
 * @author phongtq
 *
 */
@Getter
@AllArgsConstructor
public class WorktypeDis {
	/** 会社ID */
	private String companyId;
	
	/**  利用区分*/
	private UseAtr useAtr;
	
	private List<WorkTypeDisplaySetting> workTypeList;
	
	public static WorktypeDis createFromJavaType(String companyId, Integer useAtr,List<WorkTypeDisplaySetting> workTypeList){
		return new WorktypeDis(companyId, EnumAdaptor.valueOf(useAtr, UseAtr.class),workTypeList);
	}
}
