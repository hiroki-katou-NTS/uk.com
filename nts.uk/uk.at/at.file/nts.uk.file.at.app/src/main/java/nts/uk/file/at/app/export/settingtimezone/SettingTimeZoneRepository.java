package nts.uk.file.at.app.export.settingtimezone;

import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import java.util.List;

public interface  SettingTimeZoneRepository {
    List<MasterData> getListSpecialBonusPayTimeItem(String companyId);
    List<MasterData> getAutoCalSetting(String companyId);
    List<MasterData> getDetailSettingTimeZone(String companyId);
    List<MasterData> getInfoSetUpUseCompany(String companyId);
    List<MasterData> getInfoSetSubUseWorkPlace(String companyId);
    List<MasterData> getInfoSetEmployees(String companyId);
    List<MasterData> getInfoSetUsedWorkingHours(String companyId);


}
