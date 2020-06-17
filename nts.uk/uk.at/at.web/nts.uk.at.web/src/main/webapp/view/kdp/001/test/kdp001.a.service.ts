module nts.uk.at.view.kdp001.a {
    
    import ajax = nts.uk.request.ajax;
    
    export module service {

        let url = {
            startPage: 'at/record/stamp/management/personal/startPage',
            getError: 'at/record/stamp/management/personal/getDailyError' 
        }

        export function startPage(): JQueryPromise<any> {
            return ajax("at", url.startPage);
        }

        export function getError(data): JQueryPromise<any> {
            return ajax("at", url.getError + "/" + data.pageNo + "/" + data.buttonDisNo);
        }
    }

}

