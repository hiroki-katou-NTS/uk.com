package nts.uk.ctx.pereg.app.command.process.checkdata;

import nts.arc.layer.app.command.AsyncCommandHandlerContext;

public interface CheckDataEmployeeServices {
	
	/**
	 * 任意期間集計Mgrクラス
	 * @param CheckDataFromUI excuteCommand
	 * @param async 同期コマンドコンテキスト
	 */
	<C> void manager(CheckDataFromUI excuteCommand, AsyncCommandHandlerContext<C> async);
	

}
