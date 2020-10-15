module nts.uk.at.view.ksu001.ab.service {
    var paths: any = {
        // init
        initScreen: "screen/at/schedule/basicschedule"
    }

    export function initScreen(): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.initScreen);
    }
}