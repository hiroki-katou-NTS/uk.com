package nts.uk.ctx.at.schedule.dom.shift.pattern.service;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingRepository;
import nts.uk.ctx.at.shared.dom.WorkInformation;

import javax.inject.Inject;
import java.util.Optional;

public class WorkMonthlySettingServiceImpl implements WorkMonthlySettingService{

    @Inject
    private WorkMonthlySettingRepository workMonthlySettingRepository;

    @Override
    public Optional<AtomTask> register(WorkInformation.Require requireWorkInfo, Require require,WorkMonthlySetting workMonthlySetting, Boolean isOverwrite) {

        workMonthlySetting.checkForErrors(requireWorkInfo);

        if(require.checkRegister(workMonthlySetting.getCompanyId().v(), workMonthlySetting.getMonthlyPatternCode().v(), workMonthlySetting.getYmdk())) {
            if(isOverwrite) {
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

    public static interface Require {
        boolean checkRegister(String companyId, String monthlyPatternCode, GeneralDate generalDate);
        void add(WorkMonthlySetting workMonthlySetting);
        void update(WorkMonthlySetting workMonthlySetting);
    }

}
