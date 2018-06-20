module cps002.g.vm {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import close = nts.uk.ui.windows.close;
    import modal = nts.uk.ui.windows.sub.modal;
    import alert = nts.uk.ui.dialog.alert;
    import alertError = nts.uk.ui.dialog.alertError;
    import getText = nts.uk.resource.getText;
    import dialog = nts.uk.ui.dialog.info;


    export class ViewModel {

        recentRegistrationItems: KnockoutObservableArray<any> = ko.observableArray([
            { code: 1, name: getText('CPS002_89') },
            { code: 0, name: getText('CPS002_90') }
        ]);

        employeeInitItemList: KnockoutObservableArray<any> = ko.observableArray([
            { code: 1, name: getText('CPS002_78') },
            { code: 3, name: getText('CPS002_79') },
            { code: 2, name: getText('CPS002_80') }
        ]);

        cardNoInitItemList: KnockoutObservableArray<any> = ko.observableArray([
            { code: 1, name: getText('CPS002_78') },
            { code: 4, name: getText('CPS002_84') },
            { code: 3, name: getText('CPS002_85') },
            { code: 5, name: getText('CPS002_86') },
            { code: 2, name: getText('CPS002_80') }

        ]);

        currentUserSetting: KnockoutObservable<UserSetting> = ko.observable(new UserSetting());

        constructor() {


        }
        start(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            service.getUserSetting().done((result: IUserSetting) => {
                if (result) {
                    self.currentUserSetting(new UserSetting(result));
                }

                dfd.resolve();
            });

            return dfd.promise();

        }

        register() {
            let self = this,
                uSet = self.currentUserSetting();

            let command = {
                employeeId: "-1",
                empCodeValType: uSet.employeeCodeType(),
                cardNoValType: uSet.cardNumberType(),
                empCodeLetter: uSet.employeeCodeType() != 1 ? "" : uSet.employeeCodeLetter(),
                cardNoLetter: uSet.cardNumberType() != 1 ? "" : uSet.cardNumberLetter(),
                recentRegType: uSet.recentRegistrationType()
            };
            service.setUserSetting(command).done(function() {
                setShared("userSettingStatus", true);
                dialog({ messageId: "Msg_15" }).then(() => {
                    self.close();
                });
            })
                .fail(function() { setShared("userSettingStatus", false); })

        }
        close() { close(); }

    }

    class UserSetting {
        recentRegistrationType: KnockoutObservable<number> = ko.observable(1);
        employeeCodeType: KnockoutObservable<number> = ko.observable(2);
        employeeCodeLetter: KnockoutObservable<string> = ko.observable("");
        cardNumberType: KnockoutObservable<number> = ko.observable(5);
        cardNumberLetter: KnockoutObservable<string> = ko.observable("");
        constructor(param?: IUserSetting) {
            if (param) {
                this.recentRegistrationType(param.recentRegistrationType);
                this.employeeCodeType(param.employeeCodeType);
                this.employeeCodeLetter(param.employeeCodeLetter);
                this.cardNumberType(5);
                this.cardNumberLetter(param.cardNumberLetter);
            }
        }

    }
    interface IUserSetting {
        employeeCodeType: number;
        recentRegistrationType: number;
        cardNumberType: number;
        employeeCodeLetter: string;
        cardNumberLetter: string;
    }
}