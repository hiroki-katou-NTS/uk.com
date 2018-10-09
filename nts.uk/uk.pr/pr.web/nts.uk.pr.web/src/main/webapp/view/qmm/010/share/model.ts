module nts.uk.pr.view.qmm010.share.model {

    export enum SCREEN_MODE {
        NEW = 0,
        UPDATE = 1
    }

    // 社会保険事業所
    export interface ISocialInsuranceOffice {
        socialInsuranceCode: string;
        socialInsuranceName: string;
        companyID: string
        // 基本情報
        representativePosition: string;
        notes: string;
        //社会保険事業所住所
        address1: string;
        address2: string;
        addressKana1: string;
        addressKana2: string;
        phoneNumber: string;
        postalCode: string;
        representativeName: string;
        abbreviatedName: string;
        insuranceMasterInfomation: string;
    }

    // 社会保険事業所
    export class SocialInsuranceOffice {
        socialInsuranceCode: KnockoutObservable<string> = ko.observable(null);
        socialInsuranceName: KnockoutObservable<string> = ko.observable(null);
        insuranceMasterInfomation: KnockoutObservable<string> = ko.observable(null);
        companyID: KnockoutObservable<string> = ko.observable(null);
        // 基本情報
        representativePosition: KnockoutObservable<string> = ko.observable(null);
        notes: KnockoutObservable<string> = ko.observable(null);
        //社会保険事業所住所
        address1: KnockoutObservable<string> = ko.observable(null);
        address2: KnockoutObservable<string> = ko.observable(null);
        addressKana1: KnockoutObservable<string> = ko.observable(null);
        addressKana2: KnockoutObservable<string> = ko.observable(null);
        phoneNumber: KnockoutObservable<string> = ko.observable(null);
        postalCode: KnockoutObservable<string> = ko.observable(null);
        representativeName: KnockoutObservable<string> = ko.observable(null);
        abbreviatedName: KnockoutObservable<string> = ko.observable(null);

        constructor(params: ISocialInsuranceOffice) {
            this.socialInsuranceCode(params ? params.socialInsuranceCode : null);
            this.socialInsuranceName(params ? params.socialInsuranceName : null);
            this.companyID(params ? params.companyID : null);
            this.representativePosition(params ? params.representativePosition : null);
            this.notes(params ? params.notes : null);
            this.address1(params ? params.address1 : null);
            this.address2(params ? params.address2 : null);
            this.addressKana1(params ? params.addressKana1 : null);
            this.addressKana2(params ? params.addressKana2 : null);
            this.phoneNumber(params ? params.phoneNumber : null);
            this.postalCode(params ? params.postalCode : null);
            this.representativeName(params ? params.representativeName : null);
            this.abbreviatedName(params ? params.abbreviatedName : null);
            this.insuranceMasterInfomation(params ? params.insuranceMasterInfomation : null);
        }
    }

    // 労働保険事業所
    export interface ILaborInsuranceOffice {
        companyId: string;
        laborOfficeCode: string;
        laborOfficeName: string;
        notes: string;
        representativePosition: string;
        representativeName: string;
        address1: string;
        address2: string;
        addressKana1: string;
        addressKana2: string;
        phoneNumber: string;
        postalCode: string;
        employmentOfficeCode: string;
        employmentOfficeNumber1: number;
        employmentOfficeNumber2: number;
        employmentOfficeNumber3: number;
        cityCode: string;
    }
    // 労働保険事業所
    export class LaborInsuranceOffice {
        companyId: KnockoutObservable<string> = ko.observable(null);
        laborOfficeCode: KnockoutObservable<string> = ko.observable(null);
        laborOfficeName: KnockoutObservable<string> = ko.observable(null);
        notes: KnockoutObservable<string> = ko.observable(null);
        representativePosition: KnockoutObservable<string> = ko.observable(null);
        representativeName: KnockoutObservable<string> = ko.observable(null);
        address1: KnockoutObservable<string> = ko.observable(null);
        address2: KnockoutObservable<string> = ko.observable(null);
        addressKana1: KnockoutObservable<string> = ko.observable(null);
        addressKana2: KnockoutObservable<string> = ko.observable(null);
        phoneNumber: KnockoutObservable<string> = ko.observable(null);
        postalCode: KnockoutObservable<string> = ko.observable(null);
        employmentOfficeCode: KnockoutObservable<string> = ko.observable(null);
        employmentOfficeNumber1: KnockoutObservable<number> = ko.observable(null);
        employmentOfficeNumber2: KnockoutObservable<number> = ko.observable(null);
        employmentOfficeNumber3: KnockoutObservable<number> = ko.observable(null);
        cityCode: KnockoutObservable<string> = ko.observable(null);
        constructor(params: ILaborInsuranceOffice) {
            this.companyId(params ? params.companyId : null);
            this.laborOfficeCode(params ? params.laborOfficeCode : null);
            this.laborOfficeName(params ? params.laborOfficeName : null);
            this.notes(params ? params.notes : null);
            this.representativePosition(params ? params.representativePosition : null);
            this.representativeName(params ? params.representativeName : null);
            this.address1(params ? params.address1 : null);
            this.address2(params ? params.address2 : null);
            this.addressKana1(params ? params.addressKana1 : null);
            this.addressKana2(params ? params.addressKana2 : null);
            this.phoneNumber(params ? params.phoneNumber : null);
            this.postalCode(params ? params.postalCode : null);
            this.employmentOfficeCode(params ? params.employmentOfficeCode : null);
            this.employmentOfficeNumber1(params ? params.employmentOfficeNumber1 : null);
            this.employmentOfficeNumber2(params ? params.employmentOfficeNumber2 : null);
            this.employmentOfficeNumber3(params ? params.employmentOfficeNumber3 : null);
            this.cityCode(params ? params.cityCode : null);
        }
    }


}