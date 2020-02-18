package nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime;

/**
 * 予約締め時刻枠
 * @author Doan Duy Hung
 *
 */
public enum ReservationClosingTimeFrame {
	
	/**
	 * 枠1
	 */
	FRAME1(1,"枠1"),
	
	/**
	 * 枠2
	 */
	FRAME2(2,"枠2");
	 
	public int value;
	
	public String name;
	
	ReservationClosingTimeFrame(int type,String name){
		this.value = type;
		this.name = name;
	}
}
