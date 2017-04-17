module nts.uk.pr.view.qmm008._0.common {
    export module service {

        /**
         *  Service paths
         */
        var paths: any = {
            getHealthAvgEarnLimitList: "ctx/pr/core/insurance/avgearnmaster/health/find",
            getPensionAvgEarnLimitList: "ctx/pr/core/insurance/avgearnmaster/pension/find"
        };

        /**
        *  Get HealthAvgEarnLimit list 
        */
        export function getHealthAvgEarnLimitList(): JQueryPromise<Array<model.AvgEarnLimitDto>> {
            var dfd = $.Deferred<Array<model.AvgEarnLimitDto>>();
            nts.uk.request.ajax(paths.getHealthAvgEarnLimitList)
                .done(function(res: Array<model.AvgEarnLimitDto>) {
                    dfd.resolve(convertToHealthAvgEarnLimitModel(res));
                })
                .fail(function(res: Array<model.AvgEarnLimitDto>) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        export function getPensionAvgEarnLimitList(): JQueryPromise<Array<model.AvgEarnLimitDto>> {
            var dfd = $.Deferred<Array<model.AvgEarnLimitDto>>();
            nts.uk.request.ajax(paths.getPensionAvgEarnLimitList)
                .done(function(res: Array<model.AvgEarnLimitDto>) {
                    dfd.resolve(convertToHealthAvgEarnLimitModel(res));
                })
                .fail(function(res: Array<model.AvgEarnLimitDto>) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        function convertToHealthAvgEarnLimitModel(listDto: Array<model.AvgEarnLimitDto>): Array<model.AvgEarnLimitDto> {
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
            export interface AvgEarnLimitDto {
                grade: number;
                avgEarn: number;
                salLimit: number;
                salMin: number;
            }
        }
    }
}