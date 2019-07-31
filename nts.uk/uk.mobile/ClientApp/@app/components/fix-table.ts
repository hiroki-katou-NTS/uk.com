import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    template: `<div class="table-container">
        <div class="table-header">
            <table class="table table-bordered">
                <tbody></tbody>
            </table>
        </div>
        <div class="table-body">
            <table class="table table-bordered">
                <slot/>
            </table>
        </div>
        <div class="table-footer">
            <table class="table table-bordered">
                <tbody></tbody>
            </table>
        </div>
    </div>`
})
export class FixTableComponent extends Vue {

    @Prop({ default: 4 })
    public displayColumns: number;

    public startDisplayIndex: number = 1;
    public oldStartDisplayIndex: number = 1;

    private headerTable: HTMLTableElement = null;
    private bodyTable: HTMLTableElement = null;
    private footerTable: HTMLTableElement = null;

    public mounted() {
        console.log('mounted hook, move table');
        this.bodyTable = this.$el.querySelector('.table-container .table-body table') as HTMLTableElement;
        this.headerTable = this.$el.querySelector('.table-container .table-header table') as HTMLTableElement;
        this.footerTable = this.$el.querySelector('.table-container .table-footer table') as HTMLTableElement;

        this.moveTheadAndTfoot();

        this.setStyleOfTableBody();

        this.hiddenColumns();

        this.addPrevNextButtons();
    }

    private moveTheadAndTfoot() {
        this.headerTable.tHead = this.bodyTable.tHead;
        this.footerTable.tFoot = this.bodyTable.tFoot;
    }

    private setStyleOfTableBody() {
        let tableBodyDiv = this.$el.querySelector('.table-container .table-body') as HTMLDivElement;

        let heightOfARow: string = (this.bodyTable.tBodies[0].firstChild.firstChild as HTMLTableCellElement).style.height;
        let height: any = heightOfARow.substring(0, heightOfARow.length - 3);

        tableBodyDiv.style.height = `250px` ;
        tableBodyDiv.style.overflowY = 'scroll';
    }

    private hiddenColumns() {
        let displayColumns = this.displayColumns;

        let headerColums = this.headerTable.tHead.firstChild.childNodes;
        for (let i = displayColumns + 1; i < headerColums.length - 1; i++) {
            (headerColums[i] as HTMLTableHeaderCellElement).classList.add('d-none');
        }

        let rows = this.bodyTable.tBodies[0].children as HTMLCollection;
        for (let row of rows) {
            for (let i = displayColumns + 1; i < headerColums.length - 1; i++) {
                (row.childNodes[i] as HTMLTableCellElement).classList.add('d-none');
            }
        }

        let footerColums = this.footerTable.tFoot.firstChild.childNodes;
        for (let i = displayColumns + 1; i < headerColums.length - 1; i++) {
            (footerColums[i] as HTMLTableHeaderCellElement).classList.add('d-none');
        }

    }

    private changeDisplayColumn() {
        let headerColums = this.headerTable.tHead.firstChild.childNodes;
        // hidden old columns
        for (let i = this.oldStartDisplayIndex; i < this.oldStartDisplayIndex + this.displayColumns && i < headerColums.length - 1; i++) {
            (headerColums[i] as HTMLTableHeaderCellElement).classList.add('d-none');
        }
        // display new columns
        for (let i = this.startDisplayIndex; i < this.startDisplayIndex + this.displayColumns && i < headerColums.length - 1; i++) {
            (headerColums[i] as HTMLTableHeaderCellElement).classList.remove('d-none');
        }

        let rows = this.bodyTable.tBodies[0].children as HTMLCollection;
        for (let row of rows) {

            // hidden old cells
            for (let i = this.oldStartDisplayIndex; i < this.oldStartDisplayIndex + this.displayColumns && i < headerColums.length - 1; i++) {
                (row.childNodes[i] as HTMLTableCellElement).classList.add('d-none');
            }

            // display new cells
            for  (let i = this.startDisplayIndex; i < this.startDisplayIndex + this.displayColumns && i < headerColums.length - 1; i++) {
                (row.childNodes[i] as HTMLTableCellElement).classList.remove('d-none');
            }
        }


        let footerColums = this.footerTable.tFoot.firstChild.childNodes;
        // hidden old columns
        for (let i = this.oldStartDisplayIndex; i < this.oldStartDisplayIndex + this.displayColumns && i < headerColums.length - 1; i++) {
            (footerColums[i] as HTMLTableHeaderCellElement).classList.add('d-none');
        }
        // display new columns
        for (let i = this.startDisplayIndex; i < this.startDisplayIndex + this.displayColumns && i < headerColums.length - 1; i++) {
            (footerColums[i] as HTMLTableHeaderCellElement).classList.remove('d-none');
        }
        
    }

    

    private addPrevNextButtons() {

        let self = this;
        
        let prevButton = document.createElement('button');
        prevButton.textContent = 'Prev';
        prevButton.onclick = function() {
            self.oldStartDisplayIndex = self.startDisplayIndex;
            self.startDisplayIndex = self.startDisplayIndex - self.displayColumns;
            
            if (self.startDisplayIndex === 1) {
                prevButton.disabled = true;
            }

            if (nextButton.disabled === true) {
                nextButton.disabled = false;
            }

            self.changeDisplayColumn();
        };

        let nextButton = document.createElement('button');
        nextButton.textContent = 'Next';
        nextButton.onclick = function() {
            self.oldStartDisplayIndex = self.startDisplayIndex;
            self.startDisplayIndex = self.startDisplayIndex + self.displayColumns;
            
            let headerColumnLength = self.headerTable.tHead.firstChild.childNodes.length;
            if (self.startDisplayIndex + self.displayColumns >= headerColumnLength) {
                nextButton.disabled = true;
            }

            if (prevButton.disabled === true) {
                prevButton.disabled = false;
            }

            self.changeDisplayColumn();
        };

        let headerColumns = this.headerTable.tHead.firstChild.childNodes;
        headerColumns[0].appendChild(prevButton);
        headerColumns[headerColumns.length - 1].appendChild(nextButton);
    }

}