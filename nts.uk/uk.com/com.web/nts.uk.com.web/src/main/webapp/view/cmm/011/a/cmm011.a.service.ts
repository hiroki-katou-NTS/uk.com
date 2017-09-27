module nts.uk.com.view.cmm011.a {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            findLstWorkPlace: "bs/employee/workplace/config/info/find",
            findWkpHistList: "bs/employee/workplace/hist",
            findHistInfoByHistId: "bs/employee/workplace/find",
            registerWkp: "bs/employee/workplace/register",
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
