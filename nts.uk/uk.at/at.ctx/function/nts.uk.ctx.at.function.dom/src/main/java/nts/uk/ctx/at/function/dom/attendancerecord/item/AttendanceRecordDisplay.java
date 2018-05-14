package nts.uk.ctx.at.function.dom.attendancerecord.item;

import nts.uk.ctx.at.function.dom.attendancerecord.export.ExportAtr;

//出勤簿で表示する項目
public interface AttendanceRecordDisplay {

	//出力項目を切り替える
	public int switchExportItem(ExportAtr outputAtr,int number);
	
	//出力項目の登録先を切り替える
	public int switchExportRegistrationDestination(ExportAtr outputAtr,int number);
	
	//出力項目の名称を返す
	public int returnExportItemName();
}
