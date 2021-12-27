package nts.uk.ctx.at.record.dom.reservation.bento;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistory;

/**
 * 予約構成を編集する
 */
@Stateless
public class UpdateBentoMenuHistService {
    public static AtomTask register(Require require, String companyID, DatePeriod period, GeneralDate startDate){
    	
    	if(startDate.afterOrEquals(period.start())) {
    		throw new BusinessException("Msg_3261");
    	}
    	
    	// 1: 弁当メニュー履歴を取得
    	Optional<BentoMenuHistory> opBeforeBentoMenuHistory = require.getBentoMenu(companyID, startDate.decrease());
    	Optional<BentoMenuHistory> opBentoMenuHistory = require.getBentoMenu(companyID, period.start().decrease());
    	
    	return AtomTask.of(() -> {
    		// 1.1: 以前弁当メニュ履歴を更新
    		if(opBeforeBentoMenuHistory.isPresent()) {
    			BentoMenuHistory beforeBentoMenuHistory = opBeforeBentoMenuHistory.get();
    			DatePeriod newPeriod = new DatePeriod(beforeBentoMenuHistory.getHistoryItem().span().start(), period.start().decrease());
    			beforeBentoMenuHistory.getHistoryItem().changeSpan(newPeriod);
    			require.update(beforeBentoMenuHistory);
    		}
    		// 2: 最新弁当メニュ履歴を変更
    		BentoMenuHistory bentoMenuHistory = opBentoMenuHistory.get();
    		DatePeriod newPeriod = new DatePeriod(period.start(), bentoMenuHistory.getHistoryItem().span().end());
    		bentoMenuHistory.getHistoryItem().changeSpan(newPeriod);
    		require.update(bentoMenuHistory);
    	});
    }
    public static interface Require{
    	//【R-1】弁当メニュを取得する
    	Optional<BentoMenuHistory> getBentoMenu(String companyID, GeneralDate date);
        //【R-2】弁当メニュー履歴を更新する
        void update(BentoMenuHistory bentoMenuHistory);
    }
}
