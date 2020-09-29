module nts.uk.at.view.kdp005.i.service {
    let paths: any = {
        proceed: ""
    };

    export function proceed(param: any): void {
        return nts.uk.request.ajax("at", paths.proceed, param);
    }
}
