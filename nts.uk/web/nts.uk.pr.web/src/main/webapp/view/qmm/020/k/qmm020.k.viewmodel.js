var qmm020;
(function (qmm020) {
    var k;
    (function (k) {
        var viewmodel;
        (function (viewmodel) {
            var option = nts.uk.ui.option;
            var ScreenModel = (function () {
                /**
                 * Init screen model.
                 */
                function ScreenModel() {
                    var self = this;
                    self.startYmHis = ko.observable(null);
                    self.enableYm = ko.observable(false);
                    self.scrType = ko.observable(nts.uk.ui.windows.getShared('scrType'));
                    self.startYM = ko.observable(nts.uk.ui.windows.getShared('startYM'));
                    self.endYM = ko.observable(nts.uk.ui.windows.getShared('endYM'));
                    self.currentItem = ko.observable(nts.uk.ui.windows.getShared('currentItem'));
                    self.selectStartYm = ko.observable(nts.uk.ui.windows.getShared('startYM'));
                    if (self.scrType() === '1') {
                        $('#K_LBL_005').parent().hide();
                        $('#K_LBL_006').hide();
                    }
                    //console.log(option);
                    self.timeEditorOption = ko.mapping.fromJS(new option.TimeEditorOption({ inputFormat: "yearmonth" }));
                    //---radio
                    self.itemsRadio = ko.observableArray([
                        { value: 1, text: '履歴を削除する' },
                        { value: 2, text: '履歴を修正する' }
                    ]);
                    self.isRadioCheck = ko.observable(1);
                }
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    //checkbox change
                    self.isRadioCheck.subscribe(function (newValue) {
                        if (newValue === 2) {
                            self.enableYm(true);
                        }
                        else {
                            self.enableYm(false);
                        }
                    });
                    dfd.resolve();
                    // Return.
                    return dfd.promise();
                };
                //Setting History 
                ScreenModel.prototype.historyProcess = function () {
                    var self = this;
                    debugger;
                    if (parseInt(self.selectStartYm()) > parseInt($('#K_INP_001').val())) {
                        //$('#K_INP_001').ntsError('set',Error.ER023);
                        nts.uk.ui.dialog.confirm("Do you want to say \"Hello World!\"?");
                    }
                    //履歴の編集-削除処理
                    if (self.isRadioCheck() == 1) {
                        k.service.delComAllot(self.currentItem()).done(function () {
                        }).fail(function (res) {
                            alert(res);
                        });
                    }
                    else {
                        self.currentItem();
                        var previousItem = nts.uk.ui.windows.getShared('previousItem');
                        //Update current Item 
                        var startValue = $('#K_INP_001').val().replace('/', '');
                        self.currentItem().startDate = startValue;
                        //previousItem.startDate = 
                        k.service.updateComAllot(self.currentItem()).done(function () {
                        }).fail(function (res) {
                            alert(res);
                        });
                        //Update previous iTem
                        if (previousItem != undefined) {
                            previousItem.endDate = previousYM(startValue);
                            k.service.updateComAllot(previousItem).done(function () {
                            }).fail(function (res) {
                                alert(res);
                            });
                        }
                    }
                    nts.uk.ui.windows.close();
                };
                //close function
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var Error;
            (function (Error) {
                Error[Error["ER023"] = "履歴の期間が重複しています。"] = "ER023";
            })(Error || (Error = {}));
            //Previous Month 
            function previousYM(sYm) {
                var preYm = 0;
                if (sYm.length == 6) {
                    var sYear = sYm.substr(0, 4);
                    var sMonth = sYm.substr(4, 2);
                    //Trong truong hop thang 1 thi thang truoc la thang 12
                    if (sMonth == "01") {
                        preYm = parseInt((parseInt(sYear) - 1).toString() + "12");
                    }
                    else {
                        preYm = parseInt(sYm) - 1;
                    }
                }
                return preYm;
            }
        })(viewmodel = k.viewmodel || (k.viewmodel = {}));
    })(k = qmm020.k || (qmm020.k = {}));
})(qmm020 || (qmm020 = {}));
