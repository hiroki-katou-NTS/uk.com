package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.ErrorFlg;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantDays;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantNum;

/**
 * 次回特休付与
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class NextAnnualLeaveGrant {

	/** 付与年月日 */
	private GeneralDate grantDate;
	/** 付与日数 */
	private GrantDays grantDays;
	/** 付与回数 */
	private GrantNum times;
	/** 期限日 */
	private GeneralDate deadLine;
	
	/**
	 * エラーフラグ
	 */
	private Optional<ErrorFlg> errorFlg;
	
	/**
	 * コンストラクタ
	 */
	public NextAnnualLeaveGrant(){
		this.grantDate = GeneralDate.today();
		this.grantDays = new GrantDays(0.0);
		this.times = new GrantNum(0);
		this.deadLine = GeneralDate.max();
		this.errorFlg = Optional.empty();
	}
	
//	/**
//	 * コンストラクタ
//	 */
//	public NextSpecialLeaveGrant(GeneralDate grantDateIn, GrantDays grantDaysIn){
//		
//		this.grantDate = grantDateIn;
//		this.grantDays = grantDaysIn;
//	}
}
