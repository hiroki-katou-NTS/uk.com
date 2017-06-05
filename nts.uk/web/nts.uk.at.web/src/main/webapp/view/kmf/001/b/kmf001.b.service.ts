module nts.uk.pr.view.kmf001.b {
    export module service {
        /**
         *  Service paths
         */
        var paths: any = {
            updateAcquisitionRule: 'ctx/at/share/vacation/setting/acquisitionrule/update',
            findAcquisitionRule: 'ctx/at/share/vacation/setting/acquisitionrule/find',
        };
        export function updateAcquisitionRule(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateAcquisitionRule, command);
        }
        export function findAcquisitionRule(): JQueryPromise<model.ListAcquisitionDto> {
            return nts.uk.request.ajax(paths.findAcquisitionRule);
        }
        export module model {
            export class EnumerationModel {

                value: number;
                name: string;

                constructor(value: number, name: string) {
                    let self = this;
                    self.name = name;
                    self.value = value;
                }
            }
            export interface AcquisitionOrderDto {
                annualPaidLeave: number;
                compensatoryDayOff: number;
                substituteHoliday: number;
                fundedPaidHoliday: number;
                exsessHoliday: number;
                specialHoliday: number;
            }
            export interface ListAcquisitionDto {
                catalogy: number;
                listAcquisitionDto: AcquisitionOrderDto[];
            }
        }

    }
}