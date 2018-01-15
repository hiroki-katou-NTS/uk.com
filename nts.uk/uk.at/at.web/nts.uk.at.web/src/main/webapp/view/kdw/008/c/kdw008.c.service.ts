module nts.uk.at.view.kdw008.c {
    export module service {
        export class Service {
            paths = {
                findAll: "at/record/businesstype/findBusinessTypeSorted",
                updateBusinessTypeSorted: "at/record/businesstype/updateBusinessTypeSorted"
            };

            constructor() {

            }

            findAll(): JQueryPromise<any> {
                let _path = nts.uk.text.format(this.paths.findAll);
                return nts.uk.request.ajax("at", _path);
            };

            updateBusinessTypeSorted(UpdateBusinessTypeSortedCommand: any): JQueryPromise<any> {
                return nts.uk.request.ajax("at", this.paths.updateBusinessTypeSorted, {businessTypeSortedDtos: UpdateBusinessTypeSortedCommand});
            };
        }
    }
}
