module nts.uk.at.view.kmk011.b {
    import blockUI = nts.uk.ui.block;
    export module viewmodel {

        export class ScreenModel {
            columns: KnockoutObservableArray<any>;
            dataSource: KnockoutObservableArray<viewmodel.model.DivergenceTime>;
            currentCode: KnockoutObservable<any>;

            useSet: KnockoutObservableArray<any>;//list value swith button

            //B3_2
            selectUse: KnockoutObservable<any>;//value of B3_2
            enableUse: KnockoutObservable<boolean>; // for define enable for elements behind

            divTimeName: KnockoutObservable<string>;//divergence name B3_4
            timeItemName: KnockoutObservable<string>;//list name KDL021 - B3_7

            //B3_13:swith button, B3_15
            selectSelect: KnockoutObservable<any>;//value B3_13
            enableSelect: KnockoutObservable<boolean>; // for define enable for B3_15 checkbox
            checkErrorSelect: KnockoutObservable<boolean>; //value B3_15

            //B3_17:siwth button, B3_18
            selectInput: KnockoutObservable<any>; //value B3_17
            enableInput: KnockoutObservable<boolean>;
            checkErrorInput: KnockoutObservable<boolean>;


            enable: KnockoutObservable<boolean>;
            divergenceTimeId: KnockoutObservable<number>;
            itemDivergenceTime: KnockoutObservable<viewmodel.model.DivergenceTime>;
            listDivergenceItem: KnockoutObservableArray<viewmodel.model.DivergenceItem>;
            listItemSelected: KnockoutObservableArray<viewmodel.model.DivergenceItem>;
            listItem: KnockoutObservableArray<number>;

            use: KnockoutObservable<string>;
            notUse: KnockoutObservable<string>;
            check: boolean;



