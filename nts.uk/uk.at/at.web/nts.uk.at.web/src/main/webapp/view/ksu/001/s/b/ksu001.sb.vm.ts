module nts.uk.at.view.ksu001.s.sb {
    import setShare = nts.uk.ui.windows.setShared;
    import service = nts.uk.at.view.ksu001.s.sb.service;
    // Import
    export module viewmodel {
        export class ScreenModel {
            lstEmp: KnockoutObservable<any> = ko.observable(nts.uk.ui.windows.getShared('KSU001SB')).lstEmp;
            date: KnockoutObservable<any> = ko.observable(nts.uk.ui.windows.getShared('KSU001SB')).date;
            selectedEmployeeSwap: KnockoutObservable<any> = ko.observable(nts.uk.ui.windows.getShared('KSU001SB')).selectedEmployeeSwap;

            items: KnockoutObservableArray<ItemModel>;
            currentCode: KnockoutObservable<any>;
            constructor() {
                var self = this;
                this.items = ko.observableArray([]);

                for (let i = 1; i < 100; i++) {
                    this.items.push(new ItemModel('00' + i, '基本給', "description " + i, i % 3 === 0, "2010/1/1"));
                }
                this.columns2 = ko.observableArray([
                    { headerText: 'コード', key: 'code', width: 100, hidden: true },
                    { headerText: '名称', key: 'name', width: 150 },
                    { headerText: '説明', key: 'description', width: 150 },
                    { headerText: '説明1', key: 'other1', width: 150 },
                    { headerText: '説明2', key: 'other2', width: 150 },
                    { headerText: 'Switch', key: 'switchValue', width: 300, controlType: 'switch' }
                ]);
                this.currentCode = ko.observable();






            }

            public startPage(): JQueryPromise<any> {
                let self = this,
                    dataShare = nts.uk.ui.windows.getShared('KSU001SB');

                let dfd = $.Deferred();

                let param = {
                    listEmpId: dataShare.lstEmp.listEmpId,
                    date: dataShare.date,
                    selectedEmployeeSwap: dataShare.selectedEmployeeSwap
                }

                service.getData(param).done(function(data: any) {
                    console.log(data);
                });

                dfd.resolve();
                return dfd.promise();
            }

            cancel_Dialog(): any {
                let self = this;
                nts.uk.ui.windows.close();
            }

        }
    }
    class ItemModel {
        code: string;
        name: string;
        description: string;
        other1: string;
        other2: string;
        deletable: boolean;
        switchValue: boolean;
        constructor(code: string, name: string, description: string, deletable: boolean, other1?: string, other2?: string) {
            this.code = code;
            this.name = name;
            this.description = description;
            this.other1 = other1;
            this.other2 = other2 || other1;
            this.deletable = deletable;
            this.switchValue = ((code % 3) + 1).toString();
        }
    }
}