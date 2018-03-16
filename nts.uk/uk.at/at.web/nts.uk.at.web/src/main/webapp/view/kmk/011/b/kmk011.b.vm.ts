module nts.uk.at.view.kmk011.b {
    import blockUI = nts.uk.ui.block;
    import setShared = nts.uk.ui.windows.setShared;
    import modal = nts.uk.ui.windows.sub.modal;
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

            divergenceTimeName: KnockoutObservable<string>;
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
                    { headerText: nts.uk.resource.getText('KMK011_4'), key: 'divergenceTimeNo', width: 100 },
                    { headerText: nts.uk.resource.getText('KMK011_5'), key: 'divergenceTimeName', formatter: _.escape, width: 200 }
                ]);
                self.dataSource = ko.observableArray([]);
                self.useSet = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText("Enum_UseAtr_Use") },
                    { code: '0', name: nts.uk.resource.getText("Enum_UseAtr_NotUse") },
                ]);

                self.selectUse = ko.observable(0);
                self.divergenceTimeName = ko.observable('');
                self.divTimeName = ko.observable('');
                self.timeItemName = ko.observable('');
                self.checkErrorSelect = ko.observable(true);
                self.enable = ko.observable(true);
                self.divergenceTimeId = ko.observable(null);
                self.itemDivergenceTime = ko.observable(null);

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
                    
                    self.itemDivergenceTime(self.findDivergenceTime(codeChanged));
                    console.log(self.itemDivergenceTime().divergenceTimeName);
                    
                    self.selectUse(self.itemDivergenceTime().divergenceTimeUseSet);
                    self.selectSelect(self.itemDivergenceTime().divergenceReasonSelected);//temp
                    self.selectInput(self.itemDivergenceTime().divergenceReasonInputed);
                    self.divergenceTimeId(self.itemDivergenceTime().divergenceTimeNo);
                    self.divTimeName(self.itemDivergenceTime().divergenceTimeName);

                    self.timeItemName('');

                    //set name KDL selected for divergenceTime
                    self.listItemSelected();
                    var listItemAttendanceId = self.itemDivergenceTime().targetItems; //list attendanceId
                    if (listItemAttendanceId == null || listItemAttendanceId === undefined || listItemAttendanceId.length == 0) {
                        self.timeItemName();
                        self.listItemSelected([]);
                    } else {
                        service.getNameItemSelected(listItemAttendanceId).done(function(lstName: Array<model.DivergenceItem>) {
                            self.listItemSelected(lstName);
                            self.findTimeName(self.divergenceTimeId());
                        });
                    }

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
                    //
                    //                    self.check = false;
                    //                    $("#itemname").focus();
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
                
                service.getAllDivergenceTime().done(function(lstDivTime: Array<model.DivergenceTime>) {
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
            findDivergenceTime(divergenceTimeNo: number):any {
                var self = this;
                return service.findDivergenceTime(divergenceTimeNo).done(function(itemDivergenceTime: model.DivergenceTime) {
                   
                    return itemDivergenceTime;
                });
            }
            //create divergence
            Registration() {
                var self = this;
                blockUI.invisible();
                $('.nts-input').trigger("validate");
                _.defer(() => {
                    if (nts.uk.ui.errors.hasError() === false) {
                        var dfd = $.Deferred();
                        if (self.divergenceTimeId() == null) {
                            return;
                        }
                        //set 4 gia tri boolean
                        //                        var select = new model.SelectSet(self.selectSel(), self.convert(self.checkErrSelect()));
                        //                        var input = new model.SelectSet(self.selelf.checkErrInput()));

                        //                        var divTime = new model.DivergenceTime(self.divTimeId(), 0, self.divTimeName(), self.selectUse(), self.alarmTime(), self.errTime(), select, input);
                        var divergenceType = self.divergenceTimeId();  //temp
                        var targetItems: number[] = null; //temp
                        var listIdSelect = [];
                        for (let i = 0; i < self.listItemSelected().length; i++) {
                            listIdSelect[i] = self.listItemSelected()[i].attendanceItemId;
                        }
                        var divergenceTime = new model.DivergenceTime(self.divergenceTimeId(), self.selectUse(), self.divergenceTimeName(),
                            divergenceType, self.checkErrorInput(), self.checkErrorSelect(),
                            self.selectInput(), self.selectSelect(), targetItems);


                        var listAdd = new Array<model.TimeItemSet>();
                        if (self.listItem() != null) {
                            for (let k = 0; k < self.listItem().length; k++) {
                                let add = new model.TimeItemSet(self.divergenceTimeId(), self.listItem()[k]);
                                listAdd.push(add);
                            }
                        }

                        //                        var Object = new model.ObjectDivergence(divTime, listAdd, self.timeItemName());
                        service.updateDivTime(divergenceTime).done(function() {
                            self.getAllDivTimeNew();
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                            self.listItem([]);
                            self.check = false;
                            $("#itemname").focus();
                            nts.uk.ui.block.clear();

                        }).fail(function(error) {
                            nts.uk.ui.block.clear();
                            self.check = true;
                            if (error.messageId == 'Msg_82') {
                                $('#inpAlarmTime').ntsError('set', error);
                            }
                            if (error.messageId == 'Msg_32') {
                                $('#inpDialog').ntsError('set', error);
                            } else {
                                $('#inpName').ntsError(error.message);
                            }
                        })
                        dfd.resolve();
                        return dfd.promise();
                    }
                })
                nts.uk.ui.block.clear();
            }
            clearError(): void {
                if ($('.nts-validate').ntsError("hasError") == true) {
                    $('.nts-validate').ntsError('clear');
                }
                if ($('.nts-editor').ntsError("hasError") == true) {
                    $('.nts-input').ntsError('clear');
                }
            }
            //get all divergence time new
            getAllDivTimeNew() {
                var self = this;
                var dfd = $.Deferred<any>();
                self.dataSource();
                service.getAllDivergenceTime().done(function(lstDivTime: Array<model.DivergenceTime>) {
                    self.currentCode('');
                    self.dataSource(lstDivTime);
                    self.currentCode(self.divergenceTimeId());
                    dfd.resolve();
                }).fail(function(error) {
                    nts.uk.ui.dialog.alert(error.message);
                })
                dfd.resolve();
                return dfd.promise();
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
                var self = this;
                nts.uk.ui.block.grayout();
                service.getAllAttendanceItem(1).done(function(lstAllItem: Array<model.AttendanceType>) {
                    var listAllId = [];
                    for (let j = 0; j < lstAllItem.length; j++) {
                        listAllId[j] = lstAllItem[j].attendanceItemId;
                    }
                    var listIdSelect = [];
                    for (let i = 0; i < self.listItemSelected().length; i++) {
                        listIdSelect[i] = self.listItemSelected()[i].attendanceItemId;
                    }
                    setShared('AllAttendanceObj', listAllId, true);
                    setShared('SelectedAttendanceId', listIdSelect, true);
                    setShared('Multiple', true, true);
                    modal('../../../kdl/021/a/index.xhtml', { title: '乖離時間の登録＞対象項目', }).onClosed(function(): any {
                        blockUI.clear();
                        $("#itemname").focus();
                        var list = nts.uk.ui.windows.getShared('selectedChildAttendace');
                        if (list == null || list === undefined) return;
                        self.listItem(list);
                        var listUpdate = new Array<model.DivergenceTimeItem>();
                        for (let i = 0; i < list.length; i++) {
                            let itemUpdate = new model.DivergenceTimeItem(self.divergenceTimeId(), list[i]);
                            listUpdate.push(itemUpdate);
                        }
                        service.getNameItemSelected(list).done(function(lstName: Array<model.DivergenceItem>) {
                            self.listItemSelected(lstName);
                            self.findTimeName(self.divergenceTimeId());
                            if (self.timeItemName() != '') {
                                if ($('#inpName').ntsError("hasError") == true) {
                                    $('#inpName').ntsError('clear');
                                }
                            }
                        })
                    })
                });
            }

            //open C dialog
            openCDialog() {
                var self = this;
                blockUI.grayout();
                setShared('KMK011_divTimeId', self.divergenceTimeId(), true);
                nts.uk.ui.windows.sub.modal('/view/kmk/011/c/index.xhtml', { title: '選択肢の設定', }).onClosed(function(): any {
                    blockUI.clear();
                    if ($('#inpDialog').ntsError("hasError") == true) {
                        $('#inpDialog').ntsError('clear');
                    }
                    $("#itemname").focus();
                });
            }
        }

        export module model {
            export class Demo {
                attendanceId: number;
            }
            export class DivergenceTime {
                divergenceTimeNo: number;
                divergenceTimeUseSet: number;
                divergenceTimeName: string;
                divergenceType: number;
                reasonInput: boolean;
                reasonSelect: boolean;
                divergenceReasonInputed: boolean;
                divergenceReasonSelected: boolean;
                targetItems: number[];

                constructor(divergenceTimeNo: number, divergenceTimeUseSet: number, divergenceTimeName: string,
                    divergenceType: number, reasonInput: boolean, reasonSelect: boolean,
                    divergenceReasonInputed: boolean, divergenceReasonSelected: boolean, targetItems: number[]
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
            export class AttendanceType {
                attendanceItemId: number;
            }
            export class DivergenceTimeItem {
                divTimeId: number;
                attendanceId: number;
                constructor(divTimeId: number, attendanceId: number) {
                    this.divTimeId = divTimeId;
                    this.attendanceId = attendanceId;
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