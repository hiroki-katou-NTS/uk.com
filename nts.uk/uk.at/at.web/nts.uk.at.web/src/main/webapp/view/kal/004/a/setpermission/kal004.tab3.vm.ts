module nts.uk.at.view.kal004.tab3.viewmodel {
    import share = nts.uk.at.view.kal004.share.model;

    export class ScreenModel {
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        executionAuthor: KnockoutObservable<string>;
        listRoleID: KnockoutObservableArray<string> = ko.observableArray([]);
        createMode:  KnockoutObservable<boolean>;
        constructor() {
            var self = this;
            self.roundingRules = ko.observableArray([
                { code: '0', name: nts.uk.resource.getText('KAL004_112') },
                { code: '1', name: nts.uk.resource.getText('KAL004_113') }
            ]);
            self.selectedRuleCode = ko.observable(1);
            self.executionAuthor = ko.observable("");
            self.createMode =ko.observable(true);
            self.selectedRuleCode.subscribe((newV) =>{
                if(newV== 1)
                    self.executionAuthor('');    
            });
            self.listRoleID.subscribe((newListRoleID) => {
                self.changeItem(newListRoleID);
            });
        }

        private changeItem(listRoleID: Array<string>): void {
            let self = this;
            service.getListRoleName(self.listRoleID()).done(function(listRole) {
                self.executionAuthor(_.join(_.map(listRole, "name"), ", "));
            });
        }


        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

                   
        
        openCDL025() {
            let self = this;
            let param = {
                currentCode: self.listRoleID(),
                roleType: 3,
                multiple: true
            };
            nts.uk.ui.windows.setShared("paramCdl025", param);
            nts.uk.ui.windows.sub.modal("com", "/view/cdl/025/index.xhtml").onClosed(() => {
                let data: Array<string> = nts.uk.ui.windows.getShared("dataCdl025");
                if (!nts.uk.util.isNullOrUndefined(data))
                    self.listRoleID(data);
            });
        }


    }
}
