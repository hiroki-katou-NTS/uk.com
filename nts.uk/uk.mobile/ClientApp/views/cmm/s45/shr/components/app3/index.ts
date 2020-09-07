import { Vue } from '@app/provider';
import { component,Prop } from '@app/core/component';
import * as _ from 'lodash';

@component({
    name: 'cmms45componentsapp3',
    template: require('./index.vue'),
    validations: {},
    constraints: []
})
export class CmmS45ComponentsApp3Component extends Vue {
//     public title: string = 'CmmS45ComponentsApp3';
//     @Prop({
//         default: () => ({
//             appDispInfoStartup: null,
//             appDetail: null
//         })
        
//     })
//     public readonly params: {
//         appDispInfoStartup: any,
//         appDetail: any
//     };

//     public dataFetch: any;
//     public isCondition1: boolean = false;
//     public isCondition2: boolean = false;
//     public businessTrip: any = new BusinessTrip();

//     public $app() {
//         return this.businessTrip;
//     }

//     public user: any;
//     public created() {
//         const vm = this;
//         vm.params.appDetail = {};
//         vm.$auth.user.then((usr: any) => {
//             vm.user = usr;
//         }).then((res: any) => {
//             this.fetchData(vm.params);
//         });

//     }

//     public mounted() {

//     }

//     public fetchData(getParams: any) {
//         const vm = this;
//         vm.$http.post('at', API.startDetailBScreen, {
//             companyId: vm.user.companyId,
//             appId: vm.params.appDispInfoStartup.appDetailScreenInfo.application.appID,
//             appDispInfoStartupDto: vm.params.appDispInfoStartup
//         })
//             .then((res: any) => {
//                 vm.dataFetch = res.data;
//                 vm.bindStart();
//                 vm.params.appDetail = vm.dataFetch;
//                 // self.bindCodition(self.dataFetch.appWorkChangeDispInfo);
//             })
//             .catch((res: any) => {
//                 vm.$mask('hide');
//                 if (res.messageId) {
//                     vm.$modal.error({ messageId: res.messageId, messageParams: res.parameterIds });
//                 } else {

//                     if (_.isArray(res.errors)) {
//                         vm.$modal.error({ messageId: res.errors[0].messageId, messageParams: res.parameterIds });
//                     } else {
//                         vm.$modal.error({ messageId: res.errors.messageId, messageParams: res.parameterIds });
//                     }
//                 }
//             });
//     }

//     public bindStart() {
//         let params = this.dataFetch;

//         this.bindCodition(params.businessTripInfoOutput);

//         let workTypeCode = params.appWorkChange.opWorkTypeCD;
//         let workType = _.find(params.appWorkChangeDispInfo.workTypeLst, (item: any) => item.workTypeCode == workTypeCode);
//         let workTypeName = workType ? workType.name : this.$i18n('KAFS07_10');
//         this.$app().workType = workTypeCode + '  ' + workTypeName;

//         let workTimeCode = params.appWorkChange.opWorkTimeCD;
//         let workTime = _.find(params.appWorkChangeDispInfo.appDispInfoStartupOutput.appDispInfoWithDateOutput.opWorkTimeLst, (item: any) => item.worktimeCode == workTimeCode);
//         let workTimeName = workTime ? workTime.workTimeDisplayName.workTimeName : this.$i18n('KAFS07_10');
//         if (!workTimeCode) {
//             workTimeCode = this.$i18n('KAFS07_9');
//             workTimeName = '';
//         }
//         this.$app().workTime = workTimeCode + '  ' + workTimeName;
//         if (!_.isEmpty(params.appWorkChange.timeZoneWithWorkNoLst)) {
//             let time1 = _.find(params.appWorkChange.timeZoneWithWorkNoLst, (item: any) => item.workNo == 1);
//             let time2 = _.find(params.appWorkChange.timeZoneWithWorkNoLst, (item: any) => item.workNo == 2);
//             if (time1) {
//                 this.$app().workHours1 = this.$dt.timedr(time1.timeZone.startTime) + ' ~ ' + this.$dt.timedr(time1.timeZone.endTime);
//             } else {
//                 this.$app().workHours1 = this.$i18n('KAFS07_15');
//                 this.$app().isWorkHours1 = false;
//             }
//             if (time2) {
//                 this.$app().workHours2 = this.$dt.timedr(time2.timeZone.startTime) + ' ~ ' + this.$dt.timedr(time2.timeZone.endTime);
//             } else {
//                 this.$app().workHours2 = this.$i18n('KAFS07_15');
//                 this.$app().isWorkHours2 = false;
//             }
//         } else {
//             if (this.isCondition1) {
//                 this.$app().workHours1 = this.$i18n('KAFS07_15');
//                 this.$app().workHours2 = this.$i18n('KAFS07_15');
//             }
//         }
//         this.$app().straight = params.appWorkChange.straightGo == 1 ? true : false;
//         this.$app().bounce = params.appWorkChange.straightBack == 1 ? true : false;
//     }

//     public bindCodition(params: any) {
//         // set condition
//         this.isCondition1 = this.isDisplay1(params);
//         this.isCondition2 = this.isDisplay2(params);
//     }

//     public isDisplay1(params: any) {
//         return params.reflectWorkChangeAppDto.whetherReflectAttendance == 1;
//         // return true;
//     }
//     // ※1 = ○　AND　「勤務変更申請の表示情報．申請表示情報．申請表示情報(基準日関係なし)．複数回勤務の管理」= true
//     public isDisplay2(params: any) {
//         return params.reflectWorkChangeAppDto.whetherReflectAttendance == 1 && params.appDispInfoStartupOutput.appDispInfoNoDateOutput.managementMultipleWorkCycles;
//         // return true;

//     }
// }




// export class BusinessTrip {
//     constructor() {

//     }
//     public departureTime: number;
//     public returnTime: number;
//     public date: String;
//     public wkTypeCd: String;
//     public wkTimeCd: String;
//     public startWorkTime: number;
//     public endWorkTime: number;
// }

// const API = {
//     startDetailBScreen : 'at/request/application/businesstrip/mobile/startScreenB'
}