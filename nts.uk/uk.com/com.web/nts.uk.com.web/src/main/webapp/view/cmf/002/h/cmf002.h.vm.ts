module nts.uk.com.view.cmf002.h.viewmodel {
    import model = nts.uk.com.view.cmf002.share.model;
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import error = nts.uk.ui.errors;

    export class ScreenModel {
        itemList: KnockoutObservableArray<ItemModel>;
        itemName: KnockoutObservable<string>;
        currentCode: KnockoutObservable<number>
        mode: KnockoutObservable<number>
        selectedCode: KnockoutObservable<number>;
        isEnable: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            self.itemList = ko.observableArray([
                new ItemModel(0, '')
            ]);
            self.itemName = ko.observable('');
            self.currentCode = ko.observable(7);
            self.selectedCode = ko.observable(null);
            self.isEnable = ko.observable(true);
            self.mode = ko.observable(0);

            service.getIdtSetting().done(function(data: Array<any>) {
                if (data && data.length) {
                    let _rsList: Array<ItemModel> = _.map(data, rs => {
                        return new ItemModel(rs.value, rs.localizedName);
                    })
                    self.itemList(_rsList);
                }
            }).fail(error => {
                alertError({ messageId: "Msg" });
            });

            $('#list-box').on('selectionChanging', function(event) {
                console.log('Selecting value:' + (<any>event.originalEvent).detail);
            })
            $('#list-box').on('selectionChanged', function(event: any) {
                console.log('Selected value:' + (<any>event.originalEvent).detail)
            })
        }

        deselectAll() {
            $('#list-box').ntsListBox('deselectAll');
        }

        selectAll() {
            $('#list-box').ntsListBox('selectAll');
        }

        /**
* Close dialog.
*/
        cancelSetting(): void {
            nts.uk.ui.windows.close();
        }

        //設定
        saveData() {
            let self = this;
            nts.uk.ui.windows.setShared('CMF002H_Params', {
                mode: self.mode()
            });
            switch (self.selectedCode()) {
                case "0": nts.uk.ui.windows.sub.modal("/view/cmf/002/i/index.xhtml");
                    break;
                case "1": nts.uk.ui.windows.sub.modal("/view/cmf/002/j/index.xhtml");
                    break;
                case "2": nts.uk.ui.windows.sub.modal("/view/cmf/002/k/index.xhtml");
                    break;
                case "3": nts.uk.ui.windows.sub.modal("/view/cmf/002/l/index.xhtml");
                    break;
                case "4": nts.uk.ui.windows.sub.modal("/view/cmf/002/m/index.xhtml");
                    break;
                case "5": nts.uk.ui.windows.sub.modal("/view/cmf/002/n/index.xhtml");
                    break;
            }
        }
    }

    class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}