module nts.uk.com.view.cmm011.a {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            findLstWorkPlace: "bs/employee/workplace/config/info/find",
            getListWkpConfigHistory: "bs/employee/workplace/config/findAll",
            checkWorkplaceState: "bs/employee/workplace/config/validWkp",

            findWkpHistList: "bs/employee/workplace/hist",
            saveWkp: "bs/employee/workplace/save",
            removeWkp: "bs/employee/workplace/remove",
            findHistInfoByHistId: "bs/employee/workplace/info/findHistInfo",

            removeWorkplaceHistory: "bs/employee/workplace/history/remove",
            updateTree : "bs/employee/workplace/updateTree"
        };

        /**
         * findLstWorkPlace
         */
        export function findLstWorkPlace(baseDate: Date): JQueryPromise<Array<model.TreeWorkplace>> {
            return nts.uk.request.ajax(servicePath.findLstWorkPlace, { baseDate: baseDate });
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
         * checkWorkplaceState
         */
        export function checkWorkplaceState(workplace: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.checkWorkplaceState, workplace);
        }

        /**
        * saveWkp
        */
        export function saveWkp(command: any): JQueryPromise<void> {
            return nts.uk.request.ajax(servicePath.saveWkp, command);
        }

        /**
         * removeWkp
         */
        export function removeWkp(command: any): JQueryPromise<void> {
            return nts.uk.request.ajax(servicePath.removeWkp, command);
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
                let list: Array<base.IHistory> = _.map(res ? res.wkpConfigHistory : [], function(item: any) {
                    return {
                        workplaceId: "",
                        historyId: item.historyId,
                        startDate: item.startDate,
                        endDate: item.endDate
                    };
                });
                dfd.resolve(list);
            });
            return dfd.promise();
        }
        /**
         * function update tree
         */
        export function updateTree(lstWorkplace:Array<model.TreeWorkplace>):JQueryPromise<void> {
            return nts.uk.request.ajax(servicePath.updateTree,{ listWorkplaceHierarchyDto: lstWorkplace });
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
                histId:string;
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
