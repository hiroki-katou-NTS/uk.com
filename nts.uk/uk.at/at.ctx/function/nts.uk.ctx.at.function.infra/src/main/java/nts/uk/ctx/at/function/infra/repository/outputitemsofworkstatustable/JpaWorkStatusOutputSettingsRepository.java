package nts.uk.ctx.at.function.infra.repository.outputitemsofworkstatustable;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailSelectionAttendanceItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.WorkStatusOutputSettings;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.WorkStatusOutputSettingsRepository;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;

import javax.ejb.Stateless;
import java.util.List;
@Stateless
public class JpaWorkStatusOutputSettingsRepository extends JpaRepository implements WorkStatusOutputSettingsRepository {
    @Override
    public List<WorkStatusOutputSettings> getListWorkStatusOutputSettings(String cid, SettingClassificationCommon settingClassification) {

        return null;
    }

    @Override
    public List<WorkStatusOutputSettings> getListOfFreelySetOutputItems(String cid, SettingClassificationCommon settingClassification, String employeeId) {
        return null;
    }

    @Override
    public WorkStatusOutputSettings getWorkStatusOutputSettings(String cid, String settingId) {
        return null;
    }

    @Override
    public void createNewFixedPhrase(String cid, WorkStatusOutputSettings outputSettings, List<OutputItem> outputItemList, List<OutputItemDetailSelectionAttendanceItem> attendanceItemList) {

    }

    @Override
    public void createNewFreeSetting(String cid, String employeeId, WorkStatusOutputSettings outputSettings, List<OutputItem> outputItemList, List<OutputItemDetailSelectionAttendanceItem> attendanceItemList) {

    }

    @Override
    public void updateBoilerplateSelection(String cid, String settingId, WorkStatusOutputSettings outputSettings, List<OutputItem> outputItemList, List<OutputItemDetailSelectionAttendanceItem> attendanceItemList) {

    }

    @Override
    public void updateFreeSettings(String cid, String settingId, WorkStatusOutputSettings outputSettings, List<OutputItem> outputItemList, List<OutputItemDetailSelectionAttendanceItem> attendanceItemList) {

    }

    @Override
    public void deleteTheSettingDetails(String cid, String settingId) {

    }

    @Override
    public void duplicateConfigurationDetails(String cid, String replicationSourceSettingId, String replicationDestinationSettingId, OutputItemSettingCode duplicateCode, OutputItemSettingName copyDestinationName) {

    }

    @Override
    public boolean exist(OutputItemSettingCode code, String cid) {
        return false;
    }

    @Override
    public boolean exist(OutputItemSettingCode code, String cid, String employeeId) {
        return false;
    }
}
