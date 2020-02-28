module nts.uk.hr.view.jhc002.a.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import NtsGridListColumn = nts.uk.ui.NtsGridListColumn;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {

        screenMode: KnockoutObservable<any>;
        masterId: KnockoutObservable<string>;
        histList: KnockoutObservableArray<any>;
        selectedHistId: KnockoutObservable<any>;
        listCareerType: KnockoutObservableArray<any>;

        //careerpart
        listCareer: KnockoutObservableArray<any>;
        careerClass: KnockoutObservableArray<any>;
        careerType: KnockoutObservableArray<any>;
        maxClassLevel: KnockoutObservable<number>;
        checkStartFromBScreen: boolean;
        dataFromBScreen: any;
        latestCareerPathHist: KnockoutObservable<any>;
        checkUpdateEnableControl: boolean;
        historyModeUpdate: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            self.dataFromBScreen = getShared("DataShareCareerToAScreen") || { undefined };

            // history component            
            self.height = ko.observable("200px");
            self.labelDistance = ko.observable("30px");
            self.screenMode = ko.observable(1);
            self.masterId = ko.observable("");
            self.histList = ko.observableArray([]);
            self.selectedHistId = ko.observable('');
            self.pathGet = ko.observable(`careermgmt/careerpath/getDateHistoryItem`);
            self.pathAdd = ko.observable(`careermgmt/careerpath/saveDateHistoryItem`);
            self.pathUpdate = ko.observable(`careermgmt/careerpath/updateDateHistoryItem`);
            self.pathDelete = ko.observable(`careermgmt/careerpath/removeDateHistoryItem`);
            self.getQueryResult = (res) => {
                return _.map(res, h => {
                    return { histId: h.historyId, startDate: h.startDate, endDate: h.endDate, displayText: `${h.startDate} ～ ${h.endDate}` };
                });
            };
            self.getSelectedStartDate = () => {
                let selectedHist = _.find(self.histList(), h => h.histId === self.selectedHistId());
                if (selectedHist) return selectedHist.startDate;
            };
            self.commandAdd = (masterId, histId, startDate, endDate) => {
                return { startDate: moment(startDate).format("YYYY/MM/DD") }
            };
            self.commandUpdate = (masterId, histId, startDate, endDate) => {
                return {
                    historyId: histId,
                    startDate: moment(startDate).format("YYYY/MM/DD")
                }
            };
            self.commandDelete = (masterId, histId) => {
                return {
                    historyId: histId
                };
            };
            self.delVisible = ko.observable(true);
            self.delChecked = ko.observable();
            self.afterRender = () => {
                //alert("Load!");
                if(self.checkStartFromBScreen){
                    self.selectedHistId(self.dataFromBScreen.historyId);    
                }else{
                    self.selectedHistId(self.latestCareerPathHist());
                }
            };
            self.afterAdd = () => {
                new service.getLatestCareerPathHist().done(function(data: any) {
                    self.latestCareerPathHist(data);
                });
            };
            self.afterUpdate = () => {
                //alert("Updated");
            };
            self.afterDelete = () => {
                new service.getLatestCareerPathHist().done(function(data: any) {
                    self.latestCareerPathHist(data);
                });
            };

            //table 
            self.listCareerType = ko.observableArray([]);
            $("#fixed-table").ntsFixedTable({ height: 209, width: 990 });

            self.selectedHistId.subscribe(function(newValue) {
                self.getCarrerPart(newValue);
            });

            self.histList.subscribe(function(newValue) {
                if(self.histList().length == 0){
                    self.selectedHistId('');        
                }
            });

            self.listCareer = ko.observableArray([]);
            self.careerClass = ko.observableArray([]);
            self.careerType = ko.observableArray([]);
            self.maxClassLevel = ko.observable(0);
            self.latestCareerPathHist = ko.observable('');
            self.checkStartFromBScreen = false;

            //set width for table
            self.maxClassLevel.subscribe(function(newValue) {
                let width = newValue * 165;
                document.getElementsByClassName("fixed-table")[0].style.width = width + "px";
                document.getElementsByClassName("nts-fixed-header-wrapper")[0].style.width = width + "px";
                document.getElementsByClassName("nts-fixed-body-wrapper")[0].style.width = width + "px";
                document.getElementsByClassName("fixed-table")[0].style.width = width + "px";
                document.getElementsByClassName("fixed-table")[1].style.width = width + "px";
            });
            self.checkUpdateEnableControl = false;
            self.historyModeUpdate = ko.observable(false);
        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            block.grayout();
            if(self.dataFromBScreen.historyId != undefined){
                self.checkStartFromBScreen = true;
            }
            new service.getLatestCareerPathHist().done(function(data: any) {
                self.latestCareerPathHist(data);
            });
            new service.getMaxClassLevel().done(function(data: any) {
                self.maxClassLevel(data);
            }).fail(function(error) {
                nts.uk.ui.dialog.error({ messageId: error.messageId });
            }).always(function() {
                dfd.resolve();
                block.clear();
            });
            return dfd.promise();
        }
        private getCarrerPart(hisId: string): void {
            var self = this;
            if (hisId != '') {
                let startDate = _.filter(self.histList(), ['histId', hisId])[0].startDate;
                let command = {
                    historyId: hisId,
                    startDate: moment(startDate).format("YYYY/MM/DD")
                }
                block.grayout();
                self.checkUpdateEnableControl = false; 
                new service.getCareerPart(command).done(function(data: any) {
                    self.historyModeUpdate(data.modeUpdate);
                    data.careerClass.add({ id: "", code: "", name: "" });
                    //console.log(data);
                    if(self.checkStartFromBScreen && self.dataFromBScreen != null){
                        data.career = self.dataFromBScreen.career;
                        self.checkStartFromBScreen = false;
                    }
                    self.listCareer(data.career);
                    self.maxClassLevel(data.maxClassLevel);
                    self.careerType([]);
                    _.forEach(_.orderBy(data.careerType, ['code'], ['asc']), function(value) {
                        self.careerType.push(new CareerType(value));
                    });
                    //self.careerType(_.orderBy(data.careerType, ['code'], ['asc']));
                    self.careerClass(_.orderBy(data.careerClass, ['code'], ['asc']));
                    let list = [];
                    _.forEach(data.careerType, function(value) {
                        list.push(new ScreenItem(value.code, _.filter(data.career, ['careerTypeItem', value.id])));
                    });
                    self.listCareerType(list);
                    self.checkUpdateEnableControl = true;
                    self.updateEnable();
                    
                    // tab
                    $(document).on("keydown", function(evt) {
                        if(evt.keyCode != 9) return;
                        let activeElement = $(document.activeElement);
                        let tbl = activeElement.closest("table");
                        if (tbl.length > 0 && tbl.is(".rightTable")) {
                            let indexLeflTbl = activeElement.closest("tr").index() + 1;
                            let rightTbl = $(".nts-fixed-table");
                            let rowrightTbl = rightTbl.find("tr:nth-child(" + indexLeflTbl +")").find("td").each(function(td){
                                let div = $(this).find("div:first-child");
                                if($(div[0]).attr("aria-disabled" ) !== 'true'){
                                    div.focus();
                                    evt.preventDefault();
                                    return false;
                                }                                
                            });
                        }
                        if (tbl.length > 0 && tbl.is("#fixed-table")) {
                            let rowSelectedRightTbl = activeElement.closest("tr");
                            let index = rowSelectedRightTbl.index() + 1;
                            let lastColSelectedRightTbl = rowSelectedRightTbl.find("td:last-child");
                            if(lastColSelectedRightTbl[0] == activeElement.closest("td")[0]){
                                let btnLeftTbl = $(".rightTable").find("tr:nth-child(" + (index + 1) +")").find("td:first-child").find("button")
                                let btnLeftTblIsDis = btnLeftTbl.prop("disabled");
                                if(!btnLeftTblIsDis){
                                    btnLeftTbl.focus();
                                    btnLeftTbl.select();
                                    evt.preventDefault();
                                }
                            }
                        }
                    });
                    block.clear();
                }).fail(function(error) {
                    nts.uk.ui.dialog.error({ messageId: error.messageId });
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }else{
                self.listCareer();
                self.careerType([]);
                self.listCareerType([]);
                self.checkUpdateEnableControl = true;
                self.historyModeUpdate(false);
            }
        }
        public openDialogB(careerType: any): void {
            var self = __viewContext.vm;
            self.updateCareer();
            let startDate = _.filter(self.histList(), ['histId', self.selectedHistId()])[0].startDate;
            let command = {
                careerTypeItem: careerType.id,
                startDate: moment(startDate).format("YYYY/MM/DD"),
                career: __viewContext.vm.listCareer()
            }
            let dataShareCareerToBScreen = {
                careerTypeId: careerType.id,
                careerTypeName: careerType.name,
                historyId: self.selectedHistId(),
                career: self.listCareer(),
                maxClassLevel: self.maxClassLevel(),
                careerClass: self.careerClass(),
                startDate: moment(startDate).format("YYYY/MM/DD")
            }
            if (careerType != '') {
                block.grayout();
                new service.checkDataCareer(command).done(function() {
                    parent.nts.uk.ui.block.clear();
                    nts.uk.request.jump("hr", "/view/jhc/002/b/index.xhtml", dataShareCareerToBScreen);
                }).fail(function(error) {
                    nts.uk.ui.dialog.error({ messageId: error.messageId });
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }
        }
        public save(): void {
            var self = this;
            self.updateCareer();
            let kt = false;
            _.forEach(ko.toJS(self.listCareerType()), function(value) {
                kt = value.checkExistSelected(self.maxClassLevel());
                if (kt) return false;
            });
            if (kt) {
                nts.uk.ui.windows.sub.modal("../c/index.xhtml").onClosed(() => {
                    let isNotice = nts.uk.ui.windows.getShared("isNotice");
                    let command = {
                        historyId: self.selectedHistId(),
                        careerLevel: self.maxClassLevel(),
                        career: self.listCareer(),
                        isNotice: isNotice
                    }
                    //console.log(command);
                    block.grayout();
                    new service.saveCareer(command).done(function() {
                        //console.log('oki');
                        self.historyModeUpdate(true);
                    }).fail(function(error) {
                        nts.uk.ui.dialog.error({ messageId: error.messageId });
                    }).always(function() {
                        nts.uk.ui.block.clear();
                    });
                });
            } else {
                nts.uk.ui.dialog.error({ messageId: 'MsgJ_50' });
            }
            let startDate = _.filter(self.histList(), ['histId', self.selectedHistId()])[0].startDate;
        }
        
        private updateCareer(): void {
            var self = this;
            _.forEach(ko.toJS(self.listCareerType()), function(value) {
                for (let i = 1; i <= 10; i++) { 
                  self.convertData(value, i);
                }
            });
        }
        
        public updateEnable(): void{
            let self =  this;
            if (self.checkUpdateEnableControl && self.listCareerType().length > 0) {
                _.forEach(ko.toJS(self.listCareerType()), function(value) {
                    let index = _.findIndex(self.careerType(), { 'code': value.code });
                    self.careerType()[index].enable(value.checkExistSelected(self.maxClassLevel()));
                }); 
                self.listCareerType()[0].setEnableCommon(self.listCareerType()[1], self.listCareerType()[2], self.listCareerType()[3], self.listCareerType()[4]);
                self.listCareerType()[1].setEnable(self.listCareerType()[0]);
                self.listCareerType()[2].setEnable(self.listCareerType()[0]);
                self.listCareerType()[3].setEnable(self.listCareerType()[0]);
                self.listCareerType()[4].setEnable(self.listCareerType()[0]);
            }
        }
        
        private convertData(value: any, lever: number): void {
            let self = this;
            let typeInfo = _.filter(self.careerType(), ['code', value.code])[0];
            let a;
            if(lever == 1){
                a = value.l1;
            }else if(lever == 2){
                a = value.l2;
            }else if(lever == 3){
                a = value.l3;
            }else if(lever == 4){
                a = value.l4;
            }else if(lever == 5){
                a = value.l5;
            }else if(lever == 6){
                a = value.l6;
            }else if(lever == 7){
                a = value.l7;
            }else if(lever == 8){
                a = value.l8;
            }else if(lever == 9){
                a = value.l9;
            }else if(lever == 10){
                a = value.l10;
            }
            if(a != ''){
                if(_.find(self.listCareer(), { 'careerTypeItem': typeInfo.id, 'careerLevel': lever }) == undefined){
                    //add Career
                    let itemCareer = {
                        careerClassItem: a, 
                        careerClassRole: '', 
                        careerLevel: lever, 
                        careerRequirementList: [],
                        careerTypeItem: typeInfo.id
                    }
                    self.listCareer.push(itemCareer);
                }else{
                    //update Career
                    let update = _.find(self.listCareer(), { 'careerTypeItem': typeInfo.id, 'careerLevel': lever });
                    update.careerClassItem = a;
                    _.remove(self.listCareer(), function(n) {
                        return (n.careerTypeItem == typeInfo.id && n.careerLevel == lever);
                    });
                    self.listCareer.push(update);
                }
            }else{
                //remove Career
                _.remove(self.listCareer(), function(n) {
                    return (n.careerTypeItem == typeInfo.id && n.careerLevel == lever);
                });
            }
        }
    }

    class ScreenItem {
        code: KnockoutObservable<string> = ko.observable("");
        l1: KnockoutObservable<string> = ko.observable("");
        l2: KnockoutObservable<string> = ko.observable("");
        l3: KnockoutObservable<string> = ko.observable("");
        l4: KnockoutObservable<string> = ko.observable("");
        l5: KnockoutObservable<string> = ko.observable("");
        l6: KnockoutObservable<string> = ko.observable("");
        l7: KnockoutObservable<string> = ko.observable("");
        l8: KnockoutObservable<string> = ko.observable("");
        l9: KnockoutObservable<string> = ko.observable("");
        l10: KnockoutObservable<string> = ko.observable("");
        enablel1: KnockoutObservable<boolean> = ko.observable(false);
        enablel2: KnockoutObservable<boolean> = ko.observable(false);
        enablel3: KnockoutObservable<boolean> = ko.observable(false);
        enablel4: KnockoutObservable<boolean> = ko.observable(false);
        enablel5: KnockoutObservable<boolean> = ko.observable(false);
        enablel6: KnockoutObservable<boolean> = ko.observable(false);
        enablel7: KnockoutObservable<boolean> = ko.observable(false);
        enablel8: KnockoutObservable<boolean> = ko.observable(false);
        enablel9: KnockoutObservable<boolean> = ko.observable(false);
        enablel10: KnockoutObservable<boolean> = ko.observable(false);
        constructor(code: string, career: Array<any>) {
            var self2 = this;
            self2.code(code);
            _.forEach(career, function(value) {
                switch (value.careerLevel) {
                    case 1:
                        self2.l1(value.careerClassItem);
                        break;
                    case 2:
                        self2.l2(value.careerClassItem);
                        break;
                    case 3:
                        self2.l3(value.careerClassItem);
                        break;
                    case 4:
                        self2.l4(value.careerClassItem);
                        break;
                    case 5:
                        self2.l5(value.careerClassItem);
                        break;
                    case 6:
                        self2.l6(value.careerClassItem);
                        break;
                    case 7:
                        self2.l7(value.careerClassItem);
                        break;
                    case 8:
                        self2.l8(value.careerClassItem);
                        break;
                    case 9:
                        self2.l9(value.careerClassItem);
                        break;
                    case 10:
                        self2.l10(value.careerClassItem);
                        break;
                    default:
                        break;
                }
            });
            self2.l1.subscribe(function(newValue) {
                __viewContext.vm.updateEnable();
            });
            self2.l2.subscribe(function(newValue) {
                __viewContext.vm.updateEnable();
            });
            self2.l3.subscribe(function(newValue) {
                __viewContext.vm.updateEnable();
            });
            self2.l4.subscribe(function(newValue) {
                __viewContext.vm.updateEnable();
            });
            self2.l5.subscribe(function(newValue) {
                __viewContext.vm.updateEnable();
            });
            self2.l6.subscribe(function(newValue) {
                __viewContext.vm.updateEnable();
            });
            self2.l7.subscribe(function(newValue) {
                __viewContext.vm.updateEnable();
            });
            self2.l8.subscribe(function(newValue) {
                __viewContext.vm.updateEnable();
            });
            self2.l9.subscribe(function(newValue) {
                __viewContext.vm.updateEnable();
            });
            self2.l10.subscribe(function(newValue) {
                __viewContext.vm.updateEnable();
            });
        }
        
        public setEnable(obj: ScreenItem): void {
            let self =  this;
            self.enablel1(obj.l1() == '');
            self.enablel2(obj.l2() == '');
            self.enablel3(obj.l3() == '');
            self.enablel4(obj.l4() == '');
            self.enablel5(obj.l5() == '');
            self.enablel6(obj.l6() == '');
            self.enablel7(obj.l7() == '');
            self.enablel8(obj.l8() == '');
            self.enablel9(obj.l9() == '');
            self.enablel10(obj.l10() == '');
        }
        
        public setEnableCommon(obj1: ScreenItem, obj2: ScreenItem, obj3: ScreenItem, obj4: ScreenItem): void {
            let self =  this;
            self.enablel1(obj1.l1() == '' && obj2.l1() == '' && obj3.l1() == '' && obj4.l1() == '');
            self.enablel2(obj1.l2() == '' && obj2.l2() == '' && obj3.l2() == '' && obj4.l2() == '');
            self.enablel3(obj1.l3() == '' && obj2.l3() == '' && obj3.l3() == '' && obj4.l3() == '');
            self.enablel4(obj1.l4() == '' && obj2.l4() == '' && obj3.l4() == '' && obj4.l4() == '');
            self.enablel5(obj1.l5() == '' && obj2.l5() == '' && obj3.l5() == '' && obj4.l5() == '');
            self.enablel6(obj1.l6() == '' && obj2.l6() == '' && obj3.l6() == '' && obj4.l6() == '');
            self.enablel7(obj1.l7() == '' && obj2.l7() == '' && obj3.l7() == '' && obj4.l7() == '');
            self.enablel8(obj1.l8() == '' && obj2.l8() == '' && obj3.l8() == '' && obj4.l8() == '');
            self.enablel9(obj1.l9() == '' && obj2.l9() == '' && obj3.l9() == '' && obj4.l9() == '');
            self.enablel10(obj1.l10() == '' && obj2.l10() == '' && obj3.l10() == '' && obj4.l10() == '');
        }
        
        public checkExistSelected(maxLever: number): boolean {
            if (maxLever == 0) return false;
            if (maxLever >= 1) {
                if (this.l1 != "") return true;
            }
            if (maxLever >= 2) {
                if (this.l2 != "") return true;
            }
            if (maxLever >= 3) {
                if (this.l3 != "") return true;
            }
            if (maxLever >= 4) {
                if (this.l4 != "") return true;
            }
            if (maxLever >= 5) {
                if (this.l5 != "") return true;
            }
            if (maxLever >= 6) {
                if (this.l6 != "") return true;
            }
            if (maxLever >= 7) {
                if (this.l7 != "") return true;
            }
            if (maxLever >= 8) {
                if (this.l8 != "") return true;
            }
            if (maxLever >= 9) {
                if (this.l9 != "") return true;
            }
            if (maxLever == 10) {
                if (this.l10 != "") return true;
            }
            return false;
        }
        
    }
    class ItemModel {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    class CareerType {
        id: string;
        code: string;
        name: string;
        enable: KnockoutObservable<boolean> = ko.observable(false);
        constructor(obj: any) {
            this.id = obj.id
            this.code = obj.code;
            this.name = obj.name;
        }
    }
}
