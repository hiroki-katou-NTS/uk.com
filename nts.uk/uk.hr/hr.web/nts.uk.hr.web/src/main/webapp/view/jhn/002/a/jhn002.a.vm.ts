module jhn002.a.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import showDialog = nts.uk.ui.dialog;
    import text = nts.uk.resource.getText;
    import lv = nts.layout.validate;
    import format = nts.uk.text.format;
    import vc = nts.layout.validation;
    import subModal = nts.uk.ui.windows.sub.modal;
    import hasError = nts.uk.ui.errors.hasError;
    import info = nts.uk.ui.dialog.info;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import jump = nts.uk.request.jump;

    const __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];


    export class ViewModel {
        layouts: KnockoutObservableArray<ILayout> = ko.observableArray([]);
        layout: KnockoutObservable<Layout> = ko.observable(new Layout());
        reportClsId: KnockoutObservable<string> = ko.observable('');

        enaGoBack: KnockoutObservable<boolean> = ko.observable(false);
        enaSave: KnockoutObservable<boolean> = ko.observable(true);
        enaAttachedFile: KnockoutObservable<boolean> = ko.observable(true);
        enaGetEmployeeAgent: KnockoutObservable<boolean> = ko.observable(true);
        
        listItemDf = [];
        missingDocName = '';
        reportIdFromUrl = null;
        isNewMode = false;
        isUpdateMode = false;

        reportColums: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: '', key: 'id', width: 0, hidden: true },
            { headerText: text('JHN002_A221_4_1'), key: 'reportName', width: 190, hidden: false },
            { headerText: text('JHN002_A221_4_2'), key: 'remark', width: 130, hidden: false, formatter: _.escape }
        ]);

        constructor(reportId) {
            let self = this,
                layout = self.layout(),
                layouts = self.layouts;
            
            $('#menu-header').addClass("notranslate");
            
            $('.input-wrapper').addClass("notranslate");
            
            nts.uk.ui.guide.operateCurrent('guidance/guideOperate', { screenGuideParam: [{ programId: 'JHN002', screenId: 'A' }] },
            Page.NORMAL);

            if (reportId) {
                self.reportIdFromUrl = reportId;
                self.isNewMode = false;
                self.isUpdateMode = true;
            }else {
                self.reportIdFromUrl = null;
                self.isNewMode = true;
                self.isUpdateMode = false;
            }
            
            self.reportClsId.subscribe(id => {
                self.listItemDf = [];
                if (id) {

                    nts.uk.ui.errors.clearAll();
                    self.enaGetEmployeeAgent(true);

                    block();
                    let objReport = _.find(self.layouts(), function(o) { return o.id == id; })

                    if (objReport == undefined || objReport == null) {
                        return;
                    }

                    let query = {
                        reportId: string = null,
                        reportLayoutId: number = objReport.reportClsId
                    };

                    service.getReportDetails(query).done((data: any) => {
                        if (data) {
                            lv.removeDoubleLine(data.classificationItems);
                            self.layout().listItemCls(data.classificationItems || []);

                            if (data.classificationItems.length > 0) {
                               
                                $('.layout-control .drag-panel').attr(`style`, `height: 100% !important;` + `padding: 10 !important;`);
                                
                                self.setListItemDf(data.classificationItems);
                            } else {
                                
                                $('.layout-control .drag-panel').attr(`style`,  `height: 0 !important;` + `padding: 0 !important;`);

                            }

                            // set sendBackComment header A222_2_1
                            let sendBackCommentVar =  objReport.sendBackComment == null ? '' : objReport.sendBackComment;
                            layout.sendBackComment(text('JHN001_A222_2_1') + ' : ' + sendBackCommentVar);

                            // set message header A222_1_1
                            layout.message(text('JHN001_A222_1_1') + ' : ' + data.message);
                            
                            // set reportName
                            layout.reportNameLabel(data.reportName);
                            
                            layout.agentName(data.appBussinessName);
                            layout.agentSid(data.appSid);

                            // set list file document
                            self.setListDocument(data.documentSampleDto);
                            
                            // set table nguoi app
                            layout.approvalRootState(data.approvalRootState);
                            layout.approvalRootState(ko.mapping.fromJS(data.listApprovalFrame)()|| []);
                            

                            _.defer(() => {
                                new vc(self.layout().listItemCls());
                            });
                            
                            if(data.appBussinessName == null || data.appBussinessName == ''){
                                self.enaSave(false);    
                            }else{
                                 self.enaSave(true);
                            }
                            
                            unblock();
                        } else {
                            self.layout().listItemCls.removeAll();
                            unblock();
                        }
                    }).fail(mgs => {
                        self.layout().showColor(true);
                        self.layout().listItemCls.removeAll();
                        unblock();
                    });
                    self.setHightContent();
                }else{
                    self.newMode();
                    self.enaSave(false);
                    self.enaGetEmployeeAgent(false);
                }
            });

            self.start(self.reportIdFromUrl);
        }

        setListItemDf(clsItems: any) {
            let self = this,
                itemDfs = [];
            for (let i = 0; i < clsItems.length; i++) {
                if (clsItems[i].items != undefined || clsItems[i].layoutItemType != "SeparatorLine") {
                    for (let j = 0; j < clsItems[i].items.length; j++) {
                        let item = clsItems[i].items[j];
                        let obj = {
                            categoryId: clsItems[i].personInfoCategoryID,
                            categoryCode: clsItems[i].categoryCode,
                            categoryName: clsItems[i].categoryName,
                            ctgType: clsItems[i].ctgType,
                            layoutItemType: clsItems[i].layoutItemType,
                            layoutDisOrder: clsItems[i].dispOrder,
                            dispOrder: item.dispOrder,
                            itemDefId: item.itemDefId,
                            itemCode: item.itemCode,
                            itemName: item.itemName
                        }
                        self.listItemDf.push(obj);
                    }
                }
            }
        }
        
        // param = { layoutReportId , reportId }
        getListDocument(param): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            var dfdGetData = service.getListDoc(param);

            block();
            $.when(dfdGetData).done((listdatafile: any) => {
                if (listdatafile) {
                    self.setListDocument(listdatafile);
                }
                unblock();
                dfd.resolve();
            });
            return dfd.promise();
        }

        setListDocument(listdatafile: any) {
            let self = this;
            var lstDoc = [];
            var missingDocName = '';
            for (var i = 0; i < listdatafile.length; i++) {
                let fileData = listdatafile[i];
                if (fileData.fileName == null) {
                    missingDocName = missingDocName + fileData.docName + text('JHN001_B2_3_7_1');
                }

                let urlFileSample = fileData.sampleFileId == null || fileData.sampleFileId == '' ? '#' : nts.uk.request.file.liveViewUrl(fileData.sampleFileId);
                let urlFile = fileData.fileId == null || fileData.fileId == '' ? '#' : nts.uk.request.file.liveViewUrl(fileData.fileId);
                let isShow = true;
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
                    fileSize: fileData.fileSize
                }
                
                lstDoc.push(obj);
            }
            
            self.missingDocName = missingDocName != '' ? missingDocName.substring(0, missingDocName.length - 1) : '';

            self.layout().listDocument(lstDoc);
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

       getEmployeeAgent() {
            let self = this;
            nts.uk.ui.errors.clearAll();

            subModal('/view/jmm/018/y/index.xhtml', { title: '' }).onClosed(() => {
                let dataShare = getShared('shareToJMM018Z');
                if (dataShare != undefined) {
                    console.log(dataShare);
                    self.layout().agentName(dataShare.employeeName);
                    self.layout().agentSid(dataShare.sid);
                }

                if (self.layout().agentName() == null || self.layout().agentName() == '') {
                    self.enaSave(false);
                } else {
                    self.enaSave(true);
                }
            });
        }

        start(reportId): JQueryPromise<any> {
            let self = this,
                layout = self.layout,
                layouts = self.layouts,
                dfd = $.Deferred();
            // get all layout
            layouts.removeAll();
            service.getListReport(false).done((data: Array<any>) => {
                if (data && data.length) {
                    let _data: Array<ILayout> = _.map(data, x => {
                        return {
                            id: x.clsDto.reportClsId,
                            reportCode: x.clsDto.reportCode,
                            reportName: x.clsDto.reportName,
                            reportClsId: x.clsDto.reportClsId,
                            displayOrder: x.clsDto.displayOrder,
                            remark: x.clsDto.remark,
                            memo: x.clsDto.remark,
                            message: x.clsDto.message,
                            reportId: x.reportID,
                            sendBackComment: x.sendBackComment,
                            rootSateId: x.rootSateId,
                            reportType: x.clsDto.reportType,
                            regStatus: x.regStatus, // Save_Draft(1) , Registration(2)
                            aprStatus: x.aprStatus, // Not_Started(0)
                            workId: null

                        }
                    });
                    _.each(_data, d => layouts.push(d));
                    if (_data) {
                        if (reportId == undefined || reportId == null || reportId == "null" || reportId == "undefined" ) {
                            if (self.reportClsId() == "" || self.reportClsId() == null ) {
                                self.reportClsId(null);
                            } else {
                                let reportClsId = self.reportClsId();
                                self.reportClsId(null);
                                self.reportClsId(reportClsId);
                            }

                        } else {
                            let objReport = _.find(_data, function(o) { return o.reportId == reportId; })

                            if (objReport == undefined || objReport == null) {
                                self.reportClsId(null);
                            }else{
                                self.reportClsId(null);
                                self.reportClsId(objReport.reportClsId);
                            }

                           
                        }
                    }
                } else {
                    self.newMode();
                    unblock();
                }
                
                dfd.resolve();
            });
            return dfd.promise();
        }

        newMode() {
            let self = this,
                layout = self.layout,
                layouts = self.layouts;

            self.layout().listItemCls.removeAll();
            $('.layout-control .drag-panel').attr(`style`, `height: 0 !important;` + `padding: 0 !important;`);
            self.layout().sendBackComment(text('JHN002_A222_2_1')  + ' : ' );
            self.layout().message(text('JHN002_A222_1_1')  + ' : ' );
            self.layout().reportNameLabel('');
            self.layout().agentName('');
            self.layout().agentSid('');
            self.layout().listDocument([]);
            self.layout().approvalRootState([]);

        }
        
        setHightContent() {
            let self = this;
            const $content = $('#contents-area');

            if ($content.length) {
                const bound = $content.get(0).getBoundingClientRect();

                $content.css('height', `calc(100vh - ${bound.top + 20}px)`);
            }
        }

        save() {
            let self = this,
                layout = self.layout,
                layouts = self.layouts,
                controls = self.layout().listItemCls();

            // refresh data from layout
            self.layout().outData.refresh();
            let inputs = self.layout().outData();

            let reportLayoutId = self.reportClsId();
            if (reportLayoutId == '' || reportLayoutId == null || reportLayoutId == undefined)
                return;
            
            let objReport = _.find(self.layouts(), function(o) { return o.id == reportLayoutId; })

            if (objReport == undefined || objReport == null) {
                return;
            }

            let command = {
                inputs: inputs,
                listItemDf: self.listItemDf,
                reportID: objReport.reportId,
                reportLayoutID: reportLayoutId,
                reportCode: objReport.reportCode,
                reportName: objReport.reportName,
                reportType: objReport.reportType,
                sendBackComment: objReport.sendBackComment,
                workId: objReport.workId == null ? 0 : objReport.workId,
                rootSateId: objReport.rootSateId,
                isSaveDraft: 0,
                missingDocName: self.missingDocName,
                appSid: self.layout().agentSid()
            };

            // trigger change of all control in layout
            lv.checkError(controls);

            setTimeout(() => {
                if (hasError()) {
                    $('#func-notifier-errors').trigger('click');
                    return;
                }

                // push data layout to webservice
                block();
                service.saveData(command).done((result) => {
                    info({ messageId: "MsgJ_36" }).then(function() {
                        console.log('reportId :' + result);
                        self.start(result);
                    });
                }).fail((mes: any) => {
                    unblock();
                });
            }, 50);

        }

        attachedFile() {
            let self = this,
                layout = self.layout,
                layouts = self.layouts,
                controls = self.layout().listItemCls();

            // refresh data from layout
            self.layout().outData.refresh();
            let inputs = self.layout().outData();

            let reportLayoutId = self.reportClsId();
            if (reportLayoutId == '' || reportLayoutId == null || reportLayoutId == undefined)
                return;

            let objReport = _.find(self.layouts(), function(o) { return o.id == reportLayoutId; })

            if (objReport == undefined || objReport == null) {
                return;
            }

            let command = {
                inputs: inputs,
                listItemDf: self.listItemDf,
                reportID: objReport.reportId,
                reportLayoutID: reportLayoutId,
                reportCode: objReport.reportCode,
                reportName: objReport.reportName,
                reportType: objReport.reportType,
                sendBackComment: objReport.sendBackComment,
                workId: objReport.workId == null ? 0 : objReport.workId,
                rootSateId: objReport.rootSateId,
                isSaveDraft: 1,
                missingDocName: self.missingDocName
            };

            nts.uk.ui.errors.clearAll();

            let param = {
                reportId: number = objReport.reportId,
                layoutReportId: number = reportLayoutId,
                fromJhn002 : boolean = true,
                agentName : self.layout().agentName(),
                agentSid  : self.layout().agentSid(),
                command: command
            };
            
            let agentName = self.layout().agentName();
            let agentSid  = self.layout().agentSid();

            setShared("JHN001F_PARAMS", param);

            subModal('/view/jhn/001/f/index.xhtml', { title: '' }).onClosed(() => {
                let reportId = getShared('JHN001F_DATA');
                let objReport = _.find(self.layouts(), function(o) { return o.reportId == reportId; })
                if (objReport && self.reportClsId() == objReport.id) {
                    let param = { layoutReportId: self.reportClsId(), reportId: reportId };
                    self.getListDocument(param);
                    self.layout().agentName(agentName);
                    self.layout().agentSid(agentSid);
                } else {
                    self.start(reportId).done(() => {
                        let param = { layoutReportId: self.reportClsId(), reportId: reportId };
                        self.getListDocument(param);
                    });
                }
            });
        }

        public backTopScreenTopReport(): void {
            let self = this;
            console.log('chua order lan nay');
            //window.history.back();
        }
    }

    class Layout {
        id: KnockoutObservable<string> = ko.observable('');
        mode: KnockoutObservable<TABS> = ko.observable(TABS.LAYOUT);
        showColor: KnockoutObservable<boolean> = ko.observable(false);
        outData: KnockoutObservableArray<any> = ko.observableArray([]);
        listItemCls: KnockoutObservableArray<any> = ko.observableArray([]);
        // standardDate of layout
        standardDate: KnockoutObservable<string> = ko.observable(moment.utc().format("YYYY/MM/DD"));

        message: KnockoutObservable<string> = ko.observable('');
        sendBackComment: KnockoutObservable<string> = ko.observable('');
        reportNameLabel: KnockoutObservable<string> = ko.observable('');
        reportNameVisible: KnockoutObservable<boolean> = ko.observable(false);
        agentName: KnockoutObservable<string> = ko.observable('');
        agentSid: KnockoutObservable<string> = ko.observable('');
        agentNameVisible: KnockoutObservable<boolean> = ko.observable(false);
        
        listDocument: any = ko.observableArray([]);
        
        approvalRootState: KnockoutObservableArray<any> = ko.observableArray([]);
        listApprovalFrame: KnockoutObservableArray<any> = ko.observableArray([]);

        constructor() {
            let self = this;
            
            self.reportNameLabel.subscribe(name => {
                if (name == '' || name == null || name == undefined) {
                    self.reportNameVisible(false);
                } else {
                    self.reportNameVisible(true);
                }
            });
            
            self.agentName.subscribe(name => {
                if (name == '' || name == null || name == undefined) {
                    self.agentNameVisible(false);
                } else {
                    self.agentNameVisible(true);
                }
            });
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
                    nts.uk.request.specials.donwloadFile(rowData.fileId);
                });
            }
        }
    }
    

    interface IItemDf {
        categoryId: string;
        categoryCode: string;
        categoryName: string;
        ctgType: number;
        layoutItemType: number;
        layoutDisOrder: number;
        dispOrder: number;
        itemDefId: string;
        itemCode: string;
        itemName: string;
    }

    interface ICategory {
        id: string;
        categoryCode?: string;
        categoryName?: string;
        categoryType?: IT_CAT_TYPE;
    }
    
    export enum Page {
        NORMAL = 0,
        SIDEBAR = 1,
        FREE_LAYOUT = 2
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

    interface IPersonAuth {
        functionNo: number;
        functionName: string;
        available: boolean;
        description: string;
        orderNumber: number;
    }

    enum FunctionNo {
        No1_Allow_DelEmp = 1, // có thể delete employee ở đăng ký thông tin cá nhân
        No2_Allow_UploadAva = 2, // có thể upload ảnh chân dung employee ở đăng ký thông tin cá nhân
        No3_Allow_RefAva = 3,// có thể xem ảnh chân dung employee ở đăng ký thông tin cá nhân
        No4_Allow_UploadMap = 4, // có thể upload file bản đồ ở đăng ký thông tin cá nhân
        No5_Allow_RefMap = 5, // có thể xem file bản đồ ở đăng ký thông tin cá nhân
        No6_Allow_UploadDoc = 6,// có thể upload file điện tử employee ở đăng ký thông tin cá nhân
        No7_Allow_RefDoc = 7,// có thể xem file điện tử employee ở đăng ký thông tin cá nhân
        No8_Allow_Print = 8,  // có thể in biểu mẫu của employee ở đăng ký thông tin cá nhân
        No9_Allow_SetCoppy = 9,// có thể setting copy target item khi tạo nhân viên mới ở đăng ký mới thông tin cá nhân
        No10_Allow_SetInit = 10, // có thể setting giá trị ban đầu nhập vào khi tạo nhân viên mới ở đăng ký mới thông tin cá nhân
        No11_Allow_SwitchWpl = 11  // Lọc chọn lựa phòng ban trực thuộc/workplace trực tiếp theo bộ phận liên kết cấp dưới tại đăng ký thông tin cá nhân
    }

    interface ILicensenCheck {
        display: boolean;
        registered: number;
        canBeRegistered: number;
        maxRegistered: number;
        message: string;
        licenseKey: string;
        status: string;
    }


}