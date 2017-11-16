module nts.uk.at.view.kml002.g.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {
        unitPriceItems: KnockoutObservableArray<any>;
        uPCd: KnockoutObservable<number>;
        radioMethod: KnockoutObservableArray<any>;
        selectedMethod: KnockoutObservable<number>;
        attrLabel: KnockoutObservable<String>;
        itemNameLabel: KnockoutObservable<String>;
        verticalId: KnockoutObservable<number>;


        constructor() {
            var self = this;

            var data = nts.uk.ui.windows.getShared("KML002_A_DATA");

            self.verticalId = ko.observable(data.itemId);
            self.attrLabel = ko.observable(data.attribute);
            self.itemNameLabel = ko.observable(data.itemName);

            self.unitPriceItems = ko.observableArray([
                { uPCd: 0, uPName: nts.uk.resource.getText("KML002_53") },
                { uPCd: 1, uPName: nts.uk.resource.getText("KML002_54") },
                { uPCd: 2, uPName: nts.uk.resource.getText("KML002_55") },
                { uPCd: 3, uPName: nts.uk.resource.getText("KML002_56") },
                { uPCd: 4, uPName: nts.uk.resource.getText("KML002_57") }
            ]);

            self.uPCd = ko.observable(0);

            self.radioMethod = ko.observableArray([
                { id: 0, name: nts.uk.resource.getText("KML002_62") },
                { id: 1, name: nts.uk.resource.getText("KML002_63") },
                { id: 2, name: nts.uk.resource.getText("KML002_64") }
            ]);

            self.selectedMethod = ko.observable(0);
        }

        /**
         * Start page.
         */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            dfd.resolve();
            return dfd.promise();
        }

        submit() {
            var self = this;
            var data = {
                verticalId: self.verticalId(),
                unitPriceCtg: self.uPCd(),
                attendanceDecisionCls: self.selectedMethod(),
            };

            nts.uk.ui.windows.setShared("KML002_G_DATA", data);
            nts.uk.ui.windows.close(); 
        }

        cancel() {
            var self = this;
             nts.uk.ui.windows.close(); 

        }
    }

    export interface IUnitPrice {
        verticalId?: number;
        unitPriceCtg?: String;
        attendanceDecisionCls?: number;
    }
    class UnitPrice {
        verticalId: KnockoutObservable<number> = ko.observable(0);
        unitPriceCtg: KnockoutObservable<String> = ko.observable('');
        attendanceDecisionCls: KnockoutObservable<number> = ko.observable(0);
        constructor(param: IUnitPrice) {
            this.verticalId(param.verticalId || 0);
            this.unitPriceCtg(param.unitPriceCtg || '');
            this.attendanceDecisionCls(param.attendanceDecisionCls || 0);
        }
    }
}