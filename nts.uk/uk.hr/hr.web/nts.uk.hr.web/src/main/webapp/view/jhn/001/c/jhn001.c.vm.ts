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
        }
        
        /*　承認ボタン*/
        approve():void {
            let self = this;    
        
        
        
        }
        
        /* 否認ボタン*/
        deny(): void{
            let self = this;    
        }
        
        /* 差し戻し*/
        sendBack(): void{
            let self = this;
        }
        
        /*　解除*/
        cancel(): void{
            let self = this;
        
        }
        
        /* 登録*/
        register(): void{
            let self = this;    
        }
        
        start(code?: string): JQueryPromise<any> {
            let self = this,
                layout: Layout = self.layout(),
                layouts = self.layouts,
                dfd = $.Deferred();
            //get param url
            let url = $(location).attr('search');
            let reportId: number = url.split("=")[1];
            // get all layout
            layouts.removeAll();
            service.getDetails(reportId).done((data: any) => {
                if (data) {
                    lv.removeDoubleLine(data.classificationItems);
                    layout.classifications(data.classificationItems || []);
                    _.defer(() => {
                        new vc(layout.classifications());
                    });
                } else {
                    layout.classifications.removeAll();
                    unblock();
                }
            });
            return dfd.promise();
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
        id: string;
        message?: string;
        sendBackComment?: string;
        approveComment?: string;
        classifications?: Array<IItemClassification>;
        action?: number;
        outData?: Array<any>;
    }

    class Layout {
        id: KnockoutObservable<string> = ko.observable('');
        message: KnockoutObservable<string> = ko.observable('');
        sendBackComment: KnockoutObservable<string> = ko.observable('');
        approveComment: KnockoutObservable<string> = ko.observable('');
        mode: KnockoutObservable<TABS> = ko.observable(TABS.LAYOUT);
        showColor: KnockoutObservable<boolean> = ko.observable(false);
        outData: KnockoutObservableArray<any> = ko.observableArray([]);
        classifications: KnockoutObservableArray<any> = ko.observableArray([]);
        approvalRootState : any = ko.observableArray([]);
        listDocument : any = ko.observableArray([]);
        
        constructor() {
            let self = this;

            self.listDocument([{ nameLabel: 'Bert',ngoactruoc:'(',fileSample : '<a href="https://www.w3schools.com">fileSample</a>', ngoacsau: ')', nameDoc: '<a href="https://www.w3schools.com">fileSample.pdf</a>' },
                               { nameLabel: 'Bert',ngoactruoc:'(',fileSample : '<a href="https://www.w3schools.com">fileSample</a>', ngoacsau: ')', nameDoc: '<a href="https://www.w3schools.com">fileSample.pdf</a>' },
                               { nameLabel: 'Bert',ngoactruoc:'(',fileSample : '<a href="https://www.w3schools.com">fileSample</a>', ngoacsau: ')', nameDoc: '<a href="https://www.w3schools.com">fileSample.pdf</a>' },
                               { nameLabel: 'Bert',ngoactruoc:'(',fileSample : '<a href="https://www.w3schools.com">fileSample</a>', ngoacsau: ')', nameDoc: '<a href="https://www.w3schools.com">fileSample.pdf</a>' }]);
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
}