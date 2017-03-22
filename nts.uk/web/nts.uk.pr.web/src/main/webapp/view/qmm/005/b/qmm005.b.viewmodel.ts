module qmm005.b {
    export class ViewModel {
        date: KnockoutObservable<Date>;
        selectedCodes: KnockoutObservableArray<number>;
        tableItems: any;
        listboxItem: any;
        constructor() {
            this.date = ko.observable(new Date());
            this.selectedCodes = ko.observableArray([2]);

            this.listboxItem = ko.observableArray([
                new SelectItem(1, "Option 1"),
                new SelectItem(2, "Option 2"),
                new SelectItem(3, "Option 3")
            ]);

            this.tableItems = ko.observableArray([
                new TableRowItem(1),
                new TableRowItem(2),
                new TableRowItem(3),
                new TableRowItem(4),
                new TableRowItem(5),
                new TableRowItem(6),
                new TableRowItem(7),
                new TableRowItem(8),
                new TableRowItem(9),
                new TableRowItem(10),
                new TableRowItem(11),
                new TableRowItem(12)
            ]);
        }

        toggleColumns(item, event): void {
            $('.toggle').toggleClass('hidden');
            $(event.currentTarget).parent('td').toggleClass('checkbox-cols');
            ($(event.currentTarget).text() == "-" && $(event.currentTarget).text('+')) || $(event.currentTarget).text('-');
            if (!$('.toggle').hasClass('hidden')) {
                nts.uk.ui.windows.getSelf().setWidth(1190);
            } else {
                nts.uk.ui.windows.getSelf().setWidth(730);
            }
        }

        showModalDialogC(item, event): void {
            nts.uk.ui.windows.sub.modal("../c/index.xhtml", { width: 682, height: 370, title: '処理区分の追加' }).onClosed(() => { });
        }

        showModalDialogD(item, event): void {
            nts.uk.ui.windows.sub.modal("../d/index.xhtml", { width: 682, height: 410, title: '処理区分の追加' })
                .onClosed(() => {
                    alert('ok');
                });
        }

        showModalDialogE(item, event): void {
            nts.uk.ui.windows.sub.modal("../e/index.xhtml", { width: 540, height: 600, title: '処理区分の追加' })
                .onClosed(() => {
                    alert('ok');
                });
        }
    }

    class SelectItem {
        constructor(public index: number, public label: string) {
        }
    }

    class TableRowItem {
        constructor(public index: number) {
        }

        toggleCalendar(item, event): void {
            $(event.currentTarget).parent('div').find('input[type=text]').trigger('click');
        }
    }
}