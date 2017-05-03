module ccg031.b.viewmodel {
    export class ScreenModel {
        // Position
        positionRow: KnockoutObservable<number>;
        positionColumn: KnockoutObservable<number>;
        // TopPage Part Type
        listPartType: KnockoutObservableArray<any>;
        selectedPartType: KnockoutObservable<any>;
        //TopPage Part
        listPart: KnockoutObservableArray<any>;
        selectedPart: KnockoutObservable<any>;
        listPartColumn: any;
        // External Url
        urlWidth: KnockoutObservable<number>;
        urlHeight: KnockoutObservable<number>;
        url: KnockoutObservable<string>;

        constructor() {
            this.positionRow = ko.observable(null);
            this.positionColumn = ko.observable(null);
            this.listPartType = ko.observableArray([]);
            this.selectedPartType = ko.observable(null);
            this.listPart = ko.observableArray([]);
            this.selectedPart = ko.observable(null);
            this.listPartColumn = [
                { headerText: "ID", key: "topPagePartID", dataType: "string", hidden: true },
                { headerText: "コード", key: "code", dataType: "string" },
                { headerText: "名称", key: "name", dataType: "string" },
            ];
            this.urlWidth = ko.observable(null);
            this.urlHeight = ko.observable(null);
            this.url = ko.observable(null);
        }

        /** Start Page */
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            // Position
            self.positionRow(nts.uk.ui.windows.getShared("size").row);
            self.positionColumn(nts.uk.ui.windows.getShared("size").column);
            // Get Type and Part
            service.findAll().done((data: any) => {
                console.log(data);
                self.listPartType(data.listTopPagePartType);
                console.log(self.listPartType());
                dfd.resolve();
            }).fail((res) => {
                dfd.fail();
            });
            return dfd.promise();
        }
    }

    /** TopPage Part */
    class TopPagePart {
        id: number;
        partType: string;
        code: string;
        name: string;
        constructor(id: number, partType: string, code: string, name: string) {
            this.id = id;
            this.partType = partType;
            this.code = code;
            this.name = name;
        }
    }
}