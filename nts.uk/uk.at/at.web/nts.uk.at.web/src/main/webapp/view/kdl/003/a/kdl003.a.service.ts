module nts.uk.at.view.kdl003.a {
    export module service {
        var paths: any = {
            findAllWorkType: "at/share/worktype/findAll",
            findAllRestTime: "at/share/resttime/getresttime",
            findByCodeList: "at/shared/worktime/findByCodeList",
            findByTime: "at/shared/worktime/findByTime",

            //enum
            getTimeDayAtrEnum: "at/share/resttime/enum/TimeDayAtr"
        }

        export function findAllWorkType(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findAllWorkType, command);
        }

        export function findAllRestTime(command: string): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findAllRestTime+"/"+command);
        }

        export function findByCodeList(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findByCodeList, command);
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