            constructor() {
                var self = this;
                self.check = false;
                self.listItem = ko.observableArray([]);
                self.enableUse = ko.observable(false);
                self.enableSelect = ko.observable(false);
                self.enableInput = ko.observable(false);
                self.currentCode = ko.observable(1);
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KMK011_4'), key: 'divTimeId', width: 100 },
                    { headerText: nts.uk.resource.getText('KMK011_5'), key: 'divTimeName', formatter: _.escape, width: 200 }
                ]);
                self.dataSource = ko.observableArray([]);
                self.useSet = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText("Enum_UseAtr_Use") },
                    { code: '0', name: nts.uk.resource.getText("Enum_UseAtr_NotUse") },
                ]);
                self.divTimeName = ko.observable('');
                self.timeItemName = ko.observable('');
                self.checkErrorSelect = ko.observable(true);
                self.enable = ko.observable(true);
                self.divergenceTimeId = ko.observable(null);
                self.itemDivergenceTime = ko.observable(null);
                self.selectUse = ko.observable(0);
                self.selectSelect = ko.observable(0);
                self.selectInput = ko.observable(0);
                self.checkErrorInput = ko.observable(false);
                self.checkErrorSelect = ko.observable(false);
                self.listDivergenceItem = ko.observableArray([]);
                self.listItemSelected = ko.observableArray([]);
                //subscribe currentCode
                self.currentCode.subscribe(function(codeChanged) {
                    self.clearError();
                    if (codeChanged == 0) { return; }
                    self.selectUse(null);
                    self.itemDivergenceTime(self.findDivTime(codeChanged));
                    self.selectUse(self.itemDivergenceTime().divergenceTimeUseSet);
                    self.selectSelect(self.itemDivergenceTime().divergenceReasonSelected);
                    self.selectInput(self.itemDivergenceTime().divergenceReasonInputed);
                    self.divergenceTimeId(self.itemDivergenceTime().divergenceTimeNo);
                    self.divTimeName(self.itemDivergenceTime().divergenceTimeName);
                    self.timeItemName('');
                    self.listItemSelected();
                    service.getItemSelected(self.divergenceTimeId()).done(function(lstItem: Array<model.TimeItemSet>) {
                        if (lstItem == null || lstItem === undefined || lstItem.length == 0) {
                            self.timeItemName();
                            self.listItemSelected([]);
                        } else {
                            var listItemId = [];
                            for (let j = 0; j < lstItem.length; j++) {
                                listItemId[j] = lstItem[j].attendanceId;
                            }
                            service.getNameItemSelected(listItemId).done(function(lstName: Array<model.DivergenceItem>) {
                                self.listItemSelected(lstName);
                                self.findTimeName(self.divergenceTimeId());
                            });
                        }
                    })
                    if (self.itemDivergenceTime().reasonInput) {
                        self.checkErrorInput(true);
                    } else {
                        self.checkErrorInput(false);
                    }
                    if (self.itemDivergenceTime().reasonSelect) {
                        self.checkErrorSelect(true);
                    } else {
                        self.checkErrorSelect(false);
                    }

                    self.check = false;
                    $("#itemname").focus();
                });
                //subscribe selectUse
                self.selectUse.subscribe(function(codeChanged) {
                    if (codeChanged == 1) {
                        self.enableUse(true);
                        if (self.selectSelect() == 1) {
                            self.enableSelect(true);
                        } else {
                            self.enableSelect(false);
                        }
                        if (self.selectInput() == 1) {
                            self.enableInput(true);
                        } else {
                            self.enableInput(false);
                        }
                    } else {
                        self.enableUse(false);
                        self.enableSelect(false);
                        self.enableInput(false);
                    }
                });
                //subscribe selectSel
                self.selectSelect.subscribe(function(codeChanged) {
                    if (codeChanged == 1 && self.selectUse() == 1) {
                        self.enableSelect(true);
                    } else {
                        self.enableSelect(false);
                        if ($('#inpDialog').ntsError("hasError") == true) {
                            $('#inpDialog').ntsError('clear');
                        }
                    }
                });
                //subscribe selectInp
                self.selectInput.subscribe(function(codeChanged) {
                    if (codeChanged == 1 && self.selectUse() == 1) {
                        self.enableInput(true);
                    } else {
                        self.enableInput(false);
                    }
                });
            }

            public start_page(): JQueryPromise<any> {
                var self = this;
                blockUI.invisible();
                var dfd = $.Deferred();
                service.getAllDivTime().done(function(lstDivTime: Array<model.DivergenceTime>) {
                    blockUI.clear();
                    if (lstDivTime === undefined || lstDivTime.length == 0) {
                        self.dataSource();
                    } else {
                        self.currentCode(0);
                        self.dataSource(lstDivTime);
                        let rdivTimeFirst = _.first(lstDivTime);
                        self.currentCode(rdivTimeFirst.divergenceTimeNo);
                    }
                    dfd.resolve();
                })
                return dfd.promise();
            }
            /**
       * find Divergence Time is selected
       */
            findDivTime(value: number): any {
                let self = this;
                var itemModel = null;
                return _.find(self.dataSource(), function(obj: model.DivergenceTime) {
                    return obj.divergenceTimeNo == value;
                })
            }
            //create divergence
            Registration() {
            }
            clearError(): void {
                if ($('.nts-validate').ntsError("hasError") == true) {
                    $('.nts-validate').ntsError('clear');
                }
                if ($('.nts-editor').ntsError("hasError") == true) {
                    $('.nts-input').ntsError('clear');
                }
            }
            //ghep ten hien thi 
            findTimeName(divTimeId: number) {
                var self = this;
                self.timeItemName('');
                var strName = '';
                if (self.listItemSelected().length < 1) {
                    self.timeItemName('');
                } else {
                    strName = self.listItemSelected()[0].attendanceItemName;
                    if (self.listItemSelected().length > 1) {
                        for (let j = 1; j < self.listItemSelected().length; j++) {
                            strName = strName + ' + ' + self.listItemSelected()[j].attendanceItemName;
                        }
                    }
                    self.timeItemName(strName);
                    strName = '';
                }
            }
            //open Diaglog 021
            openDialog021() {

            }

            //open C dialog
            openCDialog() {

            }
        }

        export module model {
            export class DivergenceTime {
                divergenceTimeNo: number;
                divergenceTimeUseSet: number;
                divergenceTimeName: string;
                divergenceType: number;
                reasonInput: boolean;
                reasonSelect: boolean;
                divergenceReasonInputed: boolean;
                divergenceReasonSelected: boolean;
                targetItems: string[];

                constructor(divergenceTimeNo: number, divergenceTimeUseSet: number, divergenceTimeName: string,
                    divergenceType: number, reasonInput: boolean, reasonSelect: boolean,
                    divergenceReasonInputed: boolean, divergenceReasonSelected: boolean, targetItems: string[]
                ) {
                    this.divergenceTimeNo = divergenceTimeNo;
                    this.divergenceTimeUseSet = divergenceTimeUseSet;
                    this.divergenceTimeName = divergenceTimeName;
                    this.divergenceType = divergenceType;
                    this.reasonInput = reasonInput;
                    this.reasonSelect = reasonSelect;
                    this.divergenceReasonInputed = divergenceReasonInputed;
                    this.divergenceReasonSelected = divergenceReasonSelected;
                    this.targetItems = targetItems;
                }

            }

            export class DivergenceItem {
                attendanceItemId: number;
                attendanceItemName: string;
                displayNumber: number;
                useAtr: number;
                attendanceAtr: number;
            }

            export class TimeItemSet {
                divTimeId: number;
                attendanceId: number;
                constructor(divTimeId: number, attendanceId: number) {
                    this.divTimeId = divTimeId;
                    this.attendanceId = attendanceId;
                }
            }
        }
    }
}