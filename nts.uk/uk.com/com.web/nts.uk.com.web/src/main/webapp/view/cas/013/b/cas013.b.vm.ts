module nts.uk.com.view.cas013.b.viewmodel {


    export class ScreenModel {
        dataSource: any;
        columns: KnockoutObservableArray<any>;
        //search
        searchValue: KnockoutObservable<string>;
        //user
        selectUserID: KnockoutObservable<string>;
        userName: KnockoutObservable<string>;
        
        enable1 : KnockoutObserveble<boolean>;
        enable2 : KnockoutObserveble<boolean>;

        constructor() {
            var self = this;

            self.enable1 = ko.observable(true);
            self.enable2 = ko.observable(true);
            
            self.searchValue = ko.observable('');
            self.dataSource = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText(""), key: 'userID', hidden: true },
                { headerText: nts.uk.resource.getText("CAS013_29"), key: 'loginID', width: 100 },
                { headerText: nts.uk.resource.getText("CAS013_30"), key: 'userName', width: 230 }
            ]);
            self.selectUserID = ko.observable('');
        }

        search() {
            let self = this;
            if (nts.uk.text.isNullOrEmpty(self.searchValue())) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_438" });
                return;
            }
            service.searchUser(self.searchValue()).done(function(data) {
                self.dataSource(data);
            });
        }

        decision() {
            var self = this;
            if (nts.uk.text.isNullOrEmpty(self.selectUserID())) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_218" });
                return;
            }

            var selectUser: any = _.find(self.dataSource(), (item: any) => {
                return item.userID == self.selectUserID()
            });

            let dataSetShare = {
                decisionUserID: selectUser.userID,
                decisionLoginID: selectUser.loginID,
                decisionName: selectUser.userName
            };
            nts.uk.ui.windows.setShared("UserInfo", dataSetShare);
            self.cancel_Dialog();
        }

        cancel_Dialog(): any {
            nts.uk.ui.windows.close();
        }

    }
}