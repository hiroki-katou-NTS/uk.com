module nts.uk.pr.view.qmm008._0.common {
    export module service {

        /**
         *  Service paths
         */
        var paths: any = {
            getAvgEarnLevelMasterSettingList: "ctx/pr/core/insurance/social/healthrate/getAvgEarnLevelMasterSettingList",
        };
        /**
        *  Get AvgEarnLevelMasterSetting list 
        */
        export function getAvgEarnLevelMasterSettingList(): JQueryPromise<Array<any>> {
            var dfd = $.Deferred<Array<any>>();
            nts.uk.request.ajax(paths.getAvgEarnLevelMasterSettingList)
                .done(res => {
                    dfd.resolve(convertToAvgEarnLevelMasterSettingModel(res));
                })
                .fail(res => {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        function convertToAvgEarnLevelMasterSettingModel(listDto: Array<model.AvgEarnLevelMasterSettingDto>): Array<AvgEarnLevelMasterSettingModel> {
            var salMin = 0;
            for (const i in listDto) {
                var dto = listDto[i];
                dto['salMin'] = salMin;
                salMin = dto.salLimit;
            }
            return listDto;
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
                salLimit: number;
            }
        }
    }
}