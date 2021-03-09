module nts.uk.at.view.kmt014.a.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    const API = {
        getTasks: "at/shared/scherec/taskmanagement/taskassign/employee/findtasks",
        getSettings: "at/shared/scherec/taskmanagement/taskassign/employee/find",
        register: "at/shared/scherec/taskmanagement/taskassign/employee/register",
        remove: "at/shared/scherec/taskmanagement/taskassign/employee/delete",
        searchEmployees: "basic/organization/employee/search"
    };

    @bean()
    class ViewModel extends ko.ViewModel {
        listComponentOption: any;
        date: KnockoutObservable<string> = ko.observable(new Date().toISOString());
        employeeList: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedEmployees: KnockoutObservableArray<string> = ko.observableArray([]);
        taskList: KnockoutObservableArray<any> = ko.observableArray([]);
        taskCode: KnockoutObservable<string> = ko.observable(null);
        taskName: KnockoutObservable<string> = ko.observable(null);
        startDate: KnockoutObservable<string> = ko.observable(null);
        endDate: KnockoutObservable<string> = ko.observable(null);
        taskFrameNo: KnockoutObservable<number> = ko.observable(1);
        displayGoback: KnockoutObservable<boolean>;

        created(params: any) {
            const self = this;
            self.displayGoback = ko.observable(params && params.fromKMT011);
            self.listComponentOption = {
                isShowAlreadySet: false,
                isMultiSelect: true,
                listType: 4, // Employee
                employeeInputList: self.employeeList,
                selectedCode: self.selectedEmployees,
                isDialog: true,
                hasPadding: false
            };
            self.getTaskList(self.date());
        }

        mounted() {
            const self = this;
            $('#employee-list').ntsListComponent(self.listComponentOption);
            self.taskCode.subscribe(value => {
                if (value) {
                    const selectedTask = _.find(self.taskList(), t => t.taskCode == value);
                    self.taskName(selectedTask ? selectedTask.taskName : null);
                    self.startDate(selectedTask ? selectedTask.startDate : null);
                    self.endDate(selectedTask ? selectedTask.endDate : null);
                    $('#A6_2').focus();
                } else {
                    self.taskName(null);
                    self.startDate(null);
                    self.endDate(null);
                }
            });
        }

        getTaskList(baseDate: string) {
            const self = this;
            self.$blockui("show");
            self.$ajax(API.getTasks, {
                taskFrameNo: self.taskFrameNo(),
                baseDate: moment(baseDate).format('YYYY/MM/DD')
            }).done((tasks: Array<any>) => {
                self.taskList(tasks);
                if (_.isEmpty(tasks)) {
                    self.taskCode(null);
                    self.taskName(null);
                    self.startDate(null);
                    self.endDate(null);
                } else {
                    if (!_.find(tasks, t => t.taskCode == self.taskCode())) {
                        self.taskCode(tasks[0].taskCode);
                    }
                }
            }).fail(error => {
                self.$dialog.alert(error).then(() => {
                    if (error.businessException && (error.messageId == "Msg_2122" || error.messageId == "Msg_2114")) {
                        nts.uk.request.jumpToTopPage();
                    }
                });
            }).always(() => {
                self.$blockui("hide");
            });
        }

        changeDate() {
            const self = this;
            self.getTaskList(self.date());
        }

        register() {
            const self = this;
            if (_.isEmpty(self.employeeList())) {
                $('#A6_2').ntsError('set', {messageId:'MsgB_2',messageParams:['KMT014_21']});
                return;
            }
            const command = {
                taskFrameNo: self.taskFrameNo(),
                taskCode: self.taskCode(),
                employeeIds: self.employeeList().map(e => e.id)
            };
            self.$blockui("show").then(() => {
                return self.$ajax(API.register, command);
            }).done((res: any) => {
                self.$dialog.info({messageId: 'Msg_15'}).then(() => {
                    $('#A6_2').focus();
                });
            }).fail((error: any) => {
                self.$dialog.error(error);
            }).always(() => {
                self.$blockui("hide")
            });
        }

        deleteSetting() {
            const self = this;
            self.$blockui("show");
            self.$blockui("hide");
        }

        openCDL009() {
            const self = this;
            setShared('CDL009Params', {
                // isMultiSelect For Employee List Kcp005
                isMultiple: true,
                // For Workplace List Kcp004
                selectedIds: [],
                // For Workplace List Kcp004
                baseDate: new Date(self.date()).toISOString(),
                // Workplace or Department
                target: 1
            });

            self.$window.modal('com', '/view/cdl/009/a/index.xhtml').then(function(): any {
                const employeeIds: Array<string> = getShared('CDL009Output');
                if (!_.isEmpty(employeeIds)) {
                    self.$blockui("show").then(() => {
                        return self.$ajax("com", API.searchEmployees, {baseDate: new Date().toISOString(), employeeIds: employeeIds});
                    }).done((res: Array<any>) => {
                        self.employeeList(res.map(e => ({
                            id: e.employeeId,
                            code: e.employeeCode,
                            name: e.employeeName
                        })));
                    }).fail((error: any) => {
                        self.$dialog.error(error);
                    }).always(() => {
                        self.$blockui("hide");
                        $('#A6_2').ntsError('clear');
                    });

                }
                setShared('CDL009Cancel', undefined);
                setShared('CDL009Output', undefined);
            });
        }

        removeEmployee() {
            const self = this;
            self.employeeList(self.employeeList().filter(e => self.selectedEmployees().indexOf(e.code) < 0));
        }

        iconFormater(value: string) {
            return value == 'true' ? '<i class="icon icon-78"/>' : '';
        }

        goback() {
            const vm = this;
            vm.$jump("/view/kmt/011/a/index.xhtml", {screen: "KMT014"});
        }
    }

}