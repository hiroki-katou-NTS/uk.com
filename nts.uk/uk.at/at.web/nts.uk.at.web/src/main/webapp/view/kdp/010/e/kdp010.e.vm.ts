module nts.uk.at.view.kdp010.e.viewmodel {
    import blockUI = nts.uk.ui.block;

    export class ScreenModel {

        // E4_1, E4_2, E4_6, E4_7
        optionImprint: KnockoutObservableArray<any> = ko.observableArray([
            { id: 0, name: nts.uk.resource.getText("KDP010_59") },
            { id: 1, name: nts.uk.resource.getText("KDP010_60") }
        ]);
        selectedImprint: KnockoutObservable<number> = ko.observable(0);
        messageValueFirst: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText("KDP010_130"));
        firstColors: KnockoutObservable<string> = ko.observable('#000000');

        // E5_1, E5_2, E5_6, E5_7
        optionHoliday: KnockoutObservableArray<any> = ko.observableArray([
            { id: 0, name: nts.uk.resource.getText("KDP010_59") },
            { id: 1, name: nts.uk.resource.getText("KDP010_60") }
        ]);
        selectedHoliday: KnockoutObservable<number> = ko.observable(0);
        messageValueSecond: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText("KDP010_131"));
        secondColors: KnockoutObservable<string> = ko.observable('#000000');

        // E6_1, E6_2, E6_5, E6_6
        optionOvertime: KnockoutObservableArray<any> = ko.observableArray([
            { id: 0, name: nts.uk.resource.getText("KDP010_59") },
            { id: 1, name: nts.uk.resource.getText("KDP010_60") }
        ]);
        selectedOvertime: KnockoutObservable<number> = ko.observable(0);
        messageValueThird: KnockoutObservable<string> = ko.observable(nts.uk.resource.getText("KDP010_132"));
        thirdColors: KnockoutObservable<string> = ko.observable('#000000');

        workTypeList: KnockoutObservableArray<any> = ko.observableArray([]);
        workTypeNames: KnockoutObservable<string> = ko.observable();
        currentItem: KnockoutObservable<DailyItemDto> = ko.observable(new DailyItemDto({}));

        // E31_2
        optionImp: KnockoutObservableArray<any> = ko.observableArray([
            { id: 0, name: nts.uk.resource.getText("KDP010_85") },
            { id: 1, name: nts.uk.resource.getText("KDP010_84") }
        ]);
        selectedImp: KnockoutObservable<number> = ko.observable(0);
        
        dataKdl: KnockoutObservableArray<any> = ko.observableArray([]);
        
        constructor() {
            let self = this;

            self.messageValueFirst.subscribe(function(codeChanged: string) {
                self.messageValueFirst($.trim(self.messageValueFirst()));
            });
            self.messageValueSecond.subscribe(function(codeChanged: string) {
                self.messageValueSecond($.trim(self.messageValueSecond()));
            });
            self.messageValueThird.subscribe(function(codeChanged: string) {
                self.messageValueThird($.trim(self.messageValueThird()));
            });
        }

        /**
         * Start page.
         */
        start(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            nts.uk.ui.block.clear();
            self.getStampApp();
            $.when(self.getWorkTypeList()).done(function() {
                $(document).ready(function() {
                    $('#imprint-leakage-radio-e').focus();
                });
                dfd.resolve();
            });
            return dfd.promise();
        }

        registration() {
            let self = this, dfd = $.Deferred();

            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            $.when(self.registrationApp(), self.deleteStampFunc()).done(function() {
                if (nts.uk.ui.errors.hasError()) {
                    nts.uk.ui.block.clear();
                    return;
                }
                self.registrationFunc();
                dfd.resolve();
            });
            return dfd.promise();

        }

        /**
         * Registration function.
         */
        registrationApp() {
            let self = this;
            $('#message-text-2').ntsEditor('validate');
            $('.text-color-1').find('#message-1').find('#message-text-1').ntsEditor('validate');
            $('#message-text-3').ntsEditor('validate');
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            nts.uk.ui.block.invisible();
            // Data from Screen 
            let StampRecordDisCommand = {
                lstStampRecord: [{
                    useArt: self.selectedImprint(),
                    checkErrorType: 0,
                    promptingMssage: {
                        messageContent: self.messageValueFirst(),
                        messageColor: self.firstColors()
                    }
                }, {
                        useArt: self.selectedHoliday(),
                        checkErrorType: 1,
                        promptingMssage: {
                            messageContent: self.messageValueSecond(),
                            messageColor: self.secondColors()
                        }
                    }, {
                        useArt: self.selectedOvertime(),
                        checkErrorType: 2,
                        promptingMssage: {
                            messageContent: self.messageValueThird(),
                            messageColor: self.thirdColors()
                        }
                    }]
            };
            service.saveStampApp(StampRecordDisCommand).done(function() {

            }).fail(function(res) {
                nts.uk.ui.dialog.alertError(res.message);
            }).always(() => {
                nts.uk.ui.block.clear();
            });
        }

        registrationFunc() {
            let self = this;
            let lstDisplayItem: StampAttenDisplayCommand[] = [];
            let lstDisplay = new Array<>();
             _.forEach(self.currentItem().dailyList(), function(item) {
                    lstDisplay = new StampAttenDisplayCommand({
                        displayItemId : item
                    });
                 lstDisplayItem.push(lstDisplay);
                });
            
            // Data from Screen 
            let StampRecordDisCommand = {
                usrAtr: self.selectedImp(),
                lstDisplayItemId: lstDisplayItem
            };
            service.saveStampFunc(StampRecordDisCommand).done(function() {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
            }).fail(function(res) {
                nts.uk.ui.dialog.alertError(res.message);
            }).always(() => {
                nts.uk.ui.block.clear();
            });
        }

        getWorkTypeList() {
            let self = this, dfd = $.Deferred();
            service.getOptItemByAtr().done(function(res) {
                self.workTypeList.removeAll();
                _.forEach(res, function(item) {
                    self.workTypeList.push({
                        attendanceItemId: item.attendanceItemId,
                        attendanceName: item.attendanceItemName
                    });
                });
                self.getStampFunc();
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
                dfd.reject(error);
            });
            return dfd.promise();
        }

        getStampApp(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getStampApp().done(function(totalStamp) {
                if (totalStamp.length > 0) {
                    totalStamp = _.sortBy(totalStamp, item => item.checkErrorType)
                    self.selectedImprint(totalStamp[0].useArt);
                    self.messageValueFirst(totalStamp[0].messageContent);
                    self.firstColors(totalStamp[0].messageColor);
                    self.selectedHoliday(totalStamp[1].useArt);
                    self.messageValueSecond(totalStamp[1].messageContent);
                    self.secondColors(totalStamp[1].messageColor);
                    self.selectedOvertime(totalStamp[2].useArt);
                    self.messageValueThird(totalStamp[2].messageContent);
                    self.thirdColors(totalStamp[2].messageColor);
                }
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
                dfd.reject(error);
            });
            return dfd.promise();
        }

        getStampFunc(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getStampFunc().done(function(totalStamp) {
                if (totalStamp) {
                    self.selectedImp(totalStamp.usrAtr);
                    var workTypeCodes = _.map(totalStamp.lstDisplayItemId, function(item: any) { return item.displayItemId; });
                    self.generateNameCorrespondingToAttendanceItem(workTypeCodes);
                    var names = self.getNames(self.workTypeList(), totalStamp.workTypeList);
                    if (names) {
                        self.workTypeNames(names);
                    } else {
                        self.workTypeNames("");
                    }

                }
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
                dfd.reject(error);
            });
            return dfd.promise();
        }

        deleteStampFunc(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            let data = {
                displayItemId: 1
            }
            service.deleteStampFunc(data).done(function(totalStamp) {
                dfd.resolve();
            }).fail(function(error) {
                alert(error.message);
                dfd.reject(error);
            });
            return dfd.promise();
        }

        getNames(data: Array<IDailyItemModal>, workTypeCodesSelected: Array<string>) {
            var name = [];
            var self = this;
            if (workTypeCodesSelected && workTypeCodesSelected.length > 0) {
                _.forEach(data, function(item: IDailyItemModal) {
                    _.forEach(workTypeCodesSelected, function(items: any) {
                        if (_.includes(items.attendanceItemId, item.attendanceItemId)) {
                            name.push(item.attendanceName);
                        }
                    });
                });
            }
            return name.join(" + ");
        }

        /**
         * Open Dialog KDL021
         */
        openKDL021Dialog(datas : any) {
            let self = this;
            nts.uk.ui.errors.clearAll();
            nts.uk.ui.block.invisible();
            var workTypeCodes = _.map(self.workTypeList(), function(item: IDailyItemModal) { return item.attendanceItemId });
            nts.uk.ui.windows.setShared('Multiple', true);
            nts.uk.ui.windows.setShared('DailyMode', 0);
            nts.uk.ui.windows.setShared('AllAttendanceObj', workTypeCodes);
            
            if(datas ==1) {
                let data021 = self.dataKdl.map(i=>Number(i))
                nts.uk.ui.windows.setShared('SelectedAttendanceId', data021, true);
            }
            else{
                nts.uk.ui.windows.setShared('SelectedAttendanceId', self.currentItem().dailyList(), true);
            }    

            nts.uk.ui.windows.sub.modal('/view/kdl/021/a/index.xhtml').onClosed(function(): any {
                nts.uk.ui.block.clear();
                self.dataKdl = nts.uk.ui.windows.getShared('selectedChildAttendace');
                self.generateNameCorrespondingToAttendanceItem(self.dataKdl);
            });
            nts.uk.ui.block.clear();
        }

        /**
       * アルゴリズム「勤怠項目に対応する名称を生成する」を実行する - Execute algorithm "Generate name corresponding to attendance item"
       * @param List<itemAttendanceId>
       */
        private generateNameCorrespondingToAttendanceItem(listAttendanceItemCode: Array<any>): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            if (self.dataKdl && self.dataKdl.length > 5) {
                    nts.uk.ui.dialog.error({ messageId: "Msg_1631" }).then(() => {
                        self.openKDL021Dialog(1);
                    });
                    return;
                }
            if (listAttendanceItemCode && listAttendanceItemCode.length > 0) {
                service.getAttendNameByIds(listAttendanceItemCode).done((dailyAttendanceItemNames) => {
                    if (dailyAttendanceItemNames && dailyAttendanceItemNames.length > 0) {
                        var attendanceName: string = '';
                        var name = [];
                        dailyAttendanceItemNames = dailyAttendanceItemNames.sort((x,y) => {return x.attendanceItemId - y.attendanceItemId});
                        for (var i = 0; i < dailyAttendanceItemNames.length; i++) {
                            attendanceName = dailyAttendanceItemNames[i].attendanceItemName;
                            name.push(dailyAttendanceItemNames[i].attendanceItemName);
                        }
                        self.workTypeNames(name.join(" 、 "));
                        var workTypeCodes = _.map(dailyAttendanceItemNames, function(item: any) { return item.attendanceItemId; });
                        self.currentItem().dailyList(workTypeCodes);
                        dfd.resolve(attendanceName);
                    } else {
                        dfd.resolve('');
                    }
                }).always(() => {
                    dfd.resolve('');
                });
            } else {
                dfd.resolve('');
            }
            return dfd.promise();
        }

    }
    export class DailyItemDto {
        useAtr: KnockoutObservable<number>;
        dailyList: KnockoutObservableArray<DailyItemSetDto>;
        constructor(param: IDailyItemDto) {
            this.useAtr = ko.observable(param.useAtr || 0);
            this.dailyList = ko.observableArray(param.dailyList || null);
        }
    }

    export interface IDailyItemDto {
        useAtr?: number;
        dailyList?: Array<IDailyItemSetDto>;
    }

    export class DailyItemModal {
        attendanceItemId: string;
        attendanceName: string;
        constructor(param: IDailyItemModal) {
            this.attendanceItemId = param.attendanceItemId;
            this.attendanceName = param.attendanceName;
        }
    }

    export interface IDailyItemModal {
        attendanceItemId: string;
        attendanceName: string;
    }

    export class DailyItemSetDto {
        attendanceItemId: string;
        constructor(param: IDailyItemSetDto) {
            this.attendanceItemId = param.attendanceItemId;
        }
    }

    export interface IDailyItemSetDto {
        attendanceItemId?: string;
    }
    
export class StampAttenDisplayCommand {
        displayItemId: number;
        constructor(param: IStampAttenDisplayCommand) {
            this.displayItemId = param.displayItemId;
        }
    }

    export interface IStampAttenDisplayCommand {
        displayItemId?: number;
    }
}
