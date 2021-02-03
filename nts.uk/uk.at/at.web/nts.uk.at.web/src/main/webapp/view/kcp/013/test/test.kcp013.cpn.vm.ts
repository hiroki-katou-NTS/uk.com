module nts.uk.at.view.kcp013.dialog.test.viewmodel {
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {
        width: KnockoutObservable<number>;
        tabIndex: KnockoutObservable<number | string>;
        filter: KnockoutObservable<boolean>;
        disabled: KnockoutObservable<boolean>;
        workplaceId: KnockoutObservable<string>;
        selected: KnockoutObservable<string> | KnockoutObservableArray<string>;
        dataSources: KnockoutObservableArray<WorkTimeModel>;
        showMode: KnockoutObservable<SHOW_MODE>;

        constructor() {
            var self = this;
            let data = getShared( "data" );
            
            self.width = ko.observable(data.width);
            self.tabIndex = ko.observable(data.tabIndex);
            self.filter = ko.observable(data.filter);
            self.disabled = ko.observable(data.disabled);
            self.workplaceId = ko.observable(data.workplaceId);
            self.selected = ko.observable(data.selected);
            self.dataSources = ko.observableArray(data.dataSources);
            self.showMode = ko.observable(data.showMode);
        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
        selectedWorkingHours( data, data2 ): void {

        }
        public closeDialog(): void {
            let self = this;
            nts.uk.ui.windows.close();
        }
    }
    enum SHOW_MODE {
        // not has any option
        NOT_SET = 0,
        // show none option
        NONE = 1,
        // show deffered option
        DEFFERED = 2,
        // show none & deffered option
        BOTTLE = 3
    }
    
    interface WorkTimeModel {
        id: string;
        code: string;
        name: string;
        tzStart1: number;
        tzEnd1: number;
        tzStart2: number;
        tzEnd2: number;
        workStyleClassfication: string;
        remark: string;
        useDistintion: number;
        tzStartToEnd1: string;
        tzStartToEnd2: string;
    }
}