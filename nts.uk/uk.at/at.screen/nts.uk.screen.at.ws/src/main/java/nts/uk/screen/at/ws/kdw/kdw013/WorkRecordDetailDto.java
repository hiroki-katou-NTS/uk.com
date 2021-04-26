package nts.uk.screen.at.ws.kdw.kdw013;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.ouen.WorkDetailsParam;
import nts.uk.screen.at.app.kdw013.a.ActualContent;

/**
 * 
 * @author tutt
 *
 */
public class WorkRecordDetailDto {
	// 年月日
	private GeneralDate date;
	
	// 社員ID
	private String sId;
	
	// 作業詳細リスト
	private List<WorkDetailsParamDto> lstWorkDetailsParamDto;
	
	// 実績内容
	private Optional<ActualContentDto> actualContent;
}
