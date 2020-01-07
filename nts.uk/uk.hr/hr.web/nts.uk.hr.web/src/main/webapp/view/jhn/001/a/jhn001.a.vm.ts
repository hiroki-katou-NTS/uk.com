module jhn001.a.viewmodel {
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

    const __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];


    export class ViewModel {
        layouts: KnockoutObservableArray<ILayout> = ko.observableArray([]);
        layout: KnockoutObservable<Layout> = ko.observable(new Layout()); 
        reportClsId : KnockoutObservable<string> = ko.observable('');
        
        enaGoBack : KnockoutObservable<boolean> = ko.observable(false);
        enaSave : KnockoutObservable<boolean> = ko.observable(true);
        enaSaveDraft : KnockoutObservable<boolean> = ko.observable(true);
        enaAttachedFile : KnockoutObservable<boolean> = ko.observable(true);
        enaRemove : KnockoutObservable<boolean> = ko.observable(true);
        
        reportColums: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: '', key: 'id', width: 0, hidden: true },
            { headerText: text('JHN001_A221_4_1'), key: 'reportCode', width: 80, hidden: false },
            { headerText: text('JHN001_A221_4_2'), key: 'reportName', width: 260, hidden: false, formatter: _.escape }
        ]);
        constructor() {
            let self = this,
                layout = self.layout,
                layouts = self.layouts;
            
            self.start();

            self.reportClsId.subscribe(id => {
                if (id) {
                    let query = {
                        layoutId: '4cc80933-443b-407d-9204-1d69f9a1a225',
                        browsingEmpId: 'ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570',
                        standardDate: moment.utc().toDate()
                    };
                    
                    block();
                    service.getCurrentLayout(query).done((data: any) => {
                        if (data) {
                            self.layout().showColor(true);
                            self.layout().standardDate(data.standardDate || undefined);

                            lv.removeDoubleLine(data.classificationItems);
                            self.layout().listItemCls(data.classificationItems || []);
                            self.layout().sendBackComment(text('JHN001_A222_2_1') + ' : ' + 'sendBackComment');
                            
                            _.defer(() => {
                                new vc(self.layout().listItemCls());
                                
                                // get list file document
                               self.getListDocument().done(() => {
                                   unblock();
                               });
                                
                               self.getListReportSaveDraft().done(() => {});
                                
                            });
                        } else {
                            self.layout().listItemCls.removeAll();
                            unblock();
                        }
                    }).fail(mgs => {
                        self.layout().showColor(true);
                        self.layout().listItemCls.removeAll();
                        unblock();
                    });
                }
            });
        }
        
        getListDocument(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            let param  = {layoutReportId : 1, reportId: 0 }    
            var dfdGetData = service.getListDoc(param);

            block();
            $.when(dfdGetData).done((datafile: any) => {
                var lstDoc = [];
                if (datafile) {
                    for (var i = 0; i < datafile.length; i++) {
                        let obj = {
                            docName: datafile[i].docName,
                            ngoactruoc: '(',
                            sampleFileName:  datafile[i].sampleFileName == null ? '' : '<a href="/shr/infra/file/storage/infor/'+ datafile[i].fileName +'" target="_blank">' + datafile[i].sampleFileName + '</a>', 
                            ngoacsau: ')',
                            fileName: datafile[i].fileName == null ? '' : '<a href="/shr/infra/file/storage/infor/'+ datafile[i].fileName +'" target="_blank">' + datafile[i].fileName + '</a>' ,
                            cid: datafile[i].cid,
                            reportLayoutID: datafile[i].reportLayoutID,
                            docID: datafile[i].docID,
                            dispOrder: datafile[i].dispOrder,
                            requiredDoc: datafile[i].requiredDoc,
                            docRemarks: datafile[i].docRemarks,
                            sampleFileId: datafile[i].sampleFileId,
                            reportID: datafile[i].reportID,
                            fileId: datafile[i].fileId,
                            fileSize: datafile[i].fileSize
                        }
                        lstDoc.push(obj);
                    }
                    self.layout().listDocument(lstDoc);
                }

                unblock();
                dfd.resolve();
            });
            return dfd.promise();
        }
        
        getListReportSaveDraft(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            var dfdGetData = service.getListReportSaveDraft();

            block();
            $.when(dfdGetData).done((listReportDarft: any) => {
                if (listReportDarft) {
                    subModal('/view/jhn/001/b/index.xhtml', { title: '' }).onClosed(() => {
                        dataShare = getShared('CPS001B_PARAMS');
                        debugger;
                        
                    });
                }
                unblock();
                dfd.resolve();
            });
            return dfd.promise();
        }
        
        
        newMode(){
            let self = this;
        }

        start(code?: string): JQueryPromise<any> {
            let self = this,
                layout = self.layout,
                layouts = self.layouts,
                dfd = $.Deferred();
            // get all layout
            layouts.removeAll();
            service.getAll(true).done((data: Array<any>) => {
                if (data && data.length) {
                    let _data: Array<ILayout> = _.map(data, x => {
                        return {
                            id: x.reportClsId,
                            reportCode: x.reportCode,
                            reportName: x.reportName,
                            reportClsId: x.reportClsId,
                            displayOrder: x.displayOrder,
                            remark: x.remark,
                            memo: x.remark,
                            message: x.message
                        }
                    });
                    _.each(_.orderBy(_data, ['displayOrder'], ['asc']) , d => layouts.push(d));
                    if(_data){
                        self.reportClsId(_data[0].reportClsId);
                        self.layout().message(text('JHN001_A222_1_1') + ' : ' + '_data[0].message');
                    }
                    
                } else {
                    self.createNewLayout();
                }
                dfd.resolve();
            });
            return dfd.promise();
        }
        
        createNewLayout() {
            let self = this,
                layout = self.layout,
                layouts = self.layouts;
        }
        
        save() {
            let self = this,
                layout = self.layout,
                layouts = self.layouts;
        }
        
        saveDraft() {
            let self = this,
                controls = self.layout.listItemCls();

            // refresh data from layout
            self.layout.outData.refresh();
            let inputs = self.layout.outData();
            
            let command = { inputs: inputs };

             // trigger change of all control in layout
            lv.checkError(controls);
            
            setTimeout(() => {
                if (hasError()) {
                    $('#func-notifier-errors').trigger('click');
                    return;
                }

                // push data layout to webservice
                self.block();
                service.saveDraftData(command).done(() => {
                    info({ messageId: "Msg_15" }).then(function() {
                        //self.reload();
                    });
                }).fail((mes: any) => {
                    self.unblock();
                });
            }, 50);

        }
        
        attachedFile() {
            let self = this,
                layout = self.layout,
                layouts = self.layouts;
            
            subModal('/view/jhn/001/f/index.xhtml', { title: '' }).onClosed(() => {
                console.log('test open dialog f');
                // get lại list file document
                self.getListDocument().done(() => {
                    unblock();
                });
            });
            
            
        }
        
        remove() {
            let self = this,
                layout = self.layout,
                layouts = self.layouts;
            
            
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
        
        approvalRootState : any = ko.observableArray([]);
        listDocument : any = ko.observableArray([]);
        
        constructor() {
            let self = this;

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