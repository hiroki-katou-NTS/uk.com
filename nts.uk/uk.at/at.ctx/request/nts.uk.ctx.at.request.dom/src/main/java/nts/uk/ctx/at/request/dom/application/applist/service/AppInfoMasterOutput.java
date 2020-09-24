package nts.uk.ctx.at.request.dom.application.applist.service;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.applist.service.param.ListOfApplication;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SyEmployeeImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WkpInfo;

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
	
	/**
	 * Map<<社員ID, 期間> 職場情報> 
	 */
	private Map<Pair<String, DatePeriod>, WkpInfo> mapWkpInfo;
	
}
