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
        workList: KnockoutObservable<any> = ko.observable([]);
        model: KnockoutObservable<ModelItem> = ko.observable(null);
        isNewMode: KnockoutObservable<boolean> = ko.observable(true);
        selectionCodeList: KnockoutObservableArray<string> = ko.observableArray([]);
        currentDate: string = moment(new Date()).format('YYYY/MM/DD');
        displayGoback: KnockoutObservable<boolean>;

        created(params: any) {
            const vm = this;
            vm.displayGoback = ko.observable(params && params.fromKMT011);
            //init mode
            vm.model(new ModelItem());
            vm.addNewRegistrationWork();

            vm.$blockui('show').then(() => {
                return vm.$ajax(PATH.init);
            }).done((frames: Array<any>) => {
                vm.workList(frames.filter(f => f.useAtr == 1).map(f => ({code: f.frameNo, name: f.frameName})));
            }).fail(error => {
                vm.$dialog.error(error).then(() => {
                    if (error.messageId == "Msg_2109") {
                        vm.$jump("/view/kmt/011/a/index.xhtml");
                    } else if (error.messageId == "Msg_2122" || error.messageId == "Msg_2114") {
                        nts.uk.request.jumpToTopPage();
                    }
                });
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
                $('#A6_2').focus();
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
                        $('#A6_2').focus();
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
                            if (vm.currentCode() == null) vm.currentCode.valueHasMutated();
                            else vm.currentCode(null);
                        } else {
                            if (vm.currentCode() == data[0].code) vm.currentCode.valueHasMutated();
                            else vm.currentCode(data[0].code);
                        }
                    } else {
                        vm.currentCode.valueHasMutated();
                    }
                }
                vm.$blockui('hide');
            }).fail((error) => {
                vm.$dialog.error(error);
            }).always(() => {
                $('#A6_2').focus();
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
                $('#A6_2').focus();
                vm.$blockui('hide');
            });
        }

        openDialogKDL012() {
            const vm = this;
            //kdl012 input
            let params = {
                targetType: vm.selectedWorkCode(),//??????
                isMultiple: true, //??????????????? single or multiple
                showExpireDate: true, //???????????????	show/hide expire date
                referenceDate: moment().format('YYYY/MM/DD'), //??????????????????
                workFrameNoSelection: vm.selectedWorkCode() + 1, //?????????NO??????
                selectionCodeList: vm.model().listOfRefinedItems().map(i => i.code)// ??????????????????????????????
            };

            vm.$window.modal('/view/kdl/012/index.xhtml', params).done((data) => {
                if (data && data.setShareKDL012) {
                    vm.$blockui('show');
                    vm.$ajax(PATH.getTaskList, {frameNo: vm.selectedWorkCode() + 1}).done((tasks: Array<any>) => {
                        let newListOfRefinedItems: Array<RefinedItem> = tasks
                            .filter((t: any) => data.setShareKDL012.indexOf(t.code) >= 0)
                            .map((x: any) => new RefinedItem(
                                x.code,
                                x.displayInfo.taskName,
                                x.expirationStartDate,
                                x.expirationEndDate
                            ));
                        //update model
                        newListOfRefinedItems = _.orderBy(newListOfRefinedItems, ['code', 'asc']);
                        vm.model().listOfRefinedItems.removeAll();
                        vm.model().listOfRefinedItems(newListOfRefinedItems);
                    }).fail((error) => {
                        vm.$dialog.error(error);
                    }).always(() => {
                        vm.$blockui('hide');
                    });
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
                itemListSetting: vm.registrationWorkList().filter(i => !!i.configured).map(i => i.code),
                baseDate: moment('YYYY/MM/DD').toDate(),
                workFrameNoSelection: vm.selectedWorkCode()
            };

            nts.uk.ui.windows.setShared("CDL023Input", params);
            // open dialog
            nts.uk.ui.windows.sub.modal('com', 'view/cdl/023/a/index.xhtml').onClosed(() => {
                let lstSelection: Array<string> = nts.uk.ui.windows.getShared("CDL023Output");
                let prams = nts.uk.ui.windows.getShared("CDL023Output");
                if (!nts.uk.util.isNullOrUndefined(prams)) {
                    vm.getCloneWorkTimeSetting(lstSelection, vm.currentCode());
                }
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
                vm.$dialog.info({messageId: 'Msg_926'}).then(() => {
                    vm.loadTaskList(vm.selectedWorkCode(), false);
                });
            }).fail((error) => {
                vm.$dialog.error(error);
            }).always(() => {
                $('#A6_2').focus();
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
            this.expireDate = startDate + ' ??? ' + endDate;
            this.remark = remark;
        }
    }

    export enum TargetType {
        // ??????
        EMPLOYMENT = 1,
        // ??????
        CLASSIFICATION = 2,
        // ??????
        JOB_TITLE = 3,
        // ??????
        WORKPLACE = 4,
        // ??????
        DEPARTMENT = 5,
        // ????????????
        WORKPLACE_PERSONAL = 6,
        // ????????????
        DEPARTMENT_PERSONAL = 7,
        // ?????????
        ROLE = 8,
        // ????????????
        WORK_TYPE = 9,
        //
        WORK = 10
    }

    interface IObjectDuplication {
        code: string;
        name: string;
        targetType: string | number;
        itemListSetting: Array<string>;
        baseDate?: Date; // needed when target type: ?????? or ?????? or ???????????? or ????????????
        roleType?: number; // needed when target type: ?????????,
        workFrameNoSelection?: number //ver6
    }
}