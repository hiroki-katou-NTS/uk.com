package nts.uk.ctx.at.schedule.app.command.schedule.setting.displaycontrol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.ScheQualifySet;
import nts.uk.shr.com.context.AppContexts;

/**
 * TanLV
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheQualifySetCommand {
	/** 資格コード  */
	private String qualifyCode;
	
	/**
	 * To domain
	 * @param companyId
	 * @param conditionNo
	 * @return
	 */
	public ScheQualifySet toDomain(String qualifyCode){
		String companyId = AppContexts.user().companyId();
		return ScheQualifySet.createFromJavaType(companyId, qualifyCode);
 	}
}
