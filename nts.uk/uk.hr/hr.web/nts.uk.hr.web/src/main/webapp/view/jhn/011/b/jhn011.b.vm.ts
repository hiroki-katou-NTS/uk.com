module jhn011.b.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import showDialog = nts.uk.ui.dialog;
    import text = nts.uk.resource.getText;
    import lv = nts.layout.validate;

    let __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];


    export class ViewModel {
        layouts: KnockoutObservableArray<ILayout> = ko.observableArray([]);
        layout: KnockoutObservable<Layout> = ko.observable(new Layout({ id: '', code: null, name: null }));
        enaBtnSave : KnockoutObservable<boolean> = ko.observable(true);
        enaBtnCoppy : KnockoutObservable<boolean> = ko.observable(true);
        enaBtnDel : KnockoutObservable<boolean> = ko.observable(true);
        checkAbolition: KnockoutObservable<boolean> = ko.observable(false);
        row: KnockoutObservable<number>;
        
        reportColums: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: '', key: 'id', width: 0, hidden: true },
            { headerText: text('JHN011_B221_4_1'), key: 'reportCode', width: 60, hidden: false },
            { headerText: text('JHN011_B221_4_2'), key: 'reportName', width: 230, hidden: false, formatter: _.escape },
            { headerText: text('JHN011_B221_4_3'), key: 'isAbolition', width: 50, hidden: false, formatter: makeIcon }
        ]);
        constructor() {
            let self = this,
                layout: Layout = self.layout(),
                layouts = self.layouts;
            
            nts.uk.ui.guide.operateCurrent('guidance/guideOperate', { screenGuideParam: [{ programId: 'JHN011', screenId: 'B' }] },
                Page.NORMAL);
            
            self.start();

            layout.id.subscribe(id => {
                 
                if (id) {
                    // Gọi service tải dữ liệu ra layout
                    block();
                    self.enaBtnSave(true);
                    self.enaBtnCoppy(true);
                    self.enaBtnDel(true);
                    service.getDetails(id).done((data: any) => {
                        if (data) {
                             self.setDetailLayout(data, layout);
                            // remove all sibling sperators
                            lv.removeDoubleLine(data.itemsClassification);

                            layout.classifications(data.listItemClsDto || []);
                            layout.action(LAYOUT_ACTION.UPDATE);
                            unblock();
                        }
                    });
                }else{
                    self.enaBtnSave(false);
                    self.enaBtnDel(false);
                }
                
                setTimeout(() =>{
                    $('#B221_4_container').attr(`style`, `height: 0px !important;`);    
                }, 100);
                
            });
            
            self.checkAbolition.subscribe(c =>{
                self.start(layout.id());
            });
        }
        

        start(code?: string): JQueryPromise<any> {
            let self = this,
                layout: Layout = self.layout(),
                layouts = self.layouts,
                dfd = $.Deferred();
            // get all layout
            layouts.removeAll();
            service.getAll(self.checkAbolition()).done((data: Array<any>) => {
                
                if (data && data.length) {
                    let _data: Array<ILayout> = _.map(data, x => {
                        return {
                            id: x.reportClsId,
                            reportCode: x.reportCode,
                            reportName: x.reportName,
                            isAbolition: x.isAbolition
                        }
                    });
                    _.each(_data, d => layouts.push(d));
                    //届出一覧の先頭を選択する (chọn item đầu tiên trong list đơn xin (report))
                    if (!code) {
                        layout.id(_data[0].id);
                    }
                    else {
                        let _item: ILayout = _.find(ko.toJS(layouts), (x: ILayout) => x.id == code);
                        if (_item) {
                            layout.id(_item.id);
                        } else {
                            // xử lý thêm phần selected khi không tìm thấy reportId
                            layout.id(_data[0].id);
                        }
                    }
                    layout.id.valueHasMutated();

                } else {
                    self.createNewLayout();
                }
                dfd.resolve();
            }).fail(res => {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(function() {
                        nts.uk.request.jump("com","/view/ccg/008/a/index.xhtml");
                    });
            });
            return dfd.promise();
        }

        createNewLayout() {
            let self = this,
                layout: Layout = self.layout(),
                layouts = self.layouts;
            self.checkAbolition(false);
            layout.id(undefined);
            self.setDetailLayout({}, layout);
            self.enaBtnSave(true);
            self.enaBtnDel(false);
            layout.classifications([]);
            layout.action(LAYOUT_ACTION.INSERT);
            
            $("#B222_1_1").focus();
        }

        saveDataLayout() {
            let self = this,
                data: ILayout = ko.toJS(self.layout),
                command: any = {
                    id: data.id,
                    reportCode: data.reportCode,
                    reportName: data.reportName,
                    reportNameYomi : data.reportNameYomi,
                    abolition : data.isAbolition,
                    reportType: data.reportType,
                    remark: data.remark,
                    memo: data.memo,
                    message: data.message,
                    formReport: data.formReport == 0? false: true,
                    action: data.action,
                    classifications: data.outData
                };

            // validate
            $("#B222_1_1").trigger("validate");
            $("#B222_1_2").trigger("validate");

            if (nts.uk.ui.errors.hasError()) {
                return;
            }

            // call service savedata
            block();
            service.saveData(command).done((_data: any) => {
                unblock();
                showDialog.info({ messageId: "MsgJ_36" }).then(function() {
                });

                self.start(command.id);

            }).fail((error: any) => {
                
                nts.uk.ui.dialog.bundledErrors(error);
                unblock();

            });
        }

        setDetailLayout(param: ILayout, layout: any){
            layout.reportCode(param.reportCode || null);
            layout.reportName(param.reportName || null);
            layout.reportNameYomi(param.reportNameYomi || null);
            layout.isAbolition(param.isAbolition || null);
            layout.reportType(param.reportType ==0? "0":( param.reportType|| null));
            layout.remark(param.remark || null);
            layout.memo(param.memo || null);
            layout.message(param.message || null);
            layout.formReport(param.formReport == undefined?1: param.formReport);
            layout.agentReportIsCan(param.agentReportIsCan || 0);
        }
            
        removeDataLayout() {
            let self = this,
                data: ILayout = ko.toJS(self.layout);

            data.classifications = _.map(data.classifications, m => _.omit(m, ["items", "renders"]));

            data.action = LAYOUT_ACTION.REMOVE;
            let indexItemDelete = _.findIndex(ko.toJS(self.layouts), function(item: any) { return item.id == data.id; }),
                layouts: Array<ILayout> = ko.toJS(self.layouts);

            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                var command: any = {
                    id: data.id
                };

                // call service remove
                invisible();
                let itemListLength = self.layouts().length;
                service.removeData(data.id).done((data: any) => {
                    showDialog.info({ messageId: "Msg_16" }).then(function() {
                        if(!self.checkAbolition()){
                            self.processSelected(itemListLength, indexItemDelete, layouts);                          
                        }else{
                            self.start(command.id).done(() => {
                                unblock();
                            });                            
                        
                        }

                    });
                    unblock();
                }).fail((error: any) => {
                    unblock();
                });

            }).ifCancel(() => {
            });
        }
        
        
        processSelected(itemListLength: number, indexItemDelete: number, layouts: any){
            let self = this;
            
            if (itemListLength === 1) {
                self.start().done(() => {
                    unblock();
                });
            } else if (itemListLength - 1 === indexItemDelete) {
                self.start(layouts[indexItemDelete - 1].id).done(() => {
                    unblock();
                });
            } else if (itemListLength - 1 > indexItemDelete) {
                self.start(layouts[indexItemDelete + 1].id).done(() => {
                    unblock();
                });
            } 
        
        }

        showDialogC() {
            let self = this,
                layout: Layout = self.layout(),
                data: ILayout = ko.toJS(self.layout);

            data.classifications = _.map(data.classifications, m => _.omit(m, ["items", "renders"]));

            setShared('JHN011C_PARAM', data);
            modal('../c/index.xhtml').onClosed(() => {
                let dto: Array<any> = getShared('JHN011C_VALUE');

                if (dto && dto.length) {
                    layout.classifications(_.map(dto, x => _.omit(x, ["items", "renders"])));
                    layout.action(LAYOUT_ACTION.UPDATE);
                }
            });
        }
        
        
    }

    interface IItemClassification {
        layoutID?: number;
        layoutCode?: string;
        layoutName?: string;
        dispOrder?: number;
        className?: string;
        personInfoCategoryID?: string;
        categoryCode?:string;
        categoryName?:string;
        fixAtr?: boolean;
        layoutItemType: IT_CLA_TYPE;
        listItemDf: Array<IItemDefinition>;
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
        reportCode: string;
        reportName: string;
        reportNameYomi?: string;
        isAbolition: boolean;
        reportType?: int;
        remark?: string;
        memo?: string;
        message?: string;
        formReport?: boolean;
        agentReportIsCan?: boolean;
        classifications?: Array<IItemClassification>;
        action?: number;
        outData?: Array<any>;
    }

    class Layout {
        id: KnockoutObservable<string> = ko.observable(null);
        
        reportCode: KnockoutObservable<string> = ko.observable(null);
        
        reportName: KnockoutObservable<string> = ko.observable(null);
        
        reportNameYomi: KnockoutObservable<string> = ko.observable(null);
        
        isAbolition: KnockoutObservable<boolean> = ko.observable(null);
        
        reportType: KnockoutObservable<any> = ko.observable(null);
        
        remark: KnockoutObservable<string> = ko.observable(null);
        
        memo: KnockoutObservable<string> = ko.observable(null);

        message: KnockoutObservable<string> = ko.observable(null);

        formReport: KnockoutObservable<string> = ko.observable(null);
        
        agentReportIsCan: KnockoutObservable<string> = ko.observable(null);
    
        comboxReportType: KnockoutObservableArray < any > = ko.observableArray([
            { code: "", name: ""},
            { code: "0", name: text("JHN011_B222_2_1_1") },
            { code: "1", name: text("JHN011_B222_2_1_2") },
            { code: "2", name: text("JHN011_B222_2_1_3") }]);
    
        roundingRules: KnockoutObservableArray < any > = ko.observableArray([
            { code: "1", name: text("JHN011_B222_6_1_1")},
            { code: "0", name: text("JHN011_B222_6_1_2") } ]);
        // 1 する、０しない
        classifications: KnockoutObservableArray<any> = ko.observableArray([]);
        
        action: KnockoutObservable<LAYOUT_ACTION> = ko.observable(LAYOUT_ACTION.INSERT);
        
        outData: KnockoutObservableArray<any> = ko.observableArray([]);

        constructor(param: ILayout) {
            let self = this;
            if (param) {
                self.id(param.id || null);
                self.reportCode(param.reportCode || null);
                self.reportName(param.reportName || null);
                self.reportNameYomi(param.reportNameYomi || null);
                self.isAbolition(param.isAbolition || null);
                self.reportType(param.reportType ==0? 0: (param.reportType || null));
                self.remark(param.remark || null);
                self.memo(param.memo || null);
                self.message(param.message || null);
                self.formReport(param.formReport);
                self.agentReportIsCan(param.agentReportIsCan || 0);
                self.classifications(param.classifications || []);
            }
        }
    }

    enum LAYOUT_ACTION {
        INSERT = 0,
        UPDATE = 1,
        COPY = 2,
        OVERRIDE = 3,
        REMOVE = 4
    }

    // define ITEM_CLASSIFICATION_TYPE
    enum IT_CLA_TYPE {
        ITEM = <any>"ITEM", // single item
        LIST = <any>"LIST", // list item
        SPER = <any>"SeparatorLine" // line item
    }
    
    enum Page {
        NORMAL = 0,
        SIDEBAR = 1,
        FREE_LAYOUT = 2
    }
    
    function makeIcon(value, row) {
        if (value == '1')
            return '<img src="images/checked.png" style="margin-left: 15px; width: 15px; height: 15px;" />';
        return '<span></span>';
    }
}