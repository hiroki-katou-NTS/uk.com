module nts.uk.pr.view.kmf001.b {
    export module service {
        /**
         *  Service paths
         */
        var paths: any = {
            updateAcquisitionRule: 'ctx/at/share/vacation/setting/acquisitionrule/update',
            findAcquisitionRule: 'ctx/at/share/vacation/setting/acquisitionrule/find',
            categoryEnum: "ctx/at/share/vacation/setting/acquisitionrule/enum/category"
        };
        /**
             * Update Acquisition Rule
             */
        export function updateAcquisitionRule(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateAcquisitionRule, command);
        }

        /**
             * Find Acquisition Rule
             */
        export function findAcquisitionRule(): JQueryPromise<model.ListAcquisitionDto> {
            return nts.uk.request.ajax(paths.findAcquisitionRule);
        }

        /**
             * Get VacationExpiration Enum.
             */
        export function categoryEnum(): JQueryPromise<model.Enum> {
            return nts.uk.request.ajax(paths.categoryEnum);
        }

        export module model {
            export interface AcquisitionOrderDto {
                annualPaidLeave: number;
                compensatoryDayOff: number;
                substituteHoliday: number;
                fundedPaidHoliday: number;
                exsessHoliday: number;
                specialHoliday: number;
            }
            export interface ListAcquisitionDto {
                category: number;
                listAcquisitionDto: AcquisitionOrderDto[];
            }
            export class Enum {
                value: number;
                fieldName: string;
                localizedName: string;

                constructor(value: number, fieldName: string, localizedName: string) {
                    this.value = value;
                    this.fieldName = fieldName;
                    this.localizedName = localizedName;
                }
            }
        }

    }
}