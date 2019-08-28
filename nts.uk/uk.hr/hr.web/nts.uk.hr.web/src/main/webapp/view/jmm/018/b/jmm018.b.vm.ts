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
            listEventOper: KnockoutObservableArray<EventOper> = ko.observableArray([]);
            listMenuOper: KnockoutObservableArray<MenuOper> = ko.observableArray([]);
            constructor(){
                let self = this;
                nts.uk.ui.guide.operateCurrent('guidance/guideOperate', {screenGuideParam :[{programId:'JMM018',screenId:'A'},{programId:'JMM017',screenId:'B'}]}, 
                (programId, screenId) => {
                    if (programId === "JMM018" && screenId === "A") {
                        return "tabpanel-1";
                    } 
                }, Page.SIDEBAR);
                self.columns = ko.observableArray([
                                                    { headerText: getText('JMM018_A422_4'), key: 'programId', width: "250px", dataType: "string", hidden: true },
                                                    { headerText: getText('JMM018_A422_4'), key: 'key', width: "250px", dataType: "string", hidden: true },
                                                    { headerText: getText('JMM018_A422_4'), key: 'eventId', width: "250px", dataType: "string", hidden: true },
                                                    { headerText: getText('JMM018_A422_4'), key: 'nodeText', width: "500px", dataType: "string" },
                                                    { headerText: getText('JMM018_A422_5'), key: 'useEventOrMenu', width: "400px", dataType: "boolean", formatType : "checkbox"},
                                                    { headerText: getText('JMM018_A422_6'), key: 'useNotice', width: "400px", dataType: "boolean", formatType : "checkbox"},
                                                    { headerText: getText('JMM018_A422_7'), key: 'useApproval', width: "400px", dataType: "boolean", formatType : "checkbox"}
                                                ]);
                // thêm help button trong header
                 $(document).delegate("#treegrid", "igtreegriddatarendered", function (evt, ui) {
                     let first = $(evt.target).data('_first_event');
                     
                     if(!first) {
                         let helpButton = $('<button>', {
                            id: ('nodeText'), 
                            text: getText('?'),
                            'data-bind': 'ntsHelpButton: { textId: "JMM018_A422_8", position: "top center" }'
                         }).on('focus', (evt) => {
                             $("#treegrid_nodeText").css('overflow', 'visible');
                             $("#treegrid_container > div.ui-widget-header.ui-helper-reset").css('overflow', 'visible');
                         }).on('blur', () => {
                             $("#treegrid_nodeText").css('overflow', 'hidden');
                             $("#treegrid_container > div.ui-widget-header.ui-helper-reset").css('overflow', 'hidden');
                         })
                         
                         $('#treegrid_headers').find('thead #treegrid_nodeText').append(helpButton);
                         ko.applyBindings({}, helpButton.get(0));
                         
                         let helpButton1 = $('<button>', {
                            id: ('useEventOrMenu'), 
                            text: getText('?'),
                            'data-bind': 'ntsHelpButton: { textId: "JMM018_A422_9", position: "top center" }'
                         }).on('focus', (evt) => {
                             $("#treegrid_useEventOrMenu").css('overflow', 'visible');
                             $("#treegrid_container > div.ui-widget-header.ui-helper-reset").css('overflow', 'visible');
                         }).on('blur', () => {
                             $("#treegrid_useEventOrMenu").css('overflow', 'hidden');
                             $("#treegrid_container > div.ui-widget-header.ui-helper-reset").css('overflow', 'hidden');
                             
                         });
                         
                         $('#treegrid_headers').find('thead #treegrid_useEventOrMenu').append(helpButton1);
                         ko.applyBindings({}, helpButton1.get(0));
                         
                         let helpButton2 = $('<button>', {
                            id: ('useNotice'),
                            text: getText('?'),
                            'data-bind': 'ntsHelpButton: { textId: "JMM018_A422_10", position: "top center" }'
                         }).on('focus', (evt) => {
                             $("#treegrid_useNotice").css('overflow', 'visible');
                             $("#treegrid_container > div.ui-widget-header.ui-helper-reset").css('overflow', 'visible');
                         }).on('blur', () => {
                             $("#treegrid_useNotice").css('overflow', 'hidden');
                             $("#treegrid_container > div.ui-widget-header.ui-helper-reset").css('overflow', 'hidden');
                             
                         });
                         
                         $('#treegrid_headers').find('thead #treegrid_useNotice').append(helpButton2);
                         ko.applyBindings({}, helpButton2.get(0));
                         
                         let helpButton3 = $('<button>', {
                            id: ('useApproval'),
                            text: getText('?'),
                            'data-bind': 'ntsHelpButton: { textId: "JMM018_A422_11", position: "top center" }'
                         }).on('focus', (evt) => {
                             $("#treegrid_useApproval").css('overflow', 'visible');
                             $("#treegrid_container > div.ui-widget-header.ui-helper-reset").css('overflow', 'visible');
                         }).on('blur', () => {
                             $("#treegrid_useApproval").css('overflow', 'hidden');
                             $("#treegrid_container > div.ui-widget-header.ui-helper-reset").css('overflow', 'hidden');
                         });
                         
                         $('#treegrid_headers').find('thead #treegrid_useApproval').append(helpButton3);
                         ko.applyBindings({}, helpButton3.get(0));
                             
                     }
                     $(evt.target).data('_first_event', true);
                     
                     //subcribe the change in the tree
                     $("#treegrid").bind("checkboxChanging", function(evt, query?: any) {
                         console.log(query);
                        let lisTree = self.listEventId();
                        if(query.rowData.listChild){
                            let disable = _.map(query.rowData.listChild, 'key');
                            // enable/disable child
                            if(query.value == false){
                                $("#treegrid").ntsTreeView("disableRows", disable);
                            }else{
                                $("#treegrid").ntsTreeView("enableRows", disable);
                            }
                            let obj = _.find(lisTree, function(b) {
                                return b.key == query.rowData.key;
                            });
                            self.listEventOper().push(new EventOper({eventId: obj.eventId, useEventOrMenu: obj.useEventOrMenu}));
                        }else{
                            self.listMenuOper().push(new MenuOper({
                                                                    programId: query.rowData.programId,
                                                                    useEventOrMenu: query.rowData.useEventOrMenu,
                                                                    useApproval: query.rowData.useApproval,
                                                                    useNotice: query.rowData.useNotice
                                                                }))
                        }
                     });
                     
                 });
                
                 $("#treegrid").bind("checkboxChanging", function(evt, query?: any) {
                    _.defer(() => {
                        //subcribe the change in the tree
                        let lisTree = self.listEventId();
                        _.forEach(lisTree, (value) => {
                            if(value.listChild.length > 0){
                                let disable = _.map(value.listChild, 'key');
                                // enable/disable child
                                if(value.useEventOrMenu == false || value.useEventOrMenu == 0){
                                    $("#treegrid").ntsTreeView("disableRows", disable);
                                }
                            }                            
                        });    
                    });
                 });     
                
                
            $("#buttonExpandAll").igButton({
                click: function (evt, args) {
                    for (var i = 0; i < self.listEventId().length; i++) {
                        $("#treegrid").igTreeGrid("expandRow", self.listEventId()[i].key);
                    }
                }
            });
            
            $("#buttonCollapseAll").igButton({
                click: function (evt, args) {
                    for (var i = 0; i < self.listEventId().length; i++) {
                        $("#treegrid").igTreeGrid("collapseRow", self.listEventId()[i].key);
                    }
                }
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
                let menu = [];
                service.findEventMenu().done(function(data: any){
                    if(data.available == 0){
                        nts.uk.ui.dialog.error({ messageId: "JMsg_JMM018_1"});
                    }
                    // A422_12, A422_13, A422_14, 
                    let eventNameList = _.map(data.listHrEvent, (item: any) => new MenuName({eventId: item.eventId, eventName: item.eventName}));
                    _.forEach(eventNameList, (a) => {
                            let obj = _.find(data.eventOperList, function(b) {
                                return b.eventId == a.eventId;
                            });
                            if(obj){
                                listParent.push(new DisplayItem({key: 0, eventId: a.eventId, eventName: a.eventName, useEventOrMenu: obj.useEvent}));
                                self.listEventId(listParent);
                            }
                    });
                    // A422_16 -> A422-20
                    let programName = _.map(data.hrdevMenuList, (item: any) => new HRDevMenu(item));
                    _.forEach(programName, (c) =>{
                        let agur = _.find(data.menuOperList, function(d) {
                            return d.programId == c.programId;
                        });
                        menu.push(new DisplayItem({ key: 0, 
                                                eventId: c.eventId,
                                                programId: c.programId,
                                                programName: c.programName,
                                                useEventOrMenu: agur.useMenu == 1 ? true : null,
                                                useApproval: agur.useApproval == 1 ? true : null,
                                                useNotice: agur.useNotice == 1 ? true : null} ) );
                    });
                    let list = [];
                    _.forEach(self.listEventId(), (c) => {
                        let menuOperListGroupByProgramId = _.filter(menu, function(o) {
                            return o.eventId == c.eventId;                  
                        });
                        let listAll = new DisplayItem({
                            key: 0,
                            eventId: c.eventId,
                            eventName: c.eventName,
                            useEventOrMenu: c.useEventOrMenu,
                            listChild: menuOperListGroupByProgramId              
                        }); 
                        list.push(listAll);
                    });
                    let q = 0;
                    _.forEach(list, (f) => {
                        f.key = q.toString();
                        q += 1;
                        _.forEach(f.listChild, (g) =>{
                            g.key = q.toString();
                            q += 1;
                        });
                    });
                    self.listEventId(list);
                    console.log(self.listEventId());
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
            public saveEventMenu(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                let self = this;
                nts.uk.ui.block.grayout();
                let copyList = self.listEventId();
                _.forEach(copyList, (value) => {
                    if(value.useEventOrMenu == false){
                        _.forEach(value.listChild, (a) => {
                            self.listMenuOper(_.remove(self.listMenuOper(), function(n) {
                                return n.programId == a.programId;
                            }));
                        });
                            
                    }
                });
                console.log(self.listMenuOper());
                let params = {
                    listEventOper: self.listEventOper(),
                    listMenuOper: self.listMenuOper()
                }
                service.saveEventMenu(params).done(function(){
                }).always(() => {
                    nts.uk.ui.block.clear();
                });
                
                return dfd.promise();
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
        key: string;
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
    
    interface IEventOper{
        // イベントID
        eventId: number;
        // イベントを使用する
        useEventOrMenu: boolean;
    }
    
    class EventOper{
        // イベントID
        eventId: number;
        // イベントを使用する
        useEvent: number;
        constructor(param: IEventOper){
            this.eventId = param.eventId;
            this.useEvent = param.useEventOrMenu == true ? 1 : 0;
        }
    }
    
    interface IMenuOper{
        // プログラムID
        programId: string;
        // メニューを使用する
        useEventOrMenu: boolean;
        //承認機能を使用する
        useApproval: boolean;
        // 通知機能を使用する
        useNotice: boolean;
    }
    
    class MenuOper{
       // プログラムID
        programId: string;
        // メニューを使用する
        useMenu: number;
        //承認機能を使用する
        useApproval: number;
        // 通知機能を使用する
        useNotice: number;
        constructor(param: IMenuOper){
            this.programId = param.programId;
            this.useMenu = param.useEventOrMenu == true ? 1 : 0;
            this.useApproval = param.useApproval == true ? 1 : 0;
            this.useNotice = param.useNotice == true ? 1 : 0;
        }
    }
    
    interface IMenuName{
        // イベントID
        eventId: number;
        // イベント名
        eventName: string;
    }
    
    class MenuName{
        // イベントID
        eventId: number;
        // イベント名
        eventName: string;
        constructor(param: IMenuName){
            this.eventId = param.eventId;
            this.eventName = param.eventName;    
        }
    }
}