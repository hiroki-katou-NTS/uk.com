import { _, Vue } from '@app/provider';
import { component } from '@app/core/component';
import { storage } from '@app/utils';
import { KdwS03DComponent } from 'views/kdw/s03/d';

@component({
    name: 'kdws03c',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    components: {
        'kdws03d': KdwS03DComponent,
    }
})
export class KdwS03CComponent extends Vue {
    public title: string = 'KdwS03C';
    public dailyCorrectionState: any;
    public displayData: any;
    public createDone: boolean = false;

    public created() {
        this.dailyCorrectionState = storage.local.getItem('dailyCorrectionState');
        if (this.dailyCorrectionState.displayFormat == '1') {
            this.displayData = _.filter(this.dailyCorrectionState.cellDataLst, (x) => x.ERAL.includes('ER') || x.ERAL.includes('AL'));
        }
        this.createDone = true;
        this.$mask('hide');
    }

    public mounted() {
        if (!this.createDone) {
            this.$mask('show', { message: true });
        }
        if (this.displayData.length == 0) {
            this.$modal.error({ messageId: 'Msg_1553' }).then(() => {
                this.$close();
            });
        }
    }

    public openErrorList(employeeId: any) {
        this.$modal('kdws03d', {
            employeeID: employeeId,
            employeeName: (_.find(this.dailyCorrectionState.lstEmployee, (x) => x.id == employeeId)).businessName,
            startDate: this.dailyCorrectionState.dateRange.startDate, 
            endDate: this.dailyCorrectionState.dateRange.endDate
        }, { type: 'dropback' })
            .then((v) => {
                if (v != 'NotCloseMenu') {
                    this.$close();
                }
            });

    }
}