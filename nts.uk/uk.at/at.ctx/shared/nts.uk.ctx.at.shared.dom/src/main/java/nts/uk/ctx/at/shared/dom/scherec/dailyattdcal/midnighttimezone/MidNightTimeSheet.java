package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.midnighttimezone;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 深夜時間帯
 * @author keisuke_hoshina
 *
 */
@Getter
public class MidNightTimeSheet extends AggregateRoot{

	//会社コード
	private String companyId;
	
	//開始時刻
	private TimeWithDayAttr start;
	
	//終了時刻
	private TimeWithDayAttr end;

	/**
	 * Constructor 
	 */
	public MidNightTimeSheet(String companyId, TimeWithDayAttr start, TimeWithDayAttr end) {
		super();
		this.companyId = companyId;
		this.start = start;
		this.end = end;
	}
	
	/**
	 * 計算範囲の取得
	 * @return　計算範囲
	 */
	public TimeSpanForCalc getTimeSpan() {
		return new TimeSpanForCalc(this.start, this.end);
	}
	
	/**
	 * 最大の計算範囲に対する深夜時間帯を返す
	 * @return 深夜時間帯リスト
	 */
	public List<TimeSpanForCalc> getAllMidNightTimeSheet() {
		List<TimeSpanForCalc> timeSheets = new ArrayList<>();
		timeSheets.add(this.getTimeSpan().shiftBack(1440));	//前日
		timeSheets.add(this.getTimeSpan());					//当日
		timeSheets.add(this.getTimeSpan().shiftAhead(1440));	//翌日
		timeSheets.add(this.getTimeSpan().shiftAhead(2880));	//翌々日
		return timeSheets;
	}
}
