module nts.uk.com.view.cas13.b.component {
    import getText = nts.uk.resource.getText;

    export interface Option {
        roleType?: number;
        multiple?: boolean;
    }

    export module viewmodel {
        export class ComponentModel {
            listUser: KnockoutObservableArray<model.User>;
            enable1 : KnockoutObserveble<boolean>;
            enable2 : KnockoutObserveble<boolean>;
            currentCode: any;
            private columns: KnockoutObservableArray<any>;
            private defaultOption: Option = {
                multiple: true
            }
            private setting: Option;
            private searchMode: string;

            constructor(option: Option) {
                let self = this;
                self.enable1 = ko.observable(true);
                self.enable2 = ko.observable(true);
                self.setting = $.extend({}, self.defaultOption, option);
                self.searchMode = (self.setting.multiple) ? "highlight" : "filter";
                self.listUser = ko.observableArray([]);
                if (self.setting.multiple)
                    self.currentCode = ko.observableArray([]);
                else
                    self.currentCode = ko.observable("");
                if (self.setting.multiple) {
                    self.columns = ko.observableArray([
                        { headerText: getText(""), prop: 'userID', width: 100, hidden: true },
                        { headerText: getText("CAS013_29"), prop: 'loginID', width: 100 },
                        { headerText: getText("CAS013_29"), prop: 'userName', width: 180 }
                    ]);
                } else {
                    self.columns = ko.observableArray([
                        { headerText: getText(""), prop: 'userID', width: 100, hidden: true },
                        { headerText: getText("CCG025_3"), prop: 'loginID', width: 100 },
                        { headerText: getText("CCG025_4"), prop: 'userName', width: 233 }
                    ]);
                }

            }

            /** functiton start page */
            startPage(): JQueryPromise<any> {
                let self = this;
                return self.getListUser();
            }//end start page

            /** Get list user*/
            private getListUser(): JQueryPromise<Array<model.User>> {
                let self = this;
                let dfd = $.Deferred();
                service.getListUser().done((data: Array<model.User>) => {
                    data = _.orderBy(data, ['loginID'], ['asc']);
                    self.listUser(data);
                    self.selectItem();
                    dfd.resolve(data);
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res.message).then(function() { nts.uk.ui.block.clear(); });
                });
                return dfd.promise();
            }

            /** Select first item */
            private selectItem(): void {
                var self = this;
                if (self.listUser().length > 0 && !self.setting.multiple) {
                    self.currentCode(self.listUser()[0].userID);
                }
            }

        }//end screenModel
    }//end viewmodel

    //module model
    export module model {

        /**
         * class Role
         */
        export class User {
            userID: string;
            loginID: string;
            userName: string;
            constructor(userID: string, loginID: string, userName: string) {
                this.userID = userID;
                this.loginID = loginID;
                this.userName = userName;
            }
        }//end class Role


    }//end module model

}//end module