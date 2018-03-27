package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceEmployeeDto;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * TODO
 */
@Getter
@Setter
public class MonthlyPerformanceCorrectionDto {

	private Set<ItemValue> itemValues;
	private MPControlDisplayItem lstControlDisplayItem;
	private String employmentCode;
	private List<DailyPerformanceEmployeeDto> lstEmployee;
	private List<MPDataDto> lstData;
	private Map<String, String > data;
	private List<MPCellStateDto> lstCellState;
	/**
	 * list fixed header
	 */
	private List<MPHeaderDto> lstFixedHeader;
	
	/**
	 * コメント
	 */
	private String comment;
	/**
	 * 処理年月
	 * YYYYMM
	 */
	private int processDate;
	
	/**
	 * 締め名称
	 * 画面項目「A4_2：対象締め日」
	 */
	private String closureName;
	/**
	 * ・実績期間：List＜実績期間＞
	 * 画面項目「A4_5：実績期間選択肢」
	 */
	private List<ActualTime> lstActualTimes;
	/**
	 * 期間：取得した期間に一致する
	 * ※一致する期間がない場合は、先頭を選択状態にする
	 */
	private DatePeriod selectedActualTime;
	
	public MonthlyPerformanceCorrectionDto(){
		super();
		this.lstFixedHeader = MPHeaderDto.GenerateFixedHeader();
		this.lstData = new ArrayList<>();
		this.lstCellState = new ArrayList<>();
		this.lstControlDisplayItem = new MPControlDisplayItem();
		this.itemValues = new HashSet<>();
		this.data = new HashMap<>();
		//this.dPErrorDto = new ArrayList<>();
		
	}
}
