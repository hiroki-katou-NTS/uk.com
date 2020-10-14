import { _, Vue } from '@app/provider';
import { component, Prop } from '@app/core/component';
import { TimeSetDisp, TimeZone, TimeStampAppOtherDto, DestinationTimeZoneAppDto, DestinationTimeAppDto, TimeStampAppDto } from '../../../../../kaf/s02/shr';
import {
    KafS00SubP3Component
} from 'views/kaf/s00/sub/p3';

@component({
    name: 'cmms45shrcomponentsapp70',
    route: '/cmm/s45/shr/components/app70',
    style: require('./style.scss'),
    template: require('./index.vue'),
    resource: require('./resources.json'),
    validations: {},
    constraints: [],
    components: {
        'kafs00subp3': KafS00SubP3Component,
    },
})
export class CmmS45ShrComponentsApp70Component extends Vue {
    public title: string = 'CmmS45ShrComponentsApp70';

    @Prop({
        default: () => ({
            appDispInfoStartupOutput: null,
            appDetail: null
        })
        
    })
    public readonly params: {
        appDispInfoStartupOutput: any,
        appDetail: any
    };

    public dataFetch: any;
    public isCondition1: boolean = false;
    public isCondition2: boolean = false;

    public listTimeStampApp: Array<TimeStampAppDto> = [];
    public listDestinationTimeApp: Array<DestinationTimeAppDto> = [];
    public listTimeStampAppOther: Array<TimeStampAppOtherDto> = [];
    public listDestinationTimeZoneApp: Array<DestinationTimeZoneAppDto> = [];

    public listWorkHours: TimeSetDisp[] = [];
    public listTempoHours: TimeSetDisp[] = [];
    public listOutingHours: TimeSetDisp[] = [];
    public listBreakHours: TimeSetDisp[] = [];
    public listNursingHours: TimeSetDisp[] = [];
    public listParentHours: TimeSetDisp[] = [];

    public appStamp: AppStamp = new AppStamp();
    public $app() {
        return this.appStamp;
    }

    public user: any;
    public created() {
        const vm = this;
        vm.params.appDetail = {};
        vm.$auth.user.then((usr: any) => {
            vm.user = usr;
        }).then((res: any) => {
            this.fetchData(vm.params);
        });

    }

    public mounted() {

    }

    private fetchData(params: any) {
        const self = this;

        self.$http.post('at', API.detailAppStamp, {
            companyId: self.user.companyId,
            appId: self.params.appDispInfoStartupOutput.appDetailScreenInfo.application.appID,
            appDispInfoStartupDto: self.params.appDispInfoStartupOutput,
            recoderFlag: false
        })
        .then((result: any) => {
            if (result) {
                console.log(result);
                self.dataFetch = result.data;
                self.bindData();
                self.params.appDetail = self.dataFetch;
            }
        })
        .catch((result: any) => {
            self.$mask('hide');
            if (result.messageId) {
                self.$modal.error({ messageId: result.messageId, messageParams: result.parameterIds });
            } else {
                if (result.errors) {
                    if (_.isArray(result.errors)) {
                        self.$modal.error({ messageId: result.errors[0].messageId, messageParams: result.parameterIds });
                    } else {
                        self.$modal.error({ messageId: result.errors.messageId, messageParams: result.parameterIds });
                    }
                }
            }
        });
    }

