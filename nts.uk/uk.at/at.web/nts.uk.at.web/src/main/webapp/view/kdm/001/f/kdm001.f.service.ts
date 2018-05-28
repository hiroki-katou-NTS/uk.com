module nts.uk.at.view.kdm001.f {
    import format = nts.uk.text.format;
    export module service {
        var paths: any = {
            getBySidDatePeriod: "at/record/remainnumber/getBySidDatePeriod/{0}/{1}",
            insertpayoutMan: "at/record/remainnumber/insertpayoutMan",
        }
        
        export function getBySidDatePeriod(empId:string,subOfHDID:string): JQueryPromise<any> {
            return nts.uk.request.ajax(format(paths.getBySidDatePeriod, empId, subOfHDID));
        }
        
        export function insertpayoutMan(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.insertpayoutMan, command);
        }

    }
}