package nts.uk.ctx.at.schedule.app.command.schedule.setting.displaycontrol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.SchePerInfoAtr;
import nts.uk.shr.com.context.AppContexts;

/**
 * TanLV
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchePerInfoAtrCommand {
	/** コード */
	private int personInfoAtr;
	
	/**
	 * To domain
	 * @param companyId
	 * @param personInfoAtr
	 * @return
	 */
	public SchePerInfoAtr toDomain(int personInfoAtr){
		String companyId = AppContexts.user().companyId();
		return SchePerInfoAtr.createFromJavaType(companyId, personInfoAtr);
 	}
}
