declare module nts.uk.pr.view.kmf001.c {
    module service {
        function update(command: any): JQueryPromise<any>;
        function find(): JQueryPromise<any>;
        /**
        * Model namespace.
        */
        module model {
            class EnumerationModel {
                code: string;
                name: string;
                constructor(code: string, name: string);
            }
            class ManageDistinct {
                static YES: string;
                static NO: string;
            }
            class MaxDayReference {
                static CompanyUniform: string;
                static ReferAnnualGrantTable: string;
            }
            class ApplyPermission {
                static ALLOW: string;
                static NOT_ALLOW: string;
            }
            class PreemptionPermit {
                static FIFO: string;
                static LIFO: string;
            }
            class DisplayDivision {
                static Notshow: string;
                static Indicate: string;
            }
            class TimeUnit {
                static OneMinute: string;
                static FifteenMinute: string;
                static ThirtyMinute: string;
                static OneHour: string;
                static TwoHour: string;
            }
        }
    }
}
