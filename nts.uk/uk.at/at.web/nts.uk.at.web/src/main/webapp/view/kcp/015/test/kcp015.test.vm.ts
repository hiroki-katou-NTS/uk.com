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
            
            baseDate1: KnockoutObservable<string>   = ko.observable('');
            listEmp: KnockoutObservableArray<any> = ko.observableArray([]);

            // param
            visibleA31: KnockoutObservable<boolean> = ko.observable(true);
            visibleA32: KnockoutObservable<boolean> = ko.observable(true);
            visibleA33: KnockoutObservable<boolean> = ko.observable(true);
            visibleA34: KnockoutObservable<boolean> = ko.observable(true);
            visibleA35: KnockoutObservable<boolean> = ko.observable(true);
            visibleA36: KnockoutObservable<boolean> = ko.observable(true);
            baseDate: KnockoutObservable<string>    = ko.observable('');
            sids: KnockoutObservableArray<any>      = ko.observableArray([]);
            
            ccgcomponent: any = {
                /** Common properties */
                systemType: 1, 
                showEmployeeSelection: true, 
                showQuickSearchTab: true, 
                showAdvancedSearchTab: true, 
                showBaseDate: false, 
                showClosure: false, 
                showAllClosure: true, 
                showPeriod: false, 
                periodFormatYM: true, 

                /** Required parame*/
                baseDate: moment.utc().toISOString(), // ?????????
                periodStartDate: moment.utc("1900/01/01", "YYYY/MM/DD").toISOString(), // ?????????????????????
                periodEndDate: moment.utc("9999/12/31", "YYYY/MM/DD").toISOString(), // ?????????????????????
                inService: true, 
                leaveOfAbsence: true, 
                closed: true, 
                retirement: false, 

                /** Quick search tab options */
                showAllReferableEmployee: true, 
                showOnlyMe: true, 
                showSameWorkplace: true, 
                showSameWorkplaceAndChild: true, 

                /** Advanced search properties */
                showEmployment: true, 
                showWorkplace: true, 
                showClassification: true, 
                showJobTitle: true, 
                showWorktype: false, 
                isMutipleCheck: true, 

                /** Return data */
                returnDataFromCcg001: (data: any) => {
                    let self = this;
                    self.listEmp(_.map(data.listEmployee, 'employeeId'));
                    self.sids(_.map(data.listEmployee, 'employeeId'));
                    console.log(data);
                }
            };
            
            constructor() {
                var self = this;
                $('#com-ccg001').ntsGroupComponent(self.ccgcomponent);
            }
            
            openKCP015() {
                let self = this;

                setShared('dataShareKCP015', {
                    checkedA3_1: self.checkedA3_1(),
                    checkedA3_2: self.checkedA3_2(),
                    checkedA3_3: self.checkedA3_3(),
                    checkedA3_4: self.checkedA3_4(),
                    checkedA3_5: self.checkedA3_5(),
                    checkedA3_6: self.checkedA3_6(),
                    listEmp    : self.listEmp(),
                    baseDate   : self.baseDate()
                });
                
                if(self.listEmp().length == 0){
                    nts.uk.ui.dialog.alert("please select employee");
                    return;
                }
                    
                nts.uk.ui.windows.sub.modal("/view/kcp/015/component/index.xhtml").onClosed(() => {});
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