module nts.uk.at.view.kdl049.a.viewmodel {

    import invisible = nts.uk.ui.block.invisible;
    import alertError = nts.uk.ui.dialog.alertError;
    import clear = nts.uk.ui.block.clear;
    import getText = nts.uk.resource.getText;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    export class ScreenModel {

        targetDate: KnockoutObservable<string> = ko.observable("");
        comEventName: KnockoutObservable<string> = ko.observable("");
        isSuperior: KnockoutObservable<boolean> = ko.observable(false);
        targetWorkplace: KnockoutObservable<string> = ko.observable("");
        wplEventName: KnockoutObservable<string> = ko.observable("");
        workPlaceID: KnockoutObservable<string> = ko.observable("");
        constructor() {
            var self = this;
            let param = getShared("KDL049");
        }

        startPage(): JQueryPromise<any> {
            var self = this;

            let data = nts.uk.ui.windows.getShared('KDL049');
            if (data.workplace != null && data.workplace.workPlaceID != 0) {
                self.targetWorkplace(data.workplace.targetOrgWorkplaceName);
                self.workPlaceID(data.workplace.workPlaceID);
            }
            else {
                self.targetWorkplace(null);
                self.workPlaceID(null);
            }
            if(__viewContext.user.role.isInCharge.attendance == true){
                self.isSuperior(true);
                 $("#A22").focus();
                }
            // self.targetWorkplace("dea95de1-a462-4028-ad3a-d68b8f180412");
            let dfd = $.Deferred();
            let param = {
                //                workplaceID : "dea95de1-a462-4028-ad3a-d68b8f180412",
                //                targetDate : "2020-11-07T00:00:00.000Z"
                workplaceID: self.workPlaceID(),
                targetDate: data.dateSelected
            }
            service.startUp(param).done(function(result) {
                self.targetDate(data.dateSelected);
                self.comEventName(result.optComEvent.name);
                self.wplEventName(result.optWorkplaceEvent.name);
                console.log(result);
            }).fail(function(res) {
                alertError(res.message);

            });

            dfd.resolve();

            return dfd.promise();
        }

        
        submit() {
            var self = this;
            //            var selectWorkLocation = _.find(self.workLocationList(), ['workLocationCD', self.selectCode()]);
            //            if (selectWorkLocation !== undefined) {
            //                nts.uk.ui.windows.setShared("KDL010workLocation", selectWorkLocation.workLocationCD);
            //            }
            //            else {
            //                nts.uk.ui.windows.setShared("KDL010workLocation", null, true);
            //            }
            let param = {
                targetDate: self.targetDate(),
                workPlaceID: self.workPlaceID(),
                eventComName: self.comEventName(),
                eventWorkplaceName: self.wplEventName()
            }
            service.addEvent(param).done(() => {

               
                let dataShare = {
                    targetDate: self.targetDate(),
                    workPlaceID: self.workPlaceID(),
                    eventComName: self.comEventName(),
                    eventWorkplaceName: self.wplEventName()
                }
                nts.uk.ui.windows.setShared('DataKDL049', dataShare);
                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                    nts.uk.ui.windows.close();
                });
            }).fail((res) => {

            });


        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }
    }
}