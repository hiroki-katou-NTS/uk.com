module nts.uk.at.view.kmt014.a.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    const API = {
        getTasks: "at/shared/scherec/taskmanagement/taskassign/employee/findtasks",
        getSettings: "at/shared/scherec/taskmanagement/taskassign/employee/find",
        getAlreadySet: "at/shared/scherec/taskmanagement/taskassign/employee/findAlreadySet",
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
        updateMode: KnockoutObservable<boolean> = ko.observable(false);

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
                    self.updateMode(selectedTask ? selectedTask.alreadySet : false);
                    self.$blockui("show").then(() => {
                        return self.$ajax(API.getSettings, {taskFrameNo: self.taskFrameNo(), taskCode: value});
                    }).done((res: Array<any>) => {
                        if (!_.isEmpty(res)) {
                            self.searchEmployeeInfo(res.map(i => i.employeeId));
                        } else {
                            self.employeeList([]);
                            self.$blockui("hide");
                        }
                        self.selectedEmployees([]);
                    }).fail((error: any) => {
                        self.$dialog.error(error);
                        self.$blockui("hide");
                    }).always(() => {
                        $('#A6_2').focus();
                    });
                } else {
                    self.taskName(null);
                    self.startDate(null);
                    self.endDate(null);
                    self.employeeList([]);
                    self.selectedEmployees([]);
                    self.updateMode(false);
                }
            });
            $(document).delegate('#task-list', "iggridrowsrendered", function(evt) {
                self.addIconToAlreadyCol();
            });
        }

        addIconToAlreadyCol() {
            // Add icon to column already setting.
            var iconLink = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["com"] + '/')
                .mergeRelativePath('/view/kcp/share/icon/icon78.png').serialize();
            $('.icon-78').attr('style', "background: url('" + iconLink + "');width: 20px;height: 20px;background-size: 20px 20px;")
        }

        getTaskList(baseDate: string) {
            const self = this;
            self.$blockui("show");
            $.when(
                self.$ajax(API.getTasks, {
                    taskFrameNo: self.taskFrameNo(),
                    baseDate: moment(baseDate).format('YYYY/MM/DD')
                }),
                self.$ajax(API.getAlreadySet, {taskFrameNo: self.taskFrameNo()})
            ).done((tasks: Array<any>, taskCodes: Array<string>) => {
                tasks.forEach(t => {
                    if (taskCodes.indexOf(t.taskCode) >= 0) t.alreadySet = '<i class="icon icon-78"/>';
                    else t.alreadySet = "";
                });
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
                $('#A6_2').ntsError('set', {messageId:'MsgB_2',messageParams:[nts.uk.resource.getText('KMT014_21')]});
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
                    const tasks = _.cloneDeep(self.taskList());
                    tasks.forEach(task => {
                        if (task.taskCode == self.taskCode()) {
                            task.alreadySet = '<i class="icon icon-78"/>';
                        }
                    });
                    self.taskList(tasks);
                    self.updateMode(true);
                    $('#A6_2').focus();
                });
            }).fail((error: any) => {
                self.$dialog.error(error);
            }).always(() => {
                self.$blockui("hide");
            });
        }

        deleteSetting() {
            const self = this;
            self.$dialog.confirm({ messageId: 'Msg_18'}).then((result) => {
                if( result === 'yes') {
                    self.$blockui("show").then(() => {
                        return self.$ajax(API.remove, {
                            taskFrameNo: self.taskFrameNo(),
                            taskCode: self.taskCode()
                        });
                    }).done((res: any) => {
                        self.$dialog.info({messageId: 'Msg_16'}).then(() => {
                            const tasks = _.cloneDeep(self.taskList());
                            tasks.forEach(task => {
                                if (task.taskCode == self.taskCode()) {
                                    task.alreadySet = "";
                                }
                            });
                            self.taskList(tasks);
                            self.employeeList([]);
                            self.updateMode(false);
                            $('#A6_2').focus();
                        });
                    }).fail((error: any) => {
                        self.$dialog.error(error);
                    }).always(() => {
                        self.$blockui("hide");
                    });
                }
            });
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
                    self.employeeList().forEach(e => {
                        employeeIds.push(e.id);
                    });
                    self.searchEmployeeInfo(employeeIds);
                }
                setShared('CDL009Cancel', undefined);
                setShared('CDL009Output', undefined);
            });
        }

        searchEmployeeInfo(employeeIds: Array<string>) {
            const self = this;
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

        removeEmployee() {
            const self = this;
            self.employeeList(self.employeeList().filter(e => self.selectedEmployees().indexOf(e.code) < 0));
            self.selectedEmployees([]);
        }

        goback() {
            const vm = this;
            vm.$jump("/view/kmt/011/a/index.xhtml", {screen: "KMT014"});
        }
    }

}