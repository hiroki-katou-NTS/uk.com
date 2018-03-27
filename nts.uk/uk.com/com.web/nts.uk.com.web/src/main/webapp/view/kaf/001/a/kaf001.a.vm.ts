module nts.uk.com.view.kaf001.a.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    export class ScreenModel {
        listComponentOption: any;
        selectedCode: KnockoutObservable<string>;
        multiSelectedCode: KnockoutObservableArray<string>;
        isShowAlreadySet: KnockoutObservable<boolean>;
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
        isDialog: KnockoutObservable<boolean>;
        isShowNoSelectRow: KnockoutObservable<boolean>;
        isMultiSelect: KnockoutObservable<boolean>;
        isShowWorkPlaceName: KnockoutObservable<boolean>;
        isShowSelectAllButton: KnockoutObservable<boolean>;
        employeeList: KnockoutObservableArray<UnitModel>;

        constructor() {
            let self = this;
            self.baseDate = ko.observable(new Date());
            self.selectedCode = ko.observable('1');
            self.multiSelectedCode = ko.observableArray(['0', '1', '4']);
            self.isShowAlreadySet = ko.observable(false);
            self.alreadySettingList = ko.observableArray([
                { code: '1', isAlreadySetting: true },
                { code: '2', isAlreadySetting: true }
            ]);
            self.isDialog = ko.observable(false);
            self.isShowNoSelectRow = ko.observable(false);
            self.isMultiSelect = ko.observable(true);
            self.isShowWorkPlaceName = ko.observable(true);
            self.isShowSelectAllButton = ko.observable(true);
            this.employeeList = ko.observableArray<UnitModel>([
                { code: '1', name: 'Angela Baby', workplaceName: 'HN' },
                { code: '2', name: 'Xuan Toc Do', workplaceName: 'HN' },
                { code: '3', name: 'Park Shin Hye', workplaceName: 'HCM' },
                { code: '4', name: 'Vladimir Nabokov', workplaceName: 'HN' }
            ]);
            self.listComponentOption = {
                isShowAlreadySet: self.isShowAlreadySet(),
                isMultiSelect: self.isMultiSelect(),
                listType: ListType.EMPLOYEE,
                employeeInputList: self.employeeList,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: self.selectedCode,
                isDialog: self.isDialog(),
                isShowNoSelectRow: self.isShowNoSelectRow(),
                alreadySettingList: self.alreadySettingList,
                isShowWorkPlaceName: self.isShowWorkPlaceName(),
                isShowSelectAllButton: self.isShowSelectAllButton()
            };
        }
        start(): JQueryPromise<any> {
            let self = this;
            var dfd = $.Deferred();
            $('#sample-component').ntsListComponent(self.listComponentOption);
            dfd.resolve();
            return dfd.promise();
        }
        selectApplitcation(button_id) {
            let screenToOpen = "";
            switch (button_id) {
                case '#A2_2': {
                    
                    break;
                }
                case '#A2_3': {
                    break;
                }
                case '#A2_4': {
                    break;
                }
                case '#A2_5': {
                    break;
                }
                case '#A2_6': {
                    break;
                }
                case '#A2_7': {
                    break;
                }
                case '#A2_8': {
                    break;
                }
                case '#A2_9': {
                    break;
                }
                case '#A2_10': {
                    break;
                }
                case '#A2_11': {
                    break;
                }
                case '#A2_12': {
                    break;
                }
            }
        }

        selectOvertimeApplication() {

        }
        selectVacationApplication() {

        }
        selectWorkChangeApplication() {

        }
        selectBusinessTripApplication() {

        }
        selectLeaveSoonApplication() {

        }
        selectHolidayTimeApplication() {

        }
        selectHourlyHolidayApplication() {

        }
        selectCancelEarlyWithdrawApplication() {

        }
        selectHolidayShipmentApplication() {

        }
        selectEmbrossingApplication() {

        }




    }
    export class ListType {
        static EMPLOYMENT = 1;
        static Classification = 2;
        static JOB_TITLE = 3;
        static EMPLOYEE = 4;
    }

    export interface UnitModel {
        code: string;
        name?: string;
        workplaceName?: string;
        isAlreadySetting?: boolean;
    }

    export class SelectType {
        static SELECT_BY_SELECTED_CODE = 1;
        static SELECT_ALL = 2;
        static SELECT_FIRST_ITEM = 3;
        static NO_SELECT = 4;
    }

    export interface UnitAlreadySettingModel {
        code: string;
        isAlreadySetting: boolean;
    }
}