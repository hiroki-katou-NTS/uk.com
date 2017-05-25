module nts.uk.pr.view.kmf001.c {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            update: 'ctx/at/core/vacation/setting/annualpaidleave/update',
            find: 'ctx/at/core/vacation/setting/annualpaidleave/find'
        };

        export function update(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.update, command);
        }
        
        export function find(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.find);
        }
        
        /**
        * Model namespace.
        */
        export module model {

            export class EnumerationModel {

                code: string;
                name: string;

                constructor(code: string, name: string) {
                    let self = this;
                    self.code = code;
                    self.name = name;
                }
            }
            
            export class ManageDistinct {
                public static YES = "YES";
                public static NO = "NO";
            }
            
            export class MaxDayReference {
                public static CompanyUniform = "CompanyUniform";
                public static ReferAnnualGrantTable = "ReferAnnualGrantTable";
            }
            
            export class ApplyPermission {
                public static ALLOW = "ALLOW";
                public static NOT_ALLOW = "NOT_ALLOW";
            }
            
            export class PreemptionPermit {
                public static FIFO = "FIFO";
                public static LIFO = "LIFO";
            }
            
            export class DisplayDivision {
                public static Notshow = "Notshow";
                public static Indicate = "Indicate";
            }
            
            export class TimeUnit {
                public static OneMinute = "OneMinute";
                public static FifteenMinute = "FifteenMinute";
                public static ThirtyMinute = "ThirtyMinute";
                public static OneHour = "OneHour";
                public static TwoHour = "TwoHour";
            }
        }
    }
}
