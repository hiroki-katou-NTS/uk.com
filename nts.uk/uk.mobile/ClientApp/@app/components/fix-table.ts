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
    
    public mounted() {
        console.log('mounted hook, move table');
        let slotTable = this.$el.querySelector('.table-container .table-body table') as HTMLTableElement;

        let thead = slotTable.tHead;
        let headerTable = this.$el.querySelector('.table-container .table-header table') as HTMLTableElement;
        headerTable.tHead = thead;

        let tfoot = slotTable.tFoot;
        let footerTable = this.$el.querySelector('.table-container .table-footer table') as HTMLTableElement;
        footerTable.tFoot = tfoot;

    }

}