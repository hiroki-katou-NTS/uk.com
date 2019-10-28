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
        
        guideMessageList: any;
        pageSize: number;
        
        categoryCode: KnockoutObservable<string>;
        eventName: KnockoutObservable<string>;
        programName: KnockoutObservable<string>;
        screenName: KnockoutObservable<string>;
        useSet: KnockoutObservable<string>;
        editTem: string;
        constructor() {
            let self = this;
            self.categoryList = ko.observableArray([]);
            self.guideMessageList = [];
            self.pageSize = 10;
            self.categoryCode = ko.observable("");
            self.eventName = ko.observable("");
            self.programName = ko.observable("");
            self.screenName = ko.observable("");
            self.useSet = ko.observable('1');
            self.useList = ko.observableArray([]);
            self.editTem = "<a href='#' onclick=\"editMsg({ id: '${guideMsgId}' })\">"+getText('JMM017_B422_23')+"</a>";
           
            $("#grid").bind("iggridpagingpagesizechanging", function(c, v) {
                let sidebarArea = $(".sidebar-html")[0].getBoundingClientRect().height, 
                    objCalulator = self.caculator();
                $(".contents-area").css({overflow: 'auto', height: objCalulator.contentAreaHeight +"px"});
                if (v.newPageSize <  25) {
                   $("#grid").igGrid("option", "height", sidebarArea <969? objCalulator.gridAreaHeight: objCalulator.contentAreaHeight);
                } else {
                   $("#grid").igGrid("option", "height", objCalulator.gridAreaHeight);
                }
            });
            
            $(window).resize(function() {
                let objCalulator = self.caculator();
                $(".contents-area").css({height: objCalulator.contentAreaHeight +"px"});
                $("#grid").igGrid("option", "height", objCalulator.gridAreaHeight);
            });
        }
        
        caculator(): any {
            let sidebarArea = $(".sidebar-html")[0].getBoundingClientRect().height, 
                contentArea = $(".sidebar-html")[0].getBoundingClientRect().height - ($("#header")[0].getBoundingClientRect().height + $(".sidebar-content-header")[0].getBoundingClientRect().height + $(".nts-guide-area")[0].getBoundingClientRect().height + 10) ,
                groupArea = $("#grid_container > div.ui-widget.ui-helper-clearfix.ui-iggrid-pagesizedropdowncontainerabove.ui-iggrid-toolbar.ui-widget-header.and.ui-corner-top")[0].getBoundingClientRect().height + $("#grid_groupbyarea")[0].getBoundingClientRect().height;
         return {contentAreaHeight: contentArea, gridAreaHeight: contentArea + groupArea};
        }
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.useList([{ code: "1", name: "" }, { code: "2", name: "使用する"},{ code: "3", name: "使用しない"}]);
            new service.getGuideCategory().done(function(data: any) {
                //$('#gridContent').css( "height", "200px");
                self.categoryList(data);
                self.bindData();
                $("#grid").igGridGroupBy("groupByColumn", 'categoryName');
                $("#grid").igGridGroupBy("groupByColumn", 'programName');
            }).fail(function(error) {
                $('#gridContent').css( "height", "200px");
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
                categoryCode: self.categoryCode() == 0 ? '' : self.categoryCode(),
                eventName: self.eventName(),
                programName: self.programName(),
                screenName: self.screenName(),
                useSet: self.useSet()==1?null:self.useSet()==2
            }
            new service.getGuideMessageList(param).done(function(data: any) {
                let sidebarArea = $(".sidebar-html")[0].getBoundingClientRect().height, 
                    objCalulator = self.caculator();
                $(".contents-area").css({overflow: "auto", height: objCalulator.contentAreaHeight +"px"});
                if( $("#grid").igGridPaging("option","pageSize") > 20){
                     $("#grid").igGrid("option", "height", objCalulator.contentAreaHeight);
                }else{
                     $("#grid").igGrid("option", "height", sidebarArea < 969? objCalulator.gridAreaHeight :objCalulator.contentAreaHeight);
                }
               
                let groupByColumns = $("#grid").igGridGroupBy("groupByColumns");
                self.guideMessageList = data;
                //$('#gridContent').css( "height", "430px");
                self.bindData();
//                setTimeout(() => {
                    $("#grid").igGridGroupBy("ungroupAll");
                    _.forEach(groupByColumns, col => {
                        $("#grid").igGridGroupBy("groupByColumn", col.key);
                    });
                    $("#grid").igGridPaging("pageIndex", 0);
//                }, 1);
            }).fail(function(errorInfor) {
                error({ messageId: errorInfor.messageId });
                let groupByColumns = $("#grid").igGridGroupBy("groupByColumns");
                self.guideMessageList = [];
                //$('#gridContent').css( "height", "200px");
                self.bindData();
                $("#grid").igGridGroupBy("ungroupAll");
                _.forEach(groupByColumns, col => {
                    $("#grid").igGridGroupBy("groupByColumn", col.key);
                });
                $("#grid").igGridPaging("pageIndex", 0);    
            }).always(function() {
                block.clear();
            });
        }
        
        public reloadGrid(): void {
            block.grayout();
            let self = this;
            let pageSizeOld =  $('#grid').igGridPaging("option", "pageSize");
            let groupByColumns = $("#grid").igGridGroupBy("groupByColumns");
            let filters = $("#grid").data("igGridFiltering")._filteringExpressions
            let pageIndex = $('#grid').igGridPaging("option", "currentPageIndex");
            self.bindData();
            setTimeout(() => {
                _.forEach(groupByColumns, col => {
                    $("#grid").igGridGroupBy("groupByColumn", col.key);
                });
                $("#grid").igGridPaging("pageSize", pageSizeOld);
                $("#grid").igGridFiltering("filter", filters);
                $("#grid").igGridPaging("pageIndex", pageIndex);
            }, 1);
            block.clear();
        }
        
        public editMsg(msgId: string): void {
            let self = this;
            let index = _.findIndex(self.guideMessageList, function(o) { return o.guideMsgId == msgId; });
            let guideMsg = _.find(self.guideMessageList, function(o) { return o.guideMsgId == msgId });
            setShared('guideMsg', guideMsg);
            nts.uk.ui.windows.sub.modal('/view/jmm/017/c/index.xhtml').onClosed(function(): any {
                if (getShared('guideMsgB')) {
                    self.guideMessageList[index] = getShared('guideMsgB');
                    setShared("guideMsgB", undefined);
                    self.reloadGrid();
                }
            });
        }
        
         public bindData(): void {
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
                autoGenerateColumns: false,
                primaryKey: 'guideMsgId',
                columns: [
                    {
                        headerText: 'ID',
                        key: 'guideMsgId',
                        hidden: true
                    },
                    {
                         headerText: '', 
                         key: 'Edit',
                         dataType: 'string', 
                         width: '5%', 
                         unbound: true, 
                         template: self.editTem
                     },
                    {
                        headerText: getText('JMM017_B422_17'), key: 'categoryName', dataType: 'string', width: '5%'
                    },
                    {
                        headerText: getText('JMM017_B422_18'), key: 'eventName', dataType: 'string', width: '10%'
                    },
                    {
                        headerText: getText('JMM017_B422_19'), key: 'programName', dataType: 'string', width: '15%'
                    },
                    {
                        headerText: getText('JMM017_B422_20'), key: 'screenName', dataType: 'string', width: '15%'
                    },
                    {
                        headerText: getText('JMM017_B422_21'), key: 'usageFlgByScreen', dataType: 'string', width: '10%'
                    },
                    {
                        headerText: getText('JMM017_B422_22'), key: 'guideMsg', dataType: 'string', width: '25%'
                    }
                ],
                dataSource: self.guideMessageList,
                dataSourceType: 'json',
                responseDataKey: 'results',
                height: '100%',
                width: '100%',
                tabIndex: 14,
                features: [
                    {
                        name: 'Paging', 
                        type: 'local',
                        pageSize: self.pageSize
                    },
                    {
                        name: 'Filtering', 
                        type: 'local',
                        mode: 'simple'
                    },
                    {
                        name: 'Sorting', 
                        type: 'local'
                    },
                    {
                        name: 'GroupBy', 
                        groupByDialogContainment: 'window'
                    },
                    {
                        name: "CellMerging"
                    },
                    {
                        name: 'Hiding' 
                    },
                    {
                        name: 'Resizing' 
                    },
                    {
                        name: 'Updating',
                        editMode: 'none',
                        enableAddRow: false,
                        enableDeleteRow: false
                    },
                    {
                        name: 'ColumnMoving', 
                        columnMovingDialogContainment: 'window'
                    }, 
                    {
                        name: "Tooltips",                        
                        cursorLeftOffset: 50,
                        cursorTopOffset: 50  
                    }    
                ]
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

function editMsg(param) {
    nts.uk.ui._viewModel.content.tab2ViewModel().editMsg(param.id);
}

