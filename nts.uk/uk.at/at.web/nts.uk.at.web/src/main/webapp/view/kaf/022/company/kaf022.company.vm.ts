module nts.uk.at.view.kaf022.company.viewmodel {
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;

    import ScreenModelA = nts.uk.at.view.kaf022.a.viewmodel.ScreenModelA;
    import ScreenModelB = nts.uk.at.view.kaf022.b.viewmodel.ScreenModelB;
    import ScreenModelC = nts.uk.at.view.kaf022.c.viewmodel.ScreenModelC;
    import ScreenModelD = nts.uk.at.view.kaf022.d.viewmodel.ScreenModelD;
    import ScreenModelE = nts.uk.at.view.kaf022.e.viewmodel.ScreenModelE;
    import ScreenModelF = nts.uk.at.view.kaf022.f.viewmodel.ScreenModelF;
    import ScreenModelG = nts.uk.at.view.kaf022.g.viewmodel.ScreenModelG;
    import ScreenModelH = nts.uk.at.view.kaf022.h.viewmodel.ScreenModelH;
    import ScreenModelI = nts.uk.at.view.kaf022.i.viewmodel.ScreenModelI;
    import ScreenModelJ = nts.uk.at.view.kaf022.j.viewmodel.ScreenModelJ;
    import ScreenModelV = nts.uk.at.view.kaf022.v.viewmodel.ScreenModelV;

    export class ScreenModel {
        tabs: KnockoutObservableArray<NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        viewmodelA: ScreenModelA;
        viewmodelB: ScreenModelB;
        viewmodelC: ScreenModelC;
        viewmodelD: ScreenModelD;
        viewmodelE: ScreenModelE;
        viewmodelF: ScreenModelF;
        viewmodelG: ScreenModelG;
        viewmodelH: ScreenModelH;
        viewmodelI: ScreenModelI;
        viewmodelJ: ScreenModelJ;
        viewmodelV: ScreenModelV;

        constructor() {
            const self = this;
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
            self.viewmodelC = new ScreenModelC();
            self.viewmodelD = new ScreenModelD();
            self.viewmodelE = new ScreenModelE();
            self.viewmodelF = new ScreenModelF();
            self.viewmodelG = new ScreenModelG();
            self.viewmodelH = new ScreenModelH();
            self.viewmodelI = new ScreenModelI();
            self.viewmodelJ = new ScreenModelJ();
            self.viewmodelV = new ScreenModelV();
        }

        start(): JQueryPromise<any> {
            const self = this;
            return self.loadData();
        }

        loadData(): JQueryPromise<any> {
            const dfd = $.Deferred();
            let self = this,
                position: number = $('.tab-content-1').scrollTop();
            nts.uk.ui.block.grayout();
            service.findAllData().done((data: any) => {
                self.viewmodelA.initData(data);
                self.viewmodelB.initData(data);
                self.viewmodelD.initData(data);
                self.viewmodelE.initData(data);
                self.viewmodelF.initData(data);
                self.viewmodelI.initData(data);
                self.viewmodelV.initData(data);
                dfd.resolve();
            }).fail(error => {
                dfd.reject();
                alert(error);
            }).always(() => {
                nts.uk.ui.errors.clearAll();
                nts.uk.ui.block.clear();
                $("#a4_6").focus();
                setTimeout(function() {
                    $('.tab-content-1').scrollTop(position);
                }, 1500);
            });
            return dfd.promise();
        }

        saveDataAt(): void {
            $('#a16_14').trigger("validate");
            $('#a16_15').trigger("validate");
            $('#a7_23').trigger("validate");
            $('#a7_23_2').trigger("validate");
            $('#a7_23_3').trigger("validate");
            if (nts.uk.ui.errors.hasError()) { return; }
            const self = this, postion: number = $('.tab-content-1').scrollTop();;
            const dataA = self.viewmodelA.collectData();
            const dataV = self.viewmodelV.collectData();
            const dataB = self.viewmodelB.collectData();
            const dataD = self.viewmodelD.collectData();
            const data: any = {};
            data["applicationSetting"] = {
                appLimitSetting: dataA.appLimitSetting,
                appTypeSettings: dataA.appTypeSettings,
                appSetForProxyApps: dataV,
                appDeadlineSettings: dataA.appDeadlineSettings,
                appDisplaySetting: dataA.appDisplaySetting,
                receptionRestrictionSettings: dataA.receptionRestrictionSettings,
                recordDate: dataA.recordDate
            };
            data["reasonDisplaySettings"] = dataA.reasonDisplaySettings;
            data["nightOvertimeReflectAtr"] = dataA.nightOvertimeReflectAtr;
            data["includeConcurrentPersonel"] = dataA.includeConcurrentPersonel;
            data["approvalByPersonAtr"] = dataA.approvalByPersonAtr;
            data["appReflectCondition"] = dataA.appReflectCondition;
            data["overtimeApplicationSetting"] = dataB.overtimeApplicationSetting;
            data["overtimeApplicationReflect"] = dataB.overtimeApplicationReflect;
            data["appWorkChangeSetting"] = dataD;
            data["goBackReflectAtr"] = self.viewmodelF.selectedValueF13();
            data["lateEarlyCancelAtr"] = self.viewmodelI.lateEarlyCancelAtr();
            data["lateEarlyClearAlarmAtr"] = self.viewmodelI.lateEarlyClearAlarmAtr();

            nts.uk.ui.block.grayout();
            service.update(data).done(() => {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                    //Load data setting
                    self.loadData();
                });
            }).always(() => {
                nts.uk.ui.block.clear();
            });
            $('.tab-content-1').scrollTop(postion);
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