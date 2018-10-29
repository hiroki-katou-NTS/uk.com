package nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
@AllArgsConstructor
@Getter
public class ApproverApproveImport {
	/**
	 * 対象日
	 */
	private GeneralDate date;
	
	/**
	 * 対象者
	 */
	private String employeeID;
	
	/**
	 * 承認者
	 */
	private List<ApproverEmpImport> authorList;
}
