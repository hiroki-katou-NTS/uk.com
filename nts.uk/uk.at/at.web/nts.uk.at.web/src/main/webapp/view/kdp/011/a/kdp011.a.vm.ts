module nts.uk.at.view.kdp011.a {
    import service = nts.uk.at.view.kdp011.a.service;
    import blockUI = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;

    export module viewmodel {

        const DATE_FORMAT_YYYY_MM_DD = "YYYY/MM/DD";

        export class ScreenModel {

            // CCG001
            ccg001ComponentOption: GroupOption;

            datepickerValue: KnockoutObservable<any> = ko.observable({});
            startDateString: KnockoutObservable<string> = ko.observable("");
            endDateString: KnockoutObservable<string> = ko.observable("");

            // KCP005 start
            listComponentOption: any;
            selectedCodeEmployee: KnockoutObservableArray<string> = ko.observableArray([]);
            isShowAlreadySet: KnockoutObservable<boolean> = ko.observable(false);
            alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);
            isDialog: KnockoutObservable<boolean> = ko.observable(true);
            isShowNoSelectRow: KnockoutObservable<boolean> = ko.observable(true);
            isMultiSelect: KnockoutObservable<boolean> = ko.observable(true);
            isShowWorkPlaceName: KnockoutObservable<boolean> = ko.observable(true);
            isShowSelectAllButton: KnockoutObservable<boolean> = ko.observable(false);
            employeeList: KnockoutObservableArray<UnitModel> = ko.observableArray<UnitModel>([]);
            showOptionalColumn: KnockoutObservable<boolean> = ko.observable(false);
            //
            //enableCCG001: KnockoutObservable<boolean> = ko.observable(true);
            // KCP005 end


            lstOutputItemCode: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
            selectedOutputItemCode: KnockoutObservable<string> = ko.observable('');

            checkedCardNOUnregisteStamp: KnockoutObservable<boolean> = ko.observable(false);
            enableCardNOUnregisteStamp: KnockoutObservable<boolean> = ko.observable(true);
            // Process Select 
            itemList: KnockoutObservableArray<any>
            selectedIdProcessSelect: KnockoutObservable<number>;
            enableProcessSelect: KnockoutObservable<boolean> = ko.observable(true);
            constructor() {
                let self = this;
                //CCG 001 
                self.declareCCG001();
                self.listComponentOption = {
                    isShowAlreadySet: self.isShowAlreadySet(),
                    isMultiSelect: self.isMultiSelect(),
                    listType: ListType.EMPLOYEE,
                    employeeInputList: self.employeeList,
                    selectType: SelectType.NO_SELECT,
                    selectedCode: self.selectedCodeEmployee,
                    isDialog: self.isDialog(),
                    isShowNoSelectRow: self.isShowNoSelectRow(),
                    alreadySettingList: self.alreadySettingList,
                    isShowWorkPlaceName: self.isShowWorkPlaceName(),
                    isShowSelectAllButton: self.isShowSelectAllButton(),
                    showOptionalColumn: self.showOptionalColumn(),
                    maxRows: 15
                };

                self.conditionBinding();
                //Process Select
                self.listProcessSelect = ko.observableArray([
                    new ProcessSelect(1, nts.uk.resource.getText('KDP011_14')),
                    new ProcessSelect(2, nts.uk.resource.getText('KDP011_15'))

                ]);
                
                  if (__viewContext.user.role.attendance != null) {
                    self.selectedIdProcessSelect = ko.observable(1);
                     
                }
                else {
                    self.selectedIdProcessSelect = ko.observable(2);
                      self.listProcessSelect()[0].enable =  ko.observable(false);
                    
                }

            }
            if()
            /**
            * start screen
            */
            public startPage(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();

                let self = this,
                    companyId: string = __viewContext.user.companyId,
                    userId: string = __viewContext.user.employeeId;
                $.when(service.initScreen(), service.restoreCharacteristic(companyId, userId))
                    .done((dataStartPage, dataCharacteristic) => {
                        // get data from server
                        self.startDateString(dataStartPage.startDate);
                        self.endDateString(dataStartPage.endDate);
                        self.ccg001ComponentOption.periodStartDate = moment.utc(dataStartPage.startDate, DATE_FORMAT_YYYY_MM_DD).toISOString();
                        self.ccg001ComponentOption.periodEndDate = moment.utc(dataStartPage.endDate, DATE_FORMAT_YYYY_MM_DD).toISOString();

                        let arrOutputItemCodeTmp: ItemModel[] = [];
                        _.forEach(dataStartPage.lstStampingOutputItemSetDto, function(value) {
                            arrOutputItemCodeTmp.push(new ItemModel(value.stampOutputSetCode, value.stampOutputSetName));
                        });
                        self.lstOutputItemCode(arrOutputItemCodeTmp);

                        // get data from characteris
                        if (!_.isUndefined(dataCharacteristic)) {
                            self.checkedCardNOUnregisteStamp(dataCharacteristic.cardNumNotRegister);
                            self.selectedOutputItemCode(dataCharacteristic.outputSetCode);
                        }

                        // enable button when exist Authority of employment form                                        
                        self.enableCardNOUnregisteStamp(dataStartPage.existAuthEmpl);

                        dfd.resolve();
                    })
                return dfd.promise();
            }

            /**
            * binding component CCG001 and KCP005
            */
            public executeComponent(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                let self = this;
                blockUI.grayout();
                $.when($('#com-ccg001').ntsGroupComponent(self.ccg001ComponentOption),
                    $('#employee-list').ntsListComponent(self.listComponentOption)).done(() => {
                        self.changeHeightKCP005();
                        dfd.resolve();
                    });
                return dfd.promise();
            }

            private declareCCG001(): void {
                let self = this;
                // Set component option
                self.ccg001ComponentOption = {
                    /** Common properties */
                    systemType: 2,
                    showEmployeeSelection: false,
                    showQuickSearchTab: true,
                    showAdvancedSearchTab: true,
                    showBaseDate: false,
                    showClosure: false,
                    showAllClosure: false,
                    showPeriod: true,
                    periodFormatYM: false,

                    /** Required parameter */
                    baseDate: moment().toISOString(),
                    periodStartDate: moment().toISOString(),
                    periodEndDate: moment().toISOString(),
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

                    /**
                    * Self-defined function: Return data from CCG001
                    * @param: data: the data return from CCG001
                    */
                    returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                        let arrEmployeelst: UnitModel[] = [];
                        _.forEach(data.listEmployee, function(value) {
                            arrEmployeelst.push({ code: value.employeeCode, name: value.employeeName, affiliationName: value.affiliationName, id: value.employeeId });
                        });
                        self.employeeList(arrEmployeelst);
                        self.selectedCodeEmployee(_.map(arrEmployeelst, "code"));
                    }
                }
            }
    
            /**
            * Export excel
            */
            private exportExcel(): void {

                let self = this,
                    companyId: string = __viewContext.user.companyId,
                    userId: string = __viewContext.user.employeeId,
                    data: any = {
                        };

                //                if (!self.validateExportExcel()) {
                //                    return;
                //                }
                blockUI.grayout();
                let outputConditionEmbossing: OutputConditionEmbossing = new OutputConditionEmbossing(userId, self.selectedOutputItemCode(), self.checkedCardNOUnregisteStamp());
                service.saveCharacteristic(companyId, userId, outputConditionEmbossing);

                data.startDate = self.datepickerValue().startDate;
                data.endDate = self.datepickerValue().endDate;
                data.lstEmployee = self.convertDataEmployee(self.employeeList(), self.selectedCodeEmployee());
                data.selectedIdProcessSelect = self.selectedIdProcessSelect();
                // data.outputSetCode = self.selectedOutputItemCode();
                if(data.lstEmployee.length == 0){
                     dialog.alertError({ messageId: "Msg_1204" });
                     blockUI.clear();
                     return ;
                    }
                if(self.selectedCodeEmployee ==1 )
                data.cardNumNotRegister = true;
                else{
                    data.cardNumNotRegister = false; }
                service.exportExcel(data).fail((data) => {
                    console.log(data);
                }).then(() => {
                    blockUI.clear();
                })
            }

            /**
            * validate when export
            */
            private validateExportExcel(): boolean {
                let self = this;
                if (!self.checkedCardNOUnregisteStamp()) {
                    if (_.isEmpty(self.selectedCodeEmployee())) {
                        dialog.alertError({ messageId: "Msg_1204" });
                        return false;
                    }
                }

                if (_.isEmpty(self.selectedOutputItemCode())) {
                    dialog.alertError({ messageId: "Msg_1617" });
                    return false;
                }

                // when don't have error
                return true;
            }

            /**
            * Subscribe Event
            */
            private conditionBinding(): void {
                let self = this;

                self.startDateString.subscribe(function(value) {
                    self.datepickerValue().startDate = value;
                    self.datepickerValue.valueHasMutated();
                });

                self.endDateString.subscribe(function(value) {
                    self.datepickerValue().endDate = value;
                    self.datepickerValue.valueHasMutated();
                });
                

                self.checkedCardNOUnregisteStamp.subscribe((newValue) => {
                    //                    if (newValue) {
                    //                        $('#ccg001-btn-search-drawer').addClass("disable-cursor");
                    //                    } else {
                    //                        $('#ccg001-btn-search-drawer').removeClass("disable-cursor");
                    //                    }
                })
            }

            /**
            * convert data to data object matching java
            */
            private convertDataEmployee(data: UnitModel[], employeeCd: string[]): EmployeeInfor[] {
                let mapCdId: { [key: string]: string; } = {};
                let mapCdName: { [key: string]: string; } = {};

                let arrEmployee: EmployeeInfor[] = [];
                _.forEach(data, function(value) {
                    mapCdId[value.code] = value.id;
                    mapCdName[value.code] = value.name;
                });

                let emloyeeID = [];
                _.forEach(employeeCd, function(value) {
                    emloyeeID.push(mapCdId[value]);
                });

                return emloyeeID;
            }

            /**
            * set height table in KCP005 after initialize
            */
            private changeHeightKCP005(): void {
                let _document: any = document,
                    isIE = /*@cc_on!@*/false || !!_document.documentMode;
                if (isIE) {
                    let heightKCP = $('div[id$=displayContainer]').height();
                    $('div[id$=displayContainer]').height(heightKCP + 3);
                    $('div[id$=scrollContainer]').height(heightKCP + 3);
                }
            }
        }

        export interface EmployeeInfor {
            employeeID: string;
            employeeCD: string;
            employeeName?: string;
        }

        export interface GroupOption {
            /** Common properties */
            showEmployeeSelection?: boolean; // 検索タイプ
            systemType: number; // システム区分
            showQuickSearchTab?: boolean; // クイック検索
            showAdvancedSearchTab?: boolean; // 詳細検索
            showBaseDate?: boolean; // 基準日利用
            showClosure?: boolean; // 就業締め日利用
            showAllClosure?: boolean; // 全締め表示
            showPeriod?: boolean; // 対象期間利用
            periodFormatYM?: boolean; // 対象期間精度
            isInDialog?: boolean;

            /** Required parameter */
            baseDate?: string; // 基準日
            periodStartDate?: string; // 対象期間開始日
            periodEndDate?: string; // 対象期間終了日
            inService: boolean; // 在職区分
            leaveOfAbsence: boolean; // 休職区分
            closed: boolean; // 休業区分
            retirement: boolean; // 退職区分

            /** Quick search tab options */
            showAllReferableEmployee?: boolean; // 参照可能な社員すべて
            showOnlyMe?: boolean; // 自分だけ
            showSameWorkplace?: boolean; // 同じ職場の社員
            showSameWorkplaceAndChild?: boolean; // 同じ職場とその配下の社員

            /** Advanced search properties */
            showEmployment?: boolean; // 雇用条件
            showWorkplace?: boolean; // 職場条件
            showClassification?: boolean; // 分類条件
            showJobTitle?: boolean; // 職位条件
            showWorktype?: boolean; // 勤種条件
            isMutipleCheck?: boolean; // 選択モード
            isTab2Lazy?: boolean;

            /** Data returned */
            returnDataFromCcg001: (data: Ccg001ReturnedData) => void;
        }

        export interface EmployeeSearchDto {
            employeeId: string;
            employeeCode: string;
            employeeName: string;
            workplaceId: string;
            workplaceName: string;
        }

        export interface Ccg001ReturnedData {
            baseDate: string; // 基準日
            closureId?: number; // 締めID
            periodStart: string; // 対象期間（開始)
            periodEnd: string; // 対象期間（終了）
            listEmployee: Array<EmployeeSearchDto>; // 検索結果
        }


        export class ListType {
            static EMPLOYMENT = 1;
            static Classification = 2;
            static JOB_TITLE = 3;
            static EMPLOYEE = 4;
        }

        export interface UnitModel {
            code: string;
            name?: string;
            affiliationName?: string;
            id?: string;
            isAlreadySetting?: boolean;
        }

        export class SelectType {
            static SELECT_BY_SELECTED_CODE = 1;
            static SELECT_ALL = 2;
            static SELECT_FIRST_ITEM = 3;
            static NO_SELECT = 4;
        }

        export interface UnitAlreadySettingModel {
            code: string;
            isAlreadySetting: boolean;
        }

        export class ItemModel {
            code: string;
            name: string;

            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

        export class OutputConditionEmbossing {
            userID: string;
            outputSetCode: string;
            cardNumNotRegister: boolean;

            constructor(userID: string, outputSetCode: string, cardNumNotRegister: boolean) {
                this.userID = userID;
                this.outputSetCode = outputSetCode;
                this.cardNumNotRegister = cardNumNotRegister;
            }
        }

        class OutputConditionOfEmbossingDto {
            startDate: string;
            endDate: string;
            lstStampingOutputItemSetDto: StampingOutputItemSetDto[];

            constructor(startDate: string, endDate: string, lstStampingOutputItemSetDto: StampingOutputItemSetDto[]) {
                this.startDate = startDate;
                this.endDate = endDate;
                this.lstStampingOutputItemSetDto = lstStampingOutputItemSetDto;
            }
        }

        class StampingOutputItemSetDto {
            stampOutputSetName: string;
            stampOutputSetCode: string;

            constructor(stampOutputSetName: string, stampOutputSetCode: string) {
                this.stampOutputSetName = stampOutputSetName;
                this.stampOutputSetCode = stampOutputSetCode;
            }
        }
        class ProcessSelect {
            idProcessSelect: number;
            nameProcessSelect: string;   
            constructor(idProcessSelect, nameProcessSelect) {
                var self = this;
                self.idProcessSelect = idProcessSelect;
                self.nameProcessSelect = nameProcessSelect;    
            }
        }
    }
}