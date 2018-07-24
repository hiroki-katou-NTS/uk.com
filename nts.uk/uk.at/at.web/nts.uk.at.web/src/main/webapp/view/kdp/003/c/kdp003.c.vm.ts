module nts.uk.at.view.kdp003.c{
    import getText = nts.uk.resource.getText;
    export module viewmodel {

        export class ScreenModel {
            
       
            dataSource: any;
            selectedList: KnockoutObservableArray<any>;

            constructor() {
                var self = this;
                self.dataSource = [];
                self.selectedList = ko.observableArray([]);
                var features = [];
                features.push({
                    name: 'Selection',
                    mode: 'row',
                    activation: false,
                    rowSelectionChanged: this.selectionChanged.bind(this)
                });
                features.push({ name: 'Sorting', type: 'local' });
                features.push({ name: 'RowSelectors',  enableRowNumbering: true });

                $("#grid").igGrid({
                    columns: [
                        { headerText: nts.uk.resource.getText("KDP003_40"),key: "userId", dataType: "string",width: 100  },
                        { headerText: nts.uk.resource.getText("KDP003_41"),key: "loginId", dataType: "string",width: 100 },
                        { headerText: nts.uk.resource.getText("KDP003_42"), key: "userName" ,dataType: "string",width: 100},
                        { headerText: nts.uk.resource.getText("KDP003_43"), key: "lockOutDateTime", dataType: "string",width: 100},
                        { headerText: nts.uk.resource.getText("KDP003_44"), key: "logType", dataType: "string",width: 100},
                        { headerText: nts.uk.resource.getText("KDP003_45"), key: "logType", dataType: "string",width: 100},
                        { headerText: nts.uk.resource.getText("KDP003_46"), key: "logType", dataType: "string",width: 100},
                        { headerText: nts.uk.resource.getText("KDP003_47"), key: "logType", dataType: "string",width: 100},
                        { headerText: nts.uk.resource.getText("KDP003_48"), key: "logType", dataType: "string",width: 100},
                        { headerText: nts.uk.resource.getText("KDP003_49"), key: "logType", dataType: "string",width: 100},
                        { headerText: nts.uk.resource.getText("KDP003_50"), key: "logType", dataType: "string",width: 100},
                        { headerText: nts.uk.resource.getText("KDP003_51"), key: "logType", dataType: "string",width: 100},
                        { headerText: nts.uk.resource.getText("KDP003_52"), key: "logType", dataType: "string",width: 100},
                        { headerText: nts.uk.resource.getText("KDP003_53"), key: "logType", dataType: "string",width: 100},
                    ],
                    features: [{
                        name: 'Selection',
                        mode: 'row',
                        multipleSelection: false,
                        activation: false,
                        rowSelectionChanged: this.selectionChanged.bind(this)
                    },
                        { name: 'Sorting', type: 'local' },
                        //{ name: 'RowSelectors', enableCheckBoxes: true, enableRowNumbering: false },
                        { name: 'Paging',
                            pageSize: 20,
                            currentPageIndex: 0
                        },
                    ],
                    virtualization: true,
                    virtualizationMode: 'continuous',
                    width: "1080px",
                    height: "400px",
                    primaryKey: "userId",
                    dataSource: self.dataSource
                });
                $("#grid").closest('.ui-iggrid').addClass('nts-gridlist');
                $("#grid").setupSearchScroll("igGrid", true);

            }

            selectionChanged(evt, ui) {
                //console.log(evt.type);
                var selectedRows = ui.selectedRows;
                var arr = [];
                for (var i = 0; i < selectedRows.length; i++) {
                    arr.push("" + selectedRows[i].id);
                }
                this.selectedList(arr);
            };

            

            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                dfd.resolve();

                return dfd.promise();
            }

        }
    }
    
     
}