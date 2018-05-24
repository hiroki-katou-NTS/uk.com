module nts.uk.at.view.kdm001.f {
    import format = nts.uk.text.format;
    export module service {
        var paths: any = {
            getBySidDatePeriod: "at/record/remainnumber/getBySidDatePeriod/{0}/{1}",
        }
        
        export function getBySidDatePeriod(empId:string,subOfHDID:string): JQueryPromise<any> {
            return nts.uk.request.ajax(format(paths.getBySidDatePeriod, empId, subOfHDID));
        }

    }
}