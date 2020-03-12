module nts.uk.at.view.kdp010.e.viewmodel {
    import blockUI = nts.uk.ui.block;

    export class ScreenModel {

        // E4_1, E4_2, E4_6, E4_7
        optionImprint: KnockoutObservableArray<any> = ko.observableArray([
            { id: 0, name: nts.uk.resource.getText("KDP010_59") },
            { id: 1, name: nts.uk.resource.getText("KDP010_60") }
        ]);
        selectedImprint: KnockoutObservable<number> = ko.observable(0);
        messageValueFirst: KnockoutObservable<string> = ko.observable("");
        firstColors: KnockoutObservable<string> = ko.observable('#FFCC00');

        // E5_1, E5_2, E5_6, E5_7
        optionHoliday: KnockoutObservableArray<any> = ko.observableArray([
            { id: 0, name: nts.uk.resource.getText("KDP010_59") },
            { id: 1, name: nts.uk.resource.getText("KDP010_60") }
        ]);
        selectedHoliday: KnockoutObservable<number> = ko.observable(0);
        messageValueSecond: KnockoutObservable<string> = ko.observable("");
        secondColors: KnockoutObservable<string> = ko.observable('#FFCC00');

        // E6_1, E6_2, E6_5, E6_6
        optionOvertime: KnockoutObservableArray<any> = ko.observableArray([
            { id: 0, name: nts.uk.resource.getText("KDP010_59") },
            { id: 1, name: nts.uk.resource.getText("KDP010_60") }
        ]);
        selectedOvertime: KnockoutObservable<number> = ko.observable(0);
        messageValueThird: KnockoutObservable<string> = ko.observable("");
        thirdColors: KnockoutObservable<string> = ko.observable('#FFCC00');

        workTypeList: KnockoutObservableArray<any> = ko.observableArray([]);
        workTypeNames: KnockoutObservable<string> = ko.observable();
        currentItem: KnockoutObservable<WorktypeDisplayDto> = ko.observable(new WorktypeDisplayDto({}));

        // E31_2
        optionImp: KnockoutObservableArray<any> = ko.observableArray([
            { id: 0, name: nts.uk.resource.getText("KDP010_85") },
            { id: 1, name: nts.uk.resource.getText("KDP010_84") }
        ]);
        selectedImp: KnockoutObservable<number> = ko.observable(0);
        constructor() {
            let self = this;
        }

        /**
         * Start page.
         */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            return dfd.promise();
        }

        /**
         * Open Dialog KDL002
         */
        openKDL002Dialog() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            nts.uk.ui.block.invisible();
            var workTypeCodes = _.map(self.workTypeList(), function(item: IWorkTypeModal) { return item.workTypeCode });
            nts.uk.ui.windows.setShared('KDL002_Multiple', true);
            nts.uk.ui.windows.setShared('KDL002_AllItemObj', workTypeCodes);
            nts.uk.ui.windows.setShared('KDL002_SelectedItemId', self.currentItem().workTypeList());

            nts.uk.ui.windows.sub.modal('/view/kdl/002/a/index.xhtml').onClosed(function(): any {
                nts.uk.ui.block.clear();
                var data = nts.uk.ui.windows.getShared('KDL002_SelectedNewItem');
                var name = [];
                _.forEach(data, function(item: IWorkTypeModal) {
                    name.push(item.name);
                });
                self.workTypeNames(name.join(" + "));

                var workTypeCodes = _.map(data, function(item: any) { return item.code; });
                self.currentItem().workTypeList(workTypeCodes);
            });
            nts.uk.ui.block.clear();
        }


        /**
         * Registration function.
         */
        registration() {

        }



    }
    export class WorktypeDisplayDto {
        useAtr: KnockoutObservable<number>;
        workTypeList: KnockoutObservableArray<WorktypeDisplaySetDto>;
        constructor(param: IWorktypeDisplayDto) {
            this.useAtr = ko.observable(param.useAtr || 0);
            this.workTypeList = ko.observableArray(param.workTypeList || null);
        }
    }

    export interface IWorktypeDisplayDto {
        useAtr?: number;
        workTypeList?: Array<WorktypeDisplaySetDto>;
    }
    
     export class WorkTypeModal {
        workTypeCode: string;
        name: string;
        memo: string;
        constructor(param: IWorkTypeModal) {
            this.workTypeCode = param.workTypeCode;
            this.name = param.name;
            this.memo = param.memo;
        }
    }

    export interface IWorkTypeModal {
        workTypeCode: string;
        name: string;
        memo: string;
    }
    
      export class WorktypeDisplaySetDto {
        workTypeCode: string;
        constructor(param: IWorktypeDisplaySetDto) {
            this.workTypeCode = param.workTypeCode;
        }
    }

    export interface IWorktypeDisplaySetDto {
        workTypeCode?: string;
    }

}
