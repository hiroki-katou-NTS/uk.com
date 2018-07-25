package nts.uk.shr.infra.web.util;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Optional;

import nts.uk.shr.com.program.ProgramsManager;
import nts.uk.shr.com.program.WebAppId;

public class FilterHelper {
	

	public static Optional<String> detectProgram(String requestPath){
		Iterator<Entry<WebAppId, String>> iterator = FilterConst.webApps.entrySet().iterator();
		Entry<WebAppId, String> entry = null;
		int startIndex = -1, endIndex = requestPath.lastIndexOf("?");
		String partialPath = null;
		WebAppId appId = null;

		while (iterator.hasNext()) {
			entry = iterator.next();
			startIndex = requestPath.indexOf(entry.getValue());
			if (startIndex != -1) {
				startIndex += entry.getValue().length();
				partialPath = endIndex == -1 ? requestPath.substring(startIndex)
						: requestPath.substring(startIndex, endIndex);
				appId = entry.getKey();
				break;
			}
		}
		return ProgramsManager.idOf(appId, partialPath);
	}
	
	public static String detectWebapi(String requestPath){
		Iterator<Entry<WebAppId, String>> iterator = FilterConst.webApps.entrySet().iterator();
		Entry<WebAppId, String> entry = null;
		int startIndex = -1;

		while (iterator.hasNext()) {
			entry = iterator.next();
			startIndex = requestPath.indexOf(entry.getValue());
			if (startIndex != -1) {
				startIndex += entry.getValue().length();
				break;
			}
		}
		return entry.getValue();
	}
}
