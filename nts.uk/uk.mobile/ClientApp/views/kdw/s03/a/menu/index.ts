import { Vue, _ } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { storage } from '@app/utils';
import { KdwS03DComponent } from 'views/kdw/s03/d';
import { KdwS03FComponent } from 'views/kdw/s03/f';
import { KdwS03GComponent } from 'views/kdw/s03/g';

@component({
    name: 'kdws03amenu',
    style: require('../style.scss'),
    template: require('./index.vue'),
    resource: require('../resources.json'),
    components: {
        'kdws03d': KdwS03DComponent,
        'kdws03f': KdwS03FComponent,
        'kdws03g': KdwS03GComponent,
    }
})
export class KdwS03AMenuComponent extends Vue {
    @Prop({ default: () => ({}) })
    public params: MenuParam;
    public dailyCorrectionState: any = null;

    public title: string = 'KdwS03AMenu';

    public created() {
        this.dailyCorrectionState = _.cloneWith(storage.session.getItem('dailyCorrectionState'));
    }

    public openErrorList() {
        this.createMask();
        this.$modal('kdws03d', {
            employeeID: this.dailyCorrectionState.selectedEmployee,
            employeeName: (_.find(this.dailyCorrectionState.lstEmployee, (x) => x.id == this.dailyCorrectionState.selectedEmployee)).businessName,
            startDate: this.dailyCorrectionState.dateRange.startDate,
            endDate: this.dailyCorrectionState.dateRange.endDate
        }).then((v: any) => {
            if (v != undefined && v.openB) {
                this.$close(v);
            }
        });

    }
    public openKdws03f(param: number) {
        this.createMask();
        this.$modal('kdws03f');
    }

    public openKdws03g(param: boolean) {
        this.createMask();
        this.$modal('kdws03g', { 'remainDisplay': param });
    }

    public processConfirmAll(processFlag: string) {
        this.createMask();
        let dataCheckSign: Array<any> = [];
        let dateRange: any = this.dailyCorrectionState.dateRange;
        let updateItemExist = false;
        if (this.$dt.fromString(dateRange.startDate) <= new Date()) {
            _.forEach(this.dailyCorrectionState.cellDataLst, (x) => {
                if (!x.confirmDisable) {
                    if (processFlag == 'confirm') {
                        if (x.sign == false) {
                            updateItemExist = true;
                        }
                    } else {
                        if (x.sign == true) {
                            updateItemExist = true;
                        }
                    }
                    dataCheckSign.push({
                        rowId: x.id,
                        itemId: 'sign',
                        value: processFlag == 'confirm' ? true : false,
                        employeeId: this.dailyCorrectionState.selectedEmployee,
                        date: this.$dt.fromString(x.dateDetail),
                        flagRemoveAll: false
                    });
                }
            });

            if (updateItemExist == false) {
                if (processFlag == 'confirm') {
                    this.$modal.error({ messageId: 'Msg_1545' }).then((v: any) => {
                        this.$mask('hide');
                    });
                } else {
                    this.$modal.error({ messageId: 'Msg_1546' }).then((v: any) => {
                        this.$mask('hide');
                    });
                }
                
                return;
            }

            this.$http.post('at', servicePath.confirmAll, dataCheckSign).then((result: { data: any }) => {
                this.$mask('show', 0.5);
                if (processFlag == 'confirm') {
                    this.$modal.info({ messageId: 'Msg_15' }).then((v: any) => {
                        this.$close({reload: true});
                    });
                } else {
                    this.$modal.info({ messageId: 'Msg_1445' }).then((v: any) => {
                        this.$close({reload: true});
                    });
                }
            }).catch((res: any) => {
                this.$modal.error({ messageId: res.messageId }).then((v: any) => {
                    this.$mask('hide');
                });
            });

        } else {
            if (processFlag == 'confirm') {
                this.$modal.error({ messageId: 'Msg_1545' }).then((v: any) => {
                    this.$mask('hide');
                });
            } else {
                this.$modal.error({ messageId: 'Msg_1546' }).then((v: any) => {
                    this.$mask('hide');
                });
            }

            return;
        }
    }

    public createMask() {
        this.$mask('show', 0);
        setTimeout(() => {
            this.$mask('hide');
        }, 500);
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