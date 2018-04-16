package nts.uk.ctx.at.record.dom.approvalmanagement.dailyperformance.algorithm;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParamDayApproval {
  private String employeeId;
  private List<ContentApproval> contentApproval;
}
