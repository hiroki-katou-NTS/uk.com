module nts.uk.pr.view.qmm005.a.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import model = nts.uk.pr.view.qmm005.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import CurrentProcessDate = nts.uk.pr.view.qmm005.share.model.CurrentProcessDate;
    import modal = nts.uk.ui.windows.sub.modal;
    import SetDaySupport = nts.uk.pr.view.qmm005.share.model.SetDaySupport;
    export class ScreenModel {
        //A2_2
        itemTable: ItemTable;
        //A3_4 対象雇用
        targetEmployment: KnockoutObservableArray<number>;
        processCategoryNO: number;
        itemBinding: KnockoutObservableArray<ItemBinding>;

        test: Array<model.ItemModel>;


        constructor() {
            var self = this;
            $("#A2_2").ntsFixedTable({height: 300, width: 1000});
            $("#A3_1").ntsFixedTable({height: 300, width: 400});
            //A3_4 対象雇用
            self.targetEmployment = ko.observable([]);

            self.itemBinding = ko.observableArray([]);


        }

        showDialogD(processInfomation, mode): void {
            let param = {
                mode: mode,
                processInfomation: processInfomation
            }
            setShared("QMM005_output_D", param);
            modal('/view/qmm/005/d/index.xhtml', {title: '',}).onClosed(function (): any {
            })
        }


        showDialogB(param): void {
            setShared("QMM005_output_B", param);
            modal('/view/qmm/005/b/index.xhtml', {title: '',}).onClosed(function (): any {
            })
        }

        showDialogF(param): void {
            let self = this;
            let employeeArr = new Array();
            employeeArr = self.itemTable.empCdNameImports;
            let paramEmployment = {
                processCateNo: param,
                employeeList: employeeArr
            }
            setShared("QMM005_output_F", paramEmployment);
            modal('/view/qmm/005/f/index.xhtml', {title: '',}).onClosed(function (): any {
                let employeeString = '';
                let params = getShared("QMM005F_outParams");
                for (let i = 0; i < params.returnList.length; i++) {
                    employeeString == '' ? employeeString += params.returnList[i].name : employeeString += (', ' + params.returnList[i].name);
                }
                for (let i = 0; i < 5; i++) {
                    if (self.itemBinding()[i].processInfomation.processCateNo == params.processCateNo) {
                        self.itemBinding()[i].employeeString(employeeString);
                        self.itemBinding()[i].employeeCodeList(params.returnList);
                    }
                }
            });

        }


        getYear(setDaySupportList: Array<SetDaySupport>): Array<model.ItemModel> {
            var array = [];
            _.forEach(setDaySupportList, function (setDaySupport) {
                _.forEach(setDaySupport, function (value, key) {
                    if (key == "processDate") {
                        let yearCode = parseInt(value.toString().substr(0, 4));
                        let yearName = value.toString().substr(0, 4);
                        array.push(new model.ItemModel(yearCode, yearName));
                    }
                });
            });
            return _.orderBy(_.uniqBy(array, 'code'), ['code'], ['asc']);

        }


        getListMonth(setDaySupportList: Array<SetDaySupport>, i: number): Array<model.ItemModel> {
            let self = this;
            let ArrSetDaySuport: Array<SetDaySupport> = _.sortBy(_.filter(self.itemTable.setDaySupports, function (o) {
                    return o.processCateNo == i + 1;
                }),
                function (o) {
                    return o.processDate;
                });

            let Araybinding: Array<model.ItemModel> = new Array<model.ItemModel>();
            for (let i = 0; i < ArrSetDaySuport.length; i++) {
                Araybinding.push(new model.ItemModel(ArrSetDaySuport[i].processDate, ArrSetDaySuport[i].paymentDate + '-' + ArrSetDaySuport[i].empExtraRefeDate))
            }


            return Araybinding;
        }


        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.findDisplayRegister().done(data => {
                self.itemTable = new ItemTable(data);
                console.log(self.itemTable);


                var sizetalbe = self.itemTable.processInfomations.length;
                for (let i: number = sizetalbe; i < 5; i++) {
                    self.itemTable.processInfomations.push(new model.ProcessInfomation({
                            processCateNo: i + 1,
                            processDivisionName: '',
                            deprecatCate: 0
                        }
                    ));
                }

                for (let i: number = 0; i < 5; i++) {
                    let employeeSetting = _.find(self.itemTable.empTiedProYear, x => {
                        return x.processCateNo == i + 1;
                    })
                    let employeeString = '';
                    if (employeeSetting) {
                        for (let i = 0; i < employeeSetting.getEmploymentCodes.length; i++) {
                            let obj = _.find(self.itemTable.empCdNameImports, x => {
                                return x.code == employeeSetting.getEmploymentCodes[i];
                            })
                            employeeString == '' ? employeeString += obj.name : employeeString += (', ' + obj.name);
                        }
                    } else {
                        employeeSetting = {}
                    }
                    self.itemBinding.push(new ItemBinding(
                        self.itemTable.processInfomations[i],
                        _.sortBy(_.filter(self.itemTable.setDaySupports, function (o) {
                                return o.processCateNo == i + 1;
                            }),
                            function (o) {
                                return o.processDate;
                            }),
                        employeeString, employeeSetting.getEmploymentCodes,
                        self.getYear((_.filter(self.itemTable.setDaySupports, function (o) {
                            return o.processCateNo == i + 1;
                        }))),
                        self.getListMonth(self.itemTable.setDaySupports, i)
                    ));

                }
                console.log(self.itemBinding());


            });
            dfd.resolve();
            return dfd.promise();
        }
    }


    export class ItemBinding {
        processInfomation: model.ProcessInfomation;
        setDaySupports: KnockoutObservableArray<model.SetDaySupport>;
        setDaySupportsSelectedCode: KnockoutObservable<number>;
        employeeString: KnockoutObservable<string>;
        employeeCodeList: KnockoutObservableArray<any>;
        years: KnockoutObservableArray<model.ItemModel>;
        yaersSelected: KnockoutObservable<number>;
        months: KnockoutObservableArray<model.ItemModel>;
        monthsSelectd: KnockoutObservable<number>;

        constructor(processInfomation: model.ProcessInfomation, setDaySupports: Array<model.SetDaySupport>, employeeString: string, employeeCodeList: Array, years: Array<model.ItemModel>, months: Array<model.ItemModel>) {
            this.processInfomation = processInfomation;
            this.setDaySupports = ko.observableArray(setDaySupports);
            this.setDaySupportsSelectedCode = ko.observable(0);
            this.employeeString = ko.observable(employeeString);
            this.employeeCodeList = ko.observableArray(employeeCodeList);
            this.years = ko.observableArray(years);
            this.yaersSelected = ko.observable(0);
            this.months = ko.observableArray(months);
            this.monthsSelectd = ko.observable(0);
        }
    }


    export interface IitemTable {
        informationDto: Array<model.ProcessInfomation>,
        setDaySupportDto: Array<model.SetDaySupport>,
        currProcessDateDto: Array<model.CurrentProcessDate>,
        empTiedProYearDto: Array<model.EmpTiedProYear>,
        empCdNameImports: Array<model.EmpCdNameImport>
    }

    export class ItemTable {
        processInfomations: Array<model.ProcessInfomation>;
        setDaySupports: Array<model.SetDaySupport>;
        currentProcessDates: Array<model.CurrentProcessDate>;
        empCdNameImports: Array<model.EmpCdNameImport>;
        empTiedProYear: Array<model.EmpTiedProYear>;

        constructor(param: IitemTable) {
            this.processInfomations = param.informationDto;
            this.currentProcessDates = param.currProcessDateDto;
            this.setDaySupports = param.setDaySupportDto;
            this.empCdNameImports = param.empCdNameImports;
            this.empTiedProYear = param.empTiedProYearDto;
        }


    }


}

