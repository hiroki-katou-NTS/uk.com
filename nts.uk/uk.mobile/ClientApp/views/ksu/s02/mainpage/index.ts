import { component, Prop } from '@app/core/component';
import { _, Vue, moment } from '@app/provider';
import * as $ from 'jquery';

import { CalendarComponent } from 'views/ksu/s02/component/calendar';
import {
    FirstInformationDto,
    WorkAvailabilityOfOneDayDto,
    ShiftMasterAndWorkInfoScheTime,
    ShiftMasterDto,
    WorkInfoAndScheTime,
    WorkAvailabilityDisplayInfoDto,
    SubmitWorkRequestCmd
} from 'views/ksu/s02/component/a';

@component({
    name: 'ksus02',
    route: '/ksu/s02',
    template: require('./index.vue'),
    resource: require('./resources.json'),
    constraints: [],
    components: {
        'calendar': CalendarComponent
    }
})
export class Ksus02Component extends Vue {

    @Prop({ default: () => ({}) })
    public params!: any;
    public clnLst = [];
    public datas = [];
    public dataCalendar = { data: null };
    public paramRegister = null;
    public isCurrentMonth: any = true;

    public created() {
        let vm = this;
    }

    public dataStartPage: any = null;

    public mounted() {
        this.startPage();
        // this.getData();
    }

    public dataFromChild(dataFromChild) {
        let self = this;
        self.paramRegister = dataFromChild;
    }

