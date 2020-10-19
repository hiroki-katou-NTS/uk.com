module nts.uk.at.view.kcp015.test {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export module viewmodel {
        export class ScreenModel {
            itemList: KnockoutObservableArray<any>;
            selectedId: KnockoutObservable<number>;
            
            checkedA3_1 : KnockoutObservable<boolean> = ko.observable(true);
            checkedA3_2 : KnockoutObservable<boolean> = ko.observable(true);
            checkedA3_3 : KnockoutObservable<boolean> = ko.observable(true);
            checkedA3_4 : KnockoutObservable<boolean> = ko.observable(true);
            checkedA3_5 : KnockoutObservable<boolean> = ko.observable(true);
            checkedA3_6 : KnockoutObservable<boolean> = ko.observable(true);
            enable      : KnockoutObservable<boolean> = ko.observable(true);
            
            baseDate: KnockoutObservable<string>   = ko.observable('');
            listEmp : KnockoutObservableArray<any> = ko.observableArray([]);
            
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
                    self.listEmp(_.map(data.listEmployee, 'employeeId'));
                    console.log(data);
                }
            };
            
            constructor() {
                var self = this;
                
                $('#com-ccg001').ntsGroupComponent(self.ccgcomponent);
                
                self.itemList = ko.observableArray([
                    new BoxModel(1, 'has parameters'),
                    new BoxModel(2, 'no parameters')
                ]);
                self.selectedId = ko.observable(1);
                
                self.selectedId.subscribe(function(value) {
                    if (value == 1) {
                        self.checkedA3_1(true);
                        self.checkedA3_2(true);
                        self.checkedA3_3(true);
                        self.checkedA3_4(true);
                        self.checkedA3_5(true);
                        self.checkedA3_6(true);
                        self.enable(true);
                    } else {
                        self.checkedA3_1(false);
                        self.checkedA3_2(false);
                        self.checkedA3_3(false);
                        self.checkedA3_4(false);
                        self.checkedA3_5(false);
                        self.checkedA3_6(false);
                        self.enable(false);
                    }
                });
            }
            
            openKCP015() {
                let self = this;

                setShared('dataShareKCP015', {
                    hasParams: self.selectedId() == 1 ? true : false,
                    checkedA3_1: self.checkedA3_1(),
                    checkedA3_2: self.checkedA3_2(),
                    checkedA3_3: self.checkedA3_3(),
                    checkedA3_4: self.checkedA3_4(),
                    checkedA3_5: self.checkedA3_5(),
                    checkedA3_6: self.checkedA3_6(),
                    listEmp    : self.listEmp(),
                    baseDate   : self.baseDate()
                });
                
                nts.uk.ui.windows.sub.modal("/view/kcp/015/component2/index.xhtml").onClosed(() => {});
            }
        }
    }
}

class BoxModel {
    id: number;
    name: string;
    constructor(id, name){
        var self = this;
        self.id = id;
        self.name = name;
    }
}