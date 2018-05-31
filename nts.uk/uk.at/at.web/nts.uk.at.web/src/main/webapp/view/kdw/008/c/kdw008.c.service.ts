module nts.uk.at.view.kdw008.c {
    export module service {
        export class Service {
            paths = {
                findAll: "at/record/businesstype/findBusinessTypeSorted",
                findAllMonth: "at/record/attendanceitem/monthly/findall",             
                updateBusinessTypeSorted: "at/record/businesstype/updateBusinessTypeSorted",
                updateMonth: "at/function/monthlycorrection/updatebusinessMonth",
                
                getAllBusiness: "at/function/monthlycorrection/getallbusiness"
            };

            constructor() {

            }

            findAll(): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.findAll);
                return nts.uk.request.ajax("at", _path);
            };
            
            findAllMonth(): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.findAllMonth);
                return nts.uk.request.ajax("at", _path);
            };

            updateBusinessTypeSorted(UpdateBusinessTypeSortedCommand: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.updateBusinessTypeSorted, {businessTypeSortedDtos: UpdateBusinessTypeSortedCommand});
            };
            
            updateMonth(MonthlyRecordWorkTypeCmd: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.updateMonth, MonthlyRecordWorkTypeCmd);
            };
            
            getAllBusiness(): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.getAllBusiness);
                return nts.uk.request.ajax("at", _path);
            };
        }
    }
}
