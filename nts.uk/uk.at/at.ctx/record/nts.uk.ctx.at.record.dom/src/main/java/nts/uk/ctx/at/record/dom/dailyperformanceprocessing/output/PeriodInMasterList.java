package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 期間内マスタ一覧
 * @author nampt
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class PeriodInMasterList {

	private String employeeId;
	
	/** マスタ一覧 **/
	private List<MasterList> masterLists; 
}
