package nts.uk.ctx.at.schedule.app.command.schedule.setting.displaycontrol;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.ScheDispControl;
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.SchePerInfoAtr;
import nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol.ScheQualifySet;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author tanlv
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheDispControlCommand {
	/** 会社ID */
	private String companyId;
	
	/** 資格表示記号 B1_14 */
	private String personSyQualify;
	
	/** 半日表示区分 B2_4 */
	private int symbolHalfDayAtr;
	
	/** 勤務就業記号表示区分 B2_8 */
	private int symbolAtr;
	
	/** 取得超過表示区分 B3_4 */
	private int pubHolidayExcessAtr;

	/** 取得不足表示区分 B3_8 */
	private int pubHolidayShortageAtr;

	/** 半日記号 */
	private String symbolHalfDayName;
	
	private List<SchePerInfoAtrCommand> schePerInfoAtr;
	
	private List<ScheQualifySetCommand> scheQualifySet;
	
	public ScheDispControl toDomain() {
		String companyId = AppContexts.user().companyId();
		
		List<SchePerInfoAtr> schePerInfoAtr =  null;
		if(this.schePerInfoAtr != null) {
			schePerInfoAtr = this.schePerInfoAtr.stream().map(x-> {
				return SchePerInfoAtr.createFromJavaType(companyId, x.getPersonInfoAtr());
			}).collect(Collectors.toList());
		}		
		
		List<ScheQualifySet> scheQualifySet = null;
		if(this.scheQualifySet != null) {
			scheQualifySet = this.scheQualifySet.stream().map(x-> {
				return new ScheQualifySet(companyId, x.getQualifyCode());
			}).collect(Collectors.toList());
		}		
		
		return ScheDispControl.createFromJavaType(companyId, this.personSyQualify, this.symbolHalfDayAtr, this.symbolAtr, 
				this.pubHolidayExcessAtr, this.pubHolidayShortageAtr, 
				this.symbolHalfDayName, schePerInfoAtr, scheQualifySet);
	}
}
