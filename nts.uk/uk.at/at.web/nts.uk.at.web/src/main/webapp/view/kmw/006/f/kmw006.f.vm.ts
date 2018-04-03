module nts.uk.at.view.kmw006.f.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {

        items: KnockoutObservableArray<ItemModel>;

        constructor() {
            var self = this;
            this.items = ko.observableArray([]);

            for (let i = 1; i < 100; i++) {
                this.items.push(new ItemModel('00' + i, '基本給', "description " + i));
            }

            $("#single-list").ntsGrid({
                height: '300px',
                dataSource: this.items(),
                primaryKey: 'code',
                columns: [
                    { headerText: 'No', key: '', dataType: 'number', width: '60px' },
                    { headerText: 'code', key: 'code', dataType: 'string', width: '160px' },
                    { headerText: 'name', key: 'name', dataType: 'string', width: '160px' },
                    { headerText: 'description', key: "description", dataType: "string", width: '160px' },
                    { headerText: 'abcdef', key: '', dataType: 'string', width: '160px' }
                ],
                features: [
                    {
                        name: 'Paging',
                        pageSize: 10,
                        currentPageIndex: 0, 
                        showPageSizeDropDown: false
                    }
                ]
            });
        }

        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

    }

    class ItemModel {
        code: string;
        name: string;
        description: string;
        constructor(code: string, name: string, description: string) {
            this.code = code;
            this.name = name;
            this.description = description;
        }
    }

}