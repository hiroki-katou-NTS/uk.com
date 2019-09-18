import { Vue, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { storage } from '@app/utils';
import { KdwS03CComponent } from 'views/kdw/s03/c';
import { KdwS03FComponent } from 'views/kdw/s03/f';
import { KdwS03GComponent } from 'views/kdw/s03/g';

@component({
    name: 'kdws03amenu',
    style: require('../style.scss'),
    template: require('./index.vue'),
    resource: require('../resources.json'),
    components: {
        'kdws03c': KdwS03CComponent,
        'kdws03f': KdwS03FComponent,
        'kdws03g': KdwS03GComponent,
    }
})
export class KdwS03AMenuComponent extends Vue {
    @Prop({ default: () => ({}) })
    public params: MenuParam;

    public title: string = 'KdwS03AMenu';

    public openErrorList() {
        if (this.params.displayFormat == '1') {
            this.$modal('kdws03c', {}, { type: 'dropback' })
                .then((v) => {
                    if (v != 'NotCloseMenu') {
                        this.$close();
                    }
                });
        } else {
            this.$modal('kdws03d', {}, { type: 'dropback', title: 'KDWS03_6' })
                .then((v) => {

                });
        }

    }
    public openKdws03f(param: number) {
        this.$modal('kdws03f', {}, { type: 'dropback' });
    }
    public openKdws03g(param: number) {
        console.log(param);
        this.$modal('kdws03g', { 'remainOrtime36': param }, { type: 'dropback' });
    }

    public processConfirmAll(processFlag: string) {
        let dailyCorrectionState: any = _.cloneWith(storage.local.getItem('dailyCorrectionState'));
        let dataCheckSign: Array<any> = [];
        let dateRange: any = dailyCorrectionState.dateRange;
        if (this.$dt.fromString(dateRange.startDate) <= new Date()) {
            _.forEach(dailyCorrectionState.cellDataLst, (x) => {
                if (!x.confirmDisable) {
                    dataCheckSign.push({
                        rowId: x.id,
                        itemId: 'sign',
                        value: processFlag == 'confirm' ? true : false,
                        employeeId: dailyCorrectionState.selectedEmployee,
                        date: this.$dt.fromString(x.dateDetail),
                        flagRemoveAll: false
                    });
                }
            });

            this.$http.post('at', servicePath.confirmAll, dataCheckSign).then((result: { data: any }) => { 
                if (processFlag == 'confirm') {
                    this.$modal.info({ messageId: 'Msg_15' });
                } else {
                    this.$modal.info({ messageId: 'Msg_1445' });
                }
            });

        } else {
            if (processFlag == 'confirm') {
                this.$modal.error({ messageId: 'Msg_1545' });
            } else {
                this.$modal.error({ messageId: 'Msg_1546' });
            }
            
        }
    }
}
interface MenuParam {
    displayFormat: string;
    errorReferButtonDis: boolean;
    restReferButtonDis: boolean;
    monthActualReferButtonDis: boolean;
    timeExcessReferButtonDis: boolean;
    allConfirmButtonDis: boolean;
}
const servicePath = {
    confirmAll: 'screen/at/correctionofdailyperformance/confirmAll'
};