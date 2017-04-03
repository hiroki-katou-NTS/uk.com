module qmm020.k.viewmodel {
    import option = nts.uk.ui.option;
    export class ScreenModel {
        currentItem: KnockoutObservable<any>;
        scrType: KnockoutObservable<string>;
        startYM: KnockoutObservable<string>;
        endYM: KnockoutObservable<string>;
        selectStartYm: KnockoutObservable<string>;
        startYmHis: KnockoutObservable<number>;
        timeEditorOption: KnockoutObservable<any>;
        //---radio        
        isRadioCheck: KnockoutObservable<number>;
        itemsRadio: KnockoutObservableArray<any>;
        enableYm: KnockoutObservable<boolean>;
        /**
         * Init screen model.
         */
        constructor() {
            var self = this;
            self.startYmHis = ko.observable(null);
            self.enableYm = ko.observable(false);
            self.scrType = ko.observable(nts.uk.ui.windows.getShared('scrType'));
            self.startYM = ko.observable(nts.uk.ui.windows.getShared('startYM'));
            self.endYM = ko.observable(nts.uk.ui.windows.getShared('endYM'));
            self.currentItem = ko.observable(nts.uk.ui.windows.getShared('currentItem'));
            self.selectStartYm = ko.observable(nts.uk.ui.windows.getShared('startYM'));
            
            if (self.scrType() === '1') {
                $('#K_LBL_005').parent().hide();
                $('#K_LBL_006').hide();
            }
            //console.log(option);
            self.timeEditorOption = ko.mapping.fromJS(new option.TimeEditorOption({ inputFormat: "yearmonth" }));
            //---radio
            self.itemsRadio = ko.observableArray([
                { value: 1, text: '履歴を削除する' },
                { value: 2, text: '履歴を修正する' }
            ]);
            self.isRadioCheck = ko.observable(1);
        }

        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            //list data
            //self.buildItemList();
            $('#F_LST_001').on('selectionChanging', function(event) {
                console.log('Selecting value:' + (<any>event.originalEvent).detail);
            })
            $('#F_LST_001').on('selectionChanged', function(event: any) {
                console.log('Selected value:' + (<any>event.originalEvent).detail)
            })
            //fill data to dialog
            //            service.getLayoutWithMaxStartYm().done(function(layout: Array<service.model.LayoutMasterDto>){
            //                self.layouts(layout);
            //                self();
            //            });

            //checkbox change
            self.isRadioCheck.subscribe(function(newValue) {
                if (newValue === 2) {
                    self.enableYm(true);
                } else {
                    self.enableYm(false);
                }
            })
            dfd.resolve();
            // Return.
            return dfd.promise();
        }

        //Setting History 
        historyProcess(): any {
            var self = this;
            //履歴の編集-削除処理
            if (self.isRadioCheck() == 1) {
                service.delComAllot(self.currentItem()).done(function() {
                }).fail(function(res) {
                    alert(res);
                });

            } else {
                alert('update');
            }
            nts.uk.ui.windows.close();
        }

        //close function
        closeDialog(): any {
            nts.uk.ui.windows.close();
        }
    }


}