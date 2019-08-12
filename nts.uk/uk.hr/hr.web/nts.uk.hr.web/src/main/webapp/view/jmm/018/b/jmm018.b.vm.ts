module nts.uk.com.view.jmm018.b {
//    import SystemResourceDto = model.SystemResourceDto;
//    import SystemResourceCommand = model.SystemResourceSaveCommand;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import blockUI = nts.uk.ui.block;
    let __viewContext: any = window["__viewContext"] || {};
    
    export module viewmodel {
        export class ScreenModel {
            hrDevEventList: KnockoutObservableArray<any> = ko.observableArray([]);
            listEventId: KnockoutObservableArray<any> = ko.observableArray([]);

            constructor(){
                let self = this;
                let listEnum = __viewContext.enums.EventId;
                _.forEach(listEnum, (a) => {
                        self.listEventId.push(new ItemModel(a.value, a.name));
                    });
            }
            
            /**
             * init default data when start page
             */
            public start_page(): JQueryPromise<void> {
                var dfd = $.Deferred<void>();
                let self = this;
                listEnum();
                service.findEventMenu().done(function(data: any){
                    self.hrDevEventList(data.eventOperList);
                    
                    console.log(self.listEventId());
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
                $("#hierarchicalGrid").hierarchicalGrid({
                    height: '100%',
                    tabIndex: 3,
                    width: "100%",
                    autoGenerateColumns: false,
                    dataSource: self.hrDevEventList(),
                    responseDataKey: "results",
                    dataSourceType: "json",
                        
                    features: [
                                {
                                    name: "Responsive",
                                    enableVerticalRendering: false,
                                    columnSettings: [
                                        {
                                            columnKey: "Title",
                                            classes: "ui-hidden-phone"
                                        },
                                        {
                                            columnKey: "Address",
                                            classes: "ui-hidden-phone"
                                        }
                                    ]
                                },
                                {
                                    name: "Sorting",
                                    inherit: true
                                },
                                {
                                    name: "Paging",
                                    pageSize: 25,
                                    type: "local",
                                    inherit: true
                                }
                            ],
                    
                    columns: [
                        {
                            headerText: 'ID',
                            key: 'eventId',
                            hidden: true
                        },

                        {
                            headerText: getText('JMM018_B422_17'), key: 'categoryName', dataType: 'string', width: '5%'
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

                });
            }
            
            
       }
           
    }

    class ItemModel {
        code: number;
        name: string;
        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}