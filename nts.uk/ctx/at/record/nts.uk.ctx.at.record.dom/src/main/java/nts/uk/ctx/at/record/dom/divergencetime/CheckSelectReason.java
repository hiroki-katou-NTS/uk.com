package nts.uk.ctx.at.record.dom.divergencetime;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CheckSelectReason{
	@Inject
	private DivergenceTimeRepository divTimeRepo;
	
	String companyId = AppContexts.user().companyCode();
	
	public boolean checkSelectReason(int selectUseSet,int divTimeId){
		if(selectUseSet==0){
			return false;
		}else
		{
			List<DivergenceReason> lst = this.divTimeRepo.getDivReasonByCode(companyId, Integer.valueOf(divTimeId));
			if(lst.isEmpty()){
				return false;
			}else{
				return true;
			}
		}
	}
}
