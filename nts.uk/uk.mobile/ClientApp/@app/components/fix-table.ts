import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    template: `<div class="table-container">
        <div class="table-header">
            <button ref="previous" class="btn btn-secondary" v-on:click="previous">前項</button>
            <button ref="next" class="btn btn-secondary" v-on:click="next">事項</button>
            <table class="table table-bordered m-0">
                <tbody></tbody>
            </table>
        </div>
        <div class="table-body">
            <table class="table table-bordered m-0">
                <slot/>
            </table>
        </div>
        <div class="table-footer">
            <table class="table table-bordered m-0">
                <tbody></tbody>
            </table>
        </div>
    </div>`
})
export class FixTableComponent extends Vue {

    @Prop({ default: 4 })
    public displayColumns: number;

    @Prop({ default: 6}) 
    public rowNumber: number;

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

        this.hiddenColumns();

        this.addPrevNextButtons();

        this.setStyleOfTableBody();
    }

    private moveTheadAndTfoot() {
        this.headerTable.tHead = this.bodyTable.tHead;
        this.footerTable.tFoot = this.bodyTable.tFoot;
    }

    private setStyleOfTableBody() {
        this.$nextTick(() => {
            let height: number = (this.bodyTable.tBodies[0].firstChild.firstChild as HTMLTableCellElement).clientHeight;

            let tableBodyDiv = this.$el.querySelector('.table-container .table-body') as HTMLDivElement;
            tableBodyDiv.style.height = `${height * this.rowNumber}px` ;
            tableBodyDiv.style.overflowY = 'scroll';
        });
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
        let headerColumns = this.headerTable.tHead.firstChild.childNodes;
        headerColumns[0].appendChild(this.$refs.previous as Node);
        headerColumns[headerColumns.length - 1].appendChild(this.$refs.next as Node);
    }

    public previous() {
        let self = this;

        self.oldStartDisplayIndex = self.startDisplayIndex;
        self.startDisplayIndex = self.startDisplayIndex - self.displayColumns;
        
        if (self.startDisplayIndex === 1) {
            (this.$refs.previous as HTMLButtonElement).disabled = true;
        }

        let nextButton = this.$refs.next as HTMLButtonElement;
        if (nextButton.disabled === true) {
            nextButton.disabled = false;
        }

        self.changeDisplayColumn();
    }
    
    public next() {
        let self = this;
        self.oldStartDisplayIndex = self.startDisplayIndex;
        self.startDisplayIndex = self.startDisplayIndex + self.displayColumns;
        
        let headerColumnLength = self.headerTable.tHead.firstChild.childNodes.length;
        if (self.startDisplayIndex + self.displayColumns >= headerColumnLength) {
            (self.$refs.next as HTMLButtonElement).disabled = true;
        }

        let prevButton = self.$refs.previous as HTMLButtonElement;
        if (prevButton.disabled === true) {
            prevButton.disabled = false;
        }

        self.changeDisplayColumn();
    }
    
    public updated() {
        let columns = this.bodyTable.tBodies[0].children[0].children;
        let maps = Array.from(columns).map( 
            (c) => c.clientWidth
        );
        
        let headerColumns = this.headerTable.tHead.children[0].children;
        for ( let i = 0; i < headerColumns.length; i++) {
            (headerColumns[i] as HTMLTableCellElement).style.width = `${maps[i]}px`;
        }

    }

}