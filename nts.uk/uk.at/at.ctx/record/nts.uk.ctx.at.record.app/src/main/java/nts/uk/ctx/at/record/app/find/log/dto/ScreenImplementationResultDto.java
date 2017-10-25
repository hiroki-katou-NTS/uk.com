package nts.uk.ctx.at.record.app.find.log.dto;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.record.dom.workrecord.log.TargetPerson;

/**
 * 
 * @author hieult
 *
 */
@Value
public class ScreenImplementationResultDto {
	
	/** 就業計算と集計実行ログ */
	private List<EmpCalAndSumExeLogDto> empCalAndSumExeLogDto;
	
	/** 対象者 */
	private List<TargetPersonDto> targetPersonDto;
	
	/** 実行ログ */
	
	private List<ExecutionLogDto> executionLogDto;
	

}
