module nts.uk.at.view.kdm001.f {
    export module service {
        var paths: any = {
            getBySidDatePeriod: "at/record/remainnumber/paymana/getBySidDatePeriod",
        }
        
        export function getBySidDatePeriod(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getBySidDatePeriod, command);
        }

    }
}