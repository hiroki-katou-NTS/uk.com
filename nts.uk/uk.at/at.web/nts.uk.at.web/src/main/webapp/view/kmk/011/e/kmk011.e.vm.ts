module nts.uk.at.view.kmk011.e {

    import viewModelScreenD = nts.uk.at.view.kmk011.d.viewmodel;

    import WorkTypeDivergenceReferenceTimeHistoryDto = nts.uk.at.view.kmk011.e.model.WorkTypeDivergenceReferenceTimeHistoryDto;
    import WorkTypeDivergenceTimeSettingDto = nts.uk.at.view.kmk011.e.model.WorkTypeDivergenceTimeSettingDto;
    import DivergenceTimeDto = nts.uk.at.view.kmk011.e.model.DivergenceTimeDto;
    import WorkTypeDivergenceRefTimeSaveCommand = nts.uk.at.view.kmk011.e.model.WorkTypeDivergenceRefTimeSaveCommand;
    import WorkTypeDivergenceRefTimeSaveDto = nts.uk.at.view.kmk011.e.model.WorkTypeDivergenceRefTimeSaveDto;

    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import blockUI = nts.uk.ui.block;

    export module viewmodel {
        export class ScreenModel {
            useUnitSetting: KnockoutObservable<boolean>;
            enableSaveDivergenceRefSetting: KnockoutObservable<boolean>;
            wkTypeCode: KnockoutObservable<string>;
            wkTypeName: KnockoutObservable<string>;

            // work type list
            listWorkType: any;
            columns2: KnockoutObservableArray<any>;
            currentCode: KnockoutObservable<any>;

            //divergence time setting
            roundingRules: KnockoutObservableArray<any>;
            required: KnockoutObservable<boolean>;
            enable: KnockoutObservable<boolean>;
            mapObj: any;
            mapObj2: any;

            //history screen
            enable_button_creat: KnockoutObservable<boolean>;
            enable_button_edit: KnockoutObservable<boolean>;
            enable_button_delete: KnockoutObservable<boolean>;
            histList: any;
            histName: KnockoutObservable<string>;
            selectedHist: KnockoutObservable<string>;
            isEnableListHist: KnockoutObservable<boolean>;

            constructor() {
                let _self = this;
                _self.useUnitSetting = ko.observable(true);
                _self.enableSaveDivergenceRefSetting = ko.observable(true);
                _self.wkTypeCode = ko.observable("");
                _self.wkTypeName = ko.observable("");

                // work type list
                _self.listWorkType = ko.observableArray([]);
                _self.columns2 = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KDW009_6'), key: 'code', width: 100 },
                    { headerText: nts.uk.resource.getText('KMK011_49'), key: 'name', width: 150 }
                ]);
                _self.currentCode = ko.observable();

                //divergence time setting
                _self.roundingRules = ko.observableArray([
                    { code: 1, name: nts.uk.resource.getText('Enum_UseAtr_Use') },
                    { code: 0, name: nts.uk.resource.getText('Enum_UseAtr_NotUse') }
                ]);
                _self.enable = ko.observable(true);
                _self.required = ko.observable(true);
                _self.mapObj = new Map<number, WorkTypeDivergenceTimeSettingDto>();
                _self.mapObj2 = new Map<number, DivergenceTimeDto>();

                //history screen
                _self.enable_button_creat = ko.observable(true);
                _self.enable_button_edit = ko.observable(true);
                _self.enable_button_delete = ko.observable(true);
                _self.histList = ko.observableArray([]);
                _self.histName = ko.observable('');
                _self.selectedHist = ko.observable(null)
                _self.isEnableListHist = ko.observable(true);

                _self.currentCode.subscribe((value) => {
                    if (nts.uk.text.isNullOrEmpty(value)) {
                        _self.wkTypeCode("");
                        _self.wkTypeName("");
                        _self.enable_button_edit(false);
                        _self.enable_button_delete(false);
                        _self.isEnableListHist(false);
                        _self.enable_button_creat(false);
                        _self.enableSaveDivergenceRefSetting(false);
                        _self.histList([]);
                        _self.selectedHist(null);
                    } else {
                        _self.wkTypeCode(value);
                        _self.wkTypeName(_self.listWorkType().filter(e => e.code == value)[0].name);
                        _self.enable_button_edit(true);
                        _self.enable_button_delete(true);
                        _self.isEnableListHist(true);
                        _self.enable_button_creat(true);
                        _self.enableSaveDivergenceRefSetting(true);
                        _self.fillListHistory(value).done(function() {
                            let histId: string = null;
                            _self.histList().length > 0 ? histId = _self.histList()[0].historyId : histId = null;
                            _self.selectedHist(histId);
                        });
                    }
                });

                //subscribe history change
                _self.selectedHist.subscribe((value) => {
                    if (nts.uk.text.isNullOrEmpty(value)) {
                        _self.enable_button_edit(false);
                        _self.enable_button_delete(false);
                        _self.enableSaveDivergenceRefSetting(false);
                    } else {
                        _self.enable_button_edit(true);
                        _self.enable_button_delete(true);
                        _self.isEnableListHist(true);
                        _self.enableSaveDivergenceRefSetting(true);
                    }
                    _self.fillListItemSetting(_self.currentCode(), value).done(() => {

                    });
                });

                // create default data
                _self.fillListItemSettingDefault();
            }

            public start_page(): JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();

                _self.fillListWorkType().done(() => {
                    _self.currentCode(_self.listWorkType()[0].code);
                    _self.fillListHistory(_self.currentCode()).done(function() {
                        let histId: string = null;
                        _self.histList().length > 0 ? histId = _self.histList()[0].historyId : histId = null;
                        _self.fillListItemSetting(_self.currentCode(), histId).done(() => {
                            dfd.resolve();
                        });
                    });
                });

                return dfd.promise();
            }

            /**
            * save divergence reference setting
            */
            public saveDivergenceRefSetting() {
                let _self = this;
                var dfd = $.Deferred<any>();

                if (_self.hasError()) {
                    return;
                }

                let arrDto: any = [];

                _self.mapObj.forEach((value: WorkTypeDivergenceTimeSettingDto, key: number) => {

                    if (_self.isDisableAllRowWkType(key)) {
                        let commandDto = new WorkTypeDivergenceRefTimeSaveDto(
                            _self.currentCode(),
                            value.divergenceTimeNo(),
                            value.notUseAtr(),
                            _self.selectedHist(),
                            value.alarmTime(),
                            value.errorTime()
                        );
                        arrDto.push(commandDto);
                    }
                });

                var data = new WorkTypeDivergenceRefTimeSaveCommand(arrDto);

                service.save(data).done(() => {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                }).fail((res: any) => {
//                    _self.showMessageError(res);
                    _.forEach((res.errors),(error) => {
                        _.forEach(error.supplements, function(value, key) {
                            $('#workType_alarm_time_' + key).ntsError('set', {messageId:value});
                        });
                    })
                });
            }

            /**
             * check enable or disable divergence reference time setting
             */
            public isDisableAllRowWkType(diverNo: number): boolean {
                let _self = this;
                if (_self.mapObj2.get(diverNo).divergenceTimeUseSet == DivergenceTimeUseSet.NOT_USE || nts.uk.text.isNullOrEmpty( _self.selectedHist())) {
                    return false;
                }
                return true;
            }

            /**
             * check enable or disable alarm time && error time
             */
            public checkStatusEnableWkType(diverNo: number): boolean {
                let _self = this;
                if (_self.mapObj2.get(diverNo).divergenceTimeUseSet == DivergenceTimeUseSet.NOT_USE) {
                    return false;
                } else {
                    if (_self.mapObj.get(diverNo).notUseAtr() == DivergenceTimeUseSet.NOT_USE) {
                        return false;
                    }
                    return true;
                }
            }

            /**
             * showMessageError
             */
            public showMessageError(res: any) {
                let dfd = $.Deferred<any>();

                // check error business exception
                if (!res.businessException) {
                    return;
                }

                // show error message
                if (Array.isArray(res.errors)) {
                    nts.uk.ui.dialog.bundledErrors(res);
                } else {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds });
                }
            }

            /**
             * Check Errors all input.
             */
            private hasError(): boolean {
                let _self = this;
                _self.clearErrors();
                for (let i = 1; i <= 10; i++) {
                    if (_self.mapObj.get(i).notUseAtr() == DivergenceTimeUseSet.USE) {
                        $('#workType_alarm_time_' + i).ntsEditor("validate");
                        $('#workType_error_time_' + i).ntsEditor("validate");
                    }

                }
                if ($('.nts-input').ntsError('hasError')) {
                    return true;
                }
                return false;
            }

            /**
             * Clear Errors
             */
            private clearErrors(): void {
                let _self = this;
                // Clear errors
                for (let i = 1; i <= 10; i++) {
                    if (_self.mapObj.get(i).notUseAtr() == DivergenceTimeUseSet.USE) {
                        $('#workType_alarm_time_' + i).ntsEditor("clear");
                        $('#workType_error_time_' + i).ntsEditor("clear");
                    }
                }

                // Clear error inputs
                $('.nts-input').ntsError('clear');
            }

            /**
             * find list work type
             */
            private fillListWorkType(): JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();

                service.getAllWorkType().done((response: any) => {
                    if (response.length != 0) {
                        _self.listWorkType([]);
                        response.forEach((item: any) => {
                            _self.listWorkType.push(new ItemModel(item.businessTypeCode, item.businessTypeName, "", false));
                        });
                        
                        dfd.resolve();
                    } else {
                        _self.listWorkType([]);
                        _self.currentCode(null);
                        _self.wkTypeCode("");
                        _self.wkTypeName("");
                        _self.enable_button_edit(false);
                        _self.enable_button_delete(false);
                        _self.isEnableListHist(false);
                        _self.enable_button_creat(false);
                        _self.enableSaveDivergenceRefSetting(false);
                        _self.histList([]);
                        _self.selectedHist(null);
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_1051" }).then(() => {
                            nts.uk.request.jump("/view/kmk/011/d/index.xhtml");
                        });
                        dfd.resolve();
                    }
                });

                return dfd.promise();
            }

            /**
             * find list divergence reference time by his
             */
            private fillListItemSetting(wkTypeCode: string, histId: string): JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                let dto: WorkTypeDivergenceTimeSettingDto;
                service.getAllItemSetting(wkTypeCode, histId).done((response: any) => {
                    if (response != null  && response.length > 0) {
                        if (_self.mapObj.size == 0) {
                            response.forEach((item: any) => {
                                dto = new WorkTypeDivergenceTimeSettingDto();
                                dto.updateData(item);
                                _self.mapObj.set(item.divergenceTimeNo, dto);
                            });
                        } else {
                            response.forEach((item: any) => {
                                _self.mapObj.get(item.divergenceTimeNo).notUseAtr(item.notUseAtr);
                                _self.mapObj.get(item.divergenceTimeNo).alarmTime(item.divergenceReferenceTimeValue.alarmTime);
                                _self.mapObj.get(item.divergenceTimeNo).errorTime(item.divergenceReferenceTimeValue.errorTime);
                            });
                        }
                    } else {
                        _self.fillListItemSettingDefault();
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * fill list divergence time
             */
            private fillListDivergenceTime(): JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                let objTemp2: DivergenceTimeDto;
                service.getAllDivergenceTime().done((response: any) => {
                    if (response != null) {
                        response.forEach((item1: any) => {
                            objTemp2 = new DivergenceTimeDto(item1.divergenceTimeNo, item1.divergenceTimeName, item1.divergenceTimeUseSet);
                            _self.mapObj2.set(item1.divergenceTimeNo, objTemp2);
                        });
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }

            /**
             * fill list history 
             */
            private fillListHistory(workTypeCode: string): JQueryPromise<any> {
                let _self = this;
                var dfd = $.Deferred<any>();
                var historyData: any = [];
                var textDisplay = "";

                //fill list history
                service.getAllHistory(workTypeCode).done((response: any) => {
                    if (response != null) {
                        response.forEach(function(item: WorkTypeDivergenceReferenceTimeHistoryDto) {
                            textDisplay = item.startDate + " " + nts.uk.resource.getText("CMM011_26") + " " + item.endDate;
                            historyData.push(new HistModel(item.historyId, textDisplay));
                        });
                        _self.isEnableListHist(true);
                        _self.enable_button_edit(true);
                        _self.enable_button_delete(true);
                        _self.histList(historyData);
                        dfd.resolve();
                    } else {
                        _self.enable_button_edit(false);
                        _self.enable_button_delete(false);
                        _self.isEnableListHist(false);
                        _self.enableSaveDivergenceRefSetting(false);
                        _self.fillListItemSettingDefault();
                        _self.fillListItemSettingDefault();
                        _self.histList([]);
                        blockUI.clear();
                        dfd.resolve();
                    }
                });

                return dfd.promise();
            }

            /**
             * create list divergence reference time default
             */
            private fillListItemSettingDefault(): void {
                let _self = this;

                let objTemp1: WorkTypeDivergenceTimeSettingDto;

                if (_self.mapObj.size == 0) {
                    for (let i = 1; i <= 10; i++) {
                        let item = {
                            divergenceTimeNo: i,
                            notUseAtr: 0,
                            divergenceReferenceTimeValue: {
                                errorTime: '',
                                alarmTime: ''
                            }
                        };
                        let itemDto = new WorkTypeDivergenceTimeSettingDto();
                        itemDto.updateData(item);
                        _self.mapObj.set(item.divergenceTimeNo, itemDto);
                    }
                } else {
                    for (let i = 1; i <= 10; i++) {
                        let item = {
                            divergenceTimeNo: i,
                            notUseAtr: 0,
                            divergenceReferenceTimeValue: {
                                errorTime: '',
                                alarmTime: ''
                            }
                        };
                        _self.mapObj.get(item.divergenceTimeNo).notUseAtr(item.notUseAtr);
                        _self.mapObj.get(item.divergenceTimeNo).alarmTime(item.divergenceReferenceTimeValue.alarmTime);
                        _self.mapObj.get(item.divergenceTimeNo).errorTime(item.divergenceReferenceTimeValue.errorTime);
                    }
                }
            }

            // history mode
            public createMode(): void {
                let _self = this;
                if(_self.histList().length >0){                    
                    var histModel: any = _self.histList()[0];
                    var startDate: string = histModel.textDisplay.substr(0, 10);
                    nts.uk.ui.windows.setShared('startDateString', startDate);
                } else {
                        nts.uk.ui.windows.setShared('startDateString', "");
                }
                nts.uk.ui.windows.setShared('listHist', _self.histList());
                nts.uk.ui.windows.setShared('settingMode', HistorySettingMode.WORKTYPE);
                nts.uk.ui.windows.setShared('listHist', _self.histList());
                nts.uk.ui.windows.setShared('workTypeCode', _self.currentCode());
                //get old HistModel list
                var oldHistList: HistModel[] = _self.histList();
                nts.uk.ui.windows.sub.modal("/view/kmk/011/f/index.xhtml").onClosed(function() {
                    _self.fillListHistory(_self.currentCode()).done(() => {
                        //get new HistModel list
                        var newHistList: HistModel[] = _self.histList();
                        //get HistModel from 2 HistModel list
                        var hist: HistModel = _self.getDiffHist(oldHistList, newHistList);
                        //check hist undefined
                        if (typeof (hist) != "undefined") {
                            _self.selectedHist(hist.historyId);
                        }
                        $('#list-box-1').focus();
                    });
                });
            }

            // get HistModel from 2 HistModel list
            private getDiffHist(list1: any, list2: any): HistModel {
                var _self = this;
                var length: number = list2.length - 1;
                for (var i = 0; i <= length; i++) {
                    // Check every Id in list1
                    if (!_self.isSameId(list2[i].historyId, list1)) {
                        return list2[i];
                    }
                }
            }

            //Check Id in list
            private isSameId(histId: string, list: any): boolean {
                var length:number = list.length - 1;
                for (var i = 0; i <= length; i++) {
                    if (histId == list[i].historyId) return true;
                }

                return false;

            }

            public editMode(): void {
                let _self = this;
                nts.uk.ui.windows.setShared('settingMode', HistorySettingMode.WORKTYPE);
                nts.uk.ui.windows.setShared('history', _self.selectedHist());
                nts.uk.ui.windows.sub.modal("/view/kmk/011/g/index.xhtml").onClosed(function() {
                    _self.fillListHistory(_self.currentCode());
                    $('#list-box-2').focus();
                });
            }
            public deleteMode(): void {
                let _self = this;
                var command: any = {};
                command.historyId = _self.selectedHist();

                // define next history id
                var nextHistId: string = _self.getNextHistoryAfterDelete();

                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                    service.deleteHistory(command).done(() => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(() => {
                            _self.fillListHistory(_self.currentCode()).done(() => {
                                _self.selectedHist(nextHistId);
                                $('#list-box-2').focus();
                            });
                        });
                    });
                });
            }
            private getNextHistoryAfterDelete(): string {
                let _self = this;
                let nextHistId: string = null;
                var indexOfCurrentHist: number = 0;
                //find current index
                for (let index = 0; index < _self.histList().length; index++) {
                    if (_self.histList()[index].historyId == _self.selectedHist()) {
                        indexOfCurrentHist = index;
                    }
                }
                //find next histId
                if (indexOfCurrentHist == 0) {
                    if (_self.histList().length > 1) {
                        nextHistId = _self.histList()[indexOfCurrentHist + 1].historyId;
                        return nextHistId
                    }
                    return null;
                }

                if ((indexOfCurrentHist + 1) == _self.histList().length) {
                    nextHistId = _self.histList()[indexOfCurrentHist - 1].historyId;
                } else {
                    nextHistId = _self.histList()[indexOfCurrentHist + 1].historyId;
                }

                return nextHistId;
            }

            public openRegisterErrMsgDialog(): void {
                let _self = this;
                nts.uk.ui.windows.setShared('settingMode', HistorySettingMode.WORKTYPE);
                nts.uk.ui.windows.setShared('wkTypeCode', _self.wkTypeCode);
                nts.uk.ui.windows.setShared('wkTypeName', _self.wkTypeName);
                nts.uk.ui.windows.sub.modal("/view/kmk/011/i/index.xhtml").onClosed(function() {

                });
            }
        }

        export enum DivergenceTimeUseSet {
            NOT_USE = 0,
            USE = 1
        }

        export enum HistorySettingMode {
            COMPANY = 0,
            WORKTYPE = 1
        }

        // work type list
        export class ItemModel {
            code: string;
            name: string;
            description: string;
            other1: string;
            other2: string;
            deletable: boolean;
            switchValue: boolean;
            constructor(code: string, name: string, description: string, deletable: boolean, other1?: string, other2?: string) {
                this.code = code;
                this.name = name;
                this.description = description;
                this.other1 = other1;
                this.other2 = other2 || other1;
                this.deletable = false;
            }
        }

        export class HistModel {
            historyId: string;
            textDisplay: string;

            constructor(historyId: string, textDisplay: string) {
                this.historyId = historyId;
                this.textDisplay = textDisplay;
            }
        }

    }
}