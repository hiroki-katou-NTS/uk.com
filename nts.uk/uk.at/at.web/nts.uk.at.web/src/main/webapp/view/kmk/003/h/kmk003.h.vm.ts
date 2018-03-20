module nts.uk.at.view.kmk003.h {

    export module viewmodel {

        export class ScreenModel {

            isCalcFromSchedule: KnockoutObservable<boolean>;
            isReferRestTime: KnockoutObservable<boolean>;
            usePrivateGoOutRest: KnockoutObservable<boolean>;
            useAssoGoOutRest: KnockoutObservable<boolean>;
            useStamp: KnockoutObservable<boolean>;
            useStampCalcMethod: KnockoutObservable<number>;
            timeManagerSetAtr: KnockoutObservable<number>;
            calculationMethod: KnockoutObservable<number>;

            constructor() {
                let self = this;
                self.isCalcFromSchedule = ko.observable(false);
                self.isReferRestTime = ko.observable(false);
                self.usePrivateGoOutRest = ko.observable(false);
                self.useAssoGoOutRest = ko.observable(false);
                self.useStamp = ko.observable(false);
                self.useStampCalcMethod = ko.observable(0);
                self.timeManagerSetAtr = ko.observable(0);
                self.calculationMethod = ko.observable(0);
            }

            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred<any>();

                self.bindingData();

                dfd.resolve();
                return dfd.promise();
            }

            /**
             * Binding data
             */
            private bindingData() {
                let self = this;
                let dto: DialogHParam = nts.uk.ui.windows.getShared("KMK003_DIALOG_H_DTO");

                

                //TODO
            }

            /**
             * Save
             */
            public save(): void {
                let self = this;

                let dto: any = {
                    isCalcFromSchedule: self.isCalcFromSchedule(),
                    isReferRestTime: self.isReferRestTime(),
                    usePrivateGoOutRest: self.usePrivateGoOutRest(),
                    useAssoGoOutRest: self.useAssoGoOutRest(),
                    useStamp: self.useStamp(),
                    useStampCalcMethod: self.useStampCalcMethod(),
                    timeManagerSetAtr: self.timeManagerSetAtr(),
                    calculationMethod: self.calculationMethod()
                };

                nts.uk.ui.windows.setShared("KMK003_DIALOG_H_DTO", dto);
                self.close();
            }

            /**
             * Close
             */
            public close(): void {
                nts.uk.ui.windows.close();
            }

        }

        export interface DialogHParam {
            calculationMethod: number;
            isCalcFromSchedule: boolean;
            isReferRestTime: boolean;
            usePrivateGoOutRest: boolean;
            useAssoGoOutRest: boolean;
            useStamp: boolean;
            useStampCalcMethod: number;
            timeManagerSetAtr: number;
        }
    }
}