module nts.uk.pr.view.qmm008.a {

    export module service {

        // Service paths.
        var servicePath = {

        };
        /**
        * Model namespace.
        */
        export module model {
            export module finder {
                export class ChooseOption{
                    code:string;
                    name:string;   
                }
                export class InsuranceOfficeItemDto {
                    id: string;
                    name: string;
                    code: string;
                    childs: any;
                    codeName: string;
                    constructor(id: string, name: string, code: string,childs: Array<InsuranceOfficeItemDto>) {
                        this.id = id;
                        this.name = name;
                        this.code = code;
                        this.childs=childs;
                        this.codeName=code+"\u00A0"+"\u00A0"+"\u00A0"+name;
                    }
                }
                export class HealthInsuranceRateDto {
                    historyId: number;
                    companyCode: string;
                    officeCode: string;
                    applyRange: string;
                    autoCalculate: number;
                    rateItems: Array<InsuranceRateItemDto>;
                    roundingMethods: Array<HealthInsuranceRoundingDto>;
                    maxAmount:number;
                }
                export class InsuranceRateItemDto {
                    chargeRate: number; //value of person or company
                    payType: PaymentType;
                    healthInsuranceType: HealthInsuranceType;
                }
                export class HealthInsuranceRoundingDto {
                    payType: PaymentType;
                    roundAtrs: RoundingItemDto;
                }
                export class HealthInsuranceAvgearnDto {
                    historyId: number;
                    levelCode: number;
                    companyAvg: HealthInsuranceAvgearnValueDto;
                    personalAvg: HealthInsuranceAvgearnValueDto;
                }

                export class RoundingItemDto {
                    companyRoundAtr: number;
                    personalRoundAtr: number;
                }
                export class HealthInsuranceAvgearnValueDto {
                    healthGeneralMny: number;
                    healthNursingMny: number;
                    healthBasicMny: number;
                    healthSpecificMny: number;
                }
            }


            export enum PaymentType {
                Salary = 0,
                Bonus = 1
            }

            export enum HealthInsuranceType {
                General = 0,
                Nursing = 1,
                Basic = 2,
                Special = 3
            }
            export enum InsuranceGender {
                Male = 0,
                Female = 1,
                Unknow = 2
            }
        }

    }
}