    public dataChange(dataFromChild) {
        let self = this;
        let d = dataFromChild;
        self.$mask('show');
        self.$http.post('at', servicePath.getWorkRequest, { startDate: dataFromChild.startDate, endDate: dataFromChild.endDate }).then((result: any) => {
            let year = new Date(dataFromChild.startDate).getFullYear();
            let month = new Date(dataFromChild.startDate).getMonth() + 1;
            if (year > new Date().getFullYear() || year == new Date().getFullYear() && month > (new Date().getMonth() + 1)) {
                self.isCurrentMonth = true;
            } else {
                self.isCurrentMonth = false;
            }
            self.dataStartPage.displayInfoListWorkOneDay = result.data;
            self.dataStartPage.startWork = dataFromChild.startDate;
            self.dataStartPage.endWork = dataFromChild.endDate;
            self.loadData();
            console.log(result);
            self.$mask('hide');
        }).catch((res: any) => {
            self.showError(res);
        });
        let i = 0;

    }
    public alarmMsg = '';
    public getData() {
        let self = this;

        let workAvailabilityDisplayInfo: WorkAvailabilityDisplayInfoDto = {
            assignmentMethod: 1, //AssignmentMethod enum
            nameList: ['001', '082'],
            timeZoneList: []
        };
        let workAvailabilityDisplayInfo1: WorkAvailabilityDisplayInfoDto = {
            assignmentMethod: 1, //AssignmentMethod enum
            nameList: ['001', '002', '009'],
            timeZoneList: []
        };
        let workAvailabilityDisplayInfo2: WorkAvailabilityDisplayInfoDto = {
            assignmentMethod: 1, //AssignmentMethod enum
            nameList: ['XXX', '009'],
            timeZoneList: []
        };
        let workAvailabilityOfOneDays = [];
        let workAvailabilityOfOneDay: WorkAvailabilityOfOneDayDto = {
            employeeId: 'employeeId',
            workAvailabilityDate: '2021/01/11',
            memo: 'memo',
            workAvaiByHolidayDto: workAvailabilityDisplayInfo1 // 勤務希望
        };
        let workAvailabilityOfOneDay1: WorkAvailabilityOfOneDayDto = {
            employeeId: 'employeeId',
            workAvailabilityDate: '2021/01/13',
            memo: 'memo3',
            workAvaiByHolidayDto: workAvailabilityDisplayInfo // 勤務希望
        };
        let workAvailabilityOfOneDay2: WorkAvailabilityOfOneDayDto = {
            employeeId: 'employeeId',
            workAvailabilityDate: '2021/01/12',
            memo: 'memo2',
            workAvaiByHolidayDto: workAvailabilityDisplayInfo2 // 勤務希望
        };
        workAvailabilityOfOneDays.push(workAvailabilityOfOneDay);
        workAvailabilityOfOneDays.push(workAvailabilityOfOneDay1);
        workAvailabilityOfOneDays.push(workAvailabilityOfOneDay2);
        //
        let shiftMasterDto: ShiftMasterDto = {
            shiftMasterName: '1na',
            shiftMasterCode: '001',
            color: '#00ffff',
            colorSmartphone: '#00ff00',
            remark: 'remark',
            workTypeCd: '001',
            workTypeName: 'worktype1',
            workTimeCd: '001',
            workTimeName: 'workTime1',
            workTime1: '7:30～17:30',
            workTime2: '18:30～23:00',
            colorText: '#ff7f27'
        };
        let shiftMasterDto1: ShiftMasterDto = {
            shiftMasterName: '2na',
            shiftMasterCode: '002',
            color: '#00ffff',
            colorSmartphone: '#b72533',
            remark: 'remark',
            workTypeCd: '002',
            workTypeName: 'worktype1',
            workTimeCd: '002',
            workTimeName: 'workTime1',
            workTime1: '9:00～19:00',
            workTime2: '',
            colorText: 'red'
        };
        let shiftMasterDto2: ShiftMasterDto = {
            shiftMasterName: '4na',
            shiftMasterCode: '004',
            color: '#00ff22',
            colorSmartphone: '#ceda16',
            remark: 'remark',
            workTypeCd: '003',
            workTypeName: 'worktype1',
            workTimeCd: '003',
            workTimeName: 'workTime1',
            workTime1: '9:30～21:00',
            workTime2: '',
            colorText: '#ff7f27'
        };
        let shiftMasterDto3: ShiftMasterDto = {
            shiftMasterName: '9na',
            shiftMasterCode: '009',
            color: '#11ff22',
            colorSmartphone: '#16daad',
            remark: 'remark',
            workTypeCd: '009',
            workTypeName: 'worktype1',
            workTimeCd: '009',
            workTimeName: 'workTime1',
            workTime1: '7:00～16:00',
            workTime2: '17:00～22:00',
            colorText: '#00f'
        };
        let workInfo: WorkInfoAndScheTime = {
            workType: null,
            workTimeSettingDto: null,
            timeZones: []
        };
        let shiftMasterAndWorkInfoScheTime: ShiftMasterAndWorkInfoScheTime = {
            shiftMaster: shiftMasterDto,
            workInfoAndScheTime: workInfo,
            workStyle: 1
        };
        let shiftMasterAndWorkInfoScheTime1: ShiftMasterAndWorkInfoScheTime = {
            shiftMaster: shiftMasterDto1,
            workInfoAndScheTime: workInfo,
            workStyle: 2
        };
        let shiftMasterAndWorkInfoScheTime2: ShiftMasterAndWorkInfoScheTime = {
            shiftMaster: shiftMasterDto2,
            workInfoAndScheTime: workInfo,
            workStyle: 0
        };
        let shiftMasterAndWorkInfoScheTime3: ShiftMasterAndWorkInfoScheTime = {
            shiftMaster: shiftMasterDto3,
            workInfoAndScheTime: workInfo,
            workStyle: 1
        };
        let listShiftInforData = [];
        listShiftInforData.push(shiftMasterAndWorkInfoScheTime);
        listShiftInforData.push(shiftMasterAndWorkInfoScheTime1);
        listShiftInforData.push(shiftMasterAndWorkInfoScheTime2);
        listShiftInforData.push(shiftMasterAndWorkInfoScheTime3);
        let screenData: FirstInformationDto = {
            specifyWorkPre: 1,
            shiftWorkUnit: 0,
            deadlineForWork: '2021/01/16',
            startWork: '2021/01/11',
            endWork: '2021/01/17',
            displayInfoListWorkOneDay: workAvailabilityOfOneDays,
            listShiftInfor: listShiftInforData,
            listDateIsHoliday: []
        };
        self.dataCalendar = {
            data: screenData
        };


    }

