module nts.uk.at.view.kmk004.a {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            findCompanySetting: 'ctx/at/shared/employment/statutory/worktime/company/find',
            saveCompanySetting: 'ctx/at/shared/employment/statutory/worktime/company/save',
            removeCompanySetting: 'ctx/at/shared/employment/statutory/worktime/company/remove',
            findEmploymentSetting: 'ctx/at/shared/employment/statutory/worktime/employment/find',
            findAllEmploymentSetting: 'ctx/at/shared/employment/statutory/worktime/employment/findall',
            saveEmploymentSetting: 'ctx/at/shared/employment/statutory/worktime/employment/save',
            removeEmploymentSetting: 'ctx/at/shared/employment/statutory/worktime/employment/remove',
        };

        export function saveCompanySetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveCompanySetting, command);
        }

        export function findCompanySetting(year: number): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findCompanySetting + '/' + year);
        }

        export function removeCompanySetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.removeCompanySetting, command);
        }

        export function saveEmploymentSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveEmploymentSetting, command);
        }

        export function findEmploymentSetting(request: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findEmploymentSetting, request);
        }

        export function findAllEmploymentSetting(year: number): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findAllEmploymentSetting + '/' + year);
        }

        export function removeEmploymentSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.removeEmploymentSetting, command);
        }

    }
}
