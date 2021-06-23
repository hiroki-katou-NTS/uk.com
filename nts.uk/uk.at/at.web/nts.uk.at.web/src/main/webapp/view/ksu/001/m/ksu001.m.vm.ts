module nts.uk.at.view.ksu001.m.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import windows = nts.uk.ui.windows;
    import errors = nts.uk.ui.errors;
    import resource = nts.uk.resource;
    import util = nts.uk.util;
    import text = nts.uk.text;
    import block = nts.uk.ui.block;

    export class ScreenModel {
        listRankDto: KnockoutObservableArray<RankDto> = ko.observableArray([]);
        selectedRankCode: KnockoutObservable<string> = ko.observable(['1']);
        listEmpRankDto: KnockoutObservableArray<EmployeeRankDto> = ko.observableArray([]);
        listEmpData: KnockoutObservableArray<EmployeeData> = ko.observableArray([]);

        listSelectedEmpRank: KnockoutObservable<string> = ko.observable(['']);
        listDataEmp: KnockoutObservable<string> = ko.observable(['']);
        //Grird list
        columns: KnockoutObservableArray<NtsGridListColumn>;
        //KCP005
        listComponentOption: any;
        selectedCode: KnockoutObservable<string>;

        isShowAlreadySet: KnockoutObservable<boolean>;
        isDialog: KnockoutObservable<boolean> = ko.observable(true);
        isShowNoSelectRow: KnockoutObservable<boolean> = ko.observable(false);
        isMultiSelect: KnockoutObservable<boolean> = ko.observable(true);
        isShowWorkPlaceName: KnockoutObservable<boolean> = ko.observable(false);
        isShowSelectAllButton: KnockoutObservable<boolean> = ko.observable(false);
        disableSelection: KnockoutObservable<boolean> = ko.observable(false);
        employeeList: KnockoutObservableArray<UnitModel>;
        showOptionalColumn: KnockoutObservable<boolean> = ko.observable(true);
        optionalColumnDatasource: KnockoutObservableArray<any> = ko.observableArray([]);
        arrSid: any;
        selectIds: KnockoutObservable<string> = ko.observable(['']);

        enableSave: KnockoutObservable<boolean> = ko.observable(true);
        baseDate : any;

        constructor() {
            let self = this;

            self.selectedRankCode = ko.observable(['1']);

            ///KCP005
            self.baseDate = ko.observable(new Date());
            self.selectedCode = ko.observable('1');

            self.isShowAlreadySet = ko.observable(false);
            self.disableSelection = ko.observable(false);
            self.showOptionalColumn = ko.observable(true);

            this.employeeList = ko.observableArray<UnitModel>([

            ]);

            self.listComponentOption = {
                isShowAlreadySet: self.isShowAlreadySet(),
                isMultiSelect: self.isMultiSelect(),
                listType: ListType.EMPLOYEE,
                employeeInputList: self.employeeList,
                selectType: SelectType.SELECT_BY_SELECTED_CODE,
                selectedCode: self.selectedCode,
                isDialog: self.isDialog(),
                isShowNoSelectRow: self.isShowNoSelectRow(),
                isShowWorkPlaceName: self.isShowWorkPlaceName(),
                isShowSelectAllButton: self.isShowSelectAllButton(),
                disableSelection: self.disableSelection(),
                showOptionalColumn: self.showOptionalColumn(),
                optionalColumnName: nts.uk.resource.getText('KSU001_3307'),
                optionalColumnDatasource: self.optionalColumnDatasource,
                maxRows: 15,
                width: 505
            };
            self.selectedCode.subscribe(function(value) {
                if (nts.uk.util.isNullOrEmpty(value)) {
                    self.enableSave(false);
                } else {
                    self.enableSave(true);
                }
            });
        }


        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.selectedCode(_.map(nts.uk.ui.windows.getShared('KSU001M'), "id"));
            self.listEmpData(nts.uk.ui.windows.getShared('KSU001M'));
            if (nts.uk.util.isNullOrEmpty(self.listEmpData())) {
                self.enableSave(false);
            }
            var arrSid: any = [];
            var dataEmp = [];
            _.each(nts.uk.ui.windows.getShared('KSU001M'), (x) => {
                arrSid.push({ code: x.code, name: x.name, id: x.id });
            });
            self.employeeList(arrSid);


            service.startPage(self.selectedCode()).done(function(data) {
                console.log(data);
                
                 if(data.listRankDto.length == 0) {
//                nts.uk.ui.dialog.error({ messageId: "Msg_1643" });
                     nts.uk.ui.dialog.error({ messageId: "Msg_1643" }).then(() => {
                         nts.uk.ui.windows.close();
                     });
                     
                 }
                _.orderBy(data.listRankDto, 'priority', 'asc');
                
                _.forEach(data.listEmpRankDto, function(item) {
                    let matchRank = _.find(data.listRankDto, ['rankCd', item.emplRankCode]);
                    if (matchRank != undefined) {
                        item.rankName = matchRank.rankSymbol;
                        item.priority = matchRank.priority;
                    }
                    else {
                        item.rankName = item.emplRankCode + " " +  "マスタ未登録";
                    }
                    //item.rankName = (matchRank != null) ? matchRank.rankSymbol : "";
                });
                
                _.forEach(self.listEmpData(), function(item) {
                    let matchRank = _.find(data.listEmpRankDto, ['employeeID', item.id]);
                    item.emplRankCode = matchRank != null ? matchRank.emplRankCode : "";
                    item.rankName = matchRank != null ? matchRank.rankName : "";
                    item.priority = matchRank != null ? matchRank.priority : "z";

                });
                //Sort theo emplRankCode và employeeCode 
                
                self.listRankDto(data.listRankDto);
                self.listEmpRankDto(data.listEmpRankDto);
                
                self.employeeList(_.sortBy(self.listEmpData(), [(item) => {
                    if(!item.priority) {
                        return 9998;
                    } 
                    return item.emplRankCode == "" ? 9999 : item.priority; 
               }, 'code']));
               // _.orderBy(self.employeeList(), ['priority', 'code'], ['asc', 'asc']);
              //  self.employeeList(_.orderBy(self.employeeList(), ['priority', 'code'], ['asc', 'asc']));
                var tempOptionalDataSource: any = [];
                if (self.listEmpData() != null) {
                    self.listEmpData().forEach(function(item) {
                        tempOptionalDataSource.push({ empId: item.code, content: item.rankName });
                    });
                }
                self.optionalColumnDatasource(tempOptionalDataSource);
                $('#component-items-list').ntsListComponent(self.listComponentOption).done(function() {
                    _.defer(function() {
//                        let componentGridId = "#" + $("#component-items-list").find("[id$='tooltips_ruler']").attr('id').split("_")[0];
//                        $(componentGridId).igGrid("option", "width", 490);
//                        $("#component-items-list .bg-green").width(490);
//                        $(componentGridId).igGrid("option", "height", 400);
                    });
                });
                self.enableSave(false);
                dfd.resolve();
            }).fail(function(error) {
                dfd.fail();
                nts.uk.ui.dialog.alert({ messageId: error.messageId });
              
            });
            return dfd.promise();
        }
        

        registryButtonClick() {
            var self = this;
            $(".nts-input").trigger("validate");
            if (!$(".nts-input").ntsError("hasError")) {
                var arrEmpSelect: any = [];
                var select: any = [];
                 
                arrEmpSelect = _.filter(self.employeeList(), (o) => {
                    return self.selectedCode().indexOf(o.code) !== -1;
                });
                self.selectIds(_.map(arrEmpSelect, "code"));
                let param = {
                    listEmpId:_.map(arrEmpSelect, "id"),
                    rankCd: self.selectedRankCode()
                };
                block.invisible();
                service.regis(param).done((data) => {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                         $("#combo-box").focus();
                         self.startPage().done(() => {
                         self.selectedCode([]);
                         });
                    });
                    block.clear();
                   
                }).fail((res: any) => {
                    nts.uk.ui.dialog.alert({ messageId: res.messageId });
                    block.clear();
                }).always(() => {
                    $("#combo-box").focus();
                    block.clear();

                });
            }
        }

        cancel_Dialog(): any {
            let self = this;
            nts.uk.ui.windows.close();
        }


    }
    class RankDto {
        rankCd: string;
        rankSymbol: string;

        constructor(rankCd: string, rankSymbol: string) {
            this.rankCd = rankCd;
            this.rankSymbol = rankSymbol;
        }
    }

    class EmployeeRankDto {
        employeeID: string;
        emplRankCode: string;
        rankName: string;

        constructor(employeeID: string, rankSymbol: string, rankName: string) {
            this.employeeID = employeeID;
            this.emplRankCode = emplRankCode;
            this.rankName = rankName;
        }
    }

    class EmployeeData {
        employeeID: string;
        employeeName: string;
        emplRankCode: string;
        rankName: string;
        employeeName

        constructor(employeeID: string, employeeName: string, rankSymbol: string, rankName: string) {
            this.employeeID = employeeID;
            this.employeeName = employeeName;
            this.emplRankCode = emplRankCode;
            this.rankName = rankName;
        }
    }

    export class ListType {
        static EMPLOYMENT = 1;
        static Classification = 2;
        static JOB_TITLE = 3;
        static EMPLOYEE = 4;
    }

    export interface UnitModel {
        id?: string;
        code: string;

        name?: string;
        workplaceName?: string;
        isAlreadySetting?: boolean;
        optionalColumn?: any;
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
    export interface OptionalColumnDataSource {
        id: string;
        content: any;
    }

}