    public getData2(dataFromChild) {
        let self = this;

        let workAvailabilityDisplayInfo: WorkAvailabilityDisplayInfoDto = {
            assignmentMethod: 0, //AssignmentMethod enum
            nameList: [],
            timeZoneList: []
        };
        let workAvailabilityDisplayInfo1: WorkAvailabilityDisplayInfoDto = {
            assignmentMethod: 0, //AssignmentMethod enum
            nameList: [],
            timeZoneList: []
        };
        let workAvailabilityOfOneDays = [];
        let workAvailabilityOfOneDay: WorkAvailabilityOfOneDayDto = {
            employeeId: 'employeeId',
            workAvailabilityDate: dataFromChild.startDate,
            memo: 'memo',
            workAvaiByHolidayDto: workAvailabilityDisplayInfo1 // 勤務希望
        };
        let workAvailabilityOfOneDay1: WorkAvailabilityOfOneDayDto = {
            employeeId: 'employeeId',
            workAvailabilityDate: dataFromChild.endDate,
            memo: 'memo3',
            workAvaiByHolidayDto: workAvailabilityDisplayInfo // 勤務希望
        };
        workAvailabilityOfOneDays.push(workAvailabilityOfOneDay);
        workAvailabilityOfOneDays.push(workAvailabilityOfOneDay1);
        //
        let shiftMasterDto: ShiftMasterDto = {
            shiftMasterName: '1na',
            shiftMasterCode: '001',
            color: '#00ffff',
            colorSmartphone: '#00ff00',
            remark: 'remark',
            workTypeCd: '001',
            workTypeName: 'worktype1',
            workTimeCd: '001',
            workTimeName: 'workTime1',
            workTime1: '7:30～17:30',
            workTime2: '18:30～23:00',
            colorText: '#00f'
        };
        let shiftMasterDto1: ShiftMasterDto = {
            shiftMasterName: '2na',
            shiftMasterCode: '002',
            color: '#00ffff',
            colorSmartphone: '#b72533',
            remark: 'remark',
            workTypeCd: '002',
            workTypeName: 'worktype1',
            workTimeCd: '002',
            workTimeName: 'workTime1',
            workTime1: '9:00～19:00',
            workTime2: '',
            colorText: '#00f'

        };
        let shiftMasterDto2: ShiftMasterDto = {
            shiftMasterName: '4na',
            shiftMasterCode: '004',
            color: '#00ff22',
            colorSmartphone: '#ceda16',
            remark: 'remark',
            workTypeCd: '003',
            workTypeName: 'worktype1',
            workTimeCd: '003',
            workTimeName: 'workTime1',
            workTime1: '9:30～21:00',
            workTime2: '',
            colorText: '#ff7f27'
        };
        let shiftMasterDto3: ShiftMasterDto = {
            shiftMasterName: '9na',
            shiftMasterCode: '009',
            color: '#11ff22',
            colorSmartphone: '#16daad',
            remark: 'remark',
            workTypeCd: '009',
            workTypeName: 'worktype1',
            workTimeCd: '009',
            workTimeName: 'workTime1',
            workTime1: '7:00～16:00',
            workTime2: '17:00～22:00',
            colorText: 'red'
        };
        let workInfo: WorkInfoAndScheTime = {
            workType: null,
            workTimeSettingDto: null,
            timeZones: []
        };
        let shiftMasterAndWorkInfoScheTime: ShiftMasterAndWorkInfoScheTime = {
            shiftMaster: shiftMasterDto,
            workInfoAndScheTime: workInfo,
            workStyle: 1
        };
        let shiftMasterAndWorkInfoScheTime1: ShiftMasterAndWorkInfoScheTime = {
            shiftMaster: shiftMasterDto1,
            workInfoAndScheTime: workInfo,
            workStyle: 2
        };
        let shiftMasterAndWorkInfoScheTime2: ShiftMasterAndWorkInfoScheTime = {
            shiftMaster: shiftMasterDto2,
            workInfoAndScheTime: workInfo,
            workStyle: 0
        };
        let shiftMasterAndWorkInfoScheTime3: ShiftMasterAndWorkInfoScheTime = {
            shiftMaster: shiftMasterDto3,
            workInfoAndScheTime: workInfo,
            workStyle: 1
        };
        let listShiftInforData = [];
        listShiftInforData.push(shiftMasterAndWorkInfoScheTime);
        listShiftInforData.push(shiftMasterAndWorkInfoScheTime1);
        listShiftInforData.push(shiftMasterAndWorkInfoScheTime2);
        listShiftInforData.push(shiftMasterAndWorkInfoScheTime3);
        let screenData: FirstInformationDto = {
            specifyWorkPre: 0,
            shiftWorkUnit: 1,
            deadlineForWork: dataFromChild.endWork,
            startWork: dataFromChild.startDate,
            endWork: dataFromChild.endDate,
            displayInfoListWorkOneDay: workAvailabilityOfOneDays,
            listShiftInfor: listShiftInforData,
            listDateIsHoliday: []
        };
        self.dataCalendar = {
            data: screenData
        };


    }

