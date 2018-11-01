module nts.uk.pr.view.qmm031.d.viewmodel {
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import block = nts.uk.ui.block;
    import errors = nts.uk.ui.errors;
    import dialog = nts.uk.ui.dialog;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {

        lstInsuranceType: KnockoutObservableArray<IInsuranceType> = ko.observableArray([]);
        currentCode: KnockoutObservable<string> = ko.observable('');
        currentInsuranceType: KnockoutObservable<IInsuranceType> = ko.observable(null);

        isNewMode: KnockoutObservable<boolean> = ko.observable(true);
        enableInsuranceTypeCode: KnockoutObservable<boolean> = ko.observable(true);

        insuranceTypeCode: KnockoutObservable<string> = ko.observable(null);
        insuranceTypeName: KnockoutObservable<string> = ko.observable(null);

        viewCode: KnockoutObservable<string> = ko.observable("viewCode");
        viewName: KnockoutObservable<string> = ko.observable("viewName");

        atrOfInsuranceType: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        selectAtrOfInsuranceType: KnockoutObservable<number> = ko.observable(0);
        constructor() {
            let self = this,
                dfd = $.Deferred();
            self.atrOfInsuranceType(getAtrOfInsuranceType());
        }

        createInsuranceType() {
        }

        saveInsuranceType() {
        }

        deleteInsuranceType() {
        }
        close() {
            let self = this;

            nts.uk.ui.windows.close();
        }
    }
    export interface IInsuranceType {
        lifeInsuranceCode: string;
        insuranceTypeCode: string;
        insuranceTypeName: string;
        atrOfInsuranceType: number;
    }
    export class ItemModel{
        code:number;
        name:string;

        constructor(code:number,name:string){
            this.code=code;
            this.name=name;
        }
    }

    export enum AtrOfInsuranceType {
        GENERAL_LIFE_INSURANCE = 0,
        NURSING_MEDICAL_INSURANCE = 1,
        INDIVIDUAL_ANNUITY_INSURANCE = 2
    }

    export function getAtrOfInsuranceType(): Array<ItemModel> {
        return [
            new ItemModel(AtrOfInsuranceType.GENERAL_LIFE_INSURANCE.toString(), getText('enum_GENERAL_LIFE_INSURANCE')),
            new ItemModel(AtrOfInsuranceType.NURSING_MEDICAL_INSURANCE.toString(), getText('enum_NURSING_MEDICAL_INSURANCE')),
            new ItemModel(AtrOfInsuranceType.INDIVIDUAL_ANNUITY_INSURANCE.toString(), getText('enum_INDIVIDUAL_ANNUITY_INSURANCE'))
        ];
    }
}