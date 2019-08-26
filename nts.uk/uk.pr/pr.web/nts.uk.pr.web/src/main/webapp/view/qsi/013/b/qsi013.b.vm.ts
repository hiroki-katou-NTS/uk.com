module nts.uk.pr.view.qsi013.b.viewmodel {
    import getText = nts.uk.resource.getText;
    import model = nts.uk.pr.view.qsi013.share.model;
    import dialog = nts.uk.ui.dialog;

    //class for combo box
    export class ItemModel {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
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

    export enum ReasonsForLossHealthyIns {
        BLANK = 0,
        RETIREMENT = 4,
        DEATH = 5,
        ONLY_HEALTHY_INSURANCE = 7,
        DISABILITY_AUTHORIZATION = 9
    }

    export enum ReasonsForLossPensionIns {
        BLANK = 0,
        RETIREMENT = 4,
        DEATH = 5,
        ONLY_HEALTHY_INSURANCE = 6
    }

    //for interface healthLossInfo
    export interface IHealthLossInfo {
        other: number;
        otherReason: string;
        caInsurance: number;
        numRecoved: number;
        cause: number;
    }

    //for interface healthLossInfo
    export interface IPensionLossInfo {
        other: number;
        otherReason: string;
        caInsuarace: number;
        numRecoved: number;
        cause: number;
    }

    export interface IPensionBasic {
        basicPenNumber: string;
    }

    export interface IMultiWorkInfo {
        isMoreEmp: number;
    }


    //for combo box
    export function getCauseTypeHealthLossInfo(): Array<ItemModel> {
        return [
            new ItemModel(ReasonsForLossHealthyIns.BLANK.toString(), getText('Enum_ReasonsForLossHealthyIns_BLANK')),
            new ItemModel(ReasonsForLossHealthyIns.RETIREMENT.toString(), getText('Enum_ReasonsForLossHealthyIns_RETIREMENT')),
            new ItemModel(ReasonsForLossHealthyIns.DEATH.toString(), getText('Enum_ReasonsForLossHealthyIns_DEATH')),
            new ItemModel(ReasonsForLossHealthyIns.ONLY_HEALTHY_INSURANCE.toString(), getText('Enum_ReasonsForLossHealthyIns_ONLY_HEALTHY_INSURANCE')),
            new ItemModel(ReasonsForLossHealthyIns.DISABILITY_AUTHORIZATION.toString(), getText('Enum_ReasonsForLossHealthyIns_DISABILITY_AUTHORIZATION'))
        ];
    }

    export function getCauseTypePensionLossInfo(): Array<ItemModel> {
        return [
            new ItemModel(ReasonsForLossPensionIns.BLANK.toString(), getText('Enum_ReasonsForLossPensionIns_BLANK')),
            new ItemModel(ReasonsForLossPensionIns.RETIREMENT.toString(), getText('Enum_ReasonsForLossPensionIns_RETIREMENT')),
            new ItemModel(ReasonsForLossPensionIns.DEATH.toString(), getText('Enum_ReasonsForLossPensionIns_DEATH')),
            new ItemModel(ReasonsForLossPensionIns.ONLY_HEALTHY_INSURANCE.toString(), getText('Enum_ReasonsForLossPensionIns_ONLY_PENSION_INSURANCE'))
        ];
    }

    export class ScreenModel {
        screenMode: KnockoutObservable<model.SCREEN_MODE> = ko.observable(null);
        constraint: string = 'LayoutCode';
        inline: KnockoutObservable<boolean>;
        required: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        employeeInputList: KnockoutObservableArray<EmployeeModel>;
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
        itemListHealth: KnockoutObservableArray<ItemModel>;
        itemListPension: KnockoutObservableArray<ItemModel>;
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

            self.systemReference = ko.observable(SystemType.SALARY);
            self.isDisplayOrganizationName = ko.observable(false);
            self.targetBtnText = nts.uk.resource.getText("KCP009_3");

            //data from object lossinfo
            self.selectedItem.subscribe((data) => {
                self.getDataLossInfo(data);
            });
            //self.selectedItem = ko.observable('000000000000000000000000000000000002');

            self.tabindex = 1;
            // Initial listComponentOption
            self.listComponentOption = {
                systemReference: self.systemReference(),
                isDisplayOrganizationName: self.isDisplayOrganizationName(),
                employeeInputList: self.employeeInputList,
                targetBtnText: self.targetBtnText,
                selectedItem: self.selectedItem,
                tabIndex: 0
            };

            self.hOther = ko.observable(true);
            self.pOther = ko.observable(true);
            self.enable = ko.observable(true);

            //get data when load creen
            this.getDataLossInfo(self.selectedItem());

            self.numbereditor = {
                constraint: '',
                option: new nts.uk.ui.option.NumberEditorOption({
                    grouplength: 3,
                    decimallength: 2,
                    placeholder: "Placeholder for number editor",
                    width: "",
                    textalign: "left"
                }),
                //enable: ko.observable(true),
                readonly: ko.observable(false)
            };
            $('#emp-component').ntsLoadListComponent(self.listComponentOption);


            //set data enum into combo box
            self.itemListHealth = ko.observableArray(getCauseTypeHealthLossInfo());
            self.itemListPension = ko.observableArray(getCauseTypePensionLossInfo());

            self.hCause = ko.observable(1);
            self.pCause = ko.observable(1);
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);

            //for text editor
            self.pOtherReason = ko.observable("");
            self.texteditor = {
                constraint: 'ResidenceCode',
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "Placeholder for text editor",
                    width: "100px",
                    textalign: "left"
                })),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };
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

        //convert boolean to int in command
        convertDataToInt(data: boolean) {
            if (data == true) {
                return 1;
            } else {
                return 0;
            }
        }

        checkedDataUpdate(other : boolean, otherReason : string){
            if(other){
                return otherReason;
            } else {
                return '';
            }
        }

        registerLossInfo() {
            var self = this;
            //for register info
            let healthInsLossInfo: any = {
                empId: self.selectedItem(),
                other: this.convertDataToInt(self.hOther()),
                otherReason: this.checkedDataUpdate(self.hOther(),self.hOtherReason() ),
                    //self.hOtherReason(),
                caInsurance: self.hCaInsurance(),
                numRecoved: self.hNumRecoved(),
                cause: self.hCause(),
            };

            let welfPenInsLossIf: any = {
                empId: self.selectedItem(),
                other: this.convertDataToInt(self.pOther()),
                otherReason: this.checkedDataUpdate(self.pOther(),self.pOtherReason() ),
                caInsuarace: self.pCaInsurance(),
                numRecoved: self.pNumRecoved(),
                cause: self.pCause(),
            };
            let multiEmpWorkInfo: any = {
                empId: self.selectedItem(),
                isMoreEmp: this.convertDataToInt(self.isMoreEmp())
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
                    this.getDataLossInfo(self.selectedItem());
                });
            }).fail(error => {
                dialog.alertError(error);
            });
            $("#B2_2").focus();
        }
    }
}