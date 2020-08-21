import { component, Prop, Model } from '@app/core/component';
import { model } from 'views/kdp/S01/shared/index.d';
import { _, Vue, moment } from '@app/provider';

const basePath = 'at/record/stamp/smart-phone/';

const servicePath = {
    getStampResult: basePath + 'get-stamp-result-screen-b'
};

@component({
    name: 'kdpS01b',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    constraints: []
})


export class KdpS01BComponent extends Vue {
    @Prop({ default: () => ({}) })
    public params!: model.IScreenBParam;

    public screenData: IScreenData = {
        employeeCode: '000001',
        employeeName: '日通　太郎',
        date: new Date(),
        stampAtr: '出勤',
        stampCard: 'A1234567890',
        localtion: '000　本社'
    };

    private interval: any;


    public created() {
        let vm = this,
            command = {
                startDate: moment(vm.$dt.now).format('YYYY/MM/DD'),
                endDate: moment(vm.$dt.now).format('YYYY/MM/DD')
            };

        vm.$mask('show');
        vm.$http.post('at', servicePath.getStampResult, command).then((result: any) => {
            vm.$mask('hide');

            let data = result.data as model.IScreenBResult;

            if (data.empDatas) {

                let items = _(data.empDatas).flatMap('listStampInfoDisp').value();
                let item = _.head(_.orderBy(items, ['stampStringDatetime'], ['desc']));
                if (item) {
                    vm.screenData.date = item.stampStringDatetime;
                    vm.screenData.stampAtr = item.stampAtr;
                    vm.screenData.stampCard = _.get(item, 'stamp.refActualResult.cardNumberSupport', null);

                }

            } else {
                vm.$modal.error('Not Found Stamp Data');
            }

            vm.screenData.localtion = [data.workLocationCd, data.workLocationName].join(' ');
            vm.$auth.user.then((user) => {
                vm.screenData.employeeCode = user.employeeCode;
                vm.screenData.employeeName = data.empInfo.pname;
            });



        }).catch((res: any) => {
            vm.$modal.error({ messageId: res.messageId, messageParams: res.parameterIds });
        });

        vm.InitCountTime();

    }

    private InitCountTime() {
        let vm = this;
        if (vm.params.resultDisplayTime > 0) {
            vm.interval = setInterval(() => {
                vm.params.resultDisplayTime--;
                if (vm.params.resultDisplayTime === 0) { vm.$close(); }
            }, 1000);
        }

    }
}

interface IScreenData {
    employeeCode: string;
    employeeName: string;
    date: Date;
    stampAtr: string;
    stampCard: string;
    localtion: string;
}



