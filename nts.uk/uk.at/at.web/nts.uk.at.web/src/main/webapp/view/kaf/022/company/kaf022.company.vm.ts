module nts.uk.at.view.kmf022.company.viewmodel {
    import getText = nts.uk.resource.getText;
    import ScreenModelA = nts.uk.at.view.kmf022.a.viewmodel.ScreenModelA;
    import ScreenModelB = nts.uk.at.view.kmf022.b.viewmodel.ScreenModelB;
    import ScreenModelV = nts.uk.at.view.kmf022.v.viewmodel.ScreenModelV;

    export class ScreenModel {
        tabs: KnockoutObservableArray<NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        viewmodelA: ScreenModelA;
        viewmodelB: ScreenModelB;
        viewmodelV: ScreenModelV;

        constructor() {
            var self = this;
            self.tabs = ko.observableArray([
                { id: 'tab-01', title: getText('KAF022_2'), content: '.tab-content-01', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-02', title: getText('KAF022_748'), content: '.tab-content-02', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-03', title: getText('KAF022_3'), content: '.tab-content-03', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-04', title: getText('KAF022_4'), content: '.tab-content-04', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-05', title: getText('KAF022_5'), content: '.tab-content-05', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-06', title: getText('KAF022_6'), content: '.tab-content-06', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-07', title: getText('KAF022_7'), content: '.tab-content-07', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-08', title: getText('KAF022_8'), content: '.tab-content-08', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-09', title: getText('KAF022_707'), content: '.tab-content-09', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-10', title: getText('KAF022_10'), content: '.tab-content-10', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-11', title: getText('KAF022_11'), content: '.tab-content-11', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-12', title: getText('KAF022_12'), content: '.tab-content-12', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-13', title: getText('KAF022_705'), content: '.tab-content-13', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-14', title: getText('KAF022_395'), content: '.tab-content-14', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-15', title: getText('KAF022_749'), content: '.tab-content-15', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tab-01');

            self.viewmodelA = new ScreenModelA();
            self.viewmodelB = new ScreenModelB();
            self.viewmodelV = new ScreenModelV();
        }

        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            dfd.resolve();
            self.loadData();
            return dfd.promise();
        }

        loadData(): void {
            let self = this,
                position: number = $('.tab-content-1').scrollTop();
            nts.uk.ui.block.grayout();
            service.findAllData().done((data: any) => {
                self.viewmodelA.initData(data);
                self.viewmodelB.initData(data);
                self.viewmodelV.initData(data);
            }).always(() => {
                nts.uk.ui.errors.clearAll();
                nts.uk.ui.block.clear();
                $("#a4_6").focus();
                setTimeout(function() {
                    $('.tab-content-1').scrollTop(position);
                }, 1500);


            });

        }

        saveDataAt(): void {
            $('#a16_14').trigger("validate");
            $('#a16_15').trigger("validate");
            $('#a7_23').trigger("validate");
            $('#a7_23_2').trigger("validate");
            $('#a7_23_3').trigger("validate");
            if (nts.uk.ui.errors.hasError()) { return; }
            // let self = this,
            //     data: any = {},
            //     dataA4 = [],
            //     postion: number = $('.tab-content-1').scrollTop();
            // if(self.companyUnit() == 0 && self.workplaceUnit() == 0 && self.employeeUnit() == 0){
            //     nts.uk.ui.dialog.alertError({ messageId: 'Msg_1590' });
            //     return;
            // }
            // for (let i = 0; i < self.sizeArrayA4(); i++) {
            //     dataA4.push({
            //         closureId: self.dataA4Display()[i].index,
            //         userAtr: (self.dataA4Display()[i].a4_6() ? 1 : 0),
            //         deadlineCriteria: self.dataA4Display()[i].a4_7(),
            //         deadline: self.dataA4Display()[i].a4_8()
            //     });
            // }
            // data.appDead = dataA4;
            //                data.appSet = {
            //                    companyId: self.companyId(),
            //                    reasonDisp: self.selectedIdA5_14(),
            //                    warnDateDisp: self.selectedCodeA5_16(),
            //                    overtimePre: self.selectedIdA5_18(),
            //                    hdPre: self.selectedIdA5_19(),
            //                    msgAdvance: self.selectedIdA5_20(),
            //                    overtimePerfom: self.selectedIdA5_21(),
            //                    hdPerform: self.selectedIdA5_22(),
            //                    msgExceeded: self.selectedIdA5_23(),
            //                    scheduleCon: self.selectedIdA5_24(),
            //                    achiveCon: self.selectedIdA5_25(),
            //
            //                };
            // data.appCommon = {
            //     companyId: self.companyId(),
            //     showWkpNameBelong: self.selectedIdA10_3(),
            //
            // };
            // data.proxy = {
            //     companyId: self.companyId(),
            //     appType: ko.toJS(self.listDataA13())
            //     //todo A13_4
            // };
            // data.mailHd = {
            //     companyId: self.companyId(),
            //     subject: self.texteditorA16_7.value(),
            //     content: self.texteditorA16_8.value()
            //
            // };
            // data.mailOt = {
            //     companyId: self.companyId(),
            //     subject: self.texteditorA16_9.value(),
            //     content: self.texteditorA16_10.value()
            //
            // };
            // data.appTemp = {
            //     companyId: self.companyId(),
            //     content: self.texteditorA16_11.value()
            //
            // };
            // data.appliSet = {
            //     companyId: self.companyId(),
            //     baseDateFlg: self.baseDateFlg(),
            //     advanceExcessMessDispAtr: self.selectedIdA5_20(),
            //     hwAdvanceDispAtr: self.selectedIdA5_19(),
            //     hwActualDispAtr: self.selectedIdA5_22(),
            //     actualExcessMessDispAtr: self.selectedIdA5_23(),
            //     otAdvanceDispAtr: self.selectedIdA5_18(),
            //     otActualDispAtr: self.selectedIdA5_21(),
            //     warningDateDispAtr: self.selectedCodeA5_16(),
            //     appReasonDispAtr: self.selectedIdA5_14(),
            //     // r
            //     scheReflectFlg: self.selectedIdR1_4(),
            //     classScheAchi: self.selectedIdR1_7(),
            //     scheduleConfirmedAtr: self.selectedIdR1_9(),
            //     achievementConfirmedAtr: self.selectedIdR1_11(),
            //     reflecTimeofSche: self.selectedIdR3_6(),
            //     priorityTimeReflectFlg: self.selectedIdR3_8(),
            //     attendentTimeReflectFlg: self.selectedIdR3_10(),
            //
            //     appContentChangeFlg: self.selectedIdA17_4(),
            //     appActMonthConfirmFlg: self.selectedIdA11_8(),
            //     appOvertimeNightFlg: self.selectedIdA11_9(),
            //     appActLockFlg: self.selectedIdA11_10(),
            //     appEndWorkFlg: self.selectedIdA11_11(),
            //     requireAppReasonFlg: self.selectedIdA11_12(),
            //     appActConfirmFlg: self.selectedIdA11_13(),
            //     displayPrePostFlg: self.selectedIdA12_5(),
            //     displaySearchTimeFlg: self.selectedIdA12_6(),
            //     manualSendMailAtr: self.selectedIdA12_7(),
            //     companyUnit: self.companyUnit(),
            //     workplaceUnit: self.workplaceUnit(),
            //     employeeUnit: self.employeeUnit()
            //
            //     //todo wait -check
            // };
            // data.appName = ko.toJS(self.listDataA6());
            // data.stampReq = {
            //     companyId: self.companyId(),
            //     supFrameDispNO: self.selectedCodeJ18(),
            //     resultDisp: self.selectedIdJ19(),
            //     stampAtr_Work_Disp: self.selectedIdJ20(),
            //     stampAtr_GoOut_Disp: self.selectedIdJ21(),
            //     stampAtr_Care_Disp: self.selectedIdJ22(),
            //     stampAtr_Sup_Disp: self.selectedIdJ23(),
            //     stampAtr_Child_Care_Disp: self.selectedIdJ24(),
            //     stampGoOutAtr_Private_Disp: self.selectedIdJ25(),
            //     stampGoOutAtr_Public_Disp: self.selectedIdJ26(),
            //     stampGoOutAtr_Compensation_Disp: self.selectedIdJ27(),
            //     stampGoOutAtr_Union_Disp: self.selectedIdJ28(),
            //     topComment: self.texteditorJ29.value(),
            //     bottomComment: self.texteditorJ32.value(),
            //     topCommentFontColor: self.valueJ30(),
            //     topCommentFontWeight: self.enableJ31() ? 1 : 0,
            //     bottomCommentFontColor: self.valueJ30_1(),
            //     bottomCommentFontWeight: self.enableJ31_1() ? 1 : 0
            // };
            // data.goBack = {
            //     companyId: self.companyId(),
            //     workType: self.selectedIdF10(),
            //     performanceDisplayAtr: self.selectedIdF11(),
            //     contraditionCheckAtr: self.selectedIdF12(),
            //     workChangeFlg: self.selectedValueF13(),
            //     //r
            //     workChangeTimeAtr: self.selectedIdR3_4(),
            //     lateLeaveEarlySetAtr: self.selectedIdF14(),
            //     commentContent1: self.texteditorF15.value(),
            //     commentFontColor1: self.valueF15_1(),
            //     commentFontWeight1: (self.enableF15_2() ? 1 : 0),
            //     commentContent2: self.texteditorF16.value(),
            //     commentFontColor2: self.valueF16_1(),
            //     commentFontWeight2: (self.enableF16_1() ? 1 : 0),
            //
            // };
            // data.appOt = {
            //     cid: self.companyId(),
            //     workTypeChangeFlag: self.selectedIdB4(),
            //     flexJExcessUseSetAtr: self.selectedIdB6(),
            //     priorityStampSetAtr: self.selectedIdB28(),
            //     //                    flexExcessUseSetAtr: self.selectedIdB18(),
            //     //                    priorityStampSetAtr: self.selectedIdB19(),
            //     //r
            //     preTypeSiftReflectFlg: self.selectedIdR2_8(),
            //     preOvertimeReflectFlg: self.selectedIdR2_11(),
            //     postTypeSiftReflectFlg: self.selectedIdR2_15(),
            //     postWorktimeReflectFlg: self.selectedIdR2_18(),
            //     postBreakReflectFlg: self.selectedIdR2_21(),
            //     restAtr: self.selectedCodeR2_22(),
            //     //                    calendarDispAtr: self.selectedIdB31(),
            //     //                    instructExcessOtAtr: self.selectedIdB32(),
            //     //                    unitAssignmentOvertime: self.selectedCodeB33(),
            //     //                    useOt: self.selectedIdB34(),
            //     //                    earlyOverTimeUseAtr: self.selectedIdB35(),
            //     //                    normalOvertimeUseAtr: self.selectedIdB36(),
            // };
            // data.otRest = {
            //     appType: 0,
            //     bonusTimeDisplayAtr: self.selectedIdB8(),
            //     divergenceReasonFormAtr: self.selectedIdB10(),
            //     divergenceReasonInputAtr: self.selectedIdB12(),
            //     performanceDisplayAtr: self.selectedIdB15(),
            //     preDisplayAtr: self.selectedIdB17(),
            //     calculationOvertimeDisplayAtr: self.selectedIdB19(),
            //     extratimeDisplayAtr: self.selectedIdB21(),
            //     preExcessDisplaySetting: self.selectedIdB24(),
            //     performanceExcessAtr: self.selectedIdB26(),
            //     extratimeExcessAtr: self.selectedIdB30(),
            //     appDateContradictionAtr: self.selectedIdB32(),
            // }
            //
            // data.hdSet = {
            //     companyId: self.companyId(),
            //     displayUnselect: self.selectedIdC51(),
            //     changeWrkHour: self.selectedIdC48(),
            //     useYear: self.selectedIdC38(),
            //     use60h: self.selectedIdC39(),
            //     useGener: self.selectedIdC40(),
            //     wrkHours: self.selectedIdC27(),
            //     actualDisp: self.selectedIdC28(),
            //     appDateContra: self.selectedIdC29(),
            //     concheckOutLegal: self.selectedIdC30(),
            //     concheckDateRelease: self.selectedIdC31(),
            //     ckuperLimit: self.selectedIdC32(),
            //     regisNumYear: self.selectedIdC33(),
            //     regisShortLostHd: self.selectedIdC34(),
            //     regisShortReser: self.selectedIdC35(),
            //     regisLackPubHd: self.selectedIdC36(),
            //     regisInsuff: self.selectedIdC37(),
            //     pridigCheck: self.selectedIdC49(),
            //     yearHdName: self.texteditorC41.value(),
            //     obstacleName: self.texteditorC42.value(),
            //     absenteeism: self.texteditorC43.value(),
            //     specialVaca: self.texteditorC44.value(),
            //     yearResig: self.texteditorC45.value(),
            //     hdName: self.texteditorC46.value(),
            //     timeDigest: self.texteditorC47.value(),
            //     furikyuName: self.texteditorC51.value(),
            //     dayDispSet: self.selectedIdC182()
            // };
            // data.appChange = {
            //     cid: self.companyId(),
            //     initDisplayWorktime: self.selectedIdD15(),
            //     workChangeTimeAtr: self.selectedIdD13(),
            //     excludeHoliday: self.selectedIdD16(),
            //     commentContent1: self.texteditorD9.value(),
            //     commentFontColor1: self.valueD10(),
            //     commentFontWeight1: self.enableD11() ? 1 : 0,
            //     commentContent2: self.texteditorD12.value(),
            //     commentFontColor2: self.valueD10_1(),
            //     displayResultAtr: self.selectedIdD8(),
            //     commentFontWeight2: self.enableD11_1() ? 1 : 0,
            //
            //
            //
            // };
            // data.tripReq = {
            //     companyId: self.companyId(),
            //     workType: self.selectedIdE9(),
            //     contractCheck: self.selectedIdE10(),
            //     workChange: self.selectedValueE11(),
            //     workChangeTime: self.checkedE11_5() ? 1 : 0,
            //     lateLeave: self.selectedIdE12(),
            //     comment1: self.texteditorE13.value(),
            //     comment2: self.texteditorE16.value(),
            //     color1: self.valueE14(),
            //     weight1: self.enableE15() ? 1 : 0,
            //     color2: self.valueE17(),
            //     weight2: self.enableE18() ? 1 : 0
            // };
            // //g
            // data.wdApp = {
            //     companyId: self.companyId(),
            //     typePaidLeave: self.selectedIdG4(),
            //     workChange: self.selectedIdG6(),
            //     timeInit: self.selectedIdG8(),
            //     checkHdTime: self.selectedIdG26(),
            //     calStampMiss: self.selectedIdG28(),
            //     overrideSet: self.selectedIdG34(),
            //     checkOut: self.selectedIdG40(),
            //     //r
            //     restTime: self.selectedIdR4_6(),
            //     workTime: self.selectedIdR4_10(),
            //     breakTime: self.selectedIdR4_13(),
            // };
            // //g
            // data.otRestApp7 = {
            //     appType: 6,
            //     bonusTimeDisplayAtr: self.selectedIdG10(),
            //     divergenceReasonFormAtr: self.selectedIdG12(),
            //     divergenceReasonInputAtr: self.selectedIdG14(),
            //
            //     performanceDisplayAtr: self.selectedIdG17(),
            //     preDisplayAtr: self.selectedIdG19(),
            //     calculationOvertimeDisplayAtr: self.selectedIdG21(),
            //     extratimeDisplayAtr: self.selectedIdG23(),
            //
            //     preExcessDisplaySetting: self.selectedIdG30(),
            //     performanceExcessAtr: self.selectedIdG32(),
            //     extratimeExcessAtr: self.selectedIdG36(),
            //     appDateContradictionAtr: self.selectedIdG38(),
            // }
            //
            // data.timeHd = {
            //     companyId: self.companyId(),
            //     actualDisp: self.selectedIdH15(),
            //     checkOver: self.selectedIdH16(),
            //     checkDay: self.selectedIdH17(),
            //     useTimeYear: self.selectedIdH18(),
            //     use60h: self.selectedIdH19(),
            //     useTimeHd: self.selectedIdH20(),
            //     useBefore: (self.enableH21() ? 1 : 0),
            //     nameBefore: self.texteditorH22.value(),
            //     useAfter: (self.enableH23() ? 1 : 0),
            //     nameAfter: self.texteditorH23_1.value(),
            //     useAttend2: (self.enableH24() ? 1 : 0),
            //     nameBefore2: self.texteditorH24_1.value(),
            //     useAfter2: (self.enableH25() ? 1 : 0),
            //     nameAfter2: self.texteditorH25_1.value(),
            //     usePrivate: (self.enableH26() ? 1 : 0),
            //     privateName: self.texteditorH26_1.value(),
            //     unionLeave: (self.enableH27() ? 1 : 0),
            //     unionName: self.texteditorH27_1.value()
            //
            // };
            // data.wdReq = {
            //     companyId: self.companyId(),
            //     deferredWorkTimeSelect: self.selectedIdK12(),
            //     simulAppliReq: self.selectedIdK13(),
            //     lettleSuperLeave: self.selectedIdK14(),
            //     useAtr: self.selectedIdK15(),
            //     checkUpLimitHalfDayHD: self.selectedIdK16(),
            //     deferredComment: self.texteditorK17.value(),
            //     deferredLettleColor: self.valueK18() == null ? '' : self.valueK18(),
            //     deferredBold: self.enableK19() ? 1 : 0,
            //     pickUpComment: self.texteditorK20.value(),
            //     pickUpLettleColor: self.valueK23() == null ? '' : self.valueK23(),
            //     pickUpBold: self.enableK24() ? 1 : 0,
            //     permissionDivision: self.selectedIdK21(),
            //     appliDateContrac: self.selectedIdK22(),
            //
            // };
            // data.lateEarly = {
            //     companyId: self.companyId(),
            //     showResult: self.selectedIdI4()
            //
            // };
            // let dataA8 = _.filter(self.listDataA8(), function(o) { return !o.flg(); });
            // let dataA8_10 = _.filter(self.listDataA8(), function(o) { return o.flg(); });
            // data.appBf = {
            //     beforeAfter: _.map(ko.toJS(self.listDataA7()), (x: any) => {
            //         x.retrictPostAllowFutureFlg = x.retrictPostAllowFutureFlg ? 1 : 0;
            //         x.retrictPreUseFlg = x.retrictPreUseFlg ? 1 : 0;
            //         return x;
            //     }),
            //
            //     appType: _.map(ko.toJS(dataA8), (x: any) => {
            //         x.displayFixedReason = x.displayFixedReason ? 1 : 0;
            //         x.displayAppReason = x.displayAppReason ? 1 : 0;
            //         x.sendMailWhenRegister = x.sendMailWhenRegister ? 1 : 0;
            //         x.sendMailWhenApproval = x.sendMailWhenApproval ? 1 : 0;
            //         x.canClassificationChange = x.canClassificationChange ? 1 : 0;
            //         return x;
            //     })
            // };
            // data.dplReasonCmd = {
            //     listCmd :
            //         _.map(ko.toJS(dataA8_10), (x: any) => {
            //             x.displayFixedReason = x.displayFixedReason ? 1 : 0;
            //             x.displayAppReason = x.displayAppReason ? 1 : 0;
            //             return x;
            //         })
            // }
            // data.jobAssign = {
            //     isConcurrently: self.selectedIdA14_3() ? 1 : 0
            // };
            // data.approvalSet = {
            //     prinFlg: self.selectedIdA17_5()
            // };
            // data.jobSearch = ko.toJS(self.listDataA15());
            // data.contentMail = {
            //     mailTitle: self.texteditorA16_14.value(),
            //     mailBody: self.texteditorA16_15.value()
            // }
            // data.url = {
            //     urlEmbedded: self.selectedIdA16_17()
            // }
            //
            // if (nts.uk.ui.errors.hasError() === false) {
            //     nts.uk.ui.block.grayout();
            //     service.update(data).done(() => {
            //         nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
            //             //Load data setting
            //             self.loadData();
            //         });
            //     }).always(() => {
            //         nts.uk.ui.block.clear();
            //     });
            //     $('.tab-content-1').scrollTop(postion);
            // }
        }
    }

    interface NtsTabPanelModel {
        id: string;
        title: string;
        content: string;
        enable: KnockoutObservable<boolean>;
        visible: KnockoutObservable<boolean>;
    }
}