module nts.uk.at.view.ksu005.a {
    import setShare = nts.uk.ui.windows.setShared;
    const Paths = {
        GET_SCHEDULE_TABLE_OUTPUT_SETTING_BY_CID:"ctx/at/schedule/scheduletable/getall"
    };
    @bean()
    class Ksu005aViewModel extends ko.ViewModel {
        currentScreen: any = null;
        itemList: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        selectedCode: KnockoutObservable<string> = ko.observable('');
        comments: KnockoutObservable<string>;
        constructor() {
            super();
            const self = this;           
            self.comments = ko.observable("並び順社員リスト 社員情報　対象期間...");
            self.loadScheduleOutputSetting();
        }

        loadScheduleOutputSetting(): void {
            const self = this;
            let dataList: Array<ItemModel> = [];
            self.$blockui("invisible");			
            self.$ajax(Paths.GET_SCHEDULE_TABLE_OUTPUT_SETTING_BY_CID).done((data: Array<IScheduleTableOutputSetting>) => {
                if(data && data.length > 0){
                    _.each(data, item =>{
                        dataList.push(new ItemModel(item.code, item.name));
                    });
                } else {
                    self.openDialog();
                }
                self.itemList(dataList);
            }).always(() => {
                self.$blockui("hide");
            });
        }

        closeDialog(): void {
            const vm = this;
            vm.$window.close();
        }

        openDialog(): void {
            let self = this;	
            let request:any = {};
            request.code = self.selectedCode();
            // request.name = 'AAAAAAAAAA';
            let code = self.selectedCode();
            setShare('dataShareKSU005a', code);
            self.currentScreen = nts.uk.ui.windows.sub.modal('/view/ksu/005/b/index.xhtml');
        }
    }

    interface IScheduleTableOutputSetting {
        code: string;
        name: string;
        additionalColumn: number;
        shiftBackgroundColor: number;
        dailyDataDisplay: number;
        personalInfo: Array<number>;
        additionalInfo: Array<number>;
        attendanceItem: Array<number>;
        workplaceCounterCategories: Array<number>;
        personalCounterCategories: Array<number>;
 
    }
    class ScheduleTableOutputSetting {
        code: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');
        additionalColumn: KnockoutObservable<number> = ko.observable();
        shiftBackgroundColor: KnockoutObservable<number> = ko.observable();
        dailyDataDisplay: KnockoutObservable<number> = ko.observable();
        personalInfo: KnockoutObservableArray<number> = ko.observableArray([]);
        additionalInfo: KnockoutObservableArray<number> = ko.observableArray([]);
        attendanceItem: KnockoutObservableArray<number> = ko.observableArray([]);
        workplaceCounterCategories: KnockoutObservableArray<number> = ko.observableArray([]);
        personalCounterCtegories: KnockoutObservableArray<number> = ko.observableArray([]);
        constructor(params?: IScheduleTableOutputSetting) {
            const self = this;
            if(params){
                self.code(params.code);
                self.name(params.name);
                self.additionalColumn(params.additionalColumn);
                self.shiftBackgroundColor(params.shiftBackgroundColor);
                self.dailyDataDisplay(params.dailyDataDisplay);
                self.personalInfo(params.personalInfo);
                self.additionalInfo(params.additionalInfo);
                self.attendanceItem(params.attendanceItem);
                self.workplaceCounterCategories(params.workplaceCounterCategories);
                self.personalCounterCtegories(params.personalInfo);
            }
        }
 
    }



    class ItemModel {
        code: string;
        name: string;
    
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}