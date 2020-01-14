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
        reachedAgeTermList: KnockoutObservableArray<any> = ko.observableArray([]);
        dateRule: KnockoutObservableArray<any> = ko.observableArray([]);
        dateSelectItem: KnockoutObservableArray<any> = ko.observableArray([]);
        retireDateRule: KnockoutObservableArray<any> = ko.observableArray([]);
        referEvaluationTerm: KnockoutObservableArray<any> = ko.observableArray([]);
        displayNum: KnockoutObservableArray<any> = ko.observableArray([
                    new ItemModel(1, "1"),
                    new ItemModel(2, "2"),
                    new ItemModel(3, "3"),
                ]);
        monthSelectItem: KnockoutObservableArray<any> = ko.observableArray([]);
        
        // master
        latestHistId: KnockoutObservable<any>;
        isLatestHis: KnockoutObservable<boolean> = ko.observable(false);
        commonMasterName: KnockoutObservable<string> = ko.observable("");
        commonMasterItems: KnockoutObservableArray<GrpCmonMaster> = ko.observableArray([]);
        retirePlanCourseList: [];
        getRelatedMaster: KnockoutObservable<boolean> = ko.observable(false);
        
        // control
        reachedAgeTerm: KnockoutObservable<number> = ko.observable(0);
        calculationTerm: KnockoutObservable<number> = ko.observable(1);
        dateSettingDate: KnockoutObservable<number> = ko.observable(1);
        dateSettingNum: KnockoutObservable<number> = ko.observable(0);
        retireDateTerm: KnockoutObservable<number> = ko.observable(0);
        retireDateSettingDate: KnockoutObservable<number> = ko.observable(1);
        planCourseApplyFlg: KnockoutObservable<boolean> = ko.observable(false);
        applicationEnableStartAge: KnockoutObservable<number> = ko.observable(50);
        applicationEnableEndAge: KnockoutObservable<number> = ko.observable(59);
        endMonth: KnockoutObservable<any> = ko.observable(12);
        endDate: KnockoutObservable<any> = ko.observable(31);
        
        constructor() {
            let self = this;
            // radio button
            self.reachedAgeTermList(__viewContext.enums.ReachedAgeTerm);            
            self.dateRule = (__viewContext.enums.DateRule);
            self.dateSelectItem = (__viewContext.enums.DateSelectItem);
            self.retireDateRule = (__viewContext.enums.RetireDateRule);
            self.monthSelectItem = (__viewContext.enums.MonthSelectItem);
            
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
                if (self.latestHistId() === newValue) {
                    self.isLatestHis(true);
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
            
            self.applicationEnableStartAge.subscribe(function(x){
                if(x > self.applicationEnableEndAge()){
                    nts.uk.ui.dialog.error({ messageId: "MsgJ_JMM018_11"});
                }
            });
            
            self.applicationEnableEndAge.subscribe(function(y){
                if(y < self.applicationEnableStartAge()){
                    nts.uk.ui.dialog.error({ messageId: "MsgJ_JMM018_12"});
                }
            });
            self.planCourseApplyFlg.subscribe(function(val){
                $('.judg').trigger("validate");
            });
            
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
                        
                        let tg = [];
                        _.forEach(data.referEvaluationTerm, (item) => {
                            tg.push(new ReferenceValue(item));
                        });
                        let a = []
                        a.push(new ReferenceValue({evaluationItem: 1, usageFlg: true, displayNum: 1, passValue: 'B'}));
                        self.referEvaluationTerm(a);
                        
                        self.planCourseApplyFlg(data.planCourseApplyFlg);
                        
                    }).fail(function(err) {
                        error({ messageId: err.messageId });
                    }).always(function() {
                        block.clear();
                        dfd.resolve();
                    });
                }
            });
            
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
            setShared('employmentTypeToC', employmentType);
            nts.uk.ui.windows.sub.modal('/view/jmm/018/c/index.xhtml').onClosed(function(): any {
                let param = getShared('shareToJMM018B');
                item.setEnableRetirePlanCourse(param);
            })
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
            $('.judg').trigger("validate");
            _.forEach(self.retirePlanCourseList, (obj) => {
//                if(obj.retireAge() == true && obj.retireCourse() == ""){
//                    nts.uk.ui.dialog.error({ messageId: "MsgJ_JMM018_13"});
//                }
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
        evaluationItem: number;
        usageFlg: boolean;
        displayNum: number;
        passValue: string;
    }
    
    class ReferenceValue {
        name: string;
        evaluationItem: number;
        usageFlg: KnockoutObservable<boolean> = ko.observable();
        displayNum: KnockoutObservable<number> = ko.observable(1);
        passValue: KnockoutObservable<string> = ko.observable("B");
        constructor(param: IReferenceValue) {
            let self = this;
            self.evaluationItem = param.evaluationItem;
            self.usageFlg = ko.observable(param.usageFlg);
            self.displayNum = ko.observable(param.displayNum);
            self.passValue = ko.observable(param.passValue);
            self.name = _.find(__viewContext.enums.EvaluationItem, t => t.value == param.evaluationItem).name;
            self.usageFlg.subscribe(function(val){
                $('.judg').trigger("validate");
            });
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
