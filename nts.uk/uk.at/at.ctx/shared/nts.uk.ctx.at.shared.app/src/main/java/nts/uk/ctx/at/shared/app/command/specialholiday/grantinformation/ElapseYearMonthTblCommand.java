package nts.uk.ctx.at.shared.app.command.specialholiday.grantinformation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ElapseYearMonthTblCommand {

	/** 付与回数 */
	private int grantCnt; 
	
	/** 経過年数 */
	private ElapseYearMonthCommand elapseYearMonth; 
}