    public getDataHoliday() {
        let self = this;

        let workAvailabilityDisplayInfo: WorkAvailabilityDisplayInfoDto = {
            assignmentMethod: 1, //AssignmentMethod enum
            nameList: ['001', '082'],
            timeZoneList: []
        };
        let workAvailabilityDisplayInfo1: WorkAvailabilityDisplayInfoDto = {
            assignmentMethod: 0, //AssignmentMethod enum
            nameList: [],
            timeZoneList: []
        };
        let workAvailabilityDisplayInfo2: WorkAvailabilityDisplayInfoDto = {
            assignmentMethod: 0, //AssignmentMethod enum
            nameList: [],
            timeZoneList: []
        };
        let workAvailabilityOfOneDays = [];
        let workAvailabilityOfOneDay: WorkAvailabilityOfOneDayDto = {
            employeeId: 'employeeId',
            workAvailabilityDate: '2021/01/11',
            memo: 'memo',
            workAvaiByHolidayDto: workAvailabilityDisplayInfo1 // 勤務希望
        };
        let workAvailabilityOfOneDay1: WorkAvailabilityOfOneDayDto = {
            employeeId: 'employeeId',
            workAvailabilityDate: '2021/01/13',
            memo: 'memo3',
            workAvaiByHolidayDto: workAvailabilityDisplayInfo // 勤務希望
        };
        let workAvailabilityOfOneDay2: WorkAvailabilityOfOneDayDto = {
            employeeId: 'employeeId',
            workAvailabilityDate: '2021/01/12',
            memo: 'memo2',
            workAvaiByHolidayDto: workAvailabilityDisplayInfo2 // 勤務希望
        };
        workAvailabilityOfOneDays.push(workAvailabilityOfOneDay);
        workAvailabilityOfOneDays.push(workAvailabilityOfOneDay1);
        workAvailabilityOfOneDays.push(workAvailabilityOfOneDay2);
        //
        let shiftMasterDto: ShiftMasterDto = {
            shiftMasterName: '1na',
            shiftMasterCode: '001',
            color: '#00ffff',
            colorSmartphone: '#00ff00',
            remark: 'remark',
            workTypeCd: '001',
            workTypeName: 'worktype1',
            workTimeCd: '001',
            workTimeName: 'workTime1',
            workTime1: '7:30～17:30',
            workTime2: '18:30～23:00',
            colorText: '#ff7f27'
        };
        let shiftMasterDto1: ShiftMasterDto = {
            shiftMasterName: '2na',
            shiftMasterCode: '002',
            color: '#00ffff',
            colorSmartphone: '#b72533',
            remark: 'remark',
            workTypeCd: '002',
            workTypeName: 'worktype1',
            workTimeCd: '002',
            workTimeName: 'workTime1',
            workTime1: '9:00～19:00',
            workTime2: '',
            colorText: 'red'
        };
        let shiftMasterDto2: ShiftMasterDto = {
            shiftMasterName: '4na',
            shiftMasterCode: '004',
            color: '#00ff22',
            colorSmartphone: '#ceda16',
            remark: 'remark',
            workTypeCd: '003',
            workTypeName: 'worktype1',
            workTimeCd: '003',
            workTimeName: 'workTime1',
            workTime1: '9:30～21:00',
            workTime2: '',
            colorText: '#ff7f27'
        };
        let shiftMasterDto3: ShiftMasterDto = {
            shiftMasterName: '9na',
            shiftMasterCode: '009',
            color: '#11ff22',
            colorSmartphone: '#16daad',
            remark: 'remark',
            workTypeCd: '009',
            workTypeName: 'worktype1',
            workTimeCd: '009',
            workTimeName: 'workTime1',
            workTime1: '7:00～16:00',
            workTime2: '17:00～22:00',
            colorText: '#00f'
        };
        let workInfo: WorkInfoAndScheTime = {
            workType: null,
            workTimeSettingDto: null,
            timeZones: []
        };
        let shiftMasterAndWorkInfoScheTime: ShiftMasterAndWorkInfoScheTime = {
            shiftMaster: shiftMasterDto,
            workInfoAndScheTime: workInfo,
            workStyle: 1
        };
        let shiftMasterAndWorkInfoScheTime1: ShiftMasterAndWorkInfoScheTime = {
            shiftMaster: shiftMasterDto1,
            workInfoAndScheTime: workInfo,
            workStyle: 2
        };
        let shiftMasterAndWorkInfoScheTime2: ShiftMasterAndWorkInfoScheTime = {
            shiftMaster: shiftMasterDto2,
            workInfoAndScheTime: workInfo,
            workStyle: 0
        };
        let shiftMasterAndWorkInfoScheTime3: ShiftMasterAndWorkInfoScheTime = {
            shiftMaster: shiftMasterDto3,
            workInfoAndScheTime: workInfo,
            workStyle: 1
        };
        let listShiftInforData = [];
        listShiftInforData.push(shiftMasterAndWorkInfoScheTime);
        listShiftInforData.push(shiftMasterAndWorkInfoScheTime1);
        listShiftInforData.push(shiftMasterAndWorkInfoScheTime2);
        listShiftInforData.push(shiftMasterAndWorkInfoScheTime3);
        let screenData: FirstInformationDto = {
            specifyWorkPre: 0,
            shiftWorkUnit: 1,
            deadlineForWork: '2021/01/16',
            startWork: '2021/01/11',
            endWork: '2021/01/17',
            displayInfoListWorkOneDay: workAvailabilityOfOneDays,
            listShiftInfor: listShiftInforData,
            listDateIsHoliday: []
        };
        self.dataCalendar = {
            data: screenData
        };


    }
    public getDataHoliday2(dataFromChild) {
        let self = this;

        let workAvailabilityDisplayInfo: WorkAvailabilityDisplayInfoDto = {
            assignmentMethod: 0, //AssignmentMethod enum
            nameList: [],
            timeZoneList: []
        };
        let workAvailabilityDisplayInfo1: WorkAvailabilityDisplayInfoDto = {
            assignmentMethod: 0, //AssignmentMethod enum
            nameList: [],
            timeZoneList: []
        };
        let workAvailabilityOfOneDays = [];
        let workAvailabilityOfOneDay: WorkAvailabilityOfOneDayDto = {
            employeeId: 'employeeId',
            workAvailabilityDate: dataFromChild.startDate,
            memo: 'memo',
            workAvaiByHolidayDto: workAvailabilityDisplayInfo1 // 勤務希望
        };
        let workAvailabilityOfOneDay1: WorkAvailabilityOfOneDayDto = {
            employeeId: 'employeeId',
            workAvailabilityDate: dataFromChild.endDate,
            memo: 'memo3',
            workAvaiByHolidayDto: workAvailabilityDisplayInfo // 勤務希望
        };
        workAvailabilityOfOneDays.push(workAvailabilityOfOneDay);
        workAvailabilityOfOneDays.push(workAvailabilityOfOneDay1);
        //
        let shiftMasterDto: ShiftMasterDto = {
            shiftMasterName: '1na',
            shiftMasterCode: '001',
            color: '#00ffff',
            colorSmartphone: '#00ff00',
            remark: 'remark',
            workTypeCd: '001',
            workTypeName: 'worktype1',
            workTimeCd: '001',
            workTimeName: 'workTime1',
            workTime1: '7:30～17:30',
            workTime2: '18:30～23:00',
            colorText: '#00f'
        };
        let shiftMasterDto1: ShiftMasterDto = {
            shiftMasterName: '2na',
            shiftMasterCode: '002',
            color: '#00ffff',
            colorSmartphone: '#b72533',
            remark: 'remark',
            workTypeCd: '002',
            workTypeName: 'worktype1',
            workTimeCd: '002',
            workTimeName: 'workTime1',
            workTime1: '9:00～19:00',
            workTime2: '',
            colorText: '#00f'

        };
        let shiftMasterDto2: ShiftMasterDto = {
            shiftMasterName: '4na',
            shiftMasterCode: '004',
            color: '#00ff22',
            colorSmartphone: '#ceda16',
            remark: 'remark',
            workTypeCd: '003',
            workTypeName: 'worktype1',
            workTimeCd: '003',
            workTimeName: 'workTime1',
            workTime1: '9:30～21:00',
            workTime2: '',
            colorText: '#ff7f27'
        };
        let shiftMasterDto3: ShiftMasterDto = {
            shiftMasterName: '9na',
            shiftMasterCode: '009',
            color: '#11ff22',
            colorSmartphone: '#16daad',
            remark: 'remark',
            workTypeCd: '009',
            workTypeName: 'worktype1',
            workTimeCd: '009',
            workTimeName: 'workTime1',
            workTime1: '7:00～16:00',
            workTime2: '17:00～22:00',
            colorText: 'red'
        };
        let workInfo: WorkInfoAndScheTime = {
            workType: null,
            workTimeSettingDto: null,
            timeZones: []
        };
        let shiftMasterAndWorkInfoScheTime: ShiftMasterAndWorkInfoScheTime = {
            shiftMaster: shiftMasterDto,
            workInfoAndScheTime: workInfo,
            workStyle: 1
        };
        let shiftMasterAndWorkInfoScheTime1: ShiftMasterAndWorkInfoScheTime = {
            shiftMaster: shiftMasterDto1,
            workInfoAndScheTime: workInfo,
            workStyle: 2
        };
        let shiftMasterAndWorkInfoScheTime2: ShiftMasterAndWorkInfoScheTime = {
            shiftMaster: shiftMasterDto2,
            workInfoAndScheTime: workInfo,
            workStyle: 0
        };
        let shiftMasterAndWorkInfoScheTime3: ShiftMasterAndWorkInfoScheTime = {
            shiftMaster: shiftMasterDto3,
            workInfoAndScheTime: workInfo,
            workStyle: 1
        };
        let listShiftInforData = [];
        listShiftInforData.push(shiftMasterAndWorkInfoScheTime);
        listShiftInforData.push(shiftMasterAndWorkInfoScheTime1);
        listShiftInforData.push(shiftMasterAndWorkInfoScheTime2);
        listShiftInforData.push(shiftMasterAndWorkInfoScheTime3);
        let screenData: FirstInformationDto = {
            specifyWorkPre: 0,
            shiftWorkUnit: 1,
            deadlineForWork: dataFromChild.endWork,
            startWork: dataFromChild.startDate,
            endWork: dataFromChild.endDate,
            displayInfoListWorkOneDay: workAvailabilityOfOneDays,
            listShiftInfor: listShiftInforData,
            listDateIsHoliday: []
        };
        self.dataCalendar = {
            data: screenData
        };


    }

