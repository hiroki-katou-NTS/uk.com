module nts.uk.at.view.kdm001.e {
    import format = nts.uk.text.format;
    export module service {
        let paths: any = {
            getBySidDatePeriod: "at/record/remainnumber/subhd/getBySidDatePeriod/{0}/{1}",
            insertSubOfHDMan: "at/record/remainnumber/subhd/insertSubOfHDMan",
        }
        
        export function getBySidDatePeriod(empId:string, payoutID:string): JQueryPromise<any> {
            return nts.uk.request.ajax(format(paths.getBySidDatePeriod, empId, payoutID));
        }
        
        export function insertSubOfHDMan(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.insertSubOfHDMan, command);
        }

    }
}