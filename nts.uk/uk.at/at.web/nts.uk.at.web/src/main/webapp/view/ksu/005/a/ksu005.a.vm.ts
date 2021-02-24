module nts.uk.at.view.ksu005.a {
    import setShare = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import character = nts.uk.characteristics;

    const Paths = {
        GET_SCHEDULE_TABLE_OUTPUT_SETTING_BY_CID:"ctx/at/schedule/scheduletable/getall"
    };
    @bean()
    class Ksu005aViewModel extends ko.ViewModel {
        currentScreen: any = null;
        itemList: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        selectedCode: KnockoutObservable<string> = ko.observable('');
        comments: KnockoutObservable<string>;
        characteristics: Characteristics = {};
        constructor() {
            super();
            const self = this;   
            // self.selectedCode.subscribe((value) => {

            // });        
            self.comments = ko.observable("並び順社員リスト 社員情報　対象期間...");
            self.loadScheduleOutputSetting();
        }

        loadScheduleOutputSetting(): void {
            const self = this;
            let dataList: Array<ItemModel> = [];
            self.$blockui("invisible");			
            self.$ajax(Paths.GET_SCHEDULE_TABLE_OUTPUT_SETTING_BY_CID).done((data: Array<IScheduleTableOutputSetting>) => {
                if(data && data.length > 0){
                    if(data[0].isAttendance == null){
                        _.each(data, item =>{
                            dataList.push(new ItemModel(item.code, item.name));
                        });
                        character.restore('characterKsu005a').done((obj: Characteristics) => {
                            self.selectedCode(obj.code);
                        })
                    } else if(data[0].isAttendance){                       
                            self.openDialog();                    
                    } else {
                        self.$dialog.info({ messageId: 'Msg_1970'}).then(() => {
                            self.closeDialog();
                        });
                    } 
                    
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
            let code = self.selectedCode();
            setShare('dataShareKSU005a', code);
            self.currentScreen = nts.uk.ui.windows.sub.modal('/view/ksu/005/b/index.xhtml').onClosed(() => {
                let dataList: Array<ItemModel> = [];
                let currentCode = getShared('dataShareCloseKSU005b');
                self.$blockui("invisible");
                self.$ajax(Paths.GET_SCHEDULE_TABLE_OUTPUT_SETTING_BY_CID).done((data: Array<IScheduleTableOutputSetting>) => {
                    if (data && data.length > 0) {
                        if (data[0].isAttendance == null) {
                            _.each(data, item => {
                                dataList.push(new ItemModel(item.code, item.name));                                
                            });
                            self.characteristics.code = currentCode;
                            character.save('characterKsu005a', self.characteristics);
                            self.loadScheduleOutputSetting();
                        } else if (data[0].isAttendance) {
                            self.$dialog.info({ messageId: 'Msg_1766' }).then(() => {
                                self.openDialog();
                            });
                        } else {
                            self.$dialog.info({ messageId: 'Msg_1970' }).then(() => {
                                self.closeDialog();
                            });
                        }
                    }
                    self.itemList(dataList);
                }).always(() => {
                    self.$blockui("hide");
                });
            });
        }
    }

    interface Characteristics {
        code: string;
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
        isAttendance: boolean;
 
    }
    class ScheduleTableOutputSetting {
        code: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');
        additionalColumn: KnockoutObservable<number> = ko.observable();
        isAttendance: KnockoutObservable<boolean> = ko.observable(true);
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
                self.isAttendance(params.isAttendance);
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