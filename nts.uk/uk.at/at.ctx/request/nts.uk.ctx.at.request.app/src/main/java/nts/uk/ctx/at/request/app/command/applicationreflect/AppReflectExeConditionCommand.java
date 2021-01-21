package nts.uk.ctx.at.request.app.command.applicationreflect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.applicationreflect.AppReflectExecutionCondition;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppReflectExeConditionCommand {
   private int applyBeforeSchedule;
   private int evenIfScheduleConfirmed;
   private int evenIfRecordConfirmed;

   public AppReflectExecutionCondition toDomain(String companyId) {
       return AppReflectExecutionCondition.create(companyId, applyBeforeSchedule, evenIfScheduleConfirmed, evenIfRecordConfirmed);
   }
}
