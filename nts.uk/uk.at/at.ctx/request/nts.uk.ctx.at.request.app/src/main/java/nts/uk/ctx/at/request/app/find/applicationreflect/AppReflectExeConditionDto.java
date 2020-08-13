package nts.uk.ctx.at.request.app.find.applicationreflect;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.applicationreflect.service.AppReflectExecutionCondition;

@NoArgsConstructor
@AllArgsConstructor
public class AppReflectExeConditionDto {
   private int applyBeforeSchedule;
   private int evenIfScheduleConfirmed;
   private int evenIfRecordConfirmed;

   public static AppReflectExeConditionDto fromDomain(AppReflectExecutionCondition domain) {
       return new AppReflectExeConditionDto(domain.getApplyBeforeWorkSchedule().value,
               domain.getEvenIfScheduleConfirmed().value,
               domain.getEvenIfWorkRecordConfirmed().value);
   }
}
