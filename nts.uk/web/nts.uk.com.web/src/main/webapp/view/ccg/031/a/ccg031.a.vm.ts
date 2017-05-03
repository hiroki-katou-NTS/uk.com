module ccg031.a.viewmodel {
    export class ScreenModel {
        // Column & Row
        minRow: number = 4;
        minColumn: number = 6;
        totalRow: KnockoutObservable<number>;
        totalColumn: KnockoutObservable<number>;
        totalRowArray: KnockoutComputed<number[]>;
        totalColumnArray: KnockoutComputed<number[]>;

        constructor() {
            var self = this;
            this.totalRow = ko.observable(this.minRow);
            this.totalColumn = ko.observable(this.minColumn);
            this.totalRowArray = ko.computed((): Array<number> => {
                return self.numberToArray(self.totalRow());
            });
            this.totalColumnArray = ko.computed((): Array<number> => {
                return self.numberToArray(self.totalColumn());
            });
        }

        /** Start Page */
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }

        /** Add Column */
        addColumn(): void {
            this.totalColumn(this.totalColumn() + 1);
            this.placementOverflow();
        }

        /** Add Column */
        addRow(): void {
            this.totalRow(this.totalRow() + 1);
            this.placementOverflow();
        }
        
        /** Add Column */
        private placementOverflow(): void {
            if (this.totalRow() > this.minRow)
                $(".placement-container").addClass("overflow-y");
            if (this.totalColumn() > this.minColumn)
                $(".placement-container").addClass("overflow-x");
        }

        /** Open Add TopPage-Part Dialog */
        openDialog(row: number, column: number) {
            nts.uk.ui.windows.setShared("size", {row: row, column: column}, false);
            nts.uk.ui.windows.sub.modal("/view/ccg/031/b/index.xhtml");
        }

        /** Convert number to Array<number> */
        private numberToArray(value: number): Array<number> {
            var temp = [];
            var length = _.toInteger(value);
            for (var i = 1; i <= length; i++) {
                temp.push(i);
            }
            return temp;
        }
    }
}