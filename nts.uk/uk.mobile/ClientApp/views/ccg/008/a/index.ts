import { Vue, _, moment } from '@app/provider';
import { component, Watch } from '@app/core/component';
import { BaseDto, ServerAlertDto, DisplayNotifiDto, AgreementTimeDetail, TimeStatus, DisplayItemType, WidgetDisplayItemType, TimeDto, DateInfoDto, OvertimeLaborInforDto } from './ccgs08Model';
import { Ccg008BComponent } from '../b';
import { TableComponent } from './components/table/';

const servicePath = {
    getAllStartUpData: 'screen/at/mobile/ccgs08/',
    getDisplayNotify: 'screen/at/mobile/ccgs08/displaynotif',
    getOverTimeList: 'screen/at/mobile/ccgs08/overtime/',
    getKtg029:'screen/at/mobile/ccgs08/ktg029',
    getTopAlert: 'ctx/sys/gateway/stopsetting/find/',
    getVisibleConfig:'screen/at/mobile/ccgs08/visibleConfig'
};

enum SystemOperationMode {
    RUNNING = 0,
    IN_PROGESS = 1,
    STOP = 2
}

enum User {
    ADMIN = 1,
    COMPANY = 0
}

@component({
    name: 'ccg008a',
    route: '/ccg/008/a',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    components:{
        'ccgs08b': Ccg008BComponent,
        'nts-ccgs008-table': TableComponent
    }
})
export class Ccg008AComponent extends Vue {
    
    public title: string = 'Top Page';
    public labelConstraint = { required: true, min: 100, max: 1000 };
    public serverAlert: ServerAlertDto = new ServerAlertDto();
    public displayNotifisVissible = false;
    public displayNotifis: Array<BaseDto> = [];
    public overtime: AgreementTimeDetail = new AgreementTimeDetail();
    public timeStatus: TimeStatus = new TimeStatus();
    
    public model = {
        date: moment(new Date()).format('YYYYMM')
    };

    public promise: boolean = false;
    
    @Watch('model.date')
    public getOverTimeList(val, oldVal) {
        let self = this;
        self.loadOverTimeInfo();
    }

    public openModal() {
        this.$modal('ccgs08b', {})
        .then((v: any) => {
           // TODO: implement later
        });
    }

    public created() {
        let self = this;
        Promise.all([
            self.$http.post(servicePath.getTopAlert + User.COMPANY),
            self.$http.post('at', servicePath.getVisibleConfig)
        ]).then( (data: any) => {
            self.checkSystemStopSetting(data[0].data);
            let visibleConfig = data[1].data;
            self.overtime.visible = visibleConfig.overtimeHoursDto ? visibleConfig.overtimeHoursDto.visible : false;
            self.timeStatus.visible = visibleConfig.ktg029 ? visibleConfig.ktg029.visible : false;
            self.displayNotifisVissible = visibleConfig.displayNotifiDto ? visibleConfig.displayNotifiDto.visible : false;
        }).then( () => {
            self.loadData();
        });
    }

    public loadData() {
        let self = this;
        // display notif
        self.$http.post('at', servicePath.getDisplayNotify).then((res: any) => {
            let data = res.data;
            console.log(data);
            if (data && data.visible) {
                self.checkDisplayNotifi(data);
            }
        }).catch((err: any) => {

        });
        // overtime
        self.loadOverTimeInfo();
        // ktg029
        if (self.timeStatus.visible) {
            self.timeStatus.tableConfigs.loading = true;
            self.$http.post('at', servicePath.getKtg029).then((res: any) => {
                let data = res.data;
                if (data && data.data && data.visible) {
                    let displayItems = convertToDisplayItem(data.data);
                    self.timeStatus.visible = displayItems && displayItems.length > 0;
                    self.timeStatus.tableConfigs.items = displayItems;
                }
                self.timeStatus.tableConfigs.loading = false;
            }).catch((err: any) => {
                self.timeStatus.tableConfigs.noDataMessage = err.errorMessage;
            });
        }
    }

