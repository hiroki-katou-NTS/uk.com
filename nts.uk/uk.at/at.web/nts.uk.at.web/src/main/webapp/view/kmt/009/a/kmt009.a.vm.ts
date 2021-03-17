/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmt09.a {

    const PATH = {
        init: 'at/shared/scherec/taskmanagement/task/kmt009/init',
        saveRegistrationWork: 'at/shared/scherec/taskmanagement/task/kmt009/update',
        deleteRegistrationWork: 'at/shared/scherec/taskmanagement/task/kmt009/delete',
        copyRegistrationWork: 'at/shared/scherec/taskmanagement/task/kmt009/copy',
        getTaskList: 'at/shared/scherec/taskmanagement/task/kmt009/getlist',
        getChildTaskList: 'at/shared/scherec/taskmanagement/task/kmt009/getlistchild',
    };

    const registered = '<i class="icon icon icon-78" style="background: url(\'/nts.uk.com.web/view/kcp/share/icon/icon78.png\');"></i>';

    @bean()
    class ViewModel extends ko.ViewModel {

        selectedWorkCode: KnockoutObservable<number> = ko.observable(null);
        currentCode: KnockoutObservable<string> = ko.observable(null);
        registrationWorkList: KnockoutObservableArray<WorkItem> = ko.observableArray([]);
        currentCodeList: KnockoutObservableArray<any> = ko.observableArray(null);
        externalCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
        externalCode: Array<any> = [];
        workList: KnockoutObservable<any> = ko.observable([]);
        model: KnockoutObservable<ModelItem> = ko.observable(null);
        isNewMode: KnockoutObservable<boolean> = ko.observable(true);
        selectionCodeList: KnockoutObservableArray<string> = ko.observableArray([]);
        listRoleType: Array<any> = [];
        currentDate: string = moment(new Date()).format('YYYY/MM/DD');
        displayGoback: KnockoutObservable<boolean>;

        created(params: any) {
            const vm = this;
            vm.displayGoback = ko.observable(params && params.fromKMT011);
            vm.externalCodeList([
                {code: 'KMT001_36', value: ko.observable(null)},
                {code: 'KMT001_37', value: ko.observable(null)},
                {code: 'KMT001_38', value: ko.observable(null)},
                {code: 'KMT001_39', value: ko.observable(null)},
                {code: 'KMT001_40', value: ko.observable(null)},
            ]);

            vm.listRoleType = __viewContext.enums.RoleType;
            //init mode
            vm.model(new ModelItem());
            vm.addNewRegistrationWork();

            vm.$blockui('show').then(() => {
                return vm.$ajax(PATH.init);
            }).done((frames: Array<any>) => {
                vm.workList(frames.filter(f => f.useAtr == 1).map(f => ({code: f.frameNo, name: f.frameName})));
            }).fail(error => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui("hide");
            });
        }

        mounted() {
            const vm = this;
            vm.selectedWorkCode.subscribe((newValue) => {
                nts.uk.ui.errors.clearAll();
                vm.loadTaskList(newValue, true);
            });

            vm.currentCode.subscribe((newValue) => {
                nts.uk.ui.errors.clearAll();
                let currentObj: any = _.find(vm.registrationWorkList(), x => x.code == newValue);
                if (currentObj) {
                    vm.model().update(currentObj.code, currentObj.name, currentObj.startDate, currentObj.endDate);
                    vm.isNewMode(!currentObj.configured);
                    vm.loadChildTaskList(newValue);
                } else {
                    vm.addNewRegistrationWork();
                }
            });
        }

        addNewRegistrationWork() {
            const vm = this;

            vm.externalCode = [
                ko.observable(null), ko.observable(null), ko.observable(null), ko.observable(null), ko.observable(null)
            ];
            vm.model().update(null, null, vm.currentDate, '9999/12/31', []);

            vm.isNewMode(true);
            $('#KMT009_13').focus();
        }

        saveRegistrationWork() {
            const vm = this;
            vm.$blockui('show');
            const data = {
                taskFrameNo: vm.selectedWorkCode(),
                parentWorkCode: vm.currentCode(),
                childWorkList: vm.model().listOfRefinedItems().map(i => i.code)
            };
            vm.$ajax(PATH.saveRegistrationWork, data).done(() => {
                vm.$dialog.info({messageId: 'Msg_15'}).then(() => {
                    vm.loadTaskList(vm.selectedWorkCode(), false);
                });
            }).fail((error) => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui('hide');
            });
        }

        deleteRegistrationWork() {
            const vm = this;
            vm.$dialog.confirm({messageId: 'Msg_18'}).then((result) => {
                if (result === 'yes') {
                    vm.$blockui('show');
                    let params = {
                        taskFrameNo: vm.selectedWorkCode(),
                        parentWorkCode: vm.currentCode(),
                    };
                    vm.$ajax(PATH.deleteRegistrationWork, params).done(() => {
                        vm.$dialog.info({messageId: 'Msg_16'}).then(() => {
                            vm.loadTaskList(vm.selectedWorkCode(), false);
                        });
                    }).fail((error) => {
                        vm.$dialog.error(error);
                    }).always(() => {
                        vm.$blockui('hide');
                    });
                }
            });
        }

        loadTaskList(frameNo: number, selectFirst: boolean) {
            const vm = this;
            vm.$blockui('show');
            vm.$ajax(PATH.getTaskList, {frameNo: frameNo}).done((data) => {
                if (data) {
                    vm.registrationWorkList(data.map((t: any) => new WorkItem(
                        t.code,
                        t.displayInfo.taskName,
                        t.expirationStartDate,
                        t.expirationEndDate,
                        _.isEmpty(t.childTaskList) ? null : registered
                    )));
                    if (selectFirst) {
                        if (_.isEmpty(data)) {
                            vm.currentCode(null);
                        } else {
                            vm.currentCode(data[0].code);
                        }
                    } else {
                        vm.currentCode.valueHasMutated();
                    }
                }
                vm.$blockui('hide');
            }).fail((error) => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui('hide');
            });
        }

        loadChildTaskList(code: string) {
            const vm = this;
            vm.$blockui('show');
            vm.$ajax(PATH.getChildTaskList, {frameNo: vm.selectedWorkCode(), code: code}).done((data) => {
                vm.model().listOfRefinedItems(data.map((t: any) => new RefinedItem(
                    t.code,
                    t.displayInfo.taskName,
                    t.expirationStartDate,
                    t.expirationEndDate,
                    t.displayInfo.taskNote
                )));
            }).fail((error) => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui('hide');
            });
        }

        openDialogKDL012() {
            const vm = this;
            //kdl012 input
            let params = {
                targetType: vm.selectedWorkCode(),//作業
                isMultiple: true, //選択モード single or multiple
                showExpireDate: true, //表示モード	show/hide expire date
                referenceDate: moment().format('YYYY/MM/DD'), //システム日付
                workFrameNoSelection: vm.selectedWorkCode(), //作業枠NO選択
                selectionCodeList: vm.model().listOfRefinedItems().map(i => i.code)// 初期選択コードリスト
            };

            vm.$window.modal('/view/kdl/012/index.xhtml', params).done((data) => {
                if (data && data.setShareKDL012) {

                    let newListOfRefinedItems: Array<RefinedItem> = [];
                    _.forEach(data.setShareKDL012, (x) => {
                        newListOfRefinedItems.push(
                            new RefinedItem(x.code, x.name, x.startDate, x.endDate, x.remark)
                        )
                    });

                    //update model
                    newListOfRefinedItems = _.orderBy(newListOfRefinedItems, ['code', 'asc']);
                    vm.model().listOfRefinedItems.removeAll();
                    vm.model().listOfRefinedItems(newListOfRefinedItems);
                }
            });
        }

        deleteRowItem() {
            const vm = this;
            let newListOfRefinedItems = _.filter(vm.model().listOfRefinedItems(), (x) => {
                return !_.includes(vm.currentCodeList(), x.code)
            });
            vm.model().listOfRefinedItems.removeAll();
            vm.model().listOfRefinedItems(newListOfRefinedItems);

        }

        openDialogCDL023() {
            const vm = this;

            let params: IObjectDuplication = {
                code: vm.model().code(),
                name: vm.model().name(),
                targetType: TargetType.WORK,
                itemListSetting: vm.registrationWorkList().map(i => i.code),
                baseDate: moment('YYYY/MM/DD').toDate(),
                workFrameNoSelection: vm.selectedWorkCode()
            };

            nts.uk.ui.windows.setShared("CDL023Input", params);
            // open dialog
            nts.uk.ui.windows.sub.modal('com', 'view/cdl/023/a/index.xhtml').onClosed(() => {
                let lstSelection: Array<string> = nts.uk.ui.windows.getShared("CDL023Output");
                vm.getCloneWorkTimeSetting(lstSelection, vm.currentCode());
            });
        }

        getCloneWorkTimeSetting(dataTarget: Array<string>, dataSource: string) {
            const vm = this;
            vm.$blockui("show").then(() => {
                const data = {
                    taskFrameNo: vm.selectedWorkCode(),
                    copySource: dataSource,
                    copyDestinationList: dataTarget
                };
                return vm.$ajax(PATH.copyRegistrationWork, data);
            }).done(() => {
                vm.loadTaskList(vm.selectedWorkCode(), false);
            }).fail((error) => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui('hide');
            });
        }

        goback() {
            const vm = this;
            vm.$jump("/view/kmt/011/a/index.xhtml", {screen: "KMT009"});
        }
    }

    export class ModelItem {
        code: KnockoutObservable<string> = ko.observable(null);
        name: KnockoutObservable<string> = ko.observable(null);
        expStartDate: KnockoutObservable<string> = ko.observable(null);
        expEndDate: KnockoutObservable<string> = ko.observable(null);
        listOfRefinedItems: KnockoutObservableArray<RefinedItem> = ko.observableArray([]);

        constructor(code?: string, name?: string, expStartDate?: string, expEndDate?: string, listOfRefinedItems?: Array<RefinedItem>) {
            this.code(code);
            this.name(name);
            this.expStartDate(expStartDate);
            this.expEndDate(expEndDate);
            this.listOfRefinedItems(listOfRefinedItems);
        }

        update(code?: string, name?: string, expStartDate?: string, expEndDate?: string, listOfRefinedItems: Array<RefinedItem> = []) {
            this.code(code);
            this.name(name);
            this.expStartDate(expStartDate);
            this.expEndDate(expEndDate);
            this.listOfRefinedItems(listOfRefinedItems);
        }
    }

    export class WorkItem {
        code: string;
        name: string;
        display: string;
        startDate: string;
        endDate: string;
        configured: string;

        constructor(code?: string, name?: string, startDate?: string, endDate?: string, configured?: string) {
            this.code = code;
            this.name = name;
            this.display = code + ((!_.isNull(name) && !_.isEmpty(name)) ? ' ' + name : '');
            this.startDate = startDate;
            this.endDate = endDate;
            this.configured = configured;
        }
    }

    export class RefinedItem {
        code: string;
        name: string;
        display: string;
        expireDate: string;
        remark: string;
        startDate: string;
        endDate: string;

        constructor(code?: string, name?: string, startDate?: string, endDate?: string, remark?: string) {
            this.code = code;
            this.name = name;
            this.startDate = startDate;
            this.endDate = endDate;
            this.display = code + ((!_.isNull(name) && !_.isEmpty(name)) ? ' ' + name : '');
            this.expireDate = startDate + ' ～ ' + endDate;
            this.remark = remark;
        }
    }

    export enum TargetType {
        // 雇用
        EMPLOYMENT = 1,
        // 分類
        CLASSIFICATION = 2,
        // 職位
        JOB_TITLE = 3,
        // 職場
        WORKPLACE = 4,
        // 部門
        DEPARTMENT = 5,
        // 職場個人
        WORKPLACE_PERSONAL = 6,
        // 部門個人
        DEPARTMENT_PERSONAL = 7,
        // ロール
        ROLE = 8,
        // 勤務種別
        WORK_TYPE = 9,
        //
        WORK = 10
    }

    interface IObjectDuplication {
        code: string;
        name: string;
        targetType: string | number;
        itemListSetting: Array<string>;
        baseDate?: Date; // needed when target type: 職場 or 部門 or 職場個人 or 部門個人
        roleType?: number; // needed when target type: ロール,
        workFrameNoSelection?: number //ver6
    }
}