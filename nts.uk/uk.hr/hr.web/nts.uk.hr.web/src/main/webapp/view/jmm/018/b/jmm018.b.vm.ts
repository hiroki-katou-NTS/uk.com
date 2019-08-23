module nts.uk.com.view.jmm018.b {
    import alertError = nts.uk.ui.dialog.alertError;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import blockUI = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    let __viewContext: any = window["__viewContext"] || {};
    
    export module viewmodel {
        export class ScreenModel {
            menuEventList: KnockoutObservableArray<any> = ko.observableArray([]);
            listEventId: KnockoutObservableArray<DisplayItem> = ko.observableArray([]);
            columns: any;
            itemSelected: KnockoutObservable<any> = ko.observable(null);
            constructor(){
                let self = this;
                nts.uk.ui.guide.operateCurrent('guidance/guideOperate', {screenGuideParam :[{programId:'JMM018',screenId:'A'},{programId:'JMM017',screenId:'B'}]}, 
                (programId, screenId) => {
                    if (programId === "JMM018" && screenId === "A") {
                        return "tabpanel-1";
                    } 
                }, Page.SIDEBAR);
                self.columns = ko.observableArray([
                                                    { headerText: getText('JMM018_A422_4'), key: 'key', width: "250px", dataType: "string", hidden: true },
                                                    { headerText: getText('JMM018_A422_4'), key: 'eventId', width: "250px", dataType: "string", hidden: true },
                                                    { headerText: getText('JMM018_A422_4'), key: 'nodeText', width: "250px", dataType: "string" },
                                                    { headerText: getText('JMM018_A422_5'), key: 'useEventOrMenu', width: "200px", dataType: "boolean", formatType : "checkbox"},
                                                    { headerText: getText('JMM018_A422_6'), key: 'useNotice', width: "200px", dataType: "boolean", formatType : "checkbox"},
                                                    { headerText: getText('JMM018_A422_7'), key: 'useApproval', width: "100px", dataType: "boolean", formatType : "checkbox"}
                                                ]);
                // thêm help button trong header
                 $(document).delegate("#treegrid", "igtreegriddatarendered", function (evt, ui) {
                     let helpButton = $('<button>', {
                        id: ('nodeText'), 
                        text: getText('?'),
                        'data-bind': 'ntsHelpButton: { textId: "JMM018_A422_8", position: "top center" }'
                     }).on('focus', (evt) => {
                         $("#treegrid_nodeText").css('overflow', 'visible');
                     }).on('blur', () => {
                         $("#treegrid_nodeText").css('overflow', 'hidden');
                     })
                     
                     $('#treegrid_headers').find('thead #treegrid_nodeText').append(helpButton);
                     ko.applyBindings({}, helpButton.get(0));
                     
                     let helpButton1 = $('<button>', {
                        id: ('useEventOrMenu'), 
                        text: getText('?'),
                        'data-bind': 'ntsHelpButton: { textId: "JMM018_A422_9", position: "top center" }'
                     }).on('focus', (evt) => {
                         $("#treegrid_useEventOrMenu").css('overflow', 'visible');
                     }).on('blur', () => {
                         $("#treegrid_useEventOrMenu").css('overflow', 'hidden');
                         
                     });
                     
                     $('#treegrid_headers').find('thead #treegrid_useEventOrMenu').append(helpButton1);
                     ko.applyBindings({}, helpButton1.get(0));
                     
                     let helpButton2 = $('<button>', {
                        id: ('useNotice'),
                        text: getText('?'),
                        'data-bind': 'ntsHelpButton: { textId: "JMM018_A422_10", position: "top center" }'
                     }).on('focus', (evt) => {
                         $("#treegrid_useNotice").css('overflow', 'visible');
                     }).on('blur', () => {
                         $("#treegrid_useNotice").css('overflow', 'hidden');
                         
                     });
                     
                     $('#treegrid_headers').find('thead #treegrid_useNotice').append(helpButton2);
                     ko.applyBindings({}, helpButton2.get(0));
                     
                     let helpButton3 = $('<button>', {
                        id: ('useApproval'),
                        text: getText('?'),
                        'data-bind': 'ntsHelpButton: { textId: "JMM018_A422_11", position: "top center" }'
                     }).on('focus', (evt) => {
                         $("#treegrid_useApproval").css('overflow', 'visible');
                     }).on('blur', () => {
                         $("#treegrid_useApproval").css('overflow', 'hidden');
                         
                     });
                     
                     $('#treegrid_headers').find('thead #treegrid_useApproval').append(helpButton3);
                     ko.applyBindings({}, helpButton3.get(0));
                     console.log('delegate pass');
                 });
            }
            
            /**
             * init default data when start page
             */
            public start_page(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                let self = this;
                blockUI.invisible();
                let listParent = [];
                let listEnum = __viewContext.enums.EventId;
                let menu = [];
                service.findEventMenu().done(function(data: any){
                    // A422_12, A422_13, A422_14, 
                    _.forEach(__viewContext.enums.EventId, (a) => {
                            let obj = _.find(data.eventOperList, function(b) {
                                return b.eventId == a.value;
                            });
                            if(obj){
                                listParent.push(new DisplayItem({eventId: a.value, eventName: a.name, useEventOrMenu: obj.useEvent}));
                                self.listEventId(listParent);
                            }
                    });
                    // A422_16 -> A422-20
                    let programName = _.map(data.hrdevMenuList, (item: any) => new HRDevMenu(item));
                    _.forEach(programName, (c) =>{
                        let agur = _.find(data.menuOperList, function(d) {
                            return d.programId == c.programId;
                        });
                        menu.push(new DisplayItem({eventId: c.eventId,
                                                programId: c.programId,
                                                programName: c.programName,
                                                useEventOrMenu: agur.useMenu,
                                                useApproval: agur.useApproval,
                                                useNotice: agur.useNotice} ) );
                    });
                    let list = [];
                    _.forEach(self.listEventId(), (c) => {
                        let menuOperListGroupByProgramId = _.filter(menu, function(o) {
                            return o.eventId == c.eventId;                  
                        });
                        let listAll = new DisplayItem({
                            eventId: c.eventId,
                            eventName: c.eventName,
                            useEventOrMenu: c.useEventOrMenu,
                            listChild: menuOperListGroupByProgramId              
                        }); 
                        list.push(listAll);
                    });
                    let q = 0;
                    _.forEach(list, (f) => {
                        f.key = q;
                        q += 1;
                        _.forEach(f.listChild, (g) =>{
                            g.key = q;
                            q += 1;
                        });
                    });
                    self.listEventId(list);
                    self.bindData();
                    dfd.resolve();
                }).fail(function(error) {
                    alertError(error);
                }).always(function() {
                    nts.uk.ui.block.clear();
                    dfd.resolve();
                });
                
                return dfd.promise();
            }
            
            /**
             * Save System Resource setting
             */
            public saveSysResourceSetting(): JQueryPromise<void> {
                let _self = this;
                
                 // Validate
//                if (_self.hasError()) {
//                    return;    
//                }
//                
//                blockUI.invisible();
//                
//                var dfd = $.Deferred<void>();
//                  
//                var params = new SystemResourceCommand(_self.prepareDataToSave());
//                nts.uk.ui.block.grayout();
//                service.saveSysResourceSetting(params).done(function(){
//                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => { 
//                        dfd.resolve();
//                        blockUI.clear();
//                        $('#com_company').focus();
//                    });
//                }).always(() => {
//                    nts.uk.ui.block.clear();
//                });
                
                return dfd.promise();
            }
            
            
            public bindData(): void {
                let self = this;
                /*----------------- Instantiation -------------------------*/
//                $("#hierarchicalGrid").hierarchicalGrid({
//                    
//                });
//            
            }
            
        }
    }

    interface IHRDevMenu{
        // hr dev menu
        eventId: number;
        programId: string;
        programName: string;
    }
    
    class HRDevMenu{
        // hr dev menu
        eventId: number;
        programId: string;
        programName: string;
        
        constructor(param: IHRDevMenu) {
            this.eventId = param.eventId;
            this.programId= param.programId;
            this.programName= param.programName;
        }
    }
    
    interface IChildLevel{
        eventId: number;
        programId: string;
        programName: string;
        useMenu: number;
        useApproval: number;
        useNotice: number;
    }
    
    class ChildLevel{
        eventId: number;
        programId: string;
        programName: string;
        useMenu: number;
        useApproval: number;
        useNotice: number;
        constructor(param: IChildLevel){
            this.eventId = param.eventId,
            this.programId = param.programId,
            this.programName = param.programName,
            this.useMenu = param.useMenu,
            this.useApproval = param.useApproval,
            this.useNotice = param.useNotice
        }
    }
    
    interface IParentLevel{
        // hr dev event
        eventId: number;
        eventName: string;
        // event operation
        useEvent: boolean;
        listChild: Array<ChildLevel>;
    }
    
    class ParentLevel {
        eventId: number;
        eventName: string;
        useEvent: boolean;
        listChild: Array<ChildLevel>;
        constructor(param: IParentLevel) {
            this.eventId = param.eventId;
            this.eventName = param.eventName;
            this.useEvent = param.useEvent;
            this.listChild = param.listChild;
        }
    }
    
    enum Page {
         NORMAL = 0,
         SIDEBAR = 1,
         FREE_LAYOUT = 2
    }
    
    interface IDisplayItem{
        key: number;
        eventId: number;
        eventName: string;
        useEventOrMenu: boolean;
        programId: string;
        programName: string;
        useApproval: boolean;
        useNotice: boolean;
        nodeText: string;
        listChild: Array<DisplayItem>;
    }
    
    class DisplayItem {
        key: number;
        eventId: number;
        eventName: string;
        useEventOrMenu: boolean;
        programId: string;
        programName: string;
        useApproval: boolean;
        useNotice: boolean;
        nodeText: string;
        listChild: Array<DisplayItem>;
        constructor(param: IDisplayItem) {
            this.key = param.key;
            this.eventId = param.eventId;
            this.eventName = param.eventName;
            this.useEventOrMenu = (param.useEventOrMenu == 1 ? true : null) || (param.useEventOrMenu == undefined ? null : param.useEventOrMenu);
            this.programId = param.programId;
            this.programName = param.programName;
            this.useApproval = (param.useApproval == 1 ? true : null) || (param.useApproval == undefined ? null : param.useApproval);
            this.useNotice = (param.useNotice == 1 ? true : null) || (param.useNotice == undefined ? null : param.useNotice);
            this.nodeText = param.eventName ? param.eventId + ' ' + param.eventName : param.programId + ' ' + param.programName;
            this.listChild = param.listChild;
        }
    }
}