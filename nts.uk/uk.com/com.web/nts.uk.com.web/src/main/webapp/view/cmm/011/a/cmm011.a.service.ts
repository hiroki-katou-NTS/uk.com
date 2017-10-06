module nts.uk.com.view.cmm011.a {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            findLstWorkPlace: "bs/employee/workplace/config/info/find",
            findWkpHistList: "bs/employee/workplace/hist",
            findHistInfoByHistId: "bs/employee/workplace/findHistInfo",
            registerWkp: "bs/employee/workplace/register",
            
            getListWkpConfigHistory: "bs/employee/workplace/config/findAll",
            
            removeWorkplaceHistory: "bs/employee/workplace/history/remove",
        };

        /**
         * findLstWorkPlace
         */
        export function findLstWorkPlace(baseDate: Date): JQueryPromise<Array<model.TreeWorkplace>> {
            return nts.uk.request.ajax(servicePath.findLstWorkPlace, { startDate: baseDate });
        }
        
        /**
         * getLstWkpHist
         */
        export function getLstWkpHist(wkpId: string): JQueryPromise<model.Workplace> {
            return nts.uk.request.ajax(servicePath.findWkpHistList + "/" + wkpId);
        }

        /**
         * getLstWkpHist
         */
        export function getWkpInfoByHistId(wkpId: string, historyId: string): JQueryPromise<model.Workplace> {
            return nts.uk.request.ajax(servicePath.findHistInfoByHistId, { workplaceId: wkpId, historyId: historyId });
        }
        
         /**
         * registerWkp
         */
        export function registerWkp(data: any): JQueryPromise<model.Workplace> {
            return nts.uk.request.ajax(servicePath.registerWkp, data);
        }
        
        /**
         * removeWorkplaceHistory
         */
        export function removeWorkplaceHistory(command: any): JQueryPromise<void> {
            return nts.uk.request.ajax(servicePath.removeWorkplaceHistory, command);
        }
        
        /**
         * findLstWkpConfigHistory
         */
        export function findLstWkpConfigHistory(): JQueryPromise<Array<base.IHistory>> {
            let dfd = $.Deferred<Array<base.IHistory>>();
            nts.uk.request.ajax(servicePath.getListWkpConfigHistory).done((res: any) => {
                let list: Array<base.IHistory> = _.map(res.wkpConfigHistory, function(item: any) {
                    return {
                        workplaceId: "",
                        historyId: item.historyId,
                        startDate: item.period.startDate,
                        endDate: item.period.endDate
                    };
                });
                dfd.resolve(list);
            });
            return dfd.promise();
        }
        
        /**
        * Model namespace.
        */
        export module model {
            export interface TreeWorkplace {
                workplaceId: string;
                code: string;
                name: string;
                nodeText?: string;
                hierarchyCode: string;
                level?: number;
                childs: Array<TreeWorkplace>;
            }

            export interface Workplace {
                companyId: string;
                workplaceId: string;
                workplaceHistory: Array<WorkplaceHistory>;
            }

            export interface WorkplaceHistory {
                historyId: string;
                period: Period;
            }

            export interface Period {
                startDate: string;
                endDate: string;
            }
        }
    }
}
