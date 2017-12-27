package nts.uk.ctx.at.record.dom.daily.attendanceleavinggate;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/** 日別実績のPCログオン情報*/
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PCLogOnInfoOfDaily {
	
	/** 社員ID: 社員ID */
	private String employeeId;
	
	/** 年月日: 年月日 */
	private GeneralDate ymd;

	/** ログオン情報: ログオン情報 */
	private List<LogOnInfo> logOnInfo;
}
