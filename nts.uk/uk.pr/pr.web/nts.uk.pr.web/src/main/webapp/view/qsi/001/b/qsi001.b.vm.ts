module nts.uk.pr.view.qsi001.b.viewmodel {

    import block = nts.uk.ui.block;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        employeeInputList: KnockoutObservableArray<EmployeeModel>;
        //

        depNotiAttach: KnockoutObservableArray<any>;
        selectedDepNotoAttach: any;

        basicPension: KnockoutObservable<number>;
        salaryMonthly: KnockoutObservable<number>;
        salaryMonthlyActual: KnockoutObservable<number>;
        totalCompensation: KnockoutObservable<number>;
        depNotiAttach: KnockoutObservableArray<any>;
        selectedDepNotiAttach: any;

        applyToEmployeeOver70: KnockoutObservable<boolean>;
        twoOrMoreEmployee: KnockoutObservable<boolean>;
        shortWorkHours: KnockoutObservable<boolean>;
        continuousEmpAfterRetire: KnockoutObservable<boolean>;
        otherNotes: KnockoutObservable<boolean>;
        textOtherNotes: KnockoutObservable<boolean>;
        shortTermResidence: KnockoutObservable<boolean>;
        otherNotes1: KnockoutObservable<boolean>;
        textOtherNotes1: KnockoutObservable<boolean>;

        livingAbroad: KnockoutObservable<boolean>;
        //kcp009
        systemReference: KnockoutObservable<number>;
        isDisplayOrganizationName: KnockoutObservable<boolean>;
        targetBtnText: string;
        baseDate: KnockoutObservable<Date>;
        listComponentOption: ComponentOption;
        selectedItem: KnockoutObservable<string>;
        tabindex: number;

        constructor() {
            block.invisible();
            let self = this;

            let params = getShared('QSI001_PARAMS_TO_SCREEN_B');

            if (params && params.listEmpId.length > 0) {
                self.loadKCP009(self.createEmployeeModel(params.listEmpId));

                //起動する
                //load page
                service.getSocialInsurAcquisiInforById(params.listEmpId[0].employeeId).done(e => {
                    if (e) {
                        self.otherNotes(e.remarksOther == 1 ? true : false);
                        self.textOtherNotes(e.remarksAndOtherContents);
                        self.salaryMonthlyActual(e.remunMonthlyAmountKind);
                        self.salaryMonthly(e.remunMonthlyAmount);
                        self.totalCompensation(e.totalMonthlyRemun);
                        self.livingAbroad(e.livingAbroad == 1 ? true : false);
                        self.otherNotes1(e.reasonOther == 1 ? true : false);
                        self.textOtherNotes1(e.reasonAndOtherContents);
                        self.shortTermResidence(e.shortStay == 1 ? true : false);
                        self.selectedDepNotiAttach(e.depenAppoint);
                        self.shortWorkHours(e.shortTimeWorkers == 1 ? true : false);
                        self.continuousEmpAfterRetire(e.continReemAfterRetirement == 1 ? true : false);
                    } else {
                        self.otherNotes(false);
                        self.textOtherNotes(null);
                        self.salaryMonthlyActual(null);
                        self.salaryMonthly(null);
                        self.totalCompensation(null);
                        self.livingAbroad(false);
                        self.otherNotes1(false);
                        self.textOtherNotes1(null);
                        self.shortTermResidence(false);
                        self.selectedDepNotiAttach(0);
                        self.shortWorkHours(false);
                        self.continuousEmpAfterRetire(false);
                    }
                }).fail(e => {

                });

                service.getPersonInfo(self.selectedItem()).done(r => {
                    if (self.getAge(r.birthDay, params.date) >= 70) {
                        self.applyToEmployeeOver70(true);
                        self.otherNotes(true);
                    }
                }).fail(f => {
                    console.dir(f);
                });

                //社員を切り替える
                //select employee
                self.selectedItem.subscribe(e => {
                    service.getSocialInsurAcquisiInforById(self.selectedItem()).done(e => {
                        if (e) {
                            self.otherNotes(e.remarksOther == 1 ? true : false);
                            self.textOtherNotes(e.remarksAndOtherContents);
                            self.salaryMonthlyActual(e.remunMonthlyAmountKind);
                            self.salaryMonthly(e.remunMonthlyAmount);
                            self.totalCompensation(e.totalMonthlyRemun);
                            self.livingAbroad(e.livingAbroad == 1 ? true : false);
                            self.otherNotes1(e.reasonOther == 1 ? true : false);
                            self.textOtherNotes1(e.reasonAndOtherContents);
                            self.shortTermResidence(e.shortStay == 1 ? true : false);
                            self.selectedDepNotiAttach(e.depenAppoint);
                            self.shortWorkHours(e.shortTimeWorkers == 1 ? true : false);
                            self.continuousEmpAfterRetire(e.continReemAfterRetirement == 1 ? true : false);
                        } else {
                            self.otherNotes(false);
                            self.textOtherNotes(null);
                            self.salaryMonthlyActual(null);
                            self.salaryMonthly(null);
                            self.totalCompensation(null);
                            self.livingAbroad(false);
                            self.otherNotes1(false);
                            self.textOtherNotes1(null);
                            self.shortTermResidence(false);
                            self.selectedDepNotiAttach(0);
                            self.shortWorkHours(false);
                            self.continuousEmpAfterRetire(false);
                        }

                    }).fail(e => {

                    });

                    service.getPersonInfo(self.selectedItem()).done(r => {
                        if (self.getAge(r.birthDay, params.date) >= 70) {
                            self.applyToEmployeeOver70(true);
                            self.otherNotes(true);
                        }
                    }).fail(f => {
                        console.dir(f);
                    });
                });


            }


            //init

            self.depNotiAttach = ko.observableArray([]);
            self.selectedDepNotoAttach = ko.observable({});

            self.basicPension = ko.observable(null);
            self.salaryMonthly = ko.observable(null);
            self.salaryMonthlyActual = ko.observable(null);
            self.totalCompensation = ko.observable(null);
            self.depNotiAttach = ko.observableArray([
                {code: '0', name: nts.uk.resource.getText('QSI001_54')},
                {code: '1', name: nts.uk.resource.getText('QSI001_55')}
            ]);
            self.selectedDepNotiAttach = ko.observable(0);

            self.applyToEmployeeOver70 = ko.observable(false);
            self.twoOrMoreEmployee = ko.observable(false);
            self.shortWorkHours = ko.observable(false);
            self.continuousEmpAfterRetire = ko.observable(false);
            self.otherNotes = ko.observable(false);
            self.textOtherNotes = ko.observable(null);

            self.livingAbroad = ko.observable(false);
            self.shortTermResidence = ko.observable(false);
            self.otherNotes1 = ko.observable(false);
            self.textOtherNotes1 = ko.observable(null);


            block.clear();
        }

        getAge(DOB, date) {
            var today = new Date(date);
            var birthDate = new Date(DOB);
            var age = today.getFullYear() - birthDate.getFullYear();
            var m = today.getMonth() - birthDate.getMonth();
            if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
                age = age - 1;
            }

            return age;
        }

        cancel() {
            nts.uk.ui.windows.close();
        }

        add() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            $("input").trigger("validate");
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            block.invisible();
            let data = {
                socialInsurAcquisiInforCommand: {
                    employeeId: self.selectedItem(),
                    //70歳以上被用者
                    percentOrMore: self.applyToEmployeeOver70() == true ? 1 : 0,
                    //備考その他
                    remarksOther: self.otherNotes() == true ? 1 : 0,
                    //備考その他内容
                    remarksAndOtherContents: self.textOtherNotes(),
                    //報酬月額（現物）
                    remunMonthlyAmountKind: Number(self.salaryMonthlyActual()),
                    //報酬月額（金額）
                    remunMonthlyAmount: Number(self.salaryMonthly()),
                    //報酬月額合計
                    totalMonthlyRemun: Number(self.totalCompensation()),
                    //海外在住
                    livingAbroad: self.livingAbroad() == true ? 1 : 0,
                    //理由その他
                    reasonOther: self.otherNotes1() == true ? 1 : 0,
                    //理由その他内容
                    reasonAndOtherContents: self.textOtherNotes1(),
                    //短期在留
                    shortStay: self.shortTermResidence() == true ? 1 : 0,
                    //被扶養者届出区分
                    depenAppoint: self.selectedDepNotiAttach(),
                    qualifiDistin: null,
                    //短時間労働者
                    shortTimeWorkers: self.shortWorkHours() == true ? 1 : 0,
                    //退職後の継続再雇用者
                    continReemAfterRetirement: self.continuousEmpAfterRetire() == true ? 1 : 0
                },
                empBasicPenNumInforCommand: {
                    employeeId: self.selectedItem(),
                    basicPenNumber: Number(self.basicPension())

                },
                multiEmpWorkInfoCommand: {
                    employeeId: self.selectedItem(),
                    isMoreEmp: self.twoOrMoreEmployee() == true ? 1 : 0,

                }
            }


            service.add(data).done(e => {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(e=>{
                    block.clear();
                })
            }).fail(e => {
                block.clear();
            });
        }

        createEmployeeModel(data) {
            let listEmployee = [];
            _.each(data, data => {
                listEmployee.push({
                    id: data.employeeId,
                    code: data.employeeCode,
                    businessName: data.employeeName,
                    workplaceName: data.workplaceName
                });
            });

            return listEmployee;
        }

        loadKCP009(data) {
            let self = this;
            self.employeeInputList = ko.observableArray(data);
            self.systemReference = ko.observable(SystemType.EMPLOYMENT);
            self.isDisplayOrganizationName = ko.observable(true);
            self.targetBtnText = nts.uk.resource.getText("KCP009_3");
            self.selectedItem = ko.observable(null);
            self.tabindex = 3;
            // Initial listComponentOption
            self.listComponentOption = {
                systemReference: self.systemReference(),
                isDisplayOrganizationName: self.isDisplayOrganizationName(),
                employeeInputList: self.employeeInputList,
                targetBtnText: self.targetBtnText,
                selectedItem: self.selectedItem,
                tabIndex: self.tabindex
            };
            $('#emp-component').ntsLoadListComponent(self.listComponentOption);
        }


    }


    export interface ComponentOption {
        systemReference: SystemType;
        isDisplayOrganizationName: boolean;
        employeeInputList: KnockoutObservableArray<EmployeeModel>;
        targetBtnText: string;
        selectedItem: KnockoutObservable<string>;
        tabIndex: number;
        baseDate?: KnockoutObservable<Date>;
    }

    export interface EmployeeModel {
        id: string;
        code: string;
        businessName: string;
        depName?: string;
        workplaceName?: string;
    }

    export class SystemType {
        static EMPLOYMENT = 1;
        static SALARY = 2;
        static PERSONNEL = 3;
        static ACCOUNTING = 4;
        static OH = 6;
    }
}