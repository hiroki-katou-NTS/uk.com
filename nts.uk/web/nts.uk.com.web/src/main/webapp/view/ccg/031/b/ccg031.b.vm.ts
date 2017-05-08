module ccg031.b.viewmodel {
    import model = ccg.model;
    import util = nts.uk.util;
    import windows = nts.uk.ui.windows;
    
    export class ScreenModel {
        // Position
        positionRow: KnockoutObservable<number>;
        positionColumn: KnockoutObservable<number>;
        // TopPage Part Type
        listPartType: KnockoutObservableArray<any>;
        selectedPartType: KnockoutObservable<any>;
        //TopPage Part
        allPart: KnockoutObservableArray<model.TopPagePartDto>;
        listPart: KnockoutObservableArray<model.TopPagePartDto>;
        selectedPartID: KnockoutObservable<string>;
        selectedPart: KnockoutObservable<model.TopPagePartDto>;
        listPartColumn: any;
        // External Url
        isExternalUrl: KnockoutObservable<boolean>;
        urlWidth: KnockoutObservable<number>;
        urlHeight: KnockoutObservable<number>;
        url: KnockoutObservable<string>;

        constructor() {
            var self = this;
            // Position
            self.positionRow = ko.observable(null);
            self.positionColumn = ko.observable(null);
            //TopPage Part
            self.listPartType = ko.observableArray([]);
            self.selectedPartType = ko.observable(null);
            self.selectedPartType.subscribe((value) => { self.filterPartType(value); });
            self.allPart = ko.observableArray([]);
            self.listPart = ko.observableArray([]);
            self.selectedPartID = ko.observable(null);
            self.selectedPartID.subscribe((value) => { self.changeSelectedPart(value); });
            self.selectedPart = ko.observable(null);
            self.listPartColumn = [
                { headerText: "ID", key: "topPagePartID", dataType: "string", hidden: true },
                { headerText: "コード", key: "code", dataType: "string", width: 50 },
                { headerText: "名称", key: "name", dataType: "string" },
            ];
            // External Url
            self.isExternalUrl = ko.observable(false);
            self.urlWidth = ko.observable(null);
            self.urlHeight = ko.observable(null);
            self.url = ko.observable(null);
        }

        /** Start Page */
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            // Position
            self.positionRow(windows.getShared("size").row);
            self.positionColumn(windows.getShared("size").column);
            // Get Type and Part
            service.findAll().done((data: any) => {
                // Binding TopPage Part Type
                self.listPartType(data.listTopPagePartType);
                // Binding TopPage Part
                self.allPart(data.listTopPagePart);
                // Default value
                self.selectedPartType(0);
                self.selectFirstPart();
                dfd.resolve();
            }).fail((res) => {
                dfd.fail();
            });
            return dfd.promise();
        }

        /** Filter by Type */
        filterPartType(partType: number): void {
            var isExternalUrl: boolean = (partType === 3);
            this.isExternalUrl(isExternalUrl);
            if (isExternalUrl !== true) {
                var listPart = _.filter(this.allPart(), ['type', partType]);
                this.listPart(listPart);
                this.isExternalUrl(isExternalUrl);
                this.selectFirstPart();
            }
        }

        /** Change Selected Part */
        changeSelectedPart(partID: string): void {
            var selectedPart: model.TopPagePartDto = _.find(this.allPart(), ['topPagePartID', partID]);
            this.selectedPart(selectedPart);
        }

        /** Select first Part */
        selectFirstPart(): void {
            var firstPart: model.TopPagePartDto = _.head(this.listPart());
            (firstPart !== undefined) ? this.selectedPartID(firstPart.topPagePartID) : this.selectedPartID(null);
        }

        /** Submit Dialog */
        submitDialog(): void {
            var self = this;
            // Default is External Url
            var name: string = "外部URL";
            var width: number = self.urlWidth();
            var height: number = self.urlHeight();
            var topPagePartID: string = "";
            var topPagePartType: number = null;
            var url: string = self.url();
            
            // In case is TopPagePart
            if (self.selectedPartType() !== 3) {
                name = self.selectedPart().name;
                width = self.selectedPart().width;
                height = self.selectedPart().height;
                topPagePartID = self.selectedPart().topPagePartID;
                topPagePartType = self.selectedPart().type;
                url = "";
            }
            
            var placement: model.Placement = new model.Placement(
                util.randomId(), name,
                self.positionRow(), self.positionColumn(),
                width, height, url, topPagePartID, topPagePartType);
            windows.setShared("placement", placement, false);
            windows.close();
        }

        /** Close Dialog */
        closeDialog(): void {
            windows.close();
        }

    }

}