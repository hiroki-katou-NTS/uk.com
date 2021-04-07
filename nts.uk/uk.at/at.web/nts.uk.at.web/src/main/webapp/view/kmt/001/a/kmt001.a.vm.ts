/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmt001.a {

    const PATH = {
        init: 'at/shared/scherec/workmanagement/work/kmt001/init',
        saveRegistrationWork: 'at/shared/scherec/workmanagement/work/kmt001/register',
        updateRegistrationWork: 'at/shared/scherec/workmanagement/work/kmt001/update',
        deleteRegistrationWork: 'at/shared/scherec/workmanagement/work/kmt001/delete',
        getRegistrationWork: 'at/shared/scherec/workmanagement/work/kmt001/find',
        getTaskList: 'at/shared/scherec/taskmanagement/task/kmt009/getlist'
    };

    @bean()
    class ViewModel extends ko.ViewModel {

        selectedWorkCode: KnockoutObservable<number> = ko.observable(null);
        currentCode: KnockoutObservable<string> = ko.observable(null);
        registrationWorkList: KnockoutObservableArray<any> = ko.observableArray(null);
        externalCodeList: KnockoutObservableArray<any> = ko.observableArray([]);
        workList: KnockoutObservable<any> = ko.observable([]);

        model: KnockoutObservable<ModelItem> = ko.observable(null);
        isNewMode: KnockoutObservable<boolean> = ko.observable(true);

        displayGoback: KnockoutObservable<boolean>;
        displayFrames: KnockoutObservable<boolean> = ko.observable(false);

        created(params: any) {
            const vm = this;
            vm.displayGoback = ko.observable(params && params.fromKMT011);
            vm.externalCodeList([
                { code: 'KMT001_36' },
                { code: 'KMT001_37' },
                { code: 'KMT001_38' },
                { code: 'KMT001_39' },
                { code: 'KMT001_40' },
            ]);

            vm.model(new ModelItem(vm.selectedWorkCode));
            vm.addNewRegistrationWork();

            vm.$blockui('show').then(() => {
                return vm.$ajax(PATH.init);
            }).done((data: any) => {
                if (data) {
                    vm.workList(data.taskFrameSettings.filter((f: any) => f.useAtr == 1).map((f: any) => ({code: f.frameNo, name: f.frameName})));
                    vm.displayFrames(data.operationMethod == "USED_IN_ACHIEVENTS");
                }
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
                vm.loadTaskList(newValue);
            });

            vm.currentCode.subscribe((newValue) => {
                nts.uk.ui.errors.clearAll();
                let currentObj: any = _.find(vm.registrationWorkList(), x => x.code == newValue);
                if (currentObj) {
                    vm.model().update(
                        currentObj.code,
                        currentObj.name,
                        currentObj.taskAbName,
                        currentObj.color,
                        currentObj.startDate,
                        currentObj.endDate,
                        currentObj.remark,
                        currentObj.externalCodes,
                        currentObj.childTaskList
                    );
                    vm.isNewMode(false);
                    $('#A4_2').focus();
                } else {
                    vm.model().update(null, null, null, "#000000", null, null, null, []);
                    vm.isNewMode(true);
                    $('#A3_2').focus();
                }
            });
        }

        loadTaskList(frameNo: number, selectCode?: string) {
            const vm = this;
            vm.$blockui('show');
            vm.$ajax(PATH.getTaskList, {frameNo: frameNo}).done((data) => {
                if (data) {
                    vm.registrationWorkList(data.map((t: any) => ({
                        code: t.code,
                        name: t.displayInfo.taskName,
                        taskAbName: t.displayInfo.taskAbName,
                        color: t.displayInfo.color,
                        remark: t.displayInfo.taskNote,
                        startDate: t.expirationStartDate,
                        endDate: t.expirationEndDate,
                        externalCodes: [
                            t.cooperationInfo.externalCode1,
                            t.cooperationInfo.externalCode2,
                            t.cooperationInfo.externalCode3,
                            t.cooperationInfo.externalCode4,
                            t.cooperationInfo.externalCode5
                        ],
                        childTaskList: t.childTaskList
                    })));
                    if (_.isNil(selectCode)) {
                        if (_.isEmpty(data)) {
                            if (vm.currentCode() == null)
                                vm.currentCode.valueHasMutated();
                            else
                                vm.currentCode(null);
                        } else {
                            if (vm.currentCode() == data[0].code)
                                vm.currentCode.valueHasMutated();
                            else
                                vm.currentCode(data[0].code);
                        }
                    } else {
                        vm.currentCode(selectCode);
                    }
                }
                vm.$blockui('hide');
            }).fail((error) => {
                vm.$dialog.error(error);
            }).always(() => {
                vm.$blockui('hide');
            });
        }

        addNewRegistrationWork() {
            const vm = this;
            vm.currentCode(null);
            nts.uk.ui.errors.clearAll();
        }

        saveRegistrationWork() {
            const vm = this;
            vm.model().color.valueHasMutated();
            vm.$validate(['.nts-input']).then((valid: boolean) => {
                if (valid && !nts.uk.ui.errors.hasError()) {
                    const command = {
                        taskFrameNo: vm.selectedWorkCode(),
                        code: vm.model().code(),
                        startDate: new Date(vm.model().period().startDate).toISOString(),
                        endDate: new Date(vm.model().period().endDate).toISOString(),
                        cooperationInfo: {
                            externalCode1: vm.model().externalCode()[0](),
                            externalCode2: vm.model().externalCode()[1](),
                            externalCode3: vm.model().externalCode()[2](),
                            externalCode4: vm.model().externalCode()[3](),
                            externalCode5: vm.model().externalCode()[4]()
                        },
                        displayInfo: {
                            taskName: vm.model().name(),
                            taskAbName: vm.model().abbreviatedName(),
                            color: vm.model().color(),
                            taskNote: vm.model().remarks()
                        },
                        childTaskList: vm.model().childTaskList()
                    };
                    vm.$blockui("show").then(() => {
                        return vm.$ajax(vm.isNewMode() ? PATH.saveRegistrationWork : PATH.updateRegistrationWork, command);
                    }).done((res: any) => {
                        vm.$dialog.info({messageId: 'Msg_15'}).then(() => {
                            vm.loadTaskList(vm.selectedWorkCode(), command.code);
                        });
                    }).fail((error: any) => {
                        vm.$dialog.error(error);
                    }).always(() => {
                        vm.$blockui("hide");
                    });
                }
            });
        }

        deleteRegistrationWork() {
            const vm = this;
            vm.$dialog.confirm({messageId: 'Msg_18'}).then((result) => {
                if (result === 'yes') {
                    vm.$blockui('show');
                    let params = {
                        taskFrameNo: vm.selectedWorkCode(),
                        code: vm.currentCode(),
                    };
                    vm.$ajax(PATH.deleteRegistrationWork, params).done(() => {
                        vm.$dialog.info({messageId: 'Msg_16'}).then(() => {
                            let selectCode = null;
                            if (vm.registrationWorkList().length > 1) {
                                const index = _.findIndex(vm.registrationWorkList(), i => i.code == vm.currentCode());
                                if (index == vm.registrationWorkList().length - 1) {
                                    selectCode = vm.registrationWorkList()[index - 1].code;
                                } else {
                                    selectCode = vm.registrationWorkList()[index + 1].code;
                                }
                            }
                            vm.loadTaskList(vm.selectedWorkCode(), selectCode);
                        });
                    }).fail((error) => {
                        vm.$dialog.error(error);
                    }).always(() => {
                        vm.$blockui('hide');
                    });
                }
            });
        }

        goback() {
            const vm = this;
            vm.$jump("/view/kmt/011/a/index.xhtml", {screen: "KMT001"});
        }

    }

    export class ModelItem {
        code: KnockoutObservable<string> = ko.observable(null);
        name: KnockoutObservable<string> = ko.observable(null);
        abbreviatedName: KnockoutObservable<string> = ko.observable(null);
        color: KnockoutObservable<string> = ko.observable(null);
        period: KnockoutObservable<any> = ko.observable({startDate: null, endDate: null});
        remarks: KnockoutObservable<string> = ko.observable(null);
        externalCode: KnockoutObservableArray<any> = ko.observableArray([
            ko.observable(null),
            ko.observable(null),
            ko.observable(null),
            ko.observable(null),
            ko.observable(null)
        ]);
        childTaskList: KnockoutObservableArray<string> = ko.observableArray([]);

        constructor(frameNo: KnockoutObservable<number>, code?: string, name?: string, abbreviatedName?: string, color?: string,
                    expStartDate?: string, expEndDate?: string, remarks?: string, externalCode?: Array<string>) {
            if (code) this.code(code);
            if (name) this.name(name);
            if (abbreviatedName) this.abbreviatedName(abbreviatedName);
            if (color) this.color(color);
            this.period({startDate: expStartDate, endDate: expEndDate});
            if (remarks) this.remarks(remarks);
            if (externalCode) this.externalCode().forEach((v, i) => {
                v(externalCode[i]);
            });
            this.color.subscribe(value => {
                if (_.isNil(value) && frameNo() == 1) {
                    $('#colorpicker').ntsError('set', {messageId:'MsgB_1', messageParams:[nts.uk.resource.getText("KMT001_26")]});
                } else {
                    $('#colorpicker').ntsError('clear');
                }
            });
        }

        update(code?: string, name?: string, abbreviatedName?: string, color?: string,
               expStartDate?: string, expEndDate?: string, remarks?: string, externalCode?: Array<string>, childTaskList?: Array<string>) {
            this.code(code);
            this.name(name);
            this.abbreviatedName(abbreviatedName);
            this.color(color);
            this.period({startDate: expStartDate, endDate: expEndDate});
            this.remarks(remarks);
            this.externalCode().forEach((v, i) => {
                v(externalCode[i]);
            });
            this.childTaskList(childTaskList || []);
        }
    }
}