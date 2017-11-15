
module nts.uk.at.view.kdw001 {
    import shareModel = nts.uk.at.view.kdw001.share.model;
    export module e.test.viewmodel {
        export class ScreenModel {

            constructor() {
                var self = this;
            }

            sendData() {
                var param: shareModel.executionProcessingCommand = new shareModel.executionProcessingCommand();
                var paramA: shareModel.paramScreenA = { closure: 1 };
                var paramC: shareModel.paramScreenC = {
                    lstEmployeeID: ["000000000001", "000000000002", "000000000003", "000000000004", "000000000005", "000000000006", "000000000007", "000000000008", "000000000009"],
                    periodStartDate: "2017/11/08",
                    periodEndDate: "2017/11/14"
                };
                var paramB: shareModel.paramScreenB = {
                    dailyCreation: true,
                    creationType: 1,
                    resetClass: 1,
                    calClassReset: true,
                    masterReconfiguration: true,
                    specDateClassReset: true,
                    resetTimeForChildOrNurseCare: true,
                    refNumberFingerCheck: true,
                    closedHolidays: true,
                    resettingWorkingHours: true,
                    resetTimeForAssig: true,
                    dailyCalClass: true,
                    calClass: 1,
                    refApprovalresult: true,
                    refClass: 1,
                    alsoForciblyReflectEvenIfItIsConfirmed: true,
                    monthlyAggregation: true,
                    summaryClass: 1
                };
                var paramJ: shareModel.paramScreenJ = {
                    caseSpecExeContentID: "test"
                };

                param.setParamsScreenA(paramA);
                param.setParamsScreenC(paramC);
                param.setParamsScreenB(paramB);

                nts.uk.ui.windows.setShared("KDWL001E", param);
                nts.uk.ui.windows.sub.modal("/view/kdw/001/e/index.xhtml");
            }

        }
    }
}