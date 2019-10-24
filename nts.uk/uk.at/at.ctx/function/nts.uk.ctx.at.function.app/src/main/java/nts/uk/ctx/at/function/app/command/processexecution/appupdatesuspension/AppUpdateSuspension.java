package nts.uk.ctx.at.function.app.command.processexecution.appupdatesuspension;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.AppInterrupDaily;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.AppInterrupDailyRepository;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppInterrupMon;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppInterrupMonRepository;
/**
 * 承認ルート更新中断処理
 * @author tutk
 *
 */
@Stateless
public class AppUpdateSuspension {
	@Inject
	private AppInterrupDailyRepository appInterrupDailyRepository;
	
	@Inject
	private AppInterrupMonRepository appInterrupMonRepository;
	
	public void updateSuspension(String exeId,boolean isDaily) {
		if(isDaily) {
			//承認中間データ中断管理（日別実績）．中断状態を更新
			appInterrupDailyRepository.addAppInterrupDaily(new AppInterrupDaily(exeId,true));
		}else {
			//承認中間データ中断管理（月別実績）．中断状態を更新
			appInterrupMonRepository.addAppInterrupMon(new AppInterrupMon(exeId,true));
		}
	}
}
