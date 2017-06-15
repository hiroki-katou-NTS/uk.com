module nts.uk.at.view.kmk004.a {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            findCompanySetting: 'ctx/at/shared/employment/statutory/worktime/company/find',
            saveCompanySetting: 'ctx/at/shared/employment/statutory/worktime/company/save',
        };

        export function saveCompanySetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveCompanySetting, command);
        }

        export function findCompanySetting(year: number): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findCompanySetting + '/' + year);
        }

    }
}