    private startPage() {
        let self = this;
        self.$mask('show');
        self.$http.post('at', servicePath.getInforinitialStartup, { baseDate: moment(new Date()).format('YYYY/MM/DD') }).then((result: any) => {
            self.dataStartPage = result.data;
            let startDate = new Date(result.data.deadlineForWork);
            let dateOfWeek = moment(moment(startDate).format('YYYY/MM/DD')).format('dd');   
            self.alarmMsg = this.$i18n('KSUS02_1', (self.dataStartPage.shiftWorkUnit == 1 ? this.$i18n('KSUS02_19') + self.dataStartPage.deadlineForWork.substring(8, 10) + '日' :
                this.$i18n('KSUS02_20') + dateOfWeek + '曜日'));
            self.loadData();
            console.log(result);
            self.$mask('hide');
        }).catch((res: any) => {
            self.showError(res);
            self.$mask('hide');
        });
    }

    public loadData() {
        let self = this;
        self.dataCalendar = {
            data: self.dataStartPage
        };
    }
    public register() {
        let self = this;
        self.$mask('show');
        self.$http.post('at', servicePath.saveWorkRequest, self.paramRegister).then((result: any) => {
            self.$modal.info('Msg_15').then(() => {
                self.$mask('hide');
                let data = {
                    startDate: self.paramRegister.startPeriod,
                    endDate: self.paramRegister.endPeriod
                };
                self.dataChange(data);
            });
        }).catch((res: any) => {
            self.$mask('hide');
            self.showError(res);
        });
        let i = 0;
    }
    private showError(res: any) {
        let self = this;
        self.$mask('hide');
        if (!_.isEqual(res.message, 'can not found message id')) {
            self.$modal.error({ messageId: res.messageId, messageParams: res.parameterIds });
        } else {
            self.$modal.error(res.message);
        }
    }
}

const servicePath = {
    getInforinitialStartup: 'screen/at/schedule/getInforinitialStartup',
    saveWorkRequest: 'screen/at/schedule/saveworkrequest',
    getWorkRequest: 'screen/at/schedule/getWorkRequest'
};







