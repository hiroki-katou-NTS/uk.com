module nts.uk.at.view.ksp001.c.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import info = nts.uk.ui.dialog.info;
    import block = nts.uk.ui.block;

    export class ScreenModel {

        items: KnockoutObservableArray<SPMenu> = ko.observableArray([]);

        constructor() {
            let self = this;
            self.getMenuSpecial();
        }
        
        /**
         * get menu Name 
         */
        getMenuSpecial(): JQueryPromise<any> {
            block.grayout();
            let self = this,
                dfd = $.Deferred(),
                arr: any = [];
            service.getMenuSpecial().done((data) => {
                _.forEach(data.lstSPMenuDto, dt => {
                    arr.push(new SPMenu({
                        menuCd: dt.menuCd,
                        displayName: dt.displayName,
                        displayOrder: dt.displayOrder,
                        targetItems: dt.targetItems
                    }));
                });
                self.items(arr);
                $($('input.txtEditor')[0]).focus();
                dfd.resolve();
            }).fail((err) => {
                dfd.reject();
            }).always(() => {
                block.clear();
            });

            return dfd.promise();
        }
        
        /**
         * close dialog
         */
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }
        
        /**
         * save data
         */
        saveData(): JQueryPromise<any> {
            block.grayout();
            let self = this,
                dfd = $.Deferred(),
                params: any = [],
                system = 1, // 勤次郎
                menuCls = 9; // スマートフォン

            _.each(ko.toJS(self.items()), (item) => {
                params.push({
                    classification: menuCls,
                    code: item.menuCd,
                    displayName: item.displayName,
                    system: system
                });
            });

            service.changeMenuName({ standardMenus: params }).done(() => {
                info({ messageId: "Msg_15" }).then(() => {
                    self.closeDialog();
                });
                dfd.resolve();
            }).fail((err) => {
                dfd.reject();
            }).always(() => {
                block.clear();
            });

            return dfd.promise();
        }
    }

    export interface ISPMenu {
        menuCd: string,
        displayName: string,
        displayOrder: number,
        targetItems: string
    }

    export class SPMenu {
        menuCd: KnockoutObservable<string>;
        displayName: KnockoutObservable<string>;
        displayOrder: KnockoutObservable<number>;
        targetItems: KnockoutObservable<string>;

        constructor(param: ISPMenu) {
            let self = this;
            self.menuCd = ko.observable(param.menuCd);
            self.displayName = ko.observable(param.displayName);
            self.displayOrder = ko.observable(param.displayOrder);
            self.targetItems = ko.observable(param.targetItems);
        }
    }
}