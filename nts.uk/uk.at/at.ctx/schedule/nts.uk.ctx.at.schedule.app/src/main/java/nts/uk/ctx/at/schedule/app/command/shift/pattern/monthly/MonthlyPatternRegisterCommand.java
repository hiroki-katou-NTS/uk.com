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
    // 上書き
    private boolean isOverWrite;
    // 勤務サイクルのカレンダー
    private List<WorkMonthlySettingDto> workMonthlySetting;

    public WorkMonthlySetting toDomain(WorkMonthlySettingDto workMonthlySetting){
        String companyId = AppContexts.user().companyId();
        WorkMonthlySettingGetMementoImpl memento = new WorkMonthlySettingGetMementoImpl(companyId,
                workMonthlySetting.workInformation.workTypeCode, workMonthlySetting.workInformation.workTimeCode,
                GeneralDate.fromString(workMonthlySetting.ymdk,"yyyy-MM-d"), workMonthlySetting.monthlyPatternCode);
        return new WorkMonthlySetting(memento);
    }

    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    public static class WorkMonthlySettingDto{

        // 勤務情報
        private WorkInformationDto workInformation;

        // 年月日
        private String ymdk;

        // 月間パターンコード
        private String monthlyPatternCode;
    }

    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    public static class WorkInformationDto{
        private String workTypeCode;
        private String workTimeCode;
    }



    @AllArgsConstructor
    static class WorkMonthlySettingGetMementoImpl implements WorkMonthlySettingGetMemento {

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
