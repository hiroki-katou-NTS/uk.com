package nts.uk.shr.web.componen;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

final class FileTagsHelper {

    static String buildPath(FacesContext context, String specifiedPath) {
        
        String filePath;
        
        if (specifiedPath.charAt(0) == '/') {
            String appPath = ((HttpServletRequest) context.getExternalContext().getRequest()).getContextPath();
            filePath = appPath + specifiedPath;
        } else {
            filePath = specifiedPath;
        }
        
        return appendVersionString(filePath);
    }
    
    private static String appendVersionString (String filePath) {
        
        return filePath + "?";
    }
}
