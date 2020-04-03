module nts.uk.at.view.kdp002.a {
    
    import ajax = nts.uk.request.ajax;
    
    export module service {
        let url = {
            startPage: 'at/record/stamp/management/personal/startPage',
        }

        export function startPage(): JQueryPromise<any> {
            return ajax("at", url.startPage);
        }
    }

}

