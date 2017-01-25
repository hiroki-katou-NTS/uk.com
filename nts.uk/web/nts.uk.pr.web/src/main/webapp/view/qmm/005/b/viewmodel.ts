module nts.uk.pr.view.qmm005.b.viewmodel {
    export class ViewModel {
        date: any;
        selectedCodes: any;
        tableItems: any;
        listboxItem: any;
        constructor() {
            this.date = ko.observable(new Date());
            this.selectedCodes = ko.observableArray([2]);

            this.listboxItem = ko.observableArray([
                new SelectItem(1, "Option 1"),
                new SelectItem(2, "Option 2"),
                new SelectItem(3, "Option 3"),
                new SelectItem(4, "Option 4"),
                new SelectItem(5, "Option 5"),
                new SelectItem(6, "Option 6"),
                new SelectItem(7, "Option 7"),
                new SelectItem(8, "Option 8"),
                new SelectItem(9, "Option 9"),
                new SelectItem(10, "Option 10"),
                new SelectItem(11, "Option 11"),
                new SelectItem(12, "Option 12"),
                new SelectItem(13, "Option 13"),
                new SelectItem(14, "Option 14"),
                new SelectItem(15, "Option 15"),
                new SelectItem(16, "Option 16"),
                new SelectItem(17, "Option 17")
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
        }

        showModalDialogC(item, event): void {
            nts.uk.ui.windows.sub.modal("../c/index.xhtml", { width: 682, height: 370, title: '処理区分の追加' })
            .onClosed(() => {
                alert('ok');
            });
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