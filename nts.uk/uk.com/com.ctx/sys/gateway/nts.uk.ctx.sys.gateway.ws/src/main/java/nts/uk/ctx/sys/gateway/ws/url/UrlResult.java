package nts.uk.ctx.sys.gateway.ws.url;

import java.util.Map;

import lombok.AllArgsConstructor;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public class UrlResult {
	
	public String programID;
	
	public String screenID;
	
	public Map<String, String> urlTaskValueList;
	
}
