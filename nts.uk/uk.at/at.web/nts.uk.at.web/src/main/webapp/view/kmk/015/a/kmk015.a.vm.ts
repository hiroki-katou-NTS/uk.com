module nts.uk.at.view.kmk015.a {
    import OptionalItemHeaderDto = nts.uk.at.view.kmk015.a.service.model.WorkType;
    import OptionalHistory = nts.uk.at.view.kmk015.a.service.model.History;

    export module viewmodel {

        export class ScreenModel {
            columns: any;
            columnsHistory: any;
            numbereditor: any;
            numberDay: KnockoutObservable<number>;
            selectedCode: KnockoutObservable<string>;
            selectedCodeHistory: KnockoutObservable<string>;
            listWorkType: KnockoutObservableArray<OptionalItemHeaderDto>;
            hasSelected: KnockoutObservable<boolean>;
            nameWorkType: KnockoutObservable<string>;
            timeHistory: KnockoutObservable<string>;
            listHistory: KnockoutObservableArray<OptionalHistory>;
            

            constructor() {
                let self = this;
                self.listWorkType = ko.observableArray([]);
                self.hasSelected = ko.observable(true);
                
                // NumberEditor
                self.numbereditor = {
                    numberDay: ko.observable(),
                    constraint: 'CommonAmount',
                    option: {
                        width: "50",
                        defaultValue: 0,
                        unitID: 'DAYS'
                    },
                };

                self.selectedCode = ko.observable('');
                self.selectedCodeHistory = ko.observable('');
                self.nameWorkType = ko.observable('');
                self.timeHistory = ko.observable('');
                self.listHistory = ko.observableArray([]);
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KMK015_3'), key: 'workTypeCode', width: 40 },
                    { headerText: nts.uk.resource.getText('KMK015_4'), key: 'name', width: 180 },
                    {
                        headerText: nts.uk.resource.getText('KMK015_5'), key: 'abolishAtr', width: 50,
                        formatter: used => {
                            if (used == 1) {
                                return '<div style="text-align: center;max-height: 18px;">'
                                    + '<i class="icon icon-78"></i></div>';
                            }
                            return '';
                        }
                    }
                ]);
                self.columnsHistory = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KMK015_12'), key: 'time', width: 200 },
                ]);
                self.selectedCode.subscribe(code => {
                   self.listWorkType().forEach(function(item){
                        if (item.workTypeCode == code) {self.nameWorkType(item.name);}
                   }); 
                });
            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();

                // block ui
                nts.uk.ui.block.invisible();
                
                self.initialize().done(() => dfd.resolve());

                return dfd.promise();
            }
            
            /**
             * Initialization
             */
            public initialize(): JQueryPromise<void> {
                let self = this;
                let dfd = $.Deferred<void>();
                
                // get list worktype
                service.findListWorkType().done(res => {
                    self.listWorkType(res);
                    
                    // Select first item
                    self.selectedCode(res[0].workTypeCode);
                    self.nameWorkType(res[0].name);
                    
                    //get List History
                    service.getHistoryByWorkType(self.selectedCode()).done(data => {
                        self.listHistory(data);
                    });
                    dfd.resolve();
                }).always(() => nts.uk.ui.block.clear()); // clear block ui.
                
                
                
                return dfd.promise();
            }
            
            //open dialog 003 
            OpenDialogB() {
                let self = this;
                let workTypeCodes = self.selectedCode();
                nts.uk.ui.windows.setShared('parentCodes', {
                    workTypeCodes: workTypeCodes,
                }, true);
    
                nts.uk.ui.windows.sub.modal('/view/kmk/015/b/index.xhtml').onClosed(function(): any {
                    //view all code of selected item 
                    var childData = nts.uk.ui.windows.getShared('childData');
                    if (childData) {
                        self.timeHistory(childData.timeHistory);
                    }
                })
            }
            
            /**
             * Submit.
             */
            public submit() {
                let self = this;
                let dfd = $.Deferred<void>();


                // Loading, block ui.
                nts.uk.ui.block.invisible();


                return dfd.promise();
            }
        }
    }
}