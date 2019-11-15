module nts.uk.pr.view.qsi014.b.viewmodel {

    import model = nts.uk.pr.view.qsi014.share.model;
    import block = nts.uk.ui.block;
    import getShared = nts.uk.ui.windows.getShared;
    import errors = nts.uk.ui.errors;
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
        selectedItem: KnockoutObservable<string>= ko.observable('');
        tabindex: number;
        //
        empAddChangeInfoDto: KnockoutObservable<EmpAddChangeInfoDto> = ko.observable(new EmpAddChangeInfoDto(<IEmpAddChangeInfoDto>{
            sid: "",
            shortResidentAtr: 0,
            livingAbroadAtr: 0,
            residenceOtherResidentAtr: 0,
            otherAtr: 0,
            otherReason: null,
            spouseShortResidentAtr: 0,
            spouseLivingAbroadAtr: 0,
            spouseResidenceOtherResidentAtr: 0,
            spouseOtherAtr: 0,
            spouseOtherReason: null,
            basicPenNumber: null
        }));
        employeeInputList: KnockoutObservableArray<model.EmployeeModel>= ko.observableArray([]);


        constructor() {
            let self = this;
            $('#emp-component').focus();
            self.selectedItem.subscribe(e => {
                $('#emp-component').focus();
                self.initScreen(e);
            });
            self.empAddChangeInfoDto().otherAtr.subscribe(e =>{
                self.empAddChangeInfoDto().otherReason(null);
                if(!self.validate()){
                    errors.clearAll();
                }

            });
            self.empAddChangeInfoDto().spouseOtherAtr.subscribe(e =>{
                self.empAddChangeInfoDto().spouseOtherReason(null);
                if(!self.validate()){
                    errors.clearAll();
                }

            });
            let params = getShared('QSI014_PARAMS_B');
            self.employeeInputList(self.createEmployeeModel(params.employeeList));
            self.loadKCP009(self.employeeInputList());
            self.selectedItem(self.employeeInputList()[0].id);
            //select employee
            $('#emp-component').focus();


        }
        validate(){
            errors.clearAll();
            $("#B4_8").trigger("validate");
            $("#B3_10").trigger("validate");
            $("#B3_4").trigger("validate");
            return errors.hasError();
        }
        initScreen(empId: string): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.invisible();
            //load start screen
            service.start(empId).done(function(data: IEmpAddChangeInfoDto) {
                    block.clear();
                    self.empAddChangeInfoDto().sid(empId);
                    self.empAddChangeInfoDto().shortResidentAtr(data.shortResidentAtr);
                    self.empAddChangeInfoDto().livingAbroadAtr(data.livingAbroadAtr);
                    self.empAddChangeInfoDto().residenceOtherResidentAtr(data.residenceOtherResidentAtr);
                    self.empAddChangeInfoDto().otherAtr(data.otherAtr == 1);
                    self.empAddChangeInfoDto().otherReason(data.otherReason);
                    self.empAddChangeInfoDto().spouseShortResidentAtr(data.spouseShortResidentAtr);
                    self.empAddChangeInfoDto().spouseLivingAbroadAtr(data.spouseLivingAbroadAtr);
                    self.empAddChangeInfoDto().spouseResidenceOtherResidentAtr(data.spouseResidenceOtherResidentAtr);
                    self.empAddChangeInfoDto().spouseOtherAtr(data.spouseOtherAtr == 1) ;
                    self.empAddChangeInfoDto().spouseOtherReason(data.spouseOtherReason);
                    self.empAddChangeInfoDto().basicPenNumber(data.basicPenNumber);
            }).fail(function (result) {
                dialog.alertError(result.errorMessage);
                dfd.reject();
            }).always(()=>{
                if(!self.validate()){
                    errors.clearAll();
                };
                $('#emp-component').focus();
            });
            return dfd.promise();
        }

        register() {
            block.invisible();
            let dfd = $.Deferred();
            let self = this;
            //load start screen
            let data = {
                sid: self.empAddChangeInfoDto().sid(),
                shortResidentAtr: self.empAddChangeInfoDto().shortResidentAtr() ? 1 : 0,
                livingAbroadAtr: self.empAddChangeInfoDto().livingAbroadAtr()? 1 : 0,
                residenceOtherResidentAtr: self.empAddChangeInfoDto().residenceOtherResidentAtr()? 1 : 0,
                otherAtr: self.empAddChangeInfoDto().otherAtr() ? 1 : 0,
                otherReason: !self.empAddChangeInfoDto().otherAtr() || self.empAddChangeInfoDto().otherReason() == "" ? null : self.empAddChangeInfoDto().otherReason(),
                spouseShortResidentAtr: self.empAddChangeInfoDto().spouseShortResidentAtr() ? 1 : 0,
                spouseLivingAbroadAtr: self.empAddChangeInfoDto().spouseLivingAbroadAtr() ? 1 : 0,
                spouseResidenceOtherResidentAtr: self.empAddChangeInfoDto().spouseResidenceOtherResidentAtr() ? 1 : 0,
                spouseOtherAtr: self.empAddChangeInfoDto().spouseOtherAtr() ? 1 : 0,
                spouseOtherReason:  !self.empAddChangeInfoDto().spouseOtherAtr() || self.empAddChangeInfoDto().spouseOtherReason() == "" ? null : self.empAddChangeInfoDto().spouseOtherReason(),
                basicPenNumber: self.empAddChangeInfoDto().basicPenNumber() == "" ? null :self.empAddChangeInfoDto().basicPenNumber()
            };
            service.register(data).done(e => {
                block.clear();
                dialog.info({ messageId: "Msg_15" }).then(function() {
                    $('#emp-component').focus();
                    close();
                });
            }).fail(function (result) {
                dialog.alertError(result.errorMessage);
                dfd.reject();
            });

            return dfd.promise();
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
            self.tabindex = 4;
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
                    id: data.id,
                    code: data.code,
                    businessName: data.name,
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
        otherAtr: KnockoutObservable<boolean>;

        /**
         * その他理由
         */
        otherReason: KnockoutObservable<string>;

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
        spouseOtherAtr: KnockoutObservable<boolean>;

        /**
         * その他理由
         */
        spouseOtherReason: KnockoutObservable<string>;

        basicPenNumber: KnockoutObservable<string>;

        constructor(params: IEmpAddChangeInfoDto) {
            this.sid = ko.observable(params.sid)
            this.basicPenNumber = ko.observable(params.basicPenNumber);
            this.livingAbroadAtr = ko.observable(params.livingAbroadAtr);
            this.shortResidentAtr = ko.observable(params.shortResidentAtr);
            this.residenceOtherResidentAtr = ko.observable(params.residenceOtherResidentAtr);
            this.otherAtr = ko.observable(params.otherAtr == 1);
            this.otherReason = ko.observable(params.otherReason);
            this.spouseShortResidentAtr = ko.observable(params.spouseShortResidentAtr);
            this.spouseLivingAbroadAtr = ko.observable(params.spouseLivingAbroadAtr);
            this.spouseResidenceOtherResidentAtr = ko.observable(params.spouseResidenceOtherResidentAtr);
            this.spouseOtherAtr = ko.observable(params.spouseOtherAtr == 1);
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
        otherReason: string;

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