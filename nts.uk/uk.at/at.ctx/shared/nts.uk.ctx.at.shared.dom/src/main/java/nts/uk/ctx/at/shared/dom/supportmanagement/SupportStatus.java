package nts.uk.ctx.at.shared.dom.supportmanagement;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 応援状況
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.応援管理.応援状況
 * @author dan_pv
 */
@Getter
@AllArgsConstructor
public enum SupportStatus {
	
	/** 応援に行かない **/
	DO_NOT_GO( 0 ),
	/** 応援に来ない **/
	DO_NOT_COME( 1 ),
	/** 応援に行く(終日) **/
	GO_ALLDAY( 2 ),
	/** 応援に行く(時間帯) **/
	GO_TIMEZONE( 3 ),
	/** 応援に来る(終日) **/
	COME_ALLDAY( 4 ),
	/** 応援に来る(時間帯) **/
	COME_TIMEZONE( 5 ),
	;

	private final int value;
	
	/**
	 * 勤務予定を変更できるか
	 * @return
	 */
	public boolean canUpdateWorkSchedule() {
		switch (this) {
			case DO_NOT_GO:
				return true;
			case DO_NOT_COME:
				return false;
			case GO_ALLDAY:
				return true;
			case GO_TIMEZONE:
				return true;
			case COME_ALLDAY:
				return true;
			case COME_TIMEZONE:
				return false;
			default:
				return false;
		}
	}
	
	/**
	 * 勤務予定を確定できるか	
	 * @return
	 */
	public boolean canConfirmWorkSchedule() {
		switch (this) {
			case DO_NOT_GO:
				return true;
			case DO_NOT_COME:
				return false;
			case GO_ALLDAY:
				return false;
			case GO_TIMEZONE:
				return true;
			case COME_ALLDAY:
				return true;
			case COME_TIMEZONE:
				return false;
			default:
				return false;
		}
	}

}
