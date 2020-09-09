package nts.uk.ctx.at.request.dom.application.applist.service;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.applist.service.param.ListOfApplication;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SyEmployeeImport;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppInfoMasterOutput {
	
	/**
	 * 申請データ
	 */
	private ListOfApplication listOfApplication;
	
	/**
	 * Map<社員ID, 個人社員基本情報>
	 */
	private Map<String, SyEmployeeImport> mapEmpInfo;
}
