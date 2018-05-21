module nts.uk.at.view.kdm001.e {
    export module service {
        var paths: any = {
            getBySidDatePeriod: "at/record/remainnumber/paymana/getBySidDatePeriod/{0}/{1}",
        }
        
        export function getBySidDatePeriod(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getBySidDatePeriod, command);
        }

    }
}