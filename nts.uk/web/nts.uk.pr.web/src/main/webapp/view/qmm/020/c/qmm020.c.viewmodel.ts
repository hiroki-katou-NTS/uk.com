module qmm020.c.viewmodel {


    export class ScreenModel {
        // listbox
        itemList: KnockoutObservableArray<ItemModel>;
        itemListDetail: KnockoutObservableArray<EmployeeAllotModel>;
        itemName: KnockoutObservable<string>;
        currentCode: KnockoutObservable<number>
        selectedName: KnockoutObservable<string>;
        selectedCode: KnockoutObservableArray<string>;
        selectedCodes: KnockoutObservableArray<string>;
        isEnable: KnockoutObservable<boolean>;
        //selectedCode: KnockoutObservable<string>;


        employeeAllotHead: Array<EmployeeSettingHeaderModel>;
        dataSource: any;
        selectedList: any;

        constructor() {
            var self = this;
            self.itemList = ko.observableArray([]);
            self.selectedCode = ko.observableArray([]);
            self.isEnable = ko.observable(true);
            self.selectedList = ko.observableArray([]);


            // Array Data 1 
            let employment1 = ko.mapping.fromJS([
                { "NO": 1, "ID": "000000001", "Name": "正社員", "PaymentDocID": "K001", "PaymentDocName": "給与明細書001", "BonusDocID": "S001", "BonusDocName": "賞与明細書001" },
                { "NO": 2, "ID": "000000002", "Name": "DucPham社員", "PaymentDocID": "K002", "PaymentDocName": "給与明細書002", "BonusDocID": "S001", "BonusDocName": "賞与明細書002" },
                { "NO": 3, "ID": "000000003", "Name": "HoangMai社員", "PaymentDocID": "K003", "PaymentDocName": "給与明細書003", "BonusDocID": "S001", "BonusDocName": "賞与明細書003" }
            ]);
            // Array Data 2 
            let employment2 = ko.mapping.fromJS([
                { "NO": 1, "ID": "000000004", "Name": "ABC社員", "PaymentDocID": "K001", "PaymentDocName": "給与明細書001", "BonusDocID": "S001", "BonusDocName": "賞与明細書001" },
                { "NO": 2, "ID": "000000005", "Name": "DEF社員", "PaymentDocID": "K002", "PaymentDocName": "給与明細書002", "BonusDocID": "S001", "BonusDocName": "賞与明細書002" },
                { "NO": 3, "ID": "000000006", "Name": "GHK社員", "PaymentDocID": "K003", "PaymentDocName": "給与明細書003", "BonusDocID": "S001", "BonusDocName": "賞与明細書003" }
            ]);
            //self.buildGrid("#C_LST_001", "C_BTN_001", "C_BTN_002");

            self.dataSource = ko.mapping.toJS(employment1());
            //console.log(self.dataSource);
            //Build IgGrid
            $("#C_LST_001").igGrid({
                columns: [
                    { headerText: "", key: "NO", dataType: "string", width: "20px" },
                    { headerText: "コード", key: "ID", dataType: "string", width: "100px" },
                    { headerText: "名称", key: "Name", dataType: "string", width: "200px" },
                    { headerText: "", key: "PaymentDocID", dataType: "string", hidden: true },
                    { headerText: "", key: "PaymentDocName", dataType: "string", hidden: true },
                    { headerText: "", key: "BonusDocID", dataType: "string", hidden: true },
                    { headerText: "", key: "BonusDocName", dataType: "string", hidden: true },
                    {
                        headerText: "給与明細書", key: "PaymentDocID", dataType: "string", width: "250px", unbound: true,
                        template: "<input type='button' id='" + "C_BTN_001" + "' value='選択'/><label style='margin-left:5px;'>${PaymentDocID}</label><label style='margin-left:15px;'>${PaymentDocName}</label>"
                    },
                    {
                        headerText: "賞与明細書", key: "BonusDoc", dataType: "string", width: "20%", unbound: true,
                        template: "<input type='button' id='" + "C_BTN_002" + "' value='選択'/><label style='margin-left:5px;'>${BonusDocID}</label><label style='margin-left:15px;'>${BonusDocName}</label>"
                    },
                ],
                features: [{
                    name: 'Selection',
                    mode: 'row',
                    multipleSelection: true,
                    activation: false,
                    rowSelectionChanged: this.selectionChanged.bind(this)
                }],
                virtualization: true,
                virtualizationMode: 'continuous',
                width: "800px",
                height: "240px",
                primaryKey: "ID",
                dataSource: ko.mapping.toJS(employment1())
            });


            //subcribe list box's change
            self.selectedCode.subscribe(function(codeChange) {
                if (codeChange.length > 0) {
                    //get ValueName when click to item in HistoryList
                    let row = _.find(self.itemList(), function(item) {
                        let code = self.selectedCode();
                        //                        if (code === item.code) {
                        //                            //self.selectedName(item.name);
                        //                        }
                    });
                }
            });
            //SCREEN C
            //Event : Click to button Sentaku on igGrid
            var openPaymentDialog = function(evt, ui) {
                if (ui.colKey === "PaymentDocID") {
                    //Gọi hàm open SubWindow
                    //Khi close Subwindow, get dc cái new object(ID, Name... )
                    let row = _.find(employment1(), function(item) {
                        //return item.ID() === ui.rowKey;
                    });
                    //row.PaymentDocName("test");
                    //self.buildGrid("#C_LST_001", "C_BTN_001", "C_BTN_002");
                }
            }
            self.start();
        }
        //Selected changed
        selectionChanged(evt, ui) {
            //console.log(evt.type);
            var selectedRows = ui.selectedRows;
            var arr = [];
            for (var i = 0; i < selectedRows.length; i++) {
                arr.push("" + selectedRows[i].id);
            }
            this.selectedList(arr);
        };




        // start function
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            //Get list startDate, endDate of History  
            service.getEmployeeAllotHeaderList().done(function(data: Array<EmployeeSettingHeaderModel>) {
                if (data.length > 0) {
                    //TODO 2017.03.14
                    _.forEach(data, function(item) {
                        self.itemList.push(new ItemModel(item.historyId, item.startYm + ' ~ ' + item.endYm));
                    });

                } else {
                    dfd.resolve();
                }
            }).fail(function(res) {
                // Alert message
                alert(res);
            });
//            service.getEmployeeAllotDetailList().done(funtion(dataDetail: Array<EmployeeSettingDetailModel>){
//                if(dataDetail.length > 0) {
//                    _.forEach(dataDetail, function(item) {
//                        self.itemListDetail.push(new EmployeeAllotModel(item.histId, item.empCode, item.paymentCode, item.paymentName, item.bonusCode, item.bonusName ));
//                    });
//                } else {
//                    dfd.resolve();
//                }
//            }).fail(function(res) {
//                // Alert message
//                alert(res);
//            });

            // Return.
            return dfd.promise();
        }
        //Open dialog Add History
        openJDialog() {}
        //Open dialog Edit History
        openKDialog() {
            var self = this;
            //var singleSelectedCode = self.singleSelectedCode().split(';');
            //nts.uk.ui.windows.setShared('stmtCode', singleSelectedCode[0]);
            nts.uk.ui.windows.sub.modal('/view/qmm/020/k/index.xhtml', { title: '明細書の紐ずけ＞履歴編集' }).onClosed(function(): any {
                //self.start(self.singleSelectedCode());
            });
        }

    }
    export class ItemModel {
        histId: KnockoutObservable<string>;
        startEnd: string;

        constructor(histId: string, startEnd: string) {
            let self = this;
            self.histId = ko.observable(histId);
            self.startEnd = startEnd;
        }
    }

    class EmployeeAllotSettingDto {
        companyCode: KnockoutObservable<string>;
        historyId: KnockoutObservable<string>;
        employeeCode: KnockoutObservable<string>;
        bonusDetailCode: KnockoutObservable<string>;
        paymentDetailCode: KnockoutObservable<string>;
        constructor(companyCode: string, historyId: string, employeeCode: string, bonusDetailCode: string, paymentDetailCode: string) {
            this.companyCode = ko.observable(companyCode);
            this.historyId = ko.observable(historyId);
            this.bonusDetailCode = ko.observable(bonusDetailCode);
            this.paymentDetailCode = ko.observable(paymentDetailCode);
        }
    }

    export class EmployeeSettingHeaderModel {
        companyCode: string;
        startYm: string;
        endYm: string;
        historyId: string;

        constructor(companyCode: string, startYm: string, endYm: string, historyId: string) {
            this.companyCode = companyCode;
            this.startYm = startYm;
            this.endYm = endYm;
            this.historyId = historyId;
        }
    }

    export class EmployeeSettingDetailModel {
        companyCode: string;
        historyId: string;
        employeeCode: string;
        paymentDetailCode: string;
        bonusDetailCode: string;

        constructor(companyCode: string, historyId: string, employeeCode: string, paymentDetailCode: string, bonusDetailCode: string) {
            this.companyCode = companyCode;
            this.historyId = historyId;
            this.employeeCode = employeeCode;
            this.paymentDetailCode = paymentDetailCode;
            this.bonusDetailCode = bonusDetailCode;

        }
    }

}