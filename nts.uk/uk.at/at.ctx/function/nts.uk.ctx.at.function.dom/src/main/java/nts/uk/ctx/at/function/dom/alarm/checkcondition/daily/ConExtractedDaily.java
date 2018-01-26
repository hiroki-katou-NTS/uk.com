package nts.uk.ctx.at.function.dom.alarm.checkcondition.daily;
/**
 * 
 * @author tutk
 *
 */
public enum ConExtractedDaily {
	
	ALL(0,"全て"),
	
	CONFIRMED_DATA(1,"確認済のデータ"),
	
	UNCONFIRMER_DATA(2,"未確認のデータ");

	
	public int value;
	
	public String nameId;
	
	private ConExtractedDaily (int value,String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
