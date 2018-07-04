package nts.uk.ctx.exio.dom.exo.condset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.error.BusinessException;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class StdOutputCondSetService {
	
	@Inject
	private StdOutputCondSetRepository stdOutputCondSetRepository;
	
	public Map<String, String> excuteCopy(String copyDestinationCode,String destinatioName, String conditionSetCd, int overwite){
		Map<String, String> resultExvuteCopy = new HashMap<>();
		String cid = AppContexts.user().companyId();
		List<StdOutputCondSet> lstStdOutputCondSet = stdOutputCondSetRepository.getOutputCondSetByCidAndconditionSetCd(cid,conditionSetCd);
		if(lstStdOutputCondSet.size() > 0){
			if(overwite == 1){
				//result = OK
				//overwrite = TO
				resultExvuteCopy.put("result", "OK");
				resultExvuteCopy.put("overwrite", "TO");
			}else{
				throw new BusinessException("Msg_3");
			}
		}else{
			//result = OK
			//overwrite = DONOT
			resultExvuteCopy.put("result", "OK");
			resultExvuteCopy.put("overwrite", "DONOT");
		}
		resultExvuteCopy.put("copyDestinationCode", copyDestinationCode);
		resultExvuteCopy.put("destinatioName", destinatioName);
		
	return resultExvuteCopy;
	}
	
	
	
	public Map<String, String> getResultAndOverwrite(String result, String overwite){
		Map<String, String> resultAndOverwrite = new HashMap<>();
		resultAndOverwrite.put("result", result);
		resultAndOverwrite.put("overwite", overwite);
		return resultAndOverwrite;
	}
	
	
}
