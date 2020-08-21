package nts.uk.ctx.at.schedule.app.query.MonthlyPattern;


import lombok.val;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;

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
            val listPatternDto = new ArrayList<PatternDto>();
            listMonthly.forEach(e->listPatternDto.add(new PatternDto(e.getCompanyId().toString(),
                    e.getMonthlyPatternCode().toString(),e.getMonthlyPatternName().toString())));
            rs.listMonthlyPattern = listPatternDto;
        }
        return rs;
    }
}
