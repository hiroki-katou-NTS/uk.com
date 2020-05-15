package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * ボタン種類
 * @author phongtq
 *
 */
@AllArgsConstructor
public class ButtonType implements DomainValue{
	
	/** 予約区分 */
	@Getter
	private ReservationArt reservationArt;
	
	/** 打刻種類 */
	@Getter
	private Optional<StampType> stampType;

	/**
	 * [C-0] ボタン種類(予約区分, 打刻種類)
	 * @param textColor
	 * @param stampType
	 */
	
	/**
	 * 打刻区分を取得する
	 * @return
	 */
	public boolean checkStampType(){
		return this.getStampType().isPresent();	
	}
}
