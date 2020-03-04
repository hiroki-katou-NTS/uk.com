module nts.uk.pr.view.qmm031.e.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        copyEarth: "ctx/pr/yearend/lifeInsurance/copyEarth"

    }

    export function copyEarth(command: any): JQueryPromise<any> {
        return ajax('pr', paths.copyEarth, command);
    };
}
