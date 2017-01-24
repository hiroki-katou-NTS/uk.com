module nts.uk.pr.view.qmm008._0.common {
    export module service {

        /**
         *  Service paths
         */
        var paths: any = {
            getAvgEarnLevelMasterSetting: "pr/proto/insurance/social/healthrate/getAvgEarnLevelMasterSetting",
        };
        /**
        *  Get AvgEarnLevelMasterSetting list 
        */
        export function getAvgEarnLevelMasterSetting(): JQueryPromise<Array<any>> {
            var dfd = $.Deferred<Array<any>>();
            nts.uk.request.ajax(paths.getAvgEarnLevelMasterSetting)
                .done(res => {
                    dfd.resolve(res);
                })
                .fail(res => {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        /**
        * Model namespace.
        */
        export module model {
            export class AvgEarnLevelMasterSettingDto {
                code: number;
                healthLevel: number;
                pensionLevel: number;
                avgEarn: number;
                salMin: number;
                salMax: number;
                /*constructor(code: number, healthLevel: number, pensionLevel: number, avgEarn: number, salLimit: number) {
                var self = this;
                self.code = code;
                self.healthLevel = healthLevel;
                self.pensionLevel = pensionLevel;
                self.avgEarn = avgEarn;
                self.salLimit = salLimit;
            }*/
            }
        }
    }
}