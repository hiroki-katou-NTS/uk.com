import { component, Prop } from '@app/core/component';
import { _, Vue, moment } from '@app/provider';
import { model } from 'views/kdp/S01/shared/index.d';
import { TimeWithDay } from '@app/utils/time';

const basePath = 'at/record/stamp/smart-phone/';

const servicePath = {
    getStampResult: basePath + 'get-stamp-result-screen-c',
    regDailyResult: basePath + 'reg-daily-result'
};

@component({
    name: 'kdpS01c',
    route: '/kdp/s01/c',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    constraints: []
})


export class KdpS01CComponent extends Vue {
    @Prop({ default: () => ({}) })
    public params!: any;
    public screenData: IScreenData = {
        confirmResult: {
            employeeId: '',
            date: new Date(),
            status: false,
            permissionCheck: 0,
            permissionRelease: 0
        },
        employeeCode: '000001',
        employeeName: '日通　太郎',
        date: new Date(),
        stampAtr: '出勤',
        localtion: '',
        workTypeName: '',
        workTimeName: '',
        attendanceItem: {
            attendance: '',
            timeItems: [
                { itemId: 1, title: '就業時間', value: '' },
                { itemId: 2, title: '残業', value: '' },
                { itemId: 3, title: '休憩時間', value: '' },
                { itemId: 4, title: '日別項目', value: '' },
                { itemId: 5, title: '日別項目', value: '' }
            ]
        }
    };

    public mounted() {
        this.pgName = 'KDPS01_5';
    }


    public created() {
        let vm = this;
        vm.params.attendanceItemIds.push(28, 29, 31, 34);

        let command = {
            startDate: moment(vm.params.stampDate ? vm.params.stampDate : vm.$dt.now).format('YYYY/MM/DD'),
            endDate: moment(vm.params.stampDate ? vm.params.stampDate : vm.$dt.now).format('YYYY/MM/DD'),
            attendanceItemIds: vm.params.attendanceItemIds,
            baseDate: moment(vm.params.stampDate ? vm.params.stampDate : vm.$dt.now).format('YYYY/MM/DD')
        };

        vm.$mask('show');
        vm.$http.post('at', servicePath.getStampResult, command).then((result: any) => {
            vm.$mask('hide');
            let data: model.IDisplayConfirmStampResultScreenCDto = result.data;

            vm.screenData.confirmResult = data.confirmResult;
            if (data.stampings.length > 0) {
                let items = _(data.stampings).flatMap('stampDataOfEmployeesDto').flatMap('stampRecords').value();
                let item = _.head(_.orderBy(items, ['stampTimeWithSec'], ['desc']));

                if (item) {
                    vm.screenData.date = item.stampDatetime;
                    vm.screenData.stampAtr = item.stampArtName;
                    vm.screenData.localtion = [item.workLocationCD, item.workLocationName].join(' ');
                }

            }

            vm.screenData.workTimeName = data.workTimeName || '';
            vm.screenData.workTypeName = data.workTypeName || '';

            let goingWork = vm.getValue(data.itemValues, 31),
                leave = vm.getValue(data.itemValues, 34);

            if (!!goingWork && !!leave && !!goingWork.value && !!leave.value) {
                vm.screenData.attendanceItem.attendance = [TimeWithDay.getDayName(Number(goingWork.value)) + TimeWithDay.toString(Number(goingWork.value)), TimeWithDay.getDayName(Number(leave.value)) + TimeWithDay.toString(Number(leave.value))].join(' ～ ');
            } else {
                vm.screenData.attendanceItem.attendance = '';
            }

            vm.$auth.user.then((user) => {
                vm.screenData.employeeCode = data.empInfo ? data.empInfo.employeeCode : user.employeeCode;
                vm.screenData.employeeName = data.empInfo ? data.empInfo.pname : '';
            });


            _.remove(data.lstItemDisplayed, function (item) {
                return [28, 29, 31, 34].indexOf(item.attendanceItemId) != -1;
            });
            vm.screenData.attendanceItem.timeItems = [];

            let isNoItemHasData = !_.find(data.itemValues, (item) => item.value);

            if (!isNoItemHasData) {
                _.forEach(_.orderBy(data.lstItemDisplayed, 'attendanceItemId'), function (item) {
                    let value = vm.toValue(item, _.find(data.itemValues, ['itemId', item.attendanceItemId]));

                    if (value) {
                        vm.screenData.attendanceItem.timeItems.push({ itemId: item.attendanceItemId, title: item.attendanceName, value });
                    }
                });
            }

        }).catch((res: any) => {
            vm.$modal.error({ messageId: res.messageId, messageParams: res.parameterIds });
        });

    }

