module jhn001.c.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import showDialog = nts.uk.ui.dialog;
    import text = nts.uk.resource.getText;
    import lv = nts.layout.validate;
    import format = nts.uk.text.format;
    import vc = nts.layout.validation;
    import subModal = nts.uk.ui.windows.sub.modal;

    const __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];

    export class ViewModel {
        layouts: KnockoutObservableArray<ILayout> = ko.observableArray([]);
        layout: KnockoutObservable<Layout> = ko.observable(new Layout()); 
        reportId : KnockoutObservable<string> = ko.observable('');
        enaGoBack : KnockoutObservable<boolean> = ko.observable(false);
        enaSave : KnockoutObservable<boolean> = ko.observable(true);
        enaSaveDraft : KnockoutObservable<boolean> = ko.observable(true);
        enaAttachedFile : KnockoutObservable<boolean> = ko.observable(true);
        enaRemove : KnockoutObservable<boolean> = ko.observable(true);
        
        constructor() {
            let self = this,
                layout = self.layout,
                layouts = self.layouts;
            
            self.start();
            
            nts.uk.ui.guide.operateCurrent('guidance/guideOperate', { screenGuideParam: [{ programId: 'JHN001', screenId: 'C' }] },
                Page.NORMAL);
        }
        
        getFrameIndex(loopPhase, loopFrame, loopApprover) {
            let self = this;
            if (_.size(loopFrame.listApprover()) > 1) {
                return _.findIndex(loopFrame.listApprover(), o => o == loopApprover);
            }
            return _.findIndex(loopPhase.listApprovalFrame(), o => o == loopFrame);
        }
        
        frameCount(listFrame) {
            let self = this;
            if (_.size(listFrame) > 1) {
                return _.size(listFrame);
            }
            return _.chain(listFrame).map(o => self.approverCount(o.listApprover())).value()[0];
        }
        
        approverCount(listApprover) {
            let self = this;
            return _.chain(listApprover).countBy().values().value()[0];     
        }        
        
        getApproverAtr(approver) {
            if (approver.approverMail().length > 0) {
                return approver.approverName() + '(@)';
            } else {
                return approver.approverName();
            }
        }
        
        /*　承認ボタン*/
        approve():void {
            let self = this,
            layout = self.layout(),
            cmd: any = {reportId: layout.reportId(),
             approveComment: layout.approveComment(),
             actionApprove: ACTION_PROVE.APPROVE};  
            invisible();
            service.saveData(cmd).done(function(data) {
                showDialog.info({ messageId: "Msg_15" }).then(function() {
                    self.start();
                });
                unblock();
            }).fail(function(res: any) {
                unblock();
            });
        
        }
        
        /* 否認ボタン*/
        deny(): void{
            let self = this,
            layout = self.layout(),
            cmd: any = {reportId: layout.reportId(),
             approveComment: layout.approveComment(),
             actionApprove: ACTION_PROVE.DENY};  
            invisible();
            service.saveData(cmd).done(function(data) {
                showDialog.info({ messageId: "Msg_15" }).then(function() {
                    self.start();
                });
                unblock();
            }).fail(function(res: any) {
                unblock();
            });    
        }
        
        /* 差し戻し*/
        sendBack(): void{
            let self = this,
            layout = self.layout();
            setShared('JHN001D_PARAMS', {reportId: layout.reportId()});
            invisible();
            modal('/view/jhn/001/d/index.xhtml', { title: '' }).onClosed(function(): any {
                unblock();
            });
        }
        
        /*　解除*/
        cancel(): void{
            let self = this,
            layout = self.layout(),
            cmd: any = {reportId: layout.reportId(),
             approveComment: layout.approveComment(),
             actionApprove: ACTION_PROVE.CANCEL};  
            invisible();
            service.saveData(cmd).done(function(data) {
                showDialog.info({ messageId: "Msg_15" }).then(function() {
                    self.start();
                });
                unblock();
            }).fail(function(res: any) {
                unblock();
            });    
        
        }
        
        /* 登録*/
        register(): void{
            let self = this,
            layout = self.layout(),
            cmd: any = {reportId: layout.reportId(),
             approveComment: layout.approveComment(),
             actionApprove: ACTION_PROVE.REGISTER};  
            invisible();
            service.saveData(cmd).done(function(data) {
                showDialog.info({ messageId: "Msg_15" }).then(function() {
                    self.start();
                });
                unblock();
            }).fail(function(res: any) {
                unblock();
            });    
             
        }
        
        start(code?: string): JQueryPromise<any> {
            let self = this,
                layout: Layout = self.layout(),
                layouts = self.layouts,
                dfd = $.Deferred();
            //get param url
            let url = $(location).attr('search');
            let reportId: string = url.split("=")[1];
            // get all layout
            layouts.removeAll();
            service.getDetails({reportId: reportId, screenC: true}).done((data: any) => {
                if (data) {
                    lv.removeDoubleLine(data.classificationItems);
                    self.getDetailReport(layout, data);
                    _.defer(() => {
                        new vc(layout.classifications());
                    });
                } else {
                    layout.classifications.removeAll();
                }
                
                //setTimeout(function(){
                //     $("#C222_3_1").focus();
               // }, 1000);
               
                
                unblock();
            });
            return dfd.promise();
        }
        
        getDetailReport(layout: any, data: any) {
            
            layout.reportId(data.reportId);
            
            layout.reportName(data.reportName);
            
            layout.message(text('JHN001_C222_1') + ' : ' + data.message);
            
            layout.backComment(data.backComment);
            
            layout.backCommentContent(text('JHN001_C222_2') + ' : ' +data.backCommentContent);
            
            layout.approveComment(data.approveComment);
            
            layout.denyOraprrove(data.denyOraprrove);
            
            layout.classifications(data.classifications);
            
            layout.approvalRootState(data.approvalRootState);
            
            layout.listDocument(data.listDocument);
            
            layout.classifications(data.classificationItems || []);
            
            layout.approvalRootState(ko.mapping.fromJS(data.listApprovalFrame)()|| []);
            
            var lstDoc = [];
            
            for (var i = 0; i < data.documentSampleDto.length; i++) {
                
                let fileData = data.documentSampleDto[i];

                let urlFileSample = fileData.sampleFileId == null || fileData.sampleFileId == '' ? '#' : nts.uk.request.file.liveViewUrl(fileData.sampleFileId),
                
                 urlFile = fileData.fileId == null || fileData.fileId == '' ? '#' : nts.uk.request.file.liveViewUrl(fileData.fileId),
                
                 isShow = true;
                
                if(fileData.sampleFileName == null || fileData.sampleFileName == ''){
                    
                    isShow = false;    
                    
                }
                
                let obj = {
                    
                    docName: fileData.docName,
                    
                    ngoactruoc: !isShow ? '' : '(',
                    
                    sampleFileName: !isShow ? '' : '<a href=' + urlFileSample + ' target="_blank">' + fileData.sampleFileName + '</a>',
                   
                    ngoacsau:   !isShow ? '' : ')',
                   
                    fileName: fileData.fileName == null || fileData.fileName == '' ? '' : '<a style="color: blue;" href=' + urlFile + ' target="_blank">' + fileData.fileName + '</a>',
                    
                    cid: fileData.cid,
                    
                    reportLayoutID: fileData.reportLayoutID,
                    
                    docID: fileData.docID,
                   
                    dispOrder: fileData.dispOrder,
                   
                    requiredDoc: fileData.requiredDoc,
                   
                    docRemarks: fileData.docRemarks,
                    
                    sampleFileId: fileData.sampleFileId,
                   
                    reportID: fileData.reportID,
                    
                    fileId: fileData.fileId,
                   
                    fileSize: fileData.fileSize,
                }
                
                lstDoc.push(obj);
                
            }
            
            layout.listDocument(lstDoc);

        }
        
        attachedFile() {
            let self = this,
                layout = self.layout,
                layouts = self.layouts;
            
            
        }
    }
    
    interface IItemDefinition {
        id: string;
        perInfoCtgId?: string;
        itemCode?: string;
        itemName?: string;
        dispOrder: number;
    }

    interface ILayout {
        reportId: int;
        reportName?: string;
        message?: string;
        backComment?: boolean;
        backCommentContent?: string;
        approveComment?: string;
        denyOraprrove?: boolean;
        action?: number;
        classifications?: Array<IItemClassification>;
        outData?: Array<any>;
        approvalRootState?: Array<any>;
        listDocument?: Array<any>;
        listApprovalFrame?: Array<any>;
    }

    class Layout {
        reportId: KnockoutObservable<number> = ko.observable('');
        reportName: KnockoutObservable<string> = ko.observable('');
        message: KnockoutObservable<string> = ko.observable('');
        backComment: KnockoutObservable<boolean> = ko.observable(true);
        backCommentContent: KnockoutObservable<string> = ko.observable('');
        approveComment: KnockoutObservable<string> = ko.observable('');
        mode: KnockoutObservable<TABS> = ko.observable(TABS.LAYOUT);
        showColor: KnockoutObservable<boolean> = ko.observable(false);
        denyOraprrove: KnockoutObservable<boolean> = ko.observable(true);
        classifications: KnockoutObservableArray<any> = ko.observableArray([]);
        outData: KnockoutObservableArray<any> = ko.observableArray([]);
        approvalRootState : KnockoutObservableArray<any> = ko.observableArray([]);
        listDocument : KnockoutObservableArray<any> = ko.observableArray([]);
        approvalRootState: KnockoutObservableArray<any> = ko.observableArray([]);
        constructor() {
            let self = this;
//            self.reportId(param.reportId);
//            self.reportName(param.reportName);
//            self.message(param.message);
//            self.backComment(param.backComment);
//            self.backCommentContent(param.backCommentContent);
//            self.approveComment(param.approveComment);
//            self.denyOraprrove(param.denyOraprrove);
//            self.classifications(param.classifications);
//            self.approvalRootState(param.approvalRootState);
//            self.listDocument(param.listDocument);
            
        }
        
        clickSampleFileName() {
            let rowData: any = this;
            if (rowData.sampleFileId) {
                nts.uk.request.ajax("/shr/infra/file/storage/infor/" + rowData.sampleFileId).done(function(res) {
                    nts.uk.request.specials.donwloadFile(rowData.sampleFileId);
                });
            }
        }

        clickFileName() {
            let rowData: any = this;
            if (rowData.fileId) {
                nts.uk.request.ajax("/shr/infra/file/storage/infor/" + rowData.fileId).done(function(res) {

                    //                    nts.uk.request.ajax("/shr/infra/file/storage/infor/" + rowData.originalName).done(function(res) {
                    //                        console.log(res);
                    //                    }).fail(function(error) {
                    //                       console.log(error);
                    //                    });
                    nts.uk.request.specials.donwloadFile(rowData.fileId);
                });
            }
        }
    }
    
    interface ICategory {
        id: string;
        categoryCode?: string;
        categoryName?: string;
        categoryType?: IT_CAT_TYPE;
    }

    export enum TABS {
        LAYOUT = <any>"layout",
        CATEGORY = <any>"category"
    }

    export interface IPeregQuery {
        ctgId: string;
        ctgCd?: string;
        empId: string;
        standardDate: Date;
        infoId?: string;
    }

    export interface ILayoutQuery {
        layoutId: string;
        browsingEmpId: string;
        standardDate: Date;
    }

    export interface IPeregCommand {
        personId: string;
        employeeId: string;
        inputs: Array<IPeregItemCommand>;
    }

    export interface IPeregItemCommand {
        /** category code */
        categoryCd: string;
        /** Record Id, but this is null when new record */
        recordId: string;
        /** input items */
        items: Array<IPeregItemValueCommand>;
    }

    export interface IPeregItemValueCommand {
        definitionId: string;
        itemCode: string;
        value: string;
        'type': number;
    }

    export interface IParam {
        showAll?: boolean;
        employeeId: string;
        categoryId?: string;
    }

    export interface IEventData {
        id: string;
        iid?: string;
        tab: TABS;
        act?: string;
        ccode?: string;
        ctype?: IT_CAT_TYPE;
    }

    // define ITEM_CATEGORY_TYPE
    export enum IT_CAT_TYPE {
        SINGLE = 1, // Single info
        MULTI = 2, // Multi info
        CONTINU = 3, // Continuos history
        NODUPLICATE = 4, //No duplicate history
        DUPLICATE = 5, // Duplicate history,
        CONTINUWED = 6 // Continuos history with end date
    }

    export enum ITEM_SINGLE_TYPE {
        STRING = 1,
        NUMERIC = 2,
        DATE = 3,
        TIME = 4,
        TIMEPOINT = 5,
        SELECTION = 6
    }
    
    enum LAYOUT_ACTION {
        INSERT = 0,
        UPDATE = 1,
        COPY = 2,
        OVERRIDE = 3,
        REMOVE = 4
    }
    
    enum ACTION_PROVE {
        APPROVE = 0,
        DENY = 1,
        BACK = 2,
        CANCEL = 3,
        REGISTER = 4
    }
    
        
    enum Page {
        NORMAL = 0,
        SIDEBAR = 1,
        FREE_LAYOUT = 2
    }
}