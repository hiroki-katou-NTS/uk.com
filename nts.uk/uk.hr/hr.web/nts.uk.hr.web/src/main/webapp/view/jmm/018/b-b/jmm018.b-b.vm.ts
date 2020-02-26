module nts.uk.com.view.jmm018.tabb.viewmodel {
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import error = nts.uk.ui.dialog.error;
    import info = nts.uk.ui.dialog.info;
    let __viewContext: any = window["__viewContext"] || {};

    export class ScreenModel {
        screenMode: KnockoutObservable<any> = ko.observable(1);
        masterId: KnockoutObservable<string> = ko.observable("");
        histList: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedHistId: KnockoutObservable<any> = ko.observable('');
        latestHistId: KnockoutObservable<any>;
        isLatestHis: KnockoutObservable<boolean>;

        // master
        reachedAgeTermList: KnockoutObservableArray<any> = ko.observableArray([]);
        dateRule: KnockoutObservableArray<any> = ko.observableArray([
                    new ItemModel(1, getText('JMM018_B422_15_5_1')),
                    new ItemModel(2, getText('JMM018_B422_15_5_2')),
                    new ItemModel(3, getText('JMM018_B422_15_5_3')),
                    new ItemModel(6, getText('JMM018_B422_15_5_6')),
                ]);
        dateSelectItem: KnockoutObservableArray<any> = ko.observableArray([]);
        monthSelectItem: KnockoutObservableArray<any> = ko.observableArray([]);
        retireDateRule: KnockoutObservableArray<any> = ko.observableArray([]);
        referEvaluationTerm: KnockoutObservableArray<any> = ko.observableArray([]);
        displayNum: KnockoutObservableArray<any> = ko.observableArray([
                    new ItemModel(1, "1"),
                    new ItemModel(2, "2"),
                    new ItemModel(3, "3")
                ]);
        commonMasterName: KnockoutObservable<string> = ko.observable("");
        commonMasterItems: KnockoutObservableArray<GrpCmonMaster> = ko.observableArray([]);
        retirePlanCourseList: [];
        
        // data
        changeLatest: boolean;
        isStart: boolean;
        isAddNewHis: boolean;
        getRelatedMaster: KnockoutObservable<boolean> = ko.observable(false);
        mandatoryRetirementRegulation: KnockoutObservable<MandatoryRetirementRegulation>;
        
        constructor() {
            let self = this;
            // radio button
            self.reachedAgeTermList(__viewContext.enums.ReachedAgeTerm); 
            //_.remove(__viewContext.enums.DateRule, function(n) {return n.value == 0 || n.value == 4 || n.value == 5;});           
            self.dateSelectItem = (__viewContext.enums.DateSelectItem);
            self.retireDateRule = (__viewContext.enums.RetireDateRule);
            self.monthSelectItem = (__viewContext.enums.MonthSelectItem);
            
            __viewContext.primitiveValueConstraints.Integer_0_99.min = 1;
            __viewContext.primitiveValueConstraints.Integer_0_99.max = 90;
            
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
                nts.uk.ui.errors.clearAll();
                if (newValue != null && self.latestHistId() === newValue) {
                    self.isLatestHis(true);
                }else{
                    self.isLatestHis(false);
                }
                if(self.isAddNewHis){
                    self.isAddNewHis = false;
                    self.latestHistId(newValue);
                    self.isLatestHis(true); 
                    self.add();
                }else{
                    if(newValue == '' || newValue == null){
                        self.mandatoryRetirementRegulation(new MandatoryRetirementRegulation(undefined));   
                        self.setDefaultMandatoryRetireTerm();
                    }else{
                        if(self.changeLatest){
                            self.loadHisId().done(function() {
                                self.getMandatoryRetirementRegulation();
                            });    
                        }else{
                            self.getMandatoryRetirementRegulation();
                        }
                        
                    }    
                }
            });
            self.afterRender = () => {};
            self.afterAdd = () => {
                self.isAddNewHis = true;
                self.changeLatest = true;
            };
            self.afterUpdate = () => {
//                alert("Updated");
            };
            self.afterDelete = () => {
                self.changeLatest = true;
            };
            self.isLatestHis = ko.observable(false)
            self.isLatestHis.subscribe(function(val){
                $('.judg').trigger("validate");
            });
            
            self.mandatoryRetirementRegulation = ko.observable(new MandatoryRetirementRegulation(undefined));
            self.setDefaultMandatoryRetireTerm();
            $(window).resize(function() {
                if(window.innerHeight > 400){
                    $('#panel-scroll').height(window.innerHeight - 171);
                    $('#tabpanel-2').height(window.innerHeight - 97);
                }
            });
        }

        start(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.grayout();
            self.selectedHistId('');
            $.when(self.loadHisId(), self.loadCommonMasterItems()).done(function() {
                self.isStart = true;
                self.selectedHistId(self.latestHistId());
                if(self.getRelatedMaster() && (self.latestHistId() == '' || self.latestHistId() == null)){
                    self.setDefaultMandatoryRetireTerm();
                    error({ messageId: 'MsgJ_JMM018_15' });
                    self.isStart = false;
                    dfd.resolve();
                    block.clear();
                }else if(self.getRelatedMaster() && self.latestHistId() != '' && self.latestHistId() != null){
                    let param = {historyId: self.latestHistId(), getRelatedMaster: self.getRelatedMaster()}
                    new service.getMandatoryRetirementRegulation(param).done(function(data: any) {
                        console.log(data);
                        self.mandatoryRetirementRegulation(new MandatoryRetirementRegulation(data));
                        _.forEach(self.commonMasterItems(), (item) => {
                            let tg = _.find(data.mandatoryRetireTerm, h => h.empCommonMasterItemId == item.commonMasterItemId)
                            if(tg){
                                item.usageFlg(tg.usageFlg);
                                item.setEnableRetirePlanCourse(tg.enableRetirePlanCourse, self.retirePlanCourseList);
                            }else{
                                item.usageFlg(false);
                                item.setEnableRetirePlanCourse([],[]);
                            }
                        });
                    }).fail(function(err) {
                        self.setDefaultMandatoryRetireTerm();
                        error({ messageId: err.messageId });
                    }).always(function() {
                        block.clear();
                        dfd.resolve();
                    });
                }else{
                    block.clear();
                    dfd.resolve();    
                }
            });
            $('#tabpanel-2').height(window.innerHeight - 97);
            $('#panel-scroll').height(window.innerHeight - 171);
//            self.hidden('href1', 'B422_12');
            $('.hyperlink').removeClass('isDisabled');
            $('#href1').addClass('isDisabled');
            return dfd.promise();
        }
        
        getMandatoryRetirementRegulation(){
            let self = this;
            if(self.latestHistId() == '' || self.latestHistId() == null){
                return;   
            }else if(!self.isStart){
                if(self.getRelatedMaster()){
                    block.grayout();
                    let param = {historyId: self.selectedHistId(), getRelatedMaster: self.getRelatedMaster()}
                    new service.getMandatoryRetirementRegulation(param).done(function(data: any) {
                        console.log(data);
                        self.mandatoryRetirementRegulation(new MandatoryRetirementRegulation(data));
                        let listItemCommon = self.commonMasterItems();
                        _.forEach(listItemCommon, (item) => {
                            let tg = _.find(data.mandatoryRetireTerm, h => h.empCommonMasterItemId == item.commonMasterItemId)
                            if(tg){
                                item.usageFlg(tg.usageFlg);
                                item.setEnableRetirePlanCourse(tg.enableRetirePlanCourse, self.retirePlanCourseList);
                            }else{
                                item.usageFlg(false);
                                item.setEnableRetirePlanCourse([],[]);
                            }
                        });
                        self.commonMasterItems(listItemCommon);
                    }).fail(function(err) {
                        if(self.selectedHistId()==self.latestHistId()){
                            error({ messageId: 'MsgJ_JMM018_18' });      
                        }else{
                            error({ messageId: 'MsgJ_JMM018_19' });
                        }
                        self.mandatoryRetirementRegulation(new MandatoryRetirementRegulation(undefined));
                        self.setDefaultMandatoryRetireTerm();
                    }).always(function() {
                        block.clear();
                    });
                }else{
                    self.mandatoryRetirementRegulation(new MandatoryRetirementRegulation(undefined));
                    self.setDefaultMandatoryRetireTerm();
                    error({ messageId: 'MsgJ_JMM018_16' });
                }
            }else{
                self.isStart = false;    
            } 
        }
        
        add(){
            let self = this;
            if(self.getRelatedMaster()){
                block.grayout();
                let param = {historyId: self.selectedHistId(), 
                             baseDate: self.getSelectedStartDate(),
                             retirePlanCourseList: self.retirePlanCourseList,
                             commonMasterItems: self.commonMasterItems()}
                new service.add(param).done(function(data: any) {
                    console.log(data);
                    self.getMandatoryRetirementRegulation();
                }).fail(function(err) {
                    error({ messageId: err.messageId });
                }).always(function() {
                    block.clear();
                });
            }else{
                self.mandatoryRetirementRegulation(new MandatoryRetirementRegulation(undefined));
                self.setDefaultMandatoryRetireTerm();
                error({ messageId: 'MsgJ_JMM018_16' });
            }
        
        }
        
        openCDialog(item: any): void {
            let self = this;
            block.grayout();
            let employmentType = {
                listInfor: self,
                commonMasterItemName: item.commonMasterItemName,
                listSelect: item.enableRetirePlanCourse()
            }
            setShared('employmentTypeToC', employmentType);
            block.clear();
            nts.uk.ui.windows.sub.modal('/view/jmm/018/c/index.xhtml').onClosed(function(): any {
                let param = getShared('shareToJMM018B');
                if(param != undefined){
                    item.setEnableRetirePlanCourse(param, self);
                }
            })
        }
        
        loadHisId(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            new service.getLatestHistId().done(function(data: any) {
                self.latestHistId(data);
                if (self.latestHistId() == self.selectedHistId() && self.selectedHistId() != '' && self.selectedHistId() != null) {
                    self.isLatestHis(true);
                }else{
                    self.isLatestHis(false);
                }
                if(self.commonMasterItems().length > 0){
                    self.commonMasterItems()[0].usageFlg(false);    
                } 
                self.changeLatest = false;
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
                _.remove(__viewContext.enums.DateRule, function(n) {return n.value == 0 || n.value == 4 || n.value == 5;});  
                self.retirePlanCourseList = data.retirePlanCourseList;
                let tg = [];
                _.forEach(_.orderBy(data.commonMasterItems,['displayNumber'], ['asc']), (item) => {
                    tg.push(new GrpCmonMaster(item));
                });
                if(self.latestHistId() == null){
                    tg[0].usageFlg(true);
                }
                self.commonMasterItems(tg);
                self.getRelatedMaster(true);
            }).fail(function(err) {
                self.getRelatedMaster(false);
                self.commonMasterItems([]);
                self.retirePlanCourseList = [];
                error({ messageId: err.messageId });
            }).always(function() {
                dfd.resolve();
            });
            return dfd.promise();
        }

        update(): void{
            let self = this;        
            $('.judg').trigger("validate");
            let validate = true;
            let mandatoryRetireTerm = [];
            _.forEach(self.commonMasterItems(), (item) => {
                if(item.usageFlg() && item.enableRetirePlanCourse().length == 0){
                    error({ messageId: "MsgJ_JMM018_13"});
                    validate = false;
                    return;    
                }else if(item.enableRetirePlanCourse().length > 0){
                    mandatoryRetireTerm.push(item.collectMandatoryRetireTerm());    
                }
            });
            if(self.mandatoryRetirementRegulation().planCourseApplyTerm().applicationEnableStartAge() > self.mandatoryRetirementRegulation().planCourseApplyTerm().applicationEnableEndAge() && self.mandatoryRetirementRegulation().planCourseApplyFlg()){
                validate = false;
                error({ messageId: "MsgJ_JMM018_11"});
            }
            if(validate){
                self.mandatoryRetirementRegulation().mandatoryRetireTerm = mandatoryRetireTerm;
                let param = ko.toJS(self.mandatoryRetirementRegulation());
                param.historyId = self.selectedHistId();
                block.grayout();
                new service.update(param).done(function(data: any) {
                    info({ messageId: "Msg_15"});
                }).fail(function(err) {
                    error({ messageId: err.messageId });
                }).always(function() {
                    block.clear();
                });
            }
        }
        
        hidden(param: string, id: string){
            $('.hyperlink').removeClass('isDisabled');
            $('#'+ param).addClass('isDisabled');
            var elmnt = document.getElementById(id);
            elmnt.scrollIntoView();
        }
        
        setDefaultMandatoryRetireTerm(): void{
            let self = this;       
            //set default checks
            if(self.commonMasterItems().length > 0 && self.retirePlanCourseList.length > 0){ 
                let empCommonMasterItemId = _.orderBy(self.commonMasterItems() ,['displayNumber'], ['asc'])[0].commonMasterItemId;
                let retirePlanCourseFilter = _.filter(self.retirePlanCourseList, { 'retirePlanCourseClass': 0, 'durationFlg': 0 });
                if(retirePlanCourseFilter.length > 0){
                    let retirePlanCourseId = _.orderBy(retirePlanCourseFilter,['retirementAge'], ['desc'])[0].retirePlanCourseId;
                    let mandatoryRetireTerm = [{empCommonMasterItemId: empCommonMasterItemId, usageFlg: true, enableRetirePlanCourse:[{retirePlanCourseId: retirePlanCourseId}]}];
                    self.mandatoryRetirementRegulation().mandatoryRetireTerm = mandatoryRetireTerm;
                    let listItemCommon = self.commonMasterItems();
                    _.forEach(listItemCommon, (item) => {
                        item.usageFlg(false);
                        item.setEnableRetirePlanCourse([],[]);
                    });
                    self.commonMasterItems(listItemCommon);
                    self.commonMasterItems()[0].usageFlg(true);
                    self.commonMasterItems()[0].setEnableRetirePlanCourse([{retirePlanCourseId: retirePlanCourseId}],self.retirePlanCourseList);
                }
            }
        }
    }
    
    export interface IMandatoryRetirementRegulation {
        historyId: string;
        reachedAgeTerm: number;
        publicTerm: IDateCaculationTerm;
        retireDateTerm: IRetireDateTerm;
        planCourseApplyFlg: boolean;
        mandatoryRetireTerm: IMandatoryRetireTerm [];
        referEvaluationTerm: IReferEvaluationItem [];
        planCourseApplyTerm: IPlanCourseApplyTerm;
    }
    
    class MandatoryRetirementRegulation {
        reachedAgeTerm: KnockoutObservable<number>;
        publicTerm: KnockoutObservable<DateCaculationTerm>;
        retireDateTerm: KnockoutObservable<RetireDateTerm>;
        planCourseApplyFlg: KnockoutObservable<boolean>;
        mandatoryRetireTerm: [];
        referEvaluationTerm: KnockoutObservableArray<[]>;
        planCourseApplyTerm: KnockoutObservable<PlanCourseApplyTerm>;
        constructor(param: IMandatoryRetirementRegulation) {
            let self = this;
            self.reachedAgeTerm = ko.observable(param? param.reachedAgeTerm: 0);
            self.publicTerm = ko.observable(param? new DateCaculationTerm(param.publicTerm): new DateCaculationTerm(undefined));
            self.retireDateTerm = ko.observable(param? new RetireDateTerm(param.retireDateTerm): new RetireDateTerm(undefined));
            self.planCourseApplyFlg = ko.observable(param? param.planCourseApplyFlg: false);
            self.planCourseApplyFlg.subscribe(function(val){
                $('.judg').trigger("validate");
            });
            self.mandatoryRetireTerm = param? param.mandatoryRetireTerm: [];
            if(param != undefined){
                let tg = [];
                _.forEach(param.referEvaluationTerm, (item) => {
                    tg.push(new ReferEvaluationItem(item, 0));
                });
                self.referEvaluationTerm = ko.observableArray(tg);    
            }else{
                let tg = [];
                for(var i = 0; i< 3; i++){
                    tg.push(new ReferEvaluationItem(undefined, i));
                }
                self.referEvaluationTerm = ko.observableArray(tg); 
            }
            self.reachedAgeTerm.subscribe(function(val){
                console.log(val);
            });
            self.planCourseApplyTerm = ko.observable(param? new PlanCourseApplyTerm(param.planCourseApplyTerm): new PlanCourseApplyTerm(undefined));
        }
    }
    
    export interface IDateCaculationTerm {
        calculationTerm: number;
        dateSettingNum: number;
        dateSettingDate: number;
    }
    
    class DateCaculationTerm {
        calculationTerm: KnockoutObservable<number>;
        dateSettingDate: KnockoutObservable<number>;
        dateSettingNum: KnockoutObservable<number>;
        dateSettingNumRequire: KnockoutObservable<boolean>;
        constructor(param: IDateCaculationTerm) {
            let self = this;
            self.calculationTerm = ko.observable(param ? param.calculationTerm : 1);
            self.dateSettingDate = ko.observable(param ? param.dateSettingDate : '');
            self.dateSettingNum = ko.observable(param ? param.dateSettingNum : '');
            self.dateSettingNumRequire = ko.observable(false);
            self.calculationTerm.subscribe(function(val){
                if(val == 2 || val == 3){
                    self.dateSettingNumRequire(true);    
                }else{
                    self.dateSettingNumRequire(false);
                }
                $('.judg').trigger("validate");
            });
            if(param != undefined && (param.calculationTerm == 2 || param.calculationTerm == 3)){
                self.dateSettingNumRequire(true);
                setTimeout(() => {
                    $('.judg').trigger("validate");                     
                }, 1);    
            }
        }
    }

    export interface IRetireDateTerm {
        retireDateTerm: number;
        retireDateSettingDate: number;
    }
    
    class RetireDateTerm {
        retireDateTerm: KnockoutObservable<number>;
        retireDateSettingDate: KnockoutObservable<number>;
        constructor(param: IRetireDateTerm) {
            let self = this;
            self.retireDateTerm = ko.observable(param ? param.retireDateTerm : 0);
            self.retireDateSettingDate = ko.observable(param ? param.retireDateSettingDate : '');
        }
    }

    export interface IMandatoryRetireTerm {
        empCommonMasterItemId: string;
        usageFlg: boolean;
        enableRetirePlanCourse: any;
    }

    export interface IReferEvaluationItem {
        evaluationItem: number;
        usageFlg: boolean;
        displayNum: number;
        passValue: string;
    }
    
    class ReferEvaluationItem {
        name: string;
        evaluationItem: number;
        usageFlg: KnockoutObservable<boolean>;
        displayNum: KnockoutObservable<number>;
        passValue: KnockoutObservable<string>;
        constructor(param: IReferEvaluationItem, order: number) {
            let self = this;
            self.evaluationItem = param ? param.evaluationItem : order;
            self.name = _.find(__viewContext.enums.EvaluationItem, t => t.value == self.evaluationItem).name;
            self.usageFlg = ko.observable(param ? param.usageFlg : false);
            self.displayNum = ko.observable(param ? param.displayNum : 1);
            self.passValue = ko.observable(param ? param.passValue : 'B');
            self.usageFlg.subscribe(function(val){
                $('.judg').trigger("validate");
            });
        }
    }

    export interface IPlanCourseApplyTerm {
        applicationEnableStartAge: number;
        applicationEnableEndAge: number;
        endMonth: number;
        endDate: number;
    }
    
    class PlanCourseApplyTerm {
        applicationEnableStartAge: KnockoutObservable<number>;
        applicationEnableEndAge: KnockoutObservable<number>;
        endMonth: KnockoutObservable<number>;
        endDate: KnockoutObservable<number>;
        kt: boolean;
        constructor(param: IPlanCourseApplyTerm) {
            let self = this;
            self.applicationEnableStartAge = ko.observable(param ? param.applicationEnableStartAge : 50);
            self.applicationEnableEndAge = ko.observable(param ? param.applicationEnableEndAge : 59);
            self.endMonth = ko.observable(param ? param.endMonth : 12);
            self.endDate = ko.observable(param ? param.endDate : 31);
            kt = true;
            self.applicationEnableStartAge.subscribe(function(x){
                if(x > self.applicationEnableEndAge() && kt){
                    error({ messageId: "MsgJ_JMM018_11"});
                    kt = false;
                }else{
                    kt = true;    
                }
            });
            self.applicationEnableEndAge.subscribe(function(y){
                if(y < self.applicationEnableStartAge() && kt){
                    error({ messageId: "MsgJ_JMM018_12"});
                   kt = false;
                }else{
                    kt = true;    
                }
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
        usageFlg: KnockoutObservable<boolean>;
        enableRetirePlanCourse: KnockoutObservableArray<any>;
        enableRetirePlanCourseText: KnockoutObservable<string>;
        constructor(param: IGrpCmonMaster) {
            let self = this;
            self.displayNumber = param.displayNumber;
            self.usageFlg = ko.observable(false);
            self.commonMasterItemId = param.commonMasterItemId;
            self.commonMasterItemName = param.commonMasterItemName;
            self.enableRetirePlanCourse = ko.observable([]); 
            self.enableRetirePlanCourseText = ko.observable("");
        }
        setEnableRetirePlanCourse(enableRetirePlanCourse: any[], master: any[]): void{
            let self = this;
            self.enableRetirePlanCourse(enableRetirePlanCourse);
            let names = '';
            _.forEach(enableRetirePlanCourse, (id) => {
                let tg = _.find(master, h => h.retirePlanCourseId == id.retirePlanCourseId)
                if(tg){
                    let name = tg.retirePlanCourseName + ' (' +tg.retirementAge.toString() +  getText('JMM018_C222_16') + '、' + _.find(__viewContext.enums.DurationFlg, h => h.value == tg.durationFlg).name + ')'; 
                    if(names == ''){
                        names = name;     
                    }else{
                        names = names + '　、' + name; 
                    }
                }
            });
            self.enableRetirePlanCourseText(names);
        }
        
        collectMandatoryRetireTerm (): any{
            let self = this;
            return {empCommonMasterItemId: self.commonMasterItemId,
                    usageFlg: self.usageFlg(),
                    enableRetirePlanCourse: self.enableRetirePlanCourse()}
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
