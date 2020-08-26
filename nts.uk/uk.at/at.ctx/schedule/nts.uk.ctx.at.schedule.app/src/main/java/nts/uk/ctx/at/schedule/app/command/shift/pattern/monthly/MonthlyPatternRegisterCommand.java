package nts.uk.ctx.at.schedule.app.command.shift.pattern.monthly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkingCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingGetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;

@Value
public class MonthlyPatternRegisterCommand {
    private boolean isOverWrite;
    private List<WorkMonthlySettingDto> workMonthlySetting;

    public WorkMonthlySetting toDomain(WorkMonthlySettingDto workMonthlySetting){
        WorkMonthlySettingGetMementoImpl memento = new WorkMonthlySettingGetMementoImpl(workMonthlySetting.companyId,
                workMonthlySetting.workInformation.workTypeCode, workMonthlySetting.workInformation.workTimeCode,
                workMonthlySetting.ymdk, workMonthlySetting.monthlyPatternCode);
        return new WorkMonthlySetting(memento);
    }

    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    public class WorkMonthlySettingDto{

        private final String companyId = AppContexts.user().companyId();

        private WorkInformationDto workInformation;

        private GeneralDate ymdk;

        private String monthlyPatternCode;
    }

    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    public class WorkInformationDto{
        private String workTypeCode;
        private String workTimeCode;
    }



    @AllArgsConstructor
    class WorkMonthlySettingGetMementoImpl implements WorkMonthlySettingGetMemento {

        private String cid;

        private String workTypeCode;

        private String workingCode;

        private GeneralDate ymk;

        private String monthlyPatternCode;

        @Override
        public CompanyId getCompanyId() {
            return new CompanyId(cid);
        }

        @Override
        public nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode getWorkTypeCode() {
            return new nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode(workTypeCode);
        }

        @Override
        public WorkingCode getWorkingCode() {
            return new WorkingCode(workingCode);
        }

        @Override
        public GeneralDate getYmdK() {
            return ymk;
        }

        @Override
        public MonthlyPatternCode getMonthlyPatternCode() {
            return new MonthlyPatternCode(monthlyPatternCode);
        }
    }

}
