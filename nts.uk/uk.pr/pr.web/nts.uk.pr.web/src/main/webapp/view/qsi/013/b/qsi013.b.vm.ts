module nts.uk.pr.view.qsi013.b.viewmodel {
    import getText = nts.uk.resource.getText;
    import model = nts.uk.pr.view.qsi013.share.model;
    import dialog = nts.uk.ui.dialog;

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
        employeeInputList: KnockoutObservableArray<model.EmployeeModel>;
        systemReference: KnockoutObservable<number>;
        isDisplayOrganizationName: KnockoutObservable<boolean>;
        targetBtnText: string;
        baseDate: KnockoutObservable<Date>;
        listComponentOption: ComponentOption;
        selectedItem: KnockoutObservable<string> = ko.observable(null);
        tabindex: number;

        //checked
        isMoreEmp: KnockoutObservable<boolean> = ko.observable();
        hOther: KnockoutObservable<boolean> = ko.observable();
        pOther: KnockoutObservable<boolean> = ko.observable();

        //number editor
        hCaInsurance: KnockoutObservable<number> = ko.observable();
        hNumRecoved: KnockoutObservable<number> = ko.observable();
        pCaInsurance: KnockoutObservable<number> = ko.observable();
        pNumRecoved: KnockoutObservable<number> = ko.observable();
        basicPenNumber: KnockoutObservable<string> = ko.observable('');
        numbereditor: any;

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
        texteditor: any;
        hOtherReason: KnockoutObservable<string> = ko.observable('');
        pOtherReason: KnockoutObservable<string> = ko.observable('');

        getDataLossInfo(empId: string) {
            let self = this;
            nts.uk.pr.view.qsi013.b.service.getLossInfoById(empId).done(function (data: any) {
                if (data) {
                    self.hCause(data.healthInsLossInfo.cause);
                    self.hNumRecoved(data.healthInsLossInfo.numRecoved);
                    self.hOther(data.healthInsLossInfo.other == 1);
                    self.hCaInsurance(data.healthInsLossInfo.caInsurance);
                    self.hOtherReason(data.healthInsLossInfo.otherReason);

                    self.pCause(data.welfPenInsLossIf.cause);
                    self.pNumRecoved(data.welfPenInsLossIf.numRecoved);
                    self.pOther(data.welfPenInsLossIf.other);
                    self.pCaInsurance(data.welfPenInsLossIf.caInsuarace);
                    self.pOtherReason(data.welfPenInsLossIf.otherReason);

                    self.isMoreEmp(data.multiEmpWorkInfo.isMoreEmp == 1);
                    self.basicPenNumber(data.empBasicPenNumInfor.basicPenNumber);

                    self.screenMode(model.SCREEN_MODE.UPDATE);

                } else {
                    // for new mode
                    //set default value
                    self.hCause(0);
                    self.hNumRecoved();
                    self.hOther();
                    self.hCaInsurance();
                    self.hOtherReason('');

                    self.pCause(0);
                    self.pNumRecoved();
                    self.pOther();
                    self.pCaInsurance();
                    self.pOtherReason('');

                    self.isMoreEmp();
                    self.basicPenNumber('');

                    self.screenMode(model.SCREEN_MODE.NEW);
                }
            }).fail(error => {
                dialog.alertError(error);
            });
        }

        constructor() {
            var self = this;
            self.inline = ko.observable(true);
            self.required = ko.observable(true)
            self.enable = ko.observable(true);
            self.systemReference = ko.observable(model.SystemType.SALARY);
            self.isDisplayOrganizationName = ko.observable(false);
            self.targetBtnText = nts.uk.resource.getText("KCP009_3");
            self.tabindex = 1;
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);

            self.employeeInputList = ko.observableArray([
                {
                    id: '000000000000000000000000000000000001',
                    code: 'A000000000001',
                    businessName: '日通　純一郎1',
                    workplaceName: '名古屋支店',
                    depName: 'Dep Name'
                },
                {
                    id: '000000000000000000000000000000000002',
                    code: 'A000000000004',
                    businessName: '日通　純一郎4',
                    workplaceName: '名古屋支店',
                    depName: 'Dep Name'
                },
                {
                    id: '000000000000000000000000000000000003',
                    code: 'A000000000005',
                    businessName: '日通　純一郎5',
                    workplaceName: '名古屋支店',
                    depName: 'Dep Name'
                }
            ]);

            self.selectedItem.subscribe((data) => {
                self.getDataLossInfo(data);
            });

            self.listComponentOption = {
                systemReference: self.systemReference(),
                isDisplayOrganizationName: self.isDisplayOrganizationName(),
                employeeInputList: self.employeeInputList,
                targetBtnText: self.targetBtnText,
                selectedItem: self.selectedItem,
                tabIndex: 0
            };

            this.getDataLossInfo(self.selectedItem());
            $('#emp-component').ntsLoadListComponent(self.listComponentOption);

            self.itemListHealth = ko.observableArray(model.getCauseTypeHealthLossInfo());
            self.itemListPension = ko.observableArray(model.getCauseTypePensionLossInfo());

            self.hOther = ko.observable(true);
            self.pOther = ko.observable(true);
            self.enable = ko.observable(true);
            self.hCause = ko.observable(1);
            self.pCause = ko.observable(1);
            self.pOtherReason = ko.observable("");
        }

        //method for combo box
        setDefault() {
            var self = this;
            nts.uk.util.value.reset($("#combo-box, #A_SEL_001"), self.defaultValue() !== '' ? self.defaultValue() : undefined);
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
                otherReason : self.hOther() == true ? self.hOtherReason() : '',
                caInsurance: self.hCaInsurance(),
                numRecoved: self.hNumRecoved(),
                cause: self.hCause(),
            };

            let welfPenInsLossIf: any = {
                empId: self.selectedItem(),
                other : self.pOther() == true ? 1 : 0,
                otherReason : self.pOther() == true ? self.pOtherReason() : '',
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

            let lossInfoCommand: any = {
                healthInsLossInfo: healthInsLossInfo,
                welfPenInsLossIf: welfPenInsLossIf,
                multiEmpWorkInfo: multiEmpWorkInfo,
                empBasicPenNumInfor: empBasicPenNumInfor,
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
    }
}