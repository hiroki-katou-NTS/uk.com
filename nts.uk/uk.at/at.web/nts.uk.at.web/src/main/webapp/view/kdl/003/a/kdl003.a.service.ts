module nts.uk.at.view.kdl003.a {
    export module service {
        var paths: any = {
            findAllWorkType: "at/share/worktype/findAll",
            findWorkTypeByCodes: "at/share/worktype/getpossibleworktype",
            findAllWorkTime: "at/shared/worktime/findByCompanyID",
            findByCodeList: "at/shared/worktime/findByCodeList",
            findByTime: "at/shared/worktime/findByTime",

            //enum
            getTimeDayAtrEnum: "at/share/resttime/enum/TimeDayAtr"
        }

        export function findAllWorkType(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findAllWorkType);
        }
        export function findWorkTypeByCodes(command: Array<string>): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findWorkTypeByCodes, command);
        }

        export function findByCodeList(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findByCodeList, command);
        }
        export function findAllWorkTime(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findAllWorkTime);
        }

        export function findByTime(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findByTime, command);
        }
        export function findTimeDayAtrEnum(): JQueryPromise<Array<model.Enum>> {
            return nts.uk.request.ajax(paths.getTimeDayAtrEnum);
        }
        export module model {
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