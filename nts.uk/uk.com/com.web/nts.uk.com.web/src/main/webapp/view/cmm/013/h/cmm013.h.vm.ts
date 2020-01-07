module nts.uk.com.view.cmm013.h.viewmodel {
    import service = nts.uk.com.view.cmm013.h.service;   
    import text = nts.uk.resource.getText;
    import dialog = nts.uk.ui.dialog; 
    export class ScreenModel {
        approverJobLst: KnockoutObservableArray<IApproverJob> = ko.observableArray([]);
        approverGroupLst: KnockoutObservableArray<IApproverGroup> = ko.observableArray([]);
        currentApproverGroup: KnockoutObservable<ApproverGroup> = ko.observable(new ApproverGroup({ approverGroupCD: "", approverGroupName: "", approverJobList: [] }));
        currentApproverGroupCD: KnockoutObservableArray<string> = ko.observable();
        currentApproverJobCD: KnockoutObservableArray<string> = ko.observable();
        baseDate: any = moment.utc();
        listTitleInfo: any = [];
        isInsertNew: KnockoutObservableArray<boolean> = ko.observable(true);
        constructor() {
            let self = this;
            self.currentApproverGroupCD.subscribe((value) => {
                if(_.isEmpty(value)) {
                    self.isInsertNew(true);
                    self.currentApproverGroup().approverGroupCD("");
                    self.currentApproverGroup().approverGroupName("");
                    self.currentApproverGroup().approverJobList = []; 
                } else {
                    self.currentApproverGroup(self.getCurrentApproverGroup());      
                    self.approverJobLst(self.getApproverJobLst(
                        self.listTitleInfo, 
                        _.map(_.sortBy(self.currentApproverGroup().approverJobList, a => a.order), o => o.jobID))
                    );
                    self.currentApproverJobCD(self.getCurrentApproverJobCD()); 
                    self.isInsertNew(false);      
                }
            });
        }
        
        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred<any>();
            nts.uk.ui.block.invisible();
            let dfdTitleInfo = service.findAllJobTitle({ baseDate: self.baseDate });
            let dfdApproverGroup = service.findAll();
            $.when(dfdTitleInfo, dfdApproverGroup).done((dataTitleInfo, dataApproverGroup) => {
                self.listTitleInfo = dataTitleInfo;
                self.initData(dataApproverGroup);
                nts.uk.ui.block.clear();
                dfd.resolve();    
            }).fail((res1, res2) => {
                dialog.alertError({ messageId: res1.messageId })
                .then(function() { 
                    nts.uk.ui.block.clear();
                });
                dialog.alertError({ messageId: res2.messageId })
                .then(function() { 
                    nts.uk.ui.block.clear();
                });
                dfd.reject();
            });
            return dfd.promise();
        }
        
        public reload(index?) {
            let self = this;
            nts.uk.ui.block.invisible();
            service.findAll()
            .done((data: Array<IApproverGroup>) => {
                self.initData(data, index);
                nts.uk.ui.block.clear(); 
            }).fail((res) => {
                dialog.alertError({ messageId: res.messageId })
                .then(function() { 
                    nts.uk.ui.block.clear();
                });     
            });        
        }
        
        public initData(data, index?) {
            let self = this;  
            self.approverGroupLst(_.sortBy(data, o => o.approverGroupCD));
            if(_.isEmpty(self.currentApproverGroupCD())) {
                if(_.isEmpty(self.approverGroupLst())) {
                    self.createNew();    
                } else {
                    self.isInsertNew(false);
                    self.currentApproverGroupCD(_.first(self.approverGroupLst()).approverGroupCD);     
                }
            } else {
                let containCD = _.includes(_.map(self.approverGroupLst(), o => o.approverGroupCD), self.currentApproverGroupCD());
                if(containCD) {
                    self.isInsertNew(false);
                    self.currentApproverGroup(self.getCurrentApproverGroup());       
                } else {
                    if(_.isEmpty(self.approverGroupLst())) {
                        self.currentApproverGroupCD("");     
                    } else {
                        self.isInsertNew(false);
                        if(index==_.size(self.approverGroupLst())) {
                            self.currentApproverGroupCD(self.approverGroupLst()[index-1].approverGroupCD);    
                        } else {
                            self.currentApproverGroupCD(self.approverGroupLst()[index].approverGroupCD);      
                        }
                    }   
                }      
            }
            self.approverJobLst(self.getApproverJobLst(
                self.listTitleInfo, 
                _.map(_.sortBy(self.currentApproverGroup().approverJobList, a => a.order), o => o.jobID))
            );
            self.currentApproverJobCD(self.getCurrentApproverJobCD());  
        }
        
        public getCurrentApproverGroup() {
            let self = this;
            return new ApproverGroup(_.find(self.approverGroupLst(), o => o.approverGroupCD == self.currentApproverGroupCD()));    
        }
        
        public getCurrentApproverJob(currentCode: string) {
            let self = this;
            return _.first(_.find(self.approverGroupLst(), o => o.approverGroupCD == currentCode).approverJobList).jobCD;    
        }
    
        public getApproverJobLst(listTitleInfo: any, output: any) {
            let self = this;
            return _.chain(output)
                    .map(o => {
                        let info = _.find(listTitleInfo, a => a.id == o);
                        if(info) {
                            return _.assign({
                                jobID: info.id,
                                jobCD: info.code,
                                jobName: info.name        
                            });   
                        } else {
                            return null;    
                        }    
                    })
                    .filter(o => !_.isNull(o))
                    .value();
        }
    
        public getCurrentApproverJobCD() {
            let self = this;
            if(_.isEmpty(self.approverJobLst())){
                return "";    
            }
            return _.first(self.approverJobLst()).jobCD;    
        }
    
        public getCommand() {
            let self = this;
            return {
                approverGroupCD: self.currentApproverGroup().approverGroupCD(),
                approverGroupName: self.currentApproverGroup().approverGroupName(),
                approverJobList: _.map(self.approverJobLst(), (o, index) => _.assign({
                    jobID: o.jobID,
                    order: index + 1,    
                }))
            }    
        }
        
        public createNew() {
            let self = this;
            self.currentApproverGroupCD("");
        }
        
        public register() {
            let self = this;
            $('#approverGroupCD-input').trigger('validate');
            $('#approverGroupName-input').trigger('validate');
            if(nts.uk.ui.errors.hasError()) {
                return;    
            }
            if(self.isInsertNew()) {
                nts.uk.ui.block.invisible();
                service.register(self.getCommand()).done((data) => {
                    dialog.info({ messageId: "Msg_15" }).then(function() {
                        self.reload();
                        nts.uk.ui.block.clear();        
                    });        
                }).fail((res) => {
                    dialog.alertError({ messageId: res.messageId })
                    .then(function() { 
                        nts.uk.ui.block.clear();
                    });      
                });    
            } else {
                nts.uk.ui.block.invisible();
                service.update(self.getCommand()).done((data) => {
                    dialog.info({ messageId: "Msg_15" }).then(function() {
                        self.reload();
                        nts.uk.ui.block.clear();        
                    });        
                }).fail((res) => {
                    dialog.alertError({ messageId: res.messageId })
                    .then(function() { 
                        nts.uk.ui.block.clear();
                    });      
                });
            }
        }
        
        public multiInsert() {
            let self = this,
                commandLst = [],
                approverGroupLstCD = _.map(self.approverGroupLst(), o => o.approverGroupCD);
            _.forEach(self.listTitleInfo, o => {
                if(!_.includes(approverGroupLstCD, o.code)) {
                    commandLst.push({
                        approverGroupCD: o.code,
                        approverGroupName: o.name + text("CMM013_73"),
                        approverJobList: [{
                            jobID: o.id,
                            order: 1,    
                        }]
                    });        
                }    
            });
            nts.uk.ui.block.invisible();
            dialog.confirm({ messageId: "Msg_1591" }).ifYes(() => {
                service.multiInsert(commandLst).done((data) => {
                    dialog.info({ messageId: "Msg_1592" }).then(function() {
                        self.reload();
                        nts.uk.ui.block.clear();        
                    });        
                }).fail((res) => {
                    dialog.alertError({ messageId: res.messageId })
                    .then(function() { 
                        nts.uk.ui.block.clear();
                    });      
                });
            }).ifNo(() => {
                nts.uk.ui.block.clear();
            });
        }
    
        public close(): void {
            nts.uk.ui.windows.close();
        }
        
        public remove() {
            let self = this,
                index = _.findIndex(self.approverGroupLst(), o => o.approverGroupCD == self.currentApproverGroup().approverGroupCD());
                command = ko.toJS(self.currentApproverGroup()); 
            nts.uk.ui.block.invisible();
            dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                service.remove(command).done((data) => {
                    dialog.info({ messageId: "Msg_16" }).then(function() {
                        self.reload(index);
                        nts.uk.ui.block.clear();        
                    });        
                }).fail((res) => {
                    dialog.alertError({ messageId: res.messageId })
                    .then(function() { 
                        nts.uk.ui.block.clear();
                    });      
                });
            }).ifNo(() => {
                nts.uk.ui.block.clear();
            });
        }
    
        public openKDL004(): void {
            let self = this;
            nts.uk.ui.windows.setShared('inputCDL004', {
                baseDate: self.baseDate,
                selectedCodes: _.map(self.approverJobLst(), o => o.jobID),
                showNoSelection: true,
                isMultiple: true,
                isShowBaseDate: false
            }, true);
            nts.uk.ui.windows.sub.modal("com", "/view/cdl/004/a/index.xhtml").onClosed(function() {
                let isCancel = nts.uk.ui.windows.getShared('CDL004Cancel');
                if (isCancel) {
                    return;
                }
                let output = nts.uk.ui.windows.getShared('outputCDL004');
                self.approverJobLst(self.getApproverJobLst(self.listTitleInfo, output));
                self.currentApproverJobCD(self.getCurrentApproverJobCD());
            });  
                
        }
    }
    
    export class IApproverGroup {
        approverGroupCD: string;
        approverGroupName: string;
        approverJobList: Array<IApproverJob>;        
    }
    
    export class IApproverJob {
        jobID; string;
        jobCD: string;
        jobName: string;  
    }
    
    export class ApproverGroup {
        approverGroupCD: KnockoutObservable<string>;
        approverGroupName: KnockoutObservable<string>;
        approverJobList: Array<IApproverJob>;
        constructor(approverGroup: IApproverGroup) {
            this.approverGroupCD = ko.observable(approverGroup.approverGroupCD);
            this.approverGroupName = ko.observable(approverGroup.approverGroupName);
            this.approverJobList = approverGroup.approverJobList;   
        }            
    }
}