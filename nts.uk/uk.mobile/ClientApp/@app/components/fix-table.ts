import { Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';

@component({
    template: `<div class="table-container" v-bind:role="roleId">
        <div class="table-header" >
            <button ref="previous" class="btn btn-secondary btn-sm" v-on:click="previous" disabled>前項</button>
            <button ref="next" class="btn btn-secondary btn-sm" v-on:click="next">次項</button>
            <table v-bind:class="tableClass">
                <tbody></tbody>
            </table>
        </div>
        <div class="table-body" style="margin-bottom: -1px; margin-top: -1px">
            <table v-bind:class="tableClass"
                v-on:touchstart="handleTouchStart"
                v-on:touchend="handleTouchEnd">
                <slot/>
            </table>
        </div>
        <div class="table-footer">
            <table v-bind:class="tableClass">
                <tbody></tbody>
            </table>
        </div>

    </div>`
})
export class FixTableComponent extends Vue {

    @Prop({ default: 'table table-bordered table-sm m-0' })
    public tableClass: string;

    @Prop({ default: 4 })
    public displayColumns: number;

    @Prop({ default: 5 })
    public rowNumber: number;

    private roleId: string = Date.now().toString();

    public startDisplayIndex: number = 1;
    public oldStartDisplayIndex: number = 1;

    private headerTable: HTMLTableElement = null;
    private bodyTable: HTMLTableElement = null;
    private footerTable: HTMLTableElement = null;

    private xDown: number = null;
    private yDown: number = null;

    public get flexibleColumns() {
        return this.headerTable.tHead.firstChild.childNodes.length - 2;
    }

    private noChangeFooter = true;

    public mounted() {
        this.bodyTable = this.$el.querySelector('.table-container .table-body table') as HTMLTableElement;
        this.headerTable = this.$el.querySelector('.table-container .table-header table') as HTMLTableElement;
        this.footerTable = this.$el.querySelector('.table-container .table-footer table') as HTMLTableElement;

        this.addBlankColums();

        this.moveTheadAndTfoot();

        this.hiddenColumns();

        this.addPrevNextButtons();

        this.setStyle();
    }

    private addBlankColums() {
        let numberOfFlexibleColumn = this.bodyTable.tHead.firstChild.childNodes.length - 2;
        let numberOfExcessColumn = numberOfFlexibleColumn % this.displayColumns;

        if (numberOfExcessColumn === 0) {
            return;
        }

        let numberOfBlankColumn = this.displayColumns - numberOfExcessColumn;
        let header = (this.bodyTable.tHead.children[0] as HTMLTableRowElement);
        for (let i = 0; i < numberOfBlankColumn; i++) {
            header.insertBefore(document.createElement('TH'), header.lastChild);
        }

        let rows = this.bodyTable.tBodies[0].children as HTMLCollection;
        Array.from(rows).forEach((row) => {
            for (let i = 0; i < numberOfBlankColumn; i++) {
                row.insertBefore(document.createElement('TD'), row.lastChild);
            }
        });

        if ( this.bodyTable.tFoot.firstChild.childNodes.length !== 1) {
            let footer = this.bodyTable.tFoot.children[0] as HTMLTableRowElement;
            for (let i = 0; i < numberOfBlankColumn; i++) {
                footer.insertBefore(document.createElement('TD'), footer.lastChild);
            }
        }
    }

    private moveTheadAndTfoot() {
        this.headerTable.tHead = this.bodyTable.tHead;
        this.footerTable.tFoot = this.bodyTable.tFoot;

        this.noChangeFooter = this.footerTable.tFoot.firstChild.childNodes.length === 1;
    }

    private setStyle() {
        this.$nextTick(() => {

            this.generateStyle();

            let rows = this.bodyTable.tBodies[0].children;
            if (rows.length === 0) {
                return;
            }
            this.setStyleOfTableBody(rows);
        });
    }

    private generateStyle() {
        let theFirstHeader = this.headerTable.querySelector('thead>tr>th:first-child') as HTMLTableCellElement;
        let theLastHeader = this.headerTable.querySelector('thead>tr>th:last-child') as HTMLTableCellElement;

        let firstHeaderWidth = theFirstHeader.getAttribute('c-width') as unknown as number;
        let lastHeaderWidth = theLastHeader.getAttribute('c-width') as unknown as number;

        if (firstHeaderWidth === null || lastHeaderWidth === null) {
            console.warn(`Fix Table Error: the first column or the last column have not 'c-column' attribute!`);
            firstHeaderWidth = theFirstHeader.offsetWidth;
            lastHeaderWidth = theLastHeader.offsetWidth;
        }

        let middleHeaderWidth = Math.floor(((this.$el as HTMLElement).offsetWidth - firstHeaderWidth - lastHeaderWidth) / this.displayColumns - 1);

        let numberOfCols = this.headerTable.tHead.firstChild.childNodes.length;

        let css: string = '';

        css += `
            [role="${this.roleId}"] tr td:nth-child(${1}),\n
            [role="${this.roleId}"] tr th:nth-child(${1}) {\n
                width: ${firstHeaderWidth}px;\n
                min-width: ${firstHeaderWidth}px;\n
                max-width: ${firstHeaderWidth}px;\n
            }\n
            `;

        for (let i = 2; i < numberOfCols; i++) {
            css += `
            [role="${this.roleId}"] tr td:nth-child(${i}),\n
            [role="${this.roleId}"] tr th:nth-child(${i}) {\n
                width: ${middleHeaderWidth}px;\n
                min-width: ${middleHeaderWidth}px;\n
                max-width: ${middleHeaderWidth}px;\n
            }\n
        `;
        }

        css += `
            [role="${this.roleId}"] tr td:nth-child(${numberOfCols}),\n
            [role="${this.roleId}"] tr th:nth-child(${numberOfCols}) {\n
                width: ${lastHeaderWidth}px;\n
                min-width: ${lastHeaderWidth}px;\n
                max-width: ${lastHeaderWidth}px;\n
            }\n
        `;
        let styleTag = document.createElement('style');
        styleTag.setAttribute('role', this.roleId);
        styleTag.setAttribute('type', 'text/css');
        styleTag.innerHTML = css;

        let head = document.head || document.getElementsByTagName('head')[0];
        head.appendChild(styleTag);
    }

    private setStyleOfTableBody(rows: any) {
        let height: number = (rows[0] as HTMLTableRowElement).offsetHeight;

        let styleTag = document.querySelector(`style[role="${this.roleId}"]`) as HTMLStyleElement;
        let css = `.table-container .table-body {
            height: ${height * this.rowNumber - ((this.rowNumber - 1) / 3)}px;
            overflow-y: scroll;
        }`;
        styleTag.innerHTML += css;
    }

    private hiddenColumns() {
        let displayColumns = this.displayColumns;

        let headerColums = this.headerTable.tHead.firstChild.childNodes;
        for (let i = displayColumns + 1; i < headerColums.length - 1; i++) {
            (headerColums[i] as HTMLTableHeaderCellElement).classList.add('d-none');
        }

        let rows = this.bodyTable.tBodies[0].children as HTMLCollection;
        Array.from(rows).forEach((row) => {
            for (let i = displayColumns + 1; i < headerColums.length - 1; i++) {
                (row.childNodes[i] as HTMLTableCellElement).classList.add('d-none');
            }
        });

        if ( this.noChangeFooter) {
            return;
        }

        let footerColums = this.footerTable.tFoot.firstChild.childNodes;
        for (let i = displayColumns + 1; i < headerColums.length - 1; i++) {
            (footerColums[i] as HTMLTableHeaderCellElement).classList.add('d-none');
        }

    }

    private changeDisplayColumn() {
        let headerColums = this.headerTable.tHead.firstChild.childNodes;
        let footerColums = this.footerTable.tFoot.firstChild.childNodes;

        let maxHiddenColIndex = this.oldStartDisplayIndex + this.displayColumns > this.flexibleColumns ? 
                                this.flexibleColumns : 
                                this.oldStartDisplayIndex + this.displayColumns - 1;

        let maxDisplayColIndex = this.startDisplayIndex + this.displayColumns > this.flexibleColumns ?
                                this.flexibleColumns :
                                this.startDisplayIndex + this.displayColumns - 1;

        // hidden old header columns
        for (let i = this.oldStartDisplayIndex; i <= maxHiddenColIndex; i++) {
            (headerColums[i] as HTMLTableHeaderCellElement).classList.add('d-none');
        }
        // display new headercolumns
        for (let i = this.startDisplayIndex; i <= maxDisplayColIndex; i++) {
            (headerColums[i] as HTMLTableHeaderCellElement).classList.remove('d-none');
        }

        let rows = this.bodyTable.tBodies[0].children as HTMLCollection;
        Array.from(rows).forEach((row) => {
            // hidden old cells
            for (let i = this.oldStartDisplayIndex; i <= maxHiddenColIndex; i++) {
                (row.childNodes[i] as HTMLTableCellElement).classList.add('d-none');
            }

            // display new cells
            for (let i = this.startDisplayIndex; i <= maxDisplayColIndex; i++) {
                (row.childNodes[i] as HTMLTableCellElement).classList.remove('d-none');
            }
        });

        if ( this.noChangeFooter ) {
            return;
        }

        // hidden old footer columns
        for (let i = this.oldStartDisplayIndex; i <= maxHiddenColIndex; i++) {
            (footerColums[i] as HTMLTableHeaderCellElement).classList.add('d-none');
        }
        // display new footer columns
        for (let i = this.startDisplayIndex; i <= maxDisplayColIndex; i++) {
            (footerColums[i] as HTMLTableHeaderCellElement).classList.remove('d-none');
        }
    }

    private addPrevNextButtons() {
        let headerColumns = this.headerTable.tHead.children[0].children;
        headerColumns[0].appendChild(this.$refs.previous as Node);
        headerColumns[headerColumns.length - 1].appendChild(this.$refs.next as Node);
    }

    public previous() {
        let self = this;

        if (this.startDisplayIndex === 1) {
            return;
        }

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
        if (self.startDisplayIndex + self.displayColumns > self.flexibleColumns) {
            return;
        }

        self.oldStartDisplayIndex = self.startDisplayIndex;
        self.startDisplayIndex = self.startDisplayIndex + self.displayColumns;

        if (self.startDisplayIndex + self.displayColumns > self.flexibleColumns) {
            (self.$refs.next as HTMLButtonElement).disabled = true;
        }

        let prevButton = self.$refs.previous as HTMLButtonElement;
        if (prevButton.disabled === true) {
            prevButton.disabled = false;
        }

        self.changeDisplayColumn();
    }

    public handleTouchStart(evt) {
        this.xDown = evt.touches[0].clientX;
        this.yDown = evt.touches[0].clientY;
    }

    public handleTouchEnd(evt: TouchEvent) {

        if (!this.xDown || !this.yDown) {
            return;
        }

        let xDiff = this.xDown - evt.changedTouches[0].clientX;
        let yDiff = this.yDown - evt.changedTouches[0].clientY;

        if (Math.abs(xDiff) > Math.abs(yDiff)) {
            if (xDiff > 0) {
                /* left swipe */
                this.next();
            } else {
                /* right swipe */
                this.previous();
            }
        }

        this.xDown = null;
        this.yDown = null;
    }

    public destroyed() {
        let styleTag = document.querySelector(`style[role="${this.roleId}"]`) as HTMLStyleElement;
        styleTag.remove();
    }
}