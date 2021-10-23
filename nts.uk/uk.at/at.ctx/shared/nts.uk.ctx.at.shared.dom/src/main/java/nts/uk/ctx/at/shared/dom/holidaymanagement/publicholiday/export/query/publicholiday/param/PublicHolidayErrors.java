package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.param;

/**
 * 
 * @author hayata_maekawa
 *
 * 公休エラー
 */
public class PublicHolidayErrors {

	/*
	 * 公休未取得エラー
	 */
	
	public boolean publicHolidayUnGetError = false;
	
	
	
	public PublicHolidayErrors(boolean Error){
		this.publicHolidayUnGetError = Error;
	}
	
}
