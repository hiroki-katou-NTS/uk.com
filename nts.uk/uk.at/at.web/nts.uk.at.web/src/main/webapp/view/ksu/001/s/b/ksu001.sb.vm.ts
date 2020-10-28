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


                this.items.push(new ItemModel('000001 大塚　太郎B１', '000001 大塚　太郎B１', " H ", " D ", " 看護師 ", "営業残業(9:00-）", "分類３"));
                this.items.push(new ItemModel('000002 大塚　次郎２', '000002 大塚　次郎２', " ", " ", "", "Iﾜｰｸ(8:45-17:15） ", "分類1"));
                this.items.push(new ItemModel('000003 大塚　花子３', '000003 大塚　花子３', " H ", " D ", "", "職位２", "職位1"));
                this.items.push(new ItemModel('000004 000004', '000004 000004', " ", " ", " 看護師 ", "営業残業(9:00-） ", "職位1"));
                this.items.push(new ItemModel('000005 000005', '000005 000005', " ", " ", " ", "職位２", "職位1"));
                

                this.columns2 = ko.observableArray([
                    { headerText: 'コード', key: 'code', width: 100, hidden: true },
                    { headerText: 'コード／名称', key: 'name', width: 300 },
                    { headerText: 'チーム', key: 'description', width: 50 },
                    { headerText: 'ランク', key: 'other1', width: 50 },
                    { headerText: '看護区分', key: 'other2', width: 100 },
                    { headerText: '職位', key: 'other3', width: 300, width: 100 },
                    { headerText: '分類', key: 'other4', width: 300, width: 70 }

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
        other3: string;
        other4: string;

        constructor(code: string, name: string, description: string, other1: string, other2: string, other3: string, other4: string) {
            this.code = code;
            this.name = name;
            this.description = description;
            this.other1 = other1;
            this.other2 = other2;
            this.other3 = other3;
            this.other4 = other4;
        }
    }
}