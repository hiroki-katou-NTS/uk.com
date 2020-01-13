module nts.uk.com.view.jmm018.tabb.viewmodel {
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import error = nts.uk.ui.dialog.error;
    let __viewContext: any = window["__viewContext"] || {};

    export class ScreenModel {
        screenMode: KnockoutObservable<any> = ko.observable(1);
        masterId: KnockoutObservable<string> = ko.observable("");
        histList: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedHistId: KnockoutObservable<any> = ko.observable('');
        itemListB422_15: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedIdB422_15: KnockoutObservable<number> = ko.observable(0);
        releaseDate: KnockoutObservableArray<any> = ko.observableArray([]);
        releaseDateSelected: KnockoutObservable<number> = ko.observable(1);
        textEditorB422_15_6: KnockoutObservable<number> = ko.observable(0);
        textDate: KnockoutObservable<string> = ko.observable('');

        releaseTypeSelected: KnockoutObservable<number> = ko.observable(1);
        retireDate: KnockoutObservableArray<any> = ko.observableArray([]);
        retireDateSelected: KnockoutObservable<number> = ko.observable(2);
        referenceValueLs: KnockoutObservableArray<any> = ko.observableArray([]);
        numberDispLs: KnockoutObservableArray<any> = ko.observableArray([]);
        applyCondition: KnockoutObservable<boolean> = ko.observable(false);
        startAppliPossible: KnockoutObservable<string> = ko.observable("");
        ageLowerLimit: KnockoutObservable<number> = ko.observable(0);
        ageUpperLimit: KnockoutObservable<number> = ko.observable(0);
        appliAcceptLst: KnockoutObservableArray<any> = ko.observableArray([]);
        appliAcceptVal: KnockoutObservable<any> = ko.observable(1);
        appliAccepTypetLst: KnockoutObservableArray<any> = ko.observableArray([]);
        appliAcceptType: KnockoutObservable<any> = ko.observable(1);
        isLatestHis: KnockoutObservable<boolean> = ko.observable(false);
        appliConEna: KnockoutObservable<boolean> = ko.observable(false);
        retireDatePoint: KnockoutObservable<number> = ko.observable(1);
        retireDateEna: KnockoutObservable<boolean> = ko.observable(true);
        retireDateVis: KnockoutObservable<boolean> = ko.observable(true);
        retireText: KnockoutObservable<boolean> = ko.observable(true);
        
        
        latestHistId: KnockoutObservable<any>;
        commonMasterName: KnockoutObservable<string> = ko.observable("");
        commonMasterItems: KnockoutObservableArray<GrpCmonMaster> = ko.observableArray([]);
        retirePlanCourseList: [];
        getRelatedMaster: KnockoutObservable<boolean> = ko.observable(false);
        
        
        constructor() {
            let self = this;
            
            // radio button
            let ageCondition = __viewContext.enums.UseAtr;
            _.forEach(ageCondition, (obj) => {
                self.itemListB422_15.push(new ItemModel(obj.value, obj.name));
            });
            
            // combobox
            self.releaseDate = ko.observableArray([
                    new ItemModel(1, getText('JMM018_B422_15_5_1')),
                    new ItemModel(2, getText('JMM018_B422_15_5_2')),
                    new ItemModel(3, getText('JMM018_B422_15_5_3')),
                    new ItemModel(6, getText('JMM018_B422_15_5_6')),
                ]);
            
            self.retireDate = ko.observableArray([
                    new ItemModel(1, getText('JMM018_B422_15_5_1')),
                    new ItemModel(2, getText('JMM018_B422_15_5_2')),
                    new ItemModel(3, getText('JMM018_B422_15_5_3')),
                    new ItemModel(6, getText('JMM018_B422_15_5_6')),
                ]);
            
            self.numberDispLs = ko.observableArray([
                    new ItemModel(1, "1"),
                    new ItemModel(2, "2"),
                    new ItemModel(3, "3"),
                ]);
            
            let appliAccep = __viewContext.enums.MonthSelectItem;
            _.forEach(appliAccep, (obj) => {
                self.appliAcceptLst.push(new ItemModel(obj.value, obj.name));
            });
            
            let appAccepType = __viewContext.enums.DateSelectItem;
            _.forEach(appAccepType, (x) => {
                self.appliAccepTypetLst.push(new ItemModel(x.value, x.name));
            });
            
            //ThanhPV
            self.latestHistId = ko.observable('');
            
            
            self.delVisible = ko.observable(true);
            self.delChecked = ko.observable();
            self.pathGet = ko.observable(`employmentRegulationHistory/getDateHistoryItem`);
            self.pathAdd = ko.observable(`employmentRegulationHistory/saveDateHistoryItem`);
            self.pathUpdate = ko.observable(`employmentRegulationHistory/updateDateHistoryItem`);
            self.pathDelete = ko.observable(`employmentRegulationHistory/removeDateHistoryItem`);
            
            self.commandAdd = (masterId, histId, startDate, endDate) => {
                return { startDate: moment(startDate).format("YYYY/MM/DD") }
            };
            
            self.getQueryResult = (res) => {
                return _.map(res, h => {
                    return { histId: h.historyId, startDate: h.startDate, endDate: h.endDate, displayText: `${h.startDate} ～ ${h.endDate}` };
                });
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
            
            self.getSelectedStartDate = () => {
                let selectedHist = _.find(self.histList(), h => h.histId === self.selectedHistId());
                if (selectedHist) return selectedHist.startDate;
            };
            
            self.histList.subscribe(function(newValue) {
                if(self.histList().length == 0){
                    self.selectedHistId('');        
                }
            });
            
            self.selectedHistId.subscribe(function(newValue) {
                if (!_.findIndex(self.histList(), h => h.histId === newValue)) {
                    self.isLatestHis(true);
                    _.forEach(self.referenceValueLs(), (obj) => {
                        obj.enable(obj.enable());
                    })
                }else{
                    self.isLatestHis(false);
                }
            });
            
            self.afterRender = () => {
                
            };
            
            self.afterAdd = () => {
                new service.getLatestCareerPathHist().done(function(data: any) {
                    self.latestCareerPathHist(data);
                });
            };
            
            self.afterUpdate = () => {
                alert("Updated");
            };
            
            self.afterDelete = () => {
                alert("delete");
            };
            
            self.releaseDateSelected.subscribe(function(val){
                if(val == 2){
                    self.textDate(getText('JMM018_B422_17_7_1')); 
                    self.retireDateVis(false);  
                    self.retireText(true);
                }else if(val == 3){
                    self.textDate(getText('JMM018_B422_17_7_2'));  
                    self.retireDateVis(false); 
                    self.retireText(true);
                }else if(val == 6){
                    self.retireDateVis(true);
                    self.retireText(false);
                }else{
                    self.retireDateVis(false);    
                    self.retireText(false);
                }
            });
            
            self.ageLowerLimit.subscribe(function(x){
                if(x > self.ageUpperLimit()){
                    nts.uk.ui.dialog.error({ messageId: "MsgJ_JMM018_11"});
                }
            });
            
            self.ageUpperLimit.subscribe(function(y){
                if(y < self.ageLowerLimit()){
                    nts.uk.ui.dialog.error({ messageId: "MsgJ_JMM018_12"});
                }
            });
            
            self.applyCondition.subscribe(function(z){
                if(z == true){
                    self.appliConEna(true);
                }else{
                    self.appliConEna(false);
                }
            });
            
            self.retireDateSelected.subscribe(function(v) {
                if(v == 1){
                    self.retireDateEna(true);    
                }else{
                    self.retireDateEna(false);    
                }
            })
        }

        start(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.grayout();
            $.when(self.loadHisId(), self.loadCommonMasterItems()).done(function() {
                self.selectedHistId(self.latestHistId());
                if(self.getRelatedMaster() && (self.latestHistId() == '' || self.latestHistId() == null)){
                    error({ messageId: 'MsgJ_JMM018_15' });
                    dfd.resolve();
                    block.clear();
                }else if(self.getRelatedMaster() && self.latestHistId() != '' && self.latestHistId() != null){
                    let param = {historyId: self.latestHistId(), getRelatedMaster: self.getRelatedMaster()}
                    new service.getMandatoryRetirementRegulation(param).done(function(data: any) {
                        console.log(data);
                    }).fail(function(err) {
                        error({ messageId: err.messageId });
                    }).always(function() {
                        block.clear();
                        dfd.resolve();
                    });
                }
            });
            
            
            
            
            
            
            
            
            
            
            let a = {
                        index: 1,
                        // B422_15_22
                        valueItem: "a",
                        // B422_15_23
                        display: true,
                        // B422_15_24
                        numberDisplay: 2,
                        // B422_15_25
                        valueCriteria: "",
                        // B422_15_26
                        continuationCategory: "a",
                    }
            let b = {
                index: 2,
                // B422_15_22
                valueItem: "a",
                // B422_15_23
                display: false,
                // B422_15_24
                numberDisplay: 3,
                // B422_15_25
                valueCriteria: "",
                // B422_15_26
                continuationCategory: "a",
            }

            self.referenceValueLs().push(new ReferenceValue(a));
            self.referenceValueLs().push(new ReferenceValue(b));
            
            
            
            self.hidden('href1', 'B422_12');
            block.clear();

            return dfd.promise();
        }
        
        openCDialog(item: any): void {
            let self = this;
            let employmentType = {
                listInfor: self,
                listSelect: item.getRetirePlanCourseId()
            }
//            setShared('employmentType', guideMsg);
//            nts.uk.ui.windows.sub.modal('/view/jmm/018/c/index.xhtml').onClosed(function(): any {
//            })
        }
        
        loadHisId(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            new service.getLatestHistId().done(function(data: any) {
                self.latestHistId(data);
                dfd.resolve();
            });
            return dfd.promise();
        }
        
        loadCommonMasterItems(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            new service.getRelateMaster().done(function(data: any) {
                console.log(data);
                self.commonMasterName(data.commonMasterName);
                self.retirePlanCourseList = data.retirePlanCourseList;
                let tg = [];
                _.forEach(_.orderBy(data.commonMasterItems,['displayNumber'], ['asc']), (item) => {
                    tg.push(new GrpCmonMaster(item));
                });
                self.commonMasterItems(tg);
                self.getRelatedMaster(true);
            }).fail(function(err) {
                error({ messageId: err.messageId });
            }).always(function() {
                dfd.resolve();
            });
            return dfd.promise();
        }

        register(): void{
            let self = this;
            if(!!self.applyCondition()){
                $('#lowerLimit').trigger("validate");
                $('#upperLimit').trigger("validate");
            }
            $('.judg').trigger("validate");
            _.forEach(self.retirePlanCourseList(), (obj) => {
                if(obj.retireAge() == true && obj.retireCourse() == ""){
                    nts.uk.ui.dialog.error({ messageId: "MsgJ_JMM018_13"});
                }
            });    
        }
        
        hidden(param: string, id: string){
            $('.hyperlink').removeClass('isDisabled');
            $('#'+ param).addClass('isDisabled');
            var elmnt = document.getElementById(id);
            elmnt.scrollIntoView();
        }
    }
    
    export interface IReferenceValue {
        index: number;
        // B422_15_22
        valueItem: string;
        // B422_15_23
        display: boolean;
        // B422_15_24
        numberDisplay: number;
        // B422_15_25
        valueCriteria: string;
    }
    
    class ReferenceValue {
        index: number;
        // B422_15_22
        valueItem: KnockoutObservable<string> = ko.observable("");
        // B422_15_23
        display: KnockoutObservable<boolean> = ko.observable(true);
        // B422_15_24
        numberDisplay: KnockoutObservable<number> = ko.observable(1);
        // B422_15_25
        valueCriteria: KnockoutObservable<string> = ko.observable("B");
        // required
        //required: KnockoutObservable<boolean> = ko.observable(false); 
        // enable
        enable: KnockoutObservable<boolean> = ko.observable(false);
        constructor(param: IReferenceValue) {
            let self = this;
            self.index = param.index;
            self.valueItem = ko.observable(param.valueItem);
            self.display = ko.observable(param.display);
            self.numberDisplay = ko.observable(param.numberDisplay);
            self.valueCriteria = ko.observable(param.valueCriteria);
            self.enable = ko.observable(param.display);
            self.display.subscribe(function(x){
                if(x){
                    self.enable(true);
                }else{
                    self.enable(false);
                }
            })
        }
    }
    
    export interface IGrpCmonMaster {
        displayNumber: number;
        commonMasterItemId: string;
        commonMasterItemName: string;
    }
    
    class GrpCmonMaster {
        displayNumber: number;
        commonMasterItemId: string;
        commonMasterItemName: string;
        usageFlg: KnockoutObservable<boolean> = ko.observable(false);
        enableRetirePlanCourse: KnockoutObservableArray<any> = ko.observableArray([]);
        enableRetirePlanCourseText: KnockoutObservable<string> = ko.observable("");
        constructor(param: IGrpCmonMaster) {
            let self = this;
            self.displayNumber = param.displayNumber;
            self.commonMasterItemId = param.commonMasterItemId;
            self.commonMasterItemName = param.commonMasterItemName;
        }
        setUsageFlg(usageFlg: boolean): void{
            let self = this;
            self.usageFlg(usageFlg);
        }
        setEnableRetirePlanCourse(enableRetirePlanCourse: any[]): void{
            let self = this;
            self.enableRetirePlanCourse(enableRetirePlanCourse);
            self.enableRetirePlanCourseText(enableRetirePlanCourse.toString());
        }
        getRetirePlanCourseId(): any{
            let self = this;
            let id = [];
            _.forEach(self.enableRetirePlanCourse(), (item) => {
                id.push(item.retirePlanCourseId);
            });
            return id; 
        }
        setRetirePlanCourseId(retirePlanCourseIds: any[]): void{
            let self = this;
            let tg = [];
            _.forEach(retirePlanCourseIds, (id) => {
                tg.push({retirePlanCourseId: id});
            });
            self.enableRetirePlanCourse(tg); 
        }
    }

    class ItemModel {
        value: number;
        name: string;

        constructor(value: number, name: string) {
            this.value = value;
            this.name = name;
        }
    }
}
