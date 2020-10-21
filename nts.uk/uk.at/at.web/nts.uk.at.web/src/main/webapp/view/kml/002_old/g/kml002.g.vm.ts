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
        genVertId: KnockoutObservable<number>;

        constructor() {
            var self = this;

            var dataTranfer = nts.uk.ui.windows.getShared("KML002_A_DATA");
            var data = dataTranfer.unitPrice;

            self.genVertId = ko.observable(dataTranfer.verticalCalCd);
            self.verticalId = ko.observable(dataTranfer.itemId);
            self.attrLabel = ko.observable(dataTranfer.attribute);
            self.itemNameLabel = ko.observable(dataTranfer.itemName);

            self.unitPriceItems = ko.observableArray([
                { uPCd: 0, uPName: nts.uk.resource.getText("KML002_53") },
                { uPCd: 1, uPName: nts.uk.resource.getText("KML002_54") },
                { uPCd: 2, uPName: nts.uk.resource.getText("KML002_55") },
                { uPCd: 3, uPName: nts.uk.resource.getText("KML002_56") },
                { uPCd: 4, uPName: nts.uk.resource.getText("KML002_57") }
            ]);

            self.radioMethod = ko.observableArray([
                { id: 0, name: nts.uk.resource.getText("KML002_62") },
                { id: 1, name: nts.uk.resource.getText("KML002_63") },
                { id: 2, name: nts.uk.resource.getText("KML002_64") }
            ]);

            if(data != null) {
                self.uPCd = ko.observable(data.unitPrice);
                self.selectedMethod = ko.observable(data.attendanceAtr);
            } else {
                self.uPCd = ko.observable(0);
                self.selectedMethod = ko.observable(0);
            }
            
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
            nts.uk.ui.block.invisible();
                
            var item = _.find(self.unitPriceItems(), function(o) { return o.uPCd == self.uPCd(); });
            var data = {
                verticalCalCd: self.genVertId(),
                verticalCalItemId: self.verticalId(),
                unitPrice: self.uPCd(),
                unitName: item.uPName,
                attendanceAtr: self.selectedMethod(),
            };

            nts.uk.ui.windows.setShared("KML002_G_DATA", data);
            nts.uk.ui.block.clear();
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