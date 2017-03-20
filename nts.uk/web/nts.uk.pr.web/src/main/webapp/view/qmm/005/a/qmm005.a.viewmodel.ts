module qmm005.a {
    export class ViewModel {
        items: KnockoutObservableArray<TableRowItem>;
        constructor() {
            let _records: Array<TableRowItem> = [];
            for (let i = 1; i <= 5; i++) {
                _records.push(new TableRowItem(i, i < 3 ? "25日払い" : "", [{ index: 1, label: "Tabol" }], [{ index: 1, label: "Tabol" }], true, [{ index: 1, label: "Tabol" }], [{ index: 1, label: "Tabol" }]));
            }
            this.items = ko.observableArray(_records);
            services.getString();
        }

        // Navigate to qmp005/b/index.xhtml
        btn002Click(item, event): void {
            location.href = "../../../qmp005/b/index.xhtml";
        }

        btn003Click(item, event): void {
            
        }

        btn004Click(item, event): void {
            
        }

        btn005Click(item, event): void {
            
        }
    }

    class TableRowItem {
        index: KnockoutObservable<number>;
        label: KnockoutObservable<string>;

        sel001Arr: KnockoutObservableArray<SelectItem>;
        sel001Val: KnockoutObservable<any>;

        sel002Arr: KnockoutObservableArray<SelectItem>;
        sel002Val: KnockoutObservable<any>;

        sel003Val: KnockoutObservable<boolean>;

        sel004Arr: KnockoutObservableArray<SelectItem>;
        sel004Val: KnockoutObservable<any>;

        sel005Arr: KnockoutObservableArray<SelectItem>;
        sel005Val: KnockoutObservable<any>;

        constructor(index: number, label: string, sel001: Array<SelectItem>, sel002: Array<SelectItem>, sel003: boolean, sel004: Array<SelectItem>, sel005: Array<SelectItem>) {
            this.index = ko.observable(index);
            this.label = ko.observable(label);

            this.sel001Arr = ko.observableArray(sel001);
            this.sel001Val = ko.observable(sel001[0].index);

            this.sel002Arr = ko.observableArray(sel002);
            this.sel002Val = ko.observable(sel002[0].index);

            this.sel003Val = ko.observable(sel003);

            this.sel004Arr = ko.observableArray(sel004);
            this.sel004Val = ko.observable(sel004[0].index);

            this.sel005Arr = ko.observableArray(sel005);
            this.sel005Val = ko.observable(sel005[0].index);
        }

        enable(): boolean {
            return this.label() != '';
        }

        showModalDialogB(item, event): void {
            nts.uk.ui.windows.sub.modal("../b/index.xhtml", { width: 1200, height: 645, title: '給与処理月を翌月へ更新  ' })
                .onClosed(() => {
                    alert('ok');
                });
        }

        showModalDialogC(item, event): void {
            nts.uk.ui.windows.sub.modal("../c/index.xhtml", { width: 800, height: 515, title: '給与処理月を翌月へ更新  ' })
                .onClosed(() => {
                    alert('ok');
                });
        }

        showModalDialogD(item, event): void {
            nts.uk.ui.windows.sub.modal("../d/index.xhtml", { width: 800, height: 515, title: '給与処理月を翌月へ更新  ' })
                .onClosed(() => {
                    alert('ok');
                });
        }
    }

    class SelectItem {
        constructor(public index: number, public label: string) {
        }
    }
}