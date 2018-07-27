module nts.uk.at.view.kal003.b1.viewmodel{
    import block = nts.uk.ui.block;
    import errors = nts.uk.ui.errors;
    import dialog = nts.uk.ui.dialog;
    import windows = nts.uk.ui.windows;
    import resource = nts.uk.resource;
    import sharemodel = nts.uk.at.view.kal003.share.model;
    import shareutils = nts.uk.at.view.kal003.share.kal003utils;

    export class ScreenModel {
        listTypeCheckWorkRecords    : KnockoutObservableArray<model.EnumModel> = ko.observableArray([]);
        checkItem: KnockoutObservable<number>;
        messageColor: KnockoutObservable<string>;
        displayMessage: KnockoutObservable<string>;
        messageBold: KnockoutObservable<boolean>;
        
        constructor() {
            let self = this;
                
            self.checkItem = ko.observable(0);
            self.messageColor = ko.observable('');
            self.displayMessage = ko.observable('');
            self.messageBold = ko.observable(false);
        }

        //initial screen
        start(): JQueryPromise<any> {
            let self = this,
            dfd = $.Deferred();
            
            $.when(self.getAllEnums()).done(function() {
                //initial screen - in case update
                dfd.resolve();
            }).fail(() => {
                dfd.reject();
            });
            
           return dfd.promise();
        }
        
        // ===========common begin ===================
        private getAllEnums() : JQueryPromise<any> {
            let self = this,
            dfd = $.Deferred();

            $.when(service.getEnumTypeCheckWorkRecord()).done((listTypeCheckWorkRecord : Array<model.EnumModel>) => {
                    self.listTypeCheckWorkRecords(self.getLocalizedNameForEnum(listTypeCheckWorkRecord));
                    dfd.resolve();
            }).always(() => {
                dfd.resolve();
            });
            
            return dfd.promise();
        }
        
        /**
         * get Localize name by Enum
         * @param listEnum
         */
        private getLocalizedNameForEnum(listEnum : Array<model.EnumModel>) : Array<model.EnumModel> {
            if (listEnum) {
                for (var i = 0; i < listEnum.length; i++) {
                    if (listEnum[i].localizedName) {
                        listEnum[i].localizedName = resource.getText(listEnum[i].localizedName);
                    }
                }
                return listEnum;
            }
            return [];
        }
        
        /**
         * close dialog B and return result
         */
        btnDecision() {
            let self = this;
            
        }
        
        /**
         * close dialog B and return result
         */
        closeDialog() {
            
            windows.close();
        }
    }
}