module nts.uk.at.view.kmk010.a {
    export module service {
        var paths = {
            findAllOvertimeCalculationMethod: "ctx/at/shared/overtime/setting/findAll/method",
            findByIdOvertimeSetting: "ctx/at/shared/overtime/setting/findById",
            findAllClosureHistory: "ctx/at/shared/workrule/closure/history/findAll",
            findByIdClosure: "ctx/at/shared/workrule/closure/findById",
            saveClosure: "ctx/at/shared/workrule/closure/save",
            findByIdClosureHistory: "ctx/at/shared/workrule/closure/history/findById",
            saveClosureHistory: "ctx/at/shared/workrule/closure/history/save"
        }

        /**
         * find all data overtime calculation method
         */
        export function findAllOvertimeCalculationMethod(): JQueryPromise<model.EnumConstantDto[]> {
            return nts.uk.request.ajax(paths.findAllOvertimeCalculationMethod);
        }

        /**
         * find all data overtime calculation method
         */
        export function findByIdOvertimeSetting(): JQueryPromise<model.OvertimeSettingDto> {
            return nts.uk.request.ajax(paths.findByIdOvertimeSetting);
        }




        export module model {

            export interface EnumConstantDto {
                value: number;
                fieldName: string;
                localizedName: string;
            }

            export interface OvertimeDto {
                name: string;
                overtime: number;
                overtimeNo: number;
                useClassification: boolean;
            }

            export interface OvertimeBRDItemDto {
                useClassification: boolean;
                breakdownItemNo: number;
                name: string;
                productNumber: number;
            }
            
            export interface OvertimeSettingDto {
                note: string;
                calculationMethod: number;
                overtimes: OvertimeDto[];
                breakdownItems: OvertimeBRDItemDto[];
            }
        }
    }
}