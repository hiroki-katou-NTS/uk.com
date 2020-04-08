module nts.uk.at.view.kdp002.a {
    
    import ajax = nts.uk.request.ajax;
    
    export module service {
        let url = {
            startPage: 'at/record/stamp/management/personal/startPage',
            getStampData: 'at/record/stamp/management/personal/stamp/getStampData'
        }

        export function startPage(): JQueryPromise<any> {
            return ajax("at", url.startPage);
        }

        export function getStampData(data): JQueryPromise<any> {
            return ajax("at", url.getStampData, data);
        }
    }

}

