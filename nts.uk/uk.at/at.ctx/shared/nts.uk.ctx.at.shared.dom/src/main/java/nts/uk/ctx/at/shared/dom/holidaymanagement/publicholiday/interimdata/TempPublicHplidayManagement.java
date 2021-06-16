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
public class TempPublicHplidayManagement extends InterimRemain implements DomainAggregate{
	
	/**
	 * 公休使用日数
	 */
	private DayOfVacationUse publicHplidayUseNumber;
	
	
	/**
	 * コンストラクタ
	 */
	public TempPublicHplidayManagement(String remainManaID, String sID, GeneralDate ymd, CreateAtr creatorAtr, 
			RemainType remainType){
		super(remainManaID, sID, ymd, creatorAtr, remainType);
		this.publicHplidayUseNumber = new DayOfVacationUse(0.0);
	}
	
	public TempPublicHplidayManagement(String remainManaID, String sID, GeneralDate ymd, CreateAtr creatorAtr, 
			RemainType remainType,DayOfVacationUse publicHplidayUseNumber){
		super(remainManaID, sID, ymd, creatorAtr, remainType);
		this.publicHplidayUseNumber = publicHplidayUseNumber;
	}

}
