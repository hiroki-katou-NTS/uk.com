module nts.uk.com.view.cas013.b.viewmodel {

    export class ScreenModel {
        dataSource: any;
        columns: Array<any>;
        //search
        searchValue: KnockoutObservable<string>;
        //user
        selectUserID: KnockoutObservable<string>;
        userName: KnockoutObservable<string>;
        roleTypeParam: number;
        companyId: KnockoutObservable<string>;
        companyCode: KnockoutObservable<string>;
        companyName: KnockoutObservable<string>;
        ListEmployyeId: Array<any>;
        ListEmployye: KnockoutObservableArray<any>;
        empListView: KnockoutObservableArray<UnitModel>

        special: KnockoutObservable<boolean>;
        multi: KnockoutObservable<boolean>;

        //B1_2
        itemList: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        selectedCode: KnockoutObservable<string>;
        isEnable: KnockoutObservable<boolean>;
        isEditable: KnockoutObservable<boolean>;
        isRequired: KnockoutObservable<boolean>;
        selectFirstIfNull: KnockoutObservable<boolean>;
        selectedType: KnockoutObservable<string>;

        // start declare KCP005
        listComponentOption: any;
        selectedCodeKCP: KnockoutObservable<string>;
        multiSelectedCode: KnockoutObservableArray<string>;
        isShowAlreadySet: KnockoutObservable<boolean>;
        alreadySettingPersonal: KnockoutObservableArray<UnitAlreadySettingModel>;
        isDialog: KnockoutObservable<boolean>;
        isShowNoSelectRow: KnockoutObservable<boolean>;
        isMultiSelect: KnockoutObservable<boolean>;
        isShowWorkPlaceName: KnockoutObservable<boolean>;
        isShowSelectAllButton: KnockoutObservable<boolean>;
        disableSelection : KnockoutObservable<boolean>;

        employeeList: KnockoutObservableArray<UnitModel>;
        baseDate: KnockoutObservable<Date>;
        // end KCP005

        constructor() {
            var self = this;
            self.roleTypeParam = nts.uk.ui.windows.getShared("roleType");
            self.companyId = nts.uk.ui.windows.getShared("companyId");
            self.ListEmployyeId = nts.uk.ui.windows.getShared("ListEmployyeId");
            self.companyCode = ko.observable('');
            self.companyName = ko.observable('');
            self.special = ko.observable(true);
            self.multi = ko.observable(true);
            self.ListEmployye = ko.observableArray([]);
            self.empListView = ko.observableArray([]);
            self.selectedType = ko.observable('');

            self.searchValue = ko.observable('');
            self.dataSource = ko.observableArray([]);
            self.columns = [
                { headerText: nts.uk.resource.getText(""), key: 'userID', hidden: true },
                { headerText: nts.uk.resource.getText("CAS013_29"), key: 'loginID', width: 130 },
                { headerText: nts.uk.resource.getText("CAS013_30"), key: 'userName', width: 200 }
            ];
            self.selectUserID = ko.observable('');

            //B1_2
            self.itemList = ko.observableArray([

            ]);
            self.selectedCode = ko.observable('1');
            self.isEnable = ko.observable(true);
            self.isEditable = ko.observable(true);
            self.isRequired = ko.observable(true);
            self.selectFirstIfNull = ko.observable(true);

            // KCP005
            self.baseDate = ko.observable(new Date());
            self.selectedCodeKCP = ko.observable('');
            self.multiSelectedCode = ko.observableArray([]);
            self.isShowAlreadySet = ko.observable(false);
            self.alreadySettingPersonal = ko.observableArray([]);
            self.isDialog = ko.observable(false);
            self.isShowNoSelectRow = ko.observable(false);
            self.isMultiSelect = ko.observable(false);
            self.isShowWorkPlaceName = ko.observable(false);
            self.isShowSelectAllButton = ko.observable(false);
            self.disableSelection = ko.observable(false);
            self.employeeList = ko.observableArray<UnitModel>([]);


            // KCP005
            self.listComponentOption = {
                isShowAlreadySet: false,
                isMultiSelect: false,
                listType: ListType.EMPLOYEE,
                employeeInputList: self.employeeList,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: self.multiSelectedCode,
                isDialog: false,
                isShowWorkPlaceName: false,
                alreadySettingList: self.alreadySettingPersonal,
                isShowSelectAllButton: false,
                maxWidth: 310,
                maxRows: 15
            };
            $('#kcp005').ntsListComponent(self.listComponentOption)

        }

        startPage(): JQueryPromise<any> {
            let self = this;
            var dfd = $.Deferred();
            var pid = nts.uk.ui.windows.getShared("companyId");
            var eid = nts.uk.ui.windows.getShared("ListEmployyeId");
            var sid  = [];
            for (let i =0; i<eid.length;i++){
                let item = eid[i].employyeId;
                sid.push(item);
            }
            if (pid == "") {
                this.snotCompany();
            } else {
                this.sCompany(pid);
            }

            service.getWorkPlacePub(sid).done(function(data) {
                if(data) {
                }
                dfd.resolve();
            })
            service.getSyJobTitlePub(sid).done(function(data) {
                if(data) {
                }
                dfd.resolve();
            })
            return dfd.promise();
        }

        private snotCompany(): void {
            var self = this;
            service.notComapany().done(function(data) {
                var items = [];
                if(data) {
                    items.push(data.companyCode, data.companyName);
                    self.companyCode(data.companyCode)
                    self.companyName(data.companyName)
                    self.itemList = ko.observableArray([
                        new ItemModel(data.companyCode, data.companyName, data.companyId),
                    ]);
                }
                self.selectedType('');
            })
        }
        private sCompany(pid: string): void {
            var self = this;
            var eid = nts.uk.ui.windows.getShared("ListEmployyeId");
            var sid  = [];
            for (let i =0; i<eid.length;i++){
                let item = eid[i].employyeId;
                sid.push(item);
            }
            service.searchCompanyInfo(pid).done(function(data) {
                var items = [];
                if(data) {
                    items.push(data.companyCode, data.companyName);
                    self.companyCode(data.companyCode)
                    self.companyName(data.companyName)
                    self.itemList = ko.observableArray([
                        new ItemModel(data.companyCode, data.companyName, data.companyId),
                    ]);
                }
                self.selectedType(data.companyCode)

            })
            service.searchEmployyeList(sid).done(function(data) {
                var employeeSearchs: UnitModel[] = []; //KCP005
                if(data) {
                    for (let i =0; i<data.length;i++) {
                        var employee: UnitModel = {
                            code: data[i].employeeCode,
                            name: data[i].businessName
                        };
                        employeeSearchs.push(employee);
                    }
                    self.multiSelectedCode.push(employeeSearchs[0].code);
                    self.employeeList(employeeSearchs);
                } else {
                    nts.uk.request.jump("/view/ccg/008/a/index.xhtml");
                }
            })
        }

        private searchEmp(): void {
            var self = this;
            var eid = nts.uk.ui.windows.getShared("ListEmployyeId");
            var sid  = [];
            for (let i =0; i<eid.length;i++){
                let item = eid[i].employyeId;
                sid.push(item);
            }
            service.searchEmployyeList(sid).done(function(data) {
                var employeeSearchs: UnitModel[] = []; //KCP005
                if(data) {
                    for (let i =0; i<data.length;i++) {
                        var employee: UnitModel = {
                            code: data[i].employeeCode,
                            name: data[i].businessName
                        };
                        employeeSearchs.push(employee);
                    }
                    self.multiSelectedCode.push(employee.code);
                    self.employeeList(employeeSearchs);
                } else {
                    nts.uk.request.jump("/view/ccg/008/a/index.xhtml");
                }
            })
        }

        search() {
            let self = this;
            if (nts.uk.text.isNullOrEmpty(self.searchValue())) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_438", messageParams: [nts.uk.resource.getText("CAS013_33")] });
                return;
            }
            var key = self.searchValue().trim();
            if(key.length>3000){
                return;
            }
            var Special = self.special();
            var Multi = self.multi();
            var roleType =  self.roleTypeParam;
            nts.uk.ui.block.invisible();
            service.searchUser(key, Special, Multi, roleType).done(function(data) {
                var items = [];
                items = _.sortBy(data, ["loginID"]);
                self.dataSource(items);
            }).always(() => {
                nts.uk.ui.block.clear();
            });

        }

        enterPress() {
            this.search();
        }

        decision() {
            var self = this;
            // Share data B1_2 B1_3 B1_4 (CompanyId, CompanyCode, CompanyName)
            var setShareCompanyID: any = _.find(self.itemList(), (item: any) => {
                let dataSetShareC = {
                    cid: item.id,
                    ccode: item.code,
                    cname: item.name
                }
                nts.uk.ui.windows.setShared("CompanyInfo", dataSetShareC);
            });
            // Share data B3 (EmployyeCode, EmployyeName)
            var setShareEmployye: any = _.find(self.employeeList(), (item: any) => {
                let dataSetShareE = {
                    ecode: item.code,
                    ename: item.name
                }
                nts.uk.ui.windows.setShared("EmployyeList", dataSetShareE);
            });
            self.cancel_Dialog();
        }

        cancel_Dialog(): any {
            nts.uk.ui.windows.close();
        }

    }
    class ItemModel {
        code: string;
        name: string;
        id?: string
        constructor(code: string, name: string, id: string) {
            this.code = code;
            this.name = name;
            this.id = id;
        }
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
}