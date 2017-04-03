module qmm020.b.viewmodel {
    export class ScreenModel {
        //content:  KnockoutObservable<any>;
        itemList: KnockoutObservableArray<ComHistItem>;
        currentItem: KnockoutObservable<ComHistItem>;
        maxItem: KnockoutObservable<service.model.CompanyAllotSettingDto>;
        companyAllots: Array<service.model.CompanyAllotSettingDto>;
        isInsert: KnockoutObservable<boolean>;
        maxDate: string;
        constructor() {
            var self = this;
            self.maxDate = "";
            self.isInsert = ko.observable(false);
            self.itemList = ko.observableArray([]);
            self.currentItem = ko.observable(new ComHistItem({
                histId: '',
                startYm: '',
                endYm: '',
                payCode: '',
                bonusCode: ''
            }));
            self.maxItem = ko.observable(new service.model.CompanyAllotSettingDto());
            self.start();
        }
        // start function
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            //Get list startDate, endDate of History 
            //get allot Company
            service.getAllotCompanyList().done(function(companyAllots: Array<service.model.CompanyAllotSettingDto>) {
                if (companyAllots.length > 0) {
                    let _items: Array<ComHistItem> = [];
                    //push data to listItem of hist List
                    for (let i in companyAllots) {
                        let item = companyAllots[i];
                        if (item) {
                            _items.push(new ComHistItem({
                                histId: (item.historyId || "").toString(),
                                startYm: (item.startDate || "").toString(),
                                endYm: (item.endDate || "").toString(),
                                payCode: (item.paymentDetailCode || "").toString(),
                                bonusCode: (item.bonusDetailCode || "").toString()
                            }));
                        }
                    }
                    self.itemList(_items);
                    self.companyAllots = companyAllots;
                    self.currentItem().setSource(self.companyAllots);
                    self.currentItem().histId(companyAllots[0].historyId);
                } else {
                    //self.allowClick(false);
                    dfd.resolve();
                }
            }).fail(function(res) {
                // Alert message
                alert(res);
            });
            ///////////////////////////////////////
            ///////////////////////////////////////
            //get Row with max Date 
            service.getAllotCompanyMaxDate().done(function(itemMax: service.model.CompanyAllotSettingDto) {
                //console.log(current);
                self.maxDate = (itemMax.startDate || "").toString();
                self.maxItem(itemMax);
            }).fail(function(res) {
                alert(res);
            });

            // Return.
            return dfd.promise();
        }
        
        //Update data
        register() {
            var self = this;
            var current = _.find(self.companyAllots, function(item) { return item.historyId == self.currentItem().histId(); });
            debugger;
            if (current) {
                service.insertComAllot(current).done(function() {
                }).fail(function(res) {
                    alert(res);
                });
            }
        }

        //Open dialog Add History
        openJDialog() {
            var self = this;
            //getMaxDate
            var historyScreenType = "1";
            //Get value TabCode + value of selected Name in History List
            let valueShareJDialog = historyScreenType + "~" + self.maxDate;

            nts.uk.ui.windows.setShared('valJDialog', valueShareJDialog);

            nts.uk.ui.windows.sub.modal('/view/qmm/020/j/index.xhtml', { title: '明細書の紐ずけ＞履歴追加' })
                .onClosed(function() {
                    let returnJDialog: string = nts.uk.ui.windows.getShared('returnJDialog');
                    var modeRadio = returnJDialog.split("~")[0];
                    var returnValue = returnJDialog.split("~")[1];

                    if (returnValue != '') {
                        var items = self.itemList();
                        var addItem = new ComHistItem({
                            histId: new Date().getTime().toString(),
                            startYm: returnValue,
                            endYm: '999912',
                            payCode: '',
                            bonusCode: ''
                        });
                        items.push(addItem);
                        // Goi cap nhat vao currentItem
                        // Trong truong hop them moi NHANH, copy payCode, bonusCode tu Previous Item
                        if (modeRadio === "1") {
                            debugger;
                            self.currentItem().histId(addItem.histId());
                            self.currentItem().startYm(returnValue);
                            self.currentItem().endYm('999912');

                            self.currentItem().payCode(self.maxItem().paymentDetailCode);
                            self.currentItem().bonusCode(self.maxItem().bonusDetailCode);
                            //get Payment Name
                            if (self.currentItem().payCode() != '') {
                                service.getAllotLayoutName(self.currentItem().payCode()).done(function(stmtName: string) {
                                    self.currentItem().payName(stmtName);
                                }).fail(function(res) {
                                    self.currentItem().payName('');
                                });
                            } else {
                                self.currentItem().payName('');
                            }
                            //get Bonus Name
                            if (self.currentItem().bonusCode() != '') {
                                service.getAllotLayoutName(self.currentItem().bonusCode()).done(function(stmtName: string) {
                                    self.currentItem().bonusName(stmtName);
                                }).fail(function(res) {
                                    self.currentItem().bonusName('');
                                });
                            } else {
                                self.currentItem().bonusName('');
                            }
                        }else{
                            self.currentItem().histId(addItem.histId());
                            self.currentItem().startYm(returnValue);
                            self.currentItem().endYm('999912');

                            self.currentItem().payCode('');
                            self.currentItem().bonusCode('');
                            self.currentItem().payName('');
                            self.currentItem().bonusName('');
                        }

                        self.itemList([]);
                        self.itemList(items);
                    }
                });
        }

        
        //Open dialog Edit History
        openKDialog() {
            var self = this;
            nts.uk.ui.windows.setShared("endYM",self.currentItem().endYm());    
            nts.uk.ui.windows.setShared('scrType','1');
            nts.uk.ui.windows.setShared('startYM',self.maxDate);
            var current = _.find(self.companyAllots, function(item) { return item.historyId == self.currentItem().histId(); });
            if(current){
                nts.uk.ui.windows.setShared('currentItem',current);
            }
            nts.uk.ui.windows.sub.modal('/view/qmm/020/k/index.xhtml', { title: '明細書の紐ずけ＞履歴編集' }).onClosed(function(): any {
                self.start();
            });
        }
        //Open L Dialog
        openLDialog() {
            alert('2017');
        }
        //Click to button Select Payment
        openPaymentMDialog() {
            var self = this;
            var valueShareMDialog = self.currentItem().startYm();
            //debugger;
            nts.uk.ui.windows.setShared('valMDialog', valueShareMDialog);
            nts.uk.ui.windows.sub.modal('/view/qmm/020/m/index.xhtml', { title: '明細書の選択' }).onClosed(function(): any {
                //get selected code from M dialog
                var stmtCodeSelected = nts.uk.ui.windows.getShared('stmtCodeSelected');

                self.currentItem().payCode(stmtCodeSelected);
                //get Name payment Name
                service.getAllotLayoutName(self.currentItem().payCode()).done(function(stmtName: string) {
                    self.currentItem().payName(stmtName);
                }).fail(function(res) {
                    alert(res);
                });
            });
        }

        //Click to button Select Bonus
        openBonusMDialog() {
            var self = this;
            var valueShareMDialog = self.currentItem().startYm();
            //debugger;
            nts.uk.ui.windows.setShared('valMDialog', valueShareMDialog);
            nts.uk.ui.windows.sub.modal('/view/qmm/020/m/index.xhtml', { title: '明細書の選択' }).onClosed(function(): any {
                //get selected code from M dialog
                var stmtCodeSelected = nts.uk.ui.windows.getShared('stmtCodeSelected');

                self.currentItem().bonusCode(stmtCodeSelected);
                //get Name payment Name
                service.getAllotLayoutName(self.currentItem().bonusCode()).done(function(stmtName: string) {
                    self.currentItem().bonusName(stmtName);
                }).fail(function(res) {
                    alert(res);
                });


            });
        }
    }

    interface IComHistItem {
        histId: string;
        startYm: string;
        endYm: string;
        payCode: string;
        bonusCode: string;
        startEnd?: string;
        payName?: string;
        bonusName?: string;
    }

    class ComHistItem {
        id: string;
        histId: KnockoutObservable<string>;
        startYm: KnockoutObservable<string>;
        endYm: KnockoutObservable<string>;
        payCode: KnockoutObservable<string>;
        bonusCode: KnockoutObservable<string>;
        startEnd: string;
        payName: KnockoutObservable<string>;
        bonusName: KnockoutObservable<string>;
        listSource: Array<any>;
        constructor(param: IComHistItem) {
            let self = this;
            self.id = param.histId;
            self.histId = ko.observable(param.histId);
            self.startYm = ko.observable(param.startYm);
            self.endYm = ko.observable(param.endYm);
            self.payCode = ko.observable(param.payCode);
            self.bonusCode = ko.observable(param.bonusCode);
            self.startEnd = param.startYm + '~' + param.endYm;
            self.payName = ko.observable(param.payName || '');
            self.bonusName = ko.observable(param.bonusName || '');

            self.histId.subscribe(function(newValue) {
                if (typeof newValue != 'string') {
                    return
                }
                var current = _.find(self.listSource, function(item) { return item.historyId == newValue; });
                if (current) {
                    self.startYm(current.startDate);
                    self.endYm(current.endDate);
                    self.payCode(current.paymentDetailCode);
                    self.bonusCode(current.bonusDetailCode);
                    self.startEnd = self.startYm() + '~' + self.endYm();

                    if (current.paymentDetailCode != '') {
                        service.getAllotLayoutName(current.paymentDetailCode).done(function(stmtName: string) {
                            self.payName(stmtName);
                        }).fail(function(res) {
                            self.payName('');
                        });
                    } else {
                        self.payName('');
                    }
                    if (current.bonusDetailCode != '') {
                        service.getAllotLayoutName(current.bonusDetailCode).done(function(stmtName: string) {
                            self.bonusName(stmtName);
                        }).fail(function(res) {
                            self.bonusName('');
                        });
                    } else {
                        self.bonusName('');
                    }

                }
                else { // Them moi
                    var newItem: service.model.CompanyAllotSettingDto = {
                        paymentDetailCode: '',
                        bonusDetailCode: '',
                        startDate: 0,
                        endDate: 0,
                        historyId: self.histId()
                    };
                    self.listSource.push(newItem);
                    self.payName('');
                    self.bonusName('');
                }
            });

            self.payCode.subscribe(function(newValue) {
                //console.log(self.listSource);
                var current = _.find(self.listSource, function(item) { return item.historyId == self.histId(); });
                if (current) {
                    current.paymentDetailCode = newValue;
                }
            });
            self.bonusCode.subscribe(function(newValue) {
                var current = _.find(self.listSource, function(item) { return item.historyId == self.histId(); });
                if (current) {
                    current.bonusDetailCode = newValue;
                }
            });


            self.startYm.subscribe(function(newValue) {

                var current = _.find(self.listSource, function(item) { return item.historyId == self.histId(); });
                if (current) {
                    current.startDate = newValue;
                }
            });

            self.endYm.subscribe(function(newValue) {

                var current = _.find(self.listSource, function(item) { return item.historyId == self.histId(); });
                if (current) {
                    current.endDate = newValue;
                }
            });
        }

        setSource(list: Array<any>) {
            this.listSource = list || [];
        }
    }
}