    get isHasImplementation() {
        let vm = this;
        if (!vm.screenData.confirmResult) {
            return State.SETTING_NULL;
        }

        if (vm.screenData.confirmResult.permissionCheck === ReleasedAtr.IMPLEMENT && vm.screenData.attendanceItem.timeItems.length > 0) {
            return State.IMPLEMENT;
        } else {
            return State.CAN_NOT_IMPLEMENT;
        }

    }

    private toValue(attendanceItem, item) {
        let result = '';

        if (!item || !item.value) {

            return '';
        }

        if (attendanceItem.dailyAttendanceAtr == DailyAttendanceAtr.TimeOfDay) {

            result = TimeWithDay.getDayName(Number(item.value)) + TimeWithDay.toString(Number(item.value));
        }

        if (attendanceItem.dailyAttendanceAtr == DailyAttendanceAtr.NumberOfTime) {

            result = item.value;
        }

        if (attendanceItem.dailyAttendanceAtr == DailyAttendanceAtr.Time) {

            result = TimeWithDay.toString(Number(item.value));
        }

        if (attendanceItem.dailyAttendanceAtr == DailyAttendanceAtr.AmountOfMoney) {

            result = item.value.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, '$1,');
        }

        return result;
    }


    private getValue(lstItemDisplayed, itemId) {
        return _.find(lstItemDisplayed, ['itemId', itemId]);
    }

    public regDailyResult() {
        let vm = this,
            command: model.IRegisterVerifiDailyResultCommand = {
                employeeId: vm.screenData.confirmResult.employeeId,
                confirmDetails: [{
                    ymd: vm.screenData.confirmResult.date,
                    IdentityVerificationStatus: true
                }]
            };
        vm.$mask('show');
        vm.$http.post('at', servicePath.regDailyResult, command).then((result: any) => {
            vm.$mask('hide');
            vm.$modal.info('Msg_15').then(() => {
                vm.$close();
            });
        }).catch((res: any) => {
            vm.$modal.error({ messageId: res.messageId, messageParams: res.parameterIds });
        });

    }
}

enum DailyAttendanceAtr {

    /* コード */
    Code = 0,
    /* マスタを参照する */
    ReferToMaster = 1,
    /* 回数*/
    NumberOfTime = 2,
    /* 金額*/
    AmountOfMoney = 3,
    /* 区分 */
    Classification = 4,
    /* 時間 */
    Time = 5,
    /* 時刻*/
    TimeOfDay = 6,
    /* 文字 */
    Charater = 7
}

interface IScreenData {
    employeeCode: string;
    employeeName: string;
    date: Date;
    stampAtr: string;
    localtion: string;
    workTypeName: string;
    workTimeName: string;
    attendanceItem: IAttendanceItem;
    confirmResult: model.IConfirmStatusActualResultDto;
}
interface IAttendanceItem {
    attendance: string;
    timeItems: Array<ITimeItem>;
}



interface ITimeItem {
    itemId: number;
    title: string;
    value: string;
}

enum ReleasedAtr {
    //実施できない - 解除できない
    CAN_NOT_IMPLEMENT = 0,

    //実施できる - 解除できる
    IMPLEMENT = 1
}

enum State {
    //日の実績の確認状況　＝　Null
    SETTING_NULL = 0,
    //実施できる - 解除できる
    IMPLEMENT = 1,
    //実施できない - 解除できない
    CAN_NOT_IMPLEMENT = 2
}

