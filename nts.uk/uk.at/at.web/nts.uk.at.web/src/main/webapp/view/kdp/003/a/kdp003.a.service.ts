module nts.uk.at.view.kdp003.a {
    export module service {
        
        const SLASH = "/";
        
        var paths = {
           getDataStartPage: ""
        }
        
        export function a(isExist: boolean): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.getDataStartPage + SLASH + isExist);
        }
        
        
    }
}

