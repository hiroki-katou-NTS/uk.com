package nts.uk.ctx.hr.develop.dom.setting.datedisplay;

import java.util.List;

/**
 * @author anhdt
 *
 */
public interface DateDisplaySettingRepository {
	List<DateDisplaySetting> getSettingByCompanyId(String companyId);
	List<DateDisplaySetting> getSettingByCompanyIdAndProgramId(String programId, String companyId);
}
