module nts.uk.at.view.kaf011.a.screenModel {

    import dialog = nts.uk.ui.dialog.info;
    import text = nts.uk.resource.getText;
    import formatDate = nts.uk.time.formatDate;
    import common = nts.uk.at.view.kaf011.shr.common;
    import service = nts.uk.at.view.kaf011.shr.service;
    import block = nts.uk.ui.block;

    export class ViewModel {
        screenModeNew: KnockoutObservable<boolean> = ko.observable(true);
        prePostTypes = ko.observableArray([
            { code: 0, text: text('KAF011_14') },
            { code: 1, text: text('KAF011_15') }]);

        prePostSelectedCode: KnockoutObservable<number> = ko.observable(0);

        appComItems = ko.observableArray([
            { code: 0, text: text('KAF011_19') },
            { code: 1, text: text('KAF011_20') },
            { code: 2, text: text('KAF011_21') },
        ]);
        appComSelectedCode: KnockoutObservable<number> = ko.observable(0);

        appDate: KnockoutObservable<String> = ko.observable(formatDate(moment().toDate(), "yyyy/MM/dd").format());

        recWk: KnockoutObservable<common.WorkItems> = ko.observable(new common.WorkItems());

        absWk: KnockoutObservable<common.WorkItems> = ko.observable(new common.WorkItems());

        appReasons = ko.observableArray([]);

        appReasonSelectedID: KnockoutObservable<string> = ko.observable('');

        reason: KnockoutObservable<string> = ko.observable('');

        kaf000_a = new kaf000.a.viewmodel.ScreenModel();

        employeeID: KnockoutObservable<string> = ko.observable('');

        employeeName: KnockoutObservable<string> = ko.observable('');

        manualSendMailAtr: KnockoutObservable<number> = ko.observable(0);

        comment: KnockoutObservable<common.Comment> = ko.observable(new common.Comment(null));

        showReason: KnockoutObservable<boolean> = ko.observable(true);

        constructor() {
            let self = this;
            self.recWk().appDate.subscribe((newDate) => {
                self.changeDate();
            });
            self.absWk().appDate.subscribe((newDate) => {
                self.changeDate();
            });
        }
        changeDate() {
            block.invisible();
            let vm: ViewModel = __viewContext['viewModel'],
                changeDateParam = {
                    holidayDate: vm.absWk().appDate(),
                    takingOutDate: vm.recWk().appDate(),
                    comType: vm.appComSelectedCode(),
                    uiType: 1

                }
            service.changeDay(changeDateParam).done((data) => {
                vm.employeeID(data.employeeID);
            }).always(() => {
                block.clear();

            });;
        }

        start(): JQueryPromise<any> {
            block.invisible();
            var self = this,
                dfd = $.Deferred(),
                startParam = {
                    sID: null,
                    appDate: self.appDate(),
                    uiType: 1
                };

            service.start(startParam).done((data: common.IHolidayShipment) => {
                self.setDataFromStart(data);


            }).fail((error) => {
                dialog({ messageId: error.messageId });
            }).always(() => {
                block.clear();
                dfd.resolve();

            });
            return dfd.promise();
        }

        setDataFromStart(data: common.IHolidayShipment) {
            let self = this;
            if (data) {
                self.employeeName(data.employeeName);
                self.prePostSelectedCode(data.preOrPostType);
                self.recWk().wkTypes(data.recWkTypes || []);
                self.absWk().wkTypes(data.absWkTypes || []);
                self.appReasons(data.appReasons || []);
                self.employeeID(data.employeeID);
                self.manualSendMailAtr(data.applicationSetting.manualSendMailAtr);
                self.comment(data.drawalReqSet || null);
                self.showReason(data.applicationSetting.appReasonDispAtr);
            }
        }

        register() {
            block.invisible();
            let self = this,
                saveCmd: common.ISaveHolidayShipmentCommand = {
                    recCmd: ko.mapping.toJS(self.recWk()),
                    absCmd: ko.mapping.toJS(self.absWk()),
                    comType: self.appComSelectedCode(),
                    usedDays: 1,
                    appCmd: {
                        appReasonID: self.appReasonSelectedID(),
                        applicationReason: self.reason(),
                        prePostAtr: self.prePostSelectedCode(),
                        enteredPersonSID: self.employeeID(),
                    }
                };

            saveCmd.absCmd.changeWorkHoursType = saveCmd.absCmd.changeWorkHoursType ? 1 : 0;

            service.save(saveCmd).done(() => {
                dialog({ messageId: 'Msg_15' });


            }).fail((error) => {
                dialog({ messageId: error.messageId });

            }).always(() => {
                block.clear();
            });
        }

        openKDL009() {
            //chưa có màn hình KDL009
            //            nts.uk.ui.windows.sub.modal('/view/kdl/009/a/index.xhtml').onClosed(function(): any {
            //
            //            });

        }


    }





}