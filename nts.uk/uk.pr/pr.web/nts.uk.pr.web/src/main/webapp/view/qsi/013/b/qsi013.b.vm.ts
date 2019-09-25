module nts.uk.pr.view.qsi013.b.viewmodel {
    import model = nts.uk.pr.view.qsi013.share.model;
    import dialog = nts.uk.ui.dialog;
    import block = nts.uk.ui.block;
    import getShared = nts.uk.ui.windows.getShared;

    export interface ComponentOption {
        systemReference: model.SystemType;
        isDisplayOrganizationName: boolean;
        employeeInputList: KnockoutObservableArray<model.EmployeeModel>;
        targetBtnText: string;
        selectedItem: KnockoutObservable<string>;
        tabIndex: number;
        baseDate?: KnockoutObservable<Date>;
    }

    export class ScreenModel {
        screenMode: KnockoutObservable<model.SCREEN_MODE> = ko.observable(null);
        constraint: string = 'LayoutCode';
        inline: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        employeeInputList: KnockoutObservableArray<model.EmployeeModel>= ko.observableArray([]);
        systemReference: KnockoutObservable<number>;
        isDisplayOrganizationName: KnockoutObservable<boolean>;
        targetBtnText: string;
        baseDate: KnockoutObservable<Date>;
        listComponentOption: ComponentOption;
        selectedItem: KnockoutObservable<string> = ko.observable('');
        tabindex: number;

        //checked
        isMoreEmp: KnockoutObservable<boolean> = ko.observable();
        hOther: KnockoutObservable<boolean> = ko.observable(false);
        pOther: KnockoutObservable<boolean> = ko.observable(false);

        //number editor
        hCaInsurance: KnockoutObservable<number> = ko.observable(null);
        continReemAfterRetirement: KnockoutObservable<boolean> = ko.observable(false);
        hNumRecoved: KnockoutObservable<number> = ko.observable(null);
        pCaInsurance: KnockoutObservable<number> = ko.observable(null);
        pNumRecoved: KnockoutObservable<number> = ko.observable(null);
        basicPenNumber: KnockoutObservable<string> = ko.observable('');

        //combo box
        itemListHealth: KnockoutObservableArray<model.ItemModel>;
        itemListPension: KnockoutObservableArray<model.ItemModel>;
        hCause: KnockoutObservable<number> = ko.observable(0);
        pCause: KnockoutObservable<number> = ko.observable(0);
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;

        //declard para employeeId to push method
        empId: string;

        //for text editor
        hOtherReason: KnockoutObservable<string> = ko.observable('');
        pOtherReason: KnockoutObservable<string> = ko.observable('');

        getDataDefault(){
            let self = this;
            self.hCause(0);
            self.hNumRecoved(null);
            self.hOther(false);
            self.hCaInsurance(null);
            self.hOtherReason('');

            self.pCause(0);
            self.pNumRecoved(null);
            self.pOther(false);
            self.pCaInsurance(null);
            self.pOtherReason('');

            self.isMoreEmp(null);
            self.continReemAfterRetirement(null);
            self.basicPenNumber('');
            // for new mode
            //set default value
            self.screenMode(model.SCREEN_MODE.NEW);
        }
        getDataLossInfo(empId: string) {
            block.invisible();
            let self = this;
            nts.uk.pr.view.qsi013.b.service.getLossInfoById(empId).done(function (data: any) {
                if (data) {
                    if(data.healthInsLossInfo){
                        self.hCause(data.healthInsLossInfo.cause);
                        self.hNumRecoved(data.healthInsLossInfo.numRecoved);
                        self.hOther(data.healthInsLossInfo.other == 1);
                        self.hCaInsurance(data.healthInsLossInfo.caInsurance);
                        self.hOtherReason(data.healthInsLossInfo.otherReason);
                    }

                    if(data.welfPenInsLossIf){
                        self.pCause(data.welfPenInsLossIf.cause);
                        self.pNumRecoved(data.welfPenInsLossIf.numRecoved);
                        self.pOther(data.welfPenInsLossIf.other ==1);
                        self.pCaInsurance(data.welfPenInsLossIf.caInsuarace);
                        self.pOtherReason(data.welfPenInsLossIf.otherReason);
                    }

                    if(data.multiEmpWorkInfo){
                        self.isMoreEmp(data.multiEmpWorkInfo.isMoreEmp == 1);
                    }

                    if(data.empBasicPenNumInfor) {
                        self.basicPenNumber(data.empBasicPenNumInfor.basicPenNumber);
                    }

                    if(data.socialInsurAcquisiInfor){
                        self.continReemAfterRetirement(data.socialInsurAcquisiInfor.continReemAfterRetirement == 1 );
                    }
                    self.screenMode(model.SCREEN_MODE.UPDATE);

                } else {
                    this.getDataDefault();
                }
            }).fail(error => {
                dialog.alertError(error);
            });
            block.clear();
        }

        init(){
            var self = this;
            self.inline = ko.observable(true);
            self.required = ko.observable(true)
            self.enable = ko.observable(true);
            self.systemReference = ko.observable(model.SystemType.EMPLOYMENT);
            self.isDisplayOrganizationName = ko.observable(false);
            self.targetBtnText = nts.uk.resource.getText("KCP009_3");
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            self.listComponentOption = {
                systemReference: self.systemReference(),
                isDisplayOrganizationName: self.isDisplayOrganizationName(),
                employeeInputList: self.employeeInputList,
                targetBtnText: self.targetBtnText,
                selectedItem: self.selectedItem,
                tabIndex: 0
            };
            $('#emp-component').ntsLoadListComponent(self.listComponentOption);
        }
        constructor() {
            var self = this;
            self.selectedItem.subscribe((data) => {
                self.getDataLossInfo(data);
            });
            let list = getShared("QSI013_PARAMS_B");
            if(nts.uk.util.isNullOrEmpty(list) || nts.uk.util.isNullOrEmpty(list.employeeList)) {
                close();
            }
            self.employeeInputList(self.createEmployeeModel(list.employeeList));
            this.init();
            self.selectedItem(self.employeeInputList()[0].id);
            self.itemListHealth = ko.observableArray(model.getCauseTypeHealthLossInfo());
            self.itemListPension = ko.observableArray(model.getCauseTypePensionLossInfo());

        }

        //set cancel method
        cancel() {
            nts.uk.ui.windows.close();
        }

        registerLossInfo() {
            var self = this;
            //for register info
            let healthInsLossInfo: any = {
                empId: self.selectedItem(),
                other : self.hOther() == true ? 1 : 0,
                otherReason : self.hOther() == true ? self.hOtherReason() : null,
                caInsurance: self.hCaInsurance(),
                numRecoved: self.hNumRecoved(),
                cause: self.hCause(),
            };

            let welfPenInsLossIf: any = {
                empId: self.selectedItem(),
                other : self.pOther() == true ? 1 : 0,
                otherReason : self.pOther() == true ? self.pOtherReason() : null,
                caInsuarace: self.pCaInsurance(),
                numRecoved: self.pNumRecoved(),
                cause: self.pCause(),
            };
            let multiEmpWorkInfo: any = {
                empId: self.selectedItem(),
                isMoreEmp: self.isMoreEmp() == true ?  1 : 0
            };

            let empBasicPenNumInfor: any = {
                employeeId: self.selectedItem(),
                basicPenNumber: self.basicPenNumber()
            };

            let socialInsurAcquisiInfo: any = {
                employeeId: self.selectedItem(),
                continReemAfterRetirement: self.continReemAfterRetirement() == true ?  1 : 0
            };


            let lossInfoCommand: any = {
                healthInsLossInfo: healthInsLossInfo,
                welfPenInsLossIf: welfPenInsLossIf,
                multiEmpWorkInfo: multiEmpWorkInfo,
                empBasicPenNumInfor: empBasicPenNumInfor,
                socialInsurAcquisiInfo : socialInsurAcquisiInfo,
                screenMode: model.SCREEN_MODE.UPDATE

            };

            nts.uk.pr.view.qsi013.b.service.registerLossInfo(lossInfoCommand).done( function() {
                dialog.info({ messageId: "Msg_15" }).then(() => {
                    //enter update mode
                    self.screenMode(model.SCREEN_MODE.UPDATE);
                    //load data
                    self.getDataLossInfo(self.selectedItem());
                });
            }).fail(error => {
                dialog.alertError(error);
            });
            $("#B2_2").focus();
        }

        createEmployeeModel(data) {
            let listEmployee = [];
            _.each(data, data => {
                listEmployee.push({
                    id: data.id,
                    code: data.code,
                    businessName: data.name,
                    workplaceName: data.workplaceName
                });
            });

            return listEmployee;
        }

    }
}