module nts.uk.at.view.kaf011.a.screenModel {

    import dialog = nts.uk.ui.dialog.info;
    import text = nts.uk.resource.getText;
    import formatDate = nts.uk.time.formatDate;
    import common = nts.uk.at.view.kaf011.shr.common;
    import service = nts.uk.at.view.kaf011.shr.service;

    export class ViewModel {

        prePostTypes = ko.observableArray([
            { code: 0, text: text('KAF011_14') },
            { code: 1, text: text('KAF011_15') }]);

        prePostSelectedCode: KnockoutObservable<number> = ko.observable(0);

        appComItems = ko.observableArray([
            { code: 0, text: text('KAF011_19') },
            { code: 1, text: text('KAF011_20') },
            { code: 2, text: text('KAF011_21') },
        ]);
        appComSelectedCode: KnockoutObservable<number> = ko.observable(1);

        appDate: KnockoutObservable<String> = ko.observable(formatDate(moment().toDate(), "yyyy/MM/dd").format());

        takingOutWk: KnockoutObservable<common.WorkItems> = ko.observable(new common.WorkItems());

        holidayWk: KnockoutObservable<common.WorkItems> = ko.observable(new common.WorkItems());

        appReasons = ko.observableArray([]);

        appReasonSelectedType: KnockoutObservable<string> = ko.observable('');

        reason: KnockoutObservable<string> = ko.observable('');

        kaf000_a = new kaf000.a.viewmodel.ScreenModel();

        employeeID: KnockoutObservable<string> = ko.observable('');
        employeeName: KnockoutObservable<string> = ko.observable('');

        manualSendMailAtr: KnockoutObservable<number> = ko.observable(0);

        constructor() {
            let self = this;
            self.takingOutWk().appDate.subscribe((newDate) => {
                self.changeDate();
            });
            self.holidayWk().appDate.subscribe((newDate) => {
                self.changeDate();
            });
        }
        update() {

        }
        changeDate() {

            let vm: ViewModel = __viewContext['viewModel'],
                changeDateParam = {
                    holidayDate: vm.holidayWk().appDate(),
                    takingOutDate: vm.takingOutWk().appDate(),
                    comType: vm.appComSelectedCode(),
                    uiType: 1

                }
            service.changeDay(changeDateParam).done((data) => {
                vm.employeeID(data.employeeID);
            });
        }

        start(): JQueryPromise<any> {
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
                dfd.resolve();

            });
            return dfd.promise();
        }
        genWorkingText(childData: common.IWorkTime) {
            let self = this,
                result = childData.selectedWorkTimeCode + ' ' + childData.selectedWorkTimeName;
            if (childData.first) {
                result += ' ' + self.parseTime(childData.first.start) + '~' + self.parseTime(childData.first.end);
                if (childData.second) {

                }
            }
            return result;

        }
        parseTime(value) {
            return nts.uk.time.parseTime(value, true).format()
        }

        setDataFromStart(data: common.IHolidayShipment) {
            let self = this;
            if (data) {
                self.employeeName(data.employeeName);
                self.prePostSelectedCode(data.preOrPostType);
                self.takingOutWk().wkTypes(data.takingOutWkTypes || []);
                self.holidayWk().wkTypes(data.holidayWkTypes || []);
                self.appReasons(data.appReasons || []);
                self.employeeID(data.employeeID);
                self.manualSendMailAtr(data.applicationSetting.manualSendMailAtr);
            }
        }

        register() {
            let self = this,

                saveCmd: common.ISaveHolidayShipmentCommand = {
                    recCmd: ko.mapping.toJS(self.takingOutWk()),
                    absCmd: ko.mapping.toJS(self.holidayWk()),
                    comType: self.appComSelectedCode(),
                    usedDays: 1,
                    appCmd: {
                        appReasonID: self.appReasonSelectedType(),
                        applicationReason: self.reason(),
                        prePostAtr: self.prePostSelectedCode(),
                        enteredPersonSID: self.employeeID(),
                    }
                };

            saveCmd.absCmd.changeWorkHoursType = saveCmd.absCmd.changeWorkHoursType ? 1 : 0;

            service.save(saveCmd).done(() => {

            }).fail((error) => {
                dialog({ messageId: error.messageId });

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