    private loadOverTimeInfo() {
        let self = this;
        if (self.overtime.visible) {
            self.overtime.tableConfigs.loading = true;
            self.$http.post('at', servicePath.getOverTimeList + self.model.date).then((res: any) => {
                let data = res.data;
                if (data && data.data && data.visible) {
                    let ot = data.data;
                    if (ot.overtimeHours && ot.overtimeHours.overtimeLaborInfor) {
                        self.overtime.tableConfigs.items = convertToOverTimeItem(ot.overtimeHours.overtimeLaborInfor);
                    }
                }
                self.overtime.tableConfigs.loading = false;
            }).catch((err: any) => {
                self.overtime.tableConfigs.loading = false;
                self.overtime.tableConfigs.noDataMessage = err.errorMessage;
            });
        }
    }

    public checkSystemStopSetting(setting: ServerAlertDto) { 
        let self = this; 
        if (setting.company) {
            setting.company.visible = SystemOperationMode.IN_PROGESS === setting.company.systemStatus;    
        }
        if (setting.system) {
            setting.system.visible = SystemOperationMode.IN_PROGESS === setting.system.systemStatus;
        }
        setting.visible = (setting.company && setting.company.visible) || (setting.system && setting.system.visible);

        self.serverAlert = setting;
    }

    public checkDisplayNotifi(data: DisplayNotifiDto) {
        let self = this; 
        if (!data.visible) {
            self.displayNotifis = [];    
        } else {
            self.displayNotifis = [
                {title: 'CCGS08_11', visible: data.ktg002},
                {title: 'CCGS08_14', visible: data.checkDailyErrorA2_2 }
            ];
            self.displayNotifisVissible = data.ktg002 || data.checkDailyErrorA2_2;
        }
    }

}

function convertToDisplayItem(item: WidgetDisplayItemType): Array<DisplayItemType> {
    let results = [];

    // yearlyHoliday
    let yearlyHld = item.yearlyHoliday;
    if (yearlyHld && !yearlyHld.calculationMethod) {
        results.push({
            name:'KTG029_23', 
            value: yearlyHld.nextTimeInfo.day, 
            prefix: 'KTG029_60',
        }); 
    }
    if (item.reservedYearsRemainNo) {
        results.push({name:'積立年休残数', value: item.reservedYearsRemainNo.before, prefix: 'KTG029_60'});
    }
    //setRemainAlternationNoDay
    if (item.remainAlternationNoDay || item.remainAlternationNoDay === 0) {
        results.push({name:'代休残数', value: item.remainAlternationNoDay, prefix: 'KTG029_60'});
    }
  
    if (item.remainsLeft || item.remainsLeft === 0) {
        results.push({name:'振休残数', value: item.remainsLeft, prefix: 'KTG029_60'});
    }
    
    // sphdramainNo
    if (item.sphdramainNo && item.sphdramainNo.length > 0) {
        results.push({name:'KTG029_31', value:''});
        _.forEach(item.sphdramainNo, function(sphd) {
            results.push({name: sphd.name, value: sphd.before, prefix: 'KTG029_60', sub: true});
        });
    }
    
    return results;
}

function convertToOverTimeItem(overtimes: Array<OvertimeLaborInforDto>): Array<any> {
    let otDatas = [];
    if (overtimes && overtimes.length > 0) {
        let currentOTInfoCfm = overtimes[0].confirmed;
        let status = currentOTInfoCfm.status;
        let icon = '';
        if (status === 4) {
            icon = '<i class="fa fa-exclamation-triangle" aria-hidden="true"></i>';
        } else if (status === 3) {
            icon = '<i class="fa fa-exclamation-circle" aria-hidden="true"></i>';
        }
        otDatas.push({
            time1: currentOTInfoCfm.exceptionLimitErrorTime,
            time2: currentOTInfoCfm.agreementTime,
            time3: icon,
            _rowClass: 'overtime-status-' + status
        });
    }

    return otDatas;
}