    private bindData() {
        const self = this;

        self.listTimeStampApp = self.dataFetch.appStampOptional.listTimeStampApp;
        self.listDestinationTimeApp = self.dataFetch.appStampOptional.listDestinationTimeApp;
        self.listTimeStampAppOther = self.dataFetch.appStampOptional.listTimeStampAppOther;
        self.listDestinationTimeZoneApp = self.dataFetch.appStampOptional.listDestinationTimeZoneApp;

        /*
        *
        *    Bind data application
        *
        */
        if (!_.isEmpty(self.listTimeStampApp)) {
            self.listTimeStampApp.forEach((item) => {
                // loop for work hour (type = 0)
                if (item.destinationTimeApp.timeStampAppEnum === 0) {
                    // neu trong list workHours da co item co cung frame -> thay doi start hoac end time
                    if (_.filter(self.listWorkHours, { frame: item.destinationTimeApp.engraveFrameNo }).length > 0) {
                        self.listWorkHours = _.map(self.listWorkHours, (x: TimeSetDisp) => {
                            if (x.frame === item.destinationTimeApp.engraveFrameNo) {
                                if (item.destinationTimeApp.startEndClassification === 0) {
                                    x.appHours.startTime = item.timeOfDay;
                                } else {
                                    x.appHours.endTime = item.timeOfDay;
                                }
                            }

                            return x;
                        });
                    } else {
                        // neu chua co item trong list workHours -> tao moi
                        let workHour: TimeSetDisp;
                        if (item.destinationTimeApp.startEndClassification === 0) {
                            workHour = new TimeSetDisp(item.destinationTimeApp.engraveFrameNo === 1 ? 'KAFS02_4' : 'KAFS02_6', item.destinationTimeApp.engraveFrameNo, null, null, item.timeOfDay, null );
                        } else {
                            workHour = new TimeSetDisp(item.destinationTimeApp.engraveFrameNo === 1 ? 'KAFS02_4' : 'KAFS02_6', item.destinationTimeApp.engraveFrameNo, null, null, null, item.timeOfDay );
                        }

                        self.listWorkHours.push(workHour);
                    }
                }

                // loop for tempo hour (type = 1)
                if (item.destinationTimeApp.timeStampAppEnum === 1) {
                    // neu trong list tempoHours da co item co cung frame -> thay doi start hoac end time
                    if (_.filter(self.listTempoHours, { frame: item.destinationTimeApp.engraveFrameNo }).length > 0) {
                        self.listTempoHours = _.map(self.listTempoHours, (x: TimeSetDisp) => {
                            if (x.frame === item.destinationTimeApp.engraveFrameNo) {
                                if (item.destinationTimeApp.startEndClassification === 0) {
                                    x.appHours.startTime = item.timeOfDay;
                                } else {
                                    x.appHours.endTime = item.timeOfDay;
                                }
                            }

                            return x;
                        });
                    } else {
                        // neu chua co item trong list tempoHours -> tao moi
                        let tempoHour: TimeSetDisp;
                        if (item.destinationTimeApp.startEndClassification === 0) {
                            tempoHour = new TimeSetDisp('KAFS02_7', item.destinationTimeApp.engraveFrameNo, null, null, item.timeOfDay, null );
                        } else {
                            tempoHour = new TimeSetDisp('KAFS02_7', item.destinationTimeApp.engraveFrameNo, null, null, null, item.timeOfDay );
                        }

                        self.listTempoHours.push(tempoHour);
                    }
                }

                // loop for goout hour (type = 2)
                if (item.destinationTimeApp.timeStampAppEnum === 2) {
                    // neu trong list gooutHours da co item co cung frame -> thay doi start hoac end time
                    if (_.filter(self.listOutingHours, { frame: item.destinationTimeApp.engraveFrameNo }).length > 0) {
                        self.listOutingHours = _.map(self.listOutingHours, (x: TimeSetDisp) => {
                            if (x.frame === item.destinationTimeApp.engraveFrameNo) {
                                if (item.destinationTimeApp.startEndClassification === 0) {
                                    x.appHours.startTime = item.timeOfDay;
                                } else {
                                    x.appHours.endTime = item.timeOfDay;
                                }
                            }

                            return x;
                        });
                    } else {
                        // neu chua co item trong list gooutHours -> tao moi
                        let outingHour: TimeSetDisp;
                        if (item.destinationTimeApp.startEndClassification === 0) {
                            outingHour = new TimeSetDisp('KAFS02_9', item.destinationTimeApp.engraveFrameNo, null, null, item.timeOfDay, null, item.appStampGoOutAtr );
                        } else {
                            outingHour = new TimeSetDisp('KAFS02_9', item.destinationTimeApp.engraveFrameNo, null, null, null, item.timeOfDay, item.appStampGoOutAtr );
                        }

                        self.listOutingHours.push(outingHour);
                    }
                }

            });
        }
        
        if (!_.isEmpty(self.listTimeStampAppOther)) {
            self.listTimeStampAppOther.forEach((item) => {
                // loop for break hour (type = 2)
                if (item.destinationTimeZoneApp.timeZoneStampClassification === 2) {
                    if (_.filter(self.listBreakHours, { frame: item.destinationTimeZoneApp.engraveFrameNo }).length > 0) {
                        self.listOutingHours = _.map(self.listOutingHours, (x: TimeSetDisp) => {
                            if (x.frame === item.destinationTimeZoneApp.engraveFrameNo) {
                                x.appHours.startTime = item.timeZone.startTime;
                                x.appHours.endTime = item.timeZone.endTime;
                            }

                            return x;
                        });
                    } else {
                        // neu chua co item trong list gooutHours -> tao moi
                        let breakHour: TimeSetDisp;
                        breakHour = new TimeSetDisp('KAFS02_12', item.destinationTimeZoneApp.engraveFrameNo, null, null, item.timeZone.startTime, item.timeZone.endTime );
                        
                        self.listOutingHours.push(breakHour);
                    }
                }
                
                // loop for parentHours (type = 0)
                if (item.destinationTimeZoneApp.timeZoneStampClassification === 0) {
                    if (_.filter(self.listParentHours, { frame: item.destinationTimeZoneApp.engraveFrameNo }).length > 0) {
                        self.listParentHours = _.map(self.listParentHours, (x: TimeSetDisp) => {
                            if (x.frame === item.destinationTimeZoneApp.engraveFrameNo) {
                                x.appHours.startTime = item.timeZone.startTime;
                                x.appHours.endTime = item.timeZone.endTime;
                            }

                            return x;
                        });
                    } else {
                        // neu chua co item trong list parentHours -> tao moi
                        let parentHour: TimeSetDisp;
                        parentHour = new TimeSetDisp('KAFS02_13', item.destinationTimeZoneApp.engraveFrameNo, null, null, item.timeZone.startTime, item.timeZone.endTime );
                        
                        self.listParentHours.push(parentHour);
                    }
                }

                // loop for long term hour (type = 1)
                if (item.destinationTimeZoneApp.timeZoneStampClassification === 1) {
                    if (_.filter(self.listNursingHours, { frame: item.destinationTimeZoneApp.engraveFrameNo }).length > 0) {
                        self.listNursingHours = _.map(self.listNursingHours, (x: TimeSetDisp) => {
                            if (x.frame === item.destinationTimeZoneApp.engraveFrameNo) {
                                x.appHours.startTime = item.timeZone.startTime;
                                x.appHours.endTime = item.timeZone.endTime;
                            }

                            return x;
                        });
                    } else {
                        // neu chua co item trong list  -> tao moi
                        let nursingHour: TimeSetDisp;
                        nursingHour = new TimeSetDisp('KAFS02_15', item.destinationTimeZoneApp.engraveFrameNo, null, null, item.timeZone.startTime, item.timeZone.endTime );
                        
                        self.listNursingHours.push(nursingHour);
                    }
                }
            });
        }

        /*
        *
        *    Bind data actual
        *
        */


        /*
        *
        *    Sort list data
        *
        */
        self.listWorkHours = _.sortBy(self.listWorkHours, ['frame']);
        self.listTempoHours = _.sortBy(self.listTempoHours, ['frame']);
        self.listOutingHours = _.sortBy(self.listOutingHours, ['frame']);
        self.listBreakHours = _.sortBy(self.listBreakHours, ['frame']);
        self.listParentHours = _.sortBy(self.listParentHours, ['frame']);
        self.listNursingHours = _.sortBy(self.listNursingHours, ['frame']);
    }
}

const API = {
    detailAppStamp: 'at/request/application/stamp/detailAppStamp',
};

export class AppStamp {

}