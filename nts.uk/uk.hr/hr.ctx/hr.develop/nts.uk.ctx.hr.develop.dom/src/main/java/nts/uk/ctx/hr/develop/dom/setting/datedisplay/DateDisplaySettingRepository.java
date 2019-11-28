package nts.uk.ctx.hr.develop.dom.setting.datedisplay;

import java.util.List;

/**
 * @author anhdt
 *
 */
public interface DateDisplaySettingRepository {
	List<DateDisplaySetting> getSettingByCompanyId(String companyId);

	List<DateDisplaySetting> getSettingByCompanyIdAndProgramId(String programId, String companyId);

	void update(String companyId, List<DateDisplaySetting> domain);

	void add(String companyId, List<DateDisplaySetting> dateSetting);
}
