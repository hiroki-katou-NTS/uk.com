package nts.uk.ctx.at.request.app.find.application.approvalstatus;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
@AllArgsConstructor
public class ApprovalStatusByIdDto {
	//対象職場ID
	String selectedWkpId;
	
	//職場ID(リスト)
	List<String> listWkpId;
	
	//保持.期間(開始日～終了日)
	GeneralDate startDate;
	GeneralDate endDate;
	
	//保持.雇用コード(リスト)
	List<String> listEmpCode;
	
}
