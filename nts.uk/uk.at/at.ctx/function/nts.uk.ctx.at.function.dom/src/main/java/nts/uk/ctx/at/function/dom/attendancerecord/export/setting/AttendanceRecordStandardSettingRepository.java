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
	void add(AttendanceRecordStandardSetting domain);

	/**
	 * Update OutputPeriodSetting
	 * @param domain
	 */
    void update(AttendanceRecordStandardSetting domain);
    
    /**
     * Get the domain model "Attendant output item standard setting"-"Attendant output item setting" by company ID
     * 	「出勤簿の出力項目定型設定」－「出勤簿の出力項目設定」
     * @param compnayId
     */
    public Optional<AttendanceRecordStandardSetting> getStandardByCompanyId(String companyId);
    
    /**
     * Find by company id and code.
     *
     * @param compayny the companyId
     * @param code the code
     * @return the optional
     */
    public Optional<AttendanceRecordStandardSetting> findByCompanyIdAndCode(String companyId, String code);
    
    
    /**
     * Find by company code layout id.
     *
     * @param compayny the companyId
     * @param code the code
     * @param layoutId the layout id
     * @return the optional
     */
    public Optional<AttendanceRecordStandardSetting> findByCompanyCodeLayoutId(String companyId, String code, String layoutId);
}
