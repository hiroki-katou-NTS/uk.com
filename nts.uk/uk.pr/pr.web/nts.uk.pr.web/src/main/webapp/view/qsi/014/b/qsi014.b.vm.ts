module nts.uk.pr.view.qsi014.b.viewmodel {
    import block = nts.uk.ui.block;
    import getShared = nts.uk.ui.windows.getShared;
    import getText = nts.uk.resource.getText;
    var dialog = nts.uk.ui.dialog;

    export class ScreenModel {
        employeeInputList: KnockoutObservableArray<EmployeeModel>;
        //kcp009
        systemReference: KnockoutObservable<number>;
        isDisplayOrganizationName: KnockoutObservable<boolean>;
        targetBtnText: string;
        baseDate: KnockoutObservable<Date>;
        listComponentOption: ComponentOption;
        selectedItem: KnockoutObservable<string>;
        tabindex: number;


        //
        empAddChangeInfoDto: KnockoutObservable<EmpAddChangeInfoDto> = ko.observable(new EmpAddChangeInfoDto({

            sid: "",
            shortResidentAtr: 0,
            livingAbroadAtr: 0,
            residenceOtherResidentAtr: 0,
            otherAtr: 0,
            otherReason: 0,
            spouseShortResidentAtr: 0,
            spouseLivingAbroadAtr: 0,
            spouseResidenceOtherResidentAtr: 0,
            spouseOtherAtr: 0,
            spouseOtherReason: "",
            basicPenNumber: "",
            isUpdateEmpAddChangeInfo: false,
            isUpdateEmpBasicPenNumInfor: false
        }));


        constructor() {
            let self = this;
            let periodCommand: any;
            let params = getShared('QSI001_PARAMS_TO_SCREEN_B');
            self.loadKCP009(self.createEmployeeModel(params.listEmpId));
            self.initScreen(params.listEmpId[0].employeeId);
        }

        initScreen(empId: string): JQueryPromise<any> {
            let dfd = $.Deferred();
            block.invisible();
            let self = this;
            //load start screen
            service.start(empId).done(function(data: IEmpAddChangeInfoDto) {
                    self.empAddChangeInfoDto(data);
                    // self.empAddChangeInfoDto().shortResidentAtr =  data.shortResidentAtr;
                    // self.empAddChangeInfoDto().livingAbroadAtr = data.livingAbroadAtr;
                    // self.empAddChangeInfoDto().residenceOtherResidentAtr= data.residenceOtherResidentAtr;
                    // self.empAddChangeInfoDto().otherAtr = data.otherAtr;
                    // self.empAddChangeInfoDto().otherReason = data.otherReason;
                    // self.empAddChangeInfoDto().spouseShortResidentAtr = data.spouseShortResidentAtr;
                    // self.empAddChangeInfoDto().spouseLivingAbroadAtr = data.spouseLivingAbroadAtr;
                    // self.empAddChangeInfoDto().spouseResidenceOtherResidentAtr = data.spouseResidenceOtherResidentAtr;
                    // self.empAddChangeInfoDto().spouseOtherAtr = data.spouseOtherAtr;
                    // self.empAddChangeInfoDto().spouseOtherReason = data.spouseOtherReason;
                    // self.empAddChangeInfoDto().basicPenNumber = data.basicPenNumber;
                    // self.empAddChangeInfoDto().isUpdateEmpAddChangeInfo = data.isUpdateEmpAddChangeInfo;
                    // self.empAddChangeInfoDto(). isUpdateEmpBasicPenNumInfor = data.isUpdateEmpBasicPenNumInfor;
            }).fail(function (result) {
                dialog.alertError(result.errorMessage);
                dfd.reject();
            });
            block.clear();
            return dfd.promise();
        }

        register() {
            block.invisible();
            let dfd = $.Deferred();
            let self = this;
            //load start screen
            let data = {
                sid: self.empAddChangeInfoDto().sid,
                shortResidentAtr: self.empAddChangeInfoDto().shortResidentAtr,
                livingAbroadAtr: self.empAddChangeInfoDto().livingAbroadAtr,
                residenceOtherResidentAtr: self.empAddChangeInfoDto().residenceOtherResidentAtr,
                otherAtr: self.empAddChangeInfoDto().otherAtr,
                otherReason: self.empAddChangeInfoDto().otherReason,
                spouseShortResidentAtr: self.empAddChangeInfoDto().spouseShortResidentAtr,
                spouseLivingAbroadAtr: self.empAddChangeInfoDto().spouseLivingAbroadAtr,
                spouseResidenceOtherResidentAtr: self.empAddChangeInfoDto().spouseResidenceOtherResidentAtr,
                spouseOtherAtr: self.empAddChangeInfoDto().spouseOtherAtr,
                spouseOtherReason: self.empAddChangeInfoDto().spouseOtherReason,
                basicPenNumber: self.empAddChangeInfoDto().basicPenNumber,
                isUpdateEmpAddChangeInfo: self.empAddChangeInfoDto().isUpdateEmpAddChangeInfo,
                isUpdateEmpBasicPenNumInfor: self.empAddChangeInfoDto().isUpdateEmpBasicPenNumInfor
            };
            service.registerLossInfo(data).done(e => {

            }).fail(function (result) {
                dialog.alertError(result.errorMessage);
                dfd.reject();
            });
            block.clear();
            return dfd.promise();
        }

        add() {
        }

        cancel() {
            nts.uk.ui.windows.close();
        }

        loadKCP009(data) {
            let self = this;
            self.employeeInputList = ko.observableArray(data);
            self.systemReference = ko.observable(SystemType.EMPLOYMENT);
            self.isDisplayOrganizationName = ko.observable(false);
            self.targetBtnText = nts.uk.resource.getText("KCP009_3");
            self.selectedItem = ko.observable(null);
            self.tabindex = 3;
            // Initial listComponentOption
            self.listComponentOption = <ComponentOption>{
                systemReference: self.systemReference(),
                isDisplayOrganizationName: self.isDisplayOrganizationName(),
                employeeInputList: self.employeeInputList,
                targetBtnText: self.targetBtnText,
                selectedItem: self.selectedItem,
                tabIndex: self.tabindex
            };
            $('#emp-component').ntsLoadListComponent(self.listComponentOption);
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

    export class EmpAddChangeInfoDto {
        /**
         * SID
         */
        sid: KnockoutObservable<string>;

        /**
         * 短期在留者
         */
        shortResidentAtr: KnockoutObservable<number>;

        /**
         * 海外居住者
         */
        livingAbroadAtr: KnockoutObservable<number>;

        /**
         * 住民票住所以外居所
         */
        residenceOtherResidentAtr: KnockoutObservable<number>;

        /**
         * その他
         */
        otherAtr: KnockoutObservable<number>;

        /**
         * その他理由
         */
        otherReason: KnockoutObservable<number>;

        /**
         * 短期在留者
         */
        spouseShortResidentAtr: KnockoutObservable<number>;

        /**
         * 海外居住者
         */
        spouseLivingAbroadAtr: KnockoutObservable<number>;

        /**
         * 住民票住所以外居所
         */
        spouseResidenceOtherResidentAtr: KnockoutObservable<number>;

        /**
         * その他
         */
        spouseOtherAtr: KnockoutObservable<number>;

        /**
         * その他理由
         */
        spouseOtherReason: KnockoutObservable<string>;

        basicPenNumber: KnockoutObservable<string>;

        isUpdateEmpAddChangeInfo: KnockoutObservable<boolean>;

        isUpdateEmpBasicPenNumInfor: KnockoutObservable<boolean>;

        constructor(params: IEmpAddChangeInfoDto) {

            this.basicPenNumber = ko.observable(params.basicPenNumber);
            this.isUpdateEmpAddChangeInfo = ko.observable(params.isUpdateEmpAddChangeInfo);
            this.isUpdateEmpBasicPenNumInfor = ko.observable(params.isUpdateEmpBasicPenNumInfor);
            this.livingAbroadAtr = ko.observable(params.livingAbroadAtr);
            this.shortResidentAtr = ko.observable(params.shortResidentAtr);
            this.residenceOtherResidentAtr = ko.observable(params.residenceOtherResidentAtr);
            this.otherAtr = ko.observable(params.otherAtr);
            this.otherReason = ko.observable(params.otherReason);
            this.spouseShortResidentAtr = ko.observable(params.spouseShortResidentAtr);
            this.spouseLivingAbroadAtr = ko.observable(params.spouseLivingAbroadAtr);
            this.spouseResidenceOtherResidentAtr = ko.observable(params.spouseResidenceOtherResidentAtr);
            this.spouseOtherAtr = ko.observable(params.spouseOtherAtr);
            this.spouseOtherReason = ko.observable(params.spouseOtherReason);

        }
    }

    interface IEmpAddChangeInfoDto {
        /**
         * SID
         */
        sid: string;

        /**
         * 短期在留者
         */
        shortResidentAtr: number;

        /**
         * 海外居住者
         */
        livingAbroadAtr: number;

        /**
         * 住民票住所以外居所
         */
        residenceOtherResidentAtr: number;

        /**
         * その他
         */
        otherAtr: number;

        /**
         * その他理由
         */
        otherReason: number;

        /**
         * 短期在留者
         */
        spouseShortResidentAtr: number;

        /**
         * 海外居住者
         */
        spouseLivingAbroadAtr: number;

        /**
         * 住民票住所以外居所
         */
        spouseResidenceOtherResidentAtr: number;

        /**
         * その他
         */
        spouseOtherAtr: number;

        /**
         * その他理由
         */
        spouseOtherReason: string;

        basicPenNumber: string;

        isUpdateEmpAddChangeInfo: boolean;
        isUpdateEmpBasicPenNumInfor :boolean;
    }


}