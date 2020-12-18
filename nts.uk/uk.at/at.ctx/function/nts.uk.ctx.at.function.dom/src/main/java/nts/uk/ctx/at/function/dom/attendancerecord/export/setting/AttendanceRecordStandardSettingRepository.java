package nts.uk.ctx.at.function.dom.attendancerecord.export.setting;

import java.util.Optional;

/**
 * @author nws-ducnt
 * Repository
 */
public interface AttendanceRecordStandardSettingRepository {
	/**
	 * Add new OutputPeriodSetting
	 * @param domain
	 */
	void save(AttendanceRecordStandardSetting domain);
    
    /**
     * Get the domain model "Attendant output item standard setting"-"Attendant output item setting" by company ID
     * 	「出勤簿の出力項目定型設定」－「出勤簿の出力項目設定」
     * @param compnayId
     */
    public Optional<AttendanceRecordStandardSetting> getStandardByCompanyId(String companyId);
    
    /**
     * Get the domain model 「出勤簿の出力項目定型設定」 with companyId, layoutId and exportCd
     * @param companyId 会社ID
     * @param layoutId 出力レイアウトID
     * @param exportCD 複製元コード
     * @return the optional
     */
    public Optional<AttendanceRecordStandardSetting> getStandardWithLayoutId(String companyId, String layoutId, String exportCD);
}
