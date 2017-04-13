// TreeGrid Node
module qpp014.e {
    export class ScreenModel {
        //E_LST_003
        items_E_LST_003: KnockoutObservableArray<ItemModel_E_LST_003>;
        columns_E_LST_003: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCode_E_LST_003: KnockoutObservable<any>;
        timer: nts.uk.ui.sharedvm.KibanTimer;
        processingDate: KnockoutObservable<any>;
        transferDate: KnockoutObservable<any>;
        constructor() {
            var self = this;
            $('#successful').css('display', 'none');
            //$('#stop').css('display', 'none');
            $('#error').css('display', 'none');
            //            nts.uk.ui.windows.getSelf().setHeight(595);
            self.items_E_LST_003 = ko.observableArray([]);
            self.timer = new nts.uk.ui.sharedvm.KibanTimer('timer');
            for (let i = 1; i < 100; i++) {
                self.items_E_LST_003.push(new ItemModel_E_LST_003('00' + i, '基本給', "description " + i));
            }
            self.currentCode_E_LST_003 = ko.observable();
            self.transferDate = ko.observable(moment(nts.uk.ui.windows.getShared("transferDate")).format("YYYY/MM/DD"));
            self.processingDate = ko.observable(nts.uk.ui.windows.getShared("processingDate"));
        }

        /**
         * close dialog
         */
        closeDialog(): void {
            nts.uk.ui.windows.setShared("closeDialog", true, true);
            nts.uk.ui.windows.close();
        }

        /**
         * go to screen G or H
         */
        goToScreenGOrH(): void {
            nts.uk.ui.windows.close();
        }

        /**
         * stop processing
         */
        stopProcessing(): void {
            var self = this;
            self.timer.end();
            nts.uk.ui.windows.setShared("closeDialog", false, true);
            $('#successful').css('display', 'none');
            $('#stop').css('display', 'none');
            $('#error').css('display', '');
            nts.uk.ui.windows.getSelf().setHeight(595);
        }
    }

    export class ItemModel_E_LST_003 {
        code: string;
        name: string;
        description: string;

        constructor(code: string, name: string, description: string) {
            this.code = code;
            this.name = name;
            this.description = description;
        }
    }

};
