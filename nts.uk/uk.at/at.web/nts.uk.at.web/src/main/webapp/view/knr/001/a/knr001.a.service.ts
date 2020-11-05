module nts.uk.at.view.knr001.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        getAll: "screen/at/empInfoTerminal/getAll",
        getDetails: "screen/at/empInfoTerminal/getDetails/{0}/{1}",
        register: "at/record/empinfoterminal/register",
        update: "at/record/empinfoterminal/update",
        delete: "at/record/empinfoterminal/delete"
    };

    /**
    * Get All
    */
    export function getAll(): JQueryPromise<any> {
        return ajax(paths.getAll);
    }

    /**
    * Get Details
    */
    export function getDetails(empInfoTerCode, workLocationCD): JQueryPromise<any> {
        return ajax(format(paths.getDetails, empInfoTerCode, workLocationCD));
    }

   /**
    * Register
    */
    export function register(data: any) {
        return ajax(paths.register, data);
    }

    /**
    * Update
    */
    export function update(data: any) {
        return ajax(paths.update, data);
    }
    /**
    * Remove
    */
    export function removeEmpInfoTer(code) {
        return ajax(format(paths.getDetails, code));
    }



    //saveAsExcel
        
    export function saveAsExcel(languageId: String): JQueryPromise<any> {
        let program = nts.uk.ui._viewModel.kiban.programName().split(" ");
        let domainType = "KNR001";
        if (program.length > 1) {
            program.shift();
            domainType = domainType + program.join(" ");
        }
        return nts.uk.request.exportFile('/masterlist/report/print', {
            domainId: "",
            domainType: domainType, languageId: languageId, reportType: 0
        });
    }
}