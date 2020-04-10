package nts.uk.ctx.at.schedule.app.find.schedule.employeeinfo.rank;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.Rank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankRepository;
import nts.uk.shr.com.context.AppContexts;



/**
 * 
 * @author hieult
 *
 */
@Stateless
public class GetRankEmpFinder {
  
	@Inject
	private RankRepository rankRepository;
	
	//ランクマスタを優先順に並べて取得する   
	public List<Rank> getRankbyPriorityOrder(){
		String companyId = AppContexts.user().companyId();
		List<Rank> listRank = rankRepository.getListRank(companyId);
		
		// Query ランクマスタを優先順に並べて取得する
		// 優先順でランクリストを取得する(ログイン会社ID)
		//List
		
		return null;
		
	} 
}
