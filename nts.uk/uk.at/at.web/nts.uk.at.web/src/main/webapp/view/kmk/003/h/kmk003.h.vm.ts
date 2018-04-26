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
            calcMethodFixed: KnockoutObservable<number>;
            calcMethodFluctuation: KnockoutObservable<number>;

            constructor() {
                let self = this;
                self.isCalcFromSchedule = ko.observable(false);
                self.isReferRestTime = ko.observable(false);
                self.usePrivateGoOutRest = ko.observable(false);
                self.useAssoGoOutRest = ko.observable(false);
                self.useStamp = ko.observable(false);
                self.useStampCalcMethod = ko.observable(0);
                self.timeManagerSetAtr = ko.observable(0);
                self.calcMethodFixed = ko.observable(0);
                self.calcMethodFluctuation = ko.observable(0);
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

            public setInitialFocus(): void {
                const focusIndex = $('#initial-focus-h');
                const focusIndex2 = $('#initial-focus-h2');
                if (focusIndex.length > 0) {
                    focusIndex.focus();
                } else {
                    focusIndex2.focus();
                }
            }

            /**
             * Binding data
             */
            private bindingData() {
                let self = this;
                let dto: DialogHParam = nts.uk.ui.windows.getShared("KMK003_DIALOG_H_INPUT");
                
                self.isCalcFromSchedule(dto.isCalcFromSchedule ? dto.isCalcFromSchedule : false);
                self.isReferRestTime(dto.isReferRestTime ? dto.isReferRestTime : false);
                self.usePrivateGoOutRest(dto.usePrivateGoOutRest ? dto.usePrivateGoOutRest : false);
                self.useAssoGoOutRest(dto.useAssoGoOutRest ? dto.useAssoGoOutRest : false);
                self.useStamp(dto.useStamp ? dto.useStamp : false);
                self.useStampCalcMethod(dto.useStampCalcMethod ? dto.useStampCalcMethod : 0);
                self.timeManagerSetAtr(dto.timeManagerSetAtr ? dto.timeManagerSetAtr : 0);
                self.calcMethodFixed(dto.calcMethodFixed ? dto.calcMethodFixed : 0);
                self.calcMethodFluctuation(dto.calcMethodFluctuation ? dto.calcMethodFluctuation : 0);

            }

            /**
             * Save
             */
            public save(): void {
                let self = this;

                let dto: DialogHParam = {
                    isCalcFromSchedule: self.isCalcFromSchedule(),
                    isReferRestTime: self.isReferRestTime(),
                    usePrivateGoOutRest: self.usePrivateGoOutRest(),
                    useAssoGoOutRest: self.useAssoGoOutRest(),
                    useStamp: self.useStamp(),
                    useStampCalcMethod: self.useStampCalcMethod(),
                    timeManagerSetAtr: self.timeManagerSetAtr(),
                    calcMethodFixed: self.calcMethodFixed(),
                    calcMethodFluctuation: self.calcMethodFluctuation()
                };

                nts.uk.ui.windows.setShared("KMK003_DIALOG_H_OUTPUT", dto);
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
            calcMethodFixed: number;
            calcMethodFluctuation: number;
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