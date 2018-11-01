module nts.uk.pr.view.qmm031.a.viewmodel {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import block = nts.uk.ui.block;
    import errors = nts.uk.ui.errors;
    import dialog = nts.uk.ui.dialog;

    export class ScreenModel {
        isNewMode: KnockoutObservable<boolean> = ko.observable(true);
        lstLifeInsurance: KnockoutObservableArray<ILifeInsurance> = ko.observableArray([]);
        currentLifeInsurance: KnockoutObservable<ILifeInsurance> = ko.observable([]);
        currentCodeLifeInsurance: KnockoutObservable<string> = ko.observable('');
        enableLifeInsuranceCode: KnockoutObservable<boolean> = ko.observable(true);


        lifeInsuranceCode: KnockoutObservable<string> = ko.observable(null);
        lifeInsuranceName: KnockoutObservable<string> = ko.observable(null);

        lstInsuranceType: KnockoutObservableArray<IInsuranceType> = ko.observableArray([]);
        currentCodeInsuranceType: KnockoutObservable<string> = ko.observable('');

        isNewMode2: KnockoutObservable<boolean> = ko.observable(true);
        lstEarthquakeInsurance: KnockoutObservableArray<IEarthquakeInsurance> = ko.observableArray([]);
        currentEarthquakeInsurance: KnockoutObservable<IEarthquakeInsurance> = ko.observable([]);
        currentCodeEarthquakeInsurance: KnockoutObservable<string> = ko.observable('');
        enableEarthquake: KnockoutObservable<boolean> = ko.observable(true);

        earthquakeInsuranceCode: KnockoutObservable<string> = ko.observable(null);
        earthquakeInsuranceName: KnockoutObservable<string> = ko.observable(null);

        constructor() {
        }

        onSelectTabA() {
            let self = this;
        };

        onSelectTabB() {
            let self = this;
        };

        newEarthquakeInsurance() {
        };

        saveEarthquakeInsurance() {
        };

        deleteEarthquakeInsurance() {
        };

        openD(){
            let self = this;

            nts.uk.ui.errors.clearAll();

            nts.uk.ui.windows.sub.modal('../d/index.xhtml').onClosed(() => {
               /* let data = getShared("QMM031_A_Params");

                if(data != null) {
                    let categoryAtr = parseInt(data, 10);
                    self.currentKey(null);
                    self.statementItemDataSelected().statementItem().categoryAtr(categoryAtr);
                }

                if(self.statementItemDataSelected().checkCreate()) {
                    $("#B3_2").focus();
                } else {
                    $("#B3_3").focus();
                }*/
            });

        };

        newLifeInsurance() {
        };

        saveLifeInsurance() {
        };

        pdf(){

        };

        correctionLog() {
        };

        earthquakeInsurance(){

        };

        deleteLifeInsurance() {
        };

    }
    export interface ILifeInsurance {
        lifeInsuranceCode: string;
        lifeInsuranceName: string;
    }

    export interface IInsuranceType {
        lifeInsuranceCode: string;
        insuranceTypeCode: string;
        insuranceTypeName: string;
        atrOfInsuranceType: number;
    }
    export interface IEarthquakeInsurance {
        earthquakeInsuranceCode: string;
        earthquakeInsuranceName: string;
    }
}