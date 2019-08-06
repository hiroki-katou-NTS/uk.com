module nts.uk.hr.view.jmm017.b.viewmodel {
    import modal = nts.uk.ui.windows.sub.modal;
    import block = nts.uk.ui.block;
    import NtsGridListColumn = nts.uk.ui.NtsGridListColumn;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import error = nts.uk.ui.dialog.error;
    import getText = nts.uk.resource.getText;
    export class ScreenModel {
        
        categoryList: KnockoutObservableArray<any>;
        useList: KnockoutObservableArray<any>;
        
        guideMessageList: KnockoutObservableArray<any>;
        
        categoryCode: KnockoutObservable<string>;
        eventName: KnockoutObservable<string>;
        programName: KnockoutObservable<string>;
        screenName: KnockoutObservable<string>;
        useSet: KnockoutObservable<string>;
        editTem: KnockoutObservable<string>;
        constructor() {
            let self = this;
            self.categoryList = ko.observableArray([]);
            self.guideMessageList = ko.observableArray([]);
            self.categoryCode = ko.observable("");
            self.eventName = ko.observable("");
            self.programName = ko.observable("");
            self.screenName = ko.observable("");
            self.useSet = ko.observable('1');
            self.useList = ko.observableArray([]);
            self.editTem = ko.observable("<button data-bind='click: editMsg({ cateName: ${categoryName} })'>"+getText('JMM017_B422_23')+"</button>");
        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.useList([{ code: "1", name: "" }, { code: "2", name: "使用する"},{ code: "3", name: "使用しない"}]);
            new service.getGuideCategory().done(function(data: any) {
                self.categoryList(data);
                self.binData();
            }).fail(function(error) {
                error({ messageId: error.messageId });
            }).always(function() {
                dfd.resolve();
            });
            dfd.resolve();
            return dfd.promise();
        }

        public find(): void {
            block.grayout();
            var self = this;
            let param = {
                categoryCode: self.categoryCode(),
                eventName: self.eventName(),
                programName: self.programName(),
                screenName: self.screenName(),
                useSet: self.useSet()==1?null:self.useSet()==2
            }
            new service.getGuideMessageList(param).done(function(data: any) {
                console.log(data);
                _.forEach(data, d => {
                    d.id = nts.uk.util.randomId();
                });
                
                let groupByColumns = $("#grid").igGridGroupBy("groupByColumns");
                $("#grid").igGridGroupBy("ungroupAll");
                self.guideMessageList(data);
//                self.binData();
                setTimeout(() => {
                    _.forEach(groupByColumns, col => {
                        $("#grid").igGridGroupBy("groupByColumn", col.key);
                    });
                    
                    $("#grid").igGridPaging("pageIndex", 0);
                }, 1);
            }).fail(function(errorInfor) {
                error({ messageId: errorInfor.messageId });
            }).always(function() {
                block.clear();
            });
        }
        
        public editMsg(selectedMsg: any): void {
             console.log(selectedMsg);   
        }
        
         public binData(): void {
            let self = this;
             
            // process events of buttons and editors
             function getGroupRows(data) {
                if (data === undefined) {
                    return;
                }
                var groupRows;
                groupRows = $.grep(data, function (rec) {
                    return rec.__gbRecord === true;
                });
                return groupRows;
            }
             $("#buttonExpandAll").igButton({
                click: function (evt, args) {
                    var ds = $("#grid").data("igGrid").dataSource,
                        groupRows = getGroupRows(ds.groupByData());
                    if (!groupRows) {
                        return;
                    }
                    for (var i = 0; i < groupRows.length; i++) {
                        $("#grid").igGridGroupBy("expand", groupRows[i].id);
                    }
                }
            });
            $("#buttonCollapseAll").igButton({
                click: function (evt, args) {
                    var ds = $("#grid").data("igGrid").dataSource,
                       groupRows = getGroupRows(ds.groupByData());
                    if (!groupRows) {
                        return;
                    }
                    for (var i = 0; i < groupRows.length; i++) {
                        $("#grid").igGridGroupBy("collapse", groupRows[i].id);
                    }
                }
            });
            
            /*----------------- Instantiation -------------------------*/
            $("#grid").igGrid({
                
            });
        }
    }
    class ItemModel {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}

