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
            /** Common properties */
            systemType: 1, // シスッ�区�
            showEmployeeSelection: true, // 検索タイ�
            showQuickSearchTab: true, // クイヂ�検索
            showAdvancedSearchTab: true, // 詳細検索
            showBaseDate: false, // 基準日利用
            showClosure: false, // 就業�め日利用
            showAllClosure: true, // 全�め表示
            showPeriod: false, // 対象期間利用
            periodFormatYM: true, // 対象期間精度

            /** Required parame*/
            baseDate: moment.utc().toISOString(), // 基準日
            periodStartDate: moment.utc("1900/01/01", "YYYY/MM/DD").toISOString(), // 対象期間開始日
            periodEndDate: moment.utc("9999/12/31", "YYYY/MM/DD").toISOString(), // 対象期間終亗�
            inService: true, // 在職区�
            leaveOfAbsence: true, // 休�区�
            closed: true, // 休業区�
            retirement: false, // 退職区�

            /** Quick search tab options */
            showAllReferableEmployee: true, // 参�可能な社員すべて
            showOnlyMe: true, // 自刁��
            showSameWorkplace: true, // 同じ職場の社員
            showSameWorkplaceAndChild: true, // 同じ職場とそ�配下�社員

            /** Advanced search properties */
            showEmployment: true, // 雔�条件
            showWorkplace: true, // 職場条件
            showClassification: true, // 刡�条件
            showJobTitle: true, // 職位条件
            showWorktype: false, // 勤種条件
            isMutipleCheck: true, // 選択モー�

            /** Return data */
            returnDataFromCcg001: (data: any) => {
                let self = this;

                self.employees(data.listEmployee);
            }
        };

        gridList = {
            inData: {
                employees: ko.observableArray([]),
                itemDefitions: ko.observableArray([])
            },
            outData: ko.observableArray([])
        }
        
        baseDate: KnockoutObservable<Date> = ko.observable();

        category: {
            catId: KnockoutObservable<string>;
            items: KnockoutObservableArray<any>;
        } = {
            catId: ko.observable(''),
            items: ko.observableArray([])
        };

        // for employee info.
        employees: KnockoutObservableArray<IEmployee> = ko.observableArray([]);

        constructor() {
            let self = this;

            //fetch all category by login 
            service.fetch.category(__viewContext.user.employeeId)
                .done(data => self.category.items(data));

            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent).done(() => {
            });
        }

        start() {
            let self = this;
        }

        saveData() {
            let self = this,
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

        openBDialog() {
            let self = this,
                params = {
                    systemDate: "2018/12/21",
                    categoryId: "111",
                    categoryName: "AAAA",
                    mode: 1,
                    columnChange: [],
                    sids: []
                };

            block();
            setShared('CPS003B_VALUE', params);
            
            modal("/view/cps/003/b/index.xhtml").onClosed(() => {
            });
        }
    }

    interface IEmployee {
    }

    class Employee {
    }
}