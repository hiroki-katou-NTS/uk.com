import { component, Prop } from '@app/core/component';
import { _, Vue, moment } from '@app/provider';
import { model } from 'views/kdp/S01/shared/index.d';

const basePath = 'at/record/stamp/smart-phone/';

const servicePath = {
    displayHistory: basePath + 'display-history',
};

@component({
    name: 'kdpS01s',
    route: '/kdp/s01/s',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    constraints: []
})


export class KdpS01SComponent extends Vue {
    @Prop({ default: () => ({}) })
    public params!: any;
    public selectedValue = 1;
    public yearMonth = moment().format('YYYYMM');
    public dropdownList = [{
        code: '1',
        text: 'KDPS01_56'
    }, {
        code: '2',
        text: 'KDPS01_57'
    }, {
        code: '3',
        text: 'KDPS01_58'
    }, {
        code: '4',
        text: 'KDPS01_59'
    }];

    public screenData: any = {
        items: [
            {
                id: 1,
                date: '',
                stampType: '',
                stamp: {
                    relieve: { stampMeans: '' },
                    buttonValueType: -1
                }
            },
            {
                id: 2,
                date: '',
                stampType: '',
                stamp: {
                    relieve: { stampMeans: '' },
                    buttonValueType: -1
                }
            },
            {
                id: 3,
                date: '',
                stampType: '',
                stamp: {
                    relieve: { stampMeans: '' },
                    buttonValueType: -1
                }
            },
            {
                id: 4,
                date: '',
                stampType: '',
                stamp: {
                    relieve: { stampMeans: '' },
                    buttonValueType: -1
                }
            },
            {
                id: 5,
                date: '',
                stampType: '',
                stamp: {
                    relieve: { stampMeans: '' },
                    buttonValueType: -1
                }
            }
        ]
    };

    public loadData() {
        let vm = this,
            baseDate = vm.$dt.yearmonth(Number(vm.yearMonth), 'YYYY/MM/DD');

        let query = {
            startDate: moment(baseDate).startOf('month').format('YYYY/MM/DD'),
            endDate: moment(baseDate).endOf('month').format('YYYY/MM/DD')
        };

        vm.$mask('show');
        vm.$http.post('at', servicePath.displayHistory, query).then((result: any) => {
            vm.$mask('hide');
            let items = _(result.data).flatMap('listStampInfoDisp').value();

            vm.screenData.items = _.orderBy(items, ['stampDatetime'], ['asc']);
        });

    }

    public created() {
        let vm = this;

        vm.loadData();
    }

    get getItems() {
        let vm = this;


        return _.filter(vm.screenData.items, function (item) {

            if (vm.selectedValue == 1) {

                return vm.screenData.items;
            }

            if (!_.has(item, 'stamp.buttonValueType')) {

                return false;
            }
            if (vm.selectedValue == 2) {

                return item.stamp.buttonValueType === ButtonType.GOING_TO_WORK || item.stamp.buttonValueType === ButtonType.WORKING_OUT;
            }
            if (vm.selectedValue == 3) {

                return item.stamp.buttonValueType === ButtonType.GO_OUT || item.stamp.buttonValueType === ButtonType.RETURN;
            }

            if (vm.selectedValue == 4) {

                return item.stamp.buttonValueType === ButtonType.RESERVATION_SYSTEM;
            }

        });

    }


    public getSymbol(item) {

        if (!_.has(item, 'stamp.relieve.stampMeans')) {
            return '';
        }
        let value = item.stamp.relieve.stampMeans;

        const stampTypes = [
            { text: 'KDP002_120', name: '氏名選択' },
            { text: 'KDP002_120', name: '指認証打刻' },
            { text: 'KDP002_120', name: 'ICカード打刻' },
            { text: 'KDP002_120', name: '個人打刻' },
            { text: 'KDP002_120', name: 'ポータル打刻' },
            { text: 'KDP002_121', name: 'スマホ打刻' },
            { text: 'KDP002_122', name: 'タイムレコーダー打刻' }
        ];

        let data = _.find(stampTypes, ['name', value]);

        if (data) {
            return data.text;
        } else {
            return '';
        }

    }

    public getTextAlign(item) {

        if (!_.has(item, 'stamp.buttonValueType')) {
            return '';
        }
        let value = item.stamp.buttonValueType;
        if (ButtonType.GOING_TO_WORK == value || ButtonType.RESERVATION_SYSTEM == value) {

            return 'left';

        }

        if (ButtonType.WORKING_OUT == value) {

            return 'right';

        }

        return 'center';
    }

    public getTextColor(item) {

        const daysColor = [
            { day: 0, color: '#FF0000' },
            { day: 6, color: '#0000FF' }
        ];

        let day = moment.utc(item.stampDatetime).day(),

            dayColor = _.find(daysColor, ['day', day]);

        return dayColor ? dayColor.color : '#000000';

    }


    public login() {


    }

    public mounted() {
    }
}
interface Iitem {
    id: number;
    date: string;
    symbol: string;
    time: string;
    stampType: string;
}

enum ButtonType {
    // 系

    GOING_TO_WORK = 1,
    // 系

    WORKING_OUT = 2,
    // "外出系"

    GO_OUT = 3,
    // 戻り系

    RETURN = 4,
    // 予約系

    RESERVATION_SYSTEM = 5
}