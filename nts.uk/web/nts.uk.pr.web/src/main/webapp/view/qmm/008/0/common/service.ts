module nts.uk.pr.view.qmm008._0.common {
    export module service {

        /**
         *  Service paths
         */
        var paths: any = {
            getAvgEarnLevelMasterSettingList: "ctx/pr/core/insurance/avgearnmaster/find",
        };
        /**
        *  Get AvgEarnLevelMasterSetting list 
        */
        export function getAvgEarnLevelMasterSettingList(): JQueryPromise<Array<model.AvgEarnLevelMasterSettingDto>> {
            var dfd = $.Deferred<Array<model.AvgEarnLevelMasterSettingDto>>();
            nts.uk.request.ajax(paths.getAvgEarnLevelMasterSettingList)
                .done(function(res:Array<model.AvgEarnLevelMasterSettingDto>) {
                    dfd.resolve(convertToAvgEarnLevelMasterSettingModel(res));
                })
                .fail(function(res:Array<model.AvgEarnLevelMasterSettingDto>){
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        function convertToAvgEarnLevelMasterSettingModel(listDto: Array<model.AvgEarnLevelMasterSettingDto>): Array<model.AvgEarnLevelMasterSettingDto> {
            var salMin: number = 0;
            for (const i in listDto) {
                var dto = listDto[i];
                dto.salMin = salMin;
                salMin = dto.salLimit;
            }
            return listDto;
        }

        /**
        * Model namespace.
        */
        export module model {
            export interface AvgEarnLevelMasterSettingDto {
                code: number;
                healthLevel: number;
                pensionLevel: number;
                avgEarn: number;
                salLimit: number;
                salMin: number;
            }
        }
    }
}