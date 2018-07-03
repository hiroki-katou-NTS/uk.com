package nts.uk.ctx.at.function.dom.attendancerecord.item;

import nts.uk.ctx.at.function.dom.attendancerecord.export.ExportAtr;

/**
 * The Interface AttendanceRecordDisplay.
 */
//出勤簿で表示する項目
public interface AttendanceRecordDisplay {

	/**
	 * Switch export item.
	 *
	 * @param outputAtr the output atr
	 * @param number the number
	 * @return the int
	 */
	//出力項目を切り替える
	public int switchExportItem(ExportAtr outputAtr,int number);
	
	/**
	 * Switch export registration destination.
	 *
	 * @param outputAtr the output atr
	 * @param number the number
	 * @return the int
	 */
	//出力項目の登録先を切り替える
	public int switchExportRegistrationDestination(ExportAtr outputAtr,int number);
	
	/**
	 * Return export item name.
	 *
	 * @return the int
	 */
	//出力項目の名称を返す
	public int returnExportItemName();
	
	/**
	 * Gets the name display.
	 *
	 * @return the name display
	 */
	public String getNameDisplay();
}
