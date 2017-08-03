module nts.uk.at.view.ksm003.a {
    import PatternCalendarDto = service.model.DailyPatternDetailDto;
    import DailyPatternVal = service.model.DailyPatternValDto;
    export module viewmodel {
        export class ScreenModel {
            //A_label_x
            columns: KnockoutObservableArray<any>;
            columnsWork: KnockoutObservableArray<any>;
            dailyPatternVal: KnockoutObservableArray<DailyPatternVal>;
            itemPatternCal: KnockoutObservable<model.Item>;
            lstPattern: KnockoutObservableArray<model.Item>;
            currentCode: KnockoutObservable<string>;
            enableCode: KnockoutObservable<boolean>;
            enableDel: KnockoutObservable<boolean>;
            checkModel: KnockoutObservable<boolean>;
            patternCode: KnockoutObservable<string>;
            patternName: KnockoutObservable<string>;
            worktypeInfoWorkDays: KnockoutObservable<string>;
            worktimeInfoWorkDays: KnockoutObservable<string>;
            
            // Chua dung den (Check )
            workTypeSetCd: KnockoutObservable<string>;
            workingHoursCd: KnockoutObservable<string>;
            constructor() {

                var self = this;
                self.currentCode = ko.observable('');
                self.dailyPatternVal = ko.observableArray([{"dispOrder": 1}, {"dispOrder": 2}, {"dispOrder": 3}, {"dispOrder": 4}, {"dispOrder": 5}, {"dispOrder": 6}, {"dispOrder": 7}, {"dispOrder": 8}, {"dispOrder": 9}, {"dispOrder": 10}]);
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KSM003_26'), key: 'patternCode', formatter: _.escape, width: 100 },
                    { headerText: nts.uk.resource.getText('KSM003_27'), key: 'patternName', formatter: _.escape, width: 200 }
                ]);
                self.columnsWork = ko.observableArray([
                    { headerText: "", key: 'dispOrder', template: "<button id=\"button-event-${dispOrder}\" style=\" margin: 10px 10px 10px 23px;\" data-dispOrderData='${dispOrder}' >" + nts.uk.resource.getText('KSM003_34') + " </button>", formatter: _.escape, width: 100 },
                    { headerText: nts.uk.resource.getText('KSM003_30'), key: 'workTypeSetCd', formatter: _.escape, width: 180 },
                    { headerText: nts.uk.resource.getText('KSM003_31'), key: 'workingHoursCd', formatter: _.escape, width: 180 },
                    { headerText: nts.uk.resource.getText('KSM003_32'), template: "<input type=\"text\" style=\"width: 80px;\" value=\"${days}\" data-id=\"${days}\" >" + nts.uk.resource.getText('KSM003_33') + " </input>", key: 'days', formatter: _.escape, width: 60 }
                    //                   <input class=\"nts-input\" data-bind=\"ntsTextEditor: { name: '#[KSM003_32]', value: \"${days}\" , constraint: 'DiverdenceReasonCode', enable:enableCode, required: true}\" />
                ]);
                //              template: "<input type=\"text\" style=\"width: 100px;\" value=\"${days}\" data-id=\"${days}\" >" + nts.uk.resource.getText('KSM003_33') + " </input>"
                //                 <input  class="nts-input" id="inpCode" data-bind="ntsTextEditor: { name: '#[KMK011_22]', value: patternCode}" />
//                template: "<input type='button' onclick='deleteRow(${ProductID})' value='Delete' class='delete-button'/>
                self.lstPattern = ko.observableArray([]);
                self.enableCode = ko.observable(false);
                self.itemPatternCal = ko.observable(null);

                self.enableDel = ko.observable(true);
                self.checkModel = ko.observable(true);
                self.patternCode = ko.observable(null);
                self.patternName = ko.observable('');
                self.workTypeSetCd = ko.observable('');
                self.workingHoursCd = ko.observable('');
                self.worktypeInfoWorkDays = ko.observable('');
                self.worktimeInfoWorkDays = ko.observable('');

                //subscribe currentCode
                self.currentCode.subscribe(function(codeChanged: string) {

//                    console.log(codeChanged);
                    self.clearError();
                    self.itemPatternCal(self.findItemPatternCal(codeChanged));
                    if (self.itemPatternCal() === undefined || self.itemPatternCal() == null) {
                        return;
                    }
                    self.enableCode(false);
                    self.patternCode(self.itemPatternCal().patternCode);
                    self.patternName(self.itemPatternCal().patternName);
                    self.enableDel(true);
                    self.getPatternValByPatternCd(codeChanged);
                    if (self.dailyPatternVal() === undefined || self.dailyPatternVal() == null) {
                        return;
                    }
                    $("#inpPattern").focus();
                });
                
                // Create Customs handle For event rened nts grid.
                (<any>ko.bindingHandlers).rended = {
                    update: function(element: any,
                        valueAccessor: () => any,
                        allBindings: KnockoutAllBindingsAccessor,
                        viewModel: ScreenModel,
                        bindingContext: KnockoutBindingContext) {
                        var code = valueAccessor();
                        viewModel.dailyPatternVal().forEach(item => {
                            $('#button-event-' + item.dispOrder).on('click', function() {
                                let id = $(this).data('disporderdata');
                                self.openDialogWorkDays(id);
                            });
                        });
                    }
                };

            }


            /**
             * start page
             * get all pattern 
             */
            public startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.getAllPattCalender().done(function(dataRes: Array<model.Item>) {
//                    console.log(dataRes);
                    self.lstPattern(dataRes);
                    nts.uk.ui.block.clear();
                    self.currentCode(null);
                    if (dataRes === undefined || dataRes.length == 0) {
                        self.dailyPatternVal([]);
                        self.enableCode(true);
                        self.checkModel(false);
                        self.enableDel(false);
                    } else {
                        self.lstPattern(dataRes);
                        self.refreshData();
                        self.checkModel(true);
                    }
                    dfd.resolve();
                })
                return dfd.promise();
            }

                
             /**
             * open dialog KDL003 by Work Days
             */
            public openDialogWorkDays(dispOrder :number): void {
                var self = this;
                nts.uk.ui.windows.setShared('parentCodes', {
                    selectWorkTypeCode: self.dailyPatternVal()[dispOrder -1].workTypeSetCd,
                    selectSiftCode: self.dailyPatternVal()[dispOrder -1].workingHoursCd
                }, true);

                nts.uk.ui.windows.sub.modal("/view/kdl/003/a/index.xhtml").onClosed(function(){
                    var childData = nts.uk.ui.windows.getShared('childData');
                     self.dailyPatternVal()[dispOrder -1].workTypeSetCd = childData.selectedWorkTimeCode;
                    if (childData.selectedWorkTimeCode) {
                        self.worktypeInfoWorkDays(childData.selectedWorkTimeCode + ' ' + childData.selectedWorkTimeName);
                    }
                    else {
                        self.worktypeInfoWorkDays(nts.uk.resource.getText("KSM005_43"));
                    }
                    self.dailyPatternVal()[dispOrder -1].workingHoursCd = childData.selectedSiftCode;
                    if (childData.selectedSiftCode) {
                        self.worktimeInfoWorkDays(childData.selectedSiftCode + ' ' + childData.selectedSiftName);
                    } else {
                        self.worktimeInfoWorkDays(nts.uk.resource.getText("KSM005_43"));
                    } 
                    
                    self.dailyPatternVal.valueHasMutated();
                });
            }
            
            
            

            /**
            * find item Pattern Cal is selected
            */
            private findItemPatternCal(value: string): any {
                let self = this;
                return _.find(self.lstPattern(), function(obj: model.Item) {
                    return obj.patternCode == value;
                })
            }

            public refreshData(): void {
                var self = this;
                self.dailyPatternVal([{"dispOrder": 1}, {"dispOrder": 2}, {"dispOrder": 3}, {"dispOrder": 4}, {"dispOrder": 5}, {"dispOrder": 6}, {"dispOrder": 7}, {"dispOrder": 8}, {"dispOrder": 9}, {"dispOrder": 10}]);
                self.patternCode(null);
                self.patternName("");
                self.enableCode(true);
                self.clearError();
                self.enableDel(false);
                self.currentCode(null);
                $("#inpCode").focus();

            }


            private clearError(): void {
                if ($('.nts-editor').ntsError("hasError")) {
                    $('.nts-input').ntsError('clear');
                }
            }

            public registrationDivReason(): void {
                var self = this;
                if (self.patternCode == null || self.patternCode === undefined || self.patternCode() == "") {
                    nts.uk.ui.block.invisible();
                    $('.nts-input').trigger("validate");
                    let patternFirst = _.first(self.lstPattern());
                    self.currentCode(patternFirst.patternCode);
                    _.defer(() => {
                        if (!$('.nts-editor').ntsError("hasError")) {
                            nts.uk.ui.block.clear();
                            return;
                        }
                    });
                } else {
                    nts.uk.ui.block.invisible();
                    $('.nts-input').trigger("validate");
                    _.defer(() => {
                        if (!$('.nts-editor').ntsError("hasError")) {
                            if (self.enableCode() == false) {
                                self.addDailyPattern();
                            } else
                                if (self.enableCode() == true) {
                                    self.addDailyPattern();
                                }
                        }
                    });
                }
            }


            //        addDailyPattern
            private addDailyPattern() {
                var self = this;
                var dfd = $.Deferred();
                let command: any = {};
                service.addPattCalender(self.collectData()).done(function() {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    self.getAllDivReasonNew();
                    $("#inpPattern").focus();
                    nts.uk.ui.block.clear();
                }).fail(function(error) {
                    nts.uk.ui.block.clear();
                    $('#inpCode').ntsError('set', error);
                });
            }

            public openDialogWorking(): void {
                let self = this;
                nts.uk.ui.windows.setShared('patternCode', self.patternCode());
                nts.uk.ui.windows.sub.modal('/view/kdl/023/a/index.xhtml');
            }

            public openDialogKDL003(): void {
                let self = this;
                //                nts.uk.ui.windows.setShared('patternCode', self.patternCode());
                nts.uk.ui.windows.sub.modal('/view/kdl/003/a/index.xhtml');
            }

         
            
            
            //get all divergence reason new
            private getAllDivReasonNew(): void {
                var self = this;
                var dfd = $.Deferred<any>();
                self.lstPattern();
                service.getAllPattCalender().done(function(dataRes: Array<model.Item>) {
                    self.currentCode('');
                    self.lstPattern(dataRes);
                    self.enableCode(false);
                    self.currentCode(self.patternCode());
                    dfd.resolve();
                    $("#inpPattern").focus();
                }).fail(function(error) {
                    nts.uk.ui.dialog.alert(error.message);
                })

                dfd.resolve();
                return dfd.promise();
            }

            //delete divergence reason
            public deleteDivReason(): void {
                nts.uk.ui.block.invisible();
                var self = this;
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    let patternCal = self.itemPatternCal();
                    service.deleteDailyPattern(patternCal.patternCode).done(function() {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                            nts.uk.ui.block.clear();
                            self.getDivReasonList_afterDelete();
                            $("#inpCode").focus();
                        });
                    });
                }).ifNo(function() {
                    nts.uk.ui.block.clear();
                    return;
                })
            }

            // get list pattern after Delete 1 pattern
            private getDivReasonList_afterDelete(): any {
                var self = this;
                var dfd = $.Deferred<any>();
                self.dailyPatternVal();
                self.getAllDivReasonNew();
                $("#inpPattern").focus();
                nts.uk.ui.block.clear();
                dfd.resolve();
                return dfd.promise();
            }



            //        closeDialog
            private closeDialog(): void {
                nts.uk.ui.windows.close();
            }

            public collectData(): PatternCalendarDto {
                var self = this;
                self.dailyPatternVal([
                    {
                        "cid": null,
                        "patternCode": "L9",
                        "dispOrder": 1,
                        "workTypeSetCd": "020",
                        "workingHoursCd": "020",
                        "days": 20
                    },
                    {
                        "cid": null,
                        "patternCode": "L9",
                        "dispOrder": 2,
                        "workTypeSetCd": "120",
                        "workingHoursCd": "120",
                        "days": 21
                    },
                    {
                        "cid": null,
                        "patternCode": "L9",
                        "dispOrder": 3,
                        "workTypeSetCd": "220",
                        "workingHoursCd": "220",
                        "days": 22
                    },
                    {
                        "cid": null,
                        "patternCode": "L9",
                        "dispOrder": 4,
                        "workTypeSetCd": "320",
                        "workingHoursCd": "320",
                        "days": 23
                    },
                    {
                        "cid": null,
                        "patternCode": "L9",
                        "dispOrder": 5,
                        "workTypeSetCd": "420",
                        "workingHoursCd": "420",
                        "days": 24
                    },
                    {
                        "cid": null,
                        "patternCode": "L9",
                        "dispOrder": 6,
                        "workTypeSetCd": "520",
                        "workingHoursCd": "520",
                        "days": 25
                    },
                    {
                        "cid": null,
                        "patternCode": "L9",
                        "dispOrder": 7,
                        "workTypeSetCd": "620",
                        "workingHoursCd": "620",
                        "days": 26
                    },
                    {
                        "cid": null,
                        "patternCode": "L9",
                        "dispOrder": 8,
                        "workTypeSetCd": "720",
                        "workingHoursCd": "720",
                        "days": 27
                    },
                    {
                        "cid": null,
                        "patternCode": "L9",
                        "dispOrder": 9,
                        "workTypeSetCd": "820",
                        "workingHoursCd": "820",
                        "days": 28
                    },
                    {
                        "cid": null,
                        "patternCode": "L9",
                        "dispOrder": 10,
                        "workTypeSetCd": "920",
                        "workingHoursCd": "920",
                        "days": 29
                    }

                ]);

                var dto: PatternCalendarDto;
                dto = {
                    patternCode: self.patternCode(),
                    patternName: self.patternName(),
                    listDailyPatternVal: self.dailyPatternVal()
                };
//                console.log(dto);
                return dto;
            }

            /**
             * call service get by pattern code
             */

            public getPatternValByPatternCd(patternCode: string): void {
                var self = this;
                service.getPatternValByPatternCd(patternCode).done(function(dataRes) {
//                    console.log(dataRes);
                    self.dailyPatternVal([]);
                    //                    if (dataRes === undefined || dataRes == null) {
                    //                        return;
                    //                    } else {
                    self.dailyPatternVal([{"dispOrder": 1}, {"dispOrder": 2}, {"dispOrder": 3}, {"dispOrder": 4}, {"dispOrder": 5}, {"dispOrder": 6}, {"dispOrder": 7}, {"dispOrder": 8}, {"dispOrder": 9}, {"dispOrder": 10}]);
                    self.dailyPatternVal(self.merge(dataRes, self.dailyPatternVal()));
                    self.patternCode(patternCode);
                    //                    }
                });
            }

            public merge(dataNew: DailyPatternVal[], data: DailyPatternVal[]) {
                var dataUpdate: DailyPatternVal[] = [];
                for (var i= 0; i < 10; i++) {
                    var item = data[i];
                    if (dataNew.listDailyPatternVal[i] === undefined || dataNew.listDailyPatternVal[i] == null) {
                        dataUpdate.push(item);
                    }
                    else {
                        dataUpdate.push(dataNew.listDailyPatternVal[i]);
                    }
                }
                return dataUpdate;
            }

            
            
            
        }
        export module model {
            export class Item {
                patternCode: string;

                patternName: string;

                itemDailyPatternVal: DailyPatternVal[];

                constructor(patternCode: string, patternName: string, itemDailyPatternVal: DailyPatternVal[]) {
                    this.patternCode = patternCode;
                    this.patternName = patternName;
                    this.itemDailyPatternVal = itemDailyPatternVal;
                }
            }

        }
    }
}