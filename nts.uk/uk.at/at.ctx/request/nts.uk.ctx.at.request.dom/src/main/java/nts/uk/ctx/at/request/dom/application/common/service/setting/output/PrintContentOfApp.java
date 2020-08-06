package nts.uk.ctx.at.request.dom.application.common.service.setting.output;

import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;

/**
 * 申請の印刷内容
 * @author Doan Duy Hung
 *
 */
@Getter
public class PrintContentOfApp {
	
	/**
	 * 会社名
	 */
	private String companyName;
	
	/**
	 * 事前事後区分
	 */
	private PrePostAtr prePostAtr;
	
	/**
	 * 承認者欄の内容
	 */
	
	/**
	 * 振休振出申請の印刷内容
	 */
	
	/**
	 * 申請開始日
	 */
	private GeneralDate appStartDate;
	
	/**
	 * 申請者の社員情報
	 */
	private List<EmployeeInfoImport> employeeInfoLst;
}
