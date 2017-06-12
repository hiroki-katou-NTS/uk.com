module nts.uk.at.view.kmk004.a {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            findCompanySetting: 'ctx/at/share/vacation/setting/annualpaidleave/find/managedistinct',
            saveCompanySetting: 'ctx/at/share/vacation/setting/annualpaidleave/save',
        };

        export function saveCompanySetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveCompanySetting, command);
        }

        export function findCompanySetting(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findCompanySetting);
        }

    }
}
