package nts.uk.shr.infra.web.util.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDateTime;

@Getter
@AllArgsConstructor
public class MenuRequestInfo {
	
	private final String ip;
	
	private final GeneralDateTime requestedTime;
	
	private final String url;

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MenuRequestInfo){
			MenuRequestInfo target = (MenuRequestInfo) obj;
			return ip.equals(target.ip) && requestedTime.equals(target.requestedTime) && url.equals(target.url);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return ip.hashCode() + requestedTime.hashCode() + url.hashCode();
	}
	
	
}
