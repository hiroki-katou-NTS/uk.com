package nts.uk.ctx.exio.dom.exo.condset;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;


@Stateless
public class StdOutputCondSetService {

	
	public Map<String, String> getResultAndOverwrite(String result, String overwite){
		Map<String, String> resultAndOverwrite = new HashMap<>();
		resultAndOverwrite.put("result", result);
		resultAndOverwrite.put("overwite", overwite);
		return resultAndOverwrite;
	}
	
	
}
