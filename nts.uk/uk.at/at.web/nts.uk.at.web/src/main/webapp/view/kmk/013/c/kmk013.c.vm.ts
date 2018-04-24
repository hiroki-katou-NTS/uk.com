module nts.uk.at.view.kmk013.c {
    import blockUI = nts.uk.ui.block;
    export module viewmodel {
        export class ScreenModel {
            selectedValueC23: KnockoutObservable<any>;
            enableC23: KnockoutObservable<boolean>;
            inline: KnockoutObservable<boolean>;
            //c3
            roundingC34: KnockoutObservableArray<any>;
            selectedC34: any;
            roundingC38: KnockoutObservableArray<any>;
            selectedC38: any;
            roundingC312: KnockoutObservableArray<any>;
            selectedC312: any;
            //c4
            roundingC44: KnockoutObservableArray<any>;
            selectedC44: any;
            roundingC48: KnockoutObservableArray<any>;
            selectedC48: any;
            roundingC412: KnockoutObservableArray<any>;
            selectedC412: any;
            //c5
            roundingC54: KnockoutObservableArray<any>;
            selectedC54: any;
            roundingC58: KnockoutObservableArray<any>;
            selectedC58: any;
            roundingC512: KnockoutObservableArray<any>;
            selectedC512: any;
            //c6
            roundingC64: KnockoutObservableArray<any>;
            selectedC64: any;
            roundingC68: KnockoutObservableArray<any>;
            selectedC68: any;
            roundingC612: KnockoutObservableArray<any>;
            selectedC612: any;

            //c8
            itemsC8: KnockoutObservableArray<GridItem>;
            //c9
            itemsC9: KnockoutObservableArray<GridItem>;
            //c10
            itemsC10: KnockoutObservableArray<GridItem>;

            //tab
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;

            //overTimeFrameNo Name
            overTimeFrameListName = [];
            workOffFrameListName = [];
            comboItemsWorkOff = [];
            comboItemOvertime = [];
            comboColumns = [{ prop: 'name', length: 4 }];

            constructor() {
                let self = this;
                self.selectedValueC23 = ko.observable(0);
                self.inline = ko.observable(true);
                self.enableC23 = ko.observable(false);
                //c3
                self.roundingC34 = ko.observableArray([
                    { code: 1, name: nts.uk.resource.getText('KMK013_96') },
                    { code: 0, name: nts.uk.resource.getText('KMK013_97') },
                ]);
                self.selectedC34 = ko.observable(1);
                self.roundingC38 = ko.observableArray([
                    { code: 1, name: nts.uk.resource.getText('KMK013_100') },
                    { code: 0, name: nts.uk.resource.getText('KMK013_101') },
                ]);
                self.selectedC38 = ko.observable(1);
                self.roundingC312 = ko.observableArray([
                    { code: 1, name: nts.uk.resource.getText('KMK013_104') },
                    { code: 0, name: nts.uk.resource.getText('KMK013_105') },
                ]);
                self.selectedC312 = ko.observable(1);
                //c4
                self.roundingC44 = ko.observableArray([
                    { code: 1, name: nts.uk.resource.getText('KMK013_109') },
                    { code: 0, name: nts.uk.resource.getText('KMK013_110') },
                ]);
                self.selectedC44 = ko.observable(1);
                self.roundingC48 = ko.observableArray([
                    { code: 1, name: nts.uk.resource.getText('KMK013_113') },
                    { code: 0, name: nts.uk.resource.getText('KMK013_114') },
                ]);
                self.selectedC48 = ko.observable(1);
                self.roundingC412 = ko.observableArray([
                    { code: 1, name: nts.uk.resource.getText('KMK013_117') },
                    { code: 0, name: nts.uk.resource.getText('KMK013_118') },
                ]);
                self.selectedC412 = ko.observable(1);
                //c5
                self.roundingC54 = ko.observableArray([
                    { code: 1, name: nts.uk.resource.getText('KMK013_122') },
                    { code: 0, name: nts.uk.resource.getText('KMK013_123') },
                ]);
                self.selectedC54 = ko.observable(1);
                self.roundingC58 = ko.observableArray([
                    { code: 1, name: nts.uk.resource.getText('KMK013_126') },
                    { code: 0, name: nts.uk.resource.getText('KMK013_127') },
                ]);
                self.selectedC58 = ko.observable(1);
                self.roundingC512 = ko.observableArray([
                    { code: 1, name: nts.uk.resource.getText('KMK013_130') },
                    { code: 0, name: nts.uk.resource.getText('KMK013_131') },
                ]);
                self.selectedC512 = ko.observable(1);
                //c6
                self.roundingC64 = ko.observableArray([
                    { code: 1, name: nts.uk.resource.getText('KMK013_135') },
                    { code: 0, name: nts.uk.resource.getText('KMK013_136') },
                ]);
                self.selectedC64 = ko.observable(1);
                self.roundingC68 = ko.observableArray([
                    { code: 1, name: nts.uk.resource.getText('KMK013_139') },
                    { code: 0, name: nts.uk.resource.getText('KMK013_140') },
                ]);
                self.selectedC68 = ko.observable(1);
                self.roundingC612 = ko.observableArray([
                    { code: 1, name: nts.uk.resource.getText('KMK013_143') },
                    { code: 0, name: nts.uk.resource.getText('KMK013_144') },
                ]);
                self.selectedC612 = ko.observable(1);
                self.selectedValueC23.subscribe(newValue => {
                    if (newValue == 1) {
                        self.enableC23(true);
                    } else {
                        self.enableC23(false);
                    }
                });
                //tab
                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: nts.uk.resource.getText("KMK013_145"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: nts.uk.resource.getText("KMK013_146"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-3', title: nts.uk.resource.getText("KMK013_147"), content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                ]);
                self.selectedTab = ko.observable('tab-1');
                self.itemsC8 = ko.observableArray([]);
                self.itemsC9 = ko.observableArray([]);
                self.itemsC10 = ko.observableArray([]);
                self.comboItemsWorkOff.push(new ItemModel('0', nts.uk.resource.getText("KMK013_235")));
                self.comboItemOvertime.push(new ItemModel('0', nts.uk.resource.getText("KMK013_235")));
                service.findAllWorkDayOffFrame().done(
                    (arr) => {
                        _.forEach(arr, data => {
                            if (data.useAtr == 1) {
                                self.comboItemsWorkOff.push(new ItemModel(data.workdayoffFrNo, data.workdayoffFrName));
                                self.workOffFrameListName.push(new ItemModel(data.workdayoffFrNo, data.workdayoffFrName));
                            }
                        });
                    }
                );
                service.findAllOvertimeWorkFrame().done(
                    (arr) => {
                        _.forEach(arr, data => {
                            if (data.useAtr == 1) {
                                self.comboItemOvertime.push(new ItemModel(data.overtimeWorkFrNo, data.overtimeWorkFrName));
                                self.overTimeFrameListName.push(new ItemModel(data.overtimeWorkFrNo, data.overtimeWorkFrName));
                            }
                        });
                    }
                );
                
                // catch event press tab, shift+tab to change tab-panel
                self.changeTabPanel();
            }
            
            changeTabPanel(): void {
                let self = this;
                $( document ).keydown(function( event ) {
                    // catch event press tab button
                    if (event.which == 9) {
                        // catch event when stand up manual button, return tab-panel 3
                       if ($( "*:focus" ).attr("class") == 'manual-button') {
                            if (event.shiftKey) {
                                self.selectedTab("tab-3");
                            }    
                       }
                       else {
                           switch(_.toNumber($( "*:focus" ).attr("tabindex"))) {
                               case 16: { 
                                    // jump into tab-panel 1
                                    self.selectedTab("tab-1");
                                    break;
                               }
                               case 19: { 
                                    if (!event.shiftKey) {
                                        // jump into tab-panel 2
                                        if (9 == _.toNumber($("*:focus").closest( "#combo-box" ).attr("posTab"))) {
                                            self.selectedTab("tab-2");
                                        }    
                                    }
                                    break; 
                               }
                               case 20: {
                                    // jump return tab-panel 1
                                    if (event.shiftKey) {
                                        if (0 == _.toNumber($("*:focus").closest( "#combo-box" ).attr("posTab"))) {
                                            self.selectedTab("tab-1");
                                        }
                                    } 
                                    // // jump into tab-panel 3
                                    else {
                                        if (9 == _.toNumber($("*:focus").closest( "#combo-box" ).attr("posTab"))) {
                                            self.selectedTab("tab-3");
                                        }    
                                    } 
                                    break; 
                               }
                               case 21: {
                                   if (event.shiftKey) {
                                       // jump return tab-panel 2
                                       if (0 == _.toNumber($("*:focus").closest( "#combo-box" ).attr("posTab"))) {
                                            self.selectedTab("tab-2");
                                        }
                                   }
                                   break;
                               } 
                               default: { 
                               } 
                            }
                       }
                       
                    }
                });
            }
            
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                $(".fixed-table").ntsFixedTable({ height: 500 });
                self.initData().done(() => {

                    dfd.resolve();
                });

                return dfd.promise();
            }
            saveData(): void {
                let self = this;
                blockUI.invisible();
                let data: any = {};
                data.calcFromZeroTime = self.selectedValueC23();
                if (self.enableC23() == true) {
                    data.legalHd = self.selectedC34();
                    data.nonLegalHd = self.selectedC38();
                    data.nonLegalPublicHd = self.selectedC312();
                    data.weekday1 = self.selectedC44();
                    data.nonLegalHd1 = self.selectedC48();
                    data.nonLegalPublicHd1 = self.selectedC412();
                    data.weekday2 = self.selectedC54();
                    data.legalHd2 = self.selectedC58();
                    data.nonLegalHd2 = self.selectedC512();
                    data.weekday3 = self.selectedC64();
                    data.legalHd3 = self.selectedC68();
                    data.nonLegalPublicHd3 = self.selectedC612();
                } else {
                    data.legalHd = 0;
                    data.nonLegalHd = 0;
                    data.nonLegalPublicHd = 0;
                    data.weekday1 = 0;
                    data.nonLegalHd1 = 0;
                    data.nonLegalPublicHd1 = 0;
                    data.weekday2 = 0;
                    data.legalHd2 = 0;
                    data.nonLegalHd2 = 0;
                    data.weekday3 = 0;
                    data.legalHd3 = 0;
                    data.nonLegalPublicHd3 = 0;
                }
                data.weekdayHoliday = [];
                _.forEach(self.itemsC8(), (obj) => {
                    data.weekdayHoliday.push({
                        overworkFrameNo: obj.code,
                        weekdayNo: (obj.col2().toString()!="0") ? obj.col2() : 0,
                        excessHolidayNo: (obj.col3().toString()!="0") ? obj.col3() : 0,
                        excessSphdNo: (obj.col4().toString()!="0") ? obj.col4() : 0
                    });
                });
                data.overdayHolidayAtten = [];
                _.forEach(self.itemsC9(), (obj) => {
                    data.overdayHolidayAtten.push({
                        holidayWorkFrameNo: obj.code,
                        overWorkNo: (obj.col2().toString()!="0") ? obj.col2() : 0,
                    });
                });
                data.overdayCalcHoliday = [];
                _.forEach(self.itemsC10(), (obj) => {
                    data.overdayCalcHoliday.push({
                        holidayWorkFrameNo: obj.code,
                        calcOverDayEnd: (obj.col2().toString() !="0") ? obj.col2() : 0,
                        statutoryHd: (obj.col3().toString()!="0") ? obj.col3() : 0,
                        excessHd: (obj.col4().toString()!="0") ? obj.col4() : 0
                    });
                });
                service.save(data).done(() => {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    blockUI.clear();
                    $(".radio-btn-left-content").focus();
                }).fail((error) => {
                    console.log(error);
                    blockUI.clear();
                    $(".radio-btn-left-content").focus();
                });
            }
            initData(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();
                service.findByCompanyId().done(arr => {
                    let data = arr[0];
                    //0時跨ぎ計算を行う
                    self.selectedValueC23(data.calcFromZeroTime);
                    //計算するしない設定（平日）.法定内休日
                    self.selectedC34(data.legalHd);
                    //計算するしない設定（平日）.法定外休日
                    self.selectedC38(data.nonLegalHd);
                    //計算するしない設定（平日）.法定外祝日
                    self.selectedC312(data.nonLegalPublicHd);
                    //計算するしない設定（法定内休日）.平日 
                    self.selectedC44(data.weekday1);
                    //計算するしない設定（法定内休日）.法定外休日
                    self.selectedC48(data.nonLegalHd1);
                    //計算するしない設定（法定内休日）.法定外祝日
                    self.selectedC412(data.nonLegalPublicHd1);
                    //計算するしない設定（法定外休日）.平日
                    self.selectedC54(data.weekday2);
                    //計算するしない設定（法定外休日）.法定内休日
                    self.selectedC58(data.legalHd2);
                    //計算するしない設定（法定外休日）.法定外祝日
                    self.selectedC512(data.nonLegalHd2);
                    //計算するしない設定（法定外祝日）.平日
                    self.selectedC64(data.weekday3);
                    //計算するしない設定（法定外祝日）.法定内休日
                    self.selectedC68(data.legalHd3);
                    //計算するしない設定（法定外祝日）.法定外休日
                    self.selectedC612(data.nonLegalPublicHd3);
                    
                        _.forEach(self.overTimeFrameListName, ot => {
                            let obj = _.find(data.weekdayHoliday, ['overworkFrameNo',ot.code]);
                            if(obj){
                                self.itemsC8.push(new GridItem(obj.overworkFrameNo,
                                    validate(obj.weekdayNo, self.comboItemsWorkOff),
                                    nts.uk.resource.getText('KMK013_152'),
                                    ot.name,
                                    validate(obj.excessHolidayNo, self.comboItemsWorkOff),
                                    validate(obj.excessSphdNo, self.comboItemsWorkOff)));
                            }else{
                                self.itemsC8.push(new GridItem(ot.code,0,nts.uk.resource.getText('KMK013_152'),ot.name,0,0));    
                            }
                            
                        });
                        
                    _.forEach(self.workOffFrameListName, wof => {
                        let obj = _.find(data.overdayHolidayAtten, ['holidayWorkFrameNo',wof.code]);
                        if (obj) {
                            self.itemsC9.push(new GridItem(obj.holidayWorkFrameNo,
                                validate(obj.overWorkNo, self.comboItemOvertime),
                                nts.uk.resource.getText('KMK013_157'),
                                wof.name));
                        }else{
                            self.itemsC9.push(new GridItem(wof.code,0,nts.uk.resource.getText('KMK013_157'),wof.name));    
                        }
                    });
                    _.forEach(self.workOffFrameListName, wof => {
                        let obj = _.find(data.overdayCalcHoliday, ['holidayWorkFrameNo', wof.code]);
                        if (obj) {
                            self.itemsC10.push(new GridItem(obj.holidayWorkFrameNo,
                                validate(obj.calcOverDayEnd, self.comboItemsWorkOff),
                                nts.uk.resource.getText('KMK013_163'),
                                wof.name,
                                validate(obj.statutoryHd, self.comboItemsWorkOff),
                                validate(obj.excessHd, self.comboItemsWorkOff)));
                        } else {
                            self.itemsC10.push(new GridItem(wof.code, 0, nts.uk.resource.getText('KMK013_163'), wof.name, 0, 0));
                        }
                    });
                    dfd.resolve();
                });

                return dfd.promise();
            }




        }
        function getName(arr, key) {
            let obj: ItemModel = _.find(arr, ['code', key]);
            if (obj) {
                return obj.name;
            } else {
                return arr[0].name;
            };

        }

        /**
         * validate value . If value not include item list, set value = default
         */
        function validate(value, itemList) {
            var index = _.findIndex(itemList, ['code', value]);
            if (index != -1) {
                return value;
            } else {
                return 0;
            }
        }
        class ItemModel {
            code: number;
            name: string;
            constructor(code, name) {
                let self = this;
                self.code = code;
                self.name = name;
            }
        }
        class GridItem {
            code: number;
            col1: string;
            col2: KnockoutObservable<number>;
            col3?: KnockoutObservable<number>;
            col4?: KnockoutObservable<number>;
            textBefore?: string;
            textAfter?: string;
            constructor(code: number, col2: number, textBefore?: string, textAfter?: string, col3?: number, col4?: number) {
                this.code = code;
                this.col1 = (code > 9) ? textBefore + code + '  ' + textAfter : textBefore + code + '   ' + textAfter;
                this.col2 = ko.observable(col2);
                this.col3 = ko.observable(col3);
                this.col4 = ko.observable(col4);
            }
        }
    }
}