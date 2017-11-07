module ksu001.d.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        itemList: KnockoutObservableArray<any> = ko.observableArray([
            new BoxModel(1, nts.uk.resource.getText("KSU001_79")),
            new BoxModel(2, nts.uk.resource.getText("KSU001_80"))
        ]);
        selectedId: KnockoutObservable<number> = ko.observable(1);
        checked: KnockoutObservable<boolean> = ko.observable(true);
        text = nts.uk.resource.getText("KSU001_82");

        //KCP005
        listComponentOption: any;
        selectedCode: KnockoutObservable<string> = ko.observable('');
        multiSelectedCode: KnockoutObservableArray<string> = ko.observableArray([]);
        isShowAlreadySet: KnockoutObservable<boolean> = ko.observable(false);
        alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);
        isDialog: KnockoutObservable<boolean> = ko.observable(false);
        isShowNoSelectRow: KnockoutObservable<boolean> = ko.observable(false);
        isMultiSelect: KnockoutObservable<boolean> = ko.observable(false);
        isShowWorkPlaceName: KnockoutObservable<boolean> = ko.observable(false);
        isShowSelectAllButton: KnockoutObservable<boolean> = ko.observable(false);
        employeeList: KnockoutObservableArray<UnitModel> = ko.observableArray([]);

        //ExCalendar
        startDate: KnockoutObservable<Date> = ko.observable(getShared("dataForScreenD").startDate);
        endDate: KnockoutObservable<Date> = ko.observable(getShared("dataForScreenD").endDate);
        selectedIds: KnockoutObservableArray<any> = ko.observableArray([]);

        constructor() {
            let self = this;

            _.each(getShared("dataForScreenD").empItems, (x) => {
                self.employeeList.push({ code: x.empCd, name: x.empName });
            });

            self.listComponentOption = {
                isShowAlreadySet: self.isShowAlreadySet(),
                isMultiSelect: self.isMultiSelect(),
                listType: ListType.EMPLOYEE,
                employeeInputList: self.employeeList,
                selectType: SelectType.NO_SELECT,
                selectedCode: self.selectedCode,
                isDialog: self.isDialog(),
                isShowNoSelectRow: self.isShowNoSelectRow(),
                alreadySettingList: self.alreadySettingList,
                isShowWorkPlaceName: self.isShowWorkPlaceName(),
                isShowSelectAllButton: self.isShowSelectAllButton(),
                maxRows: 15
            };

            $('#component-items-list').ntsListComponent(self.listComponentOption);
        }
        /**
         * decision
         */
        decision(): void {
            let self = this;
            if (!self.selectedCode() || self.selectedCode().length === 0) {
                nts.uk.ui.dialog.alertError(nts.uk.resource.getMessage('Msg_499'));
                return;
            }
            if (self.selectedIds().length === 0) {
                nts.uk.ui.dialog.alertError(nts.uk.resource.getMessage('Msg_500'));
                return;
            }

            nts.uk.ui.windows.close();
        }

        /**
         * Close dialog
         */
        closeDialog(): void {
            nts.uk.ui.windows.close();
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
        name: string;
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

    class BoxModel {
        id: number;
        name: string;
        constructor(id, name) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }
}