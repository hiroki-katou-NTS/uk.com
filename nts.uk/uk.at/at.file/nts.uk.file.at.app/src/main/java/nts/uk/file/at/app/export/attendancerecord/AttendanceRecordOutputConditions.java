package nts.uk.file.at.app.export.attendancerecord;


import lombok.Data;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ExportSettingCode;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ItemSelectionType;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * UKDesign.UniversalK.就業.KWR_帳表.KWR002₌出勤簿 (Phiếu chấm công).ユーザ固有情報.出勤簿の出力条件
 * 	出勤簿の出力条件
 * The Class AttendanceRecordOutputConditions.
 */
@Data
public class AttendanceRecordOutputConditions {
	//	項目選択区分
	private ItemSelectionType selectionType;
	//	定型選択_コード
	private ExportSettingCode standardSelectionCode;
	//	自由設定_コード
	private ExportSettingCode freeSelectionCode;
	// 	会社ID
	private CompanyId companyId;
	// 	ユーザID
	private String userId;
	//	ゼロ表示区分
	private ZeroDisplayType zeroDisplayType;
	//	自由設定_出力レイアウトID
	private String freeSettingLayoutId;
	//	定型選択_出力レイアウトID
	private String standardSelectionLayoutId;
	
	public static AttendanceRecordOutputConditions createFromJavaType(AttendanceRecordOutputConditionsDto dto) {
		AttendanceRecordOutputConditions condition = new AttendanceRecordOutputConditions();
		condition.setSelectionType(ItemSelectionType.valueOf(dto.getSelectionType()));
		condition.setStandardSelectionCode(new ExportSettingCode(dto.getStandardSelectionCode()));
		condition.setCompanyId(new CompanyId(dto.getCompanyId()));
		condition.setUserId(dto.getUserId());
		condition.setZeroDisplayType(ZeroDisplayType.valueOf(dto.getZeroDisplayType()));
		condition.setFreeSettingLayoutId(dto.getFreeSettingLayoutId());
		condition.setStandardSelectionLayoutId(dto.getStandardSelectionLayoutId());
		return condition;
	}
}
