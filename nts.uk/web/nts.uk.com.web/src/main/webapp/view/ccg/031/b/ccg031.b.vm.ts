module ccg031.b.viewmodel {
    export class ScreenModel {
        // Position
        positionRow: KnockoutObservable<number>;
        positionColumn: KnockoutObservable<number>;
        // TopPage Part Type
        listPartType: KnockoutObservableArray<any>;
        selectedPartType: KnockoutObservable<any>;
        //TopPage Part
        allPart: KnockoutObservableArray<any>;
        listPart: KnockoutObservableArray<any>;
        selectedPart: KnockoutObservable<any>;
        listPartColumn: any;
        // External Url
        urlWidth: KnockoutObservable<number>;
        urlHeight: KnockoutObservable<number>;
        url: KnockoutObservable<string>;

        constructor() {
            var self = this;
            self.positionRow = ko.observable(null);
            self.positionColumn = ko.observable(null);
            self.listPartType = ko.observableArray([]);
            self.selectedPartType = ko.observable(null);
            console.log(self.selectedPartType());
            //self.selectedPartType.subscribe((value) => { self.filterPartType(value); });
            self.allPart = ko.observableArray([]);
            self.listPart = ko.observableArray([]);
            self.selectedPart = ko.observable(null);
            self.listPartColumn = [
                { headerText: "ID", key: "topPagePartID", dataType: "string", hidden: true },
                { headerText: "コード", key: "code", dataType: "string" },
                { headerText: "名称", key: "name", dataType: "string" },
            ];
            self.urlWidth = ko.observable(null);
            self.urlHeight = ko.observable(null);
            self.url = ko.observable(null);
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
                self.allPart(data.listTopPagePart);
                self.listPart(data.listTopPagePart);
                //self.selectedPartType(0);
                dfd.resolve();
            }).fail((res) => {
                dfd.fail();
            });
            return dfd.promise();
        }

        /** Close Dialog */
        filterPartType(partType: number): void {
            var listPart = _.filter(this.allPart(), ['type', partType]);
            this.listPart(listPart);
            console.log(this.listPart());
        }

        /** Close Dialog */
        closeDialog(): void {
            this.selectedPartType(0);
            //nts.uk.ui.windows.close();
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