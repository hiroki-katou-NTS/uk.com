package nts.uk.ctx.at.schedule.dom.shift.pattern.service;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.shared.dom.WorkInformation;

import javax.ejb.Stateless;
import java.util.Optional;

/**
 * 月間パターンの勤務情報を登録する
 */
@Stateless
public class WorkMonthlySettingService {

    public static Optional<AtomTask> register(Require require, WorkMonthlySetting workMonthlySetting, Boolean isOverwrite) {

        workMonthlySetting.checkForErrors(require);
        if (require.checkRegister(workMonthlySetting.getCompanyId().v(), workMonthlySetting.getMonthlyPatternCode().v(), workMonthlySetting.getYmdk())) {
            if (isOverwrite) {
                return Optional.of(AtomTask.of(() -> {
                    require.update(workMonthlySetting);
                }));
            }
        } else {
            return Optional.of(AtomTask.of(() -> {
                require.add(workMonthlySetting);
            }));
        }
        return Optional.empty();
    }

    public static interface Require extends WorkInformation.Require {
        boolean checkRegister(String companyId, String monthlyPatternCode, GeneralDate generalDate);

        void add(WorkMonthlySetting workMonthlySetting);

        void update(WorkMonthlySetting workMonthlySetting);
    }

}
