package nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * ６０H超休情報
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class HolidayOver60hInfo implements Cloneable {

	/**
	 * 年月日
	 *  */
	private GeneralDate ymd;

	/**
	 * 残数
	*/
	private HolidayOver60hRemainingNumber remainingNumber;

	/**
	 付与残数データ
	*/
	private List<HolidayOver60hGrantRemaining> grantRemainingList;

	/**
	 * コンストラクタ
	 */
	public HolidayOver60hInfo(){
		this.ymd = GeneralDate.min();
		this.remainingNumber = new HolidayOver60hRemainingNumber();
		this.grantRemainingList = new ArrayList<HolidayOver60hGrantRemaining>();
	}
}
