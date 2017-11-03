package nts.uk.ctx.at.record.app.command.log;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.log.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExeStateOfCalAndSum;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author hieult processingMonth
 *
 */
@Stateless
@Transactional
public class EmpCalAndAggregationCommandHanlder extends CommandHandler<EmpCalAndAggregationCommand> {

	@Inject EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;
	
	@Override
	protected void handle(CommandHandlerContext<EmpCalAndAggregationCommand> context) {

        /** ログインしている社員の社員IDを取得する (Lấy login EmployeeID) */
		String employeeID = AppContexts.user().employeeId();
		/** 実行ボタン押下時のシステム日付を取得する (lấy thời gian hệ thống) */
		GeneralDate  systemTime = GeneralDate.today();
		
		
		
		
	}

}
