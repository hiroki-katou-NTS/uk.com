package nts.uk.screen.at.app.dailyperformance.correction.dto.cache;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPControlDisplayItem;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DatePeriodInfo;
import nts.arc.time.calendar.period.DatePeriod;
/**
 * 日別実績の修正の状態
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DPCorrectionStateParam implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//対象期間
	private DatePeriod period;
	
	//対象社員
	private List<String> employeeIds;
	
	//表示形式
	private Integer displayMode;
	
   //選択社員
	private List<String> lstEmpSelect;
	
	//TODO: ロック状態
	
	//表示する項目
	private DPControlDisplayItem displayItem;
	
	//TODO: 使用するフォーマット
	
	//個人別
	private DatePeriodInfo dateInfo;
	
	//遷移元の画面
	private Boolean transferDesScreen;
	
	//対象職場
	private List<String> lstWrkplaceId;
	
	//応援来た社員
	private List<String> lstEmpsSupport;

	public DPCorrectionStateParam(DatePeriod period, List<String> employeeIds, Integer displayMode,
			List<String> lstEmpSelect, DPControlDisplayItem displayItem, DatePeriodInfo dateInfo,
			Boolean transferDesScreen) {
		super();
		this.period = period;
		this.employeeIds = employeeIds;
		this.displayMode = displayMode;
		this.lstEmpSelect = lstEmpSelect;
		this.displayItem = displayItem;
		this.dateInfo = dateInfo;
		this.transferDesScreen = transferDesScreen;
	}
}
