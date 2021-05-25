package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.param;


import java.util.ArrayList;
import java.util.List;

import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.carryForwarddata.PublicHolidayCarryForwardData;

/**
 * 
 * @author hayata_maekawa
 *
 *		公休の集計結果
 *
 */

public class AggrResultOfPublicHoliday {

	/*
	 * 公休情報
	 */
	public List<PublicHolidayInformation> publicHolidayInformation;
	
	/*
	 * 繰越データ
	 */
	public List<PublicHolidayCarryForwardData> publicHolidayCarryForwardData;
	
	/**
	 * コンストラクタ
	 */
	public AggrResultOfPublicHoliday(){
		this.publicHolidayInformation = new ArrayList<>();
		this.publicHolidayCarryForwardData = new ArrayList<>();
	}
	
	public AggrResultOfPublicHoliday(List<PublicHolidayInformation> publicHolidayInformation, 
			List<PublicHolidayCarryForwardData> publicHolidayCarryForwardData){
		this.publicHolidayInformation = publicHolidayInformation;
		this.publicHolidayCarryForwardData = publicHolidayCarryForwardData;
	}
}
