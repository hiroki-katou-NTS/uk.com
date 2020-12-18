package nts.uk.file.at.app.export.attendancerecord;

import lombok.Data;

@Data
public class AttendanceRecordOutputConditionsDto {
	//	項目選択区分
	private int selectionType;
	//	定型選択_コード
	private String standardSelectionCode;
	//	自由設定_コード
	private String freeSelectionCode;
	// 	会社ID
	private String companyId;
	// 	ユーザID
	private String userId;
	//	ゼロ表示区分
	private int zeroDisplayType;
	//	自由設定_出力レイアウトID
	private String freeSettingLayoutId;
	//	定型選択_出力レイアウトID
	private String standardSelectionLayoutId;
}
