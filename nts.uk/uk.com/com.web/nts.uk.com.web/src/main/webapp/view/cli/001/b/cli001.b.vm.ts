module cli001.b.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import errors = nts.uk.ui.errors;

    export class ScreenModel {
        searchInput: KnockoutObservable<any>;
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentId: KnockoutObservable<any>;
        count: number = 100;

        constructor() {
            this.items = ko.observableArray([]);
            this.columns = ko.observableArray([
                { key: 'userID', hidden: true },
                { headerText: getText('CLI001_21'), key: 'loginID', width: 100 },
                { headerText: getText('CLI001_22'), key: 'userName', width: 150 },
            ]);
            this.searchInput = ko.observable();
            this.currentId = ko.observable();
        }

        search(): void {
            $('#searchInput').ntsError('check');
            if (errors.hasError())
                return;

            let self = this;
            let input = self.searchInput().trim();
            block.invisible();
            service.findByFormSearch(input).done(function(data) {
                console.log(data);
                self.items(data);
            }).fail(error => {
                alertError(error);
            }).always(() => {
                block.clear();
            });
        }

        unlock(): void {
            alert(this.currentId());
        }
    }

    class ItemModel {
        userID: string;
        loginID: string;
        userName: string;
        constructor(userID: string, loginID: string, userName: string) {
            this.userID = userID;
            this.loginID = loginID;
            this.userName = userName;
        }
    }
}
