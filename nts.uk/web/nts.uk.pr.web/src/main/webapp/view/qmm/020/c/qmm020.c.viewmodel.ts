module qmm020.c.viewmodel {
    export class ScreenModel {
        // listbox
        itemList: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        itemListDetail: KnockoutObservableArray<EmployeeAllotSettingDto>;
        employeeTotal: KnockoutObservableArray<TotalModel>;
        itemSetShareList: KnockoutObservableArray<EmployeeSettingHeaderModel>;
        itemTotalList: KnockoutObservableArray<TotalModel>;
        itemAllotSetting: KnockoutObservableArray<TotalModel>;
        itemName: KnockoutObservable<string>;
        currentCode: KnockoutObservable<number>;
        currentItem: KnockoutObservable<TotalModel>;
        selectedName: KnockoutObservable<string>;
        selectedCode: KnockoutObservable<string>;
        selectedCodes: KnockoutObservableArray<string>;
        isEnable: KnockoutObservable<boolean>;
        histId: KnockoutObservable<string>;
        itemHist: KnockoutObservable<EmployeeSettingHeaderModel>;
        maxItem: KnockoutObservable<TotalModel>;
        maxDate: string;
        //selectedCode: KnockoutObservable<string>;


        employeeAllotHead: Array<EmployeeSettingHeaderModel>;
        dataSource: any;
        selectedList: any;

        constructor() {
            var self = this;
            let dfd = $.Deferred<any>();
            self.itemList = ko.observableArray([]);
            self.selectedCode = ko.observable();
            self.isEnable = ko.observable(true);
            self.selectedList = ko.observableArray([]);
            self.employeeTotal = ko.observableArray([]);
            self.itemHist = ko.observable(null);
            self.itemTotalList = ko.observableArray([]);
            self.itemListDetail = ko.observableArray([]);
            self.itemAllotSetting = ko.observableArray([]);
            self.histId = ko.observable(null);
            self.maxItem = ko.observable(null);
            self.maxDate = "";
            self.dataSource = ko.observableArray([]);
            self.currentItem = ko.observable(new TotalModel({
                historyId: '',
                employmentCode: '',
                paymentDetailCode: '',
                bonusDetailCode: '',
                startYm: '',
                endYm: ''
            }));


            // LST_001
            this.columns = ko.observableArray([
                { headerText: '', prop: 'startEnd', width: 200 }
            ]);

            self.selectedCode.subscribe(function(codeChange) {
                //let x =  self.getEmpName();
                //self.itemAllotSetting([]);
                self.getHist();
                service.getEmployeeDetail(ko.toJS(codeChange)).done(function(data: Array<IModel>) {
                    let employeeItems: Array<IModel> = [];
                    if (data && data.length > 0) {
                        employeeItems = $("#C_LST_001").igGrid("option", "dataSource");
                        if (employeeItems.length == 0) {
                            employeeItems = data;
                        }
                        for (let i in employeeItems) {
                            let item = employeeItems[i];
                            let itemAllotSetting = _.find(data, function(allot) { return item.employmentCode == allot.employeeCode; });
                            if (itemAllotSetting) {
                                item.paymentDetailCode = itemAllotSetting.paymentDetailCode;
                                item.paymentDetailName = itemAllotSetting.paymentDetailName;
                                item.bonusDetailCode = itemAllotSetting.bonusDetailCode;
                                item.bonusDetailName = itemAllotSetting.bonusDetailName;
                            } else {
                                item.paymentDetailCode = undefined;
                                item.paymentDetailName = undefined;
                                item.bonusDetailCode = undefined;
                                item.bonusDetailName = undefined;
                            }
                        }

                        // Set data to grid
                        $("#C_LST_001").igGrid("option", "dataSource", employeeItems);

                        dfd.resolve();
                    }

                    //                    self.itemAllotSetting(employeeItems);
                    dfd.resolve();

                }).fail(function(res) {
                    //Alert message
                    alert(res);
                });
                dfd.promise();
                self.currentItem(self.getHist(codeChange));
            });
            // init columns and title for grid
            self.LoadData([]);
            // call first method
            self.start();
            $("#C_BTN_001").click(function() {
                alert("The paragraph was clicked.");
            });
        }

        LoadData(itemList) {
            let self = this;
            $("#C_LST_001").igGrid({
                columns: [
                    { headerText: "", key: "NO", dataType: "string", width: "20px" },
                    { headerText: "繧ｳ繝ｼ繝�", key: "employmentCode", dataType: "string", width: "100px" },
                    { headerText: "蜷咲ｧｰ", key: "employmentName", dataType: "string", width: "200px" },
                    { headerText: "", key: "paymentDetailCode", dataType: "string", hidden: true },
                    { headerText: "", key: "paymentDetailName", dataType: "string", hidden: true },
                    { headerText: "", key: "bonusDetailCode", dataType: "string", hidden: true },
                    { headerText: "", key: "bonusDetailName", dataType: "string", hidden: true },
                    {
                        headerText: "邨ｦ荳取�守ｴｰ譖ｸ", key: "paymentDetailCode", dataType: "string", width: "250px", unbound: true,
                        template: "<input type='button' data-name='${paymentDetailCode}' class='" + "C_BTN_001" + "' value='驕ｸ謚�'/><label style='margin-left:5px;'>${paymentDetailCode}</label><label style='margin-left:15px;'>${paymentDetailName}</label>"
                    },
                    {
                        headerText: "雉樔ｸ取�守ｴｰ譖ｸ", key: "bonusDetailCode", dataType: "string", width: "20%", unbound: true,
                        template: "<input type='button' data-name='${bonusDetailName}' class='" + "C_BTN_002" + "' value='驕ｸ謚�'/><label style='margin-left:5px;'>${bonusDetailCode}</label><label style='margin-left:15px;'>${bonusDetailName}</label>"
                    },
                ],
                features: [{
                    name: 'Selection',
                    mode: 'row',
                    multipleSelection: true,
                    activation: false,
                    //                    rowSelectionChanged: this.selectionChanged.bind(this)
                },
                ],
                virtualization: true,
                virtualizationMode: 'continuous',
                width: "800px",
                height: "240px",
                primaryKey: "employmentCode",
                dataSource: itemList

            });
        }

        //find histId to subscribe
        getHist(value: any) {
            let self = this;
            let rtHist = _.find(ko.toJS(self.itemTotalList()), function(item: IModel) {
                return item.historyId == value;
            });
            return rtHist;
        }
        //find employeeName initiation
        getEmpName(value: any) {
            let self = this;
            value = _.find(ko.mapping.toJS(self.employeeTotal()), function(o: IModel) {
                return o.employmentCode == '00001';
            });
            return value;
        }
        // getEmpCode initiation
        getEmpcode(value: any) {
            let self = this;
            value = _.find(ko.mapping.toJS(self.employeeTotal()), function(h: IModel) {
                return null;
            });
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
            //fill employ data to c_LST_001
            service.getEmployeeName().done(function(data: Array<IModel>) {
                let employeeItem: Array<TotalModel> = [];
                if (data && data.length > 0) {
                    _.forEach(data, function(item) {
                        employeeItem.push({ historyId: item.historyId, employmentCode: item.employmentCode, employmentName: item.employmentName });
                    });
                    dfd.resolve();
                }
                self.employeeTotal(employeeItem);
                // self.currentItem(employeeItem.employmentName);
                // Set datafor grid
                $("#C_LST_001").igGrid("option", "dataSource", employeeItem);
                dfd.resolve();

            }).fail(function(res) {
                // Alert message
                alert(res);
            });
            dfd.promise();
            //Get list startDate, endDate of History  
            let totalItem: Array<TotalModel> = [];
            service.getEmployeeAllotHeaderList().done(function(data: Array<IModel>) {
                if (data.length > 0) {
                    _.forEach(data, function(item) {
                        totalItem.push(new TotalModel({ historyId: item.historyId, startEnd: item.startYm + ' ~ ' + item.endYm, endYm: item.endYm, startYm: item.startYm }));
                    });
                    self.itemTotalList(totalItem);
                    //let max = _.maxBy(self.itemList(), (itemMax) => { return itemMax.endYm });
                    dfd.resolve();
                } else {
                    dfd.resolve();
                }
            }).fail(function(res) {
                // Alert message
                alert(res);
            });

            service.getAllotEmployeeMaxDate().done(function(itemMax: number) {
                let maxDate: TotalModel = _.find(self.itemTotalList(), function(obj) { return parseInt(obj.endYm()) == itemMax; });
                self.maxDate = (maxDate.startYm || "").toString();
                self.maxItem(maxDate);
                dfd.resolve();
            }).fail(function(res) {
                alert(res);
            });


            // Return.
            return dfd.promise();
        }
        //click register button
        /**
         * 
         */


        //        register() {
        //            var self = this;
        //            var current = _.find(self.itemTotalList(), function(item: IModel) { return item.historyId == self.currentItem().historyId(); });
        //            //            debugger;
        //            if (current) {
        //                //                service.insertEmAllot(current).done(function() {
        //                //                }).fail(function(res) {
        //                //                    alert(res);
        //                //                });
        //            }
        //        }
        //Open dialog Add History
        openJDialog() {
            var self = this;
            debugger;
            var historyScreenType = "1";
            let valueShareJDialog = historyScreenType + "~" + "201701";

            nts.uk.ui.windows.setShared('valJDialog', valueShareJDialog);

            nts.uk.ui.windows.sub.modal('../j/index.xhtml', { title: '譏守ｴｰ譖ｸ縺ｮ邏舌★縺托ｼ槫ｱ･豁ｴ霑ｽ蜉�' })
                .onClosed(function() {
                    let returnJDialog: string = nts.uk.ui.windows.getShared('returnJDialog');
                    var modeRadio = returnJDialog.split("~")[0];
                    var returnValue = returnJDialog.split("~")[1];
                    if (returnValue != '') {
                        //                        let employeeAllotSettings = new Array<EmployeeAllotSettingDto>();
                        var items = self.itemTotalList();
                        var addItem;
                        var copItem;
                        if (modeRadio === "2") {
                            addItem = new TotalModel({
                                historyId: '',
                                employmentCode: '',
                                paymentDetailCode: '',
                                bonusDetailCode: '',
                                startYm: returnValue,
                                endYm: '999912',
                                startEnd: (returnValue + ' ~ ' + '999912')
                            });
                            items.push(addItem);
                        } else {
                            copItem = new TotalModel({
                                historyId: '',
                                employmentCode: '',
                                employmentName: '',
                                paymentDetailCode: '',
                                paymentDetailName: '',
                                bonusDetailCode: '',
                                bonusDetailName: '',
                                startYm: returnValue,
                                endYm: '999912',
                                startEnd: (returnValue + ' ~ ' + '999912')
                            });
                            self.currentItem().historyId(copItem.historyId());
                            self.currentItem().startYm(returnValue);
                            self.currentItem().endYm('999912');
                            self.currentItem().employmentCode(self.maxItem().historyId());
                            //get employmentName, paymentDetailName, paymentDetailCode
                            let dfd = $.Deferred<any>();
                            service.getAllEmployeeAllotSetting(ko.toJS(self.maxItem().historyId())).done(function(data) {
                                self.itemListDetail([]);
                                if (data && data.length > 0) {
                                    _.map(data, function(item: IModel) {
                                        self.itemListDetail.push(new copItem(item.historyId, item.employmentCode, item.employmentName, item.paymentDetailCode
                                            , item.paymentDetailName, item.bonusDetailCode, item.bonusDetailName, item.startYm, item.endYm, item.startEnd));
                                    });
                                    self.currentItem().employmentCode(ko.toJS(self.itemListDetail()[0].employmentCode));
                                    self.currentItem().employmentName(ko.toJS(self.itemListDetail()[0].employmentName));
                                    self.currentItem().paymentDetailCode(ko.toJS(self.itemListDetail()[0].paymentDetailCode));
                                    self.currentItem().paymentDetailName(ko.toJS(self.itemListDetail()[0].paymentDetailName));
                                    self.currentItem().bonusDetailCode(ko.toJS(self.itemListDetail()[0].bonusDetailCode));
                                    self.currentItem().bonusDetailName(ko.toJS(self.itemListDetail()[0].bonusDetailName));
                                    dfd.resolve();
                                }

                                $("#C_LST_001").igGrid("option", "dataSource", ko.mapping.toJS(self.itemListDetail));
                                dfd.resolve();
                            }).fail(function(res) {
                                // Alert message
                                alert(res);
                            });
                            dfd.promise();

                            items.push(copItem);

                        }

                        self.itemTotalList([]);
                        self.itemTotalList(items);
                    }
                });
        }
        //Open dialog Edit History
        openKDialog() {
            var self = this;
            //var singleSelectedCode = self.singleSelectedCode().split(';');
            //nts.uk.ui.windows.setShared('stmtCode', singleSelectedCode[0]);
            nts.uk.ui.windows.sub.modal('/view/qmm/020/k/index.xhtml', { title: '譏守ｴｰ譖ｸ縺ｮ邏舌★縺托ｼ槫ｱ･豁ｴ邱ｨ髮�' }).onClosed(function(): any {
                //self.start(self.singleSelectedCode());
            });
        }

        openMDialog() {
            var self = this;
            var valueShareMDialog = self.currentItem().startYm();
            //debugger;
            nts.uk.ui.windows.setShared('valMDialog', valueShareMDialog);
            nts.uk.ui.windows.sub.modal('/view/qmm/020/m/index.xhtml', { title: '隴丞ｮ茨ｽｴ�ｽｰ隴厄ｽｸ邵ｺ�ｽｮ鬩包ｽｸ隰夲ｿｽ' }).onClosed(function(): any {
                //get selected code from M dialog
                //get Name payment Name
            });
        }

    }
    export class ItemModel {
        histId: string;
        startEnd: string;
        endYm: string;

        constructor(histId: string, startEnd: string, endYm: string) {
            let self = this;
            self.histId = (histId);
            self.startEnd = startEnd;
            self.endYm = endYm;
        }
    }

    class EmployeeAllotSettingDto {
        companyCode: KnockoutObservable<string>;
        historyId: KnockoutObservable<string>;
        employmentCode: KnockoutObservable<string>;
        employmentName: KnockoutObservable<string>;
        paymentDetailCode: KnockoutObservable<string>;
        paymentDetailName: KnockoutObservable<string>;
        bonusDetailCode: KnockoutObservable<string>;
        bonusDetailName: KnockoutObservable<string>;
        constructor(companyCode: string, historyId: string, employmentCode: string, employmentName: string, paymentDetailCode: string, paymentDetailName: string, bonusDetailCode: string, bonusDetailName: string) {
            this.companyCode = ko.observable(companyCode);
            this.historyId = ko.observable(historyId);
            this.employmentCode = ko.observable(employmentCode);
            this.employmentName = ko.observable(employmentName);
            this.paymentDetailCode = ko.observable(paymentDetailCode);
            this.paymentDetailName = ko.observable(paymentDetailName);
            this.bonusDetailCode = ko.observable(bonusDetailCode);
            this.bonusDetailName = ko.observable(bonusDetailName);
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
        employmentCode: string;
        paymentDetailCode: string;
        bonusDetailCode: string;

        constructor(companyCode: string, historyId: string, employmentCode: string, paymentDetailCode: string, bonusDetailCode: string) {
            this.companyCode = companyCode;
            this.historyId = historyId;
            this.employmentCode = employmentCode;
            this.paymentDetailCode = paymentDetailCode;
            this.bonusDetailCode = bonusDetailCode;

        }
    }

    interface IModel {
        companyCode?: string;
        historyId: string;

        employmentCode?: string;
        employmentName?: string;
        paymentDetailCode?: string;
        paymentDetailName?: string;
        bonusDetailCode?: string;
        bonusDetailName?: string;
        startYm?: string;
        endYm?: string;
        startEnd?: string;
    }

    class TotalModel {
        id: string;
        companyCode: KnockoutObservable<string>;
        historyId: KnockoutObservable<string>;

        employmentCode: KnockoutObservable<string>;
        employmentName: KnockoutObservable<string>;
        paymentDetailCode: KnockoutObservable<string>;
        paymentDetailName: KnockoutObservable<string>;
        bonusDetailCode: KnockoutObservable<string>;
        bonusDetailName: KnockoutObservable<string>;
        startYm: KnockoutObservable<string>;
        endYm: KnockoutObservable<string>;
        startEnd: string;
        constructor(param: IModel) {
            this.companyCode = ko.observable(param.companyCode);
            this.id = param.historyId;
            this.historyId = ko.observable(param.historyId);
            this.startEnd = param.startEnd;
            this.employmentCode = ko.observable(param.employmentCode);
            this.employmentName = ko.observable(param.employmentName);
            this.paymentDetailCode = ko.observable(param.paymentDetailCode);
            this.paymentDetailName = ko.observable(param.paymentDetailName);
            this.bonusDetailCode = ko.observable(param.bonusDetailCode);
            this.bonusDetailName = ko.observable(param.bonusDetailName);
            this.startYm = ko.observable(param.startYm);
            this.endYm = ko.observable(param.endYm);
        }
    }
    class EmpDetail {

    }
}

$(function() {
    $(document).on("click", ".C_BTN_001", function() {
        var self = this;
        alert($(this).data('name'));
        var valueShareMDialog = ko.mapping.toJS(__viewContext.viewModel.viewmodelC.currentItem().startYm);
        //debugger;
        nts.uk.ui.windows.setShared('valMDialog', valueShareMDialog);
        nts.uk.ui.windows.sub.modal('/view/qmm/020/m/index.xhtml', { title: '隴丞ｮ茨ｽｴ�ｽｰ隴厄ｽｸ邵ｺ�ｽｮ鬩包ｽｸ隰夲ｿｽ' }).onClosed(function(): any {
            //get selected code from M dialog
            var stmtCodeSelected = nts.uk.ui.windows.getShared('stmtCodeSelected');

            __viewContext.viewModel.viewmodelC.currentItem().paymentDetailCode(stmtCodeSelected);
            //get Name payment Name
            service.getAllotLayoutName(self.currentItem().payCode()).done(function(stmtName: string) {
                self.currentItem().payName(stmtName);
            }).fail(function(res) {
                alert(res);
            });
        });
    });
})
