package nts.uk.ctx.at.shared.app.query.MonthlyPattern;


import lombok.val;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Query: 月間パターン一覧を取得する
 */
@Stateless
public class MonthlyPatternQuery {
    @Inject
    private MonthlyPatternRepository  repository;

    public MonthlyPatternDto getListMonthlyPattern(){
        val cid = AppContexts.user().companyId();
        val listMonthly = repository.findAll(cid);
        val rs = new MonthlyPatternDto();
        if(listMonthly!=null){
            rs.listMonthlyPattern = listMonthly;
        }
        return rs;
    }
}
