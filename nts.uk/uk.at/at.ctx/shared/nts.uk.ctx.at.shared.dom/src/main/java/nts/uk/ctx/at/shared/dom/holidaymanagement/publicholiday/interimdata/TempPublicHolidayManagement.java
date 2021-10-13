package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.interimdata;


import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.DayOfVacationUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;

/**
 * 
 * @author hayata_maekawa
 * 暫定公休管理データ
 *
 */

@Getter
@Setter
public class TempPublicHolidayManagement extends InterimRemain implements DomainAggregate{
	
	/**
	 * 公休使用日数
	 */
	private DayOfVacationUse useDays;
	
	
	/**
	 * コンストラクタ
	 */
	public TempPublicHolidayManagement(String remainManaID, String sID, GeneralDate ymd, CreateAtr creatorAtr, 
			RemainType remainType){
		super(remainManaID, sID, ymd, creatorAtr, remainType);
		this.useDays = new DayOfVacationUse(0.0);
	}
	
	public TempPublicHolidayManagement(String remainManaID, String sID, GeneralDate ymd, CreateAtr creatorAtr, 
			RemainType remainType,DayOfVacationUse publicHolidayUseNumber){
		super(remainManaID, sID, ymd, creatorAtr, remainType);
		this.useDays = publicHolidayUseNumber;
	}

}
