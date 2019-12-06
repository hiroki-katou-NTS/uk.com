module nts.uk.at.view.kmr002.a.service {
    var paths = {
        startScreen: "at/record/reservation/bento/find"
    }
    
    export function startScreen(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.startScreen, param);
    }
}
