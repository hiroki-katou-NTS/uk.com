var cmm013;
(function (cmm013) {
    var d;
    (function (d) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.inp_003 = ko.observable(null);
                    self.inp_003_enable = ko.observable(true);
                    self.startDateNew = ko.observable('');
                    self.startDateUpdate = ko.observable('');
                    self.endDateUpdate = ko.observable('');
                    self.startDateLast = ko.observable(null);
                    self.historyIdLast = ko.observable(null);
                    self.length = ko.observable(0);
                    self.startDateAddNew = ko.observable("");
                    self.startDateUpdate = ko.observable(null);
                    self.endDateUpdate = ko.observable(null);
                    self.historyIdUpdate = ko.observable(null);
                    self.startDateUpdateNew = ko.observable(null);
                    self.startDatePre = ko.observable(null);
                    self.jobHistory = ko.observable(null);
                    self.selectedCode = ko.observable(null);
                    self.checkDelete = ko.observable(null);
                    self.listbox = ko.observableArray([]);
                    self.itemList = ko.observableArray([
                        new BoxModel(1, '履歴を削除する '),
                        new BoxModel(2, '履歴を修正する')
                    ]);
                    self.selectedId = ko.observable(2);
                    self.enable = ko.observable(true);
                    self.selectedId.subscribe((function (codeChanged) {
                        if (codeChanged == 1) {
                            self.inp_003_enable(false);
                        }
                        else {
                            self.inp_003_enable(true);
                        }
                    }));
                }
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.startDateUpdate(nts.uk.ui.windows.getShared('startUpdate'));
                    self.endDateUpdate(nts.uk.ui.windows.getShared('endUpdate'));
                    self.inp_003(self.startDateUpdate());
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.positionHis = function () {
                    var self = this;
                    //            if(self.selectedId() == 2){
                    //                if(self.inp_003() >= self.endDateUpdate()||self.inp_003()<=self.startDateUpdate()){
                    //                    // Update
                    //                    alert("ERRRR");    
                    //                    return;
                    //                }
                    //                else {
                    //                    nts.uk.ui.windows.setShared('startUpdateNew',self.inp_003());
                    //                    nts.uk.ui.windows.setShared('check_d',self.selectedId());
                    //                    nts.uk.ui.windows.close();
                    //                }
                    //            }else{
                    //                self.inp_003(null);
                    nts.uk.ui.windows.setShared('startUpdateNew', self.inp_003());
                    nts.uk.ui.windows.setShared('check_d', self.selectedId());
                    nts.uk.ui.windows.close();
                    //            }
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var BoxModel = (function () {
                function BoxModel(id, name) {
                    var self = this;
                    self.id = id;
                    self.name = name;
                }
                return BoxModel;
            }());
            var model;
            (function (model) {
                var historyDto = (function () {
                    function historyDto(startDate, endDate, historyId) {
                        this.startDate = startDate;
                        this.endDate = endDate;
                        this.historyId = historyId;
                    }
                    return historyDto;
                }());
                model.historyDto = historyDto;
                var ListHistoryDto = (function () {
                    function ListHistoryDto(companyCode, startDate, endDate, historyId) {
                        var self = this;
                        self.companyCode = companyCode;
                        self.startDate = startDate;
                        self.endDate = endDate;
                        self.historyId = historyId;
                    }
                    return ListHistoryDto;
                }());
                model.ListHistoryDto = ListHistoryDto;
            })(model = viewmodel.model || (viewmodel.model = {}));
        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
    })(d = cmm013.d || (cmm013.d = {}));
})(cmm013 || (cmm013 = {}));
