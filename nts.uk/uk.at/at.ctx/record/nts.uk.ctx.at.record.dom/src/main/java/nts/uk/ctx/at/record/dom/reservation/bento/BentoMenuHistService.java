package nts.uk.ctx.at.record.dom.reservation.bento;

import java.util.Collections;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistory;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * 予約構成を追加する
 */
@Stateless
public class BentoMenuHistService {

    public static AtomTask register(Require require, String companyID, DatePeriod period) {
    	// 1: 取得する
    	Optional<BentoMenuHistory> opBentoMenuHistory = require.getBentoMenu(companyID, GeneralDate.fromString("9999/12/31", "yyyy/MM/dd"));
    	
    	return AtomTask.of(() -> {
    		// 2: 更新する
    		if(opBentoMenuHistory.isPresent()) {
    			BentoMenuHistory bentoMenuHistory = opBentoMenuHistory.get();
    			if(bentoMenuHistory.getHistoryItem().span().start().afterOrEquals(period.start())) {
    				throw new BusinessException("Msg_102");
    			}
    			DatePeriod newPeriod = new DatePeriod(bentoMenuHistory.getHistoryItem().span().start(), period.start().decrease()) ;
    			bentoMenuHistory.getHistoryItem().changeSpan(newPeriod);
    			require.update(bentoMenuHistory);
    		}
    		// 3: 追加する
    		String guid = IdentifierUtil.randomUniqueId();
    		BentoMenuHistory newBentoMenuHistory = new BentoMenuHistory(guid, new DateHistoryItem(guid, period), Collections.emptyList());
    		require.add(newBentoMenuHistory);
    	});
    }
    public static interface Require {
    	//【R-1】弁当メニュを取得する
    	Optional<BentoMenuHistory> getBentoMenu(String companyID, GeneralDate date);
        //【R-2】弁当メニュー履歴を更新する
        void update(BentoMenuHistory bentoMenuHistory);
        //【R-3】弁当メニュー履歴を追加
        void add(BentoMenuHistory bentoMenuHistory);
    }
}