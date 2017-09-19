module cps001.a.vm {
    import info = nts.uk.ui.dialog.info;
    import alert = nts.uk.ui.dialog.alert;
    import text = nts.uk.resource.getText;

    let DEF_AVATAR = 'images/avatar.png',
        __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];

    export class ViewModel {
        ccgcomponent: any = {
            baseDate: ko.observable(new Date()),
            //Show/hide options
            isQuickSearchTab: ko.observable(true),
            isAdvancedSearchTab: ko.observable(true),
            isAllReferableEmployee: ko.observable(true),
            isOnlyMe: ko.observable(true),
            isEmployeeOfWorkplace: ko.observable(true),
            isEmployeeWorkplaceFollow: ko.observable(true),
            isMutipleCheck: ko.observable(true),
            isSelectAllEmployee: ko.observable(true),
            onSearchAllClicked: (dataList: Array<any>) => {
                let self = this;
            },
            onSearchOnlyClicked: (data: any) => {
                let self = this;
                console.log(data);
            },
            onSearchOfWorkplaceClicked: (dataList: Array<any>) => {
                let self = this;
            },
            onSearchWorkplaceChildClicked: (dataList: Array<any>) => {
                let self = this;
            },
            onApplyEmployee: (dataEmployee: Array<any>) => {
                let self = this;
            }
        };

        person: KnockoutObservable<PersonInfo> = ko.observable(new PersonInfo({ id: '' }));

        constructor() {
            let self = this,
                person = self.person();

            self.start();
        }

        start() {
            let self = this;
            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);
        }

        saveData() {
            let self = this;

            // push data layout to webservice
            block();
            service.saveData({}).done(() => {
                self.start();
                info({ messageId: "Msg_15" }).then(function() {
                    unblock();
                });
            }).fail((mes) => {
                unblock();
                alert(mes.message);
            });
        }
    }

    interface IPersonInfo {
        id: string;
        code?: string;
        avatar?: string;
        fullName?: string;
    }

    class PersonInfo {
        id: KnockoutObservable<string> = ko.observable('');
        code: KnockoutObservable<string> = ko.observable('');
        avatar: KnockoutObservable<string> = ko.observable(DEF_AVATAR);
        fullName: KnockoutObservable<string> = ko.observable('');

        constructor(param: IPersonInfo) {
            let self = this;

            self.id(param.id || '');
            self.code(param.code || '');
            self.avatar(param.avatar || DEF_AVATAR);
            self.fullName(param.fullName || '');
        }
    }
}