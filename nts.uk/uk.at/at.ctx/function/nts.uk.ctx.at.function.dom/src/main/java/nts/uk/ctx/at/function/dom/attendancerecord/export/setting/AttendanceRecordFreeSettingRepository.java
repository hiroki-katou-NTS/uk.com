package nts.uk.ctx.at.function.dom.attendancerecord.export.setting;

import java.util.Optional;

/**
 * @author nws-ducnt
 * 
 * Repository
 */
public interface AttendanceRecordFreeSettingRepository {
	/**
	 * Add new OutputPeriodSetting
	 * @param domain
	 */
	void save(AttendanceRecordFreeSetting domain);
    
    /**
     * Get domain model 「出勤簿の出力項目自由設定」－「出勤簿の出力項目設定」 with company ID and employee ID
     * @param companyId
     * @param employeeId
     * @return
     */
    public Optional<AttendanceRecordFreeSetting> getOutputItemsByCompnayAndEmployee(String companyId, String employeeId);

    /**
     * Get domain model 「出勤簿の出力項目自由設定」 with companyId, employeeId, layoutId and exportCd
     * @param companyId 会社ID
     * @param employeeId 社員ID
     * @param layoutId 出力レイアウトID
     * @param exportCD 複製元コード
     * @return the optional
     */
    public Optional<AttendanceRecordFreeSetting> getFreeWithLayoutId(String companyId, String employeeId, String layoutId, String exportCD);
}
