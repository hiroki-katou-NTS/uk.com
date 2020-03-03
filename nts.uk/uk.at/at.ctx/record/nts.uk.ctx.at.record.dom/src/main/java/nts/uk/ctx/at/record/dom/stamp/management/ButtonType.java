package nts.uk.ctx.at.record.dom.stamp.management;

import lombok.Getter;
import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * ボタン種類
 * @author phongtq
 *
 */
@Value
public class ButtonType implements DomainValue{
	
	/** 予約区分 */
	@Getter
	private ReservationArt reservationArt;
	
	/** 打刻種類 */
	@Getter
	private StampType stampType;

	/**
	 * [C-0] ボタン種類(予約区分, 打刻種類)
	 * @param textColor
	 * @param stampType
	 */
	public ButtonType(ReservationArt reservationArt, StampType stampType) {
		this.reservationArt = reservationArt;
		this.stampType = stampType;
	}
	
	/**
	 * 打刻区分を取得する
	 * @return
	 */
	public boolean checkStampType(){
		if(this.getStampType() == null) return false;
		
		return true;	
	}
}
