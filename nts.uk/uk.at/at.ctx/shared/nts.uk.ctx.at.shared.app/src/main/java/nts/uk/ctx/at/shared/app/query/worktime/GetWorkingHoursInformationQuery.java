package nts.uk.ctx.at.shared.app.query.worktime;

import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Query <就業時間帯情報を取得する>
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.就業時間帯.App.就業時間帯情報を取得する.就業時間帯情報を取得する
 */
@Stateless
public class GetWorkingHoursInformationQuery {
    @Inject
    private WorkTimeSettingRepository workTimeRepo;

    public List<WorkTimeSetting> getListWorkTimeSetting(String companyId, List<String> workTimeCodes){
        return workTimeRepo
                .getListWorkTimeSetByListCode(companyId,workTimeCodes);

    }
}
