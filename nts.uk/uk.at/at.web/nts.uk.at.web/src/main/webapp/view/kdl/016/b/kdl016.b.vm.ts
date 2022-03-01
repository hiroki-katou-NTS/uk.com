/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kdl016.b {
    import Moment = moment.Moment;
    import ListType = nts.uk.at.ksm008.b.ListType;

    const API = {
        get: "screen/at/kdl016/b/init",
        register: "screen/at/kdl016/register"
    };

    @bean()
    export class ViewModel extends ko.ViewModel {
        // kcp005
        listComponentOption: any;
        date: KnockoutObservable<string> = ko.observable(new Date().toISOString());
        startDate: KnockoutObservable<string> = ko.observable(null);
        endDate: KnockoutObservable<string> = ko.observable(null);
        employeeList: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedEmployees: KnockoutObservableArray<string> = ko.observableArray([]);
        displayGoback: KnockoutObservable<boolean>;
        updateMode: KnockoutObservable<boolean> = ko.observable(false);

        // combobox
        orgList: KnockoutObservableArray<OrgItemModel>;
        selectedOrgCode: KnockoutObservable<string>;

        // RadioBtn
        supportTypes: KnockoutObservableArray<any>;
        selectedSupportType: KnockoutObservable<number>;

        enableEditTimespan: KnockoutObservable<boolean> = ko.observable(false);
        timespanMin: KnockoutObservable<number>;
        timespanMax: KnockoutObservable<number>;

        startDateStr: KnockoutObservable<string> = ko.observable("");
        endDateStr: KnockoutObservable<string> = ko.observable("");
        dateValue: KnockoutObservable<any> = ko.observable({
            startDate: new Date(),
            endDate: new Date(),
        });

        constructor(params: IScreenParameter) {
            super();
            const vm = this;
            vm.employeeList = ko.observableArray<UnitModel>([
                {id: '1', code: '0001', name: 'Angela Babykasjgdkajsghdkahskdhaksdhasd', workplaceName: 'HN'},
                {id: '2', code: '0002', name: 'Xuan Toc Doaslkdhasklhdlashdhlashdl', workplaceName: 'HN'},
                {id: '3', code: '0003', name: 'Park Shin Hye', workplaceName: 'HCM'},
                {id: '4', code: '0004', name: 'Vladimir Nabokov', workplaceName: 'HN'}
            ]);

            vm.orgList = ko.observableArray([
                new OrgItemModel('1', '基本給'),
                new OrgItemModel('2', '役職手当'),
                new OrgItemModel('3', '基本給ながい文字列ながい')
            ]);
            vm.selectedOrgCode = ko.observable('1');

            vm.supportTypes = ko.observableArray([
                new BoxModel(0, '終日応援'),
                new BoxModel(1, '時間帯応援')
            ]);
            vm.selectedSupportType = ko.observable(0);

            vm.timespanMin = ko.observable(100);
            vm.timespanMax = ko.observable(300);
            vm.selectedSupportType.subscribe(value => {
                if (value == 1)
                    vm.enableEditTimespan(true);
                else
                    vm.enableEditTimespan(false);
            });

            vm.startDateStr = ko.observable('2022/01/01');
            vm.endDateStr = ko.observable('2022/02/28');
            vm.startDateStr.subscribe(function (value) {
                vm.dateValue().startDate = value;
                vm.dateValue.valueHasMutated();
            });
            vm.endDateStr.subscribe(function (value) {
                vm.dateValue().endDate = value;
                vm.dateValue.valueHasMutated();
            });
        }

        created(params: any) {
            const vm = this;
            vm.listComponentOption = {
                isShowAlreadySet: false,
                isMultiSelect: true,
                listType: 4, // Employee
                employeeInputList: vm.employeeList,
                selectedCode: vm.selectedEmployees,
                maxRows: 8,
                isDialog: true,
                hasPadding: false
            };
        }

        mounted() {
            const vm = this;
            $('#employee-list').ntsListComponent(vm.listComponentOption);
            $('#employee-list').focus();
        }

        loadData(): void {
            const vm = this;


        }

        register() {

        }

        // findById(id: number): ISupportInformation[] {
        //     let vm = this;
        //     return _.find(vm.igGridDataSource, (value: any) => {
        //         return value.id == id;
        //     });
        // }

        closeDialog(): void {
            const vm = this;
            vm.$window.close();
        }
    }

    export function redirectEditModal(id: number) {
        let dataSource = $("#grid").igGrid("option", "dataSource");
        let rowSelect = _.find(dataSource, (value: any) => {
            return value.id == id;
        });

        var checkboxes = $('#igGridSupportInfo').igGridRowSelectors("option", "enableCheckBoxes");

        nts.uk.ui.windows.sub.modal("/view/kdl/016/d/index.xhtml").onClosed(() => {

        });
    }

    interface ISupportInformation {
        id: number;
        employeeId: string;
        periodStart: string;
        periodEnd: string;
        employeeCode: string;
        employeeName: string;
        supportOrgName: string;
        supportOrgId: string;
        supportOrgUnit: number;
        supportType: number;
        timeSpan: ITimeSpan;
        supportTypeName: string;
        periodDisplay: string;
        employeeDisplay: string;
        timeSpanDisplay: string;
    }

    interface ITimeSpan {
        start: number;
        end: number;
    }

    interface IScreenParameter {
        targetOrg: ITargetOrganization;
        startDate: string;
        endDate: string;
        employeeIds: string[]
    }

    interface ITargetOrganization {
        id: string;
        code: string;
        unit: number
    }

    class OrgItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    class BoxModel {
        id: number;
        name: string;

        constructor(id: number, name: string) {
            var self = this;
            self.id = id;
            self.name = name;
        }
    }

    enum DISPLAY_MODE {
        GO_TO_SUPPORT = 1,
        COME_TO_SUPPORT = 2,
    }

    enum TARGET_ORG {
        WORKPLACE = 0,
        WORKPLACE_GROUP = 1,
    }

    enum SUPPORT_TYPE {
        /** 終日応援 **/
        ALLDAY = 0,
        /** 時間帯応援 **/
        TIMEZONE = 1
    }
}