package nts.uk.ctx.sys.gateway.ws.url;

import java.util.Map;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDateTime;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public class UrlResult {
	
	public String programID;
	
	public String screenID;
	
	public String embeddedId;
    
	public String cID;
    
	public String loginID;
    
	public String contractCD;
    
	public GeneralDateTime expiredDate;
    
	public GeneralDateTime issueDate;
    
	public String sID;
    
	public String sCD;
	
	public Map<String, String> urlTaskValueList;
	
}
