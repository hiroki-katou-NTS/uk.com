module cps003.a.vm {
    import info = nts.uk.ui.dialog.info;
    import alert = nts.uk.ui.dialog.alert;
    import text = nts.uk.resource.getText;
    import format = nts.uk.text.format;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import showDialog = nts.uk.ui.dialog;
    import hasError = nts.uk.ui.errors.hasError;
    import clearError = nts.uk.ui.errors.clearAll;
    import liveView = nts.uk.request.liveView;

    const REPL_KEY = '__REPLACE',
        __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];

    export class ViewModel {
        ccgcomponent: any = {
            baseDate: ko.observable(new Date()),
            //Show/hide options
            isQuickSearchTab: ko.observable(true),
            isAdvancedSearchTab: ko.observable(true),
            isAllReferableEmployee: ko.observable(true),
            isOnlyMe: ko.observable(true),
            isEmployeeOfWorkplace: ko.observable(true),
            isEmployeeWorkplaceFollow: ko.observable(true),
            isMutipleCheck: ko.observable(true),
            isSelectAllEmployee: ko.observable(true),
            onSearchAllClicked: (dataList: Array<IEmployee>) => {
                let self = this,
                    data = self.gridList.inData;
                data.employees(dataList);
            },
            onSearchOnlyClicked: (data: IEmployee) => {
                let self = this,
                    _data = self.gridList.inData;
                _data.employees([data]);
            },
            onSearchOfWorkplaceClicked: (dataList: Array<IEmployee>) => {
                let self = this,
                    data = self.gridList.inData;
                data.employees(dataList);
            },
            onSearchWorkplaceChildClicked: (dataList: Array<IEmployee>) => {
                let self = this,
                    data = self.gridList.inData;
                data.employees(dataList);
            },
            onApplyEmployee: (dataList: Array<IEmployee>) => {
                let self = this,
                    data = self.gridList.inData;
                data.employees(dataList);
            }
        };

        gridList = {
            inData: {
                employees: ko.observableArray([]),
                itemDefitions: ko.observableArray([])
            },
            outData: ko.observableArray([])
        }

        // for employee info.
        employees: KnockoutObservableArray<IEmployee> = ko.observableArray([]);
        employee: KnockoutObservable<Employee> = ko.observable(new Employee());

        constructor() {
            let self = this,
                employee = self.employee();

            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent).done(() => {
            });
        }

        start() {
            let self = this,
                employee = self.employee();
        }

        saveData() {
            let self = this,
                emp = self.employee(),
                command: {
                };

            // trigger change of all control in layout
            _.each(__viewContext.primitiveValueConstraints, x => {
                if (_.has(x, "itemCode")) {
                    $('#' + x.itemCode).trigger('change');
                }
            })

            if (hasError()) {
                $('#func-notifier-errors').trigger('click');
                return;
            }

            // push data to webservice
            block();
            service.push.data(command).done(() => {
                info({ messageId: "Msg_15" }).then(function() {
                    unblock();
                    self.start();
                });
            }).fail((mes) => {
                unblock();
                alert(mes.message);
            });
        }
    }

    interface IEmployee {
    }

    class Employee {
    }
}