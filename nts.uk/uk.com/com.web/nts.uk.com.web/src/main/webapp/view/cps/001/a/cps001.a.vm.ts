module cps001.a.vm {
    import info = nts.uk.ui.dialog.info;
    import alert = nts.uk.ui.dialog.alert;
    import text = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import showDialog = nts.uk.ui.dialog;

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
            onSearchAllClicked: (dataList: Array<IEmployeeInfo>) => {
                let self = this;

                self.listEmployees.removeAll();
                self.listEmployees(dataList);
            },
            onSearchOnlyClicked: (data: IEmployeeInfo) => {
                let self = this;

                self.listEmployees.removeAll();
                self.listEmployees([data]);
            },
            onSearchOfWorkplaceClicked: (dataList: Array<IEmployeeInfo>) => {
                let self = this;

                self.listEmployees.removeAll();
                self.listEmployees(dataList);
            },
            onSearchWorkplaceChildClicked: (dataList: Array<IEmployeeInfo>) => {
                let self = this;

                self.listEmployees.removeAll();
                self.listEmployees(dataList);
            },
            onApplyEmployee: (dataList: Array<IEmployeeInfo>) => {
                let self = this;

                self.listEmployees.removeAll();
                self.listEmployees(dataList);
            }
        };

        tabActive: KnockoutObservable<string> = ko.observable('layout');
        person: KnockoutObservable<PersonInfo> = ko.observable(new PersonInfo({ personId: '' }));
        layout: KnockoutObservable<any> = ko.observable({ items: ko.observableArray([]) });
        listEmployees: KnockoutObservableArray<IEmployeeInfo> = ko.observableArray([]);

        constructor() {
            let self = this,
                person = self.person();

            self.start();
        }

        start() {
            let self = this,
                layout = self.layout();

            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);

            // demo data layout
            service.getData().done(x => {
                layout.items(x.itemsClassification);
            });
        }

        deleteEmployee() {
            let self = this;

            modal('../b/index.xhtml').onClosed(() => { });
        }

        unManagerEmployee() {
            let self = this;

            modal('../c/index.xhtml').onClosed(() => { });
        }

        pickLocation() {
            let self = this;

            modal('../e/index.xhtml').onClosed(() => { });
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

    interface IEmployeeInfo {
        employeeId: string;
        text?: string;
        employeeCode?: string;
        employeeName?: string;
        workplaceId: string;
        workplaceCode?: string;
        workplaceName?: string;
    }

    interface IPersonInfo {
        personId: string;
        birthDate?: Date;
        gender?: number;
        countryId?: number;
        mailAddress?: string;
        personMobile?: string;
        code?: string;
        avatar?: string;
        fullName?: string;
    }

    class PersonInfo {
        personId: KnockoutObservable<string> = ko.observable('');
        code: KnockoutObservable<string> = ko.observable('');
        avatar: KnockoutObservable<string> = ko.observable(DEF_AVATAR);
        fullName: KnockoutObservable<string> = ko.observable('');

        constructor(param: IPersonInfo) {
            let self = this;

            self.personId(param.personId || '');
            self.code(param.code || '');
            self.avatar(param.avatar || DEF_AVATAR);
            self.fullName(param.fullName || '');
        }
    }
}