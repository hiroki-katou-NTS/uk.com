module nts.uk.com.view.cmm011.a {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            findLstWorkPlace: "bs/employee/workplace/config/info/find",
        };
        
        export function findLstWorkPlace(baseDate: Date): JQueryPromise<Array<model.TreeWorkplace>> {
            return nts.uk.request.ajax(servicePath.findLstWorkPlace,{startDate: baseDate});
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
        }
    }